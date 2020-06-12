package Battle_Ship.GUI;

import Battle_Ship.Board.Board;
import Battle_Ship.Ship.Ship;
import Battle_Ship.SocketPlayer;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import static Battle_Ship.GUI.PlayingBoard.jfrm;
import static Battle_Ship.Ship.Ship.type.None;
import static java.awt.Color.decode;

public class BoardGUI extends JPanel {
    private Board board = new Board();
    private TilePane[][] tilePanes = new TilePane[10][10];
    private int click_row, click_col;
    private Ship.type flag = None; // type of ship player choose by click button
    private ImageIcon iconSea;
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private int status = 0; // 0: prepare ; 1: play
    private ArrayList<ArrayList<Integer>> shipTiles = new ArrayList<>(); // TODO: array of tiles beginning of ship
                                                                        // {{row, column, direction},...} (Right : 1, Down:0)
    public void setChooseType(boolean isChooseType) {
        setTypeChoose(isChooseType);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Ship.type getFlag() {
        return flag;
    }

    public void setFlag(Ship.type flag) {
        this.flag = flag;
    }

    private int factoryShip(){
        switch(flag){
            case Carrier:
                return 5;
            case Battle:
                return 4;
            case Submarine:
                return 3;
            case Patrol:
                return 2;
            case Destroyer:
                return 2;
            default:
                return -1;
        }
    }

    public BoardGUI(int length, int status) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        this.status = status;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                gbc.gridx = col;
                gbc.gridy = row;

                TilePane tileGUI = new TilePane(length/10, row , col, status);
                Border border = null;
                if (row < 9) {
                    if (col < 9) {
                        border = new MatteBorder(1, 1, 0, 0, Color.GRAY);
                    } else {
                        //cells in final column of grid (right) exept the final cell of the grid
                        border = new MatteBorder(1, 1, 0, 1, Color.GRAY);
                    }
                } else {
                    if (col < 9) {
                        //cells in final rows except the final cell of the grid
                        border = new MatteBorder(1, 1, 1, 0, Color.GRAY);
                    } else {
                        //final cell
                        border = new MatteBorder(1, 1, 1, 1, Color.GRAY);
                    }
                }

                // board vs tile
                tileGUI.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        click_col = tileGUI.getColumn();
                        click_row = tileGUI.getRow();
                        if(status == 0){
                            try{
                                if(!isCrash("Right")) {
                                    for (int index = click_col; index < click_col + factoryShip(); index++) {
                                        tilePanes[click_row][index].setBackground(Color.CYAN);
                                        tilePanes[click_row][index].setTypeTile(flag);
                                    }
                                    addShip("Right");
                                    //board.placeShip(board.getTile(click_row, click_col), flag, Ship.direction.Right);
                                }
                            } catch (IndexOutOfBoundsException err){
                                //err.printStackTrace();
                                execError("Right");
                            }
                        }
                        else{
                            // System.out.println(board.getTile(click_row,click_col).getName());
                            if (tilePanes[click_row][click_col].getIsClicked() == 2){
                                //JOptionPane.showMessageDialog(jfrm,  "This tile has been clicked!", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                SocketPlayer.getInstance().emit("fire", new Object[] {String.valueOf(click_row), String.valueOf(click_col)});
                                PlayingBoard.waitPlayerTurn.makeWait("Waiting for Enemy turn", jfrm);
                            }
                        }
                    }
                });

                tilePanes[row][col] = tileGUI;
                tileGUI.setBorder(border);
                add(tileGUI, gbc);

                // Change Direction of ship : vertical or horizontal
                getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), "Move_down");
                getActionMap().put("Move_down", new MoveAction("Down"));

                getInputMap(IFW).put(KeyStroke.getKeyStroke("RIGHT"), "Move_right");
                getActionMap().put("Move_right", new MoveAction("Right"));

                setFocusable(true);
                requestFocusInWindow();
                //iconSea = new ImageIcon(".\\tile.png");
            }
        }
    }

    public Board.resultFire findShip(int row, int col){
        return board.findShip(board.getTile(row, col));
    }

    //-----------------------------------------------Prepare------------------------------------
    public void copyDetail(BoardGUI boardGUI){
        board.copyBoard(boardGUI.board);
    }

    // Error out of Array and crash execute
    private void execError(String direction){
        if (status != 1){
            JOptionPane.showMessageDialog(BoardGUI.this,
                    "Invalid position!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        tilePanes[click_row][click_col].setFlag_error(true);
        if (direction.equals("Right")){
            resetColInvalid();
        }

        if (direction.equals("Down")){
            resetRowInvalid();
        }
    }

    public void saveShip(){
        for (int indexRow = 0; indexRow < 10; indexRow++){
            for(int indexColumn = 0; indexColumn < 10; indexColumn++){
                if (tilePanes[indexRow][indexColumn].getBackground().equals(Color.CYAN)){
                    board.setTilesType(indexRow, indexColumn, tilePanes[indexRow][indexColumn].getTypeTile());
                    board.getTile(indexRow, indexColumn).setName(tilePanes[indexRow][indexColumn].getTypeTile());
                }
                // System.out.print(board.getTilesType(indexRow, indexColumn) + " ");
            }
            // System.out.println();
        }

        for(ArrayList<Integer> ship : shipTiles){
            if(ship.get(2) == 1){
                board.placeShip(board.getTile(ship.get(0), ship.get(1)), board.getTile(ship.get(0), ship.get(1)).getName(), Ship.direction.Right);
            } else{
                board.placeShip(board.getTile(ship.get(0), ship.get(1)), board.getTile(ship.get(0), ship.get(1)).getName(), Ship.direction.Down);
            }
        }
        //System.out.println(ships);
    }

    public void showTypeShip(){
        for (int indexRow = 0; indexRow < 10; indexRow++){
            for(int indexColumn = 0; indexColumn < 10; indexColumn++){
                System.out.print(board.getTilesType(indexRow, indexColumn) + " ");
            }
            System.out.println();
        }
    }

    private boolean isCrash(String direction){
        boolean crash = false;
        try{
        if (direction.equals("Down")){
            for (int row = click_row + 1; row < click_row + factoryShip(); row++){
                if(tilePanes[row][click_col].getTypeTile() != None){
                    tilePanes[click_row][click_col].setFlag_error(true);
                    tilePanes[row][click_col].setChoosen(true);
                    crash = true;
                }
            }
        } else{
            for (int col = click_col + 1; col < click_col + factoryShip(); ++col){
                if(tilePanes[click_row][col].getTypeTile() != None){
                    tilePanes[click_row][click_col].setFlag_error(true);
                    tilePanes[click_row][col].setChoosen(true);
                    crash = true;
                }
            }
        }
        if(crash)
        {
            JOptionPane.showMessageDialog(BoardGUI.this,
                    "Crash! Please choose another position!", "Error", JOptionPane.ERROR_MESSAGE);
            return true;
        }} catch(ArrayIndexOutOfBoundsException err){
            return false;
        }
        return false;
    }

    private class MoveAction extends AbstractAction{
        private String direction = "";

        MoveAction(String direction){
            this.direction = direction;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearShip(direction);
            removeShip();
            //System.out.println(direction);
            switch (direction) {
                case "Down":
                    try{
                        if(!isCrash("Down")){
                            for (int index = click_row; index < click_row + factoryShip(); index++) {
                                tilePanes[index][click_col].setBackground(Color.CYAN);
                                tilePanes[index][click_col].setTypeTile(flag);
                            }
                            addShip("Down");
                        }} catch (IndexOutOfBoundsException err){
                            //err.printStackTrace();
                            execError("Down");
                    }
                    break;
                case "Right":
                    try{
                        if (!isCrash("Right")){
                            for (int index = click_col; index <  click_col + factoryShip(); index++){
                                tilePanes[click_row][index].setBackground(Color.CYAN);
                                tilePanes[click_row][index].setTypeTile(flag);
                            }
                            addShip("Right");
                    }} catch (IndexOutOfBoundsException err){
                        execError("Right");
                    }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(new ImageIcon(String.valueOf(iconSea)).getImage(), 0, 0, null);
    }

    public void setTypeChoose(boolean isChooseType) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                tilePanes[row][col].setTypeChoosen(isChooseType);
            }
        }
    }

    private void resetRowInvalid(){
        try {
            for (int index = click_row; index < click_row + factoryShip(); index++) {
                if (!tilePanes[index][click_col].isChoosen()) {
                    tilePanes[index][click_col].setBackground(Color.decode("#002966"));
                    tilePanes[index][click_col].setTypeTile(None);
                }else{
                    tilePanes[index][click_col].setChoosen(false);
                }
            }
        }catch(ArrayIndexOutOfBoundsException err){
            for (int row = click_row; row < 10 - click_row; ++row){
                tilePanes[row][click_col].setTypeTile(None);
            }
        }
    }

    private void resetColInvalid(){
        // reset the tile to the first status
        try {
            for (int index = click_col; index < click_col + factoryShip(); index++) {
                if (!tilePanes[click_row][index].isChoosen()) {
                    tilePanes[click_row][index].setBackground(Color.decode("#002966"));
                    tilePanes[click_row][index].setTypeTile(None);
                }
                else{
                    tilePanes[click_row][index].setChoosen(false);
                }
            }
        }catch (ArrayIndexOutOfBoundsException err){
            for(int col = click_col; col < 10 - click_col; ++col){
                tilePanes[click_row][col].setTypeTile(None);
            }
        }
    }

    public void clearShip(String direction){
        switch (direction) {
            case "Down":
                resetColInvalid();
                try {
                    for (int col = click_col + 1; col < click_col + factoryShip(); ++col) {
                        if (tilePanes[click_row][col].isChoosen()) {
                            tilePanes[click_row][col].setChoosen(false);
                        } else {
                            tilePanes[click_row][col].setTypeTile(None);
                        }
                    }
                }catch (ArrayIndexOutOfBoundsException err){
                    for (int col = click_col; col < 10 - click_col; ++col){
                        tilePanes[click_row][col].setTypeTile(None);
                    }
                }
                break;
            case "Right":
                resetRowInvalid();
                try {
                    for (int row = click_row + 1; row < click_row + factoryShip(); ++row) {
                        if (tilePanes[row][click_col].isChoosen()) {
                            tilePanes[row][click_col].setChoosen(false);
                        } else {
                            tilePanes[row][click_col].setTypeTile(None);
                        }
                    }
                } catch(ArrayIndexOutOfBoundsException err){
                    for (int row = click_row; row < 10 - click_row; ++row){
                        tilePanes[row][click_col].setTypeTile(None);
                    }
                }
        }
    }

    public void addShip(String direction){
        ArrayList<Integer> ship = new ArrayList<>();
        ship.add(click_row);
        ship.add(click_col);
        if (direction.equals("Right")){
            ship.add(1);
        } else{
            ship.add(0);
        }
        shipTiles.add(ship);
    }

    public void removeShip(){
        shipTiles.remove(shipTiles.size()-1);
    }

    public static class TilePane extends JPanel{
        private Color defaultBackground;
        private int length;
        private int row, column;
        private boolean isTileType = false; // whether player choose type of ship
        private boolean flag_error = false; // to know whether tile is invalid position
        private Ship.type typeTile;
        private boolean isChoosen = false;  // whether ship exists here for crashing
        private int isClicked = 0; // 0: hasn't click, 1: click, 2: click more than one time

        public int getIsClicked() {
            return isClicked;
        }

        public void setIsClicked(int isClicked) {
            this.isClicked = isClicked;
        }

        public boolean isChoosen() {
            return isChoosen;
        }

        public void setChoosen(boolean choosen) {
            isChoosen = choosen;
        }

        public Ship.type getTypeTile() {
            return typeTile;
        }

        public void setTypeTile(Ship.type typeTile) {
            this.typeTile = typeTile;
        }

        public boolean getTypeChoosen() {
            return isTileType;
        }

        public void setTypeChoosen(boolean typeChoosen) {
            this.isTileType = typeChoosen;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public boolean getFlag_error() {
            return flag_error;
        }

        public void setFlag_error(boolean flag_error) {
            this.flag_error = flag_error;
        }

        public TilePane(int length, int row, int column, int status) {
            this.length = length;
            this.row = row;
            this.column = column;
            this.typeTile = None;
            setBackground(decode("#002966"));

            if (status != 2) {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        defaultBackground = getBackground();
                        setBackground(Color.CYAN);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (status == 0) {
                            if (isTileType) {
                                defaultBackground = getBackground();
                                setBackground(Color.CYAN);
                            }
                        } else{
                            if (isClicked >= 1){
                                isClicked = 2;
                            } else{
                                isClicked = 1;
                                setBackground(Color.RED);
                            }
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (status == 0) {
                            if (getFlag_error()) {
                                setBackground(decode("#002966"));
                                flag_error = false;
                            } else
                                setBackground(defaultBackground);
                        }
                        else {
                            if (isClicked != 0)
                                setBackground(Color.RED);
                            else
                                setBackground(decode("#002966"));
                        }
                    }
                });
            }


            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(typeTile == None && !isTileType && status == 0){ JOptionPane.showMessageDialog(TilePane.this,
                            "You must choose type of ship!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(length, length);
        }
    }

}
