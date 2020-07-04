package net.GtwoA.ishop.exception;

public abstract class AbstractApplicationExeption extends IllegalArgumentException {

	private static final long serialVersionUID = 1508271258262148556L;
	
	private final int code;

	public AbstractApplicationExeption(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}

	public AbstractApplicationExeption(Throwable cause, int code) {
		super(cause);
		this.code = code;
	}

	public AbstractApplicationExeption(String s, int code) {
		super(s);
		this.code = code;
	}

	
	public int getCode() {
		return code;
	}

}
