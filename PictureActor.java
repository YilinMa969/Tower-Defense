import greenfoot.*;

/**
 * Actor for showing static images like cookie name and story.
 * 
 * @author (Yilin Ma)
 * @version (2025.06.04)
 */
class PictureActor extends Actor {
    public PictureActor(String imageFile) {
        setPicture(imageFile);
    }

    public void setPicture(String imageFile) {
        setImage(new GreenfootImage(imageFile));
    }
}