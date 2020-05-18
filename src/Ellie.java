import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
public class Ellie extends Tower {
	public static final BufferedImage[] SRCA,SRCT,SRCB;
	public BufferedImage[] SRC;
	private boolean aiming = false;
	private boolean bull = false;
	private int imageIndex;
	private int eightwait = 0;
	public Ellie(Block b, Panel n) {
		super(b, 3000, n, 4, 6, 10);
		setRange(170);
		upgrade_price = new int[max_upgrade];
		upgrade_price[0] = 1900;
		upgrade_price[1] = 2500;
		upgrade_price[2] = 5000;
		upgrade_price[3] = 30000;
	}
	public Ellie(Block b) {
		this(b,null);
	}
	@Override
	public void upgrade() {
		addUpgrade();
		if(getUpgrade()==1)setRange(220);
	}
	@Override
	public void shoot(ArrayList<Enemy> e, ArrayList<Shoot> s) {
		if(getCooldown()==0) {
			boolean shoot = false;
			int tri = 0;
			for(int i=0;i!=e.size();i++) {
				if(Math.pow(e.get(i).getCenterX()-getCenterX(),2)+Math.pow(e.get(i).getCenterY()-getCenterY(),2)<Math.pow(getRange(), 2)) {
					shoot = true;
					tri++;
					double dx = (double)getCenterX()-e.get(i).getCenterX();
					double dy = (double)getCenterY()-e.get(i).getCenterY();
					s.add(getUpgrade()>2?new GoldenBullet(Math.PI+(e.get(i).getCenterY()<getCenterY()?Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))):-Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy))))),getCenterX(),getCenterY(),12):new SilverBullet(Math.PI+(e.get(i).getCenterY()<getCenterY()?Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))):-Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy))))),getCenterX(),getCenterY(),13));
					if(getUpgrade()<1||tri==4)break;
				}
			}
			if(shoot)setCooldown(getCooldownf());
		}
		else setCooldown(getCooldown()-1);
		if(getUpgrade()>1&&eightwait==0) {
			for(int i=0;i!=(getUpgrade()>3?11:9);i++) {
				s.add(getUpgrade()>2?new GoldenBullet((getUpgrade()>3?0.2:0.25)*Math.PI*i,getCenterX(),getCenterY(),12):new SilverBullet(0.25*Math.PI*i,getCenterX(),getCenterY(),12));
			}
			eightwait = getUpgrade()>2?getUpgrade()>3?30:40:65;
		}
		else if(getUpgrade()>1)eightwait--;
	}
	@Override
	public void render(Graphics2D ctx) {
		boolean b = false;
		for(int i=0;i!=GamePanel.enemies.size();i++) {
			if(Math.pow(GamePanel.enemies.get(i).getCenterX()-getCenterX(),2)+Math.pow(GamePanel.enemies.get(i).getCenterY()-getCenterY(),2)<Math.pow(getRange(), 2)) {
				aiming = bull;
				b = true;
			}
		}
		bull = bull?bull:!b;
		SRC = aiming?SRCB:b?SRCA:SRCT;
		ctx.drawImage(SRC[imageIndex>=SRC.length?0:imageIndex],getX(),getY(),getWidth(),getHeight(),null);
		if(getImageDilay()==0) {
			imageIndex++;
			if(imageIndex==SRCB.length)bull=false;
			if(imageIndex>=SRC.length)imageIndex=0;
			setImg(SRC[imageIndex]);
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
	}
	static{
		SRCT = new BufferedImage[4];
		SRCB = new BufferedImage[8];
		SRCA = new BufferedImage[4];
		try {
			for(int i=0;i!=4;i++) {
				SRCT[i] = ImageIO.read(new File("img/ellie$"+(i+1)+".png"));
				SRCA[i] = ImageIO.read(new File("img/elliea$"+(i+1)+".png"));
			}
			for(int i=0;i!=8;i++) {
				SRCB[i] = ImageIO.read(new File("img/ellieaim$"+(i+1)+".png"));
			}
		}
		catch(IOException e) {e.printStackTrace();}
	}
}
