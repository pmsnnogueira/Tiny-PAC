package pt.isec.pa.tinypac.model.data;

public abstract class Ghost implements IGhost{
    private Boolean lock;
    private Integer posX;
    private Integer posY;

    public Ghost(int posX , int posY){
        this.lock = true;
        this.posX = posX;
        this.posY = posY;
    }


    public Integer getPosX() {
        return posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public Boolean getLock() {
        return lock;
    }
    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }
    public void setLock(Boolean lock) {
        this.lock = lock;
    }
}