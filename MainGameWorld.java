import greenfoot.*;
import java.awt.Point;
import java.util.*;

/**
 * The main game world where the selected cookie appears in small size.
 * 
 * @author (Yilin Ma)
 * @version (2025.06.12)
 */
public class MainGameWorld extends World {
    public static ArrayList<Point> enemyPath = new ArrayList<>();
    private int round = 1;
    private List<String> enemiesThisRound;
    private int enemiesSpawned = 0;
    private int spawnDelay = 100;
    private int spawnCounter = 0;
    private StarManager starManager;
    private CookieHPManager cookieHPManager;
    private static int attackBoost = 0;
    private static double fireRateMultiplier = 1.0;
    
    private int[][] weaponSlotPositions = {
        {194, 203}, {675, 203}, {774, 203}, {865, 203},{961, 203},{1055, 203},
        {384, 300}, {484, 300}, {675, 300}, {1055, 300},
        {194, 400}, {288, 400}, {675, 400}, {865, 400},{1055, 400},
        {288, 497}, {484, 497}, {575, 497}, {675, 497},{865, 497},{1055, 497},
        {288, 590},{865, 590},
    };
    
    private List<WeaponSlot> weaponSlots = new ArrayList<>();
    
    public MainGameWorld(String smallPrefix, String cookieType) {
        super(1280, 800, 1);
        setBackground("map1.png");
        loadPath();
        setupEnemiesForRound();
        
        SelectableCookie player = new SelectableCookie(false, smallPrefix);
        addObject(player, 1145, 530);
        
        // 添加隐藏的底座
        for (int[] pos : weaponSlotPositions) {
            WeaponSlot slot = new WeaponSlot();
            slot.setTransparency(0); // 初始隐藏
            addObject(slot, pos[0], pos[1]);
            weaponSlots.add(slot);
        }
        weaponSelect();
        
        starManager = new StarManager();
        addObject(starManager, 313, 54);
        
        // 设置饼干效果
        int initialHP = 10;
        if (cookieType.equals("hazelnut")) initialHP = 15;
        cookieHPManager = new CookieHPManager(initialHP);
        addObject(cookieHPManager, 98, 54);
        
        if (cookieType.equals("blueberry")) {
            fireRateMultiplier = 0.75;
        } else {
            fireRateMultiplier = 1.0;
        }
        
        if (cookieType.equals("cocoa")) {
            attackBoost = 1;
        } else {
            attackBoost = 0;
        }
    }
    
    private void loadPath() {
        enemyPath.clear(); //清空之前的数据，避免重复添加
        int[][] PATH = {
            {95, 300},    // 起点
            {297, 300},   // →
            {297, 190},   // ↑
            {576, 190},   // →
            {576, 375},   // ↓
            {381, 375},   // ←
            {381, 575},   // ↓
            {771, 575},   // →
            {771, 285},   // ↑
            {964, 285},  // →
            {964, 575}, // ↓
            {1135, 575}, // →（终点）
        };
        for (int[] pos : PATH) {
            enemyPath.add(new Point(pos[0], pos[1]));
        }
    }

    public void act() {
        spawnCounter++;
        if (spawnCounter >= spawnDelay && enemiesSpawned < enemiesThisRound.size()) {
            spawnEnemy(enemiesThisRound.get(enemiesSpawned));
            enemiesSpawned++;
            spawnCounter = 0;
        }

        if (enemiesSpawned >= enemiesThisRound.size() && getObjects(Enemy.class).isEmpty()) {
            nextRound();
        }
    }

    private void spawnEnemy(String type) {
        Enemy enemy;
        if (type.equals("small")) {
            enemy = new SmallCakeMonster();
        } else if (type.equals("elite")) {
            enemy = new EliteCakeMonster();
        } else if (type.equals("boss")) {
            enemy = new BossMonster();
        } else {
            return;
        }
        Point start = enemyPath.get(0);
        addObject(enemy, start.x, start.y);
    }

    private void setupEnemiesForRound() {
        enemiesThisRound = new ArrayList<>();
        switch (round) {
            case 1: for (int i = 0; i < 10; i++) enemiesThisRound.add("small"); break;
            case 2: for (int i = 0; i < 7; i++) enemiesThisRound.add("small");
                    for (int i = 0; i < 3; i++) enemiesThisRound.add("elite"); break;
            case 3: for (int i = 0; i < 4; i++) enemiesThisRound.add("small");
                    for (int i = 0; i < 5; i++) enemiesThisRound.add("elite");
                    enemiesThisRound.add("boss"); break;
            case 4: for (int i = 0; i < 6; i++) enemiesThisRound.add("elite"); break;
            case 5: for (int i = 0; i < 3; i++) enemiesThisRound.add("boss"); break;
        }
        Collections.shuffle(enemiesThisRound);
        enemiesSpawned = 0;
        spawnDelay = Math.max(10, spawnDelay - 5);
    }

    private void nextRound() {
        if (round < 5) {
            round++;
            setupEnemiesForRound();
        } else {
            System.out.println("All rounds completed! You win!");
            // 这里可以写胜利逻辑或结束游戏
        }
    }
        
    public void weaponSelect()
    {
        SugarPot sugarPot = new SugarPot(559, 50); //Spawn CandyFloss
        addObject(sugarPot, 559, 50);
        
        CandyFloss candyFloss = new CandyFloss(640, 50); //Spawn CandyFloss
        addObject(candyFloss, 640, 50);
        
        CaramelLaser caramelLaser = new CaramelLaser(721, 50); //Spawn CaramelLaser
        addObject(caramelLaser, 721, 50);
        
        Bubblegum bubblegum = new Bubblegum(801, 50); //Spawn Bubblegum
        addObject(bubblegum, 801, 50);
    }
    
    // 控制底座显示
    public void setWeaponSlotVisibility(boolean visible) {
        for (WeaponSlot slot : weaponSlots) {
            slot.setTransparency(visible ? 100 : 0);
        }
    }
    
    // 提供给 Weapons 检查是否放置在槽位中
    public List<WeaponSlot> getWeaponSlots() {
        return weaponSlots;
    }
    
    // 静态方法让 Weapons 调用
    public static int getAttackBoost() {
        return attackBoost;
    }

    public static double getFireRateMultiplier() {
        return fireRateMultiplier;
    }
    
    public CookieHPManager getCookieHPManager() {
        return cookieHPManager;
    }
}