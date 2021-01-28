package observer;

/**
 *
 * @author patriciamacedo
 */
public interface Observer {
    /**
     * When a observer is notified execute this function
     * @param obj - argument of the method
     */
    void update(Object obj);

}

