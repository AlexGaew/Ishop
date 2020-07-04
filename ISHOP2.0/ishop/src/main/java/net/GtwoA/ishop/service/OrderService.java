package net.GtwoA.ishop.service;

import java.util.List;

import net.GtwoA.ishop.entity.Order;
import net.GtwoA.ishop.form.ProductForm;
import net.GtwoA.ishop.model.CurrentAccount;
import net.GtwoA.ishop.model.ShoppingCart;
import net.GtwoA.ishop.model.SocialAccount;

public interface OrderService {
	
	void addProductToShoppingCart(ProductForm productForm,ShoppingCart shoppingCart );

	String serialazeShoppingCart(ShoppingCart shoppingCart);
	
	void removeProductFromShoppingCart(ProductForm form,ShoppingCart shoppingCart);
	
	ShoppingCart deserialazeShoppingCart(String string);
	
	CurrentAccount authentificate(SocialAccount socialAccount);
	
	long makeOrder (ShoppingCart shoppingCart, CurrentAccount currentAccount);
	
	
	Order findOrderById(long id, CurrentAccount currentAccount);
	
	List<Order> listOrders(CurrentAccount currentAccount, int page,int limit);
	
	int countMyOrders(CurrentAccount currentAccount);
	

}
 