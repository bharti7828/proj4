<%@page import="com.rays.pro4.controller.TransactionCtl"%>
<%@page import="com.rays.pro4.controller.ProductCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<script src="<%=ORSView.APP_CONTEXT%>/js/ValidateToInput.js"></script>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Transaction Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
			yearRange : '1900:2024',
		//dateFormat:'yy-mm-dd'
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
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.TransactionBean"
		scope="request"></jsp:useBean>
		
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.TRANSACTION_CTL%>" method="post">

   <div align="center">

			  <%HashMap map1 =(HashMap) request.getAttribute("map1");
              HashMap map =(HashMap) request.getAttribute("map");
              %> 
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Transaction </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Transaction</font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>">

			<table>
			<tr>


					<th align="left">Description: <span style="color: red">*</span>

					</th>
					<td><input type="text" name="pooja" placeholder="Enter Description"
						onkeypress="return ValidateInput(event)" size="25"
						value="<%=DataUtility.getStringData(bean.getDescription())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("pooja", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">TransactionDate<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="dob" placeholder="Enter Date"
						size="25" readonly="readonly" id="udatee"
						value="<%=DataUtility.getDateString(bean.getDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				
			

			<tr>
					<th align="left">Type <span style="color: red">*</span> :
					</th>
					<td>
						<%
							

					String hlist = HTMLUtility.getList("type", String.valueOf(bean.getType()), map);
						%>
						
						 <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("type", request)%></font></td>
				</tr>
				
				<tr>
		       <th align="left">AccountId <span style="color: red">*</span> :
					</th>
					<td>
						<%
							

		        String hlist1 = HTMLUtility.getList("accountId", String.valueOf(bean.getAccountId()), map1);
						%> <%=hlist1%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("accountId", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				

				
				
								<tr>
					<th style="padding: 3px"></th>
				</tr>
				<%
					if (bean.getId() > 0) {
				%>

				&nbsp;
				<td align="right" colspan="2">&nbsp; &emsp; <input
					type="submit" name="operation" value="<%=TransactionCtl.OP_UPDATE%>">
					&nbsp; &nbsp; <input type="submit" name="operation"
					value="<%=TransactionCtl.OP_CANCEL%>"></td>

				<%
					} else {
				%>

				&nbsp;&nbsp;
				<td align="right" scolspan="2">&nbsp; &emsp; <input
					type="submit" name="operation" value="<%=TransactionCtl.OP_SAVE%>">
					&nbsp; &nbsp; <input type="submit" name="operation"
					value="<%=TransactionCtl.OP_RESET%>"></td>

				<%
					}
				%>
				</tr>
			</table>
			
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>
