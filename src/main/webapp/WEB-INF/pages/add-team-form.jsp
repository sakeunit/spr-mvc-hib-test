<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Add team page</title>
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
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.html">Home</a>
            <div class="navbar-brand">
              <span style="float: right">
                <a href="?lang=en_US">us</a>
                <a href="?lang=en_UK">uk</a>
              </span>
            </div>
          </div>
          <!-- Collect the nav links, forms, and other content for toggling -->
          <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <%-- organizations --%>
            <ul class="nav navbar-nav">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Organizations<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="${pageContext.request.contextPath}/organization/add">Add</a></li>
                  <li><a href="${pageContext.request.contextPath}/organization/list">List</a></li>
                </ul>
              </li>
              <%-- teams --%>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Teams<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="${pageContext.request.contextPath}/team/add">Add</a></li>
                  <li><a href="${pageContext.request.contextPath}/team/list">List</a></li>
                </ul>
              </li>
              <%-- team members --%>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">Members<span class="caret"></span></a>
                <ul class="dropdown-menu" role="menu">
                  <li><a href="${pageContext.request.contextPath}/teammember/add">Add</a></li>
                  <li><a href="${pageContext.request.contextPath}/teammember/list">List</a></li>
                </ul>
              </li>
            </ul>
          </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
      </nav>

      <%-- old --%>
      <h1>Add team page</h1>
      <p>Here you can add a new team.</p>
      <form:form method="POST" commandName="team" action="${pageContext.request.contextPath}/team/add.html">
        <table>
          <tbody>
            <tr>
              <td><strong><spring:message code="label.name"/></strong></td>
              <td><form:input path="name" /></td>
            </tr>
            <tr>
              <td><strong><spring:message code="label.rating"/></strong></td>
              <td><form:input path="rating" /></td>
            </tr>
            <tr>
              <td><strong><spring:message code="label.organization"/></strong></td>
              <td>
                <spring:message code="label.select" var="labelSelect"/></strong>
                <form:select path="organization" id="organization">
                  <form:option value="NONE" disabled="true" selected="selected" label="${labelSelect}"/>
                  <form:options items="${organizations}" itemValue="id" itemLabel="name"/>
                </form:select>
              </td>
            </tr>
            <tr>
              <td>
                <spring:message code="label.submit" var="labelSubmit"/>
                <input type="submit" value=${labelSubmit} />
              </td>
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
