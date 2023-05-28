package pt.isec.pa.tinypac.model.data;

public abstract class GameObjects implements IMazeElement{
    protected Game game;

    protected GameObjects(Game game){
        this.game = game;
    }

    abstract public boolean evolve();

    abstract public void returnToBase();
}
