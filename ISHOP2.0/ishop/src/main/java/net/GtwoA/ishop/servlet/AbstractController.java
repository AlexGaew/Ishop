package net.GtwoA.ishop.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.GtwoA.ishop.form.ProductForm;
import net.GtwoA.ishop.form.SearchForm;
import net.GtwoA.ishop.service.OrderService;
import net.GtwoA.ishop.service.ProductService;
import net.GtwoA.ishop.service.SocialService;
import net.GtwoA.ishop.service.impl.ServiceManager;

public abstract class AbstractController extends HttpServlet {

	private static final long serialVersionUID = -5363551493730092586L;
	protected final Logger  LOGGER = LoggerFactory.getLogger(getClass());

	private ProductService productService;
	private OrderService orderService;
	private SocialService socialService;

	@Override
	public final void init() throws ServletException {
		productService = ServiceManager.getInstance(getServletContext()).getProductService();
		orderService = ServiceManager.getInstance(getServletContext()).getOrderService();
		socialService = ServiceManager.getInstance(getServletContext()).getSocialService();
	}

	public final ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public final OrderService getOrderService() {
		return orderService;
	}
	
	public final SocialService getSocialService() {
		return socialService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public final int getPageCount(int totalCount, int itemsPerPage) {
		int res = totalCount / itemsPerPage;
		if (res * itemsPerPage != totalCount) {
			res++;
		}
		return res;
	}

	public final int getPage(HttpServletRequest request) {
		try {
			return Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			return 1;
		}

	}

	public final SearchForm createSearchForm(HttpServletRequest request) {

		return new SearchForm(request.getParameter("query"), request.getParameterValues("category"),
				request.getParameterValues("producer"));

	}

	public final ProductForm createProductForm(HttpServletRequest request) {

		return new ProductForm(Integer.parseInt(request.getParameter("idProduct")),
				Integer.parseInt(request.getParameter("count")));

	}
}
