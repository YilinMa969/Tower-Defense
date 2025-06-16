import greenfoot.*;

/**
 * Shows a bubble image attached to a bubbled enemy.
 * 
 * @author Yilin Ma
 * @version 2025.6.13
 */
public class BubbleOverlay extends Actor {
    private Enemy target;
    private int timer = 120;  // 泡泡持续时间

    public BubbleOverlay(Enemy target) {
        this.target = target;
        setImage("bubble_overlay.png");  // 需要准备这个图片
    }

    public void act() {
        if (target == null || !getWorld().getObjects(Enemy.class).contains(target)) {
            getWorld().removeObject(this);
            return;
        }

        setLocation(target.getX(), target.getY());

        timer--;
        if (timer <= 0) {
            target.setBubbled(false);
            getWorld().removeObject(this);
        }
    }
}
