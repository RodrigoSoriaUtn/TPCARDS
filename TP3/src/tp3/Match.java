/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3;

import Classes.Dealer;
import Classes.Player;
import Classes.SpanishDeck;
import Classes.Table;
import java.util.ArrayList;


/**
 *
 * @author Rodrigo Soria
 */
public class Match {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Table table = new Table();
        
        Dealer dealer = new Dealer(new SpanishDeck(true), "Hansy", "Dickenson", table);
        ArrayList<Player> players = new ArrayList<>();
        
        for(int i = 0; i < 4; i++){
            players.add(new Player("Jug. " + i, new SpanishDeck(false), table));
        }
        
        
        
    }
    
}
