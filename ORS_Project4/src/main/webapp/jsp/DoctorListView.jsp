<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.DoctorListCtl"%>
<%@page import="com.rays.pro4.Bean.DoctorBean"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.DoctorBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.DOCTOR_LIST_CTL%>" method="post">

		<center>

			<div align="centre">
				<h1>Doctor List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>


				<%
					// List dlist= (List) request.getAttribute("dList");

					//List blist = (List) request.getAttribute("BankList");
					List eList = (List) request.getAttribute("Expertise");
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<DoctorBean> it = list.iterator();

					if (list.size() != 0) {
				%>
				<table width="100%" align="center">
					<tr>
						<th></th>
						<label>Name</font> :
						</label>
						<input type="text" name="name"  onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="30"
						placeholder="Enter  Name"
							value="<%=ServletUtility.getParameter("name", request)%>">
						&nbsp;
					</tr>


					<tr>
						<th></th>
						<label>MobileNo</font> :
						</label>
						<input type="text" name="mobileNo" maxlength="10"  placeholder="Enter  MobileNo"
							value="<%=ServletUtility.getParameter("mobileNo", request)%>">
						&nbsp;
					</tr>


					<%-- <tr>
						<th></th>
						<label>BankName</font> :
						</label> <input type="text" name="bankName" placeholder="Enter  bankName"
							value="<%=ServletUtility.getParameter("bankName", request)%>">&nbsp;
					</tr> --%>
					<th></th> &emsp;
					<label>ExpertiseRole</font> :
					</label>
					<%=HTMLUtility.getList("expertise", String.valueOf(bean.getExpertise()), eList)%>
					&nbsp;
					<%--  <label>Dob</font> :
					</label> <input type="text" name="dob" placeholder="Enter Dob" id="udatee"
					
						value="<%=ServletUtility.getParameter("dob", request)%>">
					 --%> 
					&nbsp; &emsp;
					<%-- <label>DOB</font> :
					</label>
					<%= HTMLUtility.getList("id", String.valueOf(bean.getId()), DList)%>
					&nbsp;
 --%>
					<tr>
						<input type="submit" name="operation"
							value="<%=DoctorListCtl.OP_SEARCH%>"> &nbsp;
						<input type="submit" name="operation"
							value="<%=DoctorListCtl.OP_RESET%>">
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
							<th>MobileNo</th>
							<th>Expertise</th>
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

							<td><%=bean.getMobileNo()%></td>
							<td><%=bean.getExpertise()%></td>

							<td><a href="DoctorCtl?id=<%=bean.getId()%>">Edit</a></td>
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
										disabled="disabled" value="<%=DoctorListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										} else {
									%>
									<td><input type="submit" name="operation"
										value="<%=DoctorListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										}
									%>

									<td><input type="submit" name="operation"
										value="<%=DoctorListCtl.OP_DELETE%>">&nbsp;&nbsp;</td>
									<td><input type="submit" name="operation"
										value="<%=DoctorListCtl.OP_NEW%>">&nbsp;&nbsp;</td>



									<td align="right"><input type="submit" name="operation"
										value="<%=DoctorListCtl.OP_NEXT%>"
										<%=(list.size() < pageSize) ? "disabled" : ""%>>&nbsp;&nbsp;</td>



									</center>
							</table>
						</tr>



						<%
							}
							if (list.size() == 0) {
						%>
						<td align="centre"><input type="submit" name="operation"
							value="<%=DoctorListCtl.OP_BACK%>"> &nbsp; &nbsp;</td>
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