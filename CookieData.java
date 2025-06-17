/**
 * Holds data for each cookie option.
 * 
 * @author (Yilin Ma)
 * @version (2025.06.04)
 */
class CookieData {
    public String animationPrefix;
    public String nameImage;
    public String storyImage;
    public String skillImage;
    public boolean isBig;

    public CookieData(String animationPrefix, String nameImage, String storyImage, String skillImage, boolean isBig) {
        this.animationPrefix = animationPrefix;
        this.nameImage = nameImage;
        this.storyImage = storyImage;
        this.skillImage = skillImage;
        this.isBig = isBig;
    }
}
