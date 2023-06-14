package pt.isec.pa.tinypac.model.data;

import java.io.Serializable;

public abstract class GameObjects implements IMazeElement , Serializable {
    protected Game game;

    protected GameObjects(Game game){
        this.game = game;
    }

    abstract public boolean evolve();

    abstract public void returnToBase();
}
