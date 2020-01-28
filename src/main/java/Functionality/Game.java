/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionality;

import Entity.Player;
import java.util.Scanner;

/**
 *
 * @author emilt
 */
public class Game {
    
    public int amountOfPlayers;
    public Player[] players;
    
    
    public void setUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter amount of players");
        String amountOfPlayers = sc.nextLine();
        
        //Check om ^^ er gyldigt svar
        
        System.out.println(amountOfPlayers);
        
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
    
    
    
}
