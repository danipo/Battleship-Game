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
public class Carrier extends Ship 
{
    public Carrier()
    {
        setShipLength(Constants.CARRIER_LENGTH);
        setShipName("Carrier");
        setMaxNumberOfHits(Constants.CARRIER_LENGTH);
    }
}
