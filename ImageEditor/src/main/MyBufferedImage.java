package main;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import imageProcessors.BWProcessor;
import imageProcessors.BlurProcessor;
import imageProcessors.FXProcessor;
import imageProcessors.GrainProcessor;
import imageProcessors.SharpenningProcessor;
//Extends buffered image and has the functions to apply the effects
public class MyBufferedImage extends BufferedImage{
	private int width;
	private int height;
	private byte[] pixels;
	private boolean bw;
	private boolean[] written;
	
	public MyBufferedImage(BufferedImage bi) {
		super(bi.getColorModel(),bi.getRaster(),bi.getColorModel().isAlphaPremultiplied(), null);
		this.pixels=copyBytes(bi);
		this.width=bi.getWidth();
		this.height=bi.getHeight();
		this.bw=false;
		this.written= new boolean[4];
	}
	
	public MyBufferedImage applyBlackWhite() {
		if(!bw) {
			bw=true;
			byte threads=8;
			this.written= new boolean[threads];
			for (byte i=0;i<threads;++i) {
				BWProcessor p= new BWProcessor(i, 0, height/threads*i, width, height/threads*(i+1), this, pixels);
			}
			//The reason why I use sleep instead of a produces/consumer approach is that
			//the main thread is the AWT one and when it waits the program dies
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	
	public synchronized MyBufferedImage applyColorFX(int valR, int valG, int valB) {
		byte threads=8;
		this.written= new boolean[threads];
		for (byte i=0;i<threads;++i) {
			FXProcessor p= new FXProcessor(i, 0, height/threads*i, width, height/threads*(i+1), this, valR, valG, valB);
		}
		try {
			Thread.sleep(33);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public synchronized MyBufferedImage applyBlur() {
		byte threads=4;
		this.written= new boolean[threads];
		for (byte i=0;i<threads;++i) {
			BlurProcessor p= new BlurProcessor(i, 0, height/threads*i, width, height/threads*(i+1), this);
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	
	public synchronized MyBufferedImage applySharpenning() {
		byte threads=8;
		this.written= new boolean[threads];
		for (byte i=0;i<threads;++i) {
			SharpenningProcessor p= new SharpenningProcessor(i, 0, height/threads*i, width, height/threads*(i+1), this);
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	
	public synchronized MyBufferedImage applyGrain() {
		byte threads=8;
		this.written= new boolean[threads];
		for (byte i=0;i<threads;++i) {
			GrainProcessor p= new GrainProcessor(i, 0, height/threads*i, width, height/threads*(i+1), this);
		}
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}

	public BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
	}
	public byte[] copyBytes(BufferedImage bi) {
		DataBufferByte db=(DataBufferByte)bi.getData().getDataBuffer();
		int size=db.getSize();
		byte[] newarray = new byte[size];	
		byte[] originalpx = db.getData();
		for (int i=0; i<size; ++i) {
			newarray[i]=originalpx[i];
		}
		return newarray;	
	}
	
	
	//The data resulting from the processing threads overwrites the current data. 
	//When all the threads are done the date is inserted into the image
	public synchronized void writeData(byte[] data, byte id) {
		int end=data.length/written.length*(id+1);
		for(int i=data.length/written.length*id; i<end;++i) {
			pixels[i]=data[i];			
		}
		this.written[id]=true;
		for(byte i=0; i<written.length;++i) {
			if(!written[i])break;
			if(i==written.length-1) {
				this.getRaster().setDataElements(0, 0, width, height, pixels);
			}
		}
	}
	

}
