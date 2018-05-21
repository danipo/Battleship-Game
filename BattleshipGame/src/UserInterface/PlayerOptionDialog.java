/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import core.Constants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 *
 * @author danie
 */
public class PlayerOptionDialog 
{
    
    private JDialog dialog;
    private JPanel optionsPanel;
    private JPanel playerOptionPanel;
    private JPanel buttonPanel;
    
    private JLabel shipColorLbl; 
    private JLabel firstPlayerLbl;
    private JComboBox shipColor;
    private JComboBox firstPlayer;
    private JButton saveBtn;
    private JButton canxBtn;
    private JRadioButton player1;
    private JRadioButton player2;
    private ButtonGroup playerOptions; //makes it so you can only select one Radio Button

    // data for JComboBox    
    private String[] colors = {"Cyan", "Green", "Yellow", "Magenta", "Pink", "Red", "White"};
    private String[] players = {"Player 1", "Player 2", "Random"};
    private Color[] color = {Color.cyan, Color.green, Color.yellow, Color.magenta, Color.pink, Color.red, Color.white};
    
    // Array of Players
    private Player[] playerArray;
    private Player currentPlayer;
    
    //ActionListeners
    private PlayerListener playerListener = new PlayerListener();
    private SaveListener saveListener = new SaveListener();
    private CancelListener cancelListener = new CancelListener();
            
    // Custom constructor    
    public PlayerOptionDialog(JFrame parent, Player[] inPlayers) //parent forces the window to stay on top until closed
    {
            playerArray = inPlayers;
            initComponents(parent);
    }
    
    private void initComponents(JFrame parent)
    {
        
        dialog = new JDialog(parent, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); //EXIT_ON_CLOSE but for dialogs
        
        optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        playerOptionPanel = new JPanel(new GridLayout(3,2));
        playerOptionPanel.setBorder(BorderFactory.createTitledBorder("Player Options"));
        playerOptionPanel.setPreferredSize(new Dimension(275,125));
        
        playerOptions = new ButtonGroup();          //knows that there are radio buttons
        player1 = new JRadioButton("Player 1");
        player1.setSelected(true);
        player2 = new JRadioButton("Player 2");
        player1.addActionListener(playerListener);
        player2.addActionListener(playerListener);
        playerOptions.add(player1);
        playerOptions.add(player2);
        playerOptionPanel.add(player1);
        playerOptionPanel.add(player2);
        
        shipColorLbl = new JLabel("Ship Color");
        playerOptionPanel.add(shipColorLbl);
        shipColor = new JComboBox(colors);              //automatically populates the combobox with the indexes of the array passed through
        playerOptionPanel.add(shipColor);
        
        firstPlayerLbl = new JLabel("Who Plays First?");
        playerOptionPanel.add(firstPlayerLbl);
        firstPlayer = new JComboBox(players);           //same as above
        playerOptionPanel.add(firstPlayer);
        
        optionsPanel.add(playerOptionPanel);            //add the panel elements first, then the panel
        
        saveBtn = new JButton("Save");
        //SaveListener saveListener = new SaveListener();
        saveBtn.addActionListener(saveListener);
        canxBtn = new JButton("Cancel");
        //CancelListener cancelListener = new CancelListener();
        canxBtn.addActionListener(cancelListener);
        
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        buttonPanel.add(saveBtn);
        buttonPanel.add(canxBtn);
        
        //add the components of the panels first, then the panels
        dialog.setTitle("Options");
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().add(optionsPanel, BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        dialog.setMinimumSize(new Dimension(300,225));
        dialog.setLocation(200,200);
        dialog.setVisible(true);            //LAST ALWAYS
    }
    
    private class PlayerListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //set options for player1 or player2 based on what radio button is selected
            if(player1.isSelected())
            {
                int colorIndex = Arrays.asList(colors).indexOf(playerArray[Constants.PLAYER_ONE].getShipColor());
                shipColor.setSelectedIndex(colorIndex);
                
                if(playerArray[Constants.PLAYER_ONE].isIsFirst())
                {
                    firstPlayer.setSelectedIndex(Constants.PLAYER_ONE);
                    
                }
                else
                    firstPlayer.setSelectedIndex(Constants.PLAYER_TWO);
                
                currentPlayer = playerArray[Constants.PLAYER_ONE];
            }
            else if(player2.isSelected())
            {
                int colorIndex = Arrays.asList(colors).indexOf(playerArray[Constants.PLAYER_ONE].getShipColor());
                shipColor.setSelectedIndex(0);
                
                if(playerArray[Constants.PLAYER_TWO].isIsFirst())
                    firstPlayer.setSelectedIndex(Constants.PLAYER_TWO);
                else
                    firstPlayer.setSelectedIndex(Constants.PLAYER_ONE);
                
                currentPlayer = playerArray[Constants.PLAYER_TWO];
            }
        }
        
    }
    
    private class SaveListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //save options selected
            if(player1.isSelected())
            {
                playerArray[Constants.PLAYER_ONE].setShipColor(color[shipColor.getSelectedIndex()]);
                boolean isFirst = firstPlayer.getSelectedIndex() == 0 ? true: false;
                playerArray[Constants.PLAYER_ONE].setIsFirst(isFirst);
                playerArray[Constants.PLAYER_TWO].setIsFirst(!isFirst);
            }
            else if(player2.isSelected())
            {
                playerArray[Constants.PLAYER_TWO].setShipColor(color[shipColor.getSelectedIndex()]);
                boolean isFirst = firstPlayer.getSelectedIndex() == 0 ? true: false;
                playerArray[Constants.PLAYER_TWO].setIsFirst(isFirst);
                playerArray[Constants.PLAYER_ONE].setIsFirst(!isFirst);
            }
            //close UI
            dialog.dispose();
        }
        
    }
    
    private class CancelListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Close UI
            dialog.dispose();
        }
        
    }
}
