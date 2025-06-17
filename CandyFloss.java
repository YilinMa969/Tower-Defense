import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Write a description of class CandyFloss here.
 * 
 * @author (Yilin Ma) 
 * @version (2025.6.13)
 */

// CandyFloss类，继承Weapons
public class CandyFloss extends Weapons {
    private GreenfootImage[] attackFrames;
    private int frameIndex = 0;
    private int frameTimer = 0;
    private final int FRAME_DELAY = 10;
    
    private int soundCooldown = 0;
    private final int SOUND_COOLDOWN_TIME = 160; 

    public CandyFloss(int x, int y) {
        super(x, y, 150, 0, 100, 10);  // range, damage, attackSpeed, cost
        attackFrames = new GreenfootImage[] {
            new GreenfootImage("CandyFloss1.png"),
            new GreenfootImage("CandyFloss2.png")
        };
        setImage(attackFrames[0]);
    }

    @Override
    public void act() {
        super.act();
        
        if (soundCooldown > 0) {
            soundCooldown--;
        }
        
        // 减速范围内敌人，刷新减速计时
        for (Enemy enemy : getWorld().getObjects(Enemy.class)) {
            if (isInRange(enemy)) {
                if (!enemy.isSlowed()) {
                    enemy.setSlowed(true);
                    if (soundCooldown == 0) {
                        Greenfoot.playSound("candyfloss_attack.wav");
                        soundCooldown = SOUND_COOLDOWN_TIME;
                    }
                }
            }
        }

        // 动画播放（已放置后）
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
        // 不造成伤害，只减速
    }

    @Override
    public Weapons createCopy(int x, int y) {
        return new CandyFloss(x, y);
    }
    
    protected String getPriceImageName() {
        return "CandyFloss";
    }
}

