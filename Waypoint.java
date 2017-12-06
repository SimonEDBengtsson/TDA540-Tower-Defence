
public class Waypoint implements java.io.Serializable{// tree node with position
    protected Waypoint[] next=null;
    double x,y;
    boolean endPoint=false;
    public Waypoint(double x,double y){
        this.x=x;
        this.y=y;
    }
    public void attach(Waypoint next){
        if(this.next==null){
            this.next=new Waypoint[0];
        }
        Waypoint[] newNext=new Waypoint[this.next==null?1:this.next.length+1];// probably null pointer
        if(this.next!=null){
            for(int i=0;i<this.next.length;i++){
                newNext[i]=this.next[i];
            }
        }
        newNext[newNext.length-1]=next;
        this.next=newNext;
    }
    public double distance(Waypoint wp){
        return Math.sqrt(Math.pow(x-wp.x,2)+Math.pow(y-wp.y,2));
    }
    public void designateEnd(){
        endPoint=true;
    }
}
