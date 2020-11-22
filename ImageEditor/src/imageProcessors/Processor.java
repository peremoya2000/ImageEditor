package imageProcessors;
import main.MyBufferedImage;
///Parent class for the classes that are used to asynchronously process images in chunks
public class Processor implements Runnable{
	protected Thread myThread;
	protected int begX,begY,endX,endY;
	protected byte id;
	protected MyBufferedImage image;
	
	public Processor() {}
	
	public Processor(byte id, int begX, int begY, int endX, int endY, MyBufferedImage image) {
		this.id=id;
		this.begX=begX;
		this.begY=begY;
		this.endX=endX;
		this.endY=endY;
		this.image=image;
		myThread= new Thread(this);
		myThread.start();
	}

	@Override
	public void run() {
		
		
	}

	public int getEndY() {
		return endY;
	}

	public void setEndY(int endY) {
		this.endY = endY;
	}

	public int getBegX() {
		return begX;
	}

	public void setBegX(int begX) {
		this.begX = begX;
	}

	public int getEndX() {
		return endX;
	}

	public void setEndX(int endX) {
		this.endX = endX;
	}

	public int getBegY() {
		return begY;
	}

	public void setBegY(int begY) {
		this.begY = begY;
	}
}
