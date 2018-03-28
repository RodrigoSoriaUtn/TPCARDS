/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author alumno
 */
public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new Stack();
        for(int i = 1; i < 13; i++){
            for(int palo = 0; palo < 4; palo++){
                cards.add(new Card(i,TypeOfCard.values()[palo]));
            }
        }
    }
    
    public void mezclar(){
        Collections.shuffle(this.cards);
    }
    
    public List<Card> getCards() {
        return cards;    
    }
    
    /*
    Returns the actual cuantity of cards in the deck. Or the full size of the
    deck if you have never take one from it.
    */
    public int getActualCuantityOfCards(){
        return cards.size();
    }
    
    public Card takeCard(){
        Card card = null;        
        if(!cards.isEmpty()){
            card = (Card) ((Stack)cards).pop();
        }
        return card;
    }
    
    public void addCard(Card card){
        ((Stack)cards).push(card);
    }
    
}
