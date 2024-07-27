<!DOCTYPE html>
<%@page import="com.rays.pro4.Bean.UserBean"%>
<%@page import="com.rays.pro4.Bean.RoleBean"%>
<%@ page import ="com.rays.pro4.controller.LoginCtl" %>
<%@page import="com.rays.pro4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>


<body >
	<%
    UserBean userBean = (UserBean)session.getAttribute("user");
    boolean userLoggedIn = userBean != null;
    String welcomeMsg = "Hi, ";
    if (userLoggedIn) {
        String role = (String)session.getAttribute("role");
        welcomeMsg += userBean.getFirstName() + " (" + role + ")";
    } else {
        welcomeMsg += "Guest";
    }
	%>

<table >
    <tr ><th></th>
       <td width="90%" >
      
        <a href="<%=ORSView.WELCOME_CTL%>">Welcome</b></a> |
      
            	<%
            		if (userLoggedIn) {
      		 	 %> 
       <a href=" <%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">Logout</b></a>

            <%
                } else {
            %> 
            <a href="<%=ORSView.LOGIN_CTL%>">Login</b></a> 
            <%
  			   }
			 %>
 		</td>
        <td rowspan="2" >
            <h1 align="right" >
                <img src= "<%=ORSView.APP_CONTEXT %>/img/customLogo.jpg"  width="175" height="50">
            </h1>
        </td>
    </tr>

    <tr><th></th>
      <td>
        <h3><%=welcomeMsg%></h3>
      </td>
    </tr>
   
    <%
        if (userLoggedIn) {
    %>

    <tr><th></th>
        <td colspan="2" >     
        <font style="font-size: 18px">
       	 
        <a href="<%=ORSView.MY_PROFILE_CTL%>">MyProfile</b></a> |       
         <a href="<%=ORSView.CHANGE_PASSWORD_CTL%>">Change Password</b></a> |
       	 <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Get Marksheet</b></a> |              
       	 <a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet MeritList</b></a> |
        
        <%
            if (userBean.getRoleId() == RoleBean.ADMIN) {
        %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.USER_CTL%>">Add User</b></a> | 
        <a href="<%=ORSView.USER_LIST_CTL%>">User List</b></a> |         
        <a href="<%=ORSView.COLLEGE_CTL%>">Add College</b></a> |        
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.ROLE_CTL%>">Add Role</b></a> |        
        <a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</b></a> |
        <br>
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_CTL %>" >Add Course</b></a> |       
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_CTL %>" >Add Subject</b></a> |       
        <a href="<%=ORSView.SUBJECT_LIST_CTL %>" >Subject List</b></a> |          
        <a href="<%=ORSView.FACULTY_CTL %>" >Add Faculty</b></a> |       
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_CTL %>" >Add TimeTable</b></a> |       
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a href="<%=ORSView.EMPLOYEE_CTL%>" >Add Employee</b></a>|
        <a href="<%=ORSView.EMPLOYEE_LIST_CTL %>">Employee List</b></a>|
        <a href="<%=ORSView.LEAD_CTL%>"> Add Lead</b></a>|
        <a href="<%=ORSView.LEAD_LIST_CTL%>"> Lead List</b></a>|
        <a href="<%=ORSView.BANK_CTL%>">Add Bank</b></a>|
        <a href="<%=ORSView.BANK_LIST_CTL%>">BankList</b></a>|
        <a href="<%=ORSView.PRODUCT_CTL%>">Add Product</b></a>|
        <a href="<%=ORSView.PRODUCT_LIST_CTL%>">Product List</b></a>|
          <a href="<%=ORSView.ORDER_CTL%>">Add Order</b></a> |     
        <a href="<%=ORSView.ORDER_LIST_CTL%>">Order List</b></a> |
        <a href="<%=ORSView.CUSTOMER_CTL %>"> Add Customer</b></a>|
        <a href="<%=ORSView.CUSTOMER_LIST_CTL %>">CustomerList</b></a>|
       	<a href="<%=ORSView.PATIENT_CTL %>">Add Patient</b></a>|
        <a href="<%=ORSView.PATIENT_LIST_CTL %>">PatientList</b></a>| 
        <a href="<%=ORSView.DOCTOR_CTL %>">Add Doctor</b></a>| 
        <a href="<%=ORSView.DOCTOR_LIST_CTL %>">DoctorList</b></a>|
        
        <a href="<%=ORSView.CLIENT_CTL%>">Add Client</b></a>|
        <a href="<%=ORSView.CLIENT_LIST_CTL%>">Client List</b></a>|
        <a href="<%=ORSView.WISH_CTL%>">Add Wish</b></a>|
        <a href="<%=ORSView.WISH_LIST_CTL%>">Wish List</b></a>|
        <a href="<%=ORSView.SALARY_CTL%>">Add Salary</b></a>|
        <a href="<%=ORSView.SALARY_LIST_CTL%>">Salary List</b></a>|
        <a href="<%=ORSView.FIELD_CTL%>">Add Field</b></a>|
        <a href="<%=ORSView.FIELD_LIST_CTL%>">Field List</b></a>|                             
    	<a href="<%=ORSView.USER1_CTL%>"> Add User </b></a> |  
        <a href="<%=ORSView.USER1_LIST_CTL%>">  User list </b></a> |                                         
        <a href="<%=ORSView.PROJECT_CTL%>">Add Project</b></a> |     
        <a href="<%=ORSView.PROJECT_LIST_CTL%>">Project List</b></a> |                                                    
        <a href="<%=ORSView.FOLLOWUP_CTL%>">Add FollowUp</b></a> |     
        <a href="<%=ORSView.FOLLOWUP_LIST_CTL%>">FollowUp List</b></a> |
    	
 
               
                                <a target="blank" href="<%=ORSView.JAVA_DOC_VIEW%>">Java Doc</b></a> |
                        
             <a href="<%=ORSView.JOB_CTL%>">Add Job</b></a>|
        <a href="<%=ORSView.JOB_LIST_CTL%>">Job List</b></a>|
        
        
        
          <a href="<%=ORSView.SHOPPINGCART_CTL%>">Add ShoppingCart</b></a>|
        <a href="<%=ORSView.SHOPPINGCART_LIST_CTL%>">ShoppingCart List</b></a>|
         
         <a href="<%=ORSView.OWNER_CTL%>">Add Owner</b></a> |    
    	<a href="<%=ORSView.OWNER_LIST_CTL%>">Owner List</b></a> |  
           
          
        <%--  <a href="<%=ORSView.TRANSACTION_CTL%>">Add Transaction</b></a>|
        <a href="<%=ORSView.TRANSACTION_LIST_CTL%>">Transaction List</b></a>| --%>
      
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoleId() == RoleBean.STUDENT) {
        %> 
       
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_LIST_CTL %>">Subject List</b></a> |       
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
       <%
     		}
 		%>
		
 		<%
            if (userBean.getRoleId() == RoleBean.KIOSK) {
        %> 
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoleId() == RoleBean.FACULTY) {
           // System.out.println("======>><><>"+userBean.getRoleId());	
        %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</b></a> |
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
        <a href="<%=ORSView.SUBJECT_CTL %>" >Add Subject</b></a> |   
        <br>    
        <a href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</b></a> |       
        <a href="<%=ORSView.TIMETABLE_CTL %>" >Add TimeTable</b></a> |       
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
       <%
     		}
 		%>
 		 <%
            if (userBean.getRoleId() == RoleBean.COLLEGE) {
       //    System.out.println("======>><><>"+userBean.getRoleId());	
          %> 
       
        <a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</b></a> |       
        <a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</b></a> |
        <a href="<%=ORSView.STUDENT_CTL%>">Add Student</b></a> |
        <a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</b></a> |
        <a href="<%=ORSView.FACULTY_LIST_CTL %>">Faculty List</b></a> |
        <a href="<%=ORSView.TIMETABLE_LIST_CTL %>">TimeTable List</b></a> |
        <a href="<%=ORSView.COURSE_LIST_CTL %>">Course List</b></a> |       
       <%
     		}
 		%>
 		
 		</font>
 		</td>
    </tr>
    	<%
        	}
   		 %>
</table>
<hr>
</body>	
</html>