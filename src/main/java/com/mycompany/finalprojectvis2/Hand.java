/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectvis2;

import java.util.ArrayList;

/**
 *
 * @author samsutton
 */
public class Hand {
    private ArrayList<DrawACard> p1Cards;
    private ArrayList<DrawACard> p2Cards;
    private DrawACard p1card1, p1card2, p1card3, p1card4, p1card5, p2card1, p2card2, p2card3, p2card4, p2card5;
    
    
    public Hand(){
        p1Cards.add(p1card1);
        p1Cards.add(p1card2);
        p1Cards.add(p1card3);
        p1Cards.add(p1card4);
        p1Cards.add(p1card5);
        
        p2Cards.add(p2card1);
        p2Cards.add(p2card2);
        p2Cards.add(p2card3);
        p2Cards.add(p2card4);
        p2Cards.add(p2card5);
        for(DrawACard card:p1Cards){
            //card = Deck.getACard();
        }
    }
    
   // pass in player 1 array, player 2 array, or the crib
    public int countHand(ArrayList<DrawACard> player){
        int count = 0;
        //counting hands
        //check for counts of 15
        //check for runs - must be one after another and same suit?
        //check for pairs or triples etc - same value
        // each of these above has an associated point value -- add that point value to count
        
        return count;
        
    }
}
