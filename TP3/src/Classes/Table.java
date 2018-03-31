/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Abstract.AbstractCard;

/**
 *
 * @author rodrigo
 */
public class Table {
    
    AbstractCard cardOnTable;
    
    public Table(){
        cardOnTable = null;
    }
    
    public AbstractCard takeThrowedCard(){
        synchronized(this){
           
            while(!isAcardOnTheTable()){
                try {
                    wait();
                } catch (InterruptedException ex) { }
            }
            
            AbstractCard card = cardOnTable;
            cardOnTable = null;
            notifyAll();
            return card;
        }
    }
    
    public void setCardOnTable(AbstractCard card){
        synchronized(this){
            this.cardOnTable = card;
            notifyAll();
        }
    }
    
    public boolean isAcardOnTheTable(){
        synchronized(this){
            boolean resp = false;
            if(cardOnTable != null) resp = true;
            return resp;
        }
    }
    
}
