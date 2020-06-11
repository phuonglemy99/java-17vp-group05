package Battle_Ship.Board;

import Battle_Ship.GUI.BoardGUI;
import Battle_Ship.Ship.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static Battle_Ship.Ship.Ship.addShip;


public class Board  {
    private Tile tiles[][] = new Battle_Ship.Board.Tile[10][10];
    private HashMap<Ship.type, ArrayList<Ship>> listShip = new HashMap<Ship.type, ArrayList<Ship>>();

    public enum resultFire {Miss, Hit, Defeat, Win};

    public Board(){
        for (int row = 0; row < 10; row++){
            for(int column = 0; column < 10; column++){
                Tile tile = new Battle_Ship.Board.Tile(row, column);
                tiles[row][column] = tile;
            }
        }
    }

    public void setTilesType(int row, int col, Ship.type typeShip){
        tiles[row][col].setName(typeShip);
    }

    public Ship.type getTilesType(int row, int col){
        return tiles[row][col].getName();
    }

    public Tile getTile(int row, int col){
        return tiles[row][col];
    }

    public void copyBoard(Board board){
        for (int row = 0; row < 10; row++){
            for(int column = 0; column < 10; column++){
                this.tiles[row][column] = board.tiles[row][column];
            }
        }
        listShip = (HashMap<Ship.type, ArrayList<Ship>>) board.listShip.clone();
    }

    public void resetBoard(){
        for (Battle_Ship.Board.Tile[] row : tiles){
            for (Battle_Ship.Board.Tile col : row){
                col.setFlagFire(false);
            }
        }
    }

    public boolean remainingShip(){
        for (Tile[] row : tiles){
            for (Tile col : row){
                if(col.getFlagFire()){
                    return false;
                }
            }
        }
        return true;
    }

    // Preparing

    public void placeShip(Tile clickTile, Ship.type typeOfShip, Ship.direction direction){
        Ship ship = addShip(clickTile, direction, typeOfShip, tiles);
        listShip.computeIfAbsent(typeOfShip, k -> new ArrayList<Ship>()).add(ship);
    }

    //Playing
    public resultFire findShip(Tile clickTile){
        int x = clickTile.getRow();
        int y = clickTile.getCol();

        if(tiles[x][y].isTileEmpty()){
            System.out.println("Miss");
            return resultFire.Miss;
        }

        Ship sh = checkTileTypeShip(x, y);
        shootATile(x, y);
        if(remainingShip()) {
            return resultFire.Win;
        }
        try{
            if(sh.isDefeatedShip()){
                return resultFire.Defeat;
            }
            return resultFire.Hit;
        } catch (NullPointerException err){
            return resultFire.Defeat;
        }
    }

    public void shootATile(int x, int y){
        for(Ship.type typeOfShip : listShip.keySet()) {
            if (tiles[x][y].getName() == typeOfShip) {
                for (Ship sh : listShip.get(typeOfShip)) {
                    if (sh.getTileOfShip().contains(tiles[x][y])){
                        sh.getTileOfShip().remove(tiles[x][y]);
                    }
                }
            }
            tiles[x][y].setFlagFire(false);
        }
    }


    public Ship checkTileTypeShip(int x, int y){
        for(Ship.type typeOfShip : listShip.keySet()){
            if(tiles[x][y].getName() == typeOfShip){
                for (Ship sh : listShip.get(typeOfShip)){
                    if (sh.getTileOfShip().contains(tiles[x][y])){
                        return sh;
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args){
        Board board = new Board();
        Tile tile = new Tile(0,0);
        Tile tile1 = new Tile(1,0);
        Tile tile2 = new Tile(0,1);
        board.placeShip(tile, Ship.type.Carrier, Ship.direction.Down);
        if(board.findShip(tile2) == resultFire.Hit)
            System.out.println("Hit");
    }
}
