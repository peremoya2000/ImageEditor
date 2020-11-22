package main;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Viewer extends Canvas implements Runnable{
	MyBufferedImage img0;
	MyBufferedImage img1;
	float aspect;
	short imageWidth;
	Thread painter;
	
	public MyBufferedImage getImg0() {
		return img0;
	}
	public void setImg0(MyBufferedImage img0) {
		this.img0 = img0;
	}
	public MyBufferedImage getImg1() {
		return img1;
	}
	public void setImg1(MyBufferedImage img1) {
		this.img1 = img1;
	}
	
	public float getAspect() {
		return aspect;
	}
	
	public void setImageWidth() {
		this.imageWidth=(short) (this.getWidth()/2);
	}
	
	public void calculateAspect() {
		this.aspect=((float)img0.getHeight()/(float)img0.getWidth());
		System.out.println("New aspect ratio is: "+1/aspect);
	}
	
	public Viewer(MyBufferedImage img0, MyBufferedImage img1) {
		this.img0=img0;
		this.img1=img1;
		this.aspect=1.0f;
		this.painter= new Thread(this);
		painter.start();
	}
	
	public void myPaint() {
		this.myPaint(this.getGraphics(), img0, img1);		
	}
	public void myPaint(Graphics g,Image img0,Image img1) {
		g.drawImage(img0,0,(int)(imageWidth-(imageWidth*aspect))/2,imageWidth,(int)(imageWidth*aspect),null);
		g.drawImage(img1,imageWidth,(int)(imageWidth-(imageWidth*aspect))/2,imageWidth,(int)(imageWidth*aspect),null);
	}
	
	//Override SWING's paint and redirect it to my own
	public void paint(Graphics g) {
		this.myPaint(g, img0, img1);
	}
	public MyBufferedImage applyColorFX(int valR, int valG, int valB) {
		img1.applyColorFX(valR, valG, valB);
		return img1;
	}
	public MyBufferedImage applyBlackWhite() {
		img1.applyBlackWhite();
		return img1;
	}
	public MyBufferedImage applyBlur(int valR, int valG, int valB) {
		img1.applyBlur();
		return img1;
	}
	public MyBufferedImage applySharpenning(int valR, int valG, int valB) {
		img1.applySharpenning();
		return img1;
	}
	public MyBufferedImage applyGrain() {
		img1.applyGrain();
		return img1;
	}
	public MyBufferedImage reset() {
		ColorModel cm = img0.getColorModel();
		WritableRaster raster = img0.copyData(null);
		img1 =  new MyBufferedImage(new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null));
		return img1;
	}
	//This is a painter thread that repaints the images every 2s
	//It's used to make sure that if a processing thread finishes after the refresh changes are eventually showed
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(2000);
				myPaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
	}
}
