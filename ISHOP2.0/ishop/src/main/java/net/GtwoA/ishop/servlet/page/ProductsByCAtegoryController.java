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

@WebServlet("/products/*")
public class ProductsByCAtegoryController extends AbstractController {

	private static final long serialVersionUID = 8613449269798730336L;
	private static final int SUBSTRING_INDEX = "/products".length();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoriUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
		List<Product> products = getProductService().listProductsByCategory(categoriUrl, 1,
				Constants.MAX_PRODUCT_PER_HTML_PAGE);
		req.setAttribute("products", products);
		int totalCount = getProductService().countProductsByCategory(categoriUrl);
		req.setAttribute("pageCount", getPageCount(totalCount, Constants.MAX_PRODUCT_PER_HTML_PAGE));
		req.setAttribute("selectedCategoryUrl", categoriUrl);
		RoutingUtils.forwardToPage("products.jsp", req, resp);
	}

}
