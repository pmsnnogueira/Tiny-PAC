package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.GameManager;
import pt.isec.pa.tinypac.model.data.Top5;
import pt.isec.pa.tinypac.model.fsm.Context;
import pt.isec.pa.tinypac.model.fsm.State;
import pt.isec.pa.tinypac.model.fsm.StateAdapter;
import pt.isec.pa.tinypac.ui.gui.resources.SoundManager;

public class GameOverState extends StateAdapter {

    public GameOverState(Context context, GameManager data){
        super(context, data);
        SoundManager.play("pacman_death.mp3");
    }

    @Override
    public State getState() {
        return State.GameOver;
    }
}
