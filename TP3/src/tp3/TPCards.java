/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3;

import Classes.Abstract.AbstractDeck;
import Classes.English.EnglishDeck;
import Classes.Match;
import Classes.Spanish.SpanishDeck;
import DBA.config.DatabaseConnection;
import java.util.ArrayList;
import java.sql.*;

/**
 *
 * @author rodrigo
 */
public class TPCards {
    
    public static void main(String[] args){
        
        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("Roderick");
        nicknames.add("Almanax");
        nicknames.add("HalfZhairer");
        
        //AbstractDeck deck = new SpanishDeck(true);
        AbstractDeck deck = new EnglishDeck(true);
        Match match = new Match(4, nicknames, deck);
        
        match.startMatch();
        
    }
}
    
