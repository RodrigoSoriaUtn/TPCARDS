/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author alumno
 */
public class Card {
    private int value;
    private TypeOfCard type;

    public Card(int value, TypeOfCard type) {
        this.value = value;
        this.type = type;
    }
    
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public TypeOfCard getType() {
        return type;
    }

    public void setType(TypeOfCard type) {
        this.type = type;
    }

}
