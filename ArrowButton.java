import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A reusable arrow button for switching cookies.
 * Direction -1 for left, +1 for right.
 * 
 * @author (Yilin Ma)
 * @version (2025.06.04)
 */
class ArrowButton extends Actor {
    private int direction;

    public ArrowButton(int direction) {
        this.direction = direction;
        if (direction < 0) {
            setImage("left_arrow.png");
        } else {
            setImage("right_arrow.png");
        }
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            ((CharacterSelectWorld) getWorld()).changeCookie(direction);
        }
    }
}
