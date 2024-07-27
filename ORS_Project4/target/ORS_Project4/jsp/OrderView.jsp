<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.OrderCtl"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.OrderBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.ORDER_CTL%>" method="post">

			<%
				List plist = (List) request.getAttribute("productt");
			%>


			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Order </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Order </font></th>
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
					<th align="left">Quantity<span style="color: red">*</span> :
					</th>
					<td><input type="number" name="quantity" maxlength="10"
						placeholder="Enter Quantity	" style="width: 195px"
						value="<%=(DataUtility.getStringData(bean.getQuantity()).equals("0") ? ""
					: DataUtility.getStringData(bean.getQuantity()))%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("quantity", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Product <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("product", String.valueOf(bean.getProduct()), plist)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("product", request)%></font></td>
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
					<th align="left">Amount<span style="color: red">*</span> :
					</th>
					<td><input type="number" name="amount" maxlength="10"
						placeholder="Enter Amount" style="width: 195px"
						value="<%=(DataUtility.getStringData(bean.getAmount()).equals("0") ? ""
					: DataUtility.getStringData(bean.getAmount()))%>">
					</td>

					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("amount", request)%></font></td>
				</tr>
				<th style="padding: 3px"></th>
				</tr>


				<%-- <tr>
					<th align="left">OrderName  <span style="color: red">*</span> :
					</th>
					<td><input type="text"  name="OrderName"
						maxlength="10" placeholder="Enter Order Name" size="25"
						value="<%=DataUtility.getStringData(bean.getOrderName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("OrderName", request)%></font></td>
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
						name="operation" value="<%=OrderCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=OrderCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>
						<td colspan="2"><input type="submit" name="operation"
							value="<%=OrderCtl.OP_SAVE%>">&nbsp; &nbsp; <input
							type="submit" name="operation" value="<%=OrderCtl.OP_RESET%>"></td>
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
