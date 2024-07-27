<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.FieldCtl"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.FieldBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.FIELD_CTL%>" method="post">

			<div align="center">
			<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Field </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Field </font></th>
					</tr>
					<%
						}
					%>
				</h1>
				<%
					// List dlist= (List) request.getAttribute("dList");

					List flist = (List) request.getAttribute("TypeListt");
				%>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>


			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table>
				<tr>
					<th align="left">Label<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="label" onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="30"
					placeholder="Enter label"
						size="25" value="<%=DataUtility.getStringData(bean.getLabel())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("label", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<%-- <tr>
					<th align="left">Type<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="type" placeholder="Enter type"
						size="25" value="<%=DataUtility.getStringData(bean.getType())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("type", request)%></font></td>

				</tr>
 --%>
 </tr>
				
					<tr>
					<th align="left">TypeRole <span style="color: red">*</span> :
					</th>
					<td><%=HTMLUtility.getList("type", String.valueOf(bean.getType()), flist)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("type", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<%-- <tr>
					<th align="left">Active<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="active" placeholder="Enter Active"
						size="25" value="<%=DataUtility.getStringData(bean.getActive())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("active", request)%></font></td>

				</tr>
 --%>				<!-- <tr>
					<th style="padding: 3px"></th>
				</tr>
 -->
						<th align="left">Active <span style="color: red">*</span> :
				</th>
				<td>
					<%
						HashMap map = new HashMap();
						map.put("No", "No");
						map.put("Yes", "Yes");

						String blist = HTMLUtility.getList("active", String.valueOf(bean.getActive()), map);
					%> <%=blist%>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("active", request)%></font></td>
 
	

				 <tr>
					<th align="left">Description<span style="color: red">*</span>
						:
					</th>
					<td><textarea rows="3" cols="25" name="description" maxlength="100"
							placeholder="Enter Remark" rows="3" cols="25"><%=DataUtility.getStringData(bean.getDescription())%></textarea>
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font></td>

				</tr>
				 
				<tr>
					<th style="padding: 3px"></th>
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
							name="operation" value="<%=FieldCtl.OP_UPDATE%>"> &nbsp;
							&nbsp; <input type="submit" name="operation"
							value="<%=FieldCtl.OP_CANCEL%>"></td>

						<%
							} else {
						%>



						<td colspan="2"><input type="submit" name="operation"
							value="<%=FieldCtl.OP_SAVE%>">&nbsp; &nbsp; <input
							type="submit" name="operation" value="<%=FieldCtl.OP_RESET%>"></td>
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
