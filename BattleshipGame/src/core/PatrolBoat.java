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
public class PatrolBoat extends Ship
{
    public PatrolBoat()
    {
        setShipLength(Constants.PATROLBOAT_LENGTH);
        setShipName("Patrol Boat");
        setMaxNumberOfHits(Constants.PATROLBOAT_LENGTH);
    }
}
