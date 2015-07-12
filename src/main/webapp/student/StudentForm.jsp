<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Add New Student</title>
</head>
<body>
	<h2>Please Input Student's Information</h2>
	<c:choose>
		<c:when test="${id == null}">
			<c:set var="action" value="add"/>
		</c:when>
		<c:otherwise>
			<c:set var="action" value="../add"/>
		</c:otherwise>
	</c:choose>

	<form:form method="POST" id="command" action="${action}">
		<table>
			<tr>
				<td><form:hidden path="id"/></td>
			</tr>
			<tr>
				<td><form:label path="name">Name</form:label></td>
				<td><form:input path="name" /></td>
				<td><form:errors path="name" /></td>
			</tr>
			<tr>
				<td><form:label path="age">Age</form:label></td>
				<td><form:input path="age" type="number" /></td>
				<td><form:errors path="age" /></td>
			</tr>
			<tr><td colspan="3"><input type="submit" value="Submit" /></td></tr>
		</table>
	</form:form>

	<c:choose>
		<c:when test = "${id != null}">
			<h1>Please upload a image</h1>
			<form method="post" action="../avatar/save" enctype="multipart/form-data">
				<input type="hidden" name="id" value="${id}"/>
				<input type="file" name="file" />
				<input type="submit" value="Upload" />
			</form>
		</c:when>
	</c:choose>
	
</body>
</html>
