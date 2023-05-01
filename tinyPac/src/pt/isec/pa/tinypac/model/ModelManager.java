package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import utils.Direction;

public class ModelManager {

    private GameEngine gameEngine;

    private Context context;

    private GameManager gameManager;

    public ModelManager(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        this.gameManager = new GameManager();
        this.context = new Context(gameManager);
        gameEngine.registerClient(context.getGameManager());
    }

    private void loadGame(){
        gameManager.generateMapLevel();

    }


    public boolean start(){
        //Load dos ficheiros e carregamento do jogo
        loadGame();
        return true;
    }

    public boolean changeDirection(Direction direction){
        if(context.changeDirection(direction))
            return true;
        return false;
    }

    public char[][] showMaze(){
        return context.showMaze();
    }
}