import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Block extends Rectangle{
	public static final BufferedImage[] SRC;
	private Type type;
	private Tower tower;
	public static final int WIDTH = 50;
	public Block(int x, int y,int w,int h,Type t) {
		super(x,y,w,h);
		type = t;
		setTower(null);
	}
	public Type getType() {
		return type;
	}
	public void setType(Type t) {
		type = t;
	}
	public void render(Graphics2D ctx) {
		BufferedImage buff = null;
		if(type==Type.GRASS)buff=SRC[1];
		if(type==Type.ROAD)buff=SRC[0];
		ctx.drawImage(buff, (int)getX(), (int)getY(), (int)getWidth(), (int)getHeight(),null);
	}
	static{
		SRC = new BufferedImage[3];
		try {
			SRC[0] = ImageIO.read(new File("img/road.png"));
			SRC[1] = ImageIO.read(new File("img/grass.png"));
			SRC[2] = ImageIO.read(new File("img/water.png"));
		}
		catch(java.io.IOException e) {}
	}
	public Tower getTower() {
		return tower;
	}
	public void setTower(Tower t) {
		tower = t;
	}
	public int[] getDir(Block b) {
		int[] temp = {0,0};
		if(b==this||b==null)return temp;
		if(getX() == b.getX() && getY()<b.getY())temp[1] = 1;
		else if(getX() == b.getX())temp[1] = -1;
		else if(getX()<b.getX())temp[0] = 1;
		else temp[0] = -1;
		return temp;
	}
}
