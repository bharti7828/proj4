<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.FollowUpListCtl"%>
<%@page import="com.rays.pro4.Bean.FollowUpBean"%>
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
</head>
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
			yearRange : '1980:2002',
		//dateFormat:'yy-mm-dd'
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.FollowUpBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.FOLLOWUP_LIST_CTL%>" method="post">

		<center>

			<div align="centre">
				<h1>FollowUp List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

				<%
					List plist = (List) request.getAttribute("patient");
					List dlist = (List) request.getAttribute("doctor"); 
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<FollowUpBean> it = list.iterator();

					if (list.size() != 0) {
				%>
				<!-- <table width="100%" align="center"> -->
					<tr>
					<th></th>
					 
						<%-- <td align="center"><label>Employee</font> :
					</label> <input type="text" name="employee" placeholder="Enter  Employee"
						value="<%=ServletUtility.getParameter("employee", request)%>">
						  --%>
					<label>Patient</font> :
						</label>
						<%=HTMLUtility.getList("patient", String.valueOf(bean.getPatient()), plist)%>
						&emsp;&nbsp;
						
					
							
					<label>Doctor</font> :
						</label>
						<%=HTMLUtility.getList("doctor", String.valueOf(bean.getDoctor()), dlist)%>
						&emsp;&nbsp; 
						
						<%-- <td align="center"><label>Doctor</font> :
					</label> <input type="text" name="doctor" placeholder="Enter doctor "
						value="<%=ServletUtility.getParameter("doctor", request)%>">
						 --%>
						
						<td align="center"><label>VisitDate</font> :
						</label> <input type="text" name="visitDate" placeholder="Enter  visitDate"
							id="udatee"
							value="<%=ServletUtility.getParameter("visitDate", request)%>">
							&nbsp;

							
						<td align="center"><label>Fees</font> :
					</label> <input type="number" name="fees" placeholder="Enter fees "
						value="<%=ServletUtility.getParameter("fees", request)%>">
						
						




							<input type="submit" name="operation"
							value="<%=FollowUpListCtl.OP_SEARCH%>"> &nbsp; <input
							type="submit" name="operation"
							value="<%=FollowUpListCtl.OP_RESET%>"></td>
					</tr>


					<table border="1" width="100%" align="centre" cellpadding=6px
						cellspacing=".2">
						<tr style="background: skyblue">
							<th><input type="checkbox" id="select_all" name="select">Select
								All</th>

							<th>S.No.</th>
							<th>Patient</th>
							<th>Doctor</th>
							<th>VisitDate</th>
							<th>Fees</th>
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
							<td><%=bean.getPatient()%></td>
							<td><%=bean.getDoctor()%></td>
							<td><%=bean.getVisitDate()%></td>
							<td><%=bean.getFees()%></td>

							<td><a href="FollowUpCtl?id=<%=bean.getId()%>">Edit</a></td>
						</tr>
						<%
							}
						%>

						<table width="100%">

							<tr>
								<th></th>
								<%
									if (pageNo == 1) {
								%>
								<td><input type="submit" name="operation"
									disabled="disabled" value="<%=FollowUpListCtl.OP_PREVIOUS%>"></td>
								<%
									} else {
								%>
								<td><input type="submit" name="operation"
									value="<%=FollowUpListCtl.OP_PREVIOUS%>"></td>
								<%
									}
								%>


								<td><input type="submit" name="operation"
									value="<%=FollowUpListCtl.OP_DELETE%>"></td>

								<td><input type="submit" name="operation"
									value="<%=FollowUpListCtl.OP_NEW%>"></td>

								<td align="right"><input type="submit" name="operation"
									value="<%=FollowUpListCtl.OP_NEXT%>"
									<%=(list.size() < pageSize) ? "disabled" : ""%>></td>




							</tr>
						</table>


						<%
							}
							if (list.size() == 0) {
						%>
						<td align="centre"><input type="submit" name="operation"
							value="<%=FollowUpListCtl.OP_BACK%>"> &nbsp; &nbsp;</td>
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