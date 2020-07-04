<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:forEach var="p" items="${products}">
	<div class="col-12 col-sm-6 col-md-4 col-lg-3 col-xl-2">
		<div id="product ${p.id}" class="card product">
			<div class="card-body">
				<div class="img-thumbnail">
					<img src="${p.imageLink}" alt="${p.name}" class="img-thumbnail">
					<div class="desc">
						<div class="cell">
							<p>
								<span class="title">Details</span>${p.description}
						</div>
					</div>
				</div>

				<h4 class="name">${p.name}</h4>
				<div class="code">Code: ${p.id}</div>
				<div class="price">$ ${p.price}</div>
				<a class="btn btn-primary float-right buy-btn" data-id-product="${p.id}">Buy</a> 
					<ul class="list-group">
					<li class="list-group-item"><small>Category:</small><span class="category"> ${p.category}</span></li>
					<li class="list-group-item"><small>Producer:</small><span class="producer"> ${p.producer}</span></li>
				</ul>
			</div>
		</div>
	</div>
</c:forEach>

