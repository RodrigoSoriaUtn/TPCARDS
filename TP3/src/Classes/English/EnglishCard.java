/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.English;

import Classes.Abstract.AbstractCard;

/**
 *
 * @author rodrigo
 */
public class EnglishCard extends AbstractCard{
    
    public EnglishCard(int value, EnglishTypeOfCard type) {
        super(value, type);
    }
    
    @Override
    public EnglishTypeOfCard getType(){
        return (EnglishTypeOfCard) super.getType();
    }
    
}
