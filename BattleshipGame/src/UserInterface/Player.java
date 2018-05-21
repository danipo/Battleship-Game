/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import core.Submarine;
import core.PatrolBoat;
import core.Destroyer;
import core.Carrier;
import core.Battleship;
import core.Constants;
import core.Ship;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author danie
 */
public class Player {

    private String userName;
    private JButton[][] buttonBoard;
    private final static int rows = 10;
    private final static int cols = 10;
    
    private ArrayList<Ship> ships;
    private Battleship battleship;
    private Carrier carrier;
    private Destroyer destroyer;
    private PatrolBoat patrolBoat;
    private Submarine submarine;
    
    //selection variables
    private Color shipColor;
    private boolean isFirst;
    private int currentShip;
    private int currentShipLength;
    private int currentDirection;
    private int playMode;
    
    private BattleshipUI parent;
    
    //actionListeners
    private static BoardListener boardListener;
    
    public Player(String name, BattleshipUI inParent)
    {   
        parent = inParent;
        userName = name;
        initObjects();
        initComponents();  
    }
    
    private void initObjects()
    {
        ships = new ArrayList<Ship>();

        carrier = new Carrier();
        ships.add(Constants.CARRIER, carrier);
        battleship = new Battleship();
        ships.add(Constants.BATTLESHIP, battleship);
        submarine = new Submarine();
        ships.add(Constants.SUBMARINE, submarine);
        destroyer = new Destroyer();
        ships.add(Constants.DESTROYER, destroyer);
        patrolBoat = new PatrolBoat();
        ships.add(Constants.PATROLBOAT, patrolBoat);
        
        setBoardListener(new BoardListener());
        
    }
    
    private void initComponents()
    {
        buttonBoard = new JButton[10][10];
        
        for(int row = 0; row < 10 ; row++)
        {
            for(int col = 0; col < 10; col++)
            {
                buttonBoard[row][col] = new JButton();
                buttonBoard[row][col].putClientProperty("row", row);       //takes the current row and col and puts them as a "property" of the button 
                buttonBoard[row][col].putClientProperty("col", col);
                buttonBoard[row][col].addActionListener(boardListener);
            }
        }
        
    }
    
    private boolean isValid(int row, int col)
    {
        switch(getCurrentDirection())
        {
            case Constants.VERTICAL:
            {
                if(row + getCurrentShipLength() > 10)
                {
                    //System.out.println("Not a valid move");
                    return false;
                }
                    
                break;
            }
            case Constants.HORIZONTAL:
            {
                //System.out.println("Move attempted: " + "col: " + col + ", Length: " + getCurrentShipLength());
                if(col + getCurrentShipLength() > 10){
                    //System.out.println(col+getCurrentShipLength() + ", Not a valid move");
                    return false;
                }
                    
                break;
            }
        }
        return true;
    }
    
    private boolean isOccupied(int rowClick, int colClick)
    {
        switch(getCurrentDirection())
        {
            case Constants.VERTICAL:
            {
               for(int r = rowClick; r < (rowClick + getCurrentShipLength()); r++)
               {
                   if(buttonBoard[r][colClick].getBackground() == getShipColor())
                        return true;
               }
                break;
            }
            case Constants.HORIZONTAL:
            {
               for(int c = colClick; c < (colClick + getCurrentShipLength()); c++)
               {
                   if(buttonBoard[rowClick][c].getBackground() == getShipColor())
                        return true;
               }
                break;
            }
        }
        return false;
    }
    
    private void placeShip(int rowClick, int colClick)
    {
        if(getCurrentShip() == Constants.BATTLESHIP && getBattleShip().isShipPlaced())
        {
            removeShip(getBattleShip());
        }
        else if(getCurrentShip() == Constants.DESTROYER && getDestroyer().isShipPlaced())
        {
            removeShip(getDestroyer());
        }
        else if(getCurrentShip() == Constants.SUBMARINE && getSubmarine().isShipPlaced())
        {
            removeShip(getSubmarine());
        }
        else if(getCurrentShip() == Constants.CARRIER && getCarrier().isShipPlaced())
        {
            removeShip(getCarrier());
        }
        else if(getCurrentShip() == Constants.PATROLBOAT && getPatrolBoat().isShipPlaced())
        {
            removeShip(getPatrolBoat());
        }
        
        switch(getCurrentDirection())
        {
            case Constants.VERTICAL:
            {
                for(int r = rowClick; r < (rowClick + getCurrentShipLength()); r++)
                {    
                    buttonBoard[r][colClick].setBackground(getShipColor());
                }
                break;
            }
            case Constants.HORIZONTAL:
            {
                for(int c = colClick; c < (colClick + getCurrentShipLength()); c++)
                {
                    buttonBoard[rowClick][c].setBackground(getShipColor());
                }   
            }
        }
        
        if(getCurrentShip() == Constants.BATTLESHIP)
        {
            getBattleShip().setShipDirection(getCurrentDirection());
            getBattleShip().setShipLocation(rowClick,colClick);
            getBattleShip().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.DESTROYER)
        {
            getDestroyer().setShipDirection(getCurrentDirection());
            getDestroyer().setShipLocation(rowClick,colClick);
            getDestroyer().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.SUBMARINE)
        {
            getSubmarine().setShipDirection(getCurrentDirection());
            getSubmarine().setShipLocation(rowClick,colClick);
            getSubmarine().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.CARRIER)
        {
            getCarrier().setShipDirection(getCurrentDirection());
            getCarrier().setShipLocation(rowClick,colClick);
            getCarrier().setShipPlaced(true);
        }
        else if(getCurrentShip() == Constants.PATROLBOAT)
        {
            getPatrolBoat().setShipDirection(getCurrentDirection());
            getPatrolBoat().setShipLocation(rowClick,colClick);
            getPatrolBoat().setShipPlaced(true);
        }
        
        if(getUserName().equals("Player One"))
            isReadyToDeploy();
                   
    }
    
    public void isReadyToDeploy()
    {
        //check if each ship is placed on the board
        if(getBattleShip().isShipPlaced() == true &&
           getCarrier().isShipPlaced() == true &&
           getSubmarine().isShipPlaced() == true &&
           getDestroyer().isShipPlaced() == true &&
           getPatrolBoat().isShipPlaced() == true)
        {
           parent.setDeployEnabled(true);
        }
        
    }
        
    private void removeShip(Ship inShip)
    {
        switch(inShip.getShipDirection())
        {
            case Constants.HORIZONTAL:
            {
                for(int col = inShip.getShipStartCol(); col < (inShip.getShipStartCol() + inShip.getShipLength()); col++)
                {
                    buttonBoard[inShip.getShipStartRow()][col].setBackground(null);
                    //System.out.println(inShip.getShipStartRow());
                }
                break;
            }
            
            case Constants.VERTICAL:
            {
                for (int row = inShip.getShipStartRow(); row < (inShip.getShipStartRow() + inShip.getShipLength()); row++)
                {
                    buttonBoard[row][inShip.getShipStartCol()].setBackground(null);
                }
                break;
            }
            
        }
        inShip.setShipPlaced(false);
    }
    
    public ArrayList<Ship> getShips()
    {
        return ships;
    }
    
    public void autoLayout()
    {
        Random random = new Random();
        
        for(int ship = 0; ship < getShips().size(); ship++)
        {
            setCurrentShip(ship);
            setCurrentDirection(random.nextInt(2));
            
            int rowClick = random.nextInt(getRows());
            int colClick = random.nextInt(getCols());
            
            while(!getShips().get(ship).isShipPlaced())
            {
                if(isValid(rowClick,colClick) && !isOccupied(rowClick, colClick))
                {
                    placeShip(rowClick, colClick);
                    getShips().get(ship).setShipPlaced(true);
                }
                else
                {
                    rowClick = random.nextInt(getRows());
                    colClick = random.nextInt(getCols());
                    setCurrentDirection(random.nextInt(2));
                }
            }
        }
    }
    
    public boolean isHit( int rowClick , int colClick)
    {
        
        for(Ship ship: ships)
        {
            for(int row = ship.getShipStartRow(); row <= ship.getShipStopRow(); row++)
            {
                for(int col = ship.getShipStartCol(); col <= ship.getShipStopCol(); col++)
                {
                    if(row == rowClick && col == colClick)
                    {
                        int hits = ship.getHitsLeft();
                        //System.out.println(hits + " hits");
                        ship.setHitsLeft(ship.getHitsLeft()-1);
                        //System.out.println(ship.getShipName()+ " has "+ ship.getHitsLeft()+ " hits left");
                        if(ship.getHitsLeft() == 0)
                        {
                            ship.setShipSunk(true);
                            parent.updateTextArea(ship.getShipName() + " has been sunk!");
                        }
                        
                        return true;
                    }
                }
            }
        }
        
        return false;
    }
    
    private class BoardListener implements ActionListener
        {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //if current ship fits
                //move a ship that has already been placed
                if(ae.getSource() instanceof JButton)
                {
                    //if this is a JButton, get the row and column
                    JButton button = (JButton)ae.getSource();
                    int rowClick = (int)button.getClientProperty("row");
                    int colClick = (int)button.getClientProperty("col");
                    //System.out.println(rowClick + " , " + colClick);
                
                    //checks if ship will fit
                    if(!isValid(rowClick,colClick))
                    {
                        JOptionPane.showMessageDialog(null,"Ship Will Not Fit", "Try Again", JOptionPane.ERROR_MESSAGE);
                    }
                    //&& Current ship doesn't touch another ship
                    else if(isOccupied(rowClick,colClick))
                    {
                        JOptionPane.showMessageDialog(null,"Cannot place here. Space is Occupied", "Try Again", JOptionPane.ERROR_MESSAGE);
                    }
                    //place the ship
                    else
                        placeShip(rowClick,colClick);
                        //System.out.println("Ship placed");
                }
            }
    
    }
    
    public void removeListener(JButton button, ActionListener listener)
    {
        button.removeActionListener(listener);
    }
    
    public void setListener(JButton button, ActionListener listener)
    {
        button.addActionListener(listener);
    }
    
    public JButton[][] getBoard()
    {
        return buttonBoard;
    }

    /**
     * @return the playMode
     */
    public int getPlayMode() {
        return playMode;
    }

    /**
     * @param playMode the playMode to set
     */
    public void setPlayMode(int playMode) {
        this.playMode = playMode;
    }
    
        /**
     * @return the boardListener
     */
    public BoardListener getBoardListener() {
        return boardListener;
    }

    /**
     * @param aBoardListener the boardListener to set
     */
    public void setBoardListener(BoardListener aBoardListener) {
        boardListener = aBoardListener;
    }

    /**
     * @return the rows
     */
    public static int getRows() {
        return rows;
    }

    /**
     * @return the cols
     */
    public static int getCols() {
        return cols;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param ships the ships to set
     */
    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    /**
     * @return the battleship
     */
    public Battleship getBattleShip() {
        return battleship;
    }

    /**
     * @param battleship the battleship to set
     */
    public void setBattleShip(Battleship battleship) {
        this.battleship = battleship;
    }

    /**
     * @return the carrier
     */
    public Carrier getCarrier() {
        return carrier;
    }

    /**
     * @param carrier the carrier to set
     */
    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    /**
     * @return the destroyer
     */
    public Destroyer getDestroyer() {
        return destroyer;
    }

    /**
     * @param destroyer the destroyer to set
     */
    public void setDestroyer(Destroyer destroyer) {
        this.destroyer = destroyer;
    }

    /**
     * @return the patrolBoat
     */
    public PatrolBoat getPatrolBoat() {
        return patrolBoat;
    }

    /**
     * @param patrolBoat the patrolBoat to set
     */
    public void setPatrolBoat(PatrolBoat patrolBoat) {
        this.patrolBoat = patrolBoat;
    }

    /**
     * @return the submarine
     */
    public Submarine getSubmarine() {
        return submarine;
    }

    /**
     * @param submarine the submarine to set
     */
    public void setSubmarine(Submarine submarine) {
        this.submarine = submarine;
    }

    /**
     * @return the shipColor
     */
    public Color getShipColor() {
        return shipColor;
    }

    /**
     * @param shipColor the shipColor to set
     */
    public void setShipColor(Color shipColor) {
        this.shipColor = shipColor;
    }

    /**
     * @return the isFirst
     */
    public boolean isIsFirst() {
        return isFirst;
    }

    /**
     * @param isFirst the isFirst to set
     */
    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    /**
     * @return the currentShip
     */
    public int getCurrentShip() {
        return currentShip;
    }

    /**
     * @param currentShip the currentShip to set
     */
    public void setCurrentShip(int currentShip) {
        this.currentShip = currentShip;
        
        if(currentShip == Constants.BATTLESHIP)
            setCurrentShipLength(Constants.BATTLESHIP_LENGTH);
        else if(currentShip == Constants.CARRIER)
            setCurrentShipLength(Constants.CARRIER_LENGTH);
        else if(currentShip == Constants.SUBMARINE)
            setCurrentShipLength(Constants.SUBMARINE_LENGTH);
        else if(currentShip == Constants.DESTROYER)
            setCurrentShipLength(Constants.DESTROYER_LENGTH);
        else if(currentShip == Constants.PATROLBOAT)
            setCurrentShipLength(Constants.PATROLBOAT_LENGTH);
    }

    /**
     * @return the currentShipLength
     */
    public int getCurrentShipLength() {
        return currentShipLength;
    }

    /**
     * @param currentShipLength the currentShipLength to set
     */
    public void setCurrentShipLength(int currentShipLength) {
        this.currentShipLength = currentShipLength;
    }

    /**
     * @return the currentDirection
     */
    public int getCurrentDirection() {
        return currentDirection;
    }

    /**
     * @param currentDirection the currentDirection to set
     */
    public void setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
    }
    
}
