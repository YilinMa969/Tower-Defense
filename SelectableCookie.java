import greenfoot.*;

/**
 * Represents a selectable cookie character with animated sprite frames.
 * Supports big or small size versions.
 * 
 * @author (Yilin Ma)
 * @version (2025.06.04)
 */
class SelectableCookie extends Actor {
    private GreenfootImage[] animationFrames;
    private int frameIndex = 0;
    private int animationCounter = 0;
    private int frameDelay = 6;

    private String animationPrefix;
    private boolean isBig;

    public SelectableCookie(boolean isBig, String animationPrefix) {
        this.isBig = isBig;
        this.animationPrefix = animationPrefix;
        loadFrames();
        setImage(animationFrames[0]);
    }

    public void act() {
        animate();
    }

    private void animate() {
        animationCounter++;
        if (animationCounter >= frameDelay) {
            frameIndex = (frameIndex + 1) % animationFrames.length;
            setImage(animationFrames[frameIndex]);
            animationCounter = 0;
        }
    }

    public void setAnimationPrefixAndSize(String prefix, boolean isBig) {
        this.animationPrefix = prefix;
        this.isBig = isBig;
        loadFrames();
        frameIndex = 0;
        animationCounter = 0;
        setImage(animationFrames[0]);
    }

    private void loadFrames() {
        int count = 9;
        animationFrames = new GreenfootImage[count];
        for (int i = 0; i < count; i++) {
            GreenfootImage img = new GreenfootImage(animationPrefix + i + ".png");
            if (!isBig) img.scale(img.getWidth() / 2, img.getHeight() / 2);
            animationFrames[i] = img;
        }
    }
}