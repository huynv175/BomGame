package moving;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import screen.Game;
import staticObj.Item;

public class Bomber extends Char {

    public Bomber() {

    }
    public Bomber(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.speed = 5;
    }


    @Override
    public void paint(GraphicsContext gc) {
        gc.drawImage(image, x, y);
    }

    @Override
    public void move(int x, int y) {
        if (isDie) {
            return;
        }
        if (x < 0 || x > 570) {
            return;
        }
        if (y < 0 || y > 570) {
            return;
        }

        // gặp brick và wall
        Rectangle me =
                new Rectangle(x + 1, y + 1, 28, 28);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if ((Game.dataMap[i][j] == 1 || Game.dataMap[i][j] == 4)
                        && me.intersects(j * 30, i * 30, 30, 30)) {
                    return;
                }
            }
        }
        this.x = x;
        this.y = y;
    }

    /**
     * enemy.
     *
     * @param enemy tham số
     * @return trả về true hoặc false
     */
    public boolean enemyAttack(Enemy enemy) {
        Rectangle me =
                new Rectangle(x + 1, y + 1, 28, 28);
        if (me.intersects(enemy.x, enemy.y, 30, 30)) {
            return true;
        }
        return false;
    }

    public boolean onealAttack(Oneal oneal) {
        Rectangle me =
                new Rectangle(x + 1, y + 1, 28, 28);
        if (me.intersects(oneal.x, oneal.y, 30, 30)) {
            return true;
        }
        return false;
    }
    public boolean pickItem(Item item) {
        Rectangle me =
                new Rectangle(x + 1, y + 1, 28, 28);
        if (me.intersects(item.x, item.y, 30, 30)) {
            return true;
        }
        return false;
    }


}
