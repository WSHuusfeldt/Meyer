/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionality;

import Entity.Player;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author emilt
 */
public class Game {
    
    public int amountOfPlayers;
    public ArrayList<Player> players = new ArrayList();
    public boolean game;
    
    public void run() {
        
        //Game setup
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter amount of players");
        String amountOfPlayers = sc.nextLine();
        
        //Check om ^^ er gyldigt svar        
        System.out.println(amountOfPlayers);
        
        for (int i = 0; i < 3; ++i) {
            System.out.println("Enter the name of player " + (i+1));
            String nameOfPlayer = sc.nextLine();
            System.out.println("Is this name fine: " + nameOfPlayer + "?. 'y' for yes - everything else for no");
            String checkNameOfPlayer = sc.nextLine();
            if (checkNameOfPlayer.equals("y")) {
                players.add(new Player(nameOfPlayer));
            } else {
                --i;
            }
        }
        

        //Running game
        while(this.game) {
            
            
            
            
            //Checking wether all but one player has 0 life and thus if the game has ended.
            int count = 0;
            for (int i = 0; i < players.size(); ++i) {
                if (players.get(i).getLifeTotal() == 0) {
                    count ++;
                }
            }
            if (count == players.size()-1) {
                this.game = false;
            }
        }
        
        
        
    }
    
    
    
}
