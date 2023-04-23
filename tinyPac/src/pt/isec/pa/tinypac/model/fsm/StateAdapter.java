package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.data.Maze;

public abstract class StateAdapter implements IState {

    protected Context context;
    protected GameManager data;

    protected StateAdapter(Context context, GameManager data){
        this.context = context;
        this.data = data;
    }

    protected void changeState(State newState){
        newState.createState(context, data);
    }

    @Override
    public Maze maze() {
        return null;
    }

    @Override
    public boolean startGame() {
        return false;
    }

    @Override
    public IState move() {
        return null;
    }

    @Override
    public IState touchGhost() {
        return null;
    }

    @Override
    public IState eatFood() {
        return null;
    }

    @Override
    public IState releaseGhosts() {
        return null;
    }

    @Override
    public IState newLevel() {
        return null;
    }

    @Override
    public IState pauseGame() {
        return null;
    }

    @Override
    public IState resumeGame() {
        return null;
    }

    @Override
    public IState exitGame() {
        return null;
    }

    @Override
    public IState saveGame() {
        return null;
    }
}
