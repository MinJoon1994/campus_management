package member.service;

import javax.servlet.http.HttpServletRequest;

import member.dao.MemberDAO;
import member.vo.ProfessorVO;
import member.vo.StaffVO;
import member.vo.StudentVO;
import member.vo.UserVO;

public class MemberService {
	
	UserVO userVO = new UserVO();
	StudentVO studentVO = new StudentVO();
	ProfessorVO professorVO = new ProfessorVO();
	StaffVO staffVO = new StaffVO();
	
	MemberDAO memberDAO = new MemberDAO();
	
	public void insertMember(HttpServletRequest req) {
		
		// 회원 가입 처리
		String email = req.getParameter("email");
    	String password = req.getParameter("password");
    	String name = req.getParameter("name");
    	String gender = req.getParameter("gender");
    	String phone = req.getParameter("phone");
    	String role = req.getParameter("role");
    	
    	userVO.setEmail(email);
    	userVO.setPassword(password);
    	userVO.setName(name);
    	userVO.setRole(role);
    	
    	if(role.equals("student")) { //학생이 회원가입 하는 경우라면?
    		String studentdepartment = req.getParameter("student_department");
    		String grade = req.getParameter("grade");
    		String status = req.getParameter("status");
    	
    		studentVO.setDepartment(studentdepartment);
    		studentVO.setGrade(grade);
    		studentVO.setStatus(status);
    		
    		// DB에 회원 정보 저장
    		memberDAO.insertStudent(userVO, studentVO);
    		
		} else if(role.equals("professor")) { //교수가 회원가입 하는 경우라면?
			
			String professorDepartment = req.getParameter("professor_department");
			professorVO.setProfessor_department(professorDepartment);
			
			// DB에 회원 정보 저장
			memberDAO.insertProfessor(userVO, professorVO);
			
		}
	}

	public UserVO login(String email, String password) {
		
		//로그인
		return memberDAO.login(email, password);
	}

	public boolean serviceOverLappedId(HttpServletRequest req) {
		// 요청 파라미터에서 'id' 값을 가져옵니다.
        String email = req.getParameter("email");
        // MemberDAO의 overlappedId 메소드를 호출하여 DB에서 아이디 중복 검사를 수행하고 결과를 반환합니다.
        // DAO의 반환값 (true: 중복 존재, false: 중복 없음)을 그대로 Controller에게 전달합니다.
        return memberDAO.overlappedId(email);
	}

}
