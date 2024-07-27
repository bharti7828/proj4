<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.CustomerListCtl"%>
<%@page import="com.rays.pro4.Bean.CustomerBean"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.CustomerBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.CUSTOMER_LIST_CTL%>" method="post">

		<center>

			<div align="centre">
				<h1>Customer List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>


				<%
					 List cList= (List) request.getAttribute("city");

					//List blist = (List) request.getAttribute("BankList");
					//List DList = (List) request.getAttribute("dob");
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<CustomerBean> it = list.iterator();

					if (list.size() != 0) {
				%>
				<table width="100%" align="center">
					<tr>
						<th></th>
						<label> First Name</font> :
						</label>
						<input type="text" name="firstName"  onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="30"
						placeholder="Enter  Name"
							value="<%=ServletUtility.getParameter("firstName", request)%>">
						&nbsp;
					</tr>


					<tr>
						<th></th>
						<label>Company</font> :
						</label>
						<input type="text" name="company" placeholder="Enter  Company"
							value="<%=ServletUtility.getParameter("company", request)%>">
						&nbsp;
					</tr>
					
					<tr>
						<th></th>
						<label>PhoneNo</font> :
						</label>
						<input type="text" name="phone" maxlength="10"   placeholder="Enter  phoneNo"
							value="<%=ServletUtility.getParameter("phone", request)%>">
						&nbsp;
					</tr>


					<label>City Role</font> :
					</label>
					<%= HTMLUtility.getList("city", String.valueOf(bean.getId()), cList)%>
					&nbsp;
 
					<tr>
						<input type="submit" name="operation"
							value="<%=CustomerListCtl.OP_SEARCH%>"> &nbsp;
						<input type="submit" name="operation"
							value="<%=CustomerListCtl.OP_RESET%>">
						</td>
					</tr>


					<table border="1" width="100%" align="centre" cellpadding=6px
						cellspacing=".2">
						<tr style="background: skyblue">
							<th><input type="checkbox" id="select_all" name="select">Select
								All</th>

							<th>S.No.</th>
							<th>FirstName</th>
							<th>LastName</th>
							<th>Date Of Birth</th>
							<th>Gender</th>
							<th>City</th>
							<th>Company</th>
							<th>PhoneNO</th>
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
							<td><%=bean.getFirstName()%></td>
							<td><%=bean.getLastName()%></td>
							<td><%=bean.getDob()%></td>
							<td><%=bean.getGender()%></td>
							<td><%=bean.getCity()%></td>
							<td><%=bean.getCompany()%></td>
							<td><%=bean.getPhoneNo() %></td>
							

							<td><a href="CustomerCtl?id=<%=bean.getId()%>">Edit</a></td>
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
										disabled="disabled" value="<%=CustomerListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										} else {
									%>
									<td><input type="submit" name="operation"
										value="<%=CustomerListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										}
									%>

									<td><input type="submit" name="operation"
										value="<%=CustomerListCtl.OP_DELETE%>">&nbsp;&nbsp;</td>
									<td><input type="submit" name="operation"
										value="<%=CustomerListCtl.OP_NEW%>">&nbsp;&nbsp;</td>



									<td align="right"><input type="submit" name="operation"
										value="<%=CustomerListCtl.OP_NEXT%>"
										<%=(list.size() < pageSize) ? "disabled" : ""%>>&nbsp;&nbsp;</td>



									</center>
							</table>
						</tr>



						<%
							}
							if (list.size() == 0) {
						%>
						<td align="centre"><input type="submit" name="operation"
							value="<%=CustomerListCtl.OP_BACK%>"> &nbsp; &nbsp;</td>
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