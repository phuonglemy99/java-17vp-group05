package Battle_Ship.Ship;
import Battle_Ship.Board.*;

import java.util.ArrayList;

public abstract class Ship {
    protected ArrayList<Tile> tileOfShip = new ArrayList<>();
    public enum direction{Right, Down};
    public enum type{Carrier, Battle, Submarine, Patrol, Destroyer, None};
    protected int length;

    public ArrayList<Tile> getTileOfShip() {
        return tileOfShip;
    }

    //Preparing
    public static Ship addShip(Tile clickTile, direction direction, type typeOfShip, Tile[][] board) {
        Ship ship = factoryShip(typeOfShip);;
        int x = clickTile.getCoordinateX();
        int y = clickTile.getCoordinateY();

        for (int index = 0; index < ship.length; index++){
            if(direction == direction.Down){
                Tile tileDown = new Tile(x ,y + index,true, typeOfShip);
                ship.tileOfShip.add(tileDown);
                board[x][y + index] = tileDown;
            }
            if (direction == direction.Right){
                Tile tileRight = new Tile(x+index, y,true,typeOfShip);
                ship.tileOfShip.add(tileRight);
                board[x + index][y] = tileRight;
            }
        }

        return ship;
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



