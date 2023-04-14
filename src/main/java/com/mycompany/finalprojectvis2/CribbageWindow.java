package com.mycompany.finalprojectvis2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author samsutton
 */
public class CribbageWindow extends javax.swing.JFrame {

    private ArrayList<JLabel> p1; //holds the JLabel components for each hand
    private ArrayList<JLabel> p2;
    private ArrayList<ArrayList> labelPiles;
    private Deck deck;
    private MouseClickHandler clickedHandler;
    //boolean for which game mode we're in 
    private boolean round1 ;  //getting rid of a card round
    private boolean counting; // counting to 31 back and forth 
    private boolean countingHands; //counting each hands' values
    //private boolean p1HasCrib; // to know which player has the crib -- true = p1 has crib(ppints will go to them)
    private ArrayList<DrawACard> p1Cards,p2Cards,crib; //holds the card info (image, suit, etc from api)
    private Hand player1Hand,player2Hand;
    private ArrayList<ArrayList> cardPiles;
    private Player player1, player2;
    private int count = 0;
    private ArrayList<DrawACard> countList;

    public class MouseClickHandler extends java.awt.event.MouseAdapter {

        /**
         *checks for a mouse click from the user and handles accordingly
         * @param evt
         */
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            //if in round1 -- round where we get rid of a card
            //deck = new Deck();
            JLabel comp = null;
            DrawACard card = null;
            JLabel componentClicked = (JLabel) evt.getComponent();
            System.out.println("here");
            if(round1 == true){
                //System.out.println("here");
                //checks for a click on each one of the cards of the player whose cards are showing
                if (componentClicked.getIcon() != deck.back && componentClicked.getIcon() != null && crib.size()<4) {
                    // get the label that was clicked and remove it 
                    //System.out.println("next");
                    for(int i = 0; i < labelPiles.size();i++){
                        for(int j = 0; j < labelPiles.get(i).size();j++){
                            if(labelPiles.get(i).get(j) == componentClicked){ // i = which pile , j = which card in pile
                                comp = (JLabel)labelPiles.get(i).get(j);
                                card = (DrawACard) cardPiles.get(i).get(j);
                                comp.setIcon(null);
                                labelPiles.get(i).remove(comp);
                                cardPiles.get(i).remove(card);
                                crib.add(card); //add card info to crib
                                flip();
                                //redraw the hand -- for label in hand, set label image to that index of card list -- so theres no empty space
//                                for(int k = 0; k < labelPiles.get(i).size();k++){
//                                    try {
//                                        jLblDeck.setIcon(new ImageIcon(deck.changeImage((DrawACard)cardPiles.get(i).get(k))));
//                                    } catch (MalformedURLException ex) {
//                                        Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
//                                    }
//                                }
                                break;
                            }
                        }
                         //have the crib visible now 
                        jLblCribLbl.setText("Crib");
                        jLblCribCard.setIcon(deck.back);
                    }
                }
                if(crib.size() == 4){
                    round1 = false;
                    try {
                        jLblDeck.setIcon(new ImageIcon(deck.changeImage(deck.getACard())));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(CribbageWindow.this, "We will now begin the counting round. You will switch back and forth trying to "
                                                        + "count to 31. You will recieve points for hitting 15, getting a run, pair, or being the "
                                                        + "last one to put a card down when neither player can  get to 31. Click a card to begin counting.");
                    counting = true;
                    //flip();
                }
            }
        
        
        // if in counting round
        
        //add a go button for when the player doesnt have a card low enough to be under 31 -- skip to other person 
            // if they both hit go thenflip cards - new count and last person to put a card down gets a point
            //go button will just increment a count -- when the count is 2 -- reset that means neither can go
        if(counting == true){
            if (componentClicked.getIcon() != deck.back && componentClicked.getIcon() != null) {
                for(int i = 0; i < labelPiles.size();i++){
                    for(int j = 0; j < labelPiles.get(i).size();j++){
                        if(labelPiles.get(i).get(j) == componentClicked){ // i = which pile , j = which card in pile
                            comp = (JLabel)labelPiles.get(i).get(j);
                            card = (DrawACard) cardPiles.get(i).get(j);
                            //removes the old label to redraw higher -- can see which of their cards theyve used
                            //add these to a list and remove them from our hand so they dont flip and cant be used again
                            //in counting -- add these elements back to the hand when counting is over so that we can count hands
//                            comp.setIcon(null);
//                            JLabel nxt = new JLabel();
//                            jPanel1.add(nxt);
//                            nxt.addMouseListener(clickedHandler);
//                            allPiles.get(oldPileIndex).add(nxt);
//                            nxt.setIcon(new ImageIcon(changeImage(oldcard)));
//                            nxt.setBounds(dimensions.get(oldPileIndex)[0], 160 + 20 * allPiles.get(oldPileIndex).size(), dimensions.get(oldPileIndex)[2], dimensions.get(oldPileIndex)[3]);
                            //use set bounds and get bounds somhow to move the selected card up slightly 
                            jLblCounterLbl.setText("Current Count");
                            
                            jLabelCount.setText(Integer.toString(count));
                            if(count < 31){ //once count is at 31 counting is over
                                count += deck.getValue(card);
                                jLabelCount.setText(Integer.toString(count));
                                countList.add(card);
                                if(countList.size()>1){
                                    System.out.println(countList.get(countList.size()-2).getCards()[0].getValue());
                                    System.out.println(card.getCards()[0].getValue());
                                    //compare the value(ex: king, 8, etc) of the newly added card and the one thrown before it
                                    if(countList.get(countList.size()-2).getCards()[0].getValue() == null ? card.getCards()[0].getValue() == null : countList.get(countList.size()-2).getCards()[0].getValue().equals(card.getCards()[0].getValue())){
                                        System.out.println("count lis:");
                                        if(i == 0){ //pile1 threw down the card -- they get the point for a pair
                                            player1.score += 2;
                                            jLblP1Score.setText(Integer.toString(player1.score));
                                        }
                                        else{ //pile2  threw down the card -- they get the point for a pair
                                            player2.score += 2;
                                            jLblP2Score.setText(Integer.toString(player2.score));
                                        }
                                    }
                                    if(count == 15){ //player who just made the count get to 15 gets 2 points
                                        if(i == 0){ //pile1 threw down the card -- they get the points for getting to 15
                                            player1.score += 2;
                                            jLblP1Score.setText(Integer.toString(player1.score));
                                        }
                                        else{ //pile2  threw down the card -- they get the points for getting to 15
                                            player2.score += 2;
                                            jLblP2Score.setText(Integer.toString(player2.score));
                                        }
                                    }
                                    if(count == 31){
                                        if(i == 0){ //pile1 threw down the card -- they get the points for getting to 31
                                            player1.score += 2;
                                            jLblP1Score.setText(Integer.toString(player1.score));
                                        }
                                        else{ //pile2  threw down the card -- they get the points for getting to 31
                                            player2.score += 2;
                                            jLblP2Score.setText(Integer.toString(player2.score));
                                        }
                                        //if players have cards left
                                            //flip over the cards that are higher(have been used)
                                            //count = 0
                                        ///if neither player has any cards left
                                            //round is over since 31 is the max
                                            //don't show counting info when we aren't in that round
                                            counting = false;
                                            jLblCounterLbl.setText(null);
                                            jLabelCount.setText(null);
                                            JOptionPane.showMessageDialog(CribbageWindow.this, "Counting round is now over. We will now count each players' hand and adjust scores accordingly");
                                            countingHands = true;
                                    }
                                    if(count < 31){
                                        if(i == 0 ){ //pile1
                                            //if both players press go -- last person to throw down a card gets a point
                                        }
                                        else{ //pile2

                                        }
                                    }
                                    //check for a run - they can be in any order
                                }
                            }
                        }
                    }
                }
                flip();
            }
        }
        
        // move that card up a little so its not in line with their hand -- jLblp13.setBounds(200, 330, 80, 110)
        // takes its value and display it as current count
        // if switched to other player's hand -- do the same stuff
        // if the score hits 15 -- add 2 to that player's overall score
        // have an arraylist of the values of the cards used in the current count
        // check this list for runs and pairs and adjust the player's scores accordingly 
        // once we hit 31 or no one has any eligible cards -- adjust scores and set count back to zero
        // stop when both players are out of cards 
        // set counting to false -- no longer in this round 
        
        }
    }
    
    /**
     * Creates new form CribbageWindow
     */
    public CribbageWindow() throws InterruptedException {
        initComponents();
        countList = new ArrayList<>();
        player1 = new Player();
        p1Cards = player1.hand.getHand();
        player2 = new Player();
        p2Cards = player2.hand.getHand();
        cardPiles = new ArrayList<>();
        cardPiles.add(p1Cards);
        cardPiles.add(p2Cards);
        jLblP1Score.setText(Integer.toString(player1.score)); //will this update as 
        jLblP2Score.setText(Integer.toString(player2.score));
        clickedHandler = new MouseClickHandler();
        labelPiles = new ArrayList<>();
        p1 = new ArrayList<>();
        p1.add(jLblp11);
        p1.add(jLblp12);
        p1.add(jLblp13);
        p1.add(jLblp14);
        p1.add(jLblp15);
        p2 = new ArrayList<>();
        p2.add(jLblp21);
        p2.add(jLblp22);
        p2.add(jLblp23);
        p2.add(jLblp24);
        p2.add(jLblp25);
        labelPiles.add(p1);
        labelPiles.add(p2);
        
        round1 = false;
        counting = false;
        countingHands = false;
        //p1HasCrib = true;
       
        deck = new Deck();
        crib = new ArrayList<>();
        //crib should be the size of a full hand(4) so 2 get added from the deck when dealt and the other 2 will be the ones the players dispose of
        crib.add(deck.getACard());
        crib.add(deck.getACard());
        //get images of cards from Hand and initialize them to show for player 1
        //Hand hand = new Hand();
        //p1Cards = player1Hand.getHand();
        //p2Cards = player2Hand.getHand();
        
        
        for (JLabel label : p1) {
            label.addMouseListener(clickedHandler);
        }
        for (JLabel label : p2) {
            label.addMouseListener(clickedHandler);
        }
        
        
        //System.out.println(hand.getHand("p1"));
        try {
            for (int i = 0; i < p1.size(); i++) {
                p1.get(i).setIcon(new ImageIcon(deck.changeImage(p1Cards.get(i))));
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        //have a message that says whenever ready, select one card to dispose of to the crib
        //Thread.sleep(1);
        JOptionPane.showMessageDialog(this, "Whenever ready, each player should select one card to get rid of. This card will be added to the crib");
        round1 = true;
        //after cards are dealt, 2 more cards will be added to the crib array so that after the players select their cards, crib will have 4 (full hand)
        
        //check once the crib has 4 cards -- once this is done draw a card and display it on top of the deck 
        
        // wait like a second and the display a message that we will begin counting and the rules (bool to true) -- try to get 15, pairs, runs etc - don't go over 31
        // when counting is false after this -- counting hands to true
 
        
        
        // call count hand method from hand on player1, then player2, then crib
        // update players' scores based on each count value returned
        // if p1HasCrib == true -- crib count gets added to p1 score else gets added to p1 score
        // countHands = false
        // display a message after counting each -- player 1 your hand was worth __ points
        
        // display message that round is over and new hands will be dealt 
        // restart everything by calling the constructor again? restart somehow
        
        //if either score is 120 -- display message that player wins -- game is over 

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLblp13 = new javax.swing.JLabel();
        jLblDeck = new javax.swing.JLabel();
        jLblp12 = new javax.swing.JLabel();
        jLblp15 = new javax.swing.JLabel();
        jLblp11 = new javax.swing.JLabel();
        jLblp21 = new javax.swing.JLabel();
        jLblp22 = new javax.swing.JLabel();
        jLblp23 = new javax.swing.JLabel();
        changePlayerBtn = new javax.swing.JButton();
        jLblCribLbl = new javax.swing.JLabel();
        jLblP2Score = new javax.swing.JLabel();
        jLblP1Score = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLblp25 = new javax.swing.JLabel();
        jLblp14 = new javax.swing.JLabel();
        jLblp24 = new javax.swing.JLabel();
        jLblCribCard = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLblCounterLbl = new javax.swing.JLabel();
        jLabelCount = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);
        jPanel1.add(jLblp13);
        jLblp13.setBounds(200, 330, 80, 110);

        jLblDeck.setBackground(new java.awt.Color(255, 255, 255));
        jLblDeck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backOfDeck (4) 10.25.33 PM.jpeg"))); // NOI18N
        jLblDeck.setText("back");
        jPanel1.add(jLblDeck);
        jLblDeck.setBounds(660, 180, 80, 110);
        jPanel1.add(jLblp12);
        jLblp12.setBounds(110, 330, 80, 110);
        jPanel1.add(jLblp15);
        jLblp15.setBounds(380, 330, 80, 110);

        jLblp11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jLblp11);
        jLblp11.setBounds(20, 330, 80, 110);

        jLblp21.setBackground(new java.awt.Color(255, 255, 255));
        jLblp21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backOfDeck (4) 10.25.33 PM.jpeg"))); // NOI18N
        jLblp21.setText("back");
        jPanel1.add(jLblp21);
        jLblp21.setBounds(20, 20, 80, 110);

        jLblp22.setBackground(new java.awt.Color(255, 255, 255));
        jLblp22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backOfDeck (4) 10.25.33 PM.jpeg"))); // NOI18N
        jPanel1.add(jLblp22);
        jLblp22.setBounds(110, 20, 80, 110);

        jLblp23.setBackground(new java.awt.Color(255, 255, 255));
        jLblp23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backOfDeck (4) 10.25.33 PM.jpeg"))); // NOI18N
        jPanel1.add(jLblp23);
        jLblp23.setBounds(200, 20, 80, 110);

        changePlayerBtn.setText("Switch Players");
        changePlayerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePlayerBtnActionPerformed(evt);
            }
        });
        jPanel1.add(changePlayerBtn);
        changePlayerBtn.setBounds(130, 210, 116, 40);

        jLblCribLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLblCribLbl);
        jLblCribLbl.setBounds(530, 160, 90, 20);

        jLblP2Score.setFont(new java.awt.Font("Hiragino Sans GB", 0, 30)); // NOI18N
        jLblP2Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblP2Score.setText("0");
        jLblP2Score.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLblP2Score);
        jLblP2Score.setBounds(660, 340, 80, 90);

        jLblP1Score.setFont(new java.awt.Font("Hiragino Sans GB", 0, 30)); // NOI18N
        jLblP1Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblP1Score.setText("0");
        jLblP1Score.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLblP1Score);
        jLblP1Score.setBounds(660, 40, 80, 90);

        jLabel4.setText("Player 1 Score");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(660, 10, 90, 20);

        jLabel5.setText("Player 2 Score");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(660, 310, 90, 20);

        jLblp25.setBackground(new java.awt.Color(255, 255, 255));
        jLblp25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backOfDeck (4) 10.25.33 PM.jpeg"))); // NOI18N
        jPanel1.add(jLblp25);
        jLblp25.setBounds(380, 20, 80, 110);
        jPanel1.add(jLblp14);
        jLblp14.setBounds(290, 330, 80, 110);

        jLblp24.setBackground(new java.awt.Color(255, 255, 255));
        jLblp24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/backOfDeck (4) 10.25.33 PM.jpeg"))); // NOI18N
        jPanel1.add(jLblp24);
        jLblp24.setBounds(290, 20, 80, 110);

        jLblCribCard.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.add(jLblCribCard);
        jLblCribCard.setBounds(540, 180, 80, 110);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Deck");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(650, 160, 90, 20);
        jPanel1.add(jLblCounterLbl);
        jLblCounterLbl.setBounds(280, 170, 110, 20);

        jLabelCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabelCount);
        jLabelCount.setBounds(320, 207, 30, 40);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void flip(){
        if (p1.get(0).getIcon() != deck.back) {
            //player one is showing -- switch to player 2 showing
            //System.out.println("next");
            for (int i = 0; i < p2.size();i++) {
                try {
                    //System.out.println(new ImageIcon(deck.changeImage(p2Cards.get(i))));
                    p2.get(i).setIcon(new ImageIcon(deck.changeImage(p2Cards.get(i))));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                for(int j = 0; j < p1.size();j++){
                    p1.get(j).setIcon(deck.back);
                }
            }
                
        } 
        //player 2 showing -- switch to player 1
        else{
            //player one is showing -- switch to player 2 showing
            for (int i = 0; i < p1.size();i++) {
                try {
                    p1.get(i).setIcon(new ImageIcon(deck.changeImage(p1Cards.get(i))));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                for(int j = 0; j < p2.size();j++){
                    p2.get(j).setIcon(deck.back);
                }
            }
        } 

    }
    
    
    /**
     * when clicked, the cards for the other player will show and the player whose cards are currently showing will be flipped
     * @param evt 
     */
    private void changePlayerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePlayerBtnActionPerformed
        
        flip();

    }//GEN-LAST:event_changePlayerBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CribbageWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CribbageWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CribbageWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CribbageWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CribbageWindow().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changePlayerBtn;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelCount;
    private javax.swing.JLabel jLblCounterLbl;
    private javax.swing.JLabel jLblCribCard;
    private javax.swing.JLabel jLblCribLbl;
    private javax.swing.JLabel jLblDeck;
    private javax.swing.JLabel jLblP1Score;
    private javax.swing.JLabel jLblP2Score;
    private javax.swing.JLabel jLblp11;
    private javax.swing.JLabel jLblp12;
    private javax.swing.JLabel jLblp13;
    private javax.swing.JLabel jLblp14;
    private javax.swing.JLabel jLblp15;
    private javax.swing.JLabel jLblp21;
    private javax.swing.JLabel jLblp22;
    private javax.swing.JLabel jLblp23;
    private javax.swing.JLabel jLblp24;
    private javax.swing.JLabel jLblp25;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
