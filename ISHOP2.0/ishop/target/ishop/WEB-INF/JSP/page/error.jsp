<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<div class="alert alert-danger hidden-print" role="alert">

<h1> Code: ${statuseCode}</h1>

<c:choose>
<c:when test="${statuseCode==403} "> You don't have permissions to view this resource</c:when>
<c:when test="${statuseCode==404} "> Request resource not found</c:when>
<c:otherwise> Can't process this request! try again later...</c:otherwise>
</c:choose>
</div>