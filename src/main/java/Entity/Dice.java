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

    public Dice(int faceValue) {
        this.faceValue = faceValue;
    }

    public int getFaceValue() {
        return faceValue;
    }
    
    public void rollDice() {
        this.faceValue = (int) (Math.random()*6)+1;
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
