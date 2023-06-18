package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.utils.Direction;

/**
 * The StateAdapter class is an abstract implementation of the IState interface.
 * It provides default implementation for the methods in the IState interface,
 * allowing subclasses to override only the necessary methods.
 *
 * @author Pedro Nogueira
 * @version 1.0
 * @since 06/2023
 */
public abstract class StateAdapter implements IState {

    protected Context context;
    protected GameManager data;

    /**
     * Constructs a StateAdapter object with the specified context and gameManager
     * @param context The context object
     * @param data  The game manager object
     */
    protected StateAdapter(Context context, GameManager data){
        this.context = context;
        this.data = data;
    }

    /**
     * Changes the state to the specific newState
     * @param newState The new State to change To
     */
    protected void changeState(State newState){
        System.out.println("\n\tChanging State: [OldState:'" + context.getState() +"'] [newState: '" + newState+"']");
        context.changeState(newState.createState(context,data));
    }

    @Override
    public boolean changeDirection(Direction direction) {
        return false;
    }

    @Override
    public boolean evolve(long currentTime) {
        return false;
    }

    @Override
    public boolean pause() {
        return false;
    }

    @Override
    public boolean resume() {
        return false;
    }
}
