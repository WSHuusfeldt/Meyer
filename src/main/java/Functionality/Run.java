/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Functionality;

import Entity.Dice;
import Entity.Player;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author emilt
 */
public class Run {

    Random ran = new Random();
    Scanner sc = new Scanner(System.in);
    public int amountOfPlayers;
    public int amountOfBots;
    public ArrayList<Player> players = new ArrayList();
    public boolean game = true;

    Dice dice1 = new Dice();
    Dice dice2 = new Dice();

    private int lastTurnSaidValue = 0;
    private int lastTurnActualValue = 0;

    private int currentValue = 0;
    private boolean currentReroll = false;

    //Main method
    public void run() throws InterruptedException {
        clearScreen();
        setUpPlayerAmount();
        setUpPlayerNames();
        if (this.amountOfPlayers < 6) {
            setUpBotAmount();
            setUpBotNames();
        }

        //Loop that runs for entirety of the game.
        while (game) {
            //For loop that runs for every player.
            for (int i = 0; i < players.size(); ++i) {
                Player currentPlayer = players.get(i);
                Player answeringPlayer = getNextPlayer(i);

                //If it is the first turn of the round.
                if (currentValue == 0) {
                    newTurn(currentPlayer);
                    if (currentPlayer.isBot() == true) {
                        botRollDice();
                        botRollChoice();
                    } else {
                        rollDices();
                        chooseRoll();
                    }
                    if (answeringPlayer.isBot() == false) {
                        answerRoll(currentPlayer, answeringPlayer);
                    }
                    botAnswerRoll(currentPlayer, answeringPlayer);
                } //Otherwise.
                else if (currentPlayer.isBot() == true) {
                    newNextTurn(currentPlayer);
                    botRollDice();
                    botRollChoice();
                    if (answeringPlayer.isBot() == false) {
                        answerRoll(currentPlayer, answeringPlayer);
                    }
                    botAnswerRoll(currentPlayer, answeringPlayer);
                } else {
                    newNextTurn(currentPlayer);
                    rollDices();
                    chooseRoll();
                    if (answeringPlayer.isBot() == false) {
                        answerRoll(currentPlayer, answeringPlayer);
                    }
                    botAnswerRoll(currentPlayer, answeringPlayer);
                }
                removeDeadPlayers();
                if (checkForWinners()) {
                    break;
                }
            }
        }
        System.out.println(players.get(0).getName() + " has won the game!");
    }

    public void setUpBotAmount() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to add bot(s)? Press 'y' for yes, anything else for no");
        String playerInput = sc.nextLine();
        if (playerInput.equals("y")) {
            System.out.println("Enter amount of bots");
            String botNumber = sc.nextLine();

            //Exception 1 - not a number
            for (char c : botNumber.toCharArray()) {
                if (!Character.isDigit(c)) {
                    clearScreen();
                    System.out.println("Must be a number");
                    setUpBotAmount();
                    return;
                }
            }

            //Exception 2 - Not a legal number
            int parsedBots = Integer.parseInt(botNumber);
            if (parsedBots <= 0 || parsedBots > (6 - amountOfPlayers)) {
                clearScreen();
                System.out.println("You can only be 6 players including bots");
                setUpBotAmount();
                return;
            }

            amountOfBots = parsedBots;
            clearScreen();
        }
    }

    //Setup the names of the bots.
    public void setUpBotNames() {
        //Loop for a number of times equal to the amountOfPlayers
        for (int i = 0; i < amountOfBots; ++i) {
            System.out.println("Enter the name of bot " + (i + 1));
            String nameOfPlayer = sc.nextLine();
            System.out.println("Is this name fine: " + nameOfPlayer + "?. 'y' for yes - everything else for no");
            String checkNameOfPlayer = sc.nextLine();

            //Checks wether the user is satisfied with the given name
            if (checkNameOfPlayer.equals("y")) {
                players.add(new Player(nameOfPlayer, true));
                clearScreen();
                //Otherwise loop through the same index
            } else {
                --i;
                clearScreen();
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
                return;
            }
        }

        //Exception 2 - Not a legal number
        int parsedPlayers = Integer.parseInt(playerInput);
        if (parsedPlayers <= 1 || parsedPlayers > 6) {
            clearScreen();
            System.out.println("You can only be 2-6 players");
            setUpPlayerAmount();
            return;
        }

        this.amountOfPlayers = parsedPlayers;
        clearScreen();
    }

    //Setup the names of the players.
    public void setUpPlayerNames() {
        //Loop for a number of times equal to the amountOfPlayers
        for (int i = 0; i < this.amountOfPlayers; ++i) {
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

    //Checks and removes if players have 0 or less life
    public void removeDeadPlayers() {
        for (int i = 0; i < players.size(); ++i) {
            if (players.get(i).getLifeTotal() <= 0) {
                players.remove(i);
            }
        }
    }

    //Check if there is any winners
    public boolean checkForWinners() {
        if (players.size() == 1) {
            this.game = false;
            return true;
        } else {
            return false;
        }
    }

    //Lets the player answer to a roll.
    public void answerRoll(Player currentPlayer, Player answeringPlayer) {
        clearScreen();
        String check = "";
        if (this.currentReroll) {
            System.out.println(currentPlayer.getName() + " made a random reroll.");
            System.out.println(answeringPlayer.getName() + ", do you believe they rolled higher than " + currentValue + "? - y for yes, everything else for no.");
            check = sc.nextLine();
        } else {
            System.out.println(currentPlayer.getName() + " said they got: " + this.lastTurnSaidValue);
            System.out.println(answeringPlayer.getName() + ", do you believe what they said? - y for yes, everything else for no.");
            check = sc.nextLine();
        }

        if (this.currentReroll) {
            if (!check.equals("y")) {
                if (lastTurnActualValue >= currentValue) {
                    System.out.println("Sorry, they did roll higher. " + currentPlayer.getName() + " rolled " + lastTurnActualValue);
                    System.out.println(answeringPlayer.getName() + " you have lost a life point.");
                    answeringPlayer.setLifeTotal(answeringPlayer.getLifeTotal() - 1);
                    this.currentValue = 0;
                    this.currentReroll = false;
                    sc.nextLine();
                } else {
                    System.out.println("You are correct! " + currentPlayer.getName() + " only rolled " + lastTurnActualValue);
                    System.out.println(currentPlayer.getName() + " you have lost a life point.");
                    currentPlayer.setLifeTotal(currentPlayer.getLifeTotal() - 1);
                    this.currentValue = 0;
                    this.currentReroll = false;
                    sc.nextLine();
                }
            }
        } else {
            if (check.equals("y")) {
                this.currentValue = this.lastTurnSaidValue;
            } else {
                if (lastTurnSaidValue == lastTurnActualValue) {
                    System.out.println("Sorry but " + currentPlayer.getName() + " told the truth!");
                    System.out.println(answeringPlayer.getName() + " you have lost a life point.");
                    answeringPlayer.setLifeTotal(answeringPlayer.getLifeTotal() - 1);
                    this.currentValue = 0;
                    sc.nextLine();
                } else {
                    System.out.println("You are correct! " + currentPlayer.getName() + " lied!");
                    System.out.println(currentPlayer.getName() + " you have lost a life point.");
                    currentPlayer.setLifeTotal(currentPlayer.getLifeTotal() - 1);
                    this.currentValue = 0;
                    sc.nextLine();
                }
            }
        }
    }

    public void botAnswerRoll(Player currentPlayer, Player answeringPlayer) throws InterruptedException {
        clearScreen();
        String check = "";
        if (this.currentReroll) {
            System.out.println(currentPlayer.getName() + " made a random reroll.");
            System.out.println(answeringPlayer.getName() + ", do you believe they rolled higher than " + currentValue + "?");
            Thread.sleep(5500);
        } else {
            System.out.println(currentPlayer.getName() + " said they got: " + this.lastTurnSaidValue);
            System.out.println(answeringPlayer.getName() + ", do you believe what they said?");
            Thread.sleep(5500);
        }

        if (this.currentReroll) {
            if (ran.nextBoolean() == false) {
                if (lastTurnActualValue >= currentValue) {
                    System.out.println(answeringPlayer.getName() + " chose NO");
                    Thread.sleep(1000);
                    System.out.println("Sorry, they did roll higher. " + currentPlayer.getName() + " rolled " + lastTurnActualValue);
                    System.out.println(answeringPlayer.getName() + " you have lost a life point.");
                    answeringPlayer.setLifeTotal(answeringPlayer.getLifeTotal() - 1);
                    this.currentValue = 0;
                    this.currentReroll = false;
                    Thread.sleep(5500);
                } else {
                    System.out.println(answeringPlayer.getName() + " chose YES");
                    Thread.sleep(1000);
                    System.out.println("You are correct! " + currentPlayer.getName() + " only rolled " + lastTurnActualValue);
                    System.out.println(currentPlayer.getName() + " you have lost a life point.");
                    currentPlayer.setLifeTotal(currentPlayer.getLifeTotal() - 1);
                    this.currentValue = 0;
                    this.currentReroll = false;
                    Thread.sleep(5500);
                }
            }
        } else {
            if (ran.nextBoolean() == true) {
                this.currentValue = this.lastTurnSaidValue;
            } else {
                if (lastTurnSaidValue == lastTurnActualValue) {
                    System.out.println(answeringPlayer.getName() + " chose NO");
                    Thread.sleep(1000);
                    System.out.println("Sorry but " + currentPlayer.getName() + " told the truth!");
                    System.out.println(answeringPlayer.getName() + " you have lost a life point.");
                    answeringPlayer.setLifeTotal(answeringPlayer.getLifeTotal() - 1);
                    this.currentValue = 0;
                    Thread.sleep(5500);
                } else {
                    System.out.println(answeringPlayer.getName() + " chose YES");
                    Thread.sleep(1000);
                    System.out.println("You are correct! " + currentPlayer.getName() + " lied!");
                    System.out.println(currentPlayer.getName() + " you have lost a life point.");
                    currentPlayer.setLifeTotal(currentPlayer.getLifeTotal() - 1);
                    this.currentValue = 0;
                    Thread.sleep(5500);
                }
            }
        }
    }

    //Clears the screen by spamming empty lines.
    public void clearScreen() {
        for (int i = 0; i < 100; ++i) {
            System.out.println("");
        }
    }

    //Clears the turn
    public void newTurn(Player player) throws InterruptedException {
        clearScreen();
        if (player.isBot() == true) {
            System.out.println("Bot " + player.getName() + " it's your turn.");
            System.out.println("You currently have: " + player.getLifeTotal() + " lives.");
            Thread.sleep(5000);
            return;
        }
        System.out.println(player.getName() + " it is your turn.");
        System.out.println("You currently have: " + player.getLifeTotal() + " lives.");
        System.out.println("Press enter.");
        sc.nextLine();
    }

    //Takes into a count if it isn't turn one.
    public void newNextTurn(Player player) throws InterruptedException {
        newTurn(player);
        System.out.println("The current value to beat is: " + this.currentValue);
        Thread.sleep(3000);
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

    public void botRollDice() {
        dice1.rollDice();
        dice2.rollDice();
    }

    //Lets the player decide to lie or go with the roll.
    public void chooseRoll() {
        //Check wich option
        System.out.println("Please pick your option:");
        System.out.println("'L': Lie about your roll");
        System.out.println("'T': Tell the truth");
        System.out.println("'R': Random hidden reroll");
        String mode = sc.nextLine();

        //Lie
        if (mode.equals("L")) {
            lieRoll();
        } //Truth
        else if (mode.equals("T")) {
            if (this.currentValue != 0) {
                if (this.currentValue == getDiceValue(dice1.getFaceValue(), dice2.getFaceValue())) {
                    System.out.println("Sorry you can't pick this option, you have rolled to low!");
                    chooseRoll();
                    return;
                }
            }
            truthRoll();
        } //Reroll
        else if (mode.equals("R")) {
            if (this.currentValue == 0) {
                System.out.println("Sorry you can't reroll on the first turn!");
                chooseRoll();
                return;
            }
            reRoll();
        } //Not an option exception
        else {
            clearScreen();
            System.out.println("Sorry, that is not an option");
            chooseRoll();
        }
    }

    public void botRollChoice() {
        if (currentValue > getDiceValue(dice1.getFaceValue(), dice2.getFaceValue())) {
            if ((ran.nextInt(9) + 1) % 2 == 0) {
                botLieRoll();
            }
            reRoll();
        } else {
            truthRoll();
        }
    }

    //Returns the player who had turn before the player on given index.
    public Player getNextPlayer(int i) {
        int totalPlayers = amountOfBots + amountOfPlayers;
        if (i == totalPlayers - 1) {
            return players.get(0);
        } else {
            return players.get(i + 1);
        }
    }

    //If the player lies about their roll.
    public void lieRoll() {
        //Check wether 
        System.out.println("Pick the first die's value:");
        String die1 = sc.nextLine();
        //Exception Handling
        //Exception 1 - Empty answer
        if (die1.isEmpty()) {
            System.out.println("Please make an input!");
            lieRoll();
            return;
        }
        //Exception 2 - Not a number
        for (char c : die1.toCharArray()) {
            if (!Character.isDigit(c)) {
                clearScreen();
                System.out.println("Must be a number");
                lieRoll();
                return;
            }
        }
        //Exception 3 - Not a legal number
        int parsedDie1 = Integer.parseInt(die1);
        if (parsedDie1 <= 0 || parsedDie1 > 6) {
            clearScreen();
            System.out.println("Pick a number from 1 - 6 please.");
            lieRoll();
            return;
        }

        System.out.println("Pick the second die's value:");
        String die2 = sc.nextLine();
        //Exception Handling
        //Exception 1 - Empty answer
        if (die2.isEmpty()) {
            System.out.println("Please make an input!");
            lieRoll();
            return;
        }
        //Exception 2 - Not a number
        for (char c : die2.toCharArray()) {
            if (!Character.isDigit(c)) {
                clearScreen();
                System.out.println("Must be a number");
                lieRoll();
                return;
            }
        }
        //Exception 3 - Not a legal number
        int parsedDie2 = Integer.parseInt(die2);
        if (parsedDie2 <= 0 || parsedDie2 > 6) {
            clearScreen();
            System.out.println("Pick a number from 1 - 6 please.");
            lieRoll();
            return;
        }

        //Exception 7 - Dice value too small
        if (currentValue > getDiceValue(parsedDie1, parsedDie2)) {
            System.out.println("Please pick a bigger number. The current value to beat is " + currentValue);
            lieRoll();
            return;
        }

        lastTurnActualValue = getDiceValue(dice1.getFaceValue(), dice2.getFaceValue());
        lastTurnSaidValue = getDiceValue(parsedDie1, parsedDie2);

    }

    public void botLieRoll() {
        Random rand = new Random();
        int parsedDie1 = rand.nextInt(6) + 1;
        int parsedDie2 = rand.nextInt(6) + 1;

        if (currentValue > getDiceValue(parsedDie1, parsedDie2)) {
            lieRoll();
        }

        lastTurnActualValue = getDiceValue(dice1.getFaceValue(), dice2.getFaceValue());
        lastTurnSaidValue = getDiceValue(parsedDie1, parsedDie2);
    }

    //If the player tells the truth about their roll.
    public void truthRoll() {
        lastTurnActualValue = getDiceValue(dice1.getFaceValue(), dice2.getFaceValue());
        lastTurnSaidValue = lastTurnActualValue;
    }

    //Random reroll.
    public void reRoll() {
        this.currentReroll = true;
        dice1.rollDice();
        dice2.rollDice();
        lastTurnActualValue = getDiceValue(dice1.getFaceValue(), dice2.getFaceValue());
        lastTurnSaidValue = lastTurnActualValue;
    }

    //Returns a value of two ints.
    public int getDiceValue(int val, int val2) {
        if (val == 1 && val2 == 2) {
            return checkMeyer(Integer.valueOf(String.valueOf(val) + String.valueOf(val2)));
        } else if (val2 == 1 && val == 2) {
            return checkMeyer(Integer.valueOf(String.valueOf(val2) + String.valueOf(val)));
        } else if (val == 1 && val2 == 3) {
            return checkSmallMeyer(Integer.valueOf(String.valueOf(val) + String.valueOf(val2)));
        } else if (val2 == 1 && val == 3) {
            return checkSmallMeyer(Integer.valueOf(String.valueOf(val2) + String.valueOf(val)));
        } else if (val2 == val) {
            return checkPair(Integer.valueOf(String.valueOf(val2) + String.valueOf(val)));
        } else if (val == 3 && val2 == 2 || val == 2 && val2 == 3) {
            return checkBeerMeyer(Integer.valueOf(String.valueOf(val) + String.valueOf(val2)));
        } else if (val > val2) {
            return Integer.valueOf(String.valueOf(val) + String.valueOf(val2));
        }
        return Integer.valueOf(String.valueOf(val2) + String.valueOf(val));
    }

    //Check methods checks for specific kind of rolls
    public int checkPair(int val) {
        if (val == 11 || val == 22 || val == 33 || val == 44 || val == 55 || val == 66) {
            return val * 10;
        }
        return val;
    }

    public int checkSmallMeyer(int val) {
        if (val == 13) {
            return val * 100;
        }
        return val;
    }

    public int checkMeyer(int val) {
        if (val == 12) {
            return val * 1000;
        }
        return val;
    }

    public int checkBeerMeyer(int val) {
        if (val == 32 || val == 23) {
            return val * 10000;
        }
        return val;
    }

}
