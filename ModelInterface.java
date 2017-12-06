import java.util.ArrayList;
public interface ModelInterface{
    ArrayList<Tower> towers=new ArrayList<Tower>(0);
    ArrayList<Monster> monsters=new ArrayList<Monster>(1);
    Road road=Road.load("road.ser");
    final static String MONSTER_IMAGE_PATH="monster.jpg";
    final static String TOWER_IMAGE_PATH="cannon.jpg";
    javax.swing.ImageIcon monsterIcon=new javax.swing.ImageIcon(new javax.swing.ImageIcon(MONSTER_IMAGE_PATH).getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
    javax.swing.ImageIcon towerIcon=new javax.swing.ImageIcon(new javax.swing.ImageIcon(TOWER_IMAGE_PATH).getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
    public void tick();
    public void addMonster();
    public void addTower(double x,double y);
    public boolean gameIsOver();
}