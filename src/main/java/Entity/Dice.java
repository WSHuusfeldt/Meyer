/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author emilt
 */
public class Dice {
    
    private int faceValue;

    public Dice() {
        this.faceValue = 0;
    }

    public int getFaceValue() {
        return faceValue;
    }

    public void rollDice() {
        this.faceValue = (int) (Math.random()*6)+1;
    }
        
    
    //compares current players dice throw to earlier players fice value
    public boolean compareValue(int dVal, int dVal2) {
        if (dVal >= dVal2)
            return true;
        return false;
    }
    
}
