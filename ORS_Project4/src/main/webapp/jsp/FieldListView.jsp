<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.FieldListCtl"%>
<%@page import="com.rays.pro4.Bean.FieldBean"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.FieldBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.FIELD_LIST_CTL%>" method="post">

		<center>

			<div align="centre">
				<h1>Field List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>


				<%
					// List dlist= (List) request.getAttribute("dList");

					List flist = (List) request.getAttribute("TypeList");
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<FieldBean> it = list.iterator();

					if (list.size() != 0) {
				%>
				<table width="100%" align="center">
					<tr>
						<th></th>
						<label>Label</font> :
						</label>
						<input type="text" name="label" onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="30"
						placeholder="Enter  Name"
							value="<%=ServletUtility.getParameter("label", request)%>">
						&nbsp;
					</tr>




					&nbsp; &emsp;
					<label>Type Role</font> :
					</label>
					<%= HTMLUtility.getList("type", String.valueOf(bean.getType()), flist)%>
					&nbsp;

					<tr>
						<input type="submit" name="operation"
							value="<%=FieldListCtl.OP_SEARCH%>"> &nbsp;
						<input type="submit" name="operation"
							value="<%=FieldListCtl.OP_RESET%>">
						</td>
					</tr>


					<table border="1" width="100%" align="centre" cellpadding=6px
						cellspacing=".2">
						<tr style="background: skyblue">
							<th><input type="checkbox" id="select_all" name="select">Select
								All</th>

							<th>S.No.</th>
							<th>Label</th>
							<th>Type</th>
							<th>Active</th>
							<th>Description</th>
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
							<td><%=bean.getLabel()%></td>
							<td><%=bean.getType()%></td>

							<td><%=bean.getActive()%></td>
							<td><%=bean.getDescription()%></td>

							<td><a href="FieldCtl?id=<%=bean.getId()%>">Edit</a></td>
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
										disabled="disabled" value="<%=FieldListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										} else {
									%>
									<td><input type="submit" name="operation"
										value="<%=FieldListCtl.OP_PREVIOUS%>">&nbsp;&nbsp;</td>
									<%
										}
									%>

									<td><input type="submit" name="operation"
										value="<%=FieldListCtl.OP_DELETE%>">&nbsp;&nbsp;</td>
									<td><input type="submit" name="operation"
										value="<%=FieldListCtl.OP_NEW%>">&nbsp;&nbsp;</td>



									<td align="right"><input type="submit" name="operation"
										value="<%=FieldListCtl.OP_NEXT%>"
										<%=(list.size() < pageSize) ? "disabled" : ""%>>&nbsp;&nbsp;</td>



									</center>
							</table>
						</tr>



						<%
							}
							if (list.size() == 0) {
						%>
						<td align="centre"><input type="submit" name="operation"
							value="<%=FieldListCtl.OP_BACK%>"> &nbsp; &nbsp;</td>
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