/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectvis2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * holds information about a players hand and counts it
 * @author samsutton
 */
public class Hand {
    //private ArrayList<DrawACard> p1Cards;
    private ArrayList<SingleCard> cards;
    //private SingleCard p1card1, p1card2, p1card3, p1card4, p1card5, p2card1, p2card2, p2card3, p2card4, p2card5;
    private Deck deck;
    
    public Hand(){
        //p1Cards = new ArrayList<>();
        cards = new ArrayList<>();
        deck = Deck.getInstance();
        
    }
    
   // pass in player 1 array, player 2 array, or the crib
    //make the crib a hand instance in window
    
    /**
     * counts the hand of player1,2, or crib and will add the count to that person's score(if crib whoever has crib will get points)
     * @param hand
     * @return count
     */
    public int countHand(ArrayList<SingleCard> hand, SingleCard deckCard){
        int count = 0;
        int totalHandCount = 0;
        //counting hands
        //check for counts of 15
        for(int i = 0; i < hand.size();i++){
            //for each card compare every other card in the list to it -- if they add to 15 they get points
            //cant tell if they have already been compared or not -- counts twice
            for(int j = i+1; j < hand.size();j++){
                if(j != i){
                    if(deck.getValue(hand.get(j))+deck.getValue(hand.get(i)) == 15){
                        totalHandCount = totalHandCount + 2;
                        System.out.println("a 15");
                        System.out.println(totalHandCount);
                    }
                    //check for 15 using 3 cards
                    for(int k = j+1; k < hand.size();k++){
                        if(k != j && k != i){
                            if(deck.getValue(hand.get(j))+deck.getValue(hand.get(i))+deck.getValue(hand.get(k)) == 15){
                                totalHandCount = totalHandCount + 2;
                                System.out.println("a 15 using 3");
                                System.out.println(totalHandCount);
                            }
                        }
                        //check for 15 using 4 cards
                        //wrong
                        for(int h = k+1; h < hand.size();h++){
                            if((h != k) && (h != j) && (h != i)){
                                if(deck.getValue(hand.get(j))+deck.getValue(hand.get(i))+deck.getValue(hand.get(k))+deck.getValue(hand.get(h)) == 15){
                                    totalHandCount = totalHandCount + 2;
                                    System.out.println("a 15 using 4");
                                    System.out.println(totalHandCount);
                                }
                            }
//                            //check for 15 of all 5 cards
//                            if(deck.getValue(hand.get(j))+deck.getValue(hand.get(i))+deck.getValue(hand.get(k))+deck.getValue(hand.get(h))+deck.getValue(hand.get(hand.size()-1)) == 15){
//                                totalHandCount = totalHandCount + 2;
//                                System.out.println("a 15 using 5");
//                                System.out.println(totalHandCount);
//                            }   
                        }
                    }
                }
            }
        }
        
        
        //sort the list 
        Collections.sort(hand);
        //check for runs - must be one after another 
        int pair = 0;
        for(int k = hand.size()-1; k>=2; k--){
            int next = k-1;
            int check2 = k-2;
            int check3 = 1;
            int check4 = 2;
            if(k > 2){
                check3 = k-3;
            }
            if(k > 3){
                check4 = k-4;
            }
            //3 in a row
            //this is so that if we have 6,6,7,8 for example itll still count
            if(k == hand.size() - 1){ //only check for the pairs once and check all cards in the hand
                if(deck.getValueUniqNum(hand.get(k)) == deck.getValueUniqNum(hand.get(k-1))){
                    if(k > 1){
                        next = k - 2;
                    }
                    if(k > 2){
                        check2 = k - 3;
                    }
                    if(k > 3){
                        check3 = k - 4;
                    }
                    //dont increment pair b/c if the last 2 are the pair it will work anyways
                    //make sure the pair is actually a part of the run first 
                }
                if(deck.getValueUniqNum(hand.get(k-1)) == deck.getValueUniqNum(hand.get(k-2))){
                    if(next > 1){
                        check2 = next - 2;
                    }
                    if(next > 2){
                        check2 = next - 3;
                    }
                    pair = pair + 1;
                }
                if(deck.getValueUniqNum(hand.get(k-2)) == deck.getValueUniqNum(hand.get(k-3))){
                    if(next > 1){
                        check3 = check2 - 2;
                    }
                    pair = pair + 1;
                }
                //last 2 cards are the same
                if(k > 3){
                    if(deck.getValueUniqNum(hand.get(k-3)) == deck.getValueUniqNum(hand.get(k-4))){
                      pair = pair + 1;
                    }
                }

            }
            
                if(((deck.getValueUniqNum(hand.get(k))) == (deck.getValueUniqNum(hand.get(next))+1) && ((deck.getValueUniqNum(hand.get(next))) == (deck.getValueUniqNum(hand.get(check2))+1)))){
                    //4 in a row
                    if(k > 2){ //only check for in a row id we're looking at last 4 cards or more
                        if(deck.getValueUniqNum(hand.get(check2)) == (deck.getValueUniqNum(hand.get(check3))+1)){
                            if(pair == 1){
                                totalHandCount = totalHandCount + 4;
                                System.out.println("1 pairs in run of 4!!!");
                            }
                            totalHandCount = totalHandCount + 4;
                            System.out.println("run of 4");
                            System.out.println(totalHandCount);
                            //5 in a row
                            if(k > 3){
                                if(deck.getValueUniqNum(hand.get(check3)) == (deck.getValueUniqNum(hand.get(check4))+1)){
                                    totalHandCount = totalHandCount + 5;
                                    System.out.println("run of 5");
                                    System.out.println(totalHandCount);
                                }
                                break;
                            }
                            else{
                                break;
                            }
                        }
                    }
                    //if theres a pair that means another run could be made
                    if(pair == 1){
                        totalHandCount = totalHandCount + 3;
                        System.out.println("1 pairs in run!!!");
                    }
                    //if there's 2 pairs then an adidtional 3 pairs can be made
                    if(pair == 2){
                        totalHandCount = totalHandCount + 9;
                        System.out.println("2 pairs in run!!!");
                    }
                    totalHandCount = totalHandCount + 3;
                    System.out.println("run of 3");
                    System.out.println(totalHandCount);
                }
            //}
            
        }
        //check for pairs or triples etc - same value
        for(int m = 0; m < hand.size()-1;m++){
            //first and second card have same value
            //if(i < hand.size()-1){ //if we are on the last card there is no next card to compare it to
                if((deck.getValueUniqNum(hand.get(m))) == (deck.getValueUniqNum(hand.get(m+1)))){
                    if(m < hand.size()-2){ //only check for 3 of the same if we have 2 cards after this one to compare it to
                        // 3 of the same value -- 6 points for each combo of 2 u can make
                        if(hand.get(m+1).getCards()[0].getValue().equals(hand.get(m+2).getCards()[0].getValue())){
                            //4 of a kind
                            if(m < hand.size()-3){
                                if(hand.get(m+2).getCards()[0].getValue().equals(hand.get(m+3).getCards()[0].getValue())){
                                    totalHandCount = totalHandCount + 12;
                                    System.out.println("4 of a kind");
                                    System.out.println(totalHandCount);
                                    break;
                                }
                            }
                            totalHandCount = totalHandCount + 6;
                            System.out.println("3 of a kind");
                            System.out.println(totalHandCount);
                            break;
                        }
                    }
                    totalHandCount = totalHandCount + 2; 
                    System.out.println("pair");
                    System.out.println(totalHandCount);
                }
            //}
            //3 in a row -- 6
            //4 in a row -- 12
        }
        //if all the same suit -- 5 points for 5 cards

        //if all suits in regular hand are the same
        if(deckCard != null){
            hand.remove(deckCard);
        }
        if((hand.get(0).getCards()[0].getSuit().equals(hand.get(1).getCards()[0].getSuit())) &&
                (hand.get(1).getCards()[0].getSuit().equals(hand.get(2).getCards()[0].getSuit())) &&
                (hand.get(2).getCards()[0].getSuit().equals(hand.get(3).getCards()[0].getSuit()))){
            totalHandCount = totalHandCount + 4;
            System.out.println("4 of same suit");
            System.out.println(totalHandCount);
            if(deckCard != null){
                hand.add(deckCard);
                Collections.sort(hand);
                //all 5 cards in hand (including deck card) same suit
                if((hand.get(0).getCards()[0].getSuit().equals(hand.get(1).getCards()[0].getSuit())) &&
                    (hand.get(1).getCards()[0].getSuit().equals(hand.get(2).getCards()[0].getSuit())) &&
                    (hand.get(2).getCards()[0].getSuit().equals(hand.get(3).getCards()[0].getSuit())) &&
                    (hand.get(3).getCards()[0].getSuit().equals(hand.get(4).getCards()[0].getSuit()))){
                    totalHandCount = totalHandCount + 5;
                    System.out.println("5 of same suit");
                    System.out.println(totalHandCount);
                }
            }
        }
        
        return totalHandCount;
        
    }
    /**
     * gets the private list of cards for other classes to access
     * @return array list of cards
     */
    public ArrayList<SingleCard> getHand(){
        for(int i=0; i < 5;i++){
            cards.add(deck.getACard());
        }
        return cards;
    }
}
