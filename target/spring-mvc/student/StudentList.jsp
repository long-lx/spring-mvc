<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <title>Student List</title>
  <script>
    function viewStudent(event, id) {
      var xmlHttp = new XMLHttpRequest();
      xmlHttp.open("GET", "json/" + id, true);
      xmlHttp.onload = function() {
        if (this.status === 200) {
          var student = JSON.parse(this.responseText)
          showPos(event, '<table>'
                        + '<tr><td>ID:</td><td>' + student.id + '</td></tr>'
                        + '<tr><td>Name:</td><td>' + student.name + '</td></tr>'
                        + '<tr><td>Age:</td><td>' + student.age + '</td></tr>'
                        + '</table>');
        }
      };
      xmlHttp.send();
    }

    function showPos(event, text) {
      var el = document.getElementById('PopUp');
      var x, y;
      if (window.event) {
        x = window.event.clientX + document.documentElement.scrollLeft + document.body.scrollLeft;
        y = window.event.clientY + document.documentElement.scrollTop + document.body.scrollTop;
      } else {
        x = event.clientX + window.scrollX;
        y = event.clientY + window.scrollY;
      }
      x -= 2; y -= 2;
      y = y + 15;
      el.style.left = x + "px";
      el.style.top = y + "px";
      el.style.display = "block";
      document.getElementById('PopUpText').innerHTML = text;
    }
  </script>
</head>
<body>

  <DIV id='PopUp' style='display: none; position: absolute; left: 100px; top: 50px; border:
    solid black 1px; padding: 10px; background-color: rgb(200,100,100); text-align: justify; font-size:
    12px; width: 135px;' onmouseover="document.getElementById("PopUp").style.display = 'none'">
    <SPAN id="PopUpText">TEXT</SPAN>
  </DIV>

  <tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="body">

  <h2>List of Student</h2>
  <a href="../student/form">Add Student</a>
  <br>
  <br>
  <table border="1">
  	<tr>
  		<form method="GET" action="list">
  		<td colspan="4"><input type="text" name="q" size="30"/></td>
  		<td><input type="submit" value="Search"/></td>
  		</form>
  	</tr>
  </table>
  <table border="10" width="100%" ID="Table2" style="margin: 0px;">
  	<tr>
  		<td>Id</td>
  		<td>Name</td>
  		<td>Age</td>
  		<td>Modify</td>
  	</tr>

  	<c:forEach items="${students}" var="student">
  	<tr>
  		<td>${student.id}</td>
  		<td><a href="javascript:void(null)" onmouseover = "viewStudent(event, ${student.id})">${student.name}</a></td>
  		<td>${student.age}</td>
  		<td><a href="edit/${student.id}">Edit</a></td>
  		<td><a href="delete/${student.id}">Delete</a></td>
  	</tr>
  	</c:forEach>
  </table>
</tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>
