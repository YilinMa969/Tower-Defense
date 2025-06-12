import greenfoot.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The main game world where the selected cookie appears in small size.
 * 
 * @author (Yilin Ma)
 * @version (2025.06.09)
 */
class MainGameWorld extends World {
    public static ArrayList<Point> enemyPath = new ArrayList<>();
    public static ArrayList<Point> enemyPathHigh = new ArrayList<>();  // 抬高版路径
    private int round = 1;                  // 当前轮数
    private List<String> enemiesThisRound;  // 本轮待生成敌人类型队列
    private int enemiesSpawned = 0;         // 本轮已生成敌人数
    private int spawnDelay = 60;             // 生成间隔帧数
    private int spawnCounter = 0;            // 生成计时器
    
    private int[][] weaponSlotPositions = {
        {194, 203}, {675, 203}, {774, 203}, {865, 203},{961, 203},{1055, 203},
        {384, 300}, {484, 300}, {675, 300}, {1055, 300},
        {194, 400}, {288, 400}, {675, 400}, {865, 400},{1055, 400},
        {288, 497}, {484, 497}, {575, 497}, {675, 497},{865, 497},{1055, 497},
        {288, 590},{865, 590},
    };
    
    private List<WeaponSlot> weaponSlots = new ArrayList<>();
    
    public MainGameWorld(String smallPrefix) {
        super(1280, 800, 1);
        setBackground("map1.png");
        loadPath();
        setupEnemiesForRound();
        
        SelectableCookie player = new SelectableCookie(false, smallPrefix);
        addObject(player, 1145, 570);
        
        // 添加隐藏的底座
        for (int[] pos : weaponSlotPositions) {
            WeaponSlot slot = new WeaponSlot();
            slot.setTransparency(0); // 初始隐藏
            addObject(slot, pos[0], pos[1]);
            weaponSlots.add(slot);
        }
        weaponSelect();
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
            String type = enemiesThisRound.get(enemiesSpawned);
            spawnEnemy(type);
            enemiesSpawned++;
            spawnCounter = 0;
        }

        // 如果本轮所有敌人都生成完且屏幕没有敌人了，进入下一轮
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
            case 1:
                for (int i = 0; i < 10; i++) enemiesThisRound.add("small");
                break;
            case 2:
                for (int i = 0; i < 7; i++) enemiesThisRound.add("small");
                for (int i = 0; i < 3; i++) enemiesThisRound.add("elite");
                break;
            case 3:
                for (int i = 0; i < 4; i++) enemiesThisRound.add("small");
                for (int i = 0; i < 5; i++) enemiesThisRound.add("elite");
                enemiesThisRound.add("boss");
                break;
            case 4:
                for (int i = 0; i < 6; i++) enemiesThisRound.add("elite");
                break;
            case 5:
                for (int i = 0; i < 3; i++) enemiesThisRound.add("boss");
                break;
        }
        java.util.Collections.shuffle(enemiesThisRound);
        enemiesSpawned = 0;
        spawnDelay = Math.max(10, spawnDelay - 5); // 逐轮加快生成速度
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
        
    
    public void weaponSelect()
    {
        SugarPot sugarPot = new SugarPot(100, 100); //Spawn Spot
        addObject(sugarPot, 100, 100);
    }
}