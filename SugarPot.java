import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class SugarPot here.
 * 
 * @author Briannie Law 
 * @version 6/9/2025
 */
public class SugarPot extends Weapons
{
    public SugarPot (int x, int y)
    {
        super(x, y, 100, 5, 100, 50); // range, damage, attackSpeed, cost
        setImage("SugarPot.png");
    }

    @Override
    public void act()
    {
        super.act(getWorld().getObjects(Enemy.class)); 
    }

    @Override
    public void attack(Enemy enemy)
    {
        //enemy.takeDamage(this.damage);
    }

    @Override
    public Weapons createCopy() {
        return new SugarPot(0, 0);
    }
}