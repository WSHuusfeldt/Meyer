package Entity;

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
    private Dice[] diceValue;
    private String name;

    public Player(int lifeTotal, Dice[] value, String name) {
        this.lifeTotal = lifeTotal;
        this.diceValue = value;
        this.name = name;
    }
    
    public Player newPlayer(String name) {
        return new Player(6, null, name);        
    }

    public int getLifeTotal() {
        return lifeTotal;
    }

    public Dice[] getValue() {
        return diceValue;
    }

    public void setName(String name) {
        this.name = name;
    }
    
        public void setValue(Dice[] value) {
        this.diceValue = value;
    }
    
    
    
}
