import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Point;

/**
 * Enemy that follows a path and flips image when changing direction.
 * Default image faces right; flips horizontally when moving left.
 * 
 * Author: Yilin Ma
 * Version: 2025.06.10
 */
public class Enemy extends Actor {
    protected GreenfootImage originalImage;
    protected GreenfootImage flippedImage;
    protected boolean facingLeft = false;
    protected int pathIndex = 0;

     public Enemy(String imageFile) {
        originalImage = new GreenfootImage(imageFile);
        flippedImage = new GreenfootImage(originalImage);
        flippedImage.mirrorHorizontally();
        setImage(originalImage);
    }

    public void act() {
        if (pathIndex < MainGameWorld.enemyPath.size()) {
            Point target = MainGameWorld.enemyPath.get(pathIndex);
            moveTo(target);
        }
        
        // 碰撞检测
        if (isTouching(SelectableCookie.class)) {
            getWorld().removeObject(this);
            // 你可以在这里减少玩家生命或其他逻辑
        }
    
    }

    private void moveTo(Point target) {
        int dx = target.x - getX();
        int dy = target.y - getY();
        int step = 2;

        if (Math.abs(dx) <= step && Math.abs(dy) <= step) {
            setLocation(target.x, target.y);
            pathIndex++;
        } else {
            // 正确判断：dx < 0 表示向左，要翻转
            if (dx < 0 && !facingLeft) {
                setImage(flippedImage); // 向左，翻图
                facingLeft = true;
            } else if (dx > 0 && facingLeft) {
                setImage(originalImage); // 向右，用原图
                facingLeft = false;
            }

            double angle = Math.atan2(dy, dx);
            int newX = (int)(getX() + Math.cos(angle) * step);
            int newY = (int)(getY() + Math.sin(angle) * step);
            setLocation(newX, newY);
        }
    }
}

