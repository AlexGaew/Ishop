<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>



<div id="addProductPopup" class="modal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Add product to Shoping cart</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body row">
				<div class="col-xs-12 col-sm-6">
				<div class="card-body">
					<img class="product-image" src="data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs%3D"	 alt="Product image">
					</div>
				</div>
				<div class="col-xs-12 col-sm-6">
					<h4 class="name text-center">Name</h4>
					<div class="list-group d-none d-sm-block"
						style="margin-bottom: 15px;">
						<span class="list-group-item"><small>Category:</small> <span class="category"> ?</span></span> 
							<span class="list-group-item"><small>Producer:</small>	<span class="producer"> ?</span></span>
					</div>
					<div class="list-group">
						<span class="list-group-item"><small>Price:</small> <span
							class="price"> 0 </span></span> <span class="list-group-item"><small>Count:</small>
							<input type="number" class="count" value="1" min="1" max="10"></span>
						<span class="list-group-item"><small>Cost:</small> <span
							class="cost"> 0 </span></span>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<img id="addToCartIndicator" src="static/img/loading.gif"
					class="d-none" alt="Loading...">
				<button id="addToCart" type="button" class="btn btn-primary">Add
					to Cart</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
