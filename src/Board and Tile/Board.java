package Battle_Ship.Board;

import Battle_Ship.Ship.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Board  {
    private Tile board[][] = new Battle_Ship.Board.Tile[10][10];
    private Map<Ship.type, ArrayList<Ship>> listShip = new HashMap<Ship.type, ArrayList<Ship>>();
    enum resultFire {Miss, Hit, Defeat, Win};

    public Board(){
        for (int row = 0; row < 10; row++){
            for(int column = 0; column < 10; column++){
                Tile tile = new Battle_Ship.Board.Tile(row, column);
                board[row][column] = tile;
            }
        }
    }

    public void resetBoard(){
        for (Battle_Ship.Board.Tile[] row : board){
            for (Battle_Ship.Board.Tile col : row){
                col.setFlagFire(false);
            }
        }
    }

    public boolean remainingShip(){
        for (Tile[] row : board){
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
        Ship ship = Ship.addShip(clickTile, direction, typeOfShip, board);
        if(listShip.get(typeOfShip) == null){
            listShip.put(typeOfShip, new ArrayList<Ship>());
        }
        listShip.get(typeOfShip).add(ship);
    }

    //Playing
    public resultFire findShip(Tile clickTile){
        int x = clickTile.getCoordinateX();
        int y = clickTile.getCoordinateY();

        if(board[x][y].isTileEmpty()){
            return resultFire.Miss;
        }
        Ship sh = checkTileTypeShip(x, y);
        shootATile(x, y);
        if(sh.isDefeatedShip()){
            return resultFire.Defeat;
        }
        if(remainingShip())
            return resultFire.Win;
        return resultFire.Hit;
    }

    public void shootATile(int x, int y){

        for(Ship.type typeOfShip : listShip.keySet()) {
            if (board[x][y].getName() == typeOfShip) {
                for (Ship sh : listShip.get(typeOfShip)) {
                    if (sh.getTileOfShip().contains(board[x][y])){
                        sh.getTileOfShip().remove(board[x][y]);
                    }
                }
            }
            board[x][y].setFlagFire(false);
        }
    }

    public Ship checkTileTypeShip(int x, int y){
        for(Ship.type typeOfShip : listShip.keySet()){
            if(board[x][y].getName() == typeOfShip){
                for (Ship sh : listShip.get(typeOfShip)){
                    if (sh.getTileOfShip().contains(board[x][y])){
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
