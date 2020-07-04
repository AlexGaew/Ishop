package net.GtwoA.ishop.servlet.ajax;

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

@WebServlet("/ajax/html/more/products")
public class AllProductsMoreController extends AbstractController {

	private static final long serialVersionUID = 7318876744679707403L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Product> products = getProductService().listAllProducts(getPage(req), Constants.MAX_PRODUCT_PER_HTML_PAGE);
		req.setAttribute("products",products);
		RoutingUtils.forwardToFragment("product_list.jsp", req, resp);
		
		
		

	}

}
