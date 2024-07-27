<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.SalaryCtl"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.SalaryBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.SALARY_CTL%>" method="post">

			<div align="center">

				<%
					// List dlist= (List) request.getAttribute("dList");

					List slist = (List) request.getAttribute("Statuss");
					//List Slist = (List) request.getAttribute("Employee");
				%>

				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Salary </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Salary </font></th>
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
					<th align="left">Employee<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="employee"
						placeholder="Enter employee" size="25"
						value="<%=DataUtility.getStringData(bean.getEmployee())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("employee", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Amount<span style="color: red">*</span>:
					</th>
					<td><input type="text" name="amount"
						placeholder="Enter amount" size="25"
						value="<%=DataUtility.getStringData(bean.getAmount()).equals("0") ? ""
					: DataUtility.getStringData(bean.getAmount())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("amount", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Applieddate<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="applieddate"
						placeholder="Enter applieddate" size="25" readonly="readonly"
						id="udatee"
						value="<%=DataUtility.getDateString(bean.getApplieddate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("applieddate", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>




				<tr>
					<th align="left">Status Role <span style="color: red">*</span>
						:
					</th>
					<td><%=HTMLUtility.getList("Status", String.valueOf(bean.getStatus()), slist)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("Status", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<%-- <th align="left">SalaryName <span style="color: red">*</span> :
				</th>
				<td>
					<%
						HashMap map = new HashMap();
						map.put("SBI", "SBI");
						map.put("BOB", "BOB");
						map.put("CANARA", "CANARA");
						map.put("KOTAK", "KOTAK");
						map.put("ICICI", "ICICI");

						String blist = HTMLUtility.getList("SalaryName", String.valueOf(bean.getId()), map);
					%> <%=blist%>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("SalaryName", request)%></font></td>
 --%>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<table align="center">
					<tr>
						<%
							if (bean.getId() > 0) {
						%>
						<td colspan="2">&nbsp; &emsp; <input type="submit"
							name="operation" value="<%=SalaryCtl.OP_UPDATE%>"> &nbsp;
							&nbsp; <input type="submit" name="operation"
							value="<%=SalaryCtl.OP_CANCEL%>"></td>

						<%
							} else {
						%>



						<td colspan="2"><input type="submit" name="operation"
							value="<%=SalaryCtl.OP_SAVE%>">&nbsp; &nbsp; <input
							type="submit" name="operation" value="<%=SalaryCtl.OP_RESET%>"></td>
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
