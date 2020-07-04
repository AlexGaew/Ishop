package net.GtwoA.ishop.exception;

import javax.servlet.http.HttpServletResponse;

public class AccessDeniedException extends AbstractApplicationExeption {


	private static final long serialVersionUID = -6762376509263370460L;
	
	public AccessDeniedException(String s) {
		super(s,HttpServletResponse.SC_FORBIDDEN); //403 errors
		}

}
