import greenfoot.*;
import java.util.List;

public class CaramelLaser extends Weapons {
    private Enemy target;
    private int attackSpeed = 10;  // 攻击间隔帧数
    private int fireCooldown = 0;

    public CaramelLaser(int x, int y) {
        super(x, y, 150, 3, 10, 15); // range, damage, attackSpeed, cost
        setImage("CaramelLaser.png");
    }

    @Override
    public void act() {
        super.act();

        if (target == null || !isInRange(target) || target.getHealth() <= 0) {
            target = findTarget();
        }

        if (target != null) {
            int angle = (int)Math.toDegrees(Math.atan2(target.getY() - getY(), target.getX() - getX()));
            setRotation(angle + 90);

            if (fireCooldown <= 0) {
                fireCooldown = attackSpeed;
                LaserBullet bullet = new LaserBullet(target, baseDamage, this);
                getWorld().addObject(bullet, getX(), getY());
            } else {
                fireCooldown--;
            }
        }
    }

    private Enemy findTarget() {
        List<Enemy> enemies = getWorld().getObjects(Enemy.class);
        Enemy closest = null;
        double closestDist = Double.MAX_VALUE;
        for (Enemy e : enemies) {
            if (e.getHealth() > 0) {
                double dist = Math.hypot(e.getX() - getX(), e.getY() - getY());
                if (dist <= range && dist < closestDist) {
                    closest = e;
                    closestDist = dist;
                }
            }
        }
        return closest;
    }

    @Override
    public void attack(Enemy enemy) {
        if (projectileInFlight) return;

        int attackDamage = PassiveSkillManager.getInstance().isCocoaCookieActive() ? 8 : baseDamage;
        //System.out.println("CaramelLaser attack damage: " + attackDamage);
        
        Projectile p = new Projectile(enemy, attackDamage, this);
        //p.setImage("CaramelLaserBullet.png"); change image
        
        getWorld().addObject(new Projectile(enemy, attackDamage, this), getX(), getY());
        projectileInFlight = true;
    }

    @Override
    public Weapons createCopy(int x, int y) {
        return new CaramelLaser(x, y);
    }

    @Override
    protected String getPriceImageName() {
        return "CaramelLaser";
    }
}

