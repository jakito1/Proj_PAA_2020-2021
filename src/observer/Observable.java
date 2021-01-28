package observer;

/**
 * @author patriciamacedo
 */
public interface Observable {
    /**
     * Attach  observers to the subject.
     *
     * @param observers to be attached
     */
    void addObservers(Observer... observers);

    /**
     * Attach  observers to the subject.
     *
     * @param observer to be removed
     */
    void removeObservers(Observer observer);

    /**
     * notify all observer
     *
     * @param object, argument of update method
     */
    void notifyObservers(Object object);
}
