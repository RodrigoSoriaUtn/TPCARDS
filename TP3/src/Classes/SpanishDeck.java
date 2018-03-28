/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Abstract.AbstractDeck;
import java.util.Stack;

/**
 *
 * @author alumno
 */
public class SpanishDeck extends AbstractDeck{
    
    public SpanishDeck(boolean withCards) {
        cards = new Stack();
        if(withCards){
            for(int i = 1; i < 13; i++){
                for(int palo = 0; palo < 4; palo++){
                    this.addCard(new SpanishCard(i, SpanishTypeOfCard.values()[palo]));
                }
            }
        }
    }
}
