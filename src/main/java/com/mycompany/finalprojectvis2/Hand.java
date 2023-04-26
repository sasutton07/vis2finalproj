/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectvis2;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author samsutton
 */
public class Hand {
    //private ArrayList<DrawACard> p1Cards;
    private ArrayList<DrawACard> cards;
    //private DrawACard p1card1, p1card2, p1card3, p1card4, p1card5, p2card1, p2card2, p2card3, p2card4, p2card5;
    private Deck deck;
    
    public Hand(){
        //p1Cards = new ArrayList<>();
        cards = new ArrayList<>();
        deck = new Deck();
        for(int i=0; i < 6;i++){
            cards.add(deck.getACard());
        }
    }
    
   // pass in player 1 array, player 2 array, or the crib
    //make the crib a hand instance in window
    
    /**
     * counts the hand of player1,2, or crib and will add the count to that person's score(if crib whoever has crib will get points)
     * @param hand
     * @return count
     */
    public int countHand(ArrayList<DrawACard> hand){
        int count = 0;
        int totalHandCount = 0;
        //counting hands
        //check for counts of 15
        for(int i = 0; i < hand.size();i++){
            count = count + deck.getValue(hand.get(i));
            if(count == 15){
                totalHandCount = totalHandCount + 2;
            }
        }
        //sort the list 
        Collections.sort(hand);
        //check for runs - must be one after another and same suit?
        
        //these wont work because when i is too low it'll throw index out of bounds
        
        for(int i = hand.size()-1; i>=0; i--){
            //3 in a row
            if((deck.getValue(hand.get(i)) == (deck.getValue(hand.get(i-1))+1) && (deck.getValue(hand.get(i-1)) == (deck.getValue(hand.get(i-2))+1)))){
                //4 in a row
                if(deck.getValue(hand.get(i-2)) == (deck.getValue(hand.get(i-3))+1)){
                    totalHandCount = totalHandCount + 4;
                }
                //add 5 in a row - b/c will have 5 cards with the deck card
                else{
                    totalHandCount = totalHandCount + 3;
                }
            }
        }
        //check for pairs or triples etc - same value
        for(int i = 0; i < hand.size()-1;i++){
            //first and second card have same value
            if(deck.getValue(hand.get(i)) == deck.getValue(hand.get(i+1))){
                // 3 of the same value -- 6 points for each combo of 2 u can make
                if(deck.getValue(hand.get(i+1)) == deck.getValue(hand.get(i+2))){
                    if(deck.getValue(hand.get(i+2)) == deck.getValue(hand.get(i+3)))
                    totalHandCount = totalHandCount + 6;
                }
                else{
                   totalHandCount = totalHandCount + 2; 
                }
            }
            //3 in a row -- 6
            //4 in a row -- 12
        }
        //if all the same suit -- 5 points for 5 cards
        //if all cards in their hand are same suit -- 4 pts
        // each of these above has an associated point value -- add that point value to count
        
        return totalHandCount;
        
    }
    /**
     * gets the private list of cards for other classes to access
     * @return array list of cards
     */
    public ArrayList<DrawACard> getHand(){
        return cards;
    }
}
