<%@page import="main.DbcpBean"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/plain; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String subjectCode = request.getParameter("subject_code");

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = DbcpBean.getConnection();
        String sql = "SELECT subject_code FROM Subject WHERE subject_code = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, subjectCode);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            out.print("DUPLICATE"); // 존재하면 DUPLICATE
        } else {
            out.print("OK"); // 없으면 OK
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.print("ERROR");
    } finally {
        DbcpBean.close(conn, pstmt, rs);
    }
%>
