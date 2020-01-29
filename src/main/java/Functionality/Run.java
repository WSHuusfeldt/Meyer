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
public class Run {

    Scanner sc = new Scanner(System.in);
    public int amountOfPlayers;
    public ArrayList<Player> players = new ArrayList();
    public boolean game = true;

    Dice dice1 = new Dice();
    Dice dice2 = new Dice();

    private int lastTurnSaidValue = 0;
    private int currentValue = 0;
    private boolean currentLie = false;

    //Main method
    public void run() {
        setUpPlayerAmount();
        setUpPlayerNames();

        //Loop that runs for entirety of the game.
        while (game) {
            //For loop that runs for every player.
            for (int i = 0; i < players.size(); ++i) {
                Player currentPlayer = players.get(i);

                //If it is the first turn of the round.
                if (currentValue == 0) {
                    //Roll the dices
                    rollDices();
                    chooseRoll();

                } //Otherwise.
                else {

                }

            }

        }

    }

    //Helping methods    
    //Setups the amount of players.
    public void setUpPlayerAmount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter amount of players");
        String playerInput = sc.nextLine();

        //Exception Handling
        //Exception 1 - Not a number
        for (char c : playerInput.toCharArray()) {
            if (!Character.isDigit(c)) {
                clearScreen();
                System.out.println("Must be a number");
                setUpPlayerAmount();
                break;
            }
        }

        //Exception 2 - Not a legal number
        int parsedPlayers = Integer.parseInt(playerInput);
        if (parsedPlayers <= 1 || parsedPlayers > 6) {
            clearScreen();
            System.out.println("You can only be 2-6 players");
            setUpPlayerAmount();
        }

        this.amountOfPlayers = parsedPlayers;
        clearScreen();
    }

    //Setup the names of the players.
    public void setUpPlayerNames() {
        //Loop for a number of times equal to the amountOfPlayers
        for (int i = 0; i < amountOfPlayers; ++i) {
            System.out.println("Enter the name of player " + (i + 1));
            String nameOfPlayer = sc.nextLine();
            System.out.println("Is this name fine: " + nameOfPlayer + "?. 'y' for yes - everything else for no");
            String checkNameOfPlayer = sc.nextLine();

            //Checks wether the user is satisfied with the given name
            if (checkNameOfPlayer.equals("y")) {
                players.add(new Player(nameOfPlayer));
                clearScreen();
                //Otherwise loop through the same index
            } else {
                --i;
                clearScreen();
            }
        }
    }

    //Clears the screen by spamming empty lines.
    public void clearScreen() {
        for (int i = 0; i < 100; ++i) {
            System.out.println("");
        }
    }

    //Sets the dices to random numbers between 1 - 6 after a scanner nextline.
    public void rollDices() {
        System.out.println("Press for roll");
        sc.nextLine();
        dice1.rollDice();
        dice2.rollDice();
        System.out.println("You have rolled " + dice1.getFaceValue() + " and " + dice2.getFaceValue());
        sc.nextLine();
    }

    //Lets the player decide to lie or go with the roll
    public void chooseRoll() {
        //Check wether 
        System.out.println("Please pick your option:");
        System.out.println("'L': Lie about your roll");
        System.out.println("'T': Tell the truth");
        System.out.println("'R': Hidden reroll");
        String mode = sc.nextLine();

        //Lie
        if (mode.equals("L")) {

        } //Truth
        else if (mode.equals("T")) {

        } //Reroll
        else if (mode.equals("R")) {

        } //Not an option exception
        else {
            clearScreen();
            System.out.println("Sorry, that is not an option");
            chooseRoll();
        }
    }
    
    
    public void lieRoll() {
        
    }
    
    public void truthRoll() {
        
    }
    
    public void reRoll() {
        
    }

}
