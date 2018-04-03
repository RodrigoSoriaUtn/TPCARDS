/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Spanish;

import Classes.Abstract.EnumerableTypeOfCard;

/**
 *
 * @author alumno
 */
public enum SpanishTypeOfCard implements EnumerableTypeOfCard{
    CUP, SWORD, CLUB, GOLD;

    @Override
    public String getTypeOfDeck() {
        return "Spanish";
    }
    
    
}
