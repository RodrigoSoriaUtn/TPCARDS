/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Spanish;

import Classes.Abstract.AbstractDeck;

/**
 *
 * @author alumno
 */
public class SpanishDeck extends AbstractDeck{
    
    public SpanishDeck(){
        this(false);
    }
    
    public SpanishDeck(boolean withCards) {
        super();
        if(withCards){
            for(int palo = 0; palo < 4; palo++){
                for(int i = 1; i < 13; i++){
                    this.addCard(new SpanishCard(i, SpanishTypeOfCard.values()[palo]));
                }
            }
        }
    }

    @Override
    public AbstractDeck generateCleanDeck() {
        return new SpanishDeck();
    }
}
