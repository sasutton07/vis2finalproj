/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.finalprojectvis2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * window to introduce the user to the game and guide them to other windows
 * @author samsutton
 */
public class WelcomeWindow extends javax.swing.JFrame {

    /**
     * Creates new form WelcomeWindow
     */
    public WelcomeWindow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBtnNewGame = new javax.swing.JButton();
        jBtnHowTo = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        canvas1 = new java.awt.Canvas();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Welcome Window");
        setMinimumSize(new java.awt.Dimension(450, 360));
        getContentPane().setLayout(null);

        jBtnNewGame.setFont(new java.awt.Font("Optima", 0, 13)); // NOI18N
        jBtnNewGame.setText("New Game");
        jBtnNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnNewGameActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnNewGame);
        jBtnNewGame.setBounds(140, 156, 103, 22);

        jBtnHowTo.setFont(new java.awt.Font("Optima", 0, 13)); // NOI18N
        jBtnHowTo.setText("How To Play");
        jBtnHowTo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnHowToActionPerformed(evt);
            }
        });
        getContentPane().add(jBtnHowTo);
        jBtnHowTo.setBounds(140, 190, 104, 22);

        jLabel1.setBackground(new java.awt.Color(153, 153, 255));
        jLabel1.setFont(new java.awt.Font("SignPainter", 1, 55)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Cribbage");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(80, 90, 220, 60);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pngimg.com - cards_PNG8474.jpeg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 140, 240, 160);
        getContentPane().add(canvas1);
        canvas1.setBounds(330, 210, 0, 0);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/243133.jpeg"))); // NOI18N
        getContentPane().add(jLabel3);
        jLabel3.setBounds(270, 20, 130, 130);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * when clicked opens a new CribbageWindow
     * @param evt 
     */
    private void jBtnNewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnNewGameActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        try {
            new CribbageWindow().setVisible(true);
        } catch (InterruptedException ex) {
        }
    }//GEN-LAST:event_jBtnNewGameActionPerformed
    /**
     * when clicked opens a new how to window
     * @param evt 
     */
    private void jBtnHowToActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnHowToActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new HowToWindow().setVisible(true);
    }//GEN-LAST:event_jBtnHowToActionPerformed

    //action performed for button click to bring you to main game screen 
    
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
            java.util.logging.Logger.getLogger(WelcomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WelcomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WelcomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WelcomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WelcomeWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas canvas1;
    private javax.swing.JButton jBtnHowTo;
    private javax.swing.JButton jBtnNewGame;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
