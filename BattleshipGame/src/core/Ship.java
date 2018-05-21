/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 *
 * @author danie
 */
public class Ship implements IShip{
    
    private boolean shipPlaced;
    private boolean shipSunk;
    
    int hitsLeft;
    int maxNumberOfHits;
    int shipDirection;
    int shipLength;
    
    private int shipStartRow;
    private int shipStopRow;
    private int shipStartCol;
    private int shipStopCol;
    private int shipLocation;
    
    String shipName;  

        /**
     * @return the shipLocation
     */
    public int getShipLocation() {
        return shipLocation;
    }

    /**
     * @param shipLocation the shipLocation to set
     */
    public void setShipLocation(int row, int col) {
        shipStartRow = row;
        shipStartCol = col;
        
        if (getShipDirection() == Constants.HORIZONTAL)
        {
            setShipStopRow(shipStartRow);
            setShipStopCol(shipStartCol + (getShipLength() - 1));
        }
        else if(getShipDirection() == Constants.VERTICAL)
        {
            setShipStopRow(shipStartRow + (getShipLength() - 1));
            setShipStopCol(shipStartCol);
        }
    }

    /**
     * @return the shipPlaced
     */
    public boolean isShipPlaced() {
        return shipPlaced;
    }

    /**
     * @param shipPlaced the shipPlaced to set
     */
    public void setShipPlaced(boolean shipPlaced) {
        this.shipPlaced = shipPlaced;
    }

    /**
     * @return the shipSunk
     */
    public boolean isShipSunk() {
        return shipSunk;
    }

    /**
     * @param shipSunk the shipSunk to set
     */
    public void setShipSunk(boolean shipSunk) {
        this.shipSunk = shipSunk;
    }

    /**
     * @return the maxNumberOfHits
     */
    public int getMaxNumberOfHits() {
        return maxNumberOfHits;
    }

    /**
     * @param maxNumberOfHits the maxNumberOfHits to set
     */
    public void setMaxNumberOfHits(int inHits) {
        maxNumberOfHits = inHits;
        hitsLeft = inHits;
    }

    /**
     * @return the shipDirection
     */
    public int getShipDirection() {
        return shipDirection;
    }

    /**
     * @param shipDirection the shipDirection to set
     */
    public void setShipDirection(int shipDirection) {
        this.shipDirection = shipDirection;
    }

    /**
     * @return the shipLength
     */
    public int getShipLength() {
        return shipLength;
    }

    /**
     * @param shipLength the shipLength to set
     */
    public void setShipLength(int shipLength) {
        this.shipLength = shipLength;
    }

    /**
     * @return the shipStartRow
     */
    public int getShipStartRow() {
        return shipStartRow;
    }

    /**
     * @param shipStartRow the shipStartRow to set
     */
    public void setShipStartRow(int shipStartRow) {
        this.shipStartRow = shipStartRow;
    }

    /**
     * @return the shipStopRow
     */
    public int getShipStopRow() {
        return shipStopRow;
    }

    /**
     * @param shipStopRow the shipStopRow to set
     */
    public void setShipStopRow(int shipStopRow) {
        this.shipStopRow = shipStopRow;
    }

    /**
     * @return the shipStartCol
     */
    public int getShipStartCol() {
        return shipStartCol;
    }

    /**
     * @param shipStartCol the shipStartCol to set
     */
    public void setShipStartCol(int shipStartCol) {
        this.shipStartCol = shipStartCol;
    }

    /**
     * @return the shipStopCol
     */
    public int getShipStopCol() {
        return shipStopCol;
    }

    /**
     * @param shipStopCol the shipStopCol to set
     */
    public void setShipStopCol(int shipStopCol) {
        this.shipStopCol = shipStopCol;
    }

    /**
     * @return the shipName
     */
    public String getShipName() {
        return shipName;
    }

    /**
     * @param shipName the shipName to set
     */
    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    /**
     * @return the hitsLeft
     */
    public int getHitsLeft() {
        return hitsLeft;
    }

    /**
     * @param hitsLeft the hitsLeft to set
     */
    public void setHitsLeft(int hitsLeft) {
        this.hitsLeft = hitsLeft;
    }
}
