import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Panel {
    public static final int WIDTH = 120;
    private final Tower tower;
    private final BufferedImage img;

    public Panel(Tower t, BufferedImage i, GamePanel j) {
        tower = t;
        img = i;
    }

    public void render(Graphics2D ctx) {
        ctx.setColor(Color.YELLOW);
        ctx.fillRect(img.getWidth() - 260, 0, 260, img.getHeight());
        ctx.setColor(new Color(165, 128, 18));
        ctx.drawLine(img.getWidth() - 20, 0, img.getWidth(), 20);
        ctx.drawLine(img.getWidth() - 20, 20, img.getWidth(), 0);
        ctx.drawLine(img.getWidth() - 20, 0, img.getWidth() - 20, 20);
        ctx.drawLine(img.getWidth() - 20, 20, img.getWidth(), 20);
        ctx.drawRect(img.getWidth() - 220, 35, 160, 160);
        ctx.drawImage(tower.getImg(), img.getWidth() - 220, 35, 160, 160, null);
        ctx.fillRect(img.getWidth() - 220, img.getHeight() - 180, 160, 60);
        if (tower.getUpgrade() == tower.max_upgrade) ctx.setColor(Color.GRAY);
        ctx.fillRect(img.getWidth() - 220, 215, 160, 60);
        ctx.setFont(new Font("baloonist", 0, 35));
        ctx.setColor(Color.black);
        ctx.drawString("Sell:" + (tower.getPrice() / 10 * 9), img.getWidth() - 215, img.getHeight() - 140);
        ctx.drawString("Upg:" + (tower.getUpgrade() < tower.max_upgrade ? tower.upgrade_price[tower.getUpgrade()] : "Max"), img.getWidth() - 215, 255);

    }

    public Tower getTower() {
        return tower;
    }
}
