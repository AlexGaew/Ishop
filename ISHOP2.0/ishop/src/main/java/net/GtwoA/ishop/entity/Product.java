package net.GtwoA.ishop.entity;

import java.math.BigDecimal;

public class Product extends AbstractEntity<Integer> {

	private static final long serialVersionUID = 6254932711032272519L;

	private String name;
	private String description;
	private String imageLink;
	private BigDecimal price;
	private String category;
	private String producer;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	@Override
	public String toString() {
		return String.format("Product [id=%s, name=%s, price=%s, description=%s, category=%s, producer=%s, imageLink=%s]",
				getId(), name, price, description, category, producer, imageLink);
	}

}
