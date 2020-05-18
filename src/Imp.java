import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Imp extends Tower {
	public static final BufferedImage[] SRC;
	private int bombtime = 150;
	private int bullettime = 10;
	private int imageIndex = 0;
	public Imp(Block b) {
		this(b,null);
	}
	public Imp(Block b,Panel p) {
		super(b,200,p,4,150,8);
		upgrade_price = new int[max_upgrade];
		upgrade_price[0] = 80;
		upgrade_price[1] = 150;
		upgrade_price[2] = 300;
		upgrade_price[3] = 400;
		setRange(80);
	}
	@Override
	public void upgrade() {
		addUpgrade();
		if(getUpgrade()==1)setCooldownf(100);
		if(getUpgrade()==2) {
			bombtime = 300;
			setDamage(13);
		}
	}

	@Override
	public void shoot(ArrayList<Enemy> e, ArrayList<Shoot> s) {
		double[] arr = new double[0];
		if(getCooldown()==0) {
			for(int i=getCenterX()-getRange();i<=getCenterX()+getRange();i+=Block.WIDTH) {
				for(int j=getCenterY()-getRange();j<=getCenterY()+getRange();j+=Block.WIDTH) {
					if(GamePanel.getBlock(i, j)==null)continue;
					if(GamePanel.getBlock(i, j).getType()==Type.ROAD) {
						double[] temp = arr;
						arr = new double[temp.length+1];
						for (int k = 0; k < temp.length; k++) {
							arr[k] = temp[k];
						}
						Block b = GamePanel.getBlock(i, j);
						int dx = (int) (getCenterX()-b.getCenterX());
						int dy = (int) (getCenterY()-b.getCenterY());
						arr[arr.length-1] = Math.PI+(b.getCenterY()<getCenterY()?Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))):-Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))));
					}
				}
			}
			if(arr.length==0)return;
			s.add(new Bomb(arr[(int) (Math.random()*arr.length)], getCenterX(), getCenterY(), 30, getDamage(),bombtime));
			setCooldown(getCooldownf());
		}
		else setCooldown(getCooldown()-1);
		if(getUpgrade()>2 && bullettime==0) {
			boolean shoot = false;
			boolean one = false;
			for(int i=0;i!=e.size();i++) {
				if(Math.pow(e.get(i).getCenterX()-getCenterX(),2)+Math.pow(e.get(i).getCenterY()-getCenterY(),2)<Math.pow(getRange(), 2)) {
					shoot = true;
					double dx = (double)getCenterX()-e.get(i).getCenterX();
					double dy = (double)getCenterY()-e.get(i).getCenterY();
					s.add(new Bullet(Math.PI+(e.get(i).getCenterY()<getCenterY()?Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))):-Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy))))),getCenterX(),getCenterY(),10,3));
					if(getUpgrade()<4 || one)break;
					else one=true;
				}
			}
			if(shoot)bullettime = getUpgrade()==3?10:5;
		}
		else if(getUpgrade()>2)bullettime--;
	}

	@Override
	public void render(Graphics2D ctx) {
		setImg(SRC[imageIndex ]);
		ctx.drawImage(SRC[imageIndex],getX(),getY(),getWidth(),getHeight(),null);
		if(getImageDilay()==0) {
			imageIndex++;
			if(imageIndex==7)imageIndex=0;
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
	}
	static {
		SRC = new BufferedImage[7];
		try {
			for(int i=0;i!=7;i++) {
				SRC[i] = ImageIO.read(new File("img/imp$"+(i+1)+".png"));
			}
		}
		catch(IOException e) {}
	}
}
