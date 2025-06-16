import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A bullet shot from the SugarPot tower. Deals 1 damage to any enemy it hits.
 * 
 * @author Yilin Ma
 * @version 2025.06.16
 */
public class SugarPotBullet extends Actor {
    private int speed = 6;

    public SugarPotBullet() {
        setImage("SugarPotBullets.png"); // 你已经准备好的子弹图片
    }

    public void act() {
        move(speed);

        // 检测是否击中敌人
        Enemy enemy = (Enemy)getOneIntersectingObject(Enemy.class);
        if (enemy != null) {
            enemy.takeDamage(1);  // 敌人扣血
            getWorld().removeObject(this);  // 子弹消失
            return;
        }

        // 超出边界也删除
        if (isAtEdge()) {
            getWorld().removeObject(this);
        }
    }
}

