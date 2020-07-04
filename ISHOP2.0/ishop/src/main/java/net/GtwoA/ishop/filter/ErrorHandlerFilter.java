package net.GtwoA.ishop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.json.JSONObject;

import net.GtwoA.ishop.exception.AbstractApplicationExeption;
import net.GtwoA.ishop.exception.AccessDeniedException;
import net.GtwoA.ishop.exception.InternalServerErrorExeption;
import net.GtwoA.ishop.exception.ResourceNotFoundExeption;
import net.GtwoA.ishop.exception.ValidationException;
import net.GtwoA.ishop.util.RoutingUtils;
import net.GtwoA.ishop.util.UrlUtils;

@WebFilter(filterName = "ErrorHandlerFilter")
public class ErrorHandlerFilter extends AbstractFilter {
	
	private static final String INTERNAL_ERROR = "INTERNAL_ERROR";

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(req, new ThrowExeptionInsteadOnSendErrorResponse(resp));
		} catch (Throwable th) {
			String requestUrl = req.getRequestURI();
			if (th instanceof ValidationException) {
				LOGGER.warn("Request is not valid: " + th.getMessage());
			} else {
				LOGGER.error("Request " + requestUrl + " failed: " + th.getMessage(), th);
			}

			handlExeption(requestUrl, th, req, resp);
		}
	}

	private int getStatusCode(Throwable th) {
		if (th instanceof AbstractApplicationExeption) {
			return (((AbstractApplicationExeption) th).getCode());
		} else {
			return (HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}

	}

	private void handlExeption(String requestUrl, Throwable th, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int statuseCode = getStatusCode(th);
		resp.setStatus(statuseCode);

		if (UrlUtils.isAjaxJsonUrl(requestUrl)) {
			JSONObject json = new JSONObject();
			json.put("massage", th instanceof ValidationException ? th.getMessage() : INTERNAL_ERROR);
			RoutingUtils.sendJSON(json, req, resp);

		} else if (UrlUtils.isAjaxHTMLUrl(requestUrl)) {
				RoutingUtils.sendHTMLFragment(INTERNAL_ERROR, req, resp);
		} else {
				req.setAttribute("statuseCode", statuseCode);
				RoutingUtils.forwardToPage("error.jsp", req, resp);
		}

	}
	
	private static class ThrowExeptionInsteadOnSendErrorResponse extends HttpServletResponseWrapper {

		public ThrowExeptionInsteadOnSendErrorResponse(HttpServletResponse response) {
			super(response);
			
		}
		@Override
		public void sendError(int sc) throws IOException {
		sendError(sc,INTERNAL_ERROR);
		}
		@Override
		public void sendError(int sc, String msg) throws IOException {
			switch (sc) {
			case 403: {
				throw new AccessDeniedException(msg);
			}
			case 404: {
				throw new ResourceNotFoundExeption(msg);
			}
			
			case 400: {
				throw new ValidationException(msg);
			}
			default:
				throw new InternalServerErrorExeption(msg);
			}
		}
		
	}

}
