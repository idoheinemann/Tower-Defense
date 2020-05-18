import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wizard extends Enemy {
    public static final double EXCEL = 0.02;
    private byte[] dir = {0, 0};
    private int imageIndex;
    private int imageMove;
    private Block target = null;
    private Block start;
    public static final BufferedImage[] SRC;

    public Wizard() {
        super(20, 100, 0, Block.WIDTH, Block.WIDTH, 40);
    }

    @Override
    public void render(Graphics2D ctx) {
        setImg(SRC[imageIndex]);
        ctx.drawImage(SRC[imageIndex], (int) getX(), (int) getY(), 30, 30, null);
        if (getImageDilay() == 0) {
            imageIndex += imageMove;
            imageMove = imageIndex == 3 ? -1 : imageIndex == 0 ? 1 : imageMove;
            setImageDilay(2);
        } else setImageDilay(getImageDilay() - 1);
    }

    @Override
    public boolean move() {
        if (dir[0] == 0 && dir[1] == 0) {
            try {
                if (GamePanel.getBlock(getCenterX() - Block.WIDTH, getCenterY()).getType() == Type.ROAD) dir[0] = -1;
            } catch (NullPointerException ignored) {
            }
            try {
                if (GamePanel.getBlock(getCenterX() + Block.WIDTH, getCenterY()).getType() == Type.ROAD) dir[0] = 1;
            } catch (NullPointerException ignored) {
            }
            try {
                if (GamePanel.getBlock(getCenterX(), getCenterY() - Block.WIDTH).getType() == Type.ROAD) dir[1] = -1;
            } catch (NullPointerException ignored) {
            }
            try {
                if (GamePanel.getBlock(getCenterX(), getCenterY() + Block.WIDTH).getType() == Type.ROAD) dir[1] = 1;
            } catch (NullPointerException ignored) {
            }
            start = GamePanel.getBlock(getCenterX(), getCenterY());
            System.out.println(start);
            for (int i = 0; GamePanel.getBlock(getCenterX() + (Block.WIDTH * i * dir[0]), getCenterY() + (Block.WIDTH * i * dir[1])).getType() == Type.ROAD; i++)
                target = GamePanel.getBlock(getCenterX() + (Block.WIDTH * i * dir[0]), getCenterY() + (Block.WIDTH * i * dir[1]));
            System.out.println(target);
        }
        double sttg = Math.abs(target.getX() - start.getX() + target.getY() - start.getY()) / 3;
        double metg = Math.abs(target.getCenterX() - getCenterX() + target.getCenterY() - getCenterY());
        setSpeed(getSpeed() + (metg > sttg ? EXCEL : -2 * EXCEL));
        if (GamePanel.getBlock(getCenterX(), getCenterY()) == target) {
            start = target;
            if (dir[1] != 0) {
                dir[1] = 0;
                if (GamePanel.getBlock(getCenterX() - Block.WIDTH, getCenterY()).getType() == Type.ROAD) dir[0] = -1;
                if (GamePanel.getBlock(getCenterX() + Block.WIDTH, getCenterY()).getType() == Type.ROAD) dir[0] = 1;
            } else {
                dir[0] = 0;
                if (GamePanel.getBlock(getCenterX(), getCenterY() - Block.WIDTH).getType() == Type.ROAD) dir[1] = -1;
                if (GamePanel.getBlock(getCenterX(), getCenterY() + Block.WIDTH).getType() == Type.ROAD) dir[1] = 1;
            }
            try {
                for (int i = 0; GamePanel.getBlock(getCenterX() + (Block.WIDTH * i * dir[0]), getCenterY() + (Block.WIDTH * i * dir[1])).getType() == Type.ROAD; i++) {
                    if (GamePanel.getBlock(getCenterX() + (Block.WIDTH * i * dir[0]), getCenterY() + (Block.WIDTH * i * dir[1])) != null)
                        break;
                    target = GamePanel.getBlock(getCenterX() + (Block.WIDTH * i * dir[0]), getCenterY() + (Block.WIDTH * i * dir[1]));
                }
            } catch (NullPointerException e) {
                return true;
            }
        }
        return super.move();
    }

    static {
        SRC = new BufferedImage[8];
        for (int i = 0; i != SRC.length; i++) {
            try {
                SRC[i] = ImageIO.read(new File("img/wizard$" + (i + 1) + ".png"));
            } catch (IOException ignored) {
            }
        }
    }
}
