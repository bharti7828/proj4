<%@page import="com.rays.pro4.controller.ProductCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
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

<body>

	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ProductBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.PRODUCT_CTL%>" method="post">

			<div align="center">
				<h1>
					<%
						if (bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update product </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Product </font></th>
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
					<td><input type="text" name="name" placeholder="Enter Name"onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="20"
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
				<tr>
					<th align="left">Discription<span style="color: red">*</span>
						:
					</th>
					<td><textarea rows="3" cols="25" name="discription" maxlength="100"
							placeholder="Enter discription" rows="3" cols="25"><%=DataUtility.getStringData(bean.getDiscription())%></textarea>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("discription", request)%></font></td>

				</tr>
				<th style="padding: 3px"></th>
				<tr>
					<th align="left">price<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="price" placeholder="Enter Price"
						size="25" value="<%=DataUtility.getStringData(bean.getPrice())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("price", request)%></font></td>
				</tr>
				</tr>
				<th style="padding: 3px"></th>
				<tr>
				<tr>
					<%
						if (bean.getId() > 0) {
					%>
					
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=ProductCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=ProductCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>



					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="save">&nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=ProductCtl.OP_RESET%>"></td>
					</center>
					<%
						}
					%>
				</tr>

			</table>
	</center>

	<%@ include file="Footer.jsp"%>






</body>
</html>
