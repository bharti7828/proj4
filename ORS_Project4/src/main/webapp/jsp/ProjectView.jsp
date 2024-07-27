<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.ProjectCtl"%>
<%@page import="com.rays.pro4.controller.BaseCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">

 <link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
		//dateFormat:'yy-mm-dd'
		});
	});
</script>
 	

<title>Insert title here</title>
</head>

<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ProjectBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PROJECT_CTL%>" method="post">
		
		<%
				List clist = (List) request.getAttribute("categoryy");
			%>

		

			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Project </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Project </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table>
				<tr>
					<th align="left">Name<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="name" placeholder="Enter Name" onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="30"
						size="25" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				
				</tr>
				
					<tr>
					<th align="left">Category <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("category", String.valueOf(bean.getCategory()), clist)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("category", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				
				<tr>
					<th align="left">OpenDate <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="openDate"
						placeholder="Enter OpenDate" size="25" readonly="readonly"
						id="udatee" value="<%=DataUtility.getDateString(bean.getOpenDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("openDate", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Version<span style="color: red">*</span>:
					</th>
					<td><input type="text" name="version" placeholder="Enter Vsersion" step ="any"
						size="25"
						value="<%=DataUtility.getDoublee(bean.getVersion())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("version", request)%></font></td>

				</tr>
			<tr>
					<th style="padding: 3px"></th>
				
				
								
				<%-- <tr>
					<th align="left">ProjectName  <span style="color: red">*</span> :
					</th>
					<td><input type="text"  name="ProjectName"
						maxlength="10" placeholder="Enter Project Name" size="25"
						value="<%=DataUtility.getStringData(bean.getProjectName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("ProjectName", request)%></font></td>
				</tr>				 --%>
				
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<table align="center">
				<tr>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ProjectCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ProjectCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

                       

					  <td colspan="2">  
					  <input type="submit" name="operation" value="<%=ProjectCtl.OP_SAVE%>">&nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ProjectCtl.OP_RESET%>"></td>
					</center>
					<%
						}	
					%>
				</tr>
</table>
			</table>
	</center>

	<%@ include file="Footer.jsp"%>






</body>
</html>
