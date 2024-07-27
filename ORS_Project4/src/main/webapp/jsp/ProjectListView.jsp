<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.ProjectListCtl"%>
<%@page import="com.rays.pro4.Bean.ProjectBean"%>
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
		$("#udate").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2004',
		//  mindefaultDate : "01-01-1962"
		});
	});
</script>
</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.ProjectBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.PROJECT_LIST_CTL%>" method="post">

		<center>

			<div align="centre">
				<h1>Project List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>


<%
			// List dlist= (List) request.getAttribute("dList");
			
				List clist = (List) request.getAttribute("category");
				
			%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<ProjectBean> it = list.iterator();

					if (list.size() != 0) {
				%>
				<table width="100%" align="center">
					<tr>
						<th></th>
						<label>Name</font> :
						</label> <input type="text" name="name" placeholder="Enter  Name"
							value="<%=ServletUtility.getParameter("name", request)%>">
							&nbsp;
					</tr>
					
		
					
                     &emsp; <label>Category</font> :
					  </label> <%=HTMLUtility.getList("category", String.valueOf(bean.getCategory()),clist)%>
					 &nbsp;
					 
					 
					 <td align="center"><label>OpenDate</font> :
					</label> <input type="text" name="openDate" placeholder="Enter  openDate" id="udatee"
						value="<%=ServletUtility.getParameter("openDate", request)%>">
						
					 <tr>
						<th></th>
						<label>Version</font> :
						</label> <input type="text" name="version" placeholder="Enter  version"
							value="<%=ServletUtility.getParameter("version", request)%>">
							&nbsp;
					</tr>
                   
		         
					<tr>
						<input type="submit" name="operation"
							value="<%=ProjectListCtl.OP_SEARCH%>"> &nbsp;
						<input type="submit" name="operation"
							value="<%=ProjectListCtl.OP_RESET%>">
						</td>
					</tr>
					

					<table border="1" width="100%" align="centre" cellpadding=6px
						cellspacing=".2">
						<tr style="background: skyblue">
							<th><input type="checkbox" id="select_all" name="select">Select
								All</th>

							<th>S.No.</th>
							<th>Name</th>
							<th>Category</th>
							<th>OpenDate</th>
							<th>Version</th>
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
						    <td><%=bean.getCategory()%></td>
							<td><%=bean.getOpenDate()%></td>
							<td><%=bean.getVersion()%></td>

							<td><a href="ProjectCtl?id=<%=bean.getId()%>">Edit</a></td>
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
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=ProjectListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=ProjectListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>


					<td><input type="submit" name="operation"
						value="<%=ProjectListCtl.OP_DELETE%>"></td>
						
					<td><input type="submit" name="operation"
						value="<%=ProjectListCtl.OP_NEW%>"></td>

					<td align="right"><input type="submit" name="operation"
						value="<%=ProjectListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize) ? "disabled" : ""%>></td>




				</tr>
			</table>
					

						<%
							}
							if (list.size() == 0) {
						%>
						<td align="centre"><input type="submit" name="operation"
							value="<%=ProjectListCtl.OP_BACK%>"> &nbsp; &nbsp;</td>
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