package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import utils.Direction;

public interface IState {

    State getState();

    boolean changeDirection(Direction direction);

    boolean evolve(IGameEngine gameEngine, long currentTime);

    boolean pause();

    boolean resume();





}
