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
        String playerInput = sc.nextLine();
        for (char c : playerInput.toCharArray()) {
            if (!Character.isDigit(c)) {
                System.out.println("Must be a number");
                setUp();
                break;
            }
        }
        int parsedPlayers = Integer.parseInt(playerInput);
        if (parsedPlayers <= 1 || parsedPlayers > 6) {
            System.out.println("You can only be 2-6 players");
            setUp();
        } else {
            amountOfPlayers = parsedPlayers;
            System.out.println(amountOfPlayers);

        }

    }

}
