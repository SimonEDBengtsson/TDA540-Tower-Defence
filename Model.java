import java.util.ArrayList;
public class Model implements ModelInterface{// the main class of the code which runs the game
    /*ArrayList<Tower> towers=new ArrayList<Tower>(0);
    ArrayList<Monster> monsters=new ArrayList<Monster>(1);
    Road road=Road.load("road.ser");
    final static String MONSTER_IMAGE_PATH="monster.jpg";
    final static String TOWER_IMAGE_PATH="cannon.jpg";
    javax.swing.ImageIcon monsterIcon=new javax.swing.ImageIcon(new javax.swing.ImageIcon(MONSTER_IMAGE_PATH).getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
    javax.swing.ImageIcon towerIcon=new javax.swing.ImageIcon(new javax.swing.ImageIcon(TOWER_IMAGE_PATH).getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));*/
    public Model(){
        monsters.add(new Monster(road.start,monsterIcon));
    }
    public void tick(){// false means game over, otherwise true
        for(Monster m:monsters){
            m.move();
        }
        for(Tower t:towers){
            if(!t.ready() || monsters.size()<1){// checks if the cannon is loaded, else skip
                continue;
            }
            int ind=0;
            double dist=t.distance(monsters.get(0).x,monsters.get(0).y);
            for(int i=0;i<monsters.size();i++){// find the closest monster to each tower
                double temp=t.distance(monsters.get(i).x,monsters.get(i).y);
                if(temp<dist){
                    ind=i;
                    dist=temp;
                }
            }
            if(dist<=t.range){// if close enough, damage
                if(t.shoot()){// check if the tower hits
                    if(monsters.get(ind).shoot(t.power)){
                        monsters.remove(ind);
                    }
                }
            }
        }
        for(int i=0;i<towers.size();i++){// can't be in previous loop due to concurrent modification
            for(Monster m:monsters){
                if(towers.get(i).distance(m.x,m.y)<40){// 40 is an arbitrary number that means the monster is close enough to destroy the tower
                    towers.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
    public void addTower(double x,double y){// view's paint might not be to happy with negative numbers, but doesn't bother the model
        towers.add(new Tower(x,y,towerIcon));
    }
    public synchronized void addMonster(){
        monsters.add(new Monster(road.start,monsterIcon));
    }
    public boolean gameIsOver(){
        for(Monster m:monsters){
            if(m.isAtEnd()){
                return true;
            }
        }
        return false;
    }
}
