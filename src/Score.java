
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


/**
 *
 * @author Yashar Nesabian
 */
public class Score extends ScreenObject{
    
    int score = 0;

    public Score(int x, int y, int w, int h, Color cl, ScreenManager sm) {
        super(x, y, w, h, cl, sm);
    }
    
    public void addScore(){
        score++;
    }
    
    public int getScore(){
        return score;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(cl);
        g.setFont(new Font("Arial", Font.BOLD, h));
        g.drawString("" + score, x, y);
    }

}
