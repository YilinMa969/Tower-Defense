import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CaramelLaser here.
 * 
 * @author Yilin Ma 
 * @version 2025.6.14
 */
public class CaramelLaser extends Weapons {
    public CaramelLaser(int x, int y) {
        super(x, y, 100, 5, 100, 15); // range, baseDamage, attackSpeed, cost
        setImage("CaramelLaser.png");
        
    }

    @Override
    public void act() {
        super.act();
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
