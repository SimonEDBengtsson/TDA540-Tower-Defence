import java.util.*;
public class Monster extends GameObject{// gameobject with health, speed and a pointer to a road
    private final double speed=.6;// not velocity
    private final double maxHealth=10;
    private double health=maxHealth;
    private Waypoint target;
    private int[] path;
    private int crossingNum=0;
    private boolean atEnd=false;
    public Monster(Waypoint start,javax.swing.ImageIcon image){
        this.target=start;
        super.x=start.x;
        super.y=start.y;
        super.width=image.getIconWidth();
        super.height=image.getIconHeight();
        super.image=image;
        path=djikstra();
    }
    public void move(){
        if(super.x==target.x && super.y==target.y){
            if(target.endPoint){// at end of road, game over
                atEnd=true;
                return;
            }
            target=target.next[path[crossingNum]];
            crossingNum++;
        }
        if(Math.sqrt(Math.pow(super.x-target.x,2)+Math.pow(super.y-target.y,2))<speed){// deliberatly skips extra, when close to point, as curves are slower than straights
            super.x=target.x;
            super.y=target.y;
            return;
        }
        if(target.x==super.x && target.y>super.y){// vertical up
            super.y+=speed;
        }
        else if(target.x==super.x){// vertical down
            super.y-=speed;
        }
        else{
            double tan=(target.y-super.y)/(target.x-super.x);
            double dx=speed/Math.sqrt(tan*tan+1);// always positive
            if(target.x>super.x){// x should increase
                super.x+=dx;
                super.y+=dx*tan;
            }
            else{// x should decrease
                super.x-=dx;
                super.y-=dx*tan;
            }
        }
    }
    public double getDamaged(){
        return health/maxHealth>0?health/maxHealth:0;
    }
    public boolean shoot(double dmg){
        health-=dmg;
        if(health<=0){
            return true;
        }
        return false;
    }
    public boolean isAtEnd(){
        return atEnd;
    }
    public int[] djikstra(){
        Waypoint[] vuv=new Road(this.target).toArray();// both the visited and the unvisited as the number never changes
        HashMap<Waypoint,PathCost> map=new HashMap<Waypoint,PathCost>(vuv.length);
        map.put(vuv[0],new PathCost(0,null));
        for(int i=1;i<vuv.length;i++){
            map.put(vuv[i],new PathCost(Double.POSITIVE_INFINITY,null));
        }
        int ind=0;
        while(ind<vuv.length){// if loop terminates, start is not connected to any end
            // find lowest cost >= i and swap to i
            int mem=ind;
            for(int i=ind+1;i<vuv.length;i++){
                if(map.get(vuv[i]).cost<map.get(vuv[mem]).cost){
                    mem=i;
                }
            }
            Waypoint swap=vuv[ind];
            vuv[ind]=vuv[mem];
            vuv[mem]=swap;
            if(vuv[ind].endPoint){// if it finds an endpoint
                // backtrack to find the path
                Stack stack=new Stack();
                Waypoint w=vuv[ind];
                while(map.get(w).parent!=null){
                    Waypoint tempW=map.get(w).parent;
                    for(int i=0;i<tempW.next.length;i++){
                        if(tempW.next[i]==w){
                            stack.push(i);
                            break;
                        }
                    }
                    w=tempW;
                }
                int[] path=new int[stack.size()];
                for(int i=0;i<path.length;i++){
                    path[i]=(int)stack.pop();
                }
                return path;
            }
            // go through each connection, if lower assign new cost and current as parent
            if(vuv[ind].next!=null){// in case of dead end
                for(Waypoint w:vuv[ind].next){
                    double cost=vuv[ind].distance(w)+map.get(vuv[ind]).cost;
                    if(cost<map.get(w).cost){// if a shorter path is found write it in
                        map.get(w).cost=cost;
                        map.get(w).parent=vuv[ind];
                    }
                }
            }
            ind++;
        }
        return null;// there are no endpoints
    }
    private class Pair implements Map.Entry<Waypoint,PathCost>{
        Waypoint w;
        PathCost p;
        public Pair(Waypoint w,PathCost p){
            this.w=w;
            this.p=p;
        }
        public Waypoint getKey(){
            return w;
        }
        public PathCost getValue(){
            return p;
        }
        public PathCost setValue(PathCost p){
            this.p=p;
            return p;
        }
    }
    private class PathCost{
        double cost;
        Waypoint parent;
        private PathCost(double cost,Waypoint parent){
            this.cost=cost;
            this.parent=parent;
        }
    }
}
