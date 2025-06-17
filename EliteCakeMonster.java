import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class EliteCakeMonster here.
 * 
 * @author (Yilin Ma) 
 * @version (2025.6.10)
 */
public class EliteCakeMonster extends Enemy
{
    public EliteCakeMonster() {
        super("EliteCakeMonsters.png","EliteCakeMonsters_slow.png");
        this.health = 10;
    }
    @Override
    public void takeDamage(int amount) {
        super.takeDamage(amount); // Reduces health
        if (getHealth() <= 0) {
            Greenfoot.playSound("elitecake_death.wav");
        }
    }
}
