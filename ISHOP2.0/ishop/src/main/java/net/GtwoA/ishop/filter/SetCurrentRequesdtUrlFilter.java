package net.GtwoA.ishop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.GtwoA.ishop.constant.Constants;
import net.GtwoA.ishop.util.WebUtils;
@WebFilter (filterName = "SetCurrentRequesdtUrlFilter" )
public class SetCurrentRequesdtUrlFilter extends AbstractFilter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setAttribute(Constants.CURRENT_REQUEST_URL, WebUtils.getCurrentRequestUrl(request));
		chain.doFilter(request, response);

	}

}
