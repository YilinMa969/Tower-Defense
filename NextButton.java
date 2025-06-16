import greenfoot.*;

/**
 * Button to proceed from character selection to the main game.
 * 
 * @author (Yilin Ma)
 * @version (2025.06.06)
 */
class NextButton extends Actor {
    
    public NextButton() {
        setImage("next_button.png"); // 放入一张“Next”按钮图像
    }
    
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            CharacterSelectWorld selectWorld = (CharacterSelectWorld) getWorld();
            String cookieType = selectWorld.getSelectedCookieName();     // 例如 "cocoa"
            String smallPrefix = selectWorld.getSelectedSmallPrefix();   // 例如 "cocoa_small_"
            MainGameWorld world = new MainGameWorld(cookieType, smallPrefix);
            Greenfoot.setWorld(world);
        }
    }

    
}