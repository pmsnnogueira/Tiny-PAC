package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.utils.Direction;

public interface IState {

    State getState();

    boolean changeDirection(Direction direction);

    void evolve(IGameEngine gameEngine, long currentTime);

    boolean pause();

    boolean resume();





}
