import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;

import java.awt.Rectangle;

/**
 * The Weapons in the game. Weapons can be placed on certain spots on the map.
 * When a weapon kills a cake monster, money goes up. 
 * With enough money, you are able to upgrade the weapon towers, to a max of 3 levels. 
 * Upgrading a weapon lets them do more damage. 
 * 
 * Range that the weapon is allowed to hit, when upgrade the range increases. Use getObjectsInRange
 * 
 * @author Briannie Law
 * @version 6/6/2025
 */
public abstract class Weapons extends Actor
{
    protected int range;               // Attack range
    protected int damage;              // Damage per attack
    protected long attackSpeed;        // Attacks per second
    protected int cost;                // Cost to build
    protected long cooldown;           // Cooldown timer

    protected Enemy target; // Current enemy target
    
    private boolean isDragging = false;
    private int offsetX, offsetY; // Store the mouse click offset
    private long lastAttackTime = 0;
    private RangeCircle rangeCircle = null;
    private boolean isLocked = false; // To track if the tower is locked in place

    public abstract void attack(Enemy enemy);
    public abstract Weapons createCopy();
    
    public Weapons(int x, int y, int range, int damage, long attackSpeed, int cost) {
        this.range = range;
        this.damage = damage;
        this.attackSpeed = attackSpeed;
        this.cost = cost;
        this.cooldown = 0;
        isLocked = false;
    }

    public void act(List<Enemy> enemies)
    {
        super.act();
        MouseInfo info = Greenfoot.getMouseInfo();
        long currentTime = System.currentTimeMillis();

        // Drag and preview
        if (!isLocked && isDragging && info != null) {
            setLocation(info.getX() - offsetX, info.getY() - offsetY);
            showRangePreview();
        } else {
            removeRangePreview();
        }

        // Handle mouse interactions
        if (!isLocked && info != null) {
            if (Greenfoot.mousePressed(this)) {
                mouseClicked();
            } else if (Greenfoot.mouseDragEnded(this)) {
                mouseReleased();
            }
        }

        // Only attack when placed and not dragging
        if (!isDragging && isLocked) {
            if (target == null || !isInRange(target)) {
                target = findTarget(enemies);
            }

            if (target != null && currentTime - lastAttackTime >= 1000 / attackSpeed) {
                attack(target);
                lastAttackTime = currentTime;
            }
        }
    }

    // Determines if the enemy is within range
    protected boolean isInRange(Enemy enemy) {
        double dx = enemy.getX() - getX();
        double dy = enemy.getY() - getY();
        return (dx * dx + dy * dy) <= (range * range);
    }

    // Finds a new target
    protected Enemy findTarget(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (isInRange(enemy)) {
                return enemy;
            }
        }
        return null;
    }

    private void showRangePreview() {
        if (rangeCircle == null) {
            rangeCircle = new RangeCircle(range);
            getWorld().addObject(rangeCircle, getX(), getY());
        } else {
            rangeCircle.setLocation(getX(), getY());
        }
    }

    private void removeRangePreview() {
        if (rangeCircle != null) {
            getWorld().removeObject(rangeCircle);
            rangeCircle = null;
        }
    }

    public void mouseClicked() {
        if (!isLocked) {
            MouseInfo info = Greenfoot.getMouseInfo();
            if (info != null) {
                isDragging = true;
                offsetX = info.getX() - getX();
                offsetY = info.getY() - getY();
            }
        }
    }

    public void mouseReleased () {
        if (!isLocked) {
            List<WeaponSlot> slots = getWorld().getObjects(WeaponSlot.class);

            boolean validPlacement = false;
            WeaponSlot targetSlot = null;

            for (WeaponSlot slot : slots) {
                if (slot.getBoundingRectangle().contains(getX(), getY())) {
                    validPlacement = true;
                    targetSlot = slot;
                    break;
                }
            }

            if (validPlacement && targetSlot != null) {
                // Snap to the slot
                setLocation(targetSlot.getX(), targetSlot.getY());
                isDragging = false;
                isLocked = true;
                offsetX = 0;
                offsetY = 0;
                
                // Spawn a new copy at the original shop location
                spawnNewCopy();
            } else {
                // If not valid, return to origin
                setLocation(100, 100); // fallback origin
                isDragging = false;
            }
        }
    }

    public void unlock() {
        isLocked = false;
    }

    public void startDragging() {
        isDragging = true;
        MouseInfo info = Greenfoot.getMouseInfo();
        if (info != null) {
            offsetX = info.getX() - getX();
            offsetY = info.getY() - getY();
        }
    }
    
    // TODO: Move tower spawning into a ShopButton class when ready
    private void spawnNewCopy() {
        Weapons copy = createCopy();
        getWorld().addObject(copy, 100, 100); // Replace 100,100 with your shop spot
        }
}

