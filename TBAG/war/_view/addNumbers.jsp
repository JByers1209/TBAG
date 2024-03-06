<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="edu.ycp.cs320.lab02.model.Numbers" %>

<html>
	<head>
		<title>Add Numbers</title>
		<style type="text/css">
		.error {
			color: red;
		}
		
		td.label {
			text-align: right;
		}
		</style>
	</head>

	<body>
		<c:if test="${! empty errorMessage}">
			<div class="error">${errorMessage}</div>
		</c:if>
	
		<form action="${pageContext.servletContext.contextPath}/addNumbers" method="post">
			<table>
				<tr>
					<td class="label">First number:</td>
					<td><input type="get" name="first" size="12" value= "${controller.model.first}" /></td>
				</tr>
				<tr>
					<td class="label">Second number:</td>
					<td><input type="get" name="second" size="12" value="${controller.model.second}" /></td>
				</tr>
				<tr>
					<td class="label">Third number:</td>
					<td><input type="get" name="third" size="12" value="${controller.model.third}" /></td>
				</tr>
				<tr>
					<td class="label">Result:</td>
					<td><c:out value ="${controller.model.result}"/></td>
				</tr>
			</table>
			<input type="Submit" name="submit" value="Add Numbers!">
		</form>
	</body>
</html>