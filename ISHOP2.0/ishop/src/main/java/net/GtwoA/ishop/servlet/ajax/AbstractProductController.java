package net.GtwoA.ishop.servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import net.GtwoA.ishop.form.ProductForm;
import net.GtwoA.ishop.model.ShoppingCart;
import net.GtwoA.ishop.servlet.AbstractController;
import net.GtwoA.ishop.util.RoutingUtils;
import net.GtwoA.ishop.util.SessionUtils;

abstract public class AbstractProductController extends AbstractController {

	private static final long serialVersionUID = -6006197477128082191L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductForm form = createProductForm(req);
		ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);
		processProductForm(form, shoppingCart, req, resp);
		sendResponse(shoppingCart, req, resp);

	}

	protected abstract void processProductForm(ProductForm form, ShoppingCart shoppingCart, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException;

	protected void sendResponse(ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp) throws IOException {

		JSONObject cardStastistics = new JSONObject();
		cardStastistics.put("totalCount", shoppingCart.getTotalCount());
		cardStastistics.put("totalCost", shoppingCart.getTotalCost());
		RoutingUtils.sendJSON(cardStastistics, req, resp);
	}

}
