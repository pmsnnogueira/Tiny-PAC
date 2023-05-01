package utils;

import pt.isec.pa.tinypac.model.data.IMazeElement;

public class Position {

    private int posX;
    private int posY;
    private int mazeHeigth;
    private int mazeWidth;

    private Direction direction;

    public Position(int posX , int posY, int mazeHeigth, int mazeWidth){
        this.posX = posX;
        this.posY = posY;
        this.mazeHeigth = mazeHeigth;
        this.mazeWidth = mazeWidth;
        this.direction = null;
    }

    public Position(Position position){
         this.posX = position.posX;
         this.posY = position.posY;
         this.mazeHeigth = position.mazeHeigth;
         this.mazeWidth = position.mazeWidth;
         this.direction = position.direction;
    }

    public int[] getNextPosition(){
        return nextPosition(this.direction);
    }

    private int[] nextPosition(Direction direction){

        int[] pos = new int[2];
        pos[0] = posX;
        pos[1] = posY;

        if(direction != null){
            if(direction == Direction.UP){
                System.out.println("UP");
                if(posY - 1 < mazeHeigth) {
                    pos[1] = posY - 1;
                }
            }else if(direction == Direction.DOWN){
                System.out.println("DOWN");
                if(posY + 1 < mazeHeigth) {
                    pos[1] = posY + 1;
                }
            } else if(direction == Direction.LEFT){
                System.out.println("LEFT");
                if(posX - 1 < mazeWidth) {
                    pos[0] = posX - 1;
                }
            }else if(direction == Direction.RIGHT){
                System.out.println("RIGHT");
                if(posX + 1 < mazeWidth) {
                    pos[0] = posX + 1;
                }
            }
        }

        return pos;
    }

    public void setPos(int posX , int posY){
        this.posX = posX;
        this.posY = posY;
    }



    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }
}
