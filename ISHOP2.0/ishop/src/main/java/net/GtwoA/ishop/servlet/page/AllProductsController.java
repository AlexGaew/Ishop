package net.GtwoA.ishop.servlet.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.GtwoA.ishop.constant.Constants;
import net.GtwoA.ishop.entity.Product;
import net.GtwoA.ishop.servlet.AbstractController;
import net.GtwoA.ishop.util.RoutingUtils;

@WebServlet("/products")
public class AllProductsController extends AbstractController {

	private static final long serialVersionUID = 8087321231547348402L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Product> products = getProductService().listAllProducts(1, Constants.MAX_PRODUCT_PER_HTML_PAGE);
		req.setAttribute("products",products);
		int totalCount = getProductService().countAllProducts();
		req.setAttribute("pageCount", getPageCount(totalCount, Constants.MAX_PRODUCT_PER_HTML_PAGE));
		RoutingUtils.forwardToPage("products.jsp", req, resp);
	}
}
