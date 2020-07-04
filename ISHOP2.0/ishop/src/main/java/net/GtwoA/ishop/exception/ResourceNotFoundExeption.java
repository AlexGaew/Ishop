package net.GtwoA.ishop.exception;

import javax.servlet.http.HttpServletResponse;

public class ResourceNotFoundExeption extends AbstractApplicationExeption {

	private static final long serialVersionUID = -1184780923682888118L;
	
	public ResourceNotFoundExeption (String s) {
		super(s,HttpServletResponse.SC_NOT_FOUND); //error 404
	}

}
