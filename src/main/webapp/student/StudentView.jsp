<%@ page isELIgnored="false" %>
<html>
<head><title>Student View</title></head>
<body>
	<h2>Student Information</h2>
	<img src="../avatar/${id}" />
	<table>
		<tr><td>Name:</td><td>${name}</td></tr>
		<tr><td>Age:</td> <td>${age}</td> </tr>
	</table>
	<br/>
	<a href="/">Home</a>
</body>
</html>
