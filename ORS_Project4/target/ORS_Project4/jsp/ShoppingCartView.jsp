<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.ShoppingCartCtl"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ShoppingCartBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.SHOPPINGCART_CTL%>" method="post">

			<div align="center">






				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Shopping </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Shopping </font></th>
					</tr>
					<%
						}
					%>
				</h1>


				<%
				HashMap map  = (HashMap) request.getAttribute("productt");

				%>

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
					<td><input type="text" name="name"   onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="20"
					 placeholder="Enter Name"
						size="25" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				
				
				
				
				
				
				 <tr>
				 <th align="left">product<span style="color: red">*</span>:</th>
				 <td>
				 <%=HTMLUtility.getList("product", String.valueOf(bean.getProduct()), map)%></td>
				 <td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("product", request)%></font></td>
				</tr>
				<tr>
				
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				
				
					<th align="left">Date<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="date"
						placeholder="Enter Date" size="25" readonly="readonly"
						id="udatee"
						value="<%=DataUtility.getDateString(bean.getDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("date", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
								 
				
<tr>
					<th align="left">Quantity<span style="color: red">*</span>:
					</th>
					<td><input type="number" name="quantity" 
						placeholder="Enter Quantity " style="width: 195px"
						value="<%=DataUtility.getStringData(bean.getQuantity()).equals("0") ? ""
					: DataUtility.getStringData(bean.getQuantity())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("quantity", request)%></font></td>

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
							name="operation" value="<%=ShoppingCartCtl.OP_UPDATE%>"> &nbsp;
							&nbsp; <input type="submit" name="operation"
							value="<%=ShoppingCartCtl.OP_CANCEL%>"></td>

						<%
							} else {
						%>



						<td colspan="2"><input type="submit" name="operation"
							value="<%=ShoppingCartCtl.OP_SAVE%>">&nbsp; &nbsp; <input
							type="submit" name="operation" value="<%=ShoppingCartCtl.OP_RESET%>"></td>
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
