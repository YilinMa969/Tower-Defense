import greenfoot.*; 
import java.util.List;

/**
 * Bubblegum weapon: traps a single enemy in a bubble so it cannot move.
 * Each attack only bubbles one enemy at a time.
 * 
 * @author Yilin Ma
 * @version 2025.6.13
 */
public class Bubblegum extends Weapons {
    private GreenfootImage[] attackFrames;
    private int frameIndex = 0;
    private int frameTimer = 0;
    private final int FRAME_DELAY = 10;

    public Bubblegum(int x, int y) {
        super(x, y, 150, 0, 1, 5);  // range, damage=0, attackSpeed=1 hit/sec, cost
        attackFrames = new GreenfootImage[] {
            new GreenfootImage("Bubblegum1.png"),
            new GreenfootImage("Bubblegum2.png")
        };
        setImage(attackFrames[0]);
    }

    @Override
    public void act() {
        super.act();
        
        // 播放攻击动画（放置后）
        if (isLocked()) {
            frameTimer++;
            if (frameTimer >= FRAME_DELAY) {
                frameIndex = (frameIndex + 1) % attackFrames.length;
                setImage(attackFrames[frameIndex]);
                frameTimer = 0;
            }
        }
    }

    @Override
    public void attack(Enemy enemy) {
        // 只对未被泡泡困住的敌人有效
        if (!enemy.isBubbled()) {
            enemy.setBubbled(true);

            // 加一个泡泡覆盖图（你需要在 images 文件夹放 bubble_overlay.png）
            BubbleOverlay overlay = new BubbleOverlay(enemy);
            getWorld().addObject(overlay, enemy.getX(), enemy.getY());
        }
    }

    @Override
    public Weapons createCopy(int x, int y) {
        return new Bubblegum(x, y); // 注意：之前写错写成 CandyFloss
    }
    
    protected String getPriceImageName() {
        return "Bubblegum";
    }
}
