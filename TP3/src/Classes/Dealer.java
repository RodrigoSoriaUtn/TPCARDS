/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Abstract.AbstractDeck;

/**
 *
 * @author alumno
 */
public class Dealer{
    
    private AbstractDeck deck;
    private String nombre;
    private String apellido;

    public Dealer(String nombre, String apellido){
        this(null, nombre, apellido);
    }
    
    public Dealer(AbstractDeck deck, String nombre, String apellido) {
        this.deck = deck;
        this.nombre = nombre;
        this.apellido = apellido;
    }
    
    public void throwCard(){
        
    }
    
    public void setDeck(AbstractDeck deck) {
        this.deck = deck;
    }
    
    public AbstractDeck getDeck() {
        return deck;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
    
    
    
}
