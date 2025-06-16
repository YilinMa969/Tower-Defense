import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HazelnutCookie here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HazelnutCookie extends SelectableCookie {
    public HazelnutCookie(String animationPrefix) {
        super(false, animationPrefix);  // false if small version; or pass a param if needed
        // Set higher base HP specific to Hazelnut
        hpManager = new CookieHPManager(15);
    }
    
    @Override
    public int getInitialHP() {
        return 15;
    }
}
