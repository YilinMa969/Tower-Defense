import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BlueberryCookie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BlueberryCookie extends SelectableCookie {
    public BlueberryCookie(String animationPrefix) {
        super(false, animationPrefix);
        hpManager = new CookieHPManager(10);
    }
}
