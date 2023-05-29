package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.utils.Direction;
import pt.isec.pa.tinypac.utils.ProgramManager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelManager implements IGameEngineEvolve {

    public static final String PROP_MENU = "_menu_";
    public static final String PROP_DATA = "_data_";

    private GameEngine gameEngine;
    private Context context;

    private ProgramManager programManager;
    private PropertyChangeSupport pcs;

    public ModelManager(){
        this.gameEngine = new GameEngine();
        //this.context = new Context();
        this.context = null;
        this.gameEngine.registerClient(this);

        this.programManager = ProgramManager.MAIN_MENU;
        this.pcs = new PropertyChangeSupport(this);
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

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }


    public ProgramManager getProgramState(){
        return programManager;
    }

    public void changeToTop5(){
        this.programManager = ProgramManager.TOP5;
        pcs.firePropertyChange(PROP_MENU,null,null);
    }

    public void changeToMainMenu(){
        this.programManager = ProgramManager.MAIN_MENU;
        pcs.firePropertyChange(PROP_MENU,null,null);
    }

}