<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.WishCtl"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.WishBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.WISH_CTL%>" method="post">

			<%
					// List dlist= (List) request.getAttribute("dList");

					//List blist = (List) request.getAttribute("BankList");
					List pplist = (List) request.getAttribute("productt");
				%>



			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Wish </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Wish </font></th>
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
					<th align="left"> product Role <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("product", String.valueOf(bean.getProduct()), pplist)%></td>
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
					<td><input type="text" name="date"
						placeholder="Enter Date Of Birth" size="25" readonly="readonly"
						id="udatee" value="<%=DataUtility.getDateString(bean.getDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("date", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">UserName  <span style="color: red">*</span> :
					</th>
					<td><input type="text"  name="userName"
						maxlength="20" placeholder="Enter  UserName" onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="20" size="25"
						value="<%=DataUtility.getStringData(bean.getUserName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("userName", request)%></font></td>
				</tr>			
				<th style="padding: 3px"></th>
				</tr>


				<%-- <tr>
					<th align="left">WishName  <span style="color: red">*</span> :
					</th>
					<td><input type="text"  name="WishName"
						maxlength="10" placeholder="Enter Wish Name" size="25"
						value="<%=DataUtility.getStringData(bean.getWishName())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("WishName", request)%></font></td>
				</tr>				 --%>
				
				
				
				<tr>
					<th align="left">Remark<span style="color: red">*</span>
						:
					</th>
					<td><textarea rows="3" cols="25" name="remark" maxlength="100"
							placeholder="Enter Remark" rows="3" cols="25"><%=DataUtility.getStringData(bean.getRemark())%></textarea>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("remark", request)%></font></td>

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
						name="operation" value="<%=WishCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=WishCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>
						<td colspan="2"><input type="submit" name="operation"
							value="<%=WishCtl.OP_SAVE%>">&nbsp; &nbsp; <input
							type="submit" name="operation" value="<%=WishCtl.OP_RESET%>"></td>
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
