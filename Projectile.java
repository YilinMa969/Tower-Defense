import greenfoot.*;

/**
 * A projectile that targets an enemy and deals damage on hit.
 * One projectile per weapon at a time (controlled via projectileInFlight).
 * 
 * @author Bri
 * @version 6/13/2025
 */
public class Projectile extends Actor {
    private int speed = 5;
    private int damage;
    private Enemy target;
    private Weapons owner; // 保存发射该子弹的武器实例

    public Projectile(Enemy target, int baseDamage, Weapons owner) {
        this.target = target;
        this.owner = owner;
        this.damage = baseDamage + owner.damageBoost; // 伤害加上Cookie加成
        setImage("SugarPotBullets.png");
    }

    public void act() {
        if (target == null || target.getWorld() == null) {
            if (owner != null) {
                owner.projectileInFlight = false; // 子弹无目标，允许再次攻击
            }
            getWorld().removeObject(this);
            return;
        }

        turnTowards(target.getX(), target.getY());
        move(speed);

        if (getDistanceTo(target) < 10) {
            target.takeDamage(damage);
            if (owner != null) {
                owner.projectileInFlight = false;
            }
            getWorld().removeObject(this);
        }
    }

    private double getDistanceTo(Actor other) {
        return Math.hypot(getX() - other.getX(), getY() - other.getY());
    }
}

