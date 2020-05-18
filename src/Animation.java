import java.awt.Graphics2D;

public abstract class Animation {
	private int imageDilay;
	private int x;
	private int y;
	private int width;
	private int height;
	public abstract boolean render(Graphics2D ctx);
	public Animation(int x,int y,int w,int h) {
		this.x=x;
		this.y=y;
		width=w;
		height=h;
	}
	public int getImageDilay() {
		return imageDilay;
	}
	public void setImageDilay(int imageDilay) {
		this.imageDilay = imageDilay;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
