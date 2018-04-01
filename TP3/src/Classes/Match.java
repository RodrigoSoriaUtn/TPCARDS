/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.Abstract.AbstractCard;
import Classes.Abstract.AbstractDeck;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Rodrigo Soria
 */
public class Match {
    
    private final Table table;
    private List<Player> players;
    private List<Thread> activePlayers;
    private Dealer dealer;
    private final Relator relator;
    private AbstractDeck deck;
    private Winner winner;
    
    /**
     * Creates and initializes a match
     * @param players : The cuantity of players.
     * @param playerNickNames : The nickNames for the players.
     * if there are more players than nicknames they will have names based on
     * their player number.
     * @param deck : The deck to play.
     */
    public Match(int players, List<String> playerNickNames, AbstractDeck deck){
        
        this.table = new Table();
        this.deck = deck;
        this.relator = new Relator();
        this.winner = null;
        
        this.dealer = new Dealer(deck,"Henry", "Hafford",this.table);
        this.players = new ArrayList<>();
        this.activePlayers = new ArrayList<>();
        
        initPlayers(playerNickNames, players);   
        initMatch();
    }

    private void initMatch() {
        //Se mezclan las cartas.
        dealer.shuffleDeck();
        //Se define la forma de juego.
        for(Player player : players){
            player.addObserver(relator); //El relator indica quien agarra cada carta.
        }
    }

    private void initPlayers(List<String> playerNickNames, int players) {
        
        if(playerNickNames.size() < players){ //generates players with nicknames and without nicknames
            int j = 0;
            for(String nickName : playerNickNames ){
                this.players.add(new Player(nickName, this.deck.generateCleanDeck() ,this.table));
                j++;
            }
            while(j < players){
                this.players.add(new Player("Player " + j, this.deck.generateCleanDeck(), this.table));
                j++;
            }
        }else{ // generates players with nicknames;
            for(int i = 0; i < players; i++){
                this.players.add(new Player(playerNickNames.get(i), this.deck.generateCleanDeck(), this.table));
            }
        }
    }

    private int calculatePoints(Player player) {
        int points = 0;
        for(Object card : player.getHand().getCards()){
            points += ((AbstractCard)card).getValue();
        }
        return points;
    }

    public void startMatch() {
        for(Player player : players){
            Thread thread = new Thread(player);//Se coloca a cada jugador en un hilo para que comience el juego.
            activePlayers.add(thread);
            thread.start();
        }
        dealer.throwCards(); // Comienza el juego.
        for(Thread thread : activePlayers){ // Espera que los jugadores finalicen sus tareas para terminar el juego.
            try {
                thread.join();
            } catch (InterruptedException ex) {
                System.out.println("Error en la espera de los jugadores activos.");
            }
        }
        matchEnd(); // termina el juego
    }

    private void matchEnd() {
        //Muestra resultados:
        System.out.println("FIN DEL JUEGO. MOSTRANDO RESULTADOS...");
        for(Player player : this.players){
            int points = calculatePoints(player);
            System.out.println(player.getNickName()+ ": " + points);
            //Define el ganador.
            if(winner != null){
                if(winner.getPoints() < points){
                    winner = new Winner(player, points);
                }
            }else{
                winner = new Winner(player, points);
            }
        }
    }

}
