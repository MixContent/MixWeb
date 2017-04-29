<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Document Manager Sriman_Java_Gyan_Mantra</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}/getImage?id=1">Check
		Image</a>
	<h1 align="center">${message}</h1>
	<h3>Add new document</h3>
	<form method="post" action="${pageContext.request.contextPath}/save"
		enctype="multipart/form-data">

		<table>
			<tr>
				<td>Name :</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>Description :</td>
				<td><input type="text" name="description"></td>
			</tr>
			<tr>
				<td>Document :</td>
				<td><input type="file" name="myFile" class="file"></input></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Add Document" /></td>
			</tr>
		</table>
	</form>
	<br />
	<img src="image/demo" height="75px" width="75px" align="left" />
</body>
</html>