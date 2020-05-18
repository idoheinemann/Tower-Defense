import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Explotion extends Animation {
	public static final BufferedImage[] SRC;
	private int imageIndex = 0;
	public Explotion(int x, int y, int s) {
		super(x, y, s, s);
	}
	@Override
	public boolean render(Graphics2D ctx) {
		ctx.drawImage(SRC[imageIndex],getX(),getY(),getWidth(),getHeight(),null);
		if(getImageDilay()==0) {
			imageIndex++;
			if(imageIndex==8) {
				return true;
			}
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
		return false;
	}
	static{
		SRC = new BufferedImage[8];
		for(int i=0;i!=SRC.length;i++) {
			try {
				SRC[i] = ImageIO.read(new File("img/explotion$"+(1+i)+".png"));
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
