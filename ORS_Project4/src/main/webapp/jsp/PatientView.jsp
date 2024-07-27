<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.PatientCtl"%>
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
<script type="text/javascript">
	function validatephone(event) {
		const input = event.target;
		input.value = input.value.replace(/[^0-9.]/g, '')
		if (input.value.length > 0 && input.value[0] <= '5') {
			input.value = '';
		}
	}
</script>


<title>Insert title here</title>
</head>

<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.PatientBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PATIENT_CTL%>" method="post">
		
		<%
				HashMap map  = (HashMap) request.getAttribute("deceasee");

				%>
		

			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Patient </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Patient </font></th>
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
					<td><input type="text" name="name"  onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="30"
					placeholder="Enter Name"
						size="25" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Date Of Birth <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="dob"
						placeholder="Enter Date Of Birth" size="25" readonly="readonly"
						id="udatee" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Mobile No <span style="color: red">*</span> :
					</th>
					<td><input type="text"  name="mobileNo" oninput="validatephone(event)"
						maxlength="10" placeholder="Enter Mobile No" size="25"
						value="<%=DataUtility.getStringData(bean.getMobileNo()).equals("0") ? ""
					: DataUtility.getStringData(bean.getMobileNo())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>
								<tr>
					<th style="padding: 3px"></th>
				</tr>
                 <tr>
				 <th align="left">Decease<span style="color: red">*</span>:</th>
				 <td>
				 <%= HTMLUtility.getList("decease", String.valueOf(bean.getDecease()), map)%></td>
				 <td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("decease", request)%></font></td>
				</tr>				 
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<table align="center">
					<tr>
						<%
							if (bean.getId() > 0) {
						%>
						<td colspan="2">&nbsp; &emsp; <input type="submit"
							name="operation" value="<%=PatientCtl.OP_UPDATE%>"> &nbsp;
							&nbsp; <input type="submit" name="operation"
							value="<%=PatientCtl.OP_CANCEL%>"></td>

						<%
							} else {
						%>



						<td colspan="2"><input type="submit" name="operation"
							value="<%=PatientCtl.OP_SAVE%>">&nbsp; &nbsp; <input
							type="submit" name="operation" value="<%=PatientCtl.OP_RESET%>"></td>
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
