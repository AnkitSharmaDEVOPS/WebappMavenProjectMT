<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ankit Sharma- Home Page</title>
<link href="https://www.w3schools.com/w3images/lights.jpg" rel="icon">
</head>
</head>
<body>
<h1 align="center">Hi! Myself Ankit Sharma.</h1>
<h1 align="center"> This is the DevOps based project based on Real Time scnerios</h1>
<hr>
<br>
        <h1><h3> Server Side IP Address </h3><br>

<%
String ip = "";
InetAddress inetAddress = InetAddress.getLocalHost();
ip = inetAddress.getHostAddress();
out.println("Server Host Name :: "+inetAddress.getHostName());
%>
<br>
<%out.println("Server IP Address :: "+ip);%>
</h1>
<br>
<h1><h3> Client Side IP Address </h3><br>
<%out.print( "Client IP Address :: " + request.getRemoteAddr() ); %><br>
<%out.print( "Client Name Host :: "+ request.getRemoteHost() );%><br></h1>
<hr>
<div style="text-align: center;">
        <span>
                <img src="https://www.w3schools.com/w3images/lights.jpg" alt="Beautiful Landscape" width="100">
        </span>
        <span style="font-weight: bold;">
                Ankit Sharma
                Please like, Share and Comment.
                Devops Engineer
                TCSL
                <br>
                <a href="mailto:ankitrial7@gmail.com">Mail to Ankit Sharma</a>
        </span>
</div>
<hr>
        <p> Service : <a href="services/employee/getEmployeeDetails">Get Employee Details </p>
<hr>
<hr>
<p align=center>Ankit Sharma - Youtuber , DevOps Engineer and may more skills unlocking in future.</p>
<p align=center><small>Copyrights 2022 by <a href="http://mithuntechnologies.com/">Ankit Sharma</a> </small></p>

</body>
</html>
