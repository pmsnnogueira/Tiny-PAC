package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.utils.GhostPosition;
import pt.isec.pa.tinypac.utils.Position;

import java.util.ArrayList;

public abstract class Ghost extends GameObjects{
    private Boolean locked;
    private GhostPosition currentPosition;

    private GhostPosition initialPosition;
    private ArrayList<GhostPosition> positions;
    private static final char SYMBOL = 'G';

    public static final int GHOST_POINTS = 200;


    public Ghost(Game game, int posX , int posY){
        super(game);
        this.locked = true;
        this.initialPosition = new GhostPosition(posX,posY);
        this.currentPosition = new GhostPosition(posX,posY);
        this.positions = new ArrayList<>();
    }

    public void setPos(int posX , int posY){
        this.currentPosition.setPos(posX,posY);
    }

    public void reset(){
        this.locked = true;
        this.currentPosition = initialPosition;
    }

    public Integer getInitialPositionX() {
        return this.initialPosition.getPosX();
    }

    public Integer getInitialPositionY() {
        return this.initialPosition.getPosY();
    }

    public void addLastPosition(Integer posX, Integer posY){
        this.positions.add(new GhostPosition(posX, posY));
    }



    public Integer getPosX() {
        return this.currentPosition.getPosX();
    }

    public Integer getPosY() {
        return this.currentPosition.getPosY();
    }

    public Boolean getLocked() {
        return locked;
    }
    public void setPosX(Integer posX) {
        this.currentPosition.setPosX(posX);
    }

    public void setLocked(Boolean lock) {
        this.locked = lock;
    }

    @Override
    public char getSymbol() {
        return 0;
    }
}