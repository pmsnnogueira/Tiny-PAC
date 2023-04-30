package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Maze;

public interface IState {

    State getState();

    boolean startGame();            //Isto vou ter de tirar

    //WaitToMovePacman
    char[][] showMaze();        //Retirar

    //GAME
    IState touchGhost();        //Passar os ghosts
    IState eatFood();           //Passar a food

    //VulnerableGhosts
    IState releaseGhosts();


    //LevelComplete
    IState newLevel();



    //PAUSE
    IState pauseGame();
    IState resumeGame();
    IState exitGame();
    IState saveGame();
}
