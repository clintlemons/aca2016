<%-- 
    Document   : CalhounChinGenreMVC
    Created on : Dec 15, 2016, 7:11:35 PM
    Author     : calho
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Chinook Genre Manager</title>
    <style>body { font-family: Verdana, Geneva, sans-serif; }</style>
  </head>
  <body>
    
    <c:if test="${not empty message}">
      <span style="color: red;">${message}</span>
    </c:if>
    
    <form method="post">
    <c:choose>
      <c:when test="${dothis eq 'Delete'}">
        <h1>Delete Genre</h1>
        <input type="hidden" name="id" value="${id}" />
        Are you sure you want to delete genre "${genre_name}"
        <input type="submit" value="Delete" name="action" />
      </c:when>
      <c:when test="${dothis eq 'Edit'}">
        <h1>Edit Genre</h1>
        <input type="hidden" name="id" value="${id}" />
        Genre Name: <input type="text" name="name" value="${genre_name}" />
        <input type="submit" value="Edit" name="action" />
      </c:when>
      <c:otherwise>
        <h1>Add a Genre</h1>
        Genre Name: <input type="text" name="name" />
        <input type="submit" value="Add" name="action" />
      </c:otherwise>
    </c:choose>
    </form>
            
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
          <td><a href="?do=Edit&id=${genre.key}">Edit</a> <a href="?do=Delete&id=${genre.key}">Delete</a></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>

  </body>
</html>

