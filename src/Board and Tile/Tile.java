package Battle_Ship.Board;

import Battle_Ship.Ship.Ship;

import java.util.ArrayList;

public class Tile {
    private int row;
    private int col;
    private boolean flagFire = false; // true : the tile hasn't been fired
    private Ship.type name = Ship.type.None;


    public Tile(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Tile(int row, int col, boolean flag, Ship.type typeOfShip){
        this.row = row;
        this.col = col;
        this.flagFire = flag;
        this.name = typeOfShip;
    }

    public Ship.type getName() {
        return name;
    }

    public void setName(Ship.type name) {
        this.name = name;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public boolean getFlagFire() {
        return flagFire;
    }

    public void setFlagFire(boolean flag) {
        this.flagFire = flag;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isTileEmpty(){
        if (name == Ship.type.None) {
            return true;
        }
        return false;
    }
}
