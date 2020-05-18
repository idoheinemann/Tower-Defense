import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GoldenBullet extends Shoot {
	public GoldenBullet(double ang, int nx, int ny, int s) {
		super(ang, 15, nx, ny, s, s, 16);
	}
	private int pirce;
	public static BufferedImage SRC;
	@Override
	public boolean attack(Enemy e) {
		e.die(getDamage());
		pirce++;
		setDamage(getDamage()-2);
		return pirce==3;
	}
	@Override
	public void render(Graphics2D ctx) {
		ctx.translate(getX()+getWidth()/2, getY()+getHeight()/2);
		ctx.rotate(Math.PI+getAngle());
		ctx.drawImage(SRC,-getWidth()/2,-getHeight()/2,getWidth(),getHeight(),null);
		ctx.rotate(-Math.PI-getAngle());
		ctx.translate(-(getX()+getWidth()/2), -(getY()+getHeight()/2));
	}
	static{
		try {
			SRC = ImageIO.read(new File("img/goldbullet.png"));
		}
		catch(IOException e) {e.printStackTrace();}
	}
}
