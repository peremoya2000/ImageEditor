package imageProcessors;
import main.MyBufferedImage;

public class BWProcessor extends Processor{
	byte[] originalpx;
	public BWProcessor(byte id, int begX, int begY, int endX, int endY, MyBufferedImage image, byte[] ogpixels) {
		super(id,begX,begY,endX,endY,image);
		this.originalpx=ogpixels;
	}
	
	@Override
	public void run() {	
		int width=image.getWidth();
		byte[] pixels = new byte[originalpx.length];
		int newR,newG,newB,pos,avg;
			for (int i=begY; i<endY; ++i) {
				for (int j=begX;j<endX; ++j) {
					pos=i*width*3+j*3;
					newR=Byte.toUnsignedInt(originalpx[pos+2]);
					newG=Byte.toUnsignedInt(originalpx[pos+1]);
					newB=Byte.toUnsignedInt(originalpx[pos]);
					
					avg=(newR+newG+newB)/3;
					avg=(avg>255 ? 255 : avg);		
					avg=(avg<0 ? 0 : avg);
					
					pixels[pos+2]=pixels[pos+1]=pixels[pos]=(byte)avg;	
				}		
			}
		image.writeData(pixels, id);
	}
	
}
