package Battle_Ship.Ship;
import Battle_Ship.Board.*;

import java.util.ArrayList;

public class Ship {
    public ArrayList<Tile> tileOfShip;
    public enum direction{Right, Down};
    public enum type{Carrier, Battle, Submarine, Patrol, Destroyer, None};
    protected int length;

    public ArrayList<Tile> getTileOfShip() {
        return tileOfShip;
    }

    public Ship(){
        tileOfShip = new ArrayList<>();
    }

    //Preparing
    public static Ship addShip(Tile clickTile, direction direction, type typeOfShip, Tile[][] board) {
        Ship ship = factoryShip(typeOfShip);;
        int x = clickTile.getRow();
        int y = clickTile.getCol();

        for (int index = 0; index < ship.length; index++){
            if(direction == direction.Down){
                Tile tileDown = new Tile(x + index,y,true, typeOfShip);
                ship.tileOfShip.add(tileDown);
                board[x + index][y] = tileDown;
            }
            if (direction == direction.Right){
                Tile tileRight = new Tile(x, y + index,true,typeOfShip);
                ship.tileOfShip.add(tileRight);
                board[x][y + index] = tileRight;
            }
        }
        return ship;
    }

    public void showTile(){
        for (Tile tileShip : tileOfShip){
            System.out.println(tileShip.getName());
        }
    }

    // Know type of Ship
    private static Ship factoryShip(type typeOfShip) {
        switch (typeOfShip){
            case Carrier:
                return new CarrierShip();
            case Battle:
                return new BattleShip();
            case Destroyer:
                return new Destroyer();
            case Submarine:
                return new Submarine();
            case Patrol:
                return new PatrolBoat();
        }
        return null;
    }

    //Playing

    public boolean isDefeatedShip(){
        return tileOfShip.isEmpty();
    }

    public boolean vanishingShip(Tile clickTile, type typeOfShip){
        if (clickTile.getName() != typeOfShip){
            System.out.println("Vanishing. Another type of ship is here.");
            return false;
        }
        return true;
    }
}



