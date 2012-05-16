package mayaya.util.exception;

/**
 * Root class to all exception related to beans
 * @author jinyu
 *
 */


@SuppressWarnings("serial")
public abstract class BeansException extends RuntimeException{
    
    /**
     * Create a new BeansException with the specified message
     * @param msg the detail message
     */
    public BeansException(String msg){
        super(msg);
    }
    
    /**
     * Create a new BeansException with the specified message
     * and root cause.
     * @param msg the detail message
     * @param cause the root cause
     */
    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }

    
    
}
