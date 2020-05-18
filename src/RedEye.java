import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class RedEye extends Tower {
	public static final BufferedImage[] SRC;
	private int imageIndex = 0;
	private int imageMove;
	@Override
	public void render(Graphics2D ctx) {
		setImg(SRC[imageIndex]);
		ctx.drawImage(SRC[imageIndex],getX(),getY(),30,30,null);
		if(getImageDilay()==0) {
			imageIndex+=imageMove;
			imageMove = imageIndex==3?-1:imageIndex==0?1:imageMove;
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
	}
	public RedEye(Block b) {
		this(b,null);
	}
	public RedEye(Block b,Panel p) {
		super(b,50,p,3,60,5);
		upgrade_price = new int[max_upgrade];
		upgrade_price[0] = 25;
		upgrade_price[1] = 40;
		upgrade_price[2] = 80;
		setRange(300);
	}
	@Override
	public void upgrade() {
		addUpgrade();
		if(getUpgrade()==1)setCooldownf(30);
		if(getUpgrade()==2)setDamage(8);
	}
	static{
		SRC = new BufferedImage[4];
		try {
			for(int i=0;i!=SRC.length;i++)SRC[i] = ImageIO.read(new File("img/red_eye$"+(i+1)+".png"));
		}
		catch(IOException e) {}
	}
	@Override
	public void shoot(ArrayList<Enemy> e,ArrayList<Shoot> s) {
		if(getCooldown()<=0) {
			try {
				boolean shoot = false;
				boolean one = false;
				for(int i=0;i!=e.size();i++) {
					if(Math.pow(e.get(i).getCenterX()-getCenterX(),2)+Math.pow(e.get(i).getCenterY()-getCenterY(),2)<Math.pow(getRange(), 2)) {
						shoot = true;
						double dx = (double)getCenterX()-e.get(i).getCenterX();
						double dy = (double)getCenterY()-e.get(i).getCenterY();
						s.add(new Fireball(Math.PI+(e.get(i).getCenterY()<getCenterY()?Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))):-Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy))))),5,getX(),getY(),getUpgrade()>1?20:10,getDamage()));
						if(getUpgrade()<3 || one)break;
						else one=true;
					}
				}
				if(shoot)setCooldown(getCooldownf());
			}
			catch(NullPointerException er) {}
		}
		else setCooldown(getCooldown()-1);
	}
}
