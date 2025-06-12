import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Rectangle;
/**
 * Write a description of class WeaponSlot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WeaponSlot extends Actor
{
    public WeaponSlot ()
    {
        setImage("hole.png");
    }
    
    public void act() {
        super.act();
        if (isTouching (Weapons.class))
        {
            //setImage ("");
        }
    }
    
    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX() - 50, getY() - 50, 100, 100);  // 40x40 slot area
    }
}
