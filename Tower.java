
public class Tower extends GameObject{// gameobject with range and firepower
    final int range=150,power=1,loadTime=100;
    final double accuracy=0.5;
    int counter=loadTime;
    public Tower(double x,double y,javax.swing.ImageIcon image){
        super.x=x;
        super.y=y;
        super.width=image.getIconWidth();
        super.height=image.getIconHeight();
        super.image=image;
    }
    public boolean ready(){
        if(counter==loadTime){
            return true;
        }
        else{
            counter++;
            return false;
        }
    }
    public boolean shoot(){
        counter=0;
        return Math.random()<accuracy;
    }
    public double distance(double x,double y){// distance to x and y
        return Math.sqrt(Math.pow(super.x-x,2)+Math.pow(super.y-y,2));
    }
}
