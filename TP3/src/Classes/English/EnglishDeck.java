/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.English;

import Classes.Abstract.AbstractDeck;

/**
 *
 * @author rodrigo
 */
public class EnglishDeck extends AbstractDeck<EnglishCard>{

    public EnglishDeck() {
        this(false);
    }

    public EnglishDeck(boolean withCards) {
        super();
        if(withCards){
            for(int palo = 0; palo < 4; palo++){
                for(int valor = 1; valor < 14; valor++){
                    this.addCard(new EnglishCard(valor, EnglishTypeOfCard.values()[palo]) );
                }
            }
        }
    }

    @Override
    public AbstractDeck generateCleanDeck() {
        return new EnglishDeck();
    }
    
}
