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

    private static final Integer GAME_ENGINE_TIME = 250;

    private GameEngine gameEngine;
    private Context context;

    private ProgramManager programManager;
    private PropertyChangeSupport pcs;

    public ModelManager(){
        //this.gameEngine = new GameEngine();
        //this.context = new Context();
        this.context = null;
        //this.gameEngine.registerClient(this);

        this.programManager = ProgramManager.MAIN_MENU;
        this.pcs = new PropertyChangeSupport(this);
    }


    public State getState(){
        return context.getState();
    }

    public boolean changeDirection(Direction direction){
        if(context.changeDirection(direction)) {
            System.out.println("New Direction: " + direction.toString());
            pcs.firePropertyChange(PROP_DATA, null,null);
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

    public Integer getMazeRows(){
        return context.getMazeRows();
    }

    public Integer getMazeColumns(){
        return context.getMazeColumns();
    }
    public String showGameInfo(){
        return context.showGameInfo();
    }

    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {

        context.evolve(currentTime);
        pcs.firePropertyChange(PROP_DATA,null,null);
        pcs.firePropertyChange(PROP_MENU,null,null);
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

    public void changeToGame(){

        this.context = new Context();
        this.gameEngine = new GameEngine();
        this.gameEngine.registerClient(this);

        gameEngine.start(GAME_ENGINE_TIME);
        //gameEngine.waitForTheEnd();
        this.programManager = ProgramManager.GAME;
        pcs.firePropertyChange(PROP_MENU,null,null);
    }

    public void changeToMainMenu(){
        this.programManager = ProgramManager.MAIN_MENU;
        pcs.firePropertyChange(PROP_MENU,null,null);
    }

}