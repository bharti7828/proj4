<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.FollowUpCtl"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.FollowUpBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.FOLLOWUP_CTL%>" method="post">
		
		<%
				List plist = (List)request.getAttribute("patientt");
		//List dlist = (List)request.getAttribute("doctor"); //
		
			%>

			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update FollowUp </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add FollowUp </font></th>
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
					<th align="left">Patient<span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("patient", String.valueOf(bean.getPatient()), plist)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("patient", request)%></font></td>
				</tr>
				
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				
				<%-- <tr>
					<th align="left">Doctor<span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("doctor", String.valueOf(bean.getDoctor()), dlist)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("doctor", request)%></font></td>
				</tr> --%>
				<tr>
					<th align="left">Doctor <span style="color: red">*</span>:
					</th>
					<td><input type="text" name="doctor" onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="20"
						placeholder="Enter doctor" size="25"
						value="<%=DataUtility.getStringData(bean.getDoctor())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("doctor", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				

				<tr>
					<th align="left">VisitDate <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="visitDate"
						placeholder="Enter VisitDate" size="25" readonly="readonly"
						id="udatee" value="<%=DataUtility.getDateString(bean.getVisitDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("visitDate", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				

				<tr>
					<th align="left">Fees<span style="color: red">*</span>:
					</th>
					<td><input type="text" name="fees" placeholder="Enter Fees"
						size="25"
						value="<%=DataUtility.getStringData(bean.getFees()).equals("0") ? "" : DataUtility.getStringData(bean.getFees())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("fees", request)%></font></td>

				</tr>
               
					<th style="padding: 3px"></th>
				</tr>
				<table align="center">
				<tr>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=FollowUpCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=FollowUpCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

                       

					  <td colspan="2">  
					  <input type="submit" name="operation" value="<%=FollowUpCtl.OP_SAVE%>">&nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=FollowUpCtl.OP_RESET%>"></td>
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
