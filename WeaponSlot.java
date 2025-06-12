import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Rectangle;
/**
 * Write a description of class WeaponSlot here.
 * 
 * @author Briannie Law
 * @version 6/11/2025
 */
public class WeaponSlot extends Actor {
    private GreenfootImage baseImage;
    private boolean occupied = false;
    
    public boolean isOccupied() {
        return occupied;
    }
    
    public WeaponSlot() {
        baseImage = new GreenfootImage("slot.png"); // 77x77 像素的武器槽底座图
        setImage(new GreenfootImage(baseImage)); // 初始设置为透明图
        getImage().setTransparency(0);           // 默认不可见
    }

    public void showTransparent() {
        GreenfootImage img = new GreenfootImage(baseImage);
        img.setTransparency(100); // 半透明显示
        setImage(img);
    }

    public void hide() {
        GreenfootImage img = new GreenfootImage(baseImage);
        img.setTransparency(0); // 完全隐藏
        setImage(img);
    }

    public Rectangle getBoundingRectangle() {
        int w = getImage().getWidth();
        int h = getImage().getHeight();
        return new Rectangle(getX() - w / 2, getY() - h / 2, w, h);
    }
    
    public void setTransparency(int value) {
        getImage().setTransparency(value); // 0 完全透明，255 不透明
    }
    
    public void setOccupied(boolean occ) {
        this.occupied = occ;
        if (occ) {
            // 放置后底座不透明
            GreenfootImage img = new GreenfootImage(baseImage);
            img.setTransparency(255);
            setImage(img);
        } else {
            // 空闲时底座默认隐藏或半透明
            hide();
        }
    }
}