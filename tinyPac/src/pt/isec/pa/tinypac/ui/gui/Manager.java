package pt.isec.pa.tinypac.ui.gui;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.ModelManager;
import pt.isec.pa.tinypac.utils.ProgramManager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
public class Manager {

    public static final String PROP_MENU = "_menu_";
    //public static final String PROP_INGAME_MENU = "_game_";
    private ProgramManager programManager;
    private PropertyChangeSupport pcs;
    private ModelManager modelManager;

    private GameEngine gameEngine;

    public Manager(){
        this.programManager = ProgramManager.MAIN_MENU;
        this.pcs = new PropertyChangeSupport(this);
        this.modelManager = null;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }


    public ProgramManager getState(){
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

  /*  public void changeToGameMenu(){
        this.gameEngine = new GameEngine();
        //this.gameEngine.registerClient(gameEngine);
        this.modelManager = new ModelManager(gameEngine);
        this.programManager = ProgramManager.GAME;
        pcs.firePropertyChange(PROP_MENU, null,null);
    }

    public void changeMenus(){
        this.programManager = ProgramManager.MAIN_MENU;
        this.modelManager = null;
        pcs.firePropertyChange(PROP_MENU, null,null);
    }*/


}
