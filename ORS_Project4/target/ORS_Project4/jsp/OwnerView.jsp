<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.OwnerCtl"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.OwnerBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.OWNER_CTL%>" method="post">
		
		<%
					// List dlist= (List) request.getAttribute("dList");

					//List blist = (List) request.getAttribute("BankList");
					//List eList = (List) request.getAttribute("Expertisee");
				%>
		

			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Owner </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Owner </font></th>
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
					<th align="left">Name<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="name"  onkeydown="return /[a-zA-Z]/i.test(event.key)" maxlength="30"
					 placeholder="Enter Name"
						size="25" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>

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

				<tr>
					<th align="left">vehicleID <span style="color: red">*</span> :
					</th>
					<td><input type="text"  name="vehicleID"
						maxlength="10" placeholder="Enter vehicleID" size="25"
						value="<%=DataUtility.getStringData(bean.getVehicleID())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("vehicleID", request)%></font></td>
				</tr>
								<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">InsuranceAmout<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="insuranceAmout" 
						placeholder="Enter  InsuranceAmout"   style="width: 195px"
						value="<%=(DataUtility.getStringData(bean.getInsuranceAmout()).equals("0") ? ""
					: DataUtility.getStringData(bean.getInsuranceAmout()))%>">
					</td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("insuranceAmout", request)%></font></td>

				</tr>
				<%--  <tr>
					<th align="left">Decease  <span style="color: red">*</span> :
					</th>
					<td><input type="text"  name="decease"
						maxlength="10" placeholder="Enter Decease" size="25"
						value="<%=DataUtility.getStringData(bean.getDecease())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("decease", request)%></font></td>
				</tr> --%>		
				
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<%-- <th align="left">BankName <span style="color: red">*</span> :
				</th>
				<td>
					<%
						HashMap map = new HashMap();
						map.put("SBI", "SBI");
						map.put("BOB", "BOB");
						map.put("CANARA", "CANARA");
						map.put("KOTAK", "KOTAK");
						map.put("ICICI", "ICICI");

						String blist = HTMLUtility.getList("bankName", String.valueOf(bean.getId()), map);
					%> <%=blist%>
				<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("bankName", request)%></font></td>
 --%>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<table align="center">
					<tr>
						<%
							if (bean.getId() > 0) {
						%>
						<td colspan="2">&nbsp; &emsp; <input type="submit"
							name="operation" value="<%=OwnerCtl.OP_UPDATE%>"> &nbsp;
							&nbsp; <input type="submit" name="operation"
							value="<%=OwnerCtl.OP_CANCEL%>"></td>

						<%
							} else {
						%>



						<td colspan="2"><input type="submit" name="operation"
							value="<%=OwnerCtl.OP_SAVE%>">&nbsp; &nbsp; <input
							type="submit" name="operation" value="<%=OwnerCtl.OP_RESET%>"></td>
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
