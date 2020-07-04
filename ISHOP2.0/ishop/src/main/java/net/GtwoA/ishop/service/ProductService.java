package net.GtwoA.ishop.service;

import java.util.List;

import net.GtwoA.ishop.entity.*;
import net.GtwoA.ishop.form.SearchForm;

public interface ProductService {

	List<Product> listAllProducts(int page, int limit);

	int countAllProducts();

	int countProductsByCategory(String categorUrl);

	List<Product> listProductsByCategory(String categoryUrl, int page, int limit);

	List<Category> listAllCategories();

	List<Producer> listAllProducer();

	List<Product> listProductsBySearchForm(SearchForm searchForm, int page, int limit);
	
	int countProductsBySearchForm(SearchForm searchForm);
}
