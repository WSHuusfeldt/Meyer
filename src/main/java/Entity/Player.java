package Entity;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author emilt
 */
public class Player {

    private int lifeTotal;
    private ArrayList<Integer> diceValue = new ArrayList();
    private String name;
    private boolean bot;

    public Player(int lifeTotal, int value1, int value2, String name) {
        this.lifeTotal = lifeTotal;
        this.diceValue.add(value1);
        this.diceValue.add(value2);
        this.name = name;
        this.bot = false;
    }

    public Player(String name) {
        this.lifeTotal = 6;
        this.diceValue = null;
        this.name = name;
        this.bot = false;
    }
    
    public Player(String name, boolean bot) {
        this.lifeTotal = 6;
        this.diceValue = null;
        this.name = name;
        this.bot = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLifeTotal(int lifeTotal) {
        this.lifeTotal = lifeTotal;
    }

    public int getLifeTotal() {
        return lifeTotal;
    }

    public ArrayList<Integer> getDiceValue() {
        return diceValue;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    

    

}
