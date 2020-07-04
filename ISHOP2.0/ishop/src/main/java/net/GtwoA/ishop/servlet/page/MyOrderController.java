package net.GtwoA.ishop.servlet.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.GtwoA.ishop.constant.Constants;
import net.GtwoA.ishop.entity.Order;
import net.GtwoA.ishop.model.CurrentAccount;
import net.GtwoA.ishop.servlet.AbstractController;
import net.GtwoA.ishop.util.RoutingUtils;
import net.GtwoA.ishop.util.SessionUtils;
@WebServlet ("/my-orders")
public class MyOrderController extends AbstractController {

	
	private static final long serialVersionUID = 2758136392243510287L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CurrentAccount currentAccount = SessionUtils.getCurrentAccount(req);
		List<Order> orders = getOrderService().listOrders(currentAccount, 1, Constants.ORDERS_PER_PAGE);
		req.setAttribute("orders", orders);
		int orderCount = getOrderService().countMyOrders(currentAccount);
		req.setAttribute("pageCount", getPageCount(orderCount, Constants.ORDERS_PER_PAGE));
		RoutingUtils.forwardToPage("my-orders.jsp", req, resp);
	}

}
