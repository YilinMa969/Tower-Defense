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
    private ArrayList<CookieData> cookies;
    private int currentIndex = 0;

    public CharacterSelectWorld() {
        super(1280, 800, 1);
        setBackground(new GreenfootImage("character_select_bg.png"));

        cookies = new ArrayList<>();
        cookies.add(new CookieData("hazelnut_big_", "hazelnut_name.png", "hazelnut_story.png", true));
        cookies.add(new CookieData("blueberry_big_", "blueberry_name.png", "blueberry_story.png", true));
        cookies.add(new CookieData("cocoa_big_", "cocoa_name.png", "cocoa_story.png", true));

        cookieActor = new SelectableCookie(true, cookies.get(currentIndex).animationPrefix);
        addObject(cookieActor, 1000, 360);

        nameActor = new PictureActor(cookies.get(currentIndex).nameImage);
        addObject(nameActor, 995, 650);

        storyActor = new PictureActor(cookies.get(currentIndex).storyImage);
        addObject(storyActor, 410, 205);

        addObject(new ArrowButton(-1), 820, 570);    // 左箭头
        addObject(new ArrowButton(1), 1170, 570);    // 右箭头


        addObject(new NextButton(), 1200, 750);
    }

    public void changeCookie(int direction) {
        currentIndex += direction;
        if (currentIndex < 0) currentIndex = cookies.size() - 1;
        if (currentIndex >= cookies.size()) currentIndex = 0;

        CookieData data = cookies.get(currentIndex);
        cookieActor.setAnimationPrefixAndSize(data.animationPrefix, data.isBig);
        nameActor.setPicture(data.nameImage);
        storyActor.setPicture(data.storyImage);
    }
    
    public String getSelectedSmallPrefix() {
        return cookies.get(currentIndex).animationPrefix.replace("_big_", "_small_");
    }
}


