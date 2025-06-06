import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Point;

/**
 * Write a description of class Enemy here.
 * 
 * @author (Yilin Ma) 
 * @version (2025.06.05)
 */
public class Enemy extends Actor {
    private int pathIndex = 0;

    public void act() {
        if (pathIndex < MainGameWorld.enemyPath.size()) {
            Point target = MainGameWorld.enemyPath.get(pathIndex);
            moveTo(target);
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
            double angle = Math.atan2(dy, dx);
            int newX = (int)(getX() + Math.cos(angle) * step);
            int newY = (int)(getY() + Math.sin(angle) * step);
            setLocation(newX, newY);
        }
    }
}
