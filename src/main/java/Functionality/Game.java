/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionality;

import Entity.Dice;
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
    public boolean game = true;
    
    public void run() {
        clearScreen();
        //Game setup
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter amount of players");
        String amountOfPlayers = sc.nextLine();
        Dice d1 = new Dice();
        Dice d2 = new Dice();
        
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
            //All players gets a turn
            for (int i = 0; i < players.size(); ++i) {
                clearScreen();
                //Only players with life has any actions
                Player currentPlayer = players.get(i);                
                if (currentPlayer.getLifeTotal() > 0) {
                    System.out.println("It is your turn " + currentPlayer.getName());
                    System.out.println("Press to roll");
                    sc.nextLine();
                    d1.rollDice();
                    d2.rollDice();
                    System.out.println("You have rolled " + getDiceValue(d1.getFaceValue(), d2.getFaceValue()));
                    sc.nextLine();
                    
                    System.out.println("Do you wish to lie about your number? - 'y' for yes, everything else for no");
                    String checkLieOfPlayer = sc.nextLine();
                    
                }
            }
            
            
            
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
    
    
    public int getDiceValue(int val, int val2) {
        if (val == 1 && val2 == 2 || val2 == 3)
            return Integer.valueOf(String.valueOf(val) + String.valueOf(val2));
        else if (val2 == 1 && val == 2 || val == 3) 
            Integer.valueOf(String.valueOf(val2) + String.valueOf(val));
        else if (val > val2)
            return Integer.valueOf(String.valueOf(val) + String.valueOf(val2));
        return Integer.valueOf(String.valueOf(val2) + String.valueOf(val));
    }
    
       
    public int compareValue(int dVal, int dVal2) {
        if (dVal > dVal2)
            return dVal;
        return dVal2;
    }
    
    public void clearScreen() {
        for (int i = 0; i < 100; ++i) {
            System.out.println("");
        }
    }
    
    
}
