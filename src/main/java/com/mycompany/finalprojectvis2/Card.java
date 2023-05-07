/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectvis2;

/**
 * gets card information from the api
 * @author samsutton
 */
public class Card {
    private String code;
    private String image;
    private Image images;
    private String value;
    private String suit;
    
    /**
     * gets private field code to be used in other classes
     * @return the cards code (ex:2H)
     */
    public String getCode(){
        return code;
    }
    /**
     * gets private field image to be used in other classes
     * @return the image of the card
     */
    public String getImage(){
        return image;
    }
    /**
     * gets private field value to be used in other classes
     * @return value(A,2,K)
     */
    public String getValue(){
        return value;
    }
    /**
     * gets private field suit to be used in other classes
     * @return cards suit(spades)
     */
    public String getSuit(){
        return suit;
    }
}
