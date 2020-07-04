package net.GtwoA.ishop.servlet.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.GtwoA.ishop.constant.Constants;
import net.GtwoA.ishop.entity.Order;
import net.GtwoA.ishop.servlet.AbstractController;
import net.GtwoA.ishop.util.RoutingUtils;
import net.GtwoA.ishop.util.SessionUtils;
@WebServlet("/ajax/html/more/my-orders")
public class MyOrdersMoreController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6482676687406322248L;
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Order> orders = getOrderService().listOrders(SessionUtils.getCurrentAccount(req), getPage(req), Constants.ORDERS_PER_PAGE);
		req.setAttribute("orders", orders);
		RoutingUtils.forwardToPage("my-orders-tbody.jsp", req, resp);
	}
	
	

}
