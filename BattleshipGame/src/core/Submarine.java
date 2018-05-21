/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import core.IShip;

/**
 *
 * @author danie
 */
public class Submarine extends Ship 
{
    public Submarine()
    {
        setShipLength(Constants.SUBMARINE_LENGTH);
        setShipName("Submarine");
        setMaxNumberOfHits(Constants.SUBMARINE_LENGTH);
    }
}
