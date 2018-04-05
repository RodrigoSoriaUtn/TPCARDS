/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;


/**
 *
 * @author rodrigo
 */
public class Winner extends Player{
    
    private int points;
    
    public Winner(Player player, int points) {
        super(player);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
    
}
