import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Point;

/**
 * Enemy that follows a path and flips image when changing direction.
 * Default image faces right; flips horizontally when moving left.
 * 
 * @author Yilin Ma
 * @version 2025.6.16
 */
public class Enemy extends Actor {
    protected GreenfootImage originalImage;
    protected GreenfootImage flippedImage;
    protected GreenfootImage slowedImage;
    protected GreenfootImage slowedFlippedImage;

    protected boolean facingLeft = false;
    protected boolean removed = false;
    protected boolean isSlowed = false;
    protected int pathIndex = 0;

    protected int normalSpeed = 2;
    protected int slowedSpeed = 1;

    protected int slowTimer = 0;
    protected final int SLOW_DURATION = 120;

    private boolean bubbled = false;
    private int bubbleTimer = 0;
    private final int BUBBLE_DURATION = 120;

    protected int health = 10;
    
    public Enemy(String normalImageFile, String slowedImageFile) {
        originalImage = new GreenfootImage(normalImageFile);
        flippedImage = new GreenfootImage(originalImage);
        flippedImage.mirrorHorizontally();

        slowedImage = new GreenfootImage(slowedImageFile);
        slowedFlippedImage = new GreenfootImage(slowedImage);
        slowedFlippedImage.mirrorHorizontally();

        setImage(originalImage);
    }
    
    // 减速逻辑
    public void setSlowed(boolean slowed) {
        if (slowed) {
            slowTimer = SLOW_DURATION;
        }
        this.isSlowed = slowed;
        updateImage();
    }

    public boolean isSlowed() {
        return isSlowed;
    }

    // 泡泡逻辑
    public void setBubbled(boolean b) {
        if (!bubbled && b) {
            bubbleTimer = BUBBLE_DURATION;
        }
        this.bubbled = b;
    }

    public boolean isBubbled() {
        return bubbled;
    }

    private void updateImage() {
        if (isSlowed) {
            setImage(facingLeft ? slowedFlippedImage : slowedImage);
        } else {
            setImage(facingLeft ? flippedImage : originalImage);
        }
    }

    public void act() {
        if (removed) return;

        // 泡泡计时器
        if (bubbled) {
            bubbleTimer--;
            if (bubbleTimer <= 0) {
                bubbled = false;
            } else {
                return; // 泡泡中不动
            }
        }

        // 减速计时器
        if (slowTimer > 0) {
            slowTimer--;
            if (slowTimer == 0) {
                setSlowed(false);
            }
        }

        // 路径移动
        if (pathIndex < MainGameWorld.enemyPath.size()) {
            Point target = MainGameWorld.enemyPath.get(pathIndex);
            moveTo(target);
        }

        if (reachedEnd()) {
            MainGameWorld world = (MainGameWorld) getWorld();
            CookieHPManager hp = world.getCookieHPManager();
        
            if (this instanceof SmallCakeMonster) {
                hp.takeDamage(1);
            } else if (this instanceof EliteCakeMonster) {
                hp.takeDamage(2);
            } else if (this instanceof BossMonster) {
                hp.takeDamage(3);
            }
        
            removed = true;
            getWorld().removeObject(this);
        }
    }

    private void moveTo(Point target) {
        int dx = target.x - getX();
        int dy = target.y - getY();
        int step = isSlowed ? slowedSpeed : normalSpeed;

        if (Math.abs(dx) <= step && Math.abs(dy) <= step) {
            setLocation(target.x, target.y);
            pathIndex++;
        } else {
            if (dx < 0 && !facingLeft) {
                facingLeft = true;
                updateImage();
            } else if (dx > 0 && facingLeft) {
                facingLeft = false;
                updateImage();
            }

            double angle = Math.atan2(dy, dx);
            int newX = (int)(getX() + Math.cos(angle) * step);
            int newY = (int)(getY() + Math.sin(angle) * step);
            setLocation(newX, newY);
        }
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            removed = true;
            getWorld().removeObject(this);
        }
    }

    private boolean reachedEnd() {
        Point end = MainGameWorld.enemyPath.get(MainGameWorld.enemyPath.size() - 1);
        return Math.abs(getX() - end.x) < 10 && Math.abs(getY() - end.y) < 10;
    }
    
    public int getHealth() {
        return health;
    }
}