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
    
    //Parameters are value of each dice, it returns the value put together (not addition)
    public int getDiceValue(int val, int val2) {
        if (val == 1 && val2 == 2)
            return checkMeyer(Integer.valueOf(String.valueOf(val) + String.valueOf(val2)));
        
        else if (val2 == 1 && val == 2) 
            return checkMeyer(Integer.valueOf(String.valueOf(val2) + String.valueOf(val)));
        
        else if (val == 1 && val2 == 3) 
            return checkSmallMeyer(Integer.valueOf(String.valueOf(val) + String.valueOf(val2)));
        
        else if (val2 == 1 && val == 3) 
            return checkSmallMeyer(Integer.valueOf(String.valueOf(val2) + String.valueOf(val)));
        
        else if (val2 == val) 
            return checkPair(Integer.valueOf(String.valueOf(val2) + String.valueOf(val)));
        
        else if (val > val2)
            return Integer.valueOf(String.valueOf(val) + String.valueOf(val2));
        
        else if(val == 3 && val2 == 2)
            return checkBeerMeyer(Integer.valueOf(String.valueOf(val) + String.valueOf(val2)));
        
        else if(val == 2 && val2 == 3)
            return checkBeerMeyer(Integer.valueOf(String.valueOf(val2) + String.valueOf(val)));
        
        return Integer.valueOf(String.valueOf(val2) + String.valueOf(val));
    }
    
    public int checkPair(int val) {
        if(val == 11 || val == 22 || val == 33 || val == 44 || val == 55 || val == 66)
            return val*10;
        return val;
    }
    
    public int checkMeyer(int val) {
        if(val == 12)
            return val*100;
        return val;
    }
    
    public int checkSmallMeyer(int val) {
        if(val == 13)
            return val*75;
        return val;
    }
    
   public int checkBeerMeyer(int val){
       if(val == 32){
           return val*100;
       }
       return val;
   }
    
    
    //compares current players dice throw to earlier players fice value
    public boolean compareValue(int dVal, int dVal2) {
        if (dVal >= dVal2)
            return true;
        return false;
    }
    
    public static void main(String[] args) {
        Dice d = new Dice();
        Dice d2 = new Dice();
        d.rollDice();
        d2.rollDice();
        
        System.out.println(d.getDiceValue(d.getFaceValue(), d2.getFaceValue()));
    }
    
}
