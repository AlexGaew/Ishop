package net.GtwoA.ishop.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.GtwoA.ishop.constant.Constants;
import net.GtwoA.ishop.service.impl.ServiceManager;

@WebListener
public class IshopApplicationListener implements ServletContextListener {
	protected static final Logger LOGGER = LoggerFactory.getLogger(IshopApplicationListener.class);
	private ServiceManager serviceManager;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			serviceManager = ServiceManager.getInstance(sce.getServletContext());
			sce.getServletContext().setAttribute(Constants.CATEGORY_LIST, serviceManager.getProductService().listAllCategories());
			sce.getServletContext().setAttribute(Constants.PRODUCER_LIST, serviceManager.getProductService().listAllProducer());
		} catch (Exception e){
			LOGGER.error("Web application init failed " + e.getMessage(),e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		LOGGER.info("Web application 'ishop' initialized");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		serviceManager.close();
		LOGGER.info("Web application 'ishop' destroyed");
	}
}
