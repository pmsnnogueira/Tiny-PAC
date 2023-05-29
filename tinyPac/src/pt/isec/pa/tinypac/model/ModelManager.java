package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.utils.Direction;

public class ModelManager implements IGameEngineEvolve {

    private GameEngine gameEngine;
    private Context context;

    public ModelManager(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        this.context = new Context();
        this.gameEngine.registerClient(this);
    }


    public State getState(){
        return context.getState();
    }

    public boolean changeDirection(Direction direction){
        if(context.changeDirection(direction)) {
            System.out.println("New Direction: " + direction.toString());
            return true;
        }
        return false;
    }

    public boolean pause(){
        return context.pause(gameEngine.getInterval());
    }
    public boolean resume(){
        return context.resume();
    }

    public char[][] showMaze(){
        return context.showMaze();
    }

    public String showGameInfo(){
        return context.showGameInfo();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {
        context.evolve(currentTime);
    }
}