package professordao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * JNDI를 통해 DataSource(Connection Pool)를 얻어오고,
 * 필요할 때 Connection 객체를 제공하고,
 * 사용 후 자원을 반납하는 유틸리티 클래스.
 */
public class DbcpBean {
	/*
	 	기존 데이터베이스 연동은 웹 애플리케이션이 필요할 때마다 데이터베이스에 연결하여 작업하는 방식이다.
	 	
	 	✅ DataSource(커넥션풀)
	 	= 웹 애플리케이션이 시작될 때 DB 연결(Connection)을 미리 여러 개 만들어 두고,
		웹 요청이 들어오면 그 중 하나를 빌려주고, 사용 후 다시 풀에 반납한다.
		
		🔁 흐름 정리
		1. 웹 애플리케이션 시작
		→ 톰캣이 context.xml이나 application.yml 설정을 읽고
		→ DataSource를 생성하고 커넥션들을 미리 확보함
		
		2. 요청 발생 시
		→ DAO에서 DataSource.getConnection()으로 커넥션을 빌림
		→ 작업 후 conn.close()로 반환 (커넥션 종료가 아니라 반납)
		
		3. 웹 애플리케이션 종료 시
		→ 커넥션풀과 커넥션도 함께 소멸됨
	*/
	
	/*
	 	✅ JNDI란?
	 	= "자바에서 이름으로 자원을 찾아서 쓸 수 있게 해주는 기술".
	 	
	 	✅ 왜 필요한가?
	 	= 웹 애플리케이션에서 DB 연결, 메일 서버, 파일시스템 등 공통적으로 많이 쓰는 자원들을 "이름"으로 등록해두고
		  어디서든 쉽게 찾아 쓸 수 있게 하려는 목적.

		Context envContext = (Context) initContext.lookup("java:comp/env");
		ds = (DataSource) envContext.lookup("jdbc/oracle");
		=> "톰캣에 설정된 jdbc/oracle 이라는 이름의 DB 연결(DataSource)을 꺼내줘!"
		
		🔁 만약 JNDI를 안 쓰면?
	 	DB 주소, 계정, 패스워드 등을 매번 코드에 직접 써야 한다.
	 	=> 보안, 유지보수에 치명적이고, 환경 바뀔 때마다 코드 바꿔야 한다.
	 */
	
    private static DataSource ds;

    // static 초기화 블록: 클래스 로딩 시 한 번만 실행됨
    static {
        try {
            // 1. JNDI 서버 객체 생성 (InitialContext)
            Context initContext = new InitialContext();
            // 2. Context 객체를 얻어옴 (java:comp/env)
            Context envContext = (Context) initContext.lookup("java:comp/env");
            // 3. DataSource 객체 찾기 (JNDI 이름: "jdbc/mysql")
            //    context.xml 에 설정된 name 값 ("jdbc/mysql")을 사용한다.
            ds = (DataSource) envContext.lookup("jdbc/mysql");
            System.out.println("DataSource lookup 성공!");

        } catch (NamingException e) {
            System.err.println("DataSource lookup 실패: " + e.getMessage());
            e.printStackTrace();
            // 앱 실행에 필수적이므로 RuntimeException 발생시켜서 문제를 알림
            throw new RuntimeException("DataSource lookup 실패", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (ds == null) {
            // static 초기화 블록에서 문제가 발생했을 경우
            throw new SQLException("DataSource가 초기화되지 않았습니다.");
        }
        // DataSource 로부터 Connection 객체를 얻어냄 (빌려옴)
        return ds.getConnection();
    }
    // ------------------------------- close() ------------------------------------------
    
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close(); // Pool에게 Connection 반납
            } catch (SQLException e) {
                System.err.println("Connection 반납 실패: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.err.println("Statement 닫기 실패: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.err.println("ResultSet 닫기 실패: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        close(rs);
        close(stmt);
        close(conn);
    }
    public static void close(Connection conn, Statement stmt) {
        close(stmt);
        close(conn);
    }
    public static DataSource getDataSource() {
        return ds;
    }
}
