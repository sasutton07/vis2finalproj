/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectvis2;

import com.google.gson.Gson;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.ImageIcon;

/**
 * Handles call for a new deck from the api
 * @author samsutton
 */
public class Deck {
    public ImageIcon back;
    private NewDeck newResp;   //deck resp
    private NewDeck returnResp;
    private Gson gson = new Gson();
    private static Deck deck_instance = null;
    
    public Deck(){
        back = new ImageIcon(getClass().getResource("/backOfDeck (4) 10.25.33 PM.jpeg"));
        try {
            //url for a shuffle new deck initialization
            URL shuffleUrl = new URL("https://www.deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1");
            HttpsURLConnection conn = (HttpsURLConnection) shuffleUrl.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            newResp = gson.fromJson(in, NewDeck.class);

        } catch (MalformedURLException ex) {
            Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static synchronized Deck getInstance()
    {
        if (deck_instance == null)
            deck_instance = new Deck();
  
        return deck_instance;
    }
    
    //get card from api
    /**
     * makes a call to the api to grab a card and all its info from the deck
     * @return a card and its info
     */
    public SingleCard getACard() {
        SingleCard drawResp;
        try {
            URL drawUrl = new URL("https", "www.deckofcardsapi.com", "/api/deck/" + deck_instance.newResp.getDeckID() + "/draw/?count=1");
            HttpsURLConnection drawConn = (HttpsURLConnection) drawUrl.openConnection();
            BufferedReader drawIn = new BufferedReader(
                    new InputStreamReader(drawConn.getInputStream()));
            drawResp = gson.fromJson(drawIn, SingleCard.class);
            return drawResp;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    /**
     * makes a call to the api to return all the cards taken from the deck back to the deck
     */
    public void returnToDeck(){
        try {
            URL returnUrl = new URL("https", "www.deckofcardsapi.com", "/api/deck/" + deck_instance.newResp.getDeckID() + "/return/");
            HttpsURLConnection conn = (HttpsURLConnection) returnUrl.openConnection();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            returnResp = gson.fromJson(in, NewDeck.class);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * when passed a card will return the numerical value we'll use for counting based on the cards value(A,K,6,etc)
     * @param card
     * @return the value of the card
     */ 
    public int getValue(SingleCard card) {
        int value = 0;
        if (null != card.getCards()[0].getValue()) {
            switch (card.getCards()[0].getValue()) {
                case "ace" ->
                    value = 0;
                case "ACE" ->
                    value = 1;
                case "2" ->
                    value = 2;
                case "3" ->
                    value = 3;
                case "4" ->
                    value = 4;
                case "5" ->
                    value = 5;
                case "6" ->
                    value = 6;
                case "7" ->
                    value = 7;
                case "8" ->
                    value = 8;
                case "9" ->
                    value = 9;
                case "10" ->
                    value = 10;
                case "JACK" ->
                    value = 10;
                case "QUEEN" ->
                    value = 10;
                case "KING" ->
                    value = 10;
                default -> {
                }
            }
        }
        return value;
    }
    //to be used in comparison -- need uniq values for each card value
    /**
     * when passed a card will return the numerical value we'll use for checking runs - not their values but a number unique to each but in order
     * @param card
     * @return the unique value of the card
     */ 
    public int getValueUniqNum(SingleCard card) {
        int value = 0;
        if (null != card.getCards()[0].getValue()) {
            switch (card.getCards()[0].getValue()) {
                case "ace" ->
                    value = 0;
                case "ACE" ->
                    value = 1;
                case "2" ->
                    value = 2;
                case "3" ->
                    value = 3;
                case "4" ->
                    value = 4;
                case "5" ->
                    value = 5;
                case "6" ->
                    value = 6;
                case "7" ->
                    value = 7;
                case "8" ->
                    value = 8;
                case "9" ->
                    value = 9;
                case "10" ->
                    value = 10;
                case "JACK" ->
                    value = 11;
                case "QUEEN" ->
                    value = 12;
                case "KING" ->
                    value = 13;
                default -> {
                }
            }
        }
        return value;
    }
    /**
     * resizes the image from the api to fit the size of our labels
     * @param url
     * @param size
     * @return an image
     * @throws IOException 
     */
    // from https://stackoverflow.com/questions/18550284/java-resize-image-from-an-url
    public BufferedImage resize(URL url, Dimension size) throws IOException {
        BufferedImage image = ImageIO.read(url);
        BufferedImage resized = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resized.createGraphics();
        g.drawImage(image, 0, 0, size.width, size.height, null);
        g.dispose();
        return resized;
    }
    //end stackoverflow code  
    
    /**
     * shortcut method that we will use a lot which takes a card, gets its image, and resizes it to be changed in the window
     * @param card
     * @return an image
     * @throws MalformedURLException 
     */
    public BufferedImage changeImage(SingleCard card) throws MalformedURLException {
        BufferedImage img = null;
        try {
            URL url = new URL(card.getCards()[0].getImage());
            img = resize(url, new Dimension(75, 105));
        } catch (IOException ex) {
            Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }
}
