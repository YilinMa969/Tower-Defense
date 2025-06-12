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
    protected int range;               // 攻击范围
    protected int damage;              // 伤害
    protected long attackSpeed;        // 攻击速度（每秒攻击次数）
    protected int cost;                // 造价
    protected long cooldown;           // 冷却时间（毫秒）

    protected Enemy target;            // 当前攻击目标

    private boolean isDragging = false;
    private int offsetX, offsetY;      // 鼠标点击偏移，用于拖动
    private long lastAttackTime = 0;
    private RangeCircle rangeCircle = null;
    private boolean isLocked = false;  // 是否已固定放置

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

        // 拖动预览
        if (!isLocked && isDragging && info != null) {
            setLocation(info.getX() - offsetX, info.getY() - offsetY);
            showRangePreview();
            showWeaponSlots();
        } else {
            removeRangePreview();
            hideWeaponSlots();
        }

        // 鼠标交互
        if (!isLocked && info != null) {
            if (Greenfoot.mousePressed(this)) {
                mouseClicked();
            } else if (Greenfoot.mouseDragEnded(this)) {
                mouseReleased();
            }
        }

        // 固定放置后自动攻击
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

    // 判断敌人是否在范围内
    protected boolean isInRange(Enemy enemy) {
        double dx = enemy.getX() - getX();
        double dy = enemy.getY() - getY();
        return (dx * dx + dy * dy) <= (range * range);
    }

    // 寻找第一个范围内的目标
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

            // 找到鼠标释放时所在的武器槽（如果有）
            for (WeaponSlot slot : slots) {
                if (slot.getBoundingRectangle().contains(getX(), getY())) {
                    if (!slot.isOccupied()) { // 只有空闲槽可放置
                        validPlacement = true;
                        targetSlot = slot;
                        break;
                    }
                }
            }

            if (validPlacement && targetSlot != null) {
                // 把武器“吸附”到底座中心
                setLocation(targetSlot.getX(), targetSlot.getY());

                // 标记武器固定，且标记底座已被占用
                isDragging = false;
                isLocked = true;
                offsetX = 0;
                offsetY = 0;

                targetSlot.setOccupied(true); // 让底座变不透明

                // 生成一个新的备货武器回商店位置（假设100,100为商店）
                spawnNewCopy();
            } else {
                // 放置无效，返回商店原位
                setLocation(100, 100);
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

    private void spawnNewCopy() {
        Weapons copy = createCopy();
        getWorld().addObject(copy, 100, 100);
    }

    private void showWeaponSlots() {
        List<WeaponSlot> slots = getWorld().getObjects(WeaponSlot.class);
        for (WeaponSlot slot : slots) {
            if (!slot.isOccupied()) {
                slot.showTransparent();
            }
        }
    }

    private void hideWeaponSlots() {
        List<WeaponSlot> slots = getWorld().getObjects(WeaponSlot.class);
        for (WeaponSlot slot : slots) {
            if (!slot.isOccupied()) {
                slot.hide();
            }
        }
    }
}

