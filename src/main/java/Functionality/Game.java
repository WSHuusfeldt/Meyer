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
    private int lastTurnSaidValue = 0;
    private int lastTurnActualValue = 0;
    private int currentValue = 0;
    private boolean currentLie = false;

    public void run() {

        //Game setup
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter amount of players");
        Dice d1 = new Dice();
        Dice d2 = new Dice();

        String playerInput = sc.nextLine();
        for (char c : playerInput.toCharArray()) {
            if (!Character.isDigit(c)) {
                clearScreen();
                System.out.println("Must be a number");
                run();
                break;
            }
        }
        int parsedPlayers = Integer.parseInt(playerInput);
        if (parsedPlayers <= 1 || parsedPlayers > 6) {
            clearScreen();
            System.out.println("You can only be 2-6 players");
            run();
        } else {
            amountOfPlayers = parsedPlayers;
        }
        clearScreen();

        for (int i = 0; i < amountOfPlayers; ++i) {
            System.out.println("Enter the name of player " + (i + 1));
            String nameOfPlayer = sc.nextLine();
            System.out.println("Is this name fine: " + nameOfPlayer + "?. 'y' for yes - everything else for no");
            String checkNameOfPlayer = sc.nextLine();
            if (checkNameOfPlayer.equals("y")) {
                players.add(new Player(nameOfPlayer));
                clearScreen();
            } else {
                --i;
                clearScreen();
            }
        }

        //Running game
        while (this.game) {
            //All players gets a turn
            for (int i = 0; i < players.size(); ++i) {
                clearScreen();
                //Only players with life has any actions
                Player currentPlayer = players.get(i);
                if (currentPlayer.getLifeTotal() > 0) {
                    System.out.println("It is your turn " + currentPlayer.getName());
                    System.out.println("The current value to beat is " + currentValue);
                    System.out.println("The last player said they got: " + lastTurnSaidValue + ". Do you believe that? - 'y' for yes, everything else for no");

                    String checkTrustOfPlayer = sc.nextLine();
                    if (checkTrustOfPlayer.equals("y")) {
                        System.out.println("Press for roll");
                        sc.nextLine();
                        d1.rollDice();
                        d2.rollDice();
                        System.out.println("You have rolled " + d1.getFaceValue() + " and " + d2.getFaceValue());
                        sc.nextLine();

                        System.out.println("Do you wish to lie about your number? - 'y' for yes, everything else for no");
                        String checkLieOfPlayer = sc.nextLine();

                        if (getDiceValue(d1.getFaceValue(), d2.getFaceValue()) >= currentValue) {
                            if (checkLieOfPlayer.equals("y")) {
                                //do something
                            } else {
                                currentValue = getDiceValue(d1.getFaceValue(), d2.getFaceValue());
                                lastTurnActualValue = getDiceValue(d1.getFaceValue(), d2.getFaceValue());
                            }
                        } else {
                            System.out.println("You have rolled a number lower than the current value.");
                            System.out.println("Do you wish to lie about your number? - 'y' for yes, everything else for a hidden reroll");
                            checkLieOfPlayer = sc.nextLine();
                            if (checkLieOfPlayer.equals("y")) {
                                //do something
                            } else {
                                //do something
                            }
                        }
                    } else {
                        System.out.println("They said they had " + lastTurnSaidValue);
                        sc.nextLine();
                        if (lastTurnActualValue == lastTurnActualValue) {
                            System.out.println("And it was true! You have lost life.");
                            players.get(i).setLifeTotal(players.get(i).getLifeTotal()-1);
                        } else {
                            System.out.println("And they lied! They have lost life.");
                            if (i == 0) {
                                players.get(amountOfPlayers).setLifeTotal(players.get(amountOfPlayers).getLifeTotal()-1);
                            } else {
                                players.get(i-1).setLifeTotal(players.get(i-1).getLifeTotal()-1);
                            }
                        }
                    }

                } else {
                    System.out.println(players.get(i) + "has no more life and has been removed.");
                    players.remove(i);
                }
            }

            //Checking wether all but one player has 0 life and thus if the game has ended.
            int count = 0;
            for (int i = 0; i < players.size(); ++i) {
                if (players.get(i).getLifeTotal() == 0) {
                    count++;
                }
            }
            if (count == players.size() - 1) {
                this.game = false;
            }
        }

    }

    public int compareValue(int dVal, int dVal2) {
        if (dVal > dVal2) {
            return dVal;
        }
        return dVal2;
    }

    public void clearScreen() {
        for (int i = 0; i < 100; ++i) {
            System.out.println("");
        }
    }

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
        } else if (val > val2) {
            return Integer.valueOf(String.valueOf(val) + String.valueOf(val2));
        } else if (val == 3 && val2 == 2) {
            return checkBeerMeyer(Integer.valueOf(String.valueOf(val) + String.valueOf(val2)));
        } else if (val == 2 && val2 == 3) {
            return checkBeerMeyer(Integer.valueOf(String.valueOf(val2) + String.valueOf(val)));
        }

        return Integer.valueOf(String.valueOf(val2) + String.valueOf(val));
    }

    public int checkPair(int val) {
        if (val == 11 || val == 22 || val == 33 || val == 44 || val == 55 || val == 66) {
            return val * 10;
        }
        return val;
    }

    public int checkMeyer(int val) {
        if (val == 12) {
            return val * 100;
        }
        return val;
    }

    public int checkSmallMeyer(int val) {
        if (val == 13) {
            return val * 75;
        }
        return val;
    }

    public int checkBeerMeyer(int val) {
        if (val == 32) {
            return val * 100;
        }
        return val;
    }

}
