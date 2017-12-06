import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class RoadCreator extends JPanel implements MouseListener,KeyListener{
    Road r;
    java.util.Stack<Waypoint> stack=new java.util.Stack<Waypoint>();
    public static void main(String[] args){
        JFrame frame=new JFrame("進撃の巨人");
        frame.setLayout(new GridLayout(1,1));
        RoadCreator rc=new RoadCreator();
        frame.add(rc);
        frame.addKeyListener(rc);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }
    public RoadCreator(){
        setPreferredSize(new Dimension(1350,700));
        addMouseListener(this);
        grabFocus();
    }
    public void paint(Graphics g){
        super.paint(g);
        if(r!=null){
            r.paint(g);
            Waypoint temp=stack.peek();
            g.setColor(Color.red);
            g.drawOval((int)(temp.x-4.5),(int)(temp.y-4.5),10,10);
        }
    }
    public void keyPressed(KeyEvent e){
        System.out.println("button pressed");
        if(e.getKeyCode()==KeyEvent.VK_S){// for creating a road
            r.save("road1.ser");
        }
        else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
            stack.pop();
        }
        else if(e.getKeyCode()==KeyEvent.VK_E){
            stack.peek().designateEnd();
        }
        repaint();
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    public void mouseClicked(MouseEvent e){
        Waypoint w=new Waypoint(e.getX(),e.getY());
        System.out.println(w.x+" "+w.y);
        if(r==null){
            stack.push(w);
            r=new Road(w);
        }
        else{
            stack.peek().attach(w);
            stack.push(w);
        }
        repaint();
    }
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
}
