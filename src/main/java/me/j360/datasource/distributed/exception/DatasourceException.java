package me.j360.datasource.distributed.exception;


/**
 * Created with j360-datasource -> me.j360.datasource.distributed.readwritestragy.
 * User: min_xu
 * Date: 2015/10/31
 * Time: 9:55
 * 说明：数据源错误异常
 */
public class DatasourceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 702035873879198253L;
	
	public DatasourceException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public DatasourceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public DatasourceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DatasourceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DatasourceException(String message, Throwable cause,
									 boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
}
