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

/**
 *
 * @author samsutton
 */
public class CribbageWindow extends javax.swing.JFrame {

    private ArrayList<JLabel> p1;
    private ArrayList<JLabel> p2;
    //boolean for which game mode we're in 
    boolean round1;  //getting rid of a card round
    boolean counting; // counting to 31 back and forth 
    boolean countingHands; //counting each hands' values
    boolean p1HasCrib; // to know which player has the crib -- true = p1 has crib(ppints will go to them)
    private ArrayList<DrawACard> p1Cards,p2Cards;

    public class MouseClickHandler extends java.awt.event.MouseAdapter {
        //if in round1 
        
        //checks for a click on each one of the cards of the player whose cards are showing
        // get the label that was clicked and remove it 
        // get that card information and add it to the crib array list for each player -- so this should only happen twice
        
        // if in counting round
        // check which card they clicked from their hand 
        // move that card up a little so its not in line with their hand
        // takes its value and display it as current count
        // if switched to other player's hand -- do the same stuff
        // if the score hits 15 -- add 2 to that player's overall score
        // have an arraylist of the values of the cards used in the current count
        // check this list for runs and pairs and adjust the player's scores accordingly 
        // once we hit 31 or no one has any eligible cards -- adjust scores and set count back to zero
        // stop when both players are out of cards 
        // set counting to false -- no longer in this round 
        
  
    }
    
    /**
     * Creates new form CribbageWindow
     */
    public CribbageWindow() {
        initComponents();
        p1 = new ArrayList<>();
        p2 = new ArrayList<>();
        p1.add(jLblp11);
        p1.add(jLblp12);
        p1.add(jLblp13);
        p1.add(jLblp14);

        p2.add(jLblp21);
        p2.add(jLblp22);
        p2.add(jLblp23);
        p2.add(jLblp24);
       
        Deck deck = new Deck();
        
        //get images of cards from Hand and initialize them to show for player 1
        Hand hand = new Hand();
        p1Cards = hand.getHand("p1");
        p2Cards = hand.getHand("p2");
        
        try {
            for (int i = 0; i < p1Cards.size(); i++) {
                p1.get(i).setIcon(new ImageIcon(deck.changeImage(p1Cards.get(i))));
        }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CribbageWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        //have a message that says whenever ready, select one card to dispose of to the crib
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
        jLblp24 = new javax.swing.JLabel();
        jLblp12 = new javax.swing.JLabel();
        jLblp14 = new javax.swing.JLabel();
        jLblp11 = new javax.swing.JLabel();
        jLblp21 = new javax.swing.JLabel();
        jLblp22 = new javax.swing.JLabel();
        jLblp23 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLblp25 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jLblp13.setText("front");
        jLblp13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLblp13);
        jLblp13.setBounds(200, 330, 70, 110);

        jLblp24.setBackground(new java.awt.Color(255, 255, 255));
        jLblp24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finalprojectvis2/backOfDeck (4).jpeg"))); // NOI18N
        jLblp24.setText("back");
        jLblp24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLblp24);
        jLblp24.setBounds(560, 180, 80, 110);

        jLblp12.setText("front");
        jLblp12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLblp12);
        jLblp12.setBounds(110, 330, 70, 110);

        jLblp14.setText("front");
        jLblp14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLblp14);
        jLblp14.setBounds(290, 330, 70, 110);

        jLblp11.setBackground(new java.awt.Color(255, 255, 255));
        jLblp11.setText("front");
        jLblp11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLblp11);
        jLblp11.setBounds(20, 330, 70, 110);

        jLblp21.setBackground(new java.awt.Color(255, 255, 255));
        jLblp21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finalprojectvis2/backOfDeck (4).jpeg"))); // NOI18N
        jLblp21.setText("back");
        jLblp21.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLblp21);
        jLblp21.setBounds(20, 20, 80, 110);

        jLblp22.setBackground(new java.awt.Color(255, 255, 255));
        jLblp22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finalprojectvis2/backOfDeck (4).jpeg"))); // NOI18N
        jLblp22.setText("back");
        jLblp22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLblp22);
        jLblp22.setBounds(110, 20, 80, 110);

        jLblp23.setBackground(new java.awt.Color(255, 255, 255));
        jLblp23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finalprojectvis2/backOfDeck (4).jpeg"))); // NOI18N
        jLblp23.setText("back");
        jLblp23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLblp23);
        jLblp23.setBounds(200, 20, 80, 110);

        jButton1.setText("Switch Players");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(130, 210, 116, 40);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Deck");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(550, 160, 90, 20);

        jLabel2.setFont(new java.awt.Font("Hiragino Sans GB", 0, 30)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("0");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel2);
        jLabel2.setBounds(660, 340, 80, 90);

        jLabel3.setFont(new java.awt.Font("Hiragino Sans GB", 0, 30)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("0");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.add(jLabel3);
        jLabel3.setBounds(660, 40, 80, 90);

        jLabel4.setText("Player 1 Score");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(660, 10, 90, 20);

        jLabel5.setText("Player 2 Score");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(660, 310, 90, 20);

        jLblp25.setBackground(new java.awt.Color(255, 255, 255));
        jLblp25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/finalprojectvis2/backOfDeck (4).jpeg"))); // NOI18N
        jLblp25.setText("back");
        jLblp25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jLblp25);
        jLblp25.setBounds(290, 20, 80, 110);

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

    
    /**
     * when clicked, the cards for the other player will show and the player whose cards are currently showing will be flipped
     * @param evt 
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //this will be changed to changing the cards' images based on the card that was dealt for that hand
        if (p1.get(0).getText() == "front") {
            //player one is showing -- switch to player 2 showing
            for (JLabel card : p2) {
                card.setText("front");
            }
            for (JLabel c : p1) {
                c.setText("back");
            }
        } 
        //player 2 showing -- switch to player 1
        else if (p1.get(0).getText() == "back") {
            for (JLabel card : p2) {
                card.setText("back"); // p2 cards not showing
            }
            for (JLabel c : p1) {
                c.setText("front"); // p1 cards showing
            }

        }


    }//GEN-LAST:event_jButton1ActionPerformed

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
                new CribbageWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLblp11;
    private javax.swing.JLabel jLblp12;
    private javax.swing.JLabel jLblp13;
    private javax.swing.JLabel jLblp14;
    private javax.swing.JLabel jLblp21;
    private javax.swing.JLabel jLblp22;
    private javax.swing.JLabel jLblp23;
    private javax.swing.JLabel jLblp24;
    private javax.swing.JLabel jLblp25;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
