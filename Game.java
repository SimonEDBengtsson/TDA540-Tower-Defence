import javax.swing.*;
import java.awt.*;
public class Game extends JFrame{// entry point
    public Game(double[][] towerPositions){
        super("AoT Tower Defence");
        setDefaultCloseOperation(3);
        this.setLayout(new FlowLayout());
        View view=new View(towerPositions);
        JPanel go=new JPanel();
        add(view);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setVisible(true);
        view.requestFocus();
        view.run();
    }
    public static void main(String[] args){
        try{
            double[][] coord=new double[args.length][2];
            for(int i=0;i<args.length;i++){
                String[] temp=args[i].split(",");
                coord[i][0]=Double.parseDouble(temp[0]);
                coord[i][1]=Double.parseDouble(temp[1]);
            }
            //coord=new double[][]{{100,100},{200,200}};// put tower positions here
            new Game(coord);
        }
        catch(Exception x){
            new Game(null);
        }
    }
}
