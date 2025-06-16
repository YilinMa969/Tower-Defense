import greenfoot.*;  
import java.util.List;
import java.awt.Rectangle;

/**
 * Weapons can be placed, attack enemies, and be modified by cookie effects.
 * Handles drag-and-drop, cooldowns, damage boost, and fire rate changes.
 * 
 * @author Briannie Law & Yilin Ma
 * @version 2025-06-16
 */
public abstract class Weapons extends Actor {
    protected int range;
    protected int baseDamage;
    protected int cost;
    protected long attackCooldownMillis;
    protected long lastAttackTime = 0;
    protected boolean projectileInFlight = false;
    protected int damageBoost = 0;
    protected double fireRateMultiplier = 1.0;

    private boolean isDragging = false;
    private int offsetX, offsetY;
    private RangeCircle rangeCircle = null;
    private boolean isLocked = false;

    private int spawnX, spawnY;
    private PictureActor priceTag = null;
    private boolean priceShown = false;

    protected Enemy target = null; // ✅ 修正：添加 target 成员变量

    public Weapons(int spawnX, int spawnY, int range, int baseDamage, long baseCooldownMillis, int cost) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.range = range;
        this.baseDamage = baseDamage;
        this.attackCooldownMillis = baseCooldownMillis;
        this.cost = cost;
        
        if (PassiveSkillManager.getInstance().isBlueberryCookieActive()) {
            fireRateMultiplier = 0.7; // 30% faster
        } else {
            fireRateMultiplier = 1.0;
        }
    
        long effectiveCooldown = (long)(attackCooldownMillis * fireRateMultiplier);
    }

    public void act() {
        if (!isLocked) {
            handleDragAndDrop();
        } else {
            if (target == null || !isInRange(target)) {
                List<Enemy> enemies = getWorld().getObjects(Enemy.class);
                target = findTarget(enemies);
            }
            if (target != null && canAttack()) {
                attack(target);
                lastAttackTime = System.currentTimeMillis();
            }
        }
    }

    protected boolean canAttack() {
        long elapsed = System.currentTimeMillis() - lastAttackTime;
        long effectiveCooldown = (long)(attackCooldownMillis * fireRateMultiplier);
        return elapsed >= effectiveCooldown && !projectileInFlight;
    }

    protected Enemy findTarget(List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (isInRange(enemy)) return enemy;
        }
        return null;
    }

    protected boolean isInRange(Enemy enemy) {
        if (enemy == null || enemy.getWorld() == null) return false;
        double dx = enemy.getX() - getX();
        double dy = enemy.getY() - getY();
        return dx*dx + dy*dy <= range * range;
    }

    protected void handleDragAndDrop() {
        MouseInfo info = Greenfoot.getMouseInfo();
        if (info != null && containsPoint(info.getX(), info.getY())) {
            showPriceTag();
        } else {
            hidePriceTag();
        }

        if (isDragging && info != null) {
            StarManager starManager = getWorld().getObjects(StarManager.class).get(0);
            if (starManager.getStars() >= cost) {
                setLocation(info.getX() - offsetX, info.getY() - offsetY);
                showRangePreview();
                showWeaponSlots();
            } else {
                setLocation(spawnX, spawnY);
                isDragging = false;
                removeRangePreview();
                hideWeaponSlots();
                hidePriceTag();
            }
        }

        if (info != null) {
            if (Greenfoot.mousePressed(this)) {
                mouseClicked();
            } else if (isDragging && Greenfoot.mouseDragEnded(this)) {
                mouseReleased();
            }
        }
    }

    private boolean containsPoint(int x, int y) {
        GreenfootImage img = getImage();
        int left = getX() - img.getWidth()/2;
        int top = getY() - img.getHeight()/2;
        return x >= left && x <= left + img.getWidth() && y >= top && y <= top + img.getHeight();
    }

    public void mouseClicked() {
        if (!isLocked) {
            MouseInfo info = Greenfoot.getMouseInfo();
            if (info != null) {
                isDragging = true;
                offsetX = info.getX() - getX();
                offsetY = info.getY() - getY();
                showWeaponSlots();
            }
        }
    }

    public void mouseReleased() {
        if (isLocked) return;
        returnToSpawnIfNotPlacedProperly();
        isDragging = false;
        removeRangePreview();
        hideWeaponSlots();
        hidePriceTag();
    }

    private void returnToSpawnIfNotPlacedProperly() {
        List<WeaponSlot> slots = getWorld().getObjects(WeaponSlot.class);
        StarManager starManager = getWorld().getObjects(StarManager.class).get(0);
        WeaponSlot targetSlot = null;

        for (WeaponSlot slot : slots) {
            if (slot.getBoundingRectangle().contains(getX(), getY()) && !slot.isOccupied()) {
                targetSlot = slot;
                break;
            }
        }

        if (targetSlot != null && starManager.getStars() >= cost) {
            if (starManager.spendStars(cost)) {
                setLocation(targetSlot.getX(), targetSlot.getY());
                isLocked = true;
                targetSlot.setOccupied(true);
                spawnNewCopy();
            } else {
                setLocation(spawnX, spawnY);
            }
        } else {
            setLocation(spawnX, spawnY);
        }
    }

    private void spawnNewCopy() {
        Weapons copy = createCopy(spawnX, spawnY);
        getWorld().addObject(copy, spawnX, spawnY);
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

    private void showWeaponSlots() {
        for (WeaponSlot slot : getWorld().getObjects(WeaponSlot.class)) {
            if (!slot.isOccupied()) slot.showTransparent();
        }
    }

    private void hideWeaponSlots() {
        for (WeaponSlot slot : getWorld().getObjects(WeaponSlot.class)) {
            if (!slot.isOccupied()) slot.hide();
        }
    }

    private void showPriceTag() {
        if (priceTag != null) return;
        World world = getWorld();
        StarManager starManager = world.getObjects(StarManager.class).get(0);
        boolean enough = starManager.getStars() >= cost;
        String baseName = getPriceImageName();
        String imgName = enough ? baseName + "Prices.png" : baseName + "Pricesnot_enough.png";
        priceTag = new PictureActor(imgName);
        world.addObject(priceTag, getX(), getY() - getImage().getHeight()/2 + 110);
        priceShown = true;
    }

    private void hidePriceTag() {
        if (priceTag != null) {
            getWorld().removeObject(priceTag);
            priceTag = null;
            priceShown = false;
        }
    }

    public void setAttackBoost(int boost) {
        this.damageBoost = boost;
    }

    public void setFireRateMultiplier(double multiplier) {
        this.fireRateMultiplier = multiplier;
    }

    public int getTotalDamage() {
        return baseDamage + damageBoost;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public int getCost() {
        return cost;
    }

    // 子类必须实现的
    public abstract void attack(Enemy enemy);
    public abstract Weapons createCopy(int spawnX, int spawnY);
    protected abstract String getPriceImageName();
}
