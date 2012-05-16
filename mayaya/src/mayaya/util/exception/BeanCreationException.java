package mayaya.util.exception;

/*
 * @author jinyu
 */

@SuppressWarnings("serial")
public class BeanCreationException extends BeansException {

    public BeanCreationException(String msg, Throwable cause) {
        super(msg, cause);       
    }

}
