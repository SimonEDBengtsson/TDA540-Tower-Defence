import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
public class View extends JPanel implements MouseListener,KeyListener{// the io element
    ModelInterface model=new Model();
    final int width=1350,height=700;
    Timer gameTimer=new Timer(10,new java.awt.event.ActionListener(){
        public void actionPerformed(java.awt.event.ActionEvent e){
            model.tick();
        }
    }
    );
    Timer graphicsTimer=new Timer(10,new ActionListener(){// good practice to separate graphics and game logic, although unnecessry for a small game
        public void actionPerformed(ActionEvent e){
            repaint();
            requestFocus();
        }
    }
    );
    ImageIcon go=new ImageIcon(new ImageIcon("gameover.jpg").getImage().getScaledInstance(width,height,java.awt.Image.SCALE_SMOOTH));
    ImageIcon background=new ImageIcon(new ImageIcon("background.jpg").getImage().getScaledInstance(width,height,java.awt.Image.SCALE_SMOOTH));
    ImageIcon victory=new ImageIcon(new ImageIcon("victory.png").getImage().getScaledInstance(width,height,java.awt.Image.SCALE_SMOOTH));
    public View(){
        setDoubleBuffered(true);
        setPreferredSize(new java.awt.Dimension(width,height));
        setBorder(BorderFactory.createBevelBorder(0));
        addKeyListener(this);
        addMouseListener(this);
    }
    public View(double[][] towerPositions){
        this();
        if(towerPositions==null || towerPositions.length<1 || towerPositions[0].length<2){// coordinates do not make sense
            return;
        }
        try{
            for(int i=0;i<towerPositions.length;i++){
                model.addTower(towerPositions[i][0],towerPositions[i][1]);
            }
        }catch(NullPointerException x){}//in case of there being less y coordinates than x
    }
    public void run(){
        graphicsTimer.start();
        gameTimer.start();
    }
    public void stop(){
        gameTimer.stop();
        graphicsTimer.stop();
    }
    public void paintComponent(java.awt.Graphics g){
        super.paintComponent(g);
        if(model.gameIsOver()){// monster reached end
            go.paintIcon(this,g,0,0);
            stop();
            return;
        }
        else if(model.monsters.isEmpty()){// victory
            victory.paintIcon(this,g,0,0);
            stop();
            return;
        }
        background.paintIcon(this,g,0,0);
        model.road.paint(g);
        for(Tower t:model.towers){
            t.image.paintIcon(this,g,(int)(t.x+0.5)-t.width/2,(int)(t.y+0.5)-t.height/2);
            g.setColor(java.awt.Color.black);
            g.drawOval((int)(t.x+0.5)-t.range,(int)(t.y+0.5)-t.range,t.range*2,t.range*2);
        }
        for(Monster m:model.monsters){
            m.image.paintIcon(this,g,(int)(m.x+0.5)-m.width/2,(int)(m.y+0.5)-m.height/2);
            if(m.getDamaged()<.3){
                g.setColor(java.awt.Color.red);
            }
            else if(m.getDamaged()<.6){
                g.setColor(java.awt.Color.yellow);
            }
            else{
                g.setColor(java.awt.Color.green);
            }
            for(int i=2;i<6;i++){
                int x1=(int)(m.x+0.5)-m.width/2;
                int x2=x1+(int)(m.width*m.getDamaged()+0.5);
                int y=(int)(m.y+0.5)+m.width/2+i;
                g.drawLine(x1,y,x2,y);
            }
        }
    }
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_M){// add a monster
            model.addMonster();
        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    public void mouseClicked(MouseEvent e){
        model.addTower(e.getX(),e.getY());
        repaint();
    }
    public void mousePressed(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
}
