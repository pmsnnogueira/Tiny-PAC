package pt.isec.pa.tinypac.utils;

import java.io.Serial;
import java.io.Serializable;

public class PacmanPosition extends Position implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final int mazeHeigth;
    private final int mazeWidth;

    private Direction direction;

    public PacmanPosition(int posX , int posY, int mazeHeigth, int mazeWidth){
        super(posX,posY);
        this.mazeHeigth = mazeHeigth;
        this.mazeWidth = mazeWidth;
        this.direction = null;
    }

    public PacmanPosition(PacmanPosition pacmanPosition){
        super(pacmanPosition.getPosX(), pacmanPosition.getPosY());
        this.mazeHeigth = pacmanPosition.mazeHeigth;
        this.mazeWidth = pacmanPosition.mazeWidth;
        this.direction = pacmanPosition.direction;
    }

    public int[] getNextPosition(){
        return nextPosition(this.direction);
    }

    private int[] nextPosition(Direction direction){

        int[] pos = new int[2];
        pos[0] = getPosX();
        pos[1] = getPosY();

        if(direction != null){
            if(direction == Direction.UP){
                //System.out.println("UP");
                if(getPosY() - 1 < mazeHeigth) {
                    pos[1] = getPosY() - 1;
                }
            }else if(direction == Direction.DOWN){
                //System.out.println("DOWN");
                if(getPosY() + 1 < mazeHeigth) {
                    pos[1] = getPosY() + 1;
                }
            } else if(direction == Direction.LEFT){
                //System.out.println("LEFT");
                if(getPosX() - 1 < mazeWidth) {
                    pos[0] = getPosX() - 1;
                }
            }else if(direction == Direction.RIGHT){
                //System.out.println("RIGHT");
                if(getPosX() + 1 < mazeWidth) {
                    pos[0] = getPosX() + 1;
                }
            }
        }

        return pos;
    }

    public void setPos(int posX , int posY){
        this.setPosX(posX);
        this.setPosY(posY);
    }



    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
