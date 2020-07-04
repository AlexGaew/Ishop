<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags"%>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<div class="container-fluid">
		<button class="navbar-toggler" type="button" data-toggle="collapse"	data-target="#ishopNav">
			<span class="navbar-toggler-icon"></span>
		</button>
		<a class="navbar-brand" href="/products" style="color: #3300cc;">IShop</a>
		<div class="collapse navbar-collapse" id="ishopNav">
			<ul id="currentShopingCart" class="navbar-nav mr-auto ${CURRENT_SHOPPING_CART == null ? 'd-none' : ''} ">
				<li class="nav-item dropdown">
				<a	class="nav-link dropdown-toggle card-position" href="#"	id="navbarDropdown" role="button" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false"> <i	class="fa fa-shopping-cart"> </i> Shopping card (<span
						class="total-count">${CURRENT_SHOPPING_CART.totalCount }</span>)
				</a>
					<div class="dropdown-menu shoping-cart-desc" aria-labelledby="navbarDropdown">
						Total count <span class="total-count">${CURRENT_SHOPPING_CART.totalCount }</span><br> 
						Total cost	<span class="total-cost">${CURRENT_SHOPPING_CART.totalCost }</span><br> <a href="/shopping-cart" class="btn btn-primary btn-block">
						View cart</a>
					</div>
					</li>
			</ul>
		</div>
		<c:choose>  
		<c:when test="${CURRENT_ACCOUNT !=null }">
		<ul class = "nav navar-nav navbar-right">
			<li><a style = "margin-right: 7px">Welcome ${CURRENT_ACCOUNT.description}  </a> </li>
			<li><a class="btn-outline-success" style = "margin-right: 7px" href ="/my-orders" > My orders  </a> </li>
			<li><a  href="javascript:void(0);" class="post-request" data-url="/sign-out">  Sign out</a> </li>
		</ul>
		</c:when>
		<c:when test="${CURRENT_REQUEST_URL !='/sign-in' and CURRENT_REQUEST_URL != '/shopping-cart' }" >
			<ishop:sign-in classes = "navbar-btn float-right sign-in" />
		
		</c:when>
		</c:choose>
	
	</div>
</nav>