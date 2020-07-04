package net.GtwoA.ishop.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.GtwoA.ishop.entity.Account;
import net.GtwoA.ishop.entity.Category;
import net.GtwoA.ishop.entity.Order;
import net.GtwoA.ishop.entity.OrderItem;
import net.GtwoA.ishop.entity.Producer;
import net.GtwoA.ishop.entity.Product;

public final class ResultSetHandlerFactory {

	public final static ResultSetHandler<Product> PRODUCT_RESULT_SET_HANDLER = new ResultSetHandler<Product>() {

		@Override
		public Product handle(ResultSet rs) throws SQLException {

			Product p = new Product();
			p.setCategory(rs.getString("category"));
			p.setDescription(rs.getString("description"));
			p.setId(rs.getInt("id"));
			p.setImageLink(rs.getString("image_link"));
			p.setName(rs.getString("name"));
			p.setPrice(rs.getBigDecimal("price"));
			p.setProducer(rs.getString("producer"));

			// TODO Auto-generated method stub
			return p;
		}

	};

	public final static ResultSetHandler<Category> CATEGORY_RESULT_SET_HANDLER = new ResultSetHandler<Category>() {

		@Override
		public Category handle(ResultSet rs) throws SQLException {

			Category category = new Category();
			category.setId(rs.getInt("id"));
			category.setName(rs.getString("name"));
			category.setUrl(rs.getString("url"));
			category.setProductCount(rs.getInt("product_count"));

			// TODO Auto-generated method stub
			return category;
		}

	};
	public final static ResultSetHandler<Producer> PRODUCER_RESULT_SET_HANDLER = new ResultSetHandler<Producer>() {

		@Override
		public Producer handle(ResultSet rs) throws SQLException {

			Producer producer = new Producer();
			producer.setId(rs.getInt("id"));
			producer.setName(rs.getString("name"));
			producer.setProductCount(rs.getInt("product_count"));

			// TODO Auto-generated method stub
			return producer;
		}

	};
	public final static ResultSetHandler<Account> ACCOUNT_RESULT_SET_HANDLER = new ResultSetHandler<Account>() {

		@Override
		public Account handle(ResultSet rs) throws SQLException {

			Account account = new Account();
			account.setId(rs.getInt("id"));
			account.setEmail(rs.getString("email"));
			account.setName(rs.getString("name"));
		

			// TODO Auto-generated method stub
			return account;
		}

	};
	
	public final static ResultSetHandler<OrderItem> ORDER_ITEM_RESULT_SET_HANDLER = new ResultSetHandler<OrderItem>() {

		@Override
		public OrderItem handle(ResultSet rs) throws SQLException {

			OrderItem orderItem = new OrderItem();
			orderItem.setId(rs.getInt("oid"));
			orderItem.setCount(rs.getInt("count"));
			orderItem.setIdOrder(rs.getLong("id_order"));
			Product p = PRODUCT_RESULT_SET_HANDLER.handle(rs);
			orderItem.setProduct(p);

			return orderItem;
		}

	};
	
	public final static ResultSetHandler<Order> ORDER_RESULT_SET_HANDLER= new ResultSetHandler<Order>() {

		@Override
		public Order handle(ResultSet rs) throws SQLException {

			Order order = new Order();
			order.setId(rs.getLong("id"));
			order.setCreated(rs.getTimestamp("created"));
			order.setIdAccount(rs.getInt("id_account"));
			return order;
		}

	};
	public final static ResultSetHandler<Integer> getCountResultSetHandler() {
		return new ResultSetHandler<Integer>() {
			@Override
			public Integer handle(ResultSet rs) throws SQLException {
				if (rs.next()) {
					return rs.getInt(1);
				} else {
					return 0;
				}
			}
		};
	}
	
	
	
	
	
	public final static ResultSetHandler<Integer> getRezultSetHandler(){
		return new ResultSetHandler<Integer>() {
			
			@Override
			public Integer handle(ResultSet rs) throws SQLException {
				if (rs.next()) {
					return rs.getInt(1);
				} else {
					return 0;
				}
			}
		};
		
	}
	public final static <T> ResultSetHandler<T> getSingleResultSetHandler(
			final ResultSetHandler<T> oneRowResultSetHandler) {
		return new ResultSetHandler<T>() {

			@Override
			public T handle(ResultSet rs) throws SQLException {

				if (rs.next()) {
					return oneRowResultSetHandler.handle(rs);

				} else {
					return null;
				}
			}
		};

	}

	public final static <T> ResultSetHandler<List<T>> getListResultSetHandler(
			final ResultSetHandler<T> oneRowResultSetHandler) {
		return new ResultSetHandler<List<T>>() {

			@Override
			public List<T> handle(ResultSet rs) throws SQLException {
				List<T> list = new ArrayList();
				while (rs.next()) {
					list.add(oneRowResultSetHandler.handle(rs));

				}
				return list;
			}

		};

	}
}
