import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PassiveSkillManager here.
 * 
 * @author Briannie Law
 * @version 6/16/2025
 */
public class PassiveSkillManager {
    private static PassiveSkillManager instance;

    private boolean isBlueberry = false;
    private boolean isCocoa = false;

    private PassiveSkillManager() {}

    public static PassiveSkillManager getInstance() {
        if (instance == null) {
            instance = new PassiveSkillManager();
        }
        return instance;
    }

    public void selectCookie(String cookieType) {
        // Reset all flags first
        isBlueberry = false;
        isCocoa = false;

        if (cookieType == null) return; // Defensive check

        // Use equalsIgnoreCase for flexibility
        if (cookieType.equalsIgnoreCase("blueberry")) {
            isBlueberry = true;
        } else if (cookieType.equalsIgnoreCase("cocoa")) {
            isCocoa = true;
        }
    }

    public boolean isBlueberryCookieActive() {
        return isBlueberry;
    }

    public boolean isCocoaCookieActive() {
        return isCocoa;
    }

    // Optional: reset all selections
    public void clearSelection() {
        isBlueberry = false;
        isCocoa = false;
    }
}

