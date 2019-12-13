<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<style type="text/css">
table {
	border-collapse: separate;
	width: 100%;
}

th, td {
	padding: 8px;
	text-align: left;
}

tr:nth-child() {
	background-color: #f2f2f2;
}

th {
	background-color: #4CAF50;
	color: white;
}
</style>
<title>Books Store Application</title>
</head>
<body>
	<center>
		<h1>Books Management</h1>
		<h2>
			<a href="/Bookstore/new">Add New Book</a> &nbsp;&nbsp;&nbsp; <a href="/Bookstore/list">ListAll
				Books</a>
		</h2>
	</center>
	<div align="center">
		<table>
			<caption>
				<h2>List of Books</h2>
			</caption>
			<tr>
				<th>ID</th>
				<th>Title</th>
				<th>Author</th>
				<th>Price</th>
				<th>Actions</th>
			</tr>
			<c:forEach var="book" items="${listBook}">
				<tr>
					<td><c:out value="${book.id}" /></td>
					<td><c:out value="${book.title}" /></td>
					<td><c:out value="${book.author}" /></td>
					<td><c:out value="${book.price}" /></td>
					<td><a href="/Bookstore/edit?id=<c:out value='${book.id}' />">Edit</a> <a
						href="/Bookstore/delete?id=<c:out value='${book.id}' />">Delete</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>