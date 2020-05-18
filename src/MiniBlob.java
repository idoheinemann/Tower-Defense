import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class MiniBlob extends Tower{
	public static final BufferedImage[] SRCA,SRCT;
	public BufferedImage[] SRC;
	private int imageIndex = 0; 
	public MiniBlob(Block b, Panel n) {
		super(b, 0, n, 0, 45, 7);
		setBlock(b,true);
		setRange(100);
	}
	public MiniBlob(Block b) {
		this(b,null);
	}
	@Override
	public void upgrade() {}
	@Override
	public void shoot(ArrayList<Enemy> e, ArrayList<Shoot> s) {
		if(getCooldown()==0) {
			boolean shoot = false;
			for(int i=0;i!=e.size();i++) {
				if(Math.pow(e.get(i).getCenterX()-getCenterX(),2)+Math.pow(e.get(i).getCenterY()-getCenterY(),2)<Math.pow(getRange(), 2)) {
					shoot = true;
					double dx = (double)getCenterX()-e.get(i).getCenterX();
					double dy = (double)getCenterY()-e.get(i).getCenterY();
					s.add(new Slimeball(Math.PI+(e.get(i).getCenterY()<getCenterY()?Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy)))):-Math.acos(dx/(Math.sqrt((dx*dx)+(dy*dy))))),getX(),getY(),10,0.9));
					break;
				}
			}
			if(shoot)setCooldown(getCooldownf());
		}
		else setCooldown(getCooldown()-1);
	}
	@Override
	public void render(Graphics2D ctx) {
		boolean b = false;
		for(int i=0;i!=GamePanel.enemies.size();i++) {
			if(Math.pow(GamePanel.enemies.get(i).getCenterX()-getCenterX(),2)+Math.pow(GamePanel.enemies.get(i).getCenterY()-getCenterY(),2)<Math.pow(getRange(), 2)) {
				b = true;
			}
		}
		SRC = b?SRCA:SRCT;
		ctx.drawImage(SRC[imageIndex>=SRC.length?0:imageIndex],getX(),getY(),getWidth(),getHeight(),null);
		if(getImageDilay()==0) {
			imageIndex++;
			if(imageIndex>=SRC.length)imageIndex=0;
			setImg(SRC[imageIndex]);
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
	}
	static{
		SRCT = new BufferedImage[7];
		SRCA = new BufferedImage[10];
		try {
			for(int i=0;i!=7;i++) {
				SRCT[i] = ImageIO.read(new File("img/minblob$"+(i+1)+".png"));
			}
			for(int i=0;i!=10;i++) {
				SRCA[i] = ImageIO.read(new File("img/minbloba$"+(i+1)+".png"));
			}
		}
		catch(IOException e) {}
	}
}