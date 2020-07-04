package net.GtwoA.ishop.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Order extends AbstractEntity<Long> {

	private static final long serialVersionUID = -4457322251645818347L;

	private Integer idAccount;
	private List<OrderItem> items;
	private Timestamp created;

	public Integer getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Integer idAccount) {
		this.idAccount = idAccount;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public BigDecimal getTotalCost() {
		BigDecimal costBigDecimal = BigDecimal.ZERO;
		if (items != null) {

			for (OrderItem orderItem : items) {
				costBigDecimal = costBigDecimal
						.add(orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getCount())));

			}
		}
		return costBigDecimal;
	}

	@Override
	public String toString() {
		return String.format("Order [id=%s, idAccount=%s, items=%s, created=%s]", getId(), idAccount, items, created);
	}

}
