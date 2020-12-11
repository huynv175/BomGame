
package moving;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import screen.Game;
import staticObj.Bomb;

import java.util.Random;

    public class Oneal extends Char {
        public boolean moveX;
        public boolean changeDirection;
        public Char bomber;

        public Oneal(Image enemy, int i, int i1) {

        }

        public Oneal(Image image, int x, int y, Bomber bomber) {
            this.bomber = bomber;
            this.image = image;
            this.x = x;
            this.y = y;
            this.speed = 2;
            this.moveX = new Random().nextBoolean(); // random di chuyển dọc or ngang
        }

        @Override
        public void paint(GraphicsContext gc) {
            gc.drawImage(image, x, y);
        }

        @Override
        public void move(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean canMove(int x, int y, Bomb bomb) {
            if (x < 0 || x > 570) {
                return false;
            }
            if (y < 0 || y > 570) {
                return false;
            }
            Rectangle me =
                    new Rectangle(x + 1, y + 1, 28, 28);

            if (bomb.timeBomb > 50 && me.intersects(bomb.x, bomb.y, 30, 30)) {
                return false;
            }
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if ((Game.dataMap[i][j] == 1 || Game.dataMap[i][j] == 4)
                            && me.intersects(j * 30, i * 30, 30, 30)) {
                        return false;
                    }
                }
            }
            return true;
        }


        /**
         * Di theo bomber.
         */
        public void autoMove(Bomb bomb) {
            if (this.moveX) {
            if (this.x < bomber.x) {
                System.out.println("this.x < bomber.x");
                if (this.canMove(this.x + this.speed, this.y, bomb)) {
                    this.move(this.x + this.speed, this.y);
                    return;
                } else moveX = !moveX;
            } else if (this.x > bomber.x) {
                System.out.println("this.x > bomber.x");
                if (this.canMove(this.x - this.speed, this.y, bomb)) {
                    this.move(this.x - this.speed, this.y);
                    return;
                } moveX = !moveX;
            } else if (this.x == bomber.x) {
                System.out.println("x==x");
                moveX = !moveX;
                return;
            }
        } else {
            if (this.y < bomber.y) {
                System.out.println("this.y < bomber.y");
                if (this.canMove(this.x, this.y + this.speed, bomb)) {
                    this.move(this.x, this.y + this.speed);
                    return;
                } moveX = !moveX;
            } else if (this.y > bomber.y) {
                System.out.println("this.y > bomber.y");
                if (this.canMove(this.x, this.y - this.speed, bomb)) {
                    this.move(this.x, this.y - this.speed);
                    return;
                } moveX = !moveX;
            } else if (this.y == bomber.y) {
                System.out.println("y==y");
                moveX = !moveX;
                return;
            }
        }
        }
    }
//        if (this.moveX) {
//            if (!this.changeDirection) {
//                if (this.canMove(this.x + this.speed, this.y, bomb)) {
//                    this.move(this.x + this.speed, this.y);
//                    return;
//                }
//                changeDirection = true;
//                this.move(this.x - this.speed, this.y);
//                return;
//
//            }
//            if (this.canMove(this.x - this.speed, this.y, bomb)) {
//                this.move(this.x - this.speed, this.y);
//                return;
//            }
//            changeDirection = false;
//            this.move(this.x + this.speed, this.y);
//            return;
//        }
//        if (!this.changeDirection) {
//            if (this.canMove(this.x, this.y + this.speed, bomb)) {
//                this.move(this.x, this.y + this.speed);
//                return;
//            }
//            changeDirection = true;
//            this.move(this.x, this.y - this.speed);
//            return;
//        }
//        if (this.canMove(this.x, this.y - this.speed, bomb)) {
//            this.move(this.x, this.y - this.speed);
//            return;
//        }
//        changeDirection = false;
//        this.move(this.x, this.y + this.speed);
//        }
//    }

