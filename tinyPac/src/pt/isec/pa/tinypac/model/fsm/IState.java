package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Maze;

public interface IState {

    State getState();

    boolean startGame();

    //WaitToMovePacman
    IState move();

    //WaitToMovePacman
    Maze maze();

    //GAME
    IState touchGhost();
    IState eatFood();

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
