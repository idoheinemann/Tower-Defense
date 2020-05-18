import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SilverBullet extends Shoot {
	public static BufferedImage SRC;
	private int pirce;
	public SilverBullet(double ang, int x, int y, int s) {
		super(ang, 7, x, y, s, s, 10);
	}
	@Override
	public boolean attack(Enemy e) {
		e.die(getDamage());
		pirce++;
		setDamage(getDamage()-2);
		return pirce==2;
	}
	@Override
	public void render(Graphics2D ctx) {
		ctx.translate(getX()+getWidth()/2, getY()+getHeight()/2);
		ctx.rotate(getAngle());
		ctx.drawImage(SRC,-getWidth()/2,-getHeight()/2,getWidth(),getHeight(),null);
		ctx.rotate(-getAngle());
		ctx.translate(-(getX()+getWidth()/2), -(getY()+getHeight()/2));
	}
	static{
		try {
			SRC = ImageIO.read(new File("img/silverbullet.png"));
		}
		catch(IOException e) {}
	}
}
