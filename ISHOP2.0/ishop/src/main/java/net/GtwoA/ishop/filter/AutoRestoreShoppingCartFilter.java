package net.GtwoA.ishop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.GtwoA.ishop.model.ShoppingCart;
import net.GtwoA.ishop.service.OrderService;
import net.GtwoA.ishop.service.impl.ServiceManager;
import net.GtwoA.ishop.util.SessionUtils;

@WebFilter(filterName = "AutoRestoreShoppingCartFilter")
public class AutoRestoreShoppingCartFilter extends AbstractFilter {
	private static final String SHOPPING_CARD_DESERIALIZATION_DONE = "SHOPPING_CARD_DESERIALIZATION_DONE";

	private OrderService orderService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		orderService = ServiceManager.getInstance(filterConfig.getServletContext()).getOrderService();
	}

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		if (req.getSession().getAttribute(SHOPPING_CARD_DESERIALIZATION_DONE) == null) {
			if (!SessionUtils.isCurrentShoppingCartCreated(req)) {
				Cookie cookie = SessionUtils.findShoppingCartCookie(req);
				if (cookie != null) {
					ShoppingCart shoppingCart = orderService.deserialazeShoppingCart(cookie.getValue());
					SessionUtils.setCurrentShoppingCart(req, shoppingCart);

				}
			}
			req.getSession().setAttribute(SHOPPING_CARD_DESERIALIZATION_DONE, Boolean.TRUE);
		}
		chain.doFilter(req, resp);

	}
	/*
	 * protected ShoppingCart shoppingCartFromString(String cookieValue) {
	 * ShoppingCart shoppingCart = new ShoppingCart(); String[] items =
	 * cookieValue.split("\\|"); for (String item : items) { String data[] =
	 * item.split("-"); try { int idProduct = Integer.parseInt(data[0]); int count =
	 * Integer.parseInt(data[1]); orderService.addProductToShoppingCart(new
	 * ProductForm(idProduct,count), shoppingCart);
	 * 
	 * } catch (RuntimeException e) { e.printStackTrace(); }
	 * 
	 * } return shoppingCart; }
	 */
	/*
	 * protected String shopingCartToString(ShoppingCart shoppingCart) {
	 * StringBuilder res = new StringBuilder(); for (ShoppingCartItem
	 * shoppingCartItem : shoppingCart.getItems() ) {
	 * res.append(shoppingCartItem.getIdProduct()).append("-").append(
	 * shoppingCartItem.getCount()).append("|");
	 * 
	 * } if (res.length() > 0) { res.deleteCharAt(res.length() - 1); } return
	 * res.toString(); }
	 */

	/*
	 * { ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);
	 * String cookieValue = shopingCartToString(shoppingCart);
	 * SessionUtils.updateCurrentShoppingCartCookie(cookieValue, resp); }
	 */

}
