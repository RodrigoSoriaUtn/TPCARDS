/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Spanish;

import Classes.Abstract.AbstractCard;
/**
 *
 * @author rodrigo
 */
public class SpanishCard extends AbstractCard{
    
    public SpanishCard(int value, SpanishTypeOfCard type) {
        super(value, type);
    }
    
    @Override
    public SpanishTypeOfCard getType(){
        return (SpanishTypeOfCard) super.getType();
    }
    
}
