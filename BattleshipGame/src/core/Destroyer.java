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

public class Destroyer extends Ship
{
    public Destroyer(){
        setShipLength(Constants.DESTROYER_LENGTH);
        setShipName("Destroyer");
        setMaxNumberOfHits(Constants.DESTROYER_LENGTH);
    }
}
