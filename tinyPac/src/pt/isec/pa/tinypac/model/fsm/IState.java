package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Maze;

public interface IState {

    State getState();

    boolean startGame();

    //WaitToMovePacman
    IState evolve();

    //WaitToMovePacman
    char[][] showMaze();

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
