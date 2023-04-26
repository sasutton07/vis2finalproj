/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectvis2;

/**
 *
 * @author samsutton
 */
public class DrawACard implements Comparable<DrawACard>{
    private boolean success;
    private String deck_id;
    private Card[] cards;
    private int remaining;
   
    /**
     * gets private list of cards to be used by other classes
     * @return list of card info
     */
    public Card[] getCards(){
        return cards;
    }
    /**
     * gets private field remaining to be used by other classes
     * @return number of cards left in the deck
     */
    public int getRemaining (){
        return remaining;
    }

    @Override
    public int compareTo(DrawACard object) {
        Deck deck = new Deck();
        return deck.getValue(this) - deck.getValue(object);
    }
    
    
}
