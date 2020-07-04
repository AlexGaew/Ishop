package net.GtwoA.ishop.service.impl;



import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.GtwoA.ishop.entity.Account;
import net.GtwoA.ishop.entity.Order;
import net.GtwoA.ishop.entity.OrderItem;
import net.GtwoA.ishop.entity.Product;
import net.GtwoA.ishop.exception.AccessDeniedException;
import net.GtwoA.ishop.exception.InternalServerErrorExeption;
import net.GtwoA.ishop.exception.ResourceNotFoundExeption;
import net.GtwoA.ishop.form.ProductForm;
import net.GtwoA.ishop.jdbc.JDBCUtils;
import net.GtwoA.ishop.jdbc.ResultSetHandler;
import net.GtwoA.ishop.jdbc.ResultSetHandlerFactory;
import net.GtwoA.ishop.model.CurrentAccount;
import net.GtwoA.ishop.model.ShoppingCart;
import net.GtwoA.ishop.model.ShoppingCartItem;
import net.GtwoA.ishop.model.SocialAccount;
import net.GtwoA.ishop.service.OrderService;
import  net.GtwoA.ishop.exception.*;

class OrderServiceImpl implements OrderService {

	private static final ResultSetHandler<Product> productResultSetHandler = ResultSetHandlerFactory
			.getSingleResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);

	private static final ResultSetHandler<Account> accountResultSetHandler = ResultSetHandlerFactory
			.getSingleResultSetHandler(ResultSetHandlerFactory.ACCOUNT_RESULT_SET_HANDLER);
	
	private static final ResultSetHandler<Order> orderResultHadler = ResultSetHandlerFactory
			.getSingleResultSetHandler(ResultSetHandlerFactory.ORDER_RESULT_SET_HANDLER);
	
	private final ResultSetHandler<List<OrderItem>> orderItemListResultSetHandler = 
			ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.ORDER_ITEM_RESULT_SET_HANDLER);
	
	private final ResultSetHandler<List<Order>> ordersResultSetHandler = 
			ResultSetHandlerFactory.getListResultSetHandler(ResultSetHandlerFactory.ORDER_RESULT_SET_HANDLER);
	
	private final ResultSetHandler<Integer> countResultSetHandler = 
			ResultSetHandlerFactory.getCountResultSetHandler();

	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);
	private final DataSource dataSource;

	public OrderServiceImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart) {
		try (Connection c = dataSource.getConnection()) {

			Product product = JDBCUtils.select(c, "select p.*, c.name as category, pr.name as producer"
					+ " from product p, producer pr, category c where c.id=p.id_category and pr.id=id_producer and p.id=?",
					productResultSetHandler, productForm.getIdProduct());
			if (product == null) {
				throw new InternalServerErrorExeption("Product not fount by id = " + productForm.getIdProduct());
			} else {
				shoppingCart.addProduct(product, productForm.getCount());
			}
		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't exequte sql query: " + e.getMessage(), e);
		}

	}

	public String serialazeShoppingCart(ShoppingCart shoppingCart) {
		StringBuilder res = new StringBuilder();
		for (ShoppingCartItem item : shoppingCart.getItems()) {
			res.append(item.getProduct().getId()).append("-").append(item.getCount()).append("|");

		}
		if (res.length() > 0) {
			res.deleteCharAt(res.length() - 1);
		}
		return res.toString();
	}

	@Override
	public void removeProductFromShoppingCart(ProductForm form, ShoppingCart shoppingCart) {
		shoppingCart.removeProduct(form.getIdProduct(), form.getCount());

	}

	@Override
	public ShoppingCart deserialazeShoppingCart(String string) {
		ShoppingCart shoppingCart = new ShoppingCart();
		String[] items = string.split("\\|");
		for (String item : items) {
			try {
				String data[] = item.split("-");
				int idProduct = Integer.parseInt(data[0]);
				int count = Integer.parseInt(data[1]);
				addProductToShoppingCart(new ProductForm(idProduct, count), shoppingCart);

			} catch (RuntimeException e) {
				LOGGER.error("Can't add product to ShoppingCart uring deserialazation item=" + item, e);
				// TODO: handle exception
			}

		}
		return shoppingCart.getItems().isEmpty() ? null : shoppingCart;
	}

	@Override
	public CurrentAccount authentificate(SocialAccount socialAccount) {
		try (Connection c = dataSource.getConnection()) {
			Account account = JDBCUtils.select(c, "select * from account where email=?", accountResultSetHandler,
					socialAccount.getEmail());
			if (account == null) {
				account = new Account(socialAccount.getName(), socialAccount.getEmail());
				account = JDBCUtils.insert(c, "insert into account values (nextval('account_seq'),?,?)",
						accountResultSetHandler, account.getName(), account.getEmail());
				c.commit();

			}
			return account;

		} catch (Exception e) {
			throw new InternalServerErrorExeption("Can't exequte SQL request: " + e.getMessage(), e);
		}
	}

	@Override
	public long makeOrder(ShoppingCart shoppingCart, CurrentAccount currentAccount) {
		if(shoppingCart == null || shoppingCart.getItems().isEmpty()) {
			throw new InternalServerErrorExeption("shopping cart is null or empty");
			}
		try (Connection c = dataSource.getConnection()){
			Order order = JDBCUtils.insert(c, "insert into \"order\" values(nextval('order_seq'),?,?)", orderResultHadler ,
					currentAccount.getId(), new Timestamp(System.currentTimeMillis()));
			JDBCUtils.insertBatch(c, "insert into order_item values(nextval('order_item_seq'),?,?,?)", toOrderItemParametrList(order.getId(),shoppingCart.getItems()));
			
			c.commit();
			return order.getId();
		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't exequte SQL request: " + e.getMessage(),e);
		}
		
	}

	private List<Object[]> toOrderItemParametrList(Long idOrder, Collection<ShoppingCartItem> items) {
		List<Object[]> parametList = new ArrayList<>();
		for (ShoppingCartItem item : items) {
			parametList.add(new Object[] {idOrder, item.getProduct().getId(),item.getCount()});
			
		}
		return parametList;
			
	}

	@Override
	public Order findOrderById(long id, CurrentAccount currentAccount) {
		try (Connection c = dataSource.getConnection()){
			Order order = JDBCUtils.select(c, "select * from \"order\" where id=?", orderResultHadler, id);
			if (order==null) {
				throw new ResourceNotFoundExeption("Order not fount by id: " + id);
			}
			if(!order.getIdAccount().equals(currentAccount.getId())) {
				throw new AccessDeniedException("Account with id=" + currentAccount.getId( ) + "is not for order with id=" +id);
			}
			List<OrderItem> list = JDBCUtils.select(c, 
					"select o.id as oid, o.id_order as id_order, o.id_product, o.count, p.*, c.name as category, pr.name as producer from order_item o, product p, category c, producer pr "
					+ "where pr.id=p.id_producer and c.id=p.id_category and o.id_product=p.id and o.id_order=?"
					, orderItemListResultSetHandler, id);
			order.setItems(list);
			return order;
			} catch (SQLException e) {
				throw new InternalServerErrorExeption("Can't exequte request findOrderById: " + e.getMessage(),e);
			}
			
		}

	@Override
	public List<Order> listOrders(CurrentAccount currentAccount, int page, int limit) {
		int offset = (page-1)*limit;
		try(Connection c = dataSource.getConnection()){
			List<Order> orders = JDBCUtils.select(c, "select * from \"order\" where id_account=? order by id desc limit ? offset ? ", ordersResultSetHandler, currentAccount.getId(),limit,offset );
			return orders;
		}
	 catch (SQLException e) {
		throw new InternalServerErrorExeption("Can't execute SQL request listOrders: " + e.getMessage(), e);
	
	}
	}

	@Override
	public int countMyOrders(CurrentAccount currentAccount) {
		try (Connection c = dataSource.getConnection()) {
			return JDBCUtils.select(c, "select count(*) from \"order\" where id_account=?", countResultSetHandler, currentAccount.getId());
		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't execute SQL request countMyOrders: " + e.getMessage(), e);
		}
	}
	}


