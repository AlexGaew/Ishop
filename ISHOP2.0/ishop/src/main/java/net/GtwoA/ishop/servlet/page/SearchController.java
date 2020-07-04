package net.GtwoA.ishop.servlet.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.GtwoA.ishop.constant.Constants;
import net.GtwoA.ishop.entity.Product;
import net.GtwoA.ishop.form.SearchForm;
import net.GtwoA.ishop.servlet.AbstractController;
import net.GtwoA.ishop.util.RoutingUtils;

@WebServlet("/search")
public class SearchController extends AbstractController  {

	
	private static final long serialVersionUID = -4517446115604886000L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SearchForm searchForm = createSearchForm(req);
		List<Product> products = getProductService().listProductsBySearchForm(searchForm, 1, Constants.MAX_PRODUCT_PER_HTML_PAGE);
		req.setAttribute("products",products);
		int totalCount = getProductService().countProductsBySearchForm(searchForm);
		req.setAttribute("pageCount", getPageCount(totalCount, Constants.MAX_PRODUCT_PER_HTML_PAGE));
		
		req.setAttribute("productCount", totalCount);
			req.setAttribute("searchForm", searchForm);
		RoutingUtils.forwardToPage("search-result.jsp", req, resp);
	}



}
