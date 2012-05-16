package mayaya.util.exception;

/**
 * Root of the hierarchy of data access exceptions. 
 */

@SuppressWarnings("serial")
public abstract class DataAccessException extends RuntimeException {
	
	/** 
	 * Constructor for DataAccessException
	 * @param message the detail message
	 * @param cause root cause (usually from using an underlying data
	 * access API such as JDBC)
	 */
	public DataAccessException(String message, Throwable cause){
		super(message,cause);
	}

	public DataAccessException(String message){
		super(message);
	}

}
