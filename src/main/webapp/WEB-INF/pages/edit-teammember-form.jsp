<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Edit team page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css"/>
  </head>
  <body>
    <div class="container">

      <%-- navbar --%>
      <nav class="navbar navbar-default">
        <div class="container-fluid">
          <!-- Brand and toggle get grouped for better mobile display -->
          <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand"  href="${pageContext.request.contextPath}/index.html"><spring:message code="page.home.title"/></a>
            <div class="navbar-brand">
              <div class="btn-group btn-group-xs" role="group" aria-label="...">
                <a class="btn btn-xs btn-default" href="?lang=en_US"><spring:message code="page.home.lang.us"/></a>
                <a class="btn btn-xs btn-default" href="?lang=en_UK"><spring:message code="page.home.lang.uk"/></a>
              </div>
            </div>
          </div>
          <!-- Collect the nav links, forms, and other content for toggling -->
          <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <%-- organizations --%>
            <ul class="nav navbar-nav">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message code="page.organization.home"/><span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="${pageContext.request.contextPath}/organization/add"><spring:message code="page.common.add"/></a></li>
                  <li><a href="${pageContext.request.contextPath}/organization/list"><spring:message code="page.common.list"/></a></li>
                </ul>
              </li>
              <%-- teams --%>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message code="page.team.home"/><span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="${pageContext.request.contextPath}/team/add"><spring:message code="page.common.add"/></a></li>
                  <li><a href="${pageContext.request.contextPath}/team/list"><spring:message code="page.common.list"/></a></li>
                </ul>
              </li>
              <%-- team members --%>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><spring:message code="page.teammember.home"/><span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="${pageContext.request.contextPath}/teammember/add"><spring:message code="page.common.add"/></a></li>
                  <li><a href="${pageContext.request.contextPath}/teammember/list"><spring:message code="page.common.list"/></a></li>
                </ul>
              </li>
            </ul>
          </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
      </nav>

      <%-- old --%>
      <h1>Edit team member page</h1>
      <p>Here you can edit the existing team member.</p>
      <p>${message}</p>
      <form:form method="POST" commandName="teammember" action="${pageContext.request.contextPath}/teammember/edit/${teammember.id}.html">
      <table>
      <tbody>
      	<tr>
      		<td>First name:</td>
      		<td><form:input path="firstName" /></td>
      	</tr>
      	<tr>
      		<td>Last name:</td>
      		<td><form:input path="lastName" /></td>
      	</tr>
      	    <tr>
              <td>Teams:</td>
              <td>
                <form:select path="teams" id="teams" multiple="true">
                  <form:options items="${teams}" itemValue="id" itemLabel="name"/>
                </form:select>
              </td>
            </tr>
      	<tr>
      		<td><input type="submit" value="Edit" /></td>
      		<td></td>
      	</tr>
      </tbody>
      </table>
      </form:form>

    </div>

    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>

  </body>
</html>
