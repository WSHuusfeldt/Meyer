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

    public Player(int lifeTotal, Dice[] value) {
        this.lifeTotal = lifeTotal;
        this.diceValue = value;
    }
    
    public Player newPlayer() {
        return new Player(6, null);        
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
