import greenfoot.*;

public class LaserBullet extends Actor {
    private static GreenfootImage sharedImage;

    private Enemy target;
    private int damage;
    private CaramelLaser owner;
    private int speed = 10;

    public LaserBullet(Enemy target, int damage, CaramelLaser owner) {
        this.target = target;
        this.damage = damage;
        this.owner = owner;

        if (sharedImage == null) {
            GreenfootImage img = new GreenfootImage(20, 6);
            img.setColor(new Color(255, 255, 0));
            img.fillRect(0, 0, 20, 6);
            sharedImage = img;
        }

        setImage(new GreenfootImage(sharedImage));
    }

    @Override
    public void act() {
        if (target == null || target.getWorld() == null) {
            getWorld().removeObject(this);
            return;
        }

        int dx = target.getX() - getX();
        int dy = target.getY() - getY();
        double distance = Math.hypot(dx, dy);

        if (distance <= speed) {
            target.takeDamage(damage);
            getWorld().removeObject(this);
        } else {
            int newX = (int)(getX() + dx / distance * speed);
            int newY = (int)(getY() + dy / distance * speed);
            setLocation(newX, newY);

            setRotation((int) Math.toDegrees(Math.atan2(dy, dx)));
        }
    }
}