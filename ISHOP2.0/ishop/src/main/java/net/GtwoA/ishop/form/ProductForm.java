package net.GtwoA.ishop.form;

public class ProductForm {

	public ProductForm(Integer idProduct, Integer count) {
		super();
		this.idProduct = idProduct;
		this.count = count;
	}

	public ProductForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Integer idProduct;
	private Integer count;

	public Integer getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
