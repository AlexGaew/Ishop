package net.GtwoA.ishop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.GtwoA.ishop.constant.Constants;
import net.GtwoA.ishop.service.ProductService;
import net.GtwoA.ishop.service.impl.ServiceManager;

public class CategoryProducerFilter extends AbstractFilter {
	private ProductService productService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		productService = ServiceManager.getInstance(filterConfig.getServletContext()).getProductService();
	}

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		request.setAttribute(Constants.CATEGORY_LIST, productService.listAllCategories());
		request.setAttribute(Constants.PRODUCER_LIST, productService.listAllProducer());
		chain.doFilter(request, response);
	}

}
