
public abstract class GameObject{// abstract class for anything which appears in the game
    double x,y;
    int width,height;
    javax.swing.ImageIcon image;
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public javax.swing.ImageIcon getImage(){
        return image;
    }
}
