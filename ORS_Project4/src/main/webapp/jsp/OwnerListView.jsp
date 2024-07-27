<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.OwnerListCtl"%>
<%@page import="com.rays.pro4.Bean.OwnerBean"%>
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

<script>
	function validateNumericInput(inputField) {
		// Get the value entered by the user
		var inputValue = inputField.value;

		// Regular expression to check if the input is numeric
		var numericPattern = /^\d*$/;

		// Test the input value against the numeric pattern
		if (!numericPattern.test(inputValue)) {
			// If input is not numeric, clear the field
			inputField.value = inputValue.replace(/[^\d]/g, ''); // Remove non-numeric characters
		}
	}
</script>

</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.OwnerBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.OWNER_LIST_CTL%>" method="post">

		<center>

			<div align="centre">
				<h1>Owner List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>


				<%
				HashMap map  = (HashMap) request.getAttribute("vehicleID");
				
					List Dlist = (List) request.getAttribute("dob");
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<OwnerBean> it = list.iterator();

					if (list.size() != 0) {
				%>
				<table width="100%" align="center">
					<tr>
						<th></th>
						<label>Name</font> :
						</label>
						<input type="text" name="name"  onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="15"
						placeholder="Enter  Name"
							value="<%=ServletUtility.getParameter("name", request)%>">
						&nbsp;
					</tr>


					<tr>
						<th></th>
						<label>InsuranceAmout</font> :
						</label>
						<input type="text" name="insuranceAmout"  placeholder="Enter  insuranceAmout"  oninput="validateNumericInput(this)" maxlength="17"
							value="<%=ServletUtility.getParameter("insuranceAmout", request)%>">
						&nbsp;
					</tr>
					


					<th></th> &emsp;
					<th></th>
					<label>VehicleId</font> :
					</label>
					<%= HTMLUtility.getList("vehicleID", String.valueOf(bean.getVehicleID()), map)%>
					
					&nbsp;
					
					&nbsp; &emsp;
					<label>DOB</font> :
					</label>
					<%= HTMLUtility.getList("id", String.valueOf(bean.getId()), Dlist)%>
					&nbsp;

					<tr>
						<input type="submit" name="operation"
							value="<%=OwnerListCtl.OP_SEARCH%>"> &nbsp;
						<input type="submit" name="operation"
							value="<%=OwnerListCtl.OP_RESET%>">
						</td>
					</tr>


					<table border="1" width="100%" align="centre" cellpadding=6px
						cellspacing=".2">
						<tr style="background: skyblue">
							<th><input type="checkbox" id="select_all" name="select">Select
								All</th>

							<th>S.No.</th>
							<th>Name</th>
							<th>Date Of Birth</th>
							<th>VehicleId</th>
							<th>InsuranceAmout</th>
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
							<td><%=bean.getName()%></td>
							<td><%=bean.getDob()%></td>

							<td><%=bean.getVehicleID()%></td>
							<td><%=bean.getInsuranceAmout()%></td>

							<td><a href="OwnerCtl?id=<%=bean.getId()%>">Edit</a></td>
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
										disabled="disabled" value="<%=OwnerListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										} else {
									%>
									<td><input type="submit" name="operation"
										value="<%=OwnerListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										}
									%>

									<td><input type="submit" name="operation"
										value="<%=OwnerListCtl.OP_DELETE%>">&nbsp;&nbsp;</td>
									<td><input type="submit" name="operation"
										value="<%=OwnerListCtl.OP_NEW%>">&nbsp;&nbsp;</td>



									<td align="right"><input type="submit" name="operation"
										value="<%=OwnerListCtl.OP_NEXT%>"
										<%=(list.size() < pageSize) ? "disabled" : ""%>>&nbsp;&nbsp;</td>



									</center>
							</table>
						</tr>



						<%
							}
							if (list.size() == 0) {
						%>
						<td align="centre"><input type="submit" name="operation"
							value="<%=OwnerListCtl.OP_BACK%>"> &nbsp; &nbsp;</td>
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