import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class StarManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


public class StarManager extends Actor {
    private int stars = 15;  // 初始15颗星
    private long lastUpdate = System.currentTimeMillis();

    private GreenfootImage[] digits = new GreenfootImage[10];
    private PictureActor tensDigit, onesDigit, starIcon;

    public StarManager() {
        // 加载数字图片0~9
        for (int i = 0; i <= 9; i++) {
            digits[i] = new GreenfootImage("digit_" + i + ".png"); // 请放置数字图片到digits/目录
        }
        starIcon = new PictureActor("star_icon.png");  // 星星图标
        tensDigit = new PictureActor("digit_0.png");
        onesDigit = new PictureActor("digit_0.png");
    }

    protected void addedToWorld(World world) {
        world.addObject(starIcon, 435, 47);
        world.addObject(tensDigit, 313, 47);
        world.addObject(onesDigit, 363, 47);
        updateDisplay();
    }

    public void act() {
        if (System.currentTimeMillis() - lastUpdate >= 3000) {
            addStars(1);
            lastUpdate = System.currentTimeMillis();
        }
    }

    public void addStars(int amount) {
        stars += amount;
        if (stars > 99) stars = 99;  // 最大99星
        updateDisplay();
    }

    public boolean spendStars(int amount) {
        if (stars >= amount) {
            stars -= amount;
            updateDisplay();
            return true;
        }
        return false;
    }

    public int getStars() {
        return stars;
    }

    private void updateDisplay() {
        int tens = stars / 10;
        int ones = stars % 10;
        tensDigit.setPicture("digit_" + tens + ".png");
        onesDigit.setPicture("digit_" + ones + ".png");
    }
}
