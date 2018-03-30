/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes.Abstract;

/**
 *
 * @author alumno
 */
public class AbstractCard {
    private final int value;
    private final EnumerableTypeOfCard type;

    public AbstractCard(int value, EnumerableTypeOfCard type) {
        this.value = value;
        this.type = type;
    }
    
    public int getValue() {
        return value;
    }

    public EnumerableTypeOfCard getType() {
        return type;
    }
}
