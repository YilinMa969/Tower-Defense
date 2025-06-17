import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;

/**
 * Character selection screen for choosing a Cookie.
 * Shows large animated cookie and allows switching.
 *
 * @author (Yilin Ma)
 * @version (2025.06.04)
 */

public class CharacterSelectWorld extends World {
    private SelectableCookie cookieActor;
    private PictureActor nameActor;
    private PictureActor storyActor;
    private PictureActor skillActor;
    private ArrayList<CookieData> cookies;
    private int currentIndex = 0;
    
    public CharacterSelectWorld() {
        super(1280, 800, 1);
        setBackground(new GreenfootImage("character_select_bg.png"));

        cookies = new ArrayList<>();
        cookies.add(new CookieData("hazelnut_big_", "hazelnut_name.png", "hazelnut_story.png", "hazelnut_skill.png", true));
        cookies.add(new CookieData("blueberry_big_", "blueberry_name.png", "blueberry_story.png", "blueberry_skill.png",true));
        cookies.add(new CookieData("cocoa_big_", "cocoa_name.png", "cocoa_story.png",  "cocoa_skill.png",true));

        cookieActor = createCookieByPrefix(cookies.get(currentIndex).animationPrefix, true);
        addObject(cookieActor, 1000, 360);
        
        nameActor = new PictureActor(cookies.get(currentIndex).nameImage);
        addObject(nameActor, 995, 650);

        storyActor = new PictureActor(cookies.get(currentIndex).storyImage);
        addObject(storyActor, 410, 205);
        
        skillActor = new PictureActor(cookies.get(currentIndex).skillImage);
        addObject(skillActor, 380, 730);

        addObject(new ArrowButton(-1), 820, 570);    // 左箭头
        addObject(new ArrowButton(1), 1170, 570);    // 右箭头


        addObject(new NextButton(), 1200, 750);
    }

    public void changeCookie(int direction) {
        currentIndex += direction;
        if (currentIndex < 0) currentIndex = cookies.size() - 1;
        if (currentIndex >= cookies.size()) currentIndex = 0;
    
        CookieData data = cookies.get(currentIndex);
    
        // Remove the old cookie actor from the world if exists
        if (cookieActor != null) {
            removeObject(cookieActor);
        }
    
        // Create new cookie actor and add to world
        cookieActor = createCookieByPrefix(data.animationPrefix, data.isBig);
        addObject(cookieActor, 1000, 360);
    
        // Update name and story images
        nameActor.setPicture(data.nameImage);
        storyActor.setPicture(data.storyImage);
        skillActor.setPicture(data.skillImage);
    }
    
    public String getSelectedSmallPrefix() {
         return cookies.get(currentIndex).animationPrefix.replace("_big_", "_small_");
    }
    
    public String getSelectedCookieName() {
        if (currentIndex == 0) return "hazelnut_small_";
        if (currentIndex == 1) return "blueberry_small_";
        return "cocoa_small_";
    }
    
    public int getInitialHP() {
        return 10; // default HP
    }
    
    private void spawnCookie(int index) {
        if (cookieActor != null) {
            removeObject(cookieActor);
        }
    
        switch(index) {
            case 0:
                cookieActor = new HazelnutCookie("hazelnut_big_");
                break;
            case 1:
                cookieActor = new BlueberryCookie("blueberry_big_");
                break;
            case 2:
                cookieActor = new CocoaCookie("cocoa_big_");
                break;
        }
    
        addObject(cookieActor, 1000, 360);
    }
    
    private SelectableCookie createCookieByPrefix(String prefix, boolean isBig) {
        String size = isBig ? "big" : "small";
        if (prefix.startsWith("hazelnut")) {
            return new HazelnutCookie("hazelnut_" + size + "_");
        } else if (prefix.startsWith("blueberry")) {
            return new BlueberryCookie("blueberry_" + size + "_");
        } else if (prefix.startsWith("cocoa")) {
            return new CocoaCookie("cocoa_" + size + "_");
        } else {
            // fallback:
            return new HazelnutCookie("hazelnut_" + size + "_");
        }
    }
    
}


