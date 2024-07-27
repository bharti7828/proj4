<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.JobListCtl"%>
<%@page import="com.rays.pro4.Bean.JobBean"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.JobBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.JOB_LIST_CTL%>" method="post">

		<center>

			<div align="centre">
				<h1>Job List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>


				<%
				HashMap map  = (HashMap) request.getAttribute("status");

				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<JobBean> it = list.iterator();

					if (list.size() != 0) {
				%>
				<table width="100%" align="center">
					<tr>
						<th></th>
						<label>Title</font> :
						</label>
						<input type="text" name="title"  onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="30"
						
						placeholder="Enter  Title"
							value="<%=ServletUtility.getParameter("title", request)%>">
						&nbsp;
					</tr>


					<tr>
						<th></th>
						<label>Date Of Opening</font> :
						</label>
						<input type="text" name="dateOfOpening" id="udatee" readonly="readonly" placeholder="Enter  Date Of Opening"
							value="<%=ServletUtility.getParameter("dateOfOpening", request)%>">
						&nbsp;
					</tr>
					
					<tr>
						<th></th>
						<label>Experience</font> :
						</label>
						<input type="text" name="experience"  placeholder="Enter  experience"
							value="<%=ServletUtility.getParameter("experience", request)%>">
						&nbsp;
					</tr>
              <tr>
              <th></th>
					<label>status</font> :
					</label>
					<%= HTMLUtility.getList("status", String.valueOf(bean.getStatus()), map)%>
					
					&nbsp;
					</tr>

					<tr>
						<input type="submit" name="operation"
							value="<%=JobListCtl.OP_SEARCH%>"> &nbsp;
						<input type="submit" name="operation"
							value="<%=JobListCtl.OP_RESET%>">
						</td>
					</tr>


					<table border="1" width="100%" align="centre" cellpadding=6px
						cellspacing=".2">
						<tr style="background: skyblue">
							<th><input type="checkbox" id="select_all" name="select">Select
								All</th>

							<th>S.No.</th>
							<th>Title</th>
							<th>Date Of Opening</th>
							<th>Experience</th>
							<th> Status</th>
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
							<td><%=bean.getTitle()%></td>
							<td><%=bean.getDateOfOpening()%></td>

							<td><%=bean.getExperience()%></td>
							<td><%=bean.getStatus()%></td>

							<td><a href="JobCtl?id=<%=bean.getId()%>">Edit</a></td>
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
										disabled="disabled" value="<%=JobListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										} else {
									%>
									<td><input type="submit" name="operation"
										value="<%=JobListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										}
									%>

									<td><input type="submit" name="operation"
										value="<%=JobListCtl.OP_DELETE%>">&nbsp;&nbsp;</td>
									<td><input type="submit" name="operation"
										value="<%=JobListCtl.OP_NEW%>">&nbsp;&nbsp;</td>



									<td align="right"><input type="submit" name="operation"
										value="<%=JobListCtl.OP_NEXT%>"
										<%=(list.size() < pageSize) ? "disabled" : ""%>>&nbsp;&nbsp;</td>



									</center>
							</table>
						</tr>



						<%
							}
							if (list.size() == 0) {
						%>
						<td align="centre"><input type="submit" name="operation"
							value="<%=JobListCtl.OP_BACK%>"> &nbsp; &nbsp;</td>
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