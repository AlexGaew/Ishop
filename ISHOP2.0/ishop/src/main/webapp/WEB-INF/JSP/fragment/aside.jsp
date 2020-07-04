<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ishop" tagdir="/WEB-INF/tags"%>

<div class="d-block d-sm-none xs-option-container">
	<a class="float-right " data-toggle="collapse" href="#productCatalog">Product
		catalog </a> <a data-toggle="collapse" href="#findProducts">Find
		products </a>
</div>
<!--Search form-->
<form class="search" action="/search">
	<div id="findProducts" class="card collapse">
		<div class="card-header">Find products</div>
		<div class="card-body">
			<div class="input-group">
				<input type="search" name="query" class="form-control"
					placeholder="Search query" value = "${searchForm.query }"> <span class="input-group-btn">
					<button id="goSearch" class="btn btn-default">Go!</button>
				</span>
			</div>
			<div class="more-options">
				<a data-toggle="collapse" href="#searchOptions"> More filters</a>
			</div>
		</div>
		<div id="searchOptions" class=" in collapse ${searchForm.categoriesNotEmpty or searchForm.producersNotEmpty ? 'show' : ''}">
			<ishop:category-filter categories="${CATEGORY_LIST }" searchForm = "${searchForm }" />
			<ishop:producer-filter producers="${PRODUCER_LIST }" searchForm = "${searchForm }"/>
		</div>
	</div>
</form>
<!--/Search form-->
<!--Categories-->
<div id="productCatalog" class="card "> 
	<div class="card-header">Product catalog</div>
	<div class="list-group">
		<c:forEach var="category" items="${CATEGORY_LIST }">
			<a href="/products${category.url }"
				class="list-group-item list-group-item-action list-group-item-light d-flex justify-content-between align-items-center${selectedCategoryUrl == category.url ? 'active' : ''}">
				${category.name } <span class="badge  badge-primary badge-pill">${category.productCount } </span>
				
			</a>
		</c:forEach>
	</div>
</div>
 
 






