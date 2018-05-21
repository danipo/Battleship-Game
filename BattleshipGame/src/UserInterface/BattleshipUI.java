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
import javax.swing.*;
import core.BattleshipClient;
import core.Ship;
/**
 *
 * @author danie
 */
public class BattleshipUI extends JFrame {
    
    //UI elements
    private JMenuBar menuBar;    
    private JMenu gameMenu;
    private JMenu optionMenu;
    private JMenuItem playerPlayer;
    private JMenuItem playerComputer;
    private JMenuItem computerComputer;	
    private JMenuItem exit;	
    private JMenuItem game;
    private JMenuItem player;
    private JButton deploy;
    
    //UI Layout
    private JPanel shipLayoutPanel;
    private JPanel directionPanel;
    private JPanel playerOnePanel;
    private JPanel playerTwoPanel;
    private JPanel statusPanel;
    private JPanel shipPanel;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    
    //JComboBoxes
    private JComboBox shipCb;
    private JComboBox directionCb;
    
    //UI labels and ship options
    private String[] rowLetters = {" ","A","B","C","D","E","F","G","H","I","J"};
    private String[] columnNumbers = {" ","1","2","3","4","5","6","7","8","9","10"};
    private String[] ships = {"Carrier","Battleship","Submarine","Destroyer", "Patrol Boat"};
    private String[] direction = {"Horizontal","Vertical"};

    //actionListeners
    private GameListener gameListener;
    private OptionsListener optionListener;
    private DeployListener deployListener;
    
    //static variables
    private static final int PLAYER_ONE = 0;
    private static final int PLAYER_TWO = 1;
    
    //players
    private Player playerOne;
    private Player playerTwo;
    private BattleshipClient gameControl;
    
    //arrays
    private Player[] players = new Player[2];
    private Color[] color = {Color.cyan, Color.green, Color.yellow, Color.magenta, Color.pink, Color.red, Color.white};
   
    
    public BattleshipUI()
    {
        initObjects();
        initComponents();
    }
    
    private void initObjects()
    {
        
        playerOne = new Player("Player One", this);
        playerTwo = new Player("Player Two", this);
        
        players[Constants.PLAYER_ONE] = playerOne;
        players[Constants.PLAYER_TWO] = playerTwo;
        
        for(Player player: players){
            System.out.println(player.getUserName() + " is playing the game!");
        }
        
        players[Constants.PLAYER_ONE].setPlayMode(Constants.HUMAN);
        players[Constants.PLAYER_TWO].setPlayMode(Constants.COMPUTER);
        
        if(players[Constants.PLAYER_ONE].getPlayMode() == Constants.COMPUTER)
        {
            players[Constants.PLAYER_ONE].autoLayout();
        }
        
        if(players[Constants.PLAYER_TWO].getPlayMode() == Constants.COMPUTER)
        {
            players[Constants.PLAYER_TWO].autoLayout();
        }
        
        gameControl = new BattleshipClient(this, players); 
    }
    
    private void initComponents()
    {
        this.setTitle("Battleship");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(425,500));
        this.setMinimumSize(new Dimension(425,500));
                
        menuBar = new JMenuBar();
        gameMenu = new JMenu("Game");
        gameMenu.setMnemonic('G');
        optionMenu = new JMenu("Option");
        optionMenu.setMnemonic('O');
        
        
        menuBar.add(gameMenu);
        menuBar.add(optionMenu);
        
        playerPlayer = new JMenuItem("Player vs. Player");
        playerPlayer.addActionListener(gameListener);
        playerPlayer.setEnabled(false);
        gameMenu.add(playerPlayer);
        
        playerComputer = new JMenuItem("Player vs. Computer");
        playerComputer.addActionListener(gameListener);
        playerComputer.setSelected(true);
        gameMenu.add(playerComputer);
        
        computerComputer = new JMenuItem("Computer vs. Computer");
        computerComputer.addActionListener(gameListener);
        computerComputer.setEnabled(false);
        gameMenu.add(computerComputer);
              
        exit = new JMenuItem("Exit");
        exit.addActionListener(new ExitListener());
        gameMenu.add(exit);
        
        game = new JMenuItem("Game Options");
        game.addActionListener(new OptionsListener());
        optionMenu.add(game);
        
        player = new JMenuItem("Player Options");
        player.addActionListener(new OptionsListener());
        optionMenu.add(player);
        
        deploy = new JButton("Deploy Ships");
        deploy.setEnabled(false);
        deploy.addActionListener(new DeployListener());
        
        shipCb = new JComboBox();
        
        for(String shipType : ships)
        {
            shipCb.addItem(shipType);
        }
        
        shipCb.addActionListener(new ShipListener());
        shipCb.setSelectedIndex(0);
        
        directionCb = new JComboBox(direction);
        directionCb.addActionListener(new DirectionListener());
        directionCb.setSelectedIndex(0);
        
        shipLayoutPanel = new JPanel();
        //shipLayoutPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        shipPanel = new JPanel();
        shipPanel.setBorder(BorderFactory.createTitledBorder("Ships"));
        
        directionPanel = new JPanel();
        directionPanel.setBorder(BorderFactory.createTitledBorder("Direction"));
        
        shipPanel.add(shipCb);
        directionPanel.add(directionCb);
               
        shipLayoutPanel.add(shipPanel);
        shipLayoutPanel.add(directionPanel);
        shipLayoutPanel.add(deploy);
        
        statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createTitledBorder("Game Status"));
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(180,350));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        statusPanel.add(scrollPane);
        
        playerOnePanel = new JPanel(new GridLayout(11,11));
        playerOnePanel.setMinimumSize(new Dimension(400,400));
        playerOnePanel.setPreferredSize(new Dimension(400,400));
        playerOnePanel.setBorder(BorderFactory.createTitledBorder("Player One"));
        
        playerTwoPanel = new JPanel(new GridLayout(11,11));
        playerTwoPanel.setMinimumSize(new Dimension(400,400));
        playerTwoPanel.setPreferredSize(new Dimension(400,400));
        playerTwoPanel.setBorder(BorderFactory.createTitledBorder("Player Two"));
        
        JButton[][] playerOneButton = playerOne.getBoard();
        
        if(playerOneButton == null)
            System.out.println("Button Null");
            
        for(int row=0;row<11;row++)
        {
            for(int col=0;col<11;col++)
            {
                if (row == 0)
                {
                    JLabel colLabel = new JLabel(columnNumbers[col], SwingConstants.CENTER);
                    playerOnePanel.add(colLabel);
                } 
                else if(row> 0 && col == 0)
                {
                    JLabel rowLabel = new JLabel(rowLetters[row], SwingConstants.CENTER);
                    playerOnePanel.add(rowLabel);
                } 
                else
                {
                    playerOnePanel.add(playerOneButton[row-1][col-1]);
                }
            }
        }
        
        JButton[][] playerTwoButton = playerTwo.getBoard();
        
        if(playerTwoButton == null)
            System.out.println("Button Null");
            
        for(int row=0;row<11;row++)
        {
            for(int col=0;col<11;col++)
            {
                if (row == 0)
                {
                    JLabel colLabel = new JLabel(columnNumbers[col], SwingConstants.CENTER);
                    playerTwoPanel.add(colLabel);
                } 
                else if(row> 0 && col == 0)
                {
                    JLabel rowLabel = new JLabel(rowLetters[row], SwingConstants.CENTER);
                    playerTwoPanel.add(rowLabel);
                } 
                else
                {
                    playerTwoPanel.add(playerTwoButton[row-1][col-1]);
                }
            }
        }
        
        JMenu options = new JMenu("Options");
        JMenuItem gameOptions = new JMenuItem("Game Options");
        JMenuItem playerOptions = new JMenuItem("Player Options");
        OptionsListener optionListener = new OptionsListener();
        gameOptions.addActionListener(optionListener);
        playerOptions.addActionListener(optionListener);
        
        this.setJMenuBar(menuBar);
        this.add(shipLayoutPanel, BorderLayout.NORTH);
        this.add(playerOnePanel, BorderLayout.WEST);
        this.setVisible(true);
    }
    
    private JFrame getThisParent()
    {
        return this;
    }
    
    public void updateTextArea(String data)
    {
        textArea.append(data + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
        
    }
    
    private class ExitListener implements ActionListener
    {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent ae) 
        {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to Quit?",
                "Exit?", JOptionPane.YES_NO_OPTION);
            
            if(response == JOptionPane.YES_OPTION)
                System.exit(0);
        }
        
    }    
    
    public void setDeployEnabled(boolean val)
    {
        deploy.setEnabled(val);
    }
    
    private class DeployListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
            getThisParent().setMinimumSize(new Dimension(1000,500));
            getThisParent().add(statusPanel, BorderLayout.CENTER);
            getThisParent().add(playerTwoPanel, BorderLayout.EAST);
            
            updateTextArea("Let's Play!");
            
            setDeployEnabled(false);
            shipCb.setEnabled(false);
            directionCb.setEnabled(false);
            
            gameControl.play();
        }
    }
    
    private class ShipListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
           
           int index = shipCb.getSelectedIndex();
           
           if(shipCb.getSelectedItem().equals("Carrier"))
               playerOne.setCurrentShip(Constants.CARRIER);
           else if(shipCb.getSelectedItem().equals("Battleship"))
               playerOne.setCurrentShip(Constants.BATTLESHIP);
           else if(shipCb.getSelectedItem().equals("Submarine"))
               playerOne.setCurrentShip(Constants.SUBMARINE);
           else if(shipCb.getSelectedItem().equals("Destroyer"))
               playerOne.setCurrentShip(Constants.DESTROYER);
           else if(shipCb.getSelectedItem().equals("Patrol Boat"))
               playerOne.setCurrentShip(Constants.PATROLBOAT);
        }
    
        
    }
    
    private class DirectionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if(directionCb.getSelectedItem().equals("Horizontal"))
                playerOne.setCurrentDirection(Constants.HORIZONTAL);
            else if(directionCb.getSelectedItem().equals("Vertical"))
                playerOne.setCurrentDirection(Constants.VERTICAL);
        }
        
    }
    
    private class GameListener implements ActionListener
    {

        @Override
        public void actionPerformed(java.awt.event.ActionEvent ae) 
        {
            //.equals replaces == for strings
            if(ae.getActionCommand().equals("Player vs. Computer"))
            {
                
            } 
        }
        
    }
    
    private class OptionsListener implements ActionListener
    {
        GameOptionDialog gameOptions;
        PlayerOptionDialog playerOptions;
        
        @Override
        public void actionPerformed(java.awt.event.ActionEvent ae) {
           
            if(ae.getActionCommand().equals("Game Options"))
            {
                gameOptions = new GameOptionDialog(getThisParent(),players);
            }
            else if(ae.getActionCommand().equals("Player Options"))
            {
                playerOptions = new PlayerOptionDialog(getThisParent(), players);
            }
        }
    }
}