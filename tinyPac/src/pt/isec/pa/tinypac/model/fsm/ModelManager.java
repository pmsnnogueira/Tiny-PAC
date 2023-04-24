package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.data.GameManager;

public class ModelManager {

    private GameManager gameManager;

    private Context context;

    public ModelManager(GameManager gameManager){
        this.gameManager = gameManager;
        this.context = new Context();

    }

    public char[][] showMaze(){
        return context.showMaze();
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}
