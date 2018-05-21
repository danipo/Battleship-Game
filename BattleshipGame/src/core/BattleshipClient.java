/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import UserInterface.BattleshipUI;
import UserInterface.Player;
import static UserInterface.Player.getCols;
import static UserInterface.Player.getRows;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author danie
 */
public class BattleshipClient {
    
    Player currentPlayer;
    private Player[] playerArray;
    BattleshipUI parent;
    
    private PlayListener playListener = new PlayListener();
    
    public BattleshipClient(BattleshipUI ui, Player[] players)
    {
        playerArray = players;
        parent = ui;
    }
    
    public void play()
    {
        changeListener();
        
        if(playerArray[Constants.PLAYER_ONE].isIsFirst())
        {
            parent.updateTextArea("It's Player 1's turn!");
            currentPlayer = playerArray[Constants.PLAYER_ONE];
        }
        else if(playerArray[Constants.PLAYER_TWO].isIsFirst())
        {
            parent.updateTextArea("It's Player 2's turn!");
            currentPlayer = playerArray[Constants.PLAYER_TWO];
            computerPick();
        }
        else
        {
            parent.updateTextArea("It's Player 1's turn!");
            currentPlayer = playerArray[Constants.PLAYER_ONE];
        }
    }
    
    private void changeListener()
    {
        for(Player player: playerArray)
        {
            for(int row = 0; row < player.getRows(); row++)
            {
                for(int col = 0; col < player.getCols(); col++)
                {
                    player.removeListener(player.getBoard()[row][col], player.getBoardListener());
                }
            }
        }
        for(int row = 0; row < playerArray[Constants.PLAYER_TWO].getRows(); row++)
        {
            for(int col = 0; col<playerArray[Constants.PLAYER_ONE].getCols(); col++)
            {
                playerArray[Constants.PLAYER_TWO].setListener(playerArray[Constants.PLAYER_TWO].getBoard()[row][col], new PlayListener());
            }
        }
    }
    
    private void switchPlayers()
    {
        if(checkForWinner())
            endgame();
        
        if(currentPlayer == playerArray[Constants.PLAYER_ONE])
        {
            currentPlayer = playerArray[Constants.PLAYER_TWO];
            parent.updateTextArea("Player 2, it's your turn!");
            computerPick();
        }
            
        else if(currentPlayer == playerArray[Constants.PLAYER_TWO])
        {
            currentPlayer = playerArray[Constants.PLAYER_ONE];
            parent.updateTextArea("Player 1, it's your turn!");
        }
        
    }
    
    private boolean checkForWinner()
    {
        //int ships = 0;
        
        for(Player player : playerArray)
        {
            int ships = 0;
            for(Ship ship: player.getShips())
            {
                if(ship.isShipSunk())
                    ships++;
            }
            
            if(ships == player.getShips().size())
            {
                JOptionPane.showMessageDialog(parent, player.getUserName() + " has lost!");
                return true;
            }    
        }
        
        return false;
    }
    
    private void computerPick()
    {
        Random random = new Random();
        
        int rowClick = random.nextInt(10);
        int colClick = random.nextInt(10);
        
        if(playerArray[Constants.PLAYER_ONE].getBoard()[rowClick][colClick].getBackground() == Color.RED || 
            playerArray[Constants.PLAYER_ONE].getBoard()[rowClick][colClick].getBackground() == Color.BLUE)
            {
                parent.updateTextArea("That space has already been chosen. Sorry!");
            }

            else if(playerArray[Constants.PLAYER_ONE].isHit(rowClick,colClick))
            {
                playerArray[Constants.PLAYER_ONE].getBoard()[rowClick][colClick].setBackground(Color.RED);
            }
            else
                playerArray[Constants.PLAYER_ONE].getBoard()[rowClick][colClick].setBackground(Color.BLUE);
        
            switchPlayers();
    }
    
    private void endgame()
    {
        for(Player player : playerArray)
        {
            for(int row = 0; row < player.getRows(); row++)
            {
                for(int col = 0; col < player.getCols(); col++)
                {
                    player.getBoard()[row][col].setEnabled(false);
                }
            }
        }
    }
    
    private class PlayListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            if(ae.getSource() instanceof JButton)
            {
                //if this is a JButton, get the row and column
                JButton button = (JButton)ae.getSource();
                int rowClick = (int)button.getClientProperty("row");
                int colClick = (int)button.getClientProperty("col");
                
                if(playerArray[Constants.PLAYER_TWO].getBoard()[rowClick][colClick].getBackground() == Color.RED || 
                   playerArray[Constants.PLAYER_TWO].getBoard()[rowClick][colClick].getBackground() == Color.BLUE)
                {
                    parent.updateTextArea("That space has already been chosen. Sorry!");
                }
                
                else if(playerArray[Constants.PLAYER_TWO].isHit(rowClick,colClick))
                {
                    playerArray[Constants.PLAYER_TWO].getBoard()[rowClick][colClick].setBackground(Color.RED);
                }
                else
                    playerArray[Constants.PLAYER_TWO].getBoard()[rowClick][colClick].setBackground(Color.BLUE);
            }
            switchPlayers();
        }
    }
}