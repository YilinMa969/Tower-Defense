import greenfoot.*;

/**
 * A weapon that throws sugar at enemies.
 * Deals low damage, moderate range, fast attack speed.
 * 
 * @author Briannie Law
 * @version 6/9/2025
 */
public class SugarPot extends Weapons {

    public SugarPot(int spawnX, int spawnY) {
        super(spawnX, spawnY, 130, 1, 500, 5); // range=130, baseDamage=1, cooldown=500ms, cost=5
        setImage("SugarPot.png");
    }

    @Override
    public void act() {
        super.act(); // 父类已经处理了目标寻找、攻击冷却与发射逻辑
    }

    @Override
    public void attack(Enemy enemy) {
        if (projectileInFlight) return;

        int damage = PassiveSkillManager.getInstance().isCocoaCookieActive() ? 3 : baseDamage;
        
        int angle = (int) Math.toDegrees(Math.atan2(enemy.getY() - getY(), enemy.getX() - getX()));
        setRotation(angle + 90);
    
        getWorld().addObject(new Projectile(enemy, damage, this), getX(), getY());
        projectileInFlight = true;
    }

    @Override
    public Weapons createCopy(int spawnX, int spawnY) {
        return new SugarPot(spawnX, spawnY);
    }

    @Override
    protected String getPriceImageName() {
        return "SugarPot";
    }
}