/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Abstract.AbstractCard;
import Classes.Abstract.AbstractDeck;
import java.util.Observable;
import java.util.Observer;

/**
 * Extends observable to notify when it takes a card.
 * Implements Observer to be notified when the deck of the dealer is empty.
 * @author alumno
 */
public class Player extends Observable implements Observer, Runnable{
    
    String nickName;
    AbstractDeck hand;
    boolean endOfMatch;
    Table gameTable;
    
    public Player(String nickName, AbstractDeck deck, Table table){
        this.nickName = nickName;
        this.hand = deck;
        this.endOfMatch = false;
        this.gameTable = table;
    }
    
    @Override
    public void run() {
        while(!endOfMatch){
            AbstractCard card = gameTable.takeThrowedCard();
            hand.addCard(card);
            notifyObservers(card);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg != null && arg instanceof Boolean){
            this.endOfMatch = ((Boolean) arg); //method booleanValue() is not necessary (and generates a warning O.o')
        }
    }
    
}
