import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * While dragging a tower, this shows the range that the Weapon can shoot to. 
 * 
 * @author Briannie Law
 * @version 6/11/2025
 */

public class RangeCircle extends Actor {
    public RangeCircle(int radius) {
        GreenfootImage img = new GreenfootImage(radius * 2, radius * 2); //Change this to change size of radius.
        img.setColor(new Color(255, 0, 0, 50)); // red with transparency
        img.fillOval(0, 0, radius * 2, radius * 2);
        setImage(img);
    }
}