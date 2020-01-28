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
    
    private String name;
    private int lifeTotal;
    private Dice[] diceValue;

    public Player(String name, int lifeTotal, Dice[] value) {
        this.name = name;
        this.lifeTotal = lifeTotal;
        this.diceValue = value;
    }
    
    public Player newPlayer(String name) {
        return new Player(name, 6, null);        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public int getLifeTotal() {
        return lifeTotal;
    }

    public Dice[] getValue() {
        return diceValue;
    }

    public void setValue(Dice[] value) {
        this.diceValue = value;
    }
    
    
    
}
