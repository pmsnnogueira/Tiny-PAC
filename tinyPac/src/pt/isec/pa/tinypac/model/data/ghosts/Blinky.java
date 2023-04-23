package pt.isec.pa.tinypac.model.data.ghosts;

import pt.isec.pa.tinypac.model.data.Game;
import pt.isec.pa.tinypac.model.data.Ghost;
import pt.isec.pa.tinypac.model.data.IMazeElement;
import pt.isec.pa.tinypac.model.data.Maze;
import utils.Obstacles;

public class Blinky extends Ghost{


    public Blinky(Game game,int posX, int posY){
        super(game,posX, posY);
    }


    @Override
    public boolean evolve(){
        Maze maze = game.getMaze();
        IMazeElement front = maze.get(getPosY() - 1 ,getPosX());
        IMazeElement left = null;
        IMazeElement right = null;
        IMazeElement back = null;
        if(front == null)
            return false;

        if(front.getSymbol() != Obstacles.WALL.getSymbol()){
            setPos(getPosX(),getPosY() - 1);
            return true;
        }

        right = maze.get(getPosY(), getPosX() + 1);
        left = maze.get(getPosY(), getPosX() - 1);
        back = maze.get(getPosY() + 1, getPosX());
        //Verificar se vao ser nulls!!!!!


        if(left != null && right.getSymbol() == Obstacles.WALL.getSymbol() && left.getSymbol() != Obstacles.WALL.getSymbol()) {
            //GoLeft
            setPos(getPosX() - 1,getPosY());
            return true;
        }else if(right != null && right.getSymbol() != Obstacles.WALL.getSymbol() && left.getSymbol() == Obstacles.WALL.getSymbol()){
            //GoRight
            setPos(getPosX() - 1,getPosY());
            return true;
        }else if(back != null && back.getSymbol() != Obstacles.WALL.getSymbol() &&
                right.getSymbol() == Obstacles.WALL.getSymbol() && left.getSymbol() == Obstacles.WALL.getSymbol()){
            //GoBack
            setPos(getPosX(),getPosY() - 1);
            return true;
        }

        float random = (float)Math.random();
        if(random > 0.5 && left != null){
            //GoLeft
            setPos(getPosX() - 1, getPosY());
            return true;

        }else if(right != null){
            //GoRight
            setPos(getPosX() - 1,getPosY());
            return true;
        }

        System.out.println("\nposX: " + getPosX() + " posY: " + getPosY());
        System.out.println("\nposX: " + getPosX() + " posY: " + (getPosY() - 1));

        return false;
    }




    @Override
    public char getSymbol() {
        return 'B';
    }

}
