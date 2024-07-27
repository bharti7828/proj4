<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.CustomerCtl"%>
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

<script type="text/javascript">
	function validatephone(event) {
		const input = event.target;
		input.value = input.value.replace(/[^0-9.]/g, '')
		if (input.value.length > 0 && input.value[0] <= '5') {
			input.value = '';
		}
	}
</script>



<title>Insert title here</title>
</head>

<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.CustomerBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.CUSTOMER_CTL%>" method="post">

			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Customer </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Customer </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>


<%
					 List cList= (List) request.getAttribute("cityy");

					//List blist = (List) request.getAttribute("BankList");
					//List DList = (List) request.getAttribute("dob");
				%>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table>
				<tr>
					<th align="left">FirstName<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="firstName" onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="30" placeholder="Enter First Name"
						size="25" value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				
				<tr>
					<th align="left">LastName<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="lastName" onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="30"placeholder="Enter Last Name"
						size="25" value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				
				
				<tr>
					<th align="left">Date Of Birth <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="dob"
						placeholder="Enter Date Of Birth" size="25" readonly="readonly"
						id="udatee" value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
	<tr>
					<th style="padding: 3px"></th>
				</tr>

				<%--  <tr>
					<th align="left">Gender<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="gender" placeholder="Enter Gender"
						size="25" value="<%=DataUtility.getStringData(bean.getGender())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font></td>

				</tr>
 --%>				
				 <th align="left">Gender <span style="color: red">*</span> :
				</th>
				<td>
					<%
						HashMap map = new HashMap();
						map.put("Male", "Male");
						map.put("Female", "Female");

						String blist = HTMLUtility.getList("gender", String.valueOf(bean.getGender()), map);
					%> <%=blist%>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>

				
				<tr>
					<th style="padding: 3px"></th>
				</tr>


				<tr>
					<th align="left">City Role <span style="color: red">*</span>
						:
					</th>
					<td><%=HTMLUtility.getList("city", String.valueOf(bean.getCity()), cList)%></td>
					<td style="position: fixed"><font style="position: fixed"
						color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
				</tr>		 
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				
				
				<tr>
					<th align="left">Company  <span style="color: red">*</span> :
					</th>
					<td><input type="text"  name="company"
						maxlength="10" placeholder="Enter Company Name" size="25"
						value="<%=DataUtility.getStringData(bean.getCompany())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("company", request)%></font></td>
				</tr>	
				<tr>
					<th style="padding: 3px"></th>
				</tr>
				
				<tr>
					<th align="left">PhoneNo<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="phoneNo" maxlength="10"
						placeholder="Enter  PhoneNo" oninput="validatephone(event)"
						name="phoneNo" maxlength="10" size="25"
						value="<%=(DataUtility.getStringData(bean.getPhoneNo()).equals("0") ? ""
					: DataUtility.getStringData(bean.getPhoneNo()))%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>

				</tr>	
				<tr>
					<th style="padding: 3px"></th>
				</tr>		 
			

				
				
				<table align="center">
					
					
					
					<tr>
					<th style="padding: 3px"></th>
				</tr>
				<table align="center">
					<tr>
						<%
							if (bean.getId() > 0) {
						%>
						<td colspan="2">&nbsp; &emsp; <input type="submit"
							name="operation" value="<%=CustomerCtl.OP_UPDATE%>"> &nbsp;
							&nbsp; <input type="submit" name="operation"
							value="<%=CustomerCtl.OP_CANCEL%>"></td>

						<%
							} else {
						%>



						<td colspan="2"><input type="submit" name="operation"
							value="<%=CustomerCtl.OP_SAVE%>">&nbsp; &nbsp; <input
							type="submit" name="operation" value="<%=CustomerCtl.OP_RESET%>"></td>
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
