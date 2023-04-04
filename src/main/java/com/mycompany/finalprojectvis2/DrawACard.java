/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectvis2;

/**
 *
 * @author samsutton
 */
public class DrawACard {
    private boolean success;
    private String deck_id;
    private Card[] cards;
    private int remaining;
   
    public Card[] getCards(){
        return cards;
    }
    
    public int getRemaining (){
        return remaining;
    }
}
