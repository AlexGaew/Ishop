package net.GtwoA.ishop.model;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.GtwoA.ishop.constant.Constants;
import net.GtwoA.ishop.entity.Product;
import net.GtwoA.ishop.exception.ValidationException;


public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1579411036345374073L;
	private Map<Integer, ShoppingCartItem> products = new LinkedHashMap<>();
    private int totalCount = 0;
    private BigDecimal totalCost = BigDecimal.ZERO;

    public void addProduct(Product product, int count) throws ValidationException {
        validateShopingCartSize(product.getId());
        ShoppingCartItem shoppingCartItem = products.get(product.getId());
        if (shoppingCartItem == null) {
            validateProductCount(count);
            shoppingCartItem = new ShoppingCartItem(product, count);
            products.put(product.getId(), shoppingCartItem);
        } else {

            validateProductCount(count + shoppingCartItem.getCount());
            shoppingCartItem.setCount(shoppingCartItem.getCount() + count);
        }
        refreshStatistics();
    }

    public void removeProduct(int idProduct, int count) {
        ShoppingCartItem shoppingCartItem = products.get(idProduct);
        if (shoppingCartItem != null) {
            if (shoppingCartItem.getCount() > count) {
                shoppingCartItem.setCount(shoppingCartItem.getCount() - count);
            } else {
                products.remove(idProduct);
            }
            refreshStatistics();
        }
    }

    public Collection<ShoppingCartItem> getItems() {
        return products.values();
    }

    public int getTotalCount() {
    	
        return totalCount;
    }
    
 public BigDecimal getTotalCost() {
    	
        return totalCost;
    }

    public void validateShopingCartSize(int idProduct) throws ValidationException {
        if (products.size() > Constants.MAX_PRODUCT_PER_SHOPING_CART
                || (products.size() == Constants.MAX_PRODUCT_PER_SHOPING_CART && !products.containsKey(idProduct))) {
            throw new ValidationException("Limit for Shopping cert sixe reached: size=" + products.size());
        }

    }

    public void validateProductCount(int count) throws ValidationException {
        if (count > Constants.MAX_PRODUCT_COUNT_PER_SHOPING_CART) {
            throw new ValidationException("Limit for product count reached: count=" + count);

        }

    }

    private void refreshStatistics() {
        totalCount = 0;
        totalCost = BigDecimal.ZERO;
        for (ShoppingCartItem shopingCartItem : getItems()) {
            totalCount += shopingCartItem.getCount();
            totalCost = totalCost.add(shopingCartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(shopingCartItem.getCount())));
        }

    }

	@Override
	public String toString() {
		return String.format("ShoppingCart [products=%s, totalCount=%s, totalCost=%s]", products, totalCount,
				totalCost);
	}

   
}
