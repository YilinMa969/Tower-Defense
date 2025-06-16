import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CaramelLaser here.
 * 
 * @author Yilin Ma 
 * @version 2025.6.14
 */
public class CaramelLaser extends Weapons
{
    public CaramelLaser(int x, int y)
    {
        super(x, y, 100, 5, 100, 15);  // range, damage, attackSpeed, cost
        setImage("CaramelLaser.png");
    }

    @Override
    public void act()
    {
        super.act();
    }

    @Override
    public void attack(Enemy enemy)
    {
        //enemy.takeDamage(this.damage);
    }

    @Override
    public Weapons createCopy(int x, int y) {
        return new CaramelLaser(x, y);
    }
    
    protected String getPriceImageName() {
        return "CaramelLaser";
    }
}
