import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CocoaCookie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CocoaCookie extends SelectableCookie {
    public CocoaCookie(String animationPrefix) {
        super(false, animationPrefix); // pass false or true based on your use
        hpManager = new CookieHPManager(10);  // base HP for cocoa cookie
    }
}
