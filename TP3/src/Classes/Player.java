/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Abstract.AbstractCard;
import Classes.Abstract.AbstractDeck;
import java.util.Observable;

/**
 * Extends observable to notify when it takes a card.
 * @author Rodrigo Soria
 */
public class Player extends Observable implements Runnable{
    
    protected int points;
    private String nickName;
    private AbstractDeck hand;
    private Table gameTable;
    
    public Player(Player player){
        this(player.getNickName(), player.getHand(), player.getGameTable());
    }
    
    public Player(String nickName, AbstractDeck deck, Table table){
        this.nickName = nickName;
        this.hand = deck;
        this.gameTable = table;
        this.points = 0;
    }
    
    @Override
    public void run() {
        while(!gameTable.isClosed()){
            AbstractCard card = gameTable.takeThrowedCard();
            if(card != null){
                hand.addCard(card);
                setChanged();
                notifyObservers(card);
            }
        }
    }

    public String getNickName(){
        return this.nickName;
    }
    
    public AbstractDeck getHand(){
        return this.hand;
    }
    
    public Table getGameTable(){
        return this.gameTable;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
    
}
