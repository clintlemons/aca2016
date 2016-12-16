<%-- 
    Document   : CalhounChinookGenreJSP
    Created on : Dec 14, 2016, 6:39:07 PM
    Author     : calho
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.HashMap"%>
<%@page import="edu.uca.aca2016.chinook.calhoun512.ChinookGenreManager" %>
<%
    ChinookGenreManager cgm = new ChinookGenreManager();
    HashMap<Integer, String> genres = cgm.getGenres();
    request.setAttribute("genres", genres);
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chinook Genre Manager</title>
        <style>body { font-family: Verdana, Geneva, sans-serif; }</style>
    </head>
    <body>
        <h1>Manage Genre</h1>
        <table border="1">
            <thead>
                <tr> 
                    <th>ID</th>
                    <th>Name</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${genres}" var="genre">
                    <tr>
                        <td>${genre.key}</td>
                        <td>${genre.value}</td>
                        <td><a href="?do=Edit&id=${genre.key}">Edit</a> <a href="?do=Edit&id=${genre.key}">Delete</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
    </body>
</html>
