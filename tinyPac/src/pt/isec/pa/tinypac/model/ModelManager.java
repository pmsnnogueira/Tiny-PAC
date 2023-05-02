package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.utils.Direction;

public class ModelManager{

    private GameEngine gameEngine;

    private Context context;

    private GameManager gameManager;

    public ModelManager(GameEngine gameEngine){
        this.gameEngine = gameEngine;
        this.gameManager = new GameManager();
        this.context = new Context(gameManager);
        this.gameEngine.registerClient(context);
        startLevel();
    }

    public State getState(){
        return context.getState();
    }
    private void loadGame(){
        gameManager.generateMapLevel();
    }

    public boolean startLevel(){
        //Load dos ficheiros e carregamento do jogo
        loadGame();
        return true;
    }

    public boolean changeDirection(Direction direction){
        if(context.changeDirection(direction)) {
            System.out.println("New Direction: " + direction.toString());
            return true;
        }
        return false;
    }

    public boolean pause(){
        return context.pause();
    }

    public char[][] showMaze(){
        return context.showMaze();
    }

    public String showGameInfo(){
        return gameManager.showGameInfo();
    }

}