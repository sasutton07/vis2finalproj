package com.mycompany.finalprojectvis2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.net.MalformedURLException;
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
    private ArrayList<DrawACard> p1Cards,p2Cards,crib; //holds the card info (image, suit, etc from api)
    private Hand player1Hand,player2Hand;
    private ArrayList<ArrayList> cardPiles;
    private Player player1, player2;
    private int count = 0;
    private ArrayList<DrawACard> countList;
    private ArrayList<DrawACard> countingUsedList;
    private ArrayList<JLabel> countingUsedListLbls;
    private ArrayList<int[]> dimensions;
    private int[] p1dimensions, p2dimensions;
    private boolean p1ThrewLast = false;
    private boolean p2ThrewLast = false;
    private int go; //count of how many times go has been pressed - once both players press it new count (no one has cards low enough)

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
            p1ThrewLast = false;
            p2ThrewLast = false;
            int x = evt.getXOnScreen();
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
                                //comp.setIcon(null);
                                DrawACard lastCard = (DrawACard)cardPiles.get(i).get(cardPiles.get(i).size()-1);
                                JLabel lastLabel = (JLabel)labelPiles.get(i).get(labelPiles.get(i).size()-1);
                                //redraws the cards so there is no empty space where the crib was
                                //if its not the last card in the row, move that last card to this empty spot and remove image from last
                                for(int h = 0; h < labelPiles.get(i).size() - (j+1);h++){
                                    try {
                                        comp.setIcon(new ImageIcon(deck.changeImage((DrawACard)cardPiles.get(i).get(h+1))));
                                        //System.out.println(lastCard.getCards()[0].getCode());
                                    } catch (MalformedURLException ex) {
                                    }
                                }
                                lastLabel.setIcon(null);
                                labelPiles.get(i).remove(labelPiles.get(i).get(4));
                                cardPiles.get(i).remove(card);
                                crib.add(card); //add card info to crib
                                flip();
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
                    JOptionPane.showMessageDialog(CribbageWindow.this, """
                                                                       We will now begin the counting round. You will switch back and forth trying to 
                                                                       count to 31. You will recieve points for hitting 15, getting a run, pair, or being the 
                                                                       last one to put a card down when neither player can  get to 31. Click a card to begin counting.""");
                    counting = true;
                    //flip();
                }
            }
        
        
        // if in counting round
        if(counting == true){
            if (componentClicked.getIcon() != deck.back && componentClicked.getIcon() != null) {
                for(int i = 0; i < labelPiles.size();i++){
                    for(int j = 0; j < labelPiles.get(i).size();j++){
                        if(labelPiles.get(i).get(j) == componentClicked){ // i = which pile , j = which card in pile
                            comp = (JLabel)labelPiles.get(i).get(j);
                            card = (DrawACard) cardPiles.get(i).get(j);
                            count += deck.getValue(card);
                            if(i==0){ //move card up if bottom player
                                p1ThrewLast = true;
                            }
                            else{ //move card down if top player
                                p2ThrewLast = true;
                            }
                            if(count <= 31){ //once count is at 31 counting is over
                                //removes the old label to redraw higher -- can see which of their cards theyve used
                                //add these to a list and remove them from our hand so they dont flip and cant be used again
                                //in counting -- add these elements back to the hand when counting is over so that we can count hands
                                comp.setIcon(null);
                                JLabel nxt = new JLabel();
                                jPanel1.add(nxt);
                                //nxt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                                nxt.addMouseListener(clickedHandler);
                                try {
                                    nxt.setIcon(new ImageIcon(deck.changeImage(card)));
                                } catch (MalformedURLException ex) {
                                    Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                System.out.println("j" + j);
                                System.out.println("+" + (x-40));
                                if(i==0){ //move card up if bottom player
                                    nxt.setBounds(x-45, dimensions.get(i)[1]-50, dimensions.get(i)[2], dimensions.get(i)[3]);
                                }
                                else{ //move card down if top player
                                    nxt.setBounds(x-45, dimensions.get(i)[1]+50, dimensions.get(i)[2], dimensions.get(i)[3]);
                                }
                                countingUsedList.add(card);
                                countingUsedListLbls.add(nxt);
                                cardPiles.get(i).remove(j);
                                labelPiles.get(i).remove(j);
                                jLblCounterLbl.setText("Current Count");
                                jLabelCount.setText(Integer.toString(count));

                                countList.add(card);
                                if(countList.size()>1){
                                    System.out.println(countList.get(countList.size()-2).getCards()[0].getValue());
                                    System.out.println(card.getCards()[0].getValue());
                                    //compare the value(ex: king, 8, etc) of the newly added card and the one thrown before it -- checks if theyre the same
                                    if(countList.get(countList.size()-2).getCards()[0].getValue() == null ? card.getCards()[0].getValue() == null : countList.get(countList.size()-2).getCards()[0].getValue().equals(card.getCards()[0].getValue())){
                                        //only check for 3 in a row if at least 3 cards have been thrown
                                        if(countList.size()>2){
                                            //3 cards the same in a row -- they get 6 points instead of 2 for normal pair
                                            if(countList.get(countList.size()-3) == countList.get(countList.size()-1)){
                                                if(i == 0){ //pile1 threw down the card -- they get the point for a pair
                                                    player1.score += 6;
                                                    jLblP1Score.setText(Integer.toString(player1.score));
                                                }
                                                else{ //pile2  threw down the card -- they get the point for a pair
                                                    player2.score += 6;
                                                    jLblP2Score.setText(Integer.toString(player2.score));
                                                }
                                            }
                                             //just 2 cards in a row -- regular pair -- 2 points
                                            else{
                                                if(i == 0){ //pile1 threw down the card -- they get the point for a pair
                                                    player1.score += 2;
                                                    jLblP1Score.setText(Integer.toString(player1.score));
                                                }
                                                else{ //pile2  threw down the card -- they get the point for a pair
                                                    player2.score += 2;
                                                    jLblP2Score.setText(Integer.toString(player2.score));
                                                }
                                            }
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
                                    //run -- work on this - find out how to sort
                                    if(countList.size()>2){ //must be at least 3 cards to look for a run(min size 3)
                                        for(int c = 3; c < countList.size();c++){
                                            
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
                                        jLabelCount.setText("0");
                                        count = 0;
                                        //flip all of the used cards
                                        for(int k = 0; k < countingUsedListLbls.size();k++){
                                            countingUsedListLbls.get(k).setIcon(deck.back);
                                        }
                                        JOptionPane.showMessageDialog(CribbageWindow.this, "You counted to 31! We will start a new round of counting with your remaining cards if you have any "
                                                + "or move onto counting hands if all cards have been used.");
                                    }
                                   
                                }
                                flip();
                            }
                            else{
                                count = count - deck.getValue(card); //had to add to count so we can see if over 31 -- 
                                                                     //card couldn't be used so put the count back
                                JOptionPane.showMessageDialog(CribbageWindow.this, "Count cannot exceed 31. If you do not have a card low enough press GO.");
                            }
                        }
                    }
                }
                
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
     * @throws java.lang.InterruptedException
     */
    public CribbageWindow() throws InterruptedException {
        initComponents();
        go = 0;
        countList = new ArrayList<>();
        countingUsedList = new ArrayList<>();
        countingUsedListLbls = new ArrayList<>();
        player1 = new Player();
        p1Cards = player1.hand.getHand();
        player2 = new Player();
        p2Cards = player2.hand.getHand();
        cardPiles = new ArrayList<>();
        cardPiles.add(p1Cards);
        cardPiles.add(p2Cards);
        jLblP1Score.setText(Integer.toString(player1.score)); 
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
        
        dimensions = new ArrayList<>();
        p1dimensions = new int[]{20, 330, 80, 110};
        p2dimensions = new int[]{20, 20, 80, 110}; //each x of the following is 90 more than the first card(this one) 
        dimensions.add(p1dimensions);
        dimensions.add(p2dimensions);
        
        //have a message that says whenever ready, select one card to dispose of to the crib
        //Thread.sleep(1);
        JOptionPane.showMessageDialog(this, "Whenever ready, each player should select one card to get rid of. This card will be added to the crib");
        round1 = true;
        //after cards are dealt, 2 more cards will be added to the crib array so that after the players select their cards, crib will have 4 (full hand)
        
        //check once the crib has 4 cards -- once this is done draw a card and display it on top of the deck 
        
        // wait like a second and the display a message that we will begin counting and the rules (bool to true) -- try to get 15, pairs, runs etc - don't go over 31
        // when counting is false after this -- counting hands to true
 
        
        
        // call count hand method from hand on player1, then player2, then crib
        if(countingHands == true){
            //add the deck card to the hand!!!!! - but keep track of which is deck card somehow 
            //player that has the crib goes last
            //add messages to tell them how many points they earned
            if(player2.hasCrib){
                //show player 1's cards
                JOptionPane.showMessageDialog(CribbageWindow.this, "We will now count player 1's hand and update the score");
                //tell them how  many points they got 
                jLblP1Score.setText(Integer.toString(player1.hand.countHand(p1Cards)));
                JOptionPane.showMessageDialog(CribbageWindow.this, "We will now count player 2's hand and update the score");
                flip();
                //tell them how  many points they got 
                jLblP2Score.setText(Integer.toString(player2.hand.countHand(p2Cards)));
                jLblP2Score.setText(Integer.toString(player2.hand.countHand(crib)));
                
            }else{
                //show player 2's hands 
                JOptionPane.showMessageDialog(CribbageWindow.this, "We will now count player 2's hand and update the score");
                jLblP2Score.setText(Integer.toString(player2.hand.countHand(p2Cards)));
                JOptionPane.showMessageDialog(CribbageWindow.this, "We will now count player 1's hand and update the score");
                flip(); 
                jLblP1Score.setText(Integer.toString(player1.hand.countHand(p1Cards)));
                jLblP1Score.setText(Integer.toString(player1.hand.countHand(crib)));
            }
        }
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
        jBtnGo = new javax.swing.JButton();

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
        jLblP2Score.setBounds(660, 50, 80, 90);

        jLblP1Score.setFont(new java.awt.Font("Hiragino Sans GB", 0, 30)); // NOI18N
        jLblP1Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblP1Score.setText("0");
        jLblP1Score.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLblP1Score);
        jLblP1Score.setBounds(660, 350, 80, 90);

        jLabel4.setText("Player 1 Score");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(660, 320, 90, 20);

        jLabel5.setText("Player 2 Score");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(660, 20, 90, 20);

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
        jLblCounterLbl.setBounds(280, 190, 110, 20);

        jLabelCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabelCount);
        jLabelCount.setBounds(320, 220, 30, 40);

        jBtnGo.setText("GO");
        jBtnGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGoActionPerformed(evt);
            }
        });
        jPanel1.add(jBtnGo);
        jBtnGo.setBounds(20, 220, 72, 23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 768, Short.MAX_VALUE)
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
        if(p1.isEmpty() && p2.isEmpty()){ //no one has any cards left
            JOptionPane.showMessageDialog(this, "Since no one has cards left, the counting round is over. The player that put down the"
                                                + " last card will get a point. Each player's hand will now be counted and points will be awarded.");
            if(p1ThrewLast == true){
                player1.score += 1;
                jLblP1Score.setText(Integer.toString(player2.score));
            }
            else if(p2ThrewLast == true){
                player2.score += 1;
                jLblP2Score.setText(Integer.toString(player2.score));
            }
            jLabelCount.setText("0");
            count = 0;
            //whoever threw the last card gets the point 
            //message that that person gets a point and the counting round is over and we will now count hands since everyone
            //is out of cards
            //flip all cards and put them in their original positions
            for(JLabel card:countingUsedListLbls){
                System.out.println("asokdnjdkf");
                card.setIcon(null);
                jPanel1.remove(card);
            }
            
            for(int k = 0; k < 2;k++){
                for(int i = 0; i < 4;i++){
                    JLabel nxt = new JLabel();
                    jPanel1.add(nxt);
                    nxt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    nxt.addMouseListener(clickedHandler);
                    //labelPiles.get(i).add(nxt);
                    nxt.setIcon(deck.back);
                    nxt.setBounds(dimensions.get(k)[0] + 90*i, dimensions.get(k)[1], dimensions.get(k)[2], dimensions.get(k)[3]);
                    
                    //this doesn't work 
                    // have 2 copies of the original list of cards -- remove from one for flipping purposes and use the other to re add them back to original here
                    cardPiles.get(k).add((DrawACard)cardPiles.get(k).get(i));
                    labelPiles.get(k).add(nxt);
                }
            }
            countingUsedList.clear();
            countingUsedListLbls.clear();
            counting = false;
            countingHands = true;
        }
        else if(p1.isEmpty() || p1.get(0).getIcon() != deck.back) {
            //player one is showing -- switch to player 2 showing
        
            for (int i = 0; i < p2.size();i++) {
                try{
                    p2.get(i).setIcon(new ImageIcon(deck.changeImage(p2Cards.get(i))));
                } catch (MalformedURLException ex) {
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

    private void jBtnGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGoActionPerformed
        go = go + 1;
        System.out.println(go);
        if(go < 2){
            flip();
        }
        else if(go == 2){
            if(p1ThrewLast){ //if player 1's cards are showing that means they are the second to press go
                                                  // player 2 pressed go also so player 1 must've thrown the last card
                player1.score += 1;
                jLblP1Score.setText(Integer.toString(player1.score));
            }
            else{ //pile2
                player2.score += 2;
                jLblP2Score.setText(Integer.toString(player2.score));
            } 
            JOptionPane.showMessageDialog(CribbageWindow.this, "Since no one has low enough cards to continue counting, the last player to throw a card will "
                                                    + "get a point and we will restart the count from 0. Use your remaining cards to continue the round");
            jLabelCount.setText("0"); 
            count = 0;
            //flip all of the used cards
            for(int k = 0; k < countingUsedListLbls.size();k++){
                countingUsedListLbls.get(k).setIcon(deck.back);
            }
        }
    }//GEN-LAST:event_jBtnGoActionPerformed

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
    private javax.swing.JButton jBtnGo;
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
