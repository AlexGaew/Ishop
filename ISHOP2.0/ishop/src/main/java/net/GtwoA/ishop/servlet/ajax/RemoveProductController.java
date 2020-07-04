package net.GtwoA.ishop.servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.GtwoA.ishop.form.ProductForm;
import net.GtwoA.ishop.model.ShoppingCart;
import net.GtwoA.ishop.servlet.AbstractController;
import net.GtwoA.ishop.util.SessionUtils;
@WebServlet("/ajax/json/product/remove")
public class RemoveProductController extends AbstractProductController {

	
	private static final long serialVersionUID = -4851801402771228305L;
	
	@Override
	protected void processProductForm(ProductForm form,ShoppingCart shoppingCart,HttpServletRequest req,HttpServletResponse resp)  
			throws ServletException, IOException{
		getOrderService().removeProductFromShoppingCart(form, shoppingCart);
		if(shoppingCart.getItems().isEmpty()) {
			SessionUtils.clearCurrentShoppingCart(req, resp);
			
		}
		else {
			String cookieValue = getOrderService().serialazeShoppingCart(shoppingCart);
			SessionUtils.updateCurrentShoppingCartCookie(cookieValue, resp);
		}
		
	}



}
