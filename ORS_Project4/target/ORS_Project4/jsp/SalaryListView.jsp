<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.SalaryListCtl"%>
<%@page import="com.rays.pro4.Bean.SalaryBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>



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
			yearRange : '1980:2006',
		//  mindefaultDate : "01-01-1962"
		});
	});
</script>
</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.SalaryBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.SALARY_LIST_CTL%>" method="post">

		<center>

			<div align="centre">
				<h1>Salary List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>


				<%
					// List dlist= (List) request.getAttribute("dList");

					List slist = (List) request.getAttribute("Status");
					List Slist = (List) request.getAttribute("Employee");
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<SalaryBean> it = list.iterator();

					if (list.size() != 0) {
				%>
				<table width="100%" align="center">
					<%-- <tr>
						<th></th>
						<label>employee</font> :
						</label>
						<input type="text" name="employee" placeholder="Enter  employee"
							value="<%=ServletUtility.getParameter("employee", request)%>">
						&nbsp;
					</tr>
 --%>

					<tr>
						<th></th>
						<label>Amount</font> :
						</label>
						<input type="text" name="amount" placeholder="Enter  amount"
							value="<%=ServletUtility.getParameter("amount", request)%>">
						&nbsp;
					</tr>


					<%-- <tr>
						<th></th>
						<label>SalaryName</font> :
						</label> <input type="text" name="SalaryName" placeholder="Enter  SalaryName"
							value="<%=ServletUtility.getParameter("SalaryName", request)%>">&nbsp;
					</tr> --%>
					<th></th> &emsp;
					<label>StatusRole</font> :
					</label>
					<%=HTMLUtility.getList("Status", String.valueOf(bean.getStatus()), slist)%>
					&nbsp;
					<label>Applieddate</font> :
					</label>
					<input type="text" name="applieddate"
						placeholder="Enter Applieddate" id="udatee" readonly="readonly"
						value="<%=ServletUtility.getParameter("applieddate", request)%>">

					&nbsp; &emsp;
					<label>Employee</font> :
					</label>
					<%=HTMLUtility.getList("employee", String.valueOf(bean.getEmployee()), Slist)%>
					&nbsp;

					<tr>
						<input type="submit" name="operation"
							value="<%=SalaryListCtl.OP_SEARCH%>"> &nbsp;
						<input type="submit" name="operation"
							value="<%=SalaryListCtl.OP_RESET%>">
						</td>
					</tr>


					<table border="1" width="100%" align="centre" cellpadding=6px
						cellspacing=".2">
						<tr style="background: skyblue">
							<th><input type="checkbox" id="select_all" name="select">Select
								All</th>

							<th>S.No.</th>
							<th>Employee</th>
							<th>Amount</th>
							<th>Applieddate</th>
							<th>Status</th>
							<th>Edit</th>
						</tr>
						<%
							while (it.hasNext()) {
									bean = it.next();
						%>
						<tr align="centre">

							<td><input type="checkbox" class="checkbox" name="ids"
								value="<%=bean.getId()%>"></td>

							<td><%=index++%></td>
							<td><%=bean.getEmployee()%></td>
							<td><%=bean.getAmount()%></td>

							<td><%=bean.getApplieddate()%></td>
							<td><%=bean.getStatus()%></td>

							<td><a href="SalaryCtl?id=<%=bean.getId()%>">Edit</a></td>
						</tr>
						<%
							}
						%>
						<tr>
							<table>
								<tr>
									<th></th>
									<%
										if (pageNo == 1) {
									%>
									<td><input type="submit" name="operation"
										disabled="disabled" value="<%=SalaryListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										} else {
									%>
									<td><input type="submit" name="operation"
										value="<%=SalaryListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										}
									%>

									<td><input type="submit" name="operation"
										value="<%=SalaryListCtl.OP_DELETE%>">&nbsp;&nbsp;</td>
									<td><input type="submit" name="operation"
										value="<%=SalaryListCtl.OP_NEW%>">&nbsp;&nbsp;</td>



									<td align="right"><input type="submit" name="operation"
										value="<%=SalaryListCtl.OP_NEXT%>"
										<%=(list.size() < pageSize) ? "disabled" : ""%>>&nbsp;&nbsp;</td>



									</center>
							</table>
						</tr>



						<%
							}
							if (list.size() == 0) {
						%>
						<td align="centre"><input type="submit" name="operation"
							value="<%=SalaryListCtl.OP_BACK%>"> &nbsp; &nbsp;</td>
						<%
							}
						%>
						</tr>
					</table>
					<br>

				</table>

				</table>
	</form>
	</br>
	</br>
	</br>
	</br>

	</center>

	<%@include file="Footer.jsp"%>



</body>
</html>