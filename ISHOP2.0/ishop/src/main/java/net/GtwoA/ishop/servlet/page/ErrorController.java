package net.GtwoA.ishop.servlet.page;

import net.GtwoA.ishop.servlet.AbstractController;
import net.GtwoA.ishop.util.RoutingUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error")
public class ErrorController extends AbstractController{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2163290882297862692L;

	@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("statuseCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR );
        RoutingUtils.forwardToPage("error.jsp",req,resp);
    }
}
