package com.mycompany.finalprojectvis2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Contains all of the GUI info from JFrame Component, handles mouse clicks in round1 and counting, 
 * displays relevant messages to the user and calls and handles output from method calls from other classes
 * Also checks to see if a player has one and handles that
 * Flips over cards after each is played
 * Command line interface in main
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
    private static ArrayList<SingleCard> p1Cards,p2Cards,crib; //holds the card info (image, suit, etc from api)
    private Hand player1Hand,player2Hand;
    private ArrayList<ArrayList> cardPiles,cardPiles2;
    private static Player player1, player2;
    private int count = 0;
    private ArrayList<SingleCard> countList;
    private ArrayList<SingleCard> countingUsedList;
    private ArrayList<JLabel> countingUsedListLbls;
    private ArrayList<int[]> dimensions; //used to draw new jLabels by original position
    private int[] p1dimensions, p2dimensions;
    private boolean p1ThrewLast = false;
    private boolean p2ThrewLast = false;
    public SingleCard deckCard; 
    private ArrayList<JLabel> cribLbls;
    private int go; //count of how many times go has been pressed - once both players press it new count (no one has cards low enough)

    /**
     * Handles mouse clicks accordingly by round
     * @author samsutton
     */
    public class MouseClickHandler extends java.awt.event.MouseAdapter {

        /**
         * checks for a mouse click from the user and handles accordingly
         * @param evt
         */
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            
            //if in round1 -- round where we get rid of a card
            JLabel comp;
            SingleCard card;
            p1ThrewLast = false;
            p2ThrewLast = false;
            int x = evt.getXOnScreen();
            JLabel componentClicked = (JLabel) evt.getComponent();
            if(round1 == true){
                //checks for a click on each one of the cards of the player whose cards are showing
                if (componentClicked.getIcon() != deck.back && componentClicked.getIcon() != null && crib.size()<4) {
                    // get the label that was clicked and remove it 
                    for(int i = 0; i < labelPiles.size();i++){
                        for(int j = 0; j < labelPiles.get(i).size();j++){
                            if(labelPiles.get(i).get(j) == componentClicked){ // i = which pile , j = which card in pile
                                comp = (JLabel)labelPiles.get(i).get(j);
                                card = (SingleCard) cardPiles.get(i).get(j);
                                JLabel lastLabel = (JLabel)labelPiles.get(i).get(labelPiles.get(i).size()-1);
                                //redraws the cards so there is no empty space where the crib was
                                //if its not the last card in the row, move that last card to this empty spot and remove image from last
                                for(int h = 0; h < labelPiles.get(i).size() - (j+1);h++){
                                    try {
                                        comp.setIcon(new ImageIcon(deck.changeImage((SingleCard)cardPiles.get(i).get(h+1))));
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
                    //secondary list since we are removing from the original so flipping works
                    //we'll use this list to add them back to the original after counting is over and we can put cards back to original spots
                    cardPiles2 = new ArrayList<>();
                    ArrayList<SingleCard> tmp = new ArrayList<>();
                    ArrayList<SingleCard> tmp2 = new ArrayList<>();
                     //for(int m = 0; m < cardPiles.size();m++){
                     for(int p = 0; p < cardPiles.get(0).size();p++){
                         tmp.add((SingleCard)cardPiles.get(0).get(p));
                     }
                     for(int p = 0; p < cardPiles.get(1).size();p++){
                         tmp2.add((SingleCard)cardPiles.get(1).get(p));
                     }
                     cardPiles2.add(tmp);
                     cardPiles2.add(tmp2);
                    round1 = false;
                    deckCard = deck.getACard();
                    try {
                        jLblDeck.setIcon(new ImageIcon(deck.changeImage(deckCard)));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(CribbageWindow.this, """
                                                                       We will now begin the counting round. You will switch back and forth trying to 
                                                                       count to 31. You will recieve points for hitting 15, getting a run, pair, or being the 
                                                                       last one to put a card down when neither player can  get to 31. Click a card to begin counting.""");
                    counting = true;
                }
               
            }
        
        
        // if in counting round
        if(counting == true){
            if (componentClicked.getIcon() != deck.back && componentClicked.getIcon() != null) {
                for(int i = 0; i < labelPiles.size();i++){
                    for(int j = 0; j < labelPiles.get(i).size();j++){
                        if(labelPiles.get(i).get(j) == componentClicked){ // i = which pile , j = which card in pile
                            comp = (JLabel)labelPiles.get(i).get(j);
                            card = (SingleCard) cardPiles.get(i).get(j);
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
                                jPanel1.remove(comp);
                                jPanel1.repaint();
                                JLabel nxt = new JLabel();
                                jPanel1.add(nxt);
                                nxt.addMouseListener(clickedHandler);
                                try {
                                    nxt.setIcon(new ImageIcon(deck.changeImage(card)));
                                } catch (MalformedURLException ex) {
                                    Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                                }
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
                                    //compare the value(ex: king, 8, etc) of the newly added card and the one thrown before it -- checks if theyre the same
                                    if(countList.get(countList.size()-2).getCards()[0].getValue() == null ? card.getCards()[0].getValue() == null : countList.get(countList.size()-2).getCards()[0].getValue().equals(card.getCards()[0].getValue())){
                                        //only check for 3 in a row if at least 3 cards have been thrown
                                        if(countList.size()>2){
                                            //3 cards the same in a row -- they get 6 points instead of 2 for normal pair
                                            if(countList.get(countList.size()-3).getCards()[0].getValue().equals(countList.get(countList.size()-1).getCards()[0].getValue())){
                                                if(i == 0){ //pile1 threw down the card -- they get the point for a pair
                                                    player1.score += 6;
                                                    jLblP1Score.setText(Integer.toString(player1.score));
                                                    checkFor121();
                                                }
                                                else{ //pile2  threw down the card -- they get the point for a pair
                                                    player2.score += 6;
                                                    jLblP2Score.setText(Integer.toString(player2.score));
                                                    checkFor121();
                                                }
                                            }
                                             //just 2 cards in a row -- regular pair -- 2 points
                                            else{
                                                if(i == 0){ //pile1 threw down the card -- they get the point for a pair
                                                    player1.score += 2;
                                                    jLblP1Score.setText(Integer.toString(player1.score));
                                                    checkFor121();
                                                }
                                                else{ //pile2  threw down the card -- they get the point for a pair
                                                    player2.score += 2;
                                                    jLblP2Score.setText(Integer.toString(player2.score));
                                                    checkFor121();
                                                }
                                            }
                                        }
                                         //just 2 cards in a row -- regular pair -- 2 points
                                        else{
                                            if(i == 0){ //pile1 threw down the card -- they get the point for a pair
                                                player1.score += 2;
                                                jLblP1Score.setText(Integer.toString(player1.score));
                                                checkFor121();
                                            }
                                            else{ //pile2  threw down the card -- they get the point for a pair
                                                player2.score += 2;
                                                jLblP2Score.setText(Integer.toString(player2.score));
                                                checkFor121();
                                            }
                                        }
                                       
                                    }
                                    if(count == 15){ //player who just made the count get to 15 gets 2 points
                                        if(i == 0){ //pile1 threw down the card -- they get the points for getting to 15
                                            player1.score += 2;
                                            jLblP1Score.setText(Integer.toString(player1.score));
                                            checkFor121();
                                        }
                                        else{ //pile2  threw down the card -- they get the points for getting to 15
                                            player2.score += 2;
                                            jLblP2Score.setText(Integer.toString(player2.score));
                                            checkFor121();
                                        }
                                    }
                                    //run 
                                    if(countList.size()>2){ //must be at least 3 cards to look for a run(min size 3)
                                        boolean gaps = false;
                                        boolean runFound = false;
                                        for(int c = countList.size(); c > 2;c--){ //index backwards so we can break at the highest run instead of finding a run of 3 setting scores then finding out its a bigger run
                                            if(!runFound){
                                                ArrayList<SingleCard> listToBeSorted = new ArrayList<>();
                                                //add last 3 (4,up to size) to a list and sort it 
                                                for(int y = 0; y < c;y++){ //if c = 3, check last, second to last, third to last
                                                    listToBeSorted.add(countList.get((countList.size()-1)-y));
                                                } 
                                                Collections.sort(listToBeSorted);
                                                //check for gaps
                                                for(int p = listToBeSorted.size()-1; p >=1;p--){
                                                    //for each card in the sorted list, if the first's value doesn't equal the next one's value + 1(ascending), then there is a gap
                                                    if((deck.getValueUniqNum(listToBeSorted.get(p))) == (deck.getValueUniqNum(listToBeSorted.get(p-1))+1)){
                                                        gaps = false;
                                                    }
                                                    else{ //change gaps to true if these are not in order
                                                        gaps = true;
                                                        break;
                                                    }
                                                }
                                                //if no gaps, add c to the player's score
                                                //if gaps is still false after checking the whole list then they are in order
                                                if(!gaps){
                                                    if(i == 0){ //pile1 threw down the card -- they get the points for getting c in a row
                                                        player1.score += c;
                                                        jLblP1Score.setText(Integer.toString(player1.score));
                                                        checkFor121();
                                                    }
                                                    else{ //pile2  threw down the card -- they get the points for getting c in a row
                                                        player2.score += c;
                                                        jLblP2Score.setText(Integer.toString(player2.score));
                                                        checkFor121();
                                                    }
                                                    runFound = true;
                                                    c = 0;
                                                    gaps = true;
                                                }
                                            }
                                        }
                                    }
                                    if(count == 31){
                                        if(i == 0){ //pile1 threw down the card -- they get the points for getting to 31
                                            player1.score += 2;
                                            jLblP1Score.setText(Integer.toString(player1.score));
                                            checkFor121();
                                        }
                                        else{ //pile2  threw down the card -- they get the points for getting to 31
                                            player2.score += 2;
                                            jLblP2Score.setText(Integer.toString(player2.score));
                                            checkFor121();
                                        }
                                        jLabelCount.setText("0");
                                        count = 0;
                                        countList.clear(); 
                                        //flip all of the used cards
                                        for(int k = 0; k < countingUsedListLbls.size();k++){
                                            countingUsedListLbls.get(k).setIcon(deck.back);
                                        }
                                        JOptionPane.showMessageDialog(CribbageWindow.this, """
                                                                                           You counted to 31! We will start a new round of counting with your 
                                                                                           remaining cards if you have any or move onto counting hands if all cards have been used.
                                                                                           """);
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
        cribLbls = new ArrayList<>();
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
        player2.hasCrib = true;
       
        deck = Deck.getInstance();
        crib = new ArrayList<>();
        //crib should be the size of a full hand(4) so 2 get added from the deck when dealt and the other 2 will be the ones the players dispose of
        crib.add(deck.getACard());
        crib.add(deck.getACard());
        
        
        for (JLabel label : p1) {
            label.addMouseListener(clickedHandler);
        }
        for (JLabel label : p2) {
            label.addMouseListener(clickedHandler);
        }
        
       
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
        
        JOptionPane.showMessageDialog(this, """
                                            Whenever ready, each player should select one card 
                                            to get rid of. This card will be added to the crib
                                                                  """);
        round1 = true;

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
        setTitle("Main Game");
        setMinimumSize(new java.awt.Dimension(750, 510));
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(241, 242, 249));
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

        changePlayerBtn.setBackground(new java.awt.Color(229, 224, 224));
        changePlayerBtn.setFont(new java.awt.Font("Optima", 0, 13)); // NOI18N
        changePlayerBtn.setText("Switch Players");
        changePlayerBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        changePlayerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePlayerBtnActionPerformed(evt);
            }
        });
        jPanel1.add(changePlayerBtn);
        changePlayerBtn.setBounds(150, 210, 110, 40);

        jLblCribLbl.setFont(new java.awt.Font("Optima", 0, 13)); // NOI18N
        jLblCribLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLblCribLbl);
        jLblCribLbl.setBounds(530, 160, 90, 20);

        jLblP2Score.setFont(new java.awt.Font("Khmer MN", 0, 36)); // NOI18N
        jLblP2Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblP2Score.setText("0");
        jLblP2Score.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLblP2Score);
        jLblP2Score.setBounds(660, 50, 80, 90);

        jLblP1Score.setFont(new java.awt.Font("Khmer MN", 0, 36)); // NOI18N
        jLblP1Score.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLblP1Score.setText("0");
        jLblP1Score.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLblP1Score);
        jLblP1Score.setBounds(660, 350, 80, 90);

        jLabel4.setFont(new java.awt.Font("Optima", 0, 13)); // NOI18N
        jLabel4.setText("Player 1 Score");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(660, 320, 90, 20);

        jLabel5.setFont(new java.awt.Font("Optima", 0, 13)); // NOI18N
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
        jLblCribCard.setFont(new java.awt.Font("Khmer MN", 0, 13)); // NOI18N
        jPanel1.add(jLblCribCard);
        jLblCribCard.setBounds(540, 180, 80, 110);

        jLabel6.setFont(new java.awt.Font("Optima", 0, 13)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Deck");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(650, 160, 90, 20);

        jLblCounterLbl.setFont(new java.awt.Font("Optima", 0, 13)); // NOI18N
        jPanel1.add(jLblCounterLbl);
        jLblCounterLbl.setBounds(280, 190, 110, 20);

        jLabelCount.setFont(new java.awt.Font("Khmer MN", 0, 13)); // NOI18N
        jLabelCount.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabelCount);
        jLabelCount.setBounds(320, 220, 30, 40);

        jBtnGo.setBackground(new java.awt.Color(204, 204, 255));
        jBtnGo.setFont(new java.awt.Font("Optima", 0, 13)); // NOI18N
        jBtnGo.setText("GO");
        jBtnGo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jBtnGo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGoActionPerformed(evt);
            }
        });
        jPanel1.add(jBtnGo);
        jBtnGo.setBounds(20, 220, 80, 20);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 6, 770, 470);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * when we are in the counting hands round this method calls the countHand method on each hand/crib, 
     * displays messages, and adjusts scores accordingly 
     * this is also the end of a round, so it also resets everything but the scores at the end and hands our new cards
     */
    private void countingHandsRound(){
        if(countingHands == true){
            //player that has the crib goes last
            JOptionPane.showMessageDialog(CribbageWindow.this, "counting hands");
            p1Cards.add(deckCard);
            p2Cards.add(deckCard);
            crib.add(deckCard);
            int p1handscore = player1.hand.countHand(p1Cards,deckCard);
            player1.score = player1.score + p1handscore;
            int p2handscore = player2.hand.countHand(p2Cards,deckCard);
            player2.score = player2.score + p2handscore;
            int cribscore = player2.hand.countHand(crib,deckCard);
            if(player2.hasCrib){
                //show player 1's cards
                for(int i = 0; i < p1.size();i++){
                    try {
                        p1.get(i).setIcon(new ImageIcon(deck.changeImage(p1Cards.get(i))));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                JOptionPane.showMessageDialog(CribbageWindow.this, "We will now count player 1's hand and update the score");
                jLblP1Score.setText(Integer.toString(player1.score));
                checkFor121();
                JOptionPane.showMessageDialog(CribbageWindow.this, "Player 1 has received " + p1handscore +  " points");
                JOptionPane.showMessageDialog(CribbageWindow.this, "We will now count player 2's hand and update the score");
                flip();
                jLblP2Score.setText(Integer.toString(player2.score));
                checkFor121();
                JOptionPane.showMessageDialog(CribbageWindow.this, "Player 2 has received " + p2handscore +  " points");
                jPanel1.remove(jLblCribCard);
                jLblP2Score.setText(Integer.toString(player2.score));
                checkFor121();
                crib.remove(deckCard);
                //displays the crib cards
                for(int j = 0; j < crib.size();j++){
                    JLabel nxt = new JLabel();
                    cribLbls.add(nxt);
                    jPanel1.add(nxt);
                    nxt.setBounds(540 - j*20, 180, 80, 110);
                    try { 
                        nxt.setIcon(new ImageIcon(deck.changeImage(crib.get(j))));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                player2.score = player2.score + cribscore;
                JOptionPane.showMessageDialog(CribbageWindow.this, "Player 2 has received " +  cribscore +  " points from the crib");
                jLblP2Score.setText(Integer.toString(player2.score));
                checkFor121();
            }else if(player1.hasCrib){
                //show player 2's hands 
                for(int i = 0; i < p2.size();i++){
                    try {
                        p2.get(i).setIcon(new ImageIcon(deck.changeImage(p2Cards.get(i))));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                JOptionPane.showMessageDialog(CribbageWindow.this, "We will now count player 2's hand and update the score");
                jLblP2Score.setText(Integer.toString(player2.score));
                checkFor121();
                JOptionPane.showMessageDialog(CribbageWindow.this, "Player 2 has received " + p2handscore +  " points");
                JOptionPane.showMessageDialog(CribbageWindow.this, "We will now count player 1's hand and update the score");
                flip(); 
                jLblP1Score.setText(Integer.toString(player1.score));
                checkFor121();
                JOptionPane.showMessageDialog(CribbageWindow.this, "Player 1 has received " + p1handscore +  " points");
                jLblP1Score.setText(Integer.toString(player1.score));
                checkFor121();
                //lay out the crib cards
                jPanel1.remove(jLblCribCard);
                crib.remove(deckCard);
                for(int j = 0; j < crib.size();j++){
                    JLabel nxt = new JLabel();
                    cribLbls.add(nxt);
                    jPanel1.add(nxt);
                    nxt.setBounds(540 - j*20, 180, 80, 110);
                    try {
                        nxt.setIcon(new ImageIcon(deck.changeImage(crib.get(j))));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                player1.score = player1.score + cribscore;
                JOptionPane.showMessageDialog(CribbageWindow.this, "Player 1 has received " + cribscore +  " points from the crib");
                jLblP1Score.setText(Integer.toString(player1.score));
                checkFor121();
            }
        }
        
        //restart everything from the beginning - deal new cards just keep scores
        countingHands = false;
        //return all the cards drawn back to the deck 
        deck.returnToDeck();
        deckCard = deck.getACard();
        //clear all the card lists since we are shuffling and redrawing
        p1Cards.clear();
        p2Cards.clear();
        crib.clear(); 
        for(int j = 0; j < cribLbls.size();j++){
            jPanel1.remove(cribLbls.get(j));
        }
        jPanel1.repaint();
        cribLbls.clear();
        jLblDeck.setIcon(deck.back);
        go = 0;
        round1 = true;
        counting = false;
        countingHands = false;
        p1Cards = player1.hand.getHand();
        p2Cards = player2.hand.getHand();
        //add the 5th card back
        p1.add(jLblp15);
        p2.add(jLblp25);
        //switch who has the crib
        if(player1.hasCrib){
            player2.hasCrib = true;
            player1.hasCrib = false;
            //when p2 has the crib, p1 counts first so their cards will show to start
            try {
                for (int i = 0; i < p1.size(); i++) {
                    p1.get(i).setIcon(new ImageIcon(deck.changeImage(p1Cards.get(i))));
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            player2.hasCrib = false;
            player1.hasCrib = true;
            //when p1 has the crib, p2 counts first so their cards will show to start
            try {
                for (int i = 0; i < p2.size(); i++) {
                    p2.get(i).setIcon(new ImageIcon(deck.changeImage(p2Cards.get(i))));
                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        crib.add(deck.getACard());
        crib.add(deck.getACard());
       

    }
    //these are used when creating you win message -- couldn't reference this where these are implemented
    /**
     * used for selection of main menu in you win message -- creates a new welcomeWindow and disposes of this one
     */
    private void openNewWelcomeWindow(){
        this.setVisible(false);
        new WelcomeWindow().setVisible(true);
    }
     /**
     * used for selection of New Game in you win message -- creates a new CribbageWindow and disposes of this one
     */
    private void openNewMainWindow(){
        this.setVisible(false);
        try {
            new CribbageWindow().setVisible(true);
        } catch (InterruptedException ex) {
            Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     /**
     * used for selection of quit in you win message -- gets rid of this window
     */
    private void closeWindows(){
        this.setVisible(false);
    }
    /**
     * called after each score is updated to see if they have won(121 points or more)
     */
    private void checkFor121(){
        if(player1.score >= 121 || player2.score >= 121){
            ImageIcon icon = new ImageIcon(getClass().getResource("/transparent_icons_222.jpeg"));
            Object[] options = {"Main Menu",
                                "Play Again",
                                "Quit"};
            int n = 0;
            if(player1.score >= 121){
                n = JOptionPane.showOptionDialog(null,"Player 1 Wins!!","Win Message",  
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    icon,//custom Icon
                    options,//the titles of buttons
                    options[2]);//default button title
            }else if(player2.score >= 121){
                n = JOptionPane.showOptionDialog(null,"Player 2 Wins!!","Win Message",  
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    icon,//custom Icon
                    options,//the titles of buttons
                    options[2]);//default button title
            }
            switch (n) {
                //which button has been clicked 
                case JOptionPane.YES_OPTION -> {
                    //main menu
                    JOptionPane.getRootFrame().dispose();
                    openNewWelcomeWindow();
                }
                case JOptionPane.NO_OPTION -> {
                    //new game
                    JOptionPane.getRootFrame().dispose();
                    openNewMainWindow();
                }
                case JOptionPane.CANCEL_OPTION -> {
                    //close
                    JOptionPane.getRootFrame().dispose();
                    closeWindows();
                }
                default -> {
                }
            }
        }
    }
    /**
     * automatically switches whose cards are showing after each card is played or if switch player button is clicked
     */
    private void flip(){
        if(p1.isEmpty() && p2.isEmpty()){ //no one has any cards left
            JOptionPane.showMessageDialog(this, """ 
                                                Since no one has cards left, the counting round is over. The player that put down the
                                               last card will get a point. Each player's hand will now be counted and points will be awarded.""");
            if(p1ThrewLast == true){
                player1.score += 1;
                jLblP1Score.setText(Integer.toString(player1.score));
                checkFor121();
            }
            else if(p2ThrewLast == true){
                player2.score += 1;
                jLblP2Score.setText(Integer.toString(player2.score));
                checkFor121();
            }
            jLabelCount.setText(null);
            jLblCounterLbl.setText(null);
            count = 0;
            countList.clear(); 
            //whoever threw the last card gets the point 
            //message that that person gets a point and the counting round is over and we will now count hands since everyone
            //is out of cards
            //flip all cards and put them in their original positions
            for(JLabel card:countingUsedListLbls){
                card.setIcon(null);
                jPanel1.remove(card);
                jPanel1.repaint();
            }
            //cards back in originial position
            for(int k = 0; k < 2;k++){
                for(int i = 0; i < 4;i++){
                    JLabel nxt = new JLabel();
                    jPanel1.add(nxt);
                    nxt.addMouseListener(clickedHandler);
                    nxt.setIcon(deck.back);
                    nxt.setBounds(dimensions.get(k)[0] + 90*i, dimensions.get(k)[1], dimensions.get(k)[2], dimensions.get(k)[3]);
                   
                    cardPiles.get(k).add((SingleCard)cardPiles2.get(k).get(i));
                    System.out.println("--------");
                    labelPiles.get(k).add(nxt);
                }
            }
            countingUsedList.clear();
            countingUsedListLbls.clear();
            counting = false;
            countingHands = true;
            countingHandsRound();
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
    /**
     * when clicked, go will be incremented and if its 2 it displays a message b/c no one has low enough cards
     * @param evt 
     */
    private void jBtnGoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGoActionPerformed
        go = go + 1;
        if(go < 2){
            flip();
        }
        else if(go == 2){
            go = 0;
            if(p1ThrewLast){ //if player 1's cards are showing that means they are the second to press go
                                                  // player 2 pressed go also so player 1 must've thrown the last card
                player1.score += 1;
                jLblP1Score.setText(Integer.toString(player1.score));
                checkFor121();
            }
            else{ //pile2
                player2.score += 1;
                jLblP2Score.setText(Integer.toString(player2.score));
                checkFor121();
            } 
            JOptionPane.showMessageDialog(CribbageWindow.this, """
                                                               Since no one has low enough cards to continue counting, the last player to throw a card will 
                                                               get a point and we will restart the count from 0. Use your remaining cards to continue the round
                                                                                                                                                    """);
            jLabelCount.setText("0"); 
            count = 0;
            countList.clear(); 
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
        // Command Line interface
        Scanner sc = new Scanner(System.in);
        System.out.println("""
                           At any point type 'player1' or 'player2' in the command line to be told what your score would be if you 
                           removed each card. This may be especially helpful when deciding which card elimination will give you 
                           the most points. Don't entirely rely on this though. Keep in mind that the deck card that will be 
                           flipped after cards are removed can be very helpful in scoring points. 
                           Or say --help for more info
                           """);
        String input = sc.nextLine();  // Read user input
        if("player1".equals(input)){
            Collections.sort(p1Cards);
            for (int i = 0; i < p1Cards.size();i++) {
                SingleCard card = p1Cards.get(i);
                p1Cards.remove(card);
                int score = player1.hand.countHand(p1Cards, null);
                System.out.println("If you get rid of card " + card.getCards()[0].getCode() + " the hand will score " + score + " points");
                p1Cards.add(i,card);
            }
        }
        else if("player2".equals(input)){
            for (int i = 0; i < p2Cards.size();i++) {
                SingleCard card = p2Cards.get(i);
                p2Cards.remove(card);
                int score = player2.hand.countHand(p2Cards, null);
                System.out.println("If you get rid of card " + card.getCards()[0].getCode() + " the hand will score " + score + " points");
                p2Cards.add(card);
            }
        }
        else if("--help".equals(input)){
            System.out.println("""
                           This interface will help you determine what card in your hand to get rid of.
                           What to enter: say either "player1" or "player2"
                           What info you'll get: the program will take the hand you have requested and
                               count the hand for every combination of 4. It will tell you how many points
                               your hand will score without a certain card. For example, "if you get rid of
                               10S, your hand will score 3 points. And you will get this output for every 
                               possibility of a card that would be given up.
                            Disclaimer: The deck will draw a card after each player has gotten rid of one,
                                so keep an eye out for possible points. For example, if you have a 6 and a 7, 
                                they may not be helpful in your current hand, but you could get a 5 or 8 from
                                the deck that would give you 3 points.
                           """);
        }
        else{
             System.out.println("invalid input please try again");
        }
        sc.close();
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
