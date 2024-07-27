<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.ClientCtl"%>
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
	function validatephoneNo(event) {
		const input = event.target;
		input.value = input.value.replace(/[^0-9.]/g, '')
		if (input.value.length > 0 && input.value[0]<= '5') {
			input.value = '';
		}
	}
</script>
	

<title>Insert title here</title>
</head>

<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ClientBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.CLIENT_CTL%>" method="post">
		
		<%
				HashMap map = (HashMap) request.getAttribute("priorityy");
			%>

			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Client </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Client </font></th>
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
					<td><input type="text" name="name" placeholder="Enter Name" "onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="20"
						size="25" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
         <tr>
					<th align="left">Address<span style="color: red">*</span>:
					</th>
					<td><textarea rows="3" cols="25" name="address" maxlength="100"
							placeholder="Enter Address" rows="3" cols="25"><%=DataUtility.getStringData(bean.getAddress())%></textarea>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("address", request)%></font></td>

				</tr>
				
					<tr>		
					<th style="padding: 3px"></th>
				</tr>
				
								
				<tr>
					<th align="left">PhoneNo<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="phoneNo" maxlength="10"
						placeholder="Enter  PhoneNo" oninput=" validatephoneNo(event)" name="PhoneNo" maxlength="10" style="width: 195px"
						value="<%=(DataUtility.getStringData(bean.getPhoneNo()).equals("0") ? ""
					: DataUtility.getStringData(bean.getPhoneNo()))%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>

				</tr>
				
					
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				
              
				<tr>
					<th style="padding: 3px"></th>
				</tr> 
					<tr>
					<th align="left">Priority<span style="color: red">*</span>
						:
					</th>
					<td><%=HTMLUtility.getList("priority", String.valueOf(bean.getPriority()), map)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("priority", request)%></font></td>
				</tr>

				<table align="center">
				<tr>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ClientCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ClientCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

                       

					  <td colspan="2">  
					  <input type="submit" name="operation" value="<%=ClientCtl.OP_SAVE%>">&nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ClientCtl.OP_RESET%>"></td>
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
