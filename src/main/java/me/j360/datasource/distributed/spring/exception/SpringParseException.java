package me.j360.datasource.distributed.spring.exception;

/**
 * Created with j360-datasource -> me.j360.datasource.distributed.spring.datasource.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：spring 解析错误
 */
public class SpringParseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3862711829434903737L;
	
	/**
	 * 
	 */
	public SpringParseException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public SpringParseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public SpringParseException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SpringParseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SpringParseException(String message, Throwable cause,
								boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
