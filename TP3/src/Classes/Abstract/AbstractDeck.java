/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Abstract;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author rodrigo
 * @param <T> The card to be used
 */
public abstract class AbstractDeck <T extends AbstractCard> {
    protected List<T> cards;
    
    public void mezclar(){
        Collections.shuffle(this.cards);
    }
    
    public List<T> getCards() {
        return cards;    
    }
    
    /*
    Returns the actual cuantity of cards in the deck. Or the full size of the
    deck if you have never take one from it.
    */
     public int getActualCuantityOfCards(){
        return cards.size();
    }
    
    public T takeCard(){
        T card = null;        
        if(!cards.isEmpty()){
            card = (T) ((Stack)cards).pop();
        }
        return card;
    }
    
    public void addCard(T card){
        ((Stack)cards).push(card);
    }
    
}
