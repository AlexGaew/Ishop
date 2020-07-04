package net.GtwoA.ishop.servlet.page;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.GtwoA.ishop.constant.Constants;
import net.GtwoA.ishop.model.CurrentAccount;
import net.GtwoA.ishop.model.SocialAccount;
import net.GtwoA.ishop.servlet.AbstractController;
import net.GtwoA.ishop.util.RoutingUtils;
import net.GtwoA.ishop.util.SessionUtils;

@WebServlet("/from-social")
public class FromSocialController extends AbstractController {
	private static final long serialVersionUID = -8146770694377066438L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		if (code != null) {
			SocialAccount socialAccount = getSocialService().getSocialAccount(code);
			CurrentAccount currentAccount = getOrderService().authentificate(socialAccount);
			SessionUtils.setCurrentAccount(req, currentAccount);
			redirectToSuccesspage(req, resp);
			
		} else {
			LOGGER.warn("Parameter code not found");
			if(req.getSession().getAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGIN) != null) {
				req.getSession().removeAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGIN);
			}
		}
		RoutingUtils.redirect("/sign-in", req, resp);
	}
	
	protected void redirectToSuccesspage(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
		String targetUrl = (String) req.getSession().getAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGIN);
		if(targetUrl !=null) {
			req.getSession().removeAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGIN);
			RoutingUtils.redirect(URLDecoder.decode(targetUrl,"UTF-8"), req, resp);
		}
		else {
			RoutingUtils.redirect("/my-orders", req, resp);
		}
	}
}