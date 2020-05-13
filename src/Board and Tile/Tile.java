package Battle_Ship.Board;

import Battle_Ship.Ship.Ship;

import java.util.ArrayList;

public class Tile {
    private int coordinateX;
    private int coordinateY;
    private boolean flagFire = false; // true : the tile hasn't been fired
    private Ship.type name = Ship.type.None;


    public Tile(int coordinateX, int coordinateY){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    public Tile(int coordinateX, int coordinateY, boolean flag, Ship.type typeOfShip){
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.flagFire = flag;
        this.name = typeOfShip;
    }

    public Ship.type getName() {
        return name;
    }

    public void setName(Ship.type name) {
        this.name = name;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public boolean getFlagFire() {
        return flagFire;
    }

    public void setFlagFire(boolean flag) {
        this.flagFire = flag;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public boolean isTileEmpty(){
        if (flagFire)
            return false;
        return true;
    }
}
