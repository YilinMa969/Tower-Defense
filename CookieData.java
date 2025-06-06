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
    public boolean isBig;

    public CookieData(String animationPrefix, String nameImage, String storyImage, boolean isBig) {
        this.animationPrefix = animationPrefix;
        this.nameImage = nameImage;
        this.storyImage = storyImage;
        this.isBig = isBig;
    }
}
