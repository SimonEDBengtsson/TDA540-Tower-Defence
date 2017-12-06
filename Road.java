import java.io.*;
public class Road implements Serializable{// tree graph
    protected final Waypoint start;
    public Road(double[] x,double[] y){
        start=new Waypoint(x[0],y[0]);
        Waypoint mem=start;
        for(int i=1;i<x.length;i++){
            mem.attach(new Waypoint(x[i],y[i]));
            mem=mem.next[0];
        }
    }
    public Road(Waypoint start){
        this.start=start;
    }
    public Waypoint get(double x,double y){// find the waypoint with given coordinates, else null
        Waypoint mem=start;
        while(mem.x!=x || mem.y!=y){
            System.out.println(1);
            if(mem.next==null || mem.next.length<1){// not found
                return null;
            }
            if(mem.next.length==1){// standard, only forward
                mem=mem.next[0];
            }
            else{// fork
                for(Waypoint w:mem.next){// recursive search throuh each sub-road
                    Road temp=new Road(w);
                    Waypoint tempW=temp.get(x,y);
                    if(tempW!=null){
                        return tempW;
                    }
                }
                return null;// no sub-road contains the specified point
            }
        }
        return mem;
    }
    public Waypoint[] toArray(){
        java.util.ArrayList<Waypoint> list=new java.util.ArrayList<Waypoint>(1);
        list.add(start);
        Waypoint mem=start;
        while(mem.next!=null && mem.next.length>0){
            if(mem.next.length==1){
                mem=mem.next[0];
                list.add(mem);
            }
            else{// recurse
                for(Waypoint w:mem.next){
                    list.addAll(java.util.Arrays.asList((Waypoint[])new Road(w).toArray()));
                }
                break;
            }
        }
        return list.toArray(new Waypoint[0]);
    }
    public void fork(Road road,double x,double y){// attach a road at the waypoint with coordinates x,y
        get(x,y).attach(road.start);
    }
    public void paint(java.awt.Graphics g){
        g.setColor(java.awt.Color.black);
        Waypoint cur=start;
        while(cur.next!=null){// keep going until the road ends
            for(Waypoint w:cur.next){// draw a line to each next
                g.drawLine(round(cur.x),round(cur.y),round(w.x),round(w.y));
            }
            if(cur.next.length==1){// 
                cur=cur.next[0];
            }
            else{
                for(Waypoint w:cur.next){// recursivly paint each sub-road in case of a fork
                    new Road(w).paint(g);
                }
                break;// terminate current iteration
            }
        }
    }
    public void save(String path){
        try{
            FileOutputStream fos=new FileOutputStream(path);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        }
        catch(IOException x){
            x.printStackTrace();
        }
    }
    public static Road load(String path){
        Road road;
        try{
            FileInputStream fis=new FileInputStream(path);
            ObjectInputStream ois=new ObjectInputStream(fis);
            road=(Road)ois.readObject();
            ois.close();
            fis.close();
            return road;
        }
        catch(Exception x){
            x.printStackTrace();
            return null;
        }
    }
    private static int round(double d){
        return (int)(d+0.5);
    }
}
