import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CookieHPManager here.
 * 
 * @author Yilin Ma
 * @version 2025.06.16
 */
public class CookieHPManager extends Actor {
    private int hp;
    private GreenfootImage[] digits = new GreenfootImage[10];
    private PictureActor tensDigit, onesDigit, heartIcon;

    public CookieHPManager(int initialHP) {
        this.hp = initialHP;
        for (int i = 0; i <= 9; i++) {
            digits[i] = new GreenfootImage("HPdigit_" + i + ".png");
        }
        heartIcon = new PictureActor("heart_icon.png");
        tensDigit = new PictureActor("HPdigit_1.png");
        onesDigit = new PictureActor("HPdigit_0.png");
    }

    protected void addedToWorld(World world) {
        world.addObject(heartIcon, 203, 47);
        world.addObject(tensDigit, 98, 47);
        world.addObject(onesDigit, 147, 47);
        updateDisplay();
    }

    public void takeDamage(int amount) {
        hp -= amount;
        if (hp < 0) hp = 0;
        updateDisplay();
        if (hp == 0) {
            Greenfoot.stop(); // Game over
        }
    }

    public int getHP() {
        return hp;
    }

    public void updateDisplay() {
        int tens = hp / 10;
        int ones = hp % 10;
        tensDigit.setPicture("HPdigit_" + tens + ".png");
        onesDigit.setPicture("HPdigit_" + ones + ".png");
    }
}
