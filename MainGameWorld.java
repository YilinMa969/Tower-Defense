import greenfoot.*;
import java.awt.Point;
import java.util.ArrayList;

/**
 * The main game world where the selected cookie appears in small size.
 * 
 * @author (Yilin Ma)
 * @version (2025.06.04)
 */
class MainGameWorld extends World {
    public static ArrayList<Point> enemyPath = new ArrayList<>();
    
    public MainGameWorld(String smallPrefix) {
        super(1280, 800, 1);
        setBackground("map1.png");
        loadPath(); // 添加路径
        addObject(new Enemy(), 75, 360); // 从起点生成敌人
        
        SelectableCookie player = new SelectableCookie(false, smallPrefix);
        addObject(player, 1145, 570);
    }
    
    private void loadPath() {
        int[][] PATH = {
            {75, 360}, {155, 360}, {235, 360}, {235, 270},
            {390, 270}, {390, 360}, {310, 360}, {310, 520},
            {470, 520}, {470, 440}, {550, 440}, {550, 270},
            {710, 270}, {710, 360}, {630, 360}, {630, 520},
            {790, 520}, {870, 520}
        };
        for (int[] pos : PATH) {
            enemyPath.add(new Point(pos[0], pos[1]));
        }
    }
    
}