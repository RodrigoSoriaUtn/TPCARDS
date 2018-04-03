/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.English;

import Classes.Abstract.EnumerableTypeOfCard;

/**
 *
 * @author rodrigo
 */
public enum EnglishTypeOfCard implements EnumerableTypeOfCard{
    PIKE, HEART, DIAMOND, CLOVER;

    @Override
    public String getTypeOfDeck() {
       return "English";
    }
    
    
}
