package net.GtwoA.ishop.exception;

import javax.servlet.http.HttpServletResponse;

public class ValidationException extends AbstractApplicationExeption {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3693957394486243044L;

	public ValidationException(String s) {
		super(s,HttpServletResponse.SC_BAD_REQUEST);  //error 400
	}

}
