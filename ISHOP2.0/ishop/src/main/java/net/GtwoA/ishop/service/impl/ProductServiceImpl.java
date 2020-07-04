package net.GtwoA.ishop.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.GtwoA.ishop.entity.Category;
import net.GtwoA.ishop.entity.Producer;
import net.GtwoA.ishop.entity.Product;
import net.GtwoA.ishop.exception.InternalServerErrorExeption;
import net.GtwoA.ishop.form.SearchForm;
import net.GtwoA.ishop.jdbc.JDBCUtils;
import net.GtwoA.ishop.jdbc.ResultSetHandler;
import net.GtwoA.ishop.jdbc.ResultSetHandlerFactory;
import net.GtwoA.ishop.jdbc.SearchQuery;
import net.GtwoA.ishop.service.ProductService;

class ProductServiceImpl implements ProductService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	private static final ResultSetHandler<List<Product>> productResultSetHandler = ResultSetHandlerFactory
			.getListResultSetHandler(ResultSetHandlerFactory.PRODUCT_RESULT_SET_HANDLER);

	private static final ResultSetHandler<List<Category>> categoryListResultHendler = ResultSetHandlerFactory
			.getListResultSetHandler(ResultSetHandlerFactory.CATEGORY_RESULT_SET_HANDLER);

	private static final ResultSetHandler<List<Producer>> producerListResultHendler = ResultSetHandlerFactory
			.getListResultSetHandler(ResultSetHandlerFactory.PRODUCER_RESULT_SET_HANDLER);
	private static final ResultSetHandler<Integer> countRezultSetHandler = ResultSetHandlerFactory
			.getRezultSetHandler();

	private final DataSource dataSource;

	public ProductServiceImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<Product> listAllProducts(int page, int limit) {
		try (Connection c = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			return JDBCUtils.select(c, "select p.*, c.name as category, pr.name as producer"
					+ " from product p, producer pr, category c where c.id=p.id_category and pr.id=id_producer limit ? offset ?",
					productResultSetHandler, limit, offset);

		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't exequte sql query: " + e.getMessage(), e);
		}

	}

	@Override
	public List<Product> listProductsByCategory(String categoryUrl, int page, int limit) {
		try (Connection c = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			return JDBCUtils.select(c, "select p.*, c.name as category, pr.name as producer"
					+ " from product p, category c, producer pr  where c.url=? and pr.id=p.id_producer and c.id=p.id_category order by p.id limit ? offset ?",
					productResultSetHandler, categoryUrl, limit, offset);

		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't exequte sql query: " + e.getMessage(), e);
		}

	}

	@Override
	public List<Category> listAllCategories() {
		try (Connection c = dataSource.getConnection()) {
			return JDBCUtils.select(c, "select * from category order by id", categoryListResultHendler);
		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't exequte sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Producer> listAllProducer() {
		try (Connection c = dataSource.getConnection()) {
			return JDBCUtils.select(c, "select * from producer order by name", producerListResultHendler);
		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't exequte sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public int countAllProducts() {
		try (Connection c = dataSource.getConnection()) {
			return JDBCUtils.select(c, "select  count(*) from product", countRezultSetHandler);

		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't exequte sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public int countProductsByCategory(String categoryUrl) {
		try (Connection c = dataSource.getConnection()) {
			return JDBCUtils.select(c,
					"select  count(p.*) from product p, category c where c.id=id_category and c.url=?",
					countRezultSetHandler, categoryUrl);

		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't exequte sql query: " + e.getMessage(), e);
		}
	}

	@Override
	public List<Product> listProductsBySearchForm(SearchForm form, int page, int limit) {
		try (Connection c = dataSource.getConnection()) {
			int offset = (page - 1) * limit;
			SearchQuery sq = buildSearchQuery("p.*, c.name as category, pr.name as producer", form);
			sq.getSql().append(" order by p.id limit ? offset ?");
			sq.getParams().add(limit);
			sq.getParams().add(offset);
			LOGGER.debug("search query={} with params={}", sq.getSql(), sq.getParams());

			return JDBCUtils.select(c, sq.getSql().toString(), productResultSetHandler, sq.getParams().toArray());
		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't exequte sql query: " + e.getMessage(), e);
		}
	}

	protected SearchQuery buildSearchQuery(String selectFields, SearchForm form) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder("select ");
		sql.append(selectFields).append(" from product p, category c, producer pr where pr.id=p.id_producer and "
				+ "c.id=p.id_category and (p.name ilike ? or p.description ilike ?)");
		params.add("%" + form.getQuery() + "%");
		params.add("%" + form.getQuery() + "%");
		JDBCUtils.populateSqlAndParams(sql, params, form.getCategories(), "c.id = ?");
		JDBCUtils.populateSqlAndParams(sql, params, form.getProducers(), "pr.id = ?");
		return new SearchQuery(sql, params);
	}

	@Override
	public int countProductsBySearchForm(SearchForm form) {
		try (Connection c = dataSource.getConnection()) {
			SearchQuery sq = buildSearchQuery("count(*)", form);
			LOGGER.debug("search query={} with params={}", sq.getSql(), sq.getParams());

			return JDBCUtils.select(c, sq.getSql().toString(), countRezultSetHandler, sq.getParams().toArray());

		} catch (SQLException e) {
			throw new InternalServerErrorExeption("Can't exequte sql query: " + e.getMessage(), e);
		}
	}

}
