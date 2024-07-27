<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.controller.OrderListCtl"%>
<%@page import="com.rays.pro4.Bean.*"%>
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
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.OrderBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.ORDER_LIST_CTL%>" method="post">

		<center>

			<div align="centre">
				<h1>Order List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>


				<%
					// List dlist= (List) request.getAttribute("dList");

					List plist = (List) request.getAttribute("product");
				%>

				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<OrderBean> it = list.iterator();

					if (list.size() != 0) {
				%>
				<!-- <table width="100%" align="center"> -->
					<tr>
						<th></th>
						<td align="center"><label>Quantity</font> :
						</label> <input type="number" name="quantity"
							placeholder="Enter  quantity"
							value="<%=ServletUtility.getParameter("quantity", request)%>">
							&emsp;
						<td align="center"><label>Dob</font> :
						</label> <input type="text" name="dob" placeholder="Enter  Dob" 
							
							value="<%=ServletUtility.getParameter("dob", request)%>">


							&emsp; <label>Product</font> :
						</label> <%=HTMLUtility.getList("product", String.valueOf(bean.getProduct()), plist)%>


							<label>Amount</font> :
						</label> <input type="number" name="amount" placeholder="Enter  Amount"
							value="<%=ServletUtility.getParameter("amount", request)%>">



							&emsp; <input type="submit" name="operation"
							value="<%=OrderListCtl.OP_SEARCH%>"> &nbsp; <input
							type="submit" name="operation" value="<%=OrderListCtl.OP_RESET%>">
						</td>
					</tr>


					<table border="1" width="100%" align="centre" cellpadding=6px
						cellspacing=".2">
						<tr style="background: skyblue">
							<th><input type="checkbox" id="select_all" name="select">Select
								All</th>

							<th>S.No.</th>
							<th>Quantity</th>
							<th>Date Of Birth</th>
							<th>Product</th>
							<th>Amount</th>
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
							<td><%=bean.getQuantity()%></td>
							<td><%=bean.getDob()%></td>
							<td><%=bean.getProduct()%></td>
							<td><%=bean.getAmount()%></td>

							<td><a href="OrderCtl?id=<%=bean.getId()%>">Edit</a></td>
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
									disabled="disabled" value="<%=OrderListCtl.OP_PREVIOUS%>"></td>
								<%
									} else {
								%>
								<td><input type="submit" name="operation"
									value="<%=OrderListCtl.OP_PREVIOUS%>"></td>
								<%
									}
								%>


								<td><input type="submit" name="operation"
									value="<%=OrderListCtl.OP_DELETE%>"></td>

								<td><input type="submit" name="operation"
									value="<%=OrderListCtl.OP_NEW%>"></td>

								<td align="right"><input type="submit" name="operation"
									value="<%=OrderListCtl.OP_NEXT%>"
									<%=(list.size() < pageSize) ? "disabled" : ""%>></td>




							</tr>
						</table>


						<%
							}
							if (list.size() == 0) {
						%>
						<td align="centre"><input type="submit" name="operation"
							value="<%=OrderListCtl.OP_BACK%>"> &nbsp; &nbsp;</td>
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