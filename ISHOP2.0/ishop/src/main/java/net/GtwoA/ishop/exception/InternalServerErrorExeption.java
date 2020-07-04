package net.GtwoA.ishop.exception;

import javax.servlet.http.HttpServletResponse;

public class InternalServerErrorExeption extends AbstractApplicationExeption {


	private static final long serialVersionUID = -6304970120628086808L;

	public InternalServerErrorExeption(String message, Throwable cause) {
		super(message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 error
		
	}

	public InternalServerErrorExeption(String message) {
		super(message,HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	}

	public InternalServerErrorExeption(Throwable cause) {
		super(cause,HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	}
	
	
	
	

}
