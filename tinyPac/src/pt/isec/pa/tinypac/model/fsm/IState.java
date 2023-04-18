package pt.isec.pa.tinypac.model.fsm;

public interface IState {

    State getState();

    //WaitToMovePacman
    IState move();

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
