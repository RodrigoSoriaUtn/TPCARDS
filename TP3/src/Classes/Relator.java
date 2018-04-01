/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Abstract.AbstractCard;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author rodrigo
 */
public class Relator implements Observer{
    
    @Override
    public void update(Observable o, Object arg) {
        synchronized(this){
            if(o instanceof Player && arg instanceof AbstractCard){
                Player player = (Player) o;
                AbstractCard card = (AbstractCard) arg;

                System.out.println(player.getNickName() + " Takes a " + card.getValue()
                                                             + " of " + card.getType());    
            }
        }
    }
    
}
