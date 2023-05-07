/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectvis2;

/**
 * used to get a new deck from the api -- holds all the information the api returns and has getters for values
 */


public class NewDeck {
    private boolean success;
    private String deck_id;
    private boolean shuffled;
    private int remaining;
    
    public String getDeckID(){
        return deck_id;
    }
    
    public int getRemaining(){
        return remaining;
    }
}
