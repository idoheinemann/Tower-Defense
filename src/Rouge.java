import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rouge extends Enemy {
	private int imageIndex;
	
	public Rouge() {
		super(8, 30, 2.5, Block.WIDTH, Block.WIDTH,20);
	}
	public static final BufferedImage[] SRC;
	static{
		SRC = new BufferedImage[6];
		for(int i=0;i!=SRC.length;i++) {
			try {
				SRC[i] = ImageIO.read(new File("img/rouge$"+(i+1)+".png"));
			}
			catch(IOException e) {}
		}
	}
	@Override
	public void render(Graphics2D ctx) {
		setImg(SRC[imageIndex]);
		ctx.drawImage(SRC[imageIndex],(int)getX(),(int)getY(),getWidth(),getHeight(),null);
		if(getImageDilay()==0) {
			imageIndex++;
			if(imageIndex==6)imageIndex=0;
			setImageDilay(2);
		}
		else setImageDilay(getImageDilay()-1);
	}
}
