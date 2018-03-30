/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Abstract.AbstractDeck;
import java.util.Observable;
/**
 *
 * @author alumno
 */
public class Dealer extends Observable{
    
    private AbstractDeck deck;
    private String nombre;
    private String apellido;
    private Table table;
    
    public Dealer(String nombre, String apellido, Table table){
        this(null, nombre, apellido, table);
    }
    
    public Dealer(AbstractDeck deck, String nombre, String apellido, Table table) {
        this.deck = deck;
        this.nombre = nombre;
        this.apellido = apellido;
        this.table = table;
    }
    
    
    public void throwCards(){ // will throw all the cards to the table, one by one while the players take it from the table.
        while(deck != null && !deck.isEmpty()){
            if(!table.isAcardOnTheTable()){
                table.setCardOnTable(deck.takeCard());
            }
        }
        //Avisa que ya no hay cartas en el mazo.
        setChanged();
        notifyObservers();
    }
    
    public void shuffleDeck(){
        deck.shuffle();
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
