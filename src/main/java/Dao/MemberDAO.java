package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Vo.ProfessorVO;
import Vo.StudentVO;
import Vo.UserVO;

public class MemberDAO {
	
	Connection con = null;
    PreparedStatement pstmtUser = null;
    PreparedStatement pstmtStudent = null;
    PreparedStatement pstmtProfessor = null;
    ResultSet rs = null;
    
	public void insertStudent(UserVO userVO, StudentVO studentVO) {
		
		try {
			
			con = DbcpBean.getConnection();
			
			 // 1. user 테이블에 INSERT
	        String sqlUser = "INSERT INTO user (password, name, email, role) " +
	                         "VALUES (?, ?, ?, ?)";
	        
	        pstmtUser = con.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);

	        pstmtUser.setString(1, userVO.getPassword());
	        pstmtUser.setString(2, userVO.getName());
	        pstmtUser.setString(3, userVO.getEmail());
	        pstmtUser.setString(4, userVO.getRole().toUpperCase()); // 대소문자 맞춤!
	        pstmtUser.executeUpdate();
	        
	        // 2. 방금 넣은 user의 id 얻기
	        rs = pstmtUser.getGeneratedKeys();
	        int userId = -1;
	        if (rs.next()) {
	            userId = rs.getInt(1);
	        }
	        
	        // 3. student 테이블에 INSERT
	        String sqlStudent = "INSERT INTO student (user_id, department, grade, status) VALUES (?, ?, ?, ?)";
	        pstmtStudent = con.prepareStatement(sqlStudent);
	        pstmtStudent.setInt(1, userId);
	        pstmtStudent.setString(2, studentVO.getDepartment());
	        pstmtStudent.setInt(3, Integer.parseInt(studentVO.getGrade()));
	        pstmtStudent.setString(4, studentVO.getStatus());

	        pstmtStudent.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbcpBean.close(con,pstmtUser,pstmtStudent);
		}
	
	}

	public void insertProfessor(UserVO userVO, ProfessorVO professorVO) {
		
		try {
			
			con = DbcpBean.getConnection();
			
			 // 1. user 테이블에 INSERT
	        String sqlUser = "INSERT INTO user (password, name, email, role) " +
	                         "VALUES (?, ?, ?, ?)";
	        
	        pstmtUser = con.prepareStatement(sqlUser, Statement.RETURN_GENERATED_KEYS);

	        pstmtUser.setString(1, userVO.getPassword());
	        pstmtUser.setString(2, userVO.getName());
	        pstmtUser.setString(3, userVO.getEmail());
	        pstmtUser.setString(4, userVO.getRole().toUpperCase()); // 대소문자 맞춤!
	        pstmtUser.executeUpdate();
	        
	        // 2. 방금 넣은 user의 id 얻기
	        rs = pstmtUser.getGeneratedKeys();
	        int userId = -1;
	        if (rs.next()) {
	            userId = rs.getInt(1);
	        }
	        
	        // 3. professor 테이블에 INSERT
	        String sqlStudent = "INSERT INTO professor (user_id,department) VALUES (?,?)";
	        pstmtProfessor = con.prepareStatement(sqlStudent);
	        pstmtProfessor.setInt(1, userId);
	        pstmtProfessor.setString(2, professorVO.getProfessor_department());

	        pstmtProfessor.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbcpBean.close(con,pstmtUser,pstmtProfessor);
		}
		
	}

	public boolean overlappedId(String email) {
		
		// 결과를 저장할 변수, 기본값은 false (중복 없음)
        boolean result = false;

        try {
              // 1. 커넥션 풀에서 Connection 객체 얻기 (DB 연결)
              con = DbcpBean.getConnection();

              // 2. SQL 쿼리 준비:
              //    member 테이블에서 입력받은 id와 일치하는 레코드 수를 세고,
              //    Oracle의 decode 함수를 사용하여 개수가 1이면 'true'(중복), 0이면 'false'(중복 아님) 문자열 반환
              String sql  = "SELECT CASE WHEN COUNT(*) = 1 THEN 'true' ELSE 'false' END AS result "
	                      + "FROM user "
	                      + "WHERE email = ?";

              // 3. PreparedStatement 객체 생성 및 파라미터 설정
              pstmtUser = con.prepareStatement(sql);
              pstmtUser.setString(1, email); // 첫 번째 '?' 자리에 id 값 바인딩

              // 4. SQL 쿼리 실행 및 결과 받기
              rs = pstmtUser.executeQuery(); // SELECT 쿼리 실행

              // 5. 결과 처리
              if(rs.next()) { // 결과 행이 존재하면
                  // 'result' 컬럼의 값을 문자열로 가져옴 ('true' 또는 'false')
                  String value  = rs.getString("result");
                  // 문자열을 boolean 타입으로 변환하여 result 변수에 저장
                  result = Boolean.parseBoolean(value);
              }

        }catch (Exception e) {
            // 예외 발생 시 콘솔에 오류 메시지 출력
            System.out.println("MemberDAO.overlappedId() 메소드 오류: "+e);
            
            e.printStackTrace();
        }finally {
            // 6. 사용한 자원 해제 (DB 연결 반납 등)
        	DbcpBean.close(con, pstmtUser, rs);
        }

        // 7. 최종 결과 반환 (true: 아이디 중복, false: 아이디 사용 가능)
        return result;
	}

	public UserVO login(String email, String password) {
		
		UserVO vo = new UserVO();
		
		try {
			con = DbcpBean.getConnection();
			String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
			pstmtUser = con.prepareStatement(sql);
			pstmtUser.setString(1, email);
			pstmtUser.setString(2, password);
			
			rs = pstmtUser.executeQuery();
			
			if(rs.next()) {
				vo.setId(rs.getInt("user_id"));
				vo.setEmail(rs.getString("email"));
				vo.setName(rs.getString("name"));
				vo.setPassword(rs.getString("password"));
				vo.setRole(rs.getString("role"));
				
				//로그인시 학생인 경우 학생정보 VO에 추가
				if(rs.getString("role").equals("STUDENT")) {
					
					StudentVO studentVO = new StudentVO();
					pstmtUser = con.prepareStatement("SELECT * FROM student WHERE user_id = ?");
					pstmtUser.setInt(1, rs.getInt("user_id"));
					rs = pstmtUser.executeQuery();
					
					if(rs.next()) {
						studentVO.setDepartment(rs.getString("department"));
						studentVO.setGrade(rs.getString("grade"));
						studentVO.setStatus(rs.getString("status"));
					}
					
					vo.setStudentVO(studentVO);
					
				}
				//로그인시 교수인 경우 교수정보 VO에 추가
				else if(rs.getString("role").equals("PROFESSOR")) {
					
					ProfessorVO professorVO = new ProfessorVO();
					pstmtUser = con.prepareStatement("SELECT * FROM professor WHERE user_id = ?");
					pstmtUser.setInt(1, rs.getInt("user_id"));
					rs = pstmtUser.executeQuery();
					
					if(rs.next()) {
						professorVO.setProfessor_department(rs.getString("department"));
					}
					
					vo.setProfessorVO(professorVO);
					
				}
				
				return vo;
			}
	
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			DbcpBean.close(con, pstmtUser, rs);
		}
		
		return null;
	}
	
}
