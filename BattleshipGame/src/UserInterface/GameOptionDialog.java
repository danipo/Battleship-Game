/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserInterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;

/**
 *
 * @author danie
 */
public class GameOptionDialog {
    
    private JDialog dialog;
    private JPanel optionsPanel;
    private JPanel buttonPanel;
    private JPanel gameOptionPanel;
    
    private JLabel computerAiLbl;
    private JLabel shipLayoutLbl;
    private JComboBox computerAi;
    private JComboBox shipLayout;
    private JButton saveBtn;
    private JButton canxBtn;

    // data for the JComboBox    
    private String[] level = {"Normal", "Ridiculously Hard"}; 
    private String[] layout = {"Manual","Automatic"};

    // array of Players
    private Player[] playerArray;
    
    //ActionListeners
    private SaveListener saveListener;
    private CancelListener cancelListener;

    // Customer constructor    
    public GameOptionDialog(JFrame parent, Player[] inPlayers)
    {
        playerArray = inPlayers;
        initComponents(parent);
    }
    
    // initialize components    
    private void initComponents(JFrame parent)
    {
        
        dialog = new JDialog(parent, true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        gameOptionPanel = new JPanel(new GridLayout(2,2));
        gameOptionPanel.setBorder(BorderFactory.createTitledBorder("Game Options"));
        gameOptionPanel.setPreferredSize(new Dimension(275,125));
        
        computerAiLbl = new JLabel("Computer AI");
        gameOptionPanel.add(computerAiLbl);
        computerAi = new JComboBox(level);
        gameOptionPanel.add(computerAi);
        
        shipLayoutLbl = new JLabel("Ship Layout");
        gameOptionPanel.add(shipLayoutLbl);
        shipLayout = new JComboBox(layout);
        gameOptionPanel.add(shipLayout);
        
        optionsPanel.add(gameOptionPanel);
        
        saveBtn = new JButton("Save");
        canxBtn = new JButton("Cancel");
        
        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        buttonPanel.add(saveBtn);
        SaveListener saveListener = new SaveListener();
        saveBtn.addActionListener(saveListener);
        buttonPanel.add(canxBtn);
        CancelListener cancelListener = new CancelListener();
        canxBtn.addActionListener(cancelListener);
        
        optionsPanel.add(buttonPanel);
        
        dialog.setTitle("Options");
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().add(optionsPanel, BorderLayout.CENTER);
        dialog.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        dialog.setMinimumSize(new Dimension(300,225));
        dialog.setLocation(200,200);
        dialog.setVisible(true);
    }

    private class SaveListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            //close UI
            dialog.dispose();
        }
        
    }
    
    private class CancelListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            dialog.dispose();
        }
        
    }
}
