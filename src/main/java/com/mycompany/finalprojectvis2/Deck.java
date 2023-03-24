/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.finalprojectvis2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.ImageIcon;

/**
 *
 * @author samsutton
 */
public class Deck {
    ImageIcon back;
    
    public Deck(){
        back = new ImageIcon(getClass().getResource("/backOfDeck (4).jpeg"));
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
    //get card from api
    private DrawACard getACard() {
        DrawACard drawResp;
        try {
            URL drawUrl = new URL("https", "www.deckofcardsapi.com", "/api/deck/" + newResp.getDeckID() + "/draw/?count=1");
            HttpsURLConnection drawConn = (HttpsURLConnection) drawUrl.openConnection();
            BufferedReader drawIn = new BufferedReader(
                    new InputStreamReader(drawConn.getInputStream()));
            drawResp = gson.fromJson(drawIn, DrawACard.class);
            return drawResp;
        } catch (MalformedURLException ex) {
            Logger.getLogger(MainGameWindow.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(MainGameWindow.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
    private int getValue(DrawACard card) {
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
}
