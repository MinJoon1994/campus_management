<%@page import="java.io.BufferedReader"%>
<%@page import="main.DbcpBean"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"%>
<%@ page import="java.sql.*, com.google.gson.*, java.util.*"%>

<%
    request.setCharacterEncoding("UTF-8");

    System.out.println("요일중복체크 시작...\n");

    // JSON 요청 본문 읽기
    BufferedReader reader = request.getReader();
    StringBuilder json = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null)
        json.append(line);

    // JSON 파싱
    Gson gson = new Gson();
    Map<String, Object> data = gson.fromJson(json.toString(), Map.class);

    String professorId = data.get("professorId").toString();
    String day = ((String) data.get("day")).trim();
    int start = ((Double) data.get("start")).intValue();
    int end = ((Double) data.get("end")).intValue();

    System.out.println("요일중복체크 교수 아이디 : " + professorId);
    System.out.println("요일중복체크 day : " + day);
    System.out.println("요일중복체크 start : " + start);
    System.out.println("요일중복체크 end : " + end);

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    boolean conflict = false;

    try {
        conn = DbcpBean.getConnection();
        String sql = "SELECT schedule FROM subject WHERE professor_id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, professorId);
        rs = pstmt.executeQuery();

        while (rs.next()) {
            String schedule = rs.getString("schedule");
            System.out.println("조회된 스케줄: " + schedule);
            // 조회된 스케줄: 금 6-7교시

            if (schedule.contains(day)) {
                String[] entries = schedule.split(",\\s*");
                for (String entry : entries) {
                    System.out.println("요일중복체크 날짜 : " + entry);
                    // 요일중복체크 날짜 : 금 6-7교시
                    if (entry.startsWith(day)) {
                        String[] parts = entry.replace("교시", "").split(" ")[1].split("-");
                        int dbStart = Integer.parseInt(parts[0]);
                        int dbEnd = Integer.parseInt(parts[1]);
                        System.out.println("→ 비교 대상: " + dbStart + " ~ " + dbEnd);
                        // → 비교 대상: 6 ~ 7

                        if (!(end < dbStart || start > dbEnd)) {
                            System.out.println("중복됨! => start: " + start + ", end: " + end);
                            // 중복됨! => start: 6, end: 7
                            out.print("DUPLICATE");
                            return;
                        }
                    }
                }
            }

            if (conflict) break;
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.print("ERROR");
        return;
    } finally {
        if (rs != null) try { rs.close(); } catch (Exception e) {}
        if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
        if (conn != null) try { conn.close(); } catch (Exception e) {}
    }

    out.print(conflict ? "DUPLICATE" : "OK");
%>
