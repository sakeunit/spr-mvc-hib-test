<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
    <title>Home page</title>
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
            <a class="navbar-brand" href="#"><spring:message code="page.home.title"/></a>
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

      <div class="jumbotron">
        <h1><spring:message code="page.home.title"/></h1>
        <p><spring:message code="page.home.description"/></p>
      </div>
      <div>
        <div>
          <h3 id="message" class="bg-warning">${message}</h3>
        </div>
        <ul>
          <li><a href="${pageContext.request.contextPath}/organization/add"><spring:message code="page.organization.add.title"/></a>
          <li><a href="${pageContext.request.contextPath}/organization/list"><spring:message code="page.organization.list.title"/></a>
          <li><a href="${pageContext.request.contextPath}/team/add"><spring:message code="page.team.add.title"/></a>
          <li><a href="${pageContext.request.contextPath}/team/list"><spring:message code="page.team.list.title"/></a>
          <li><a href="${pageContext.request.contextPath}/teammember/add"><spring:message code="page.teammember.add.title"/></a>
          <li><a href="${pageContext.request.contextPath}/teammember/list"><spring:message code="page.teammember.list.title"/></a>
        <ul>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>

  </body>
</html>
