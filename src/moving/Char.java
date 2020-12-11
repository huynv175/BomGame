package moving;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import staticObj.Bomb;

public abstract class Char {
    public int x;
    public int y;
    public Image image;
    public int speed;
    public boolean isDie;



    public Char() {

    }

    public abstract void paint(GraphicsContext gc);

    public abstract void move(int x, int y);

    public boolean bombBangDie(Bomb bomb) {
        Rectangle me =
                new Rectangle(x + 1, y + 1, 28, 28);
        if (me.intersects(bomb.x - 30, bomb.y, 90, 30)) {
            return true;
        }
        if (me.intersects(bomb.x, bomb.y - 30, 30, 90)) {
            return true;
        }
        return false;
    }
}
