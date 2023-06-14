package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.utils.Direction;

public interface IState {

    State getState();

    boolean changeDirection(Direction direction);

    boolean evolve(long currentTime);

    boolean pause();

    boolean resume();
}
