package imageProcessors;

import java.awt.image.DataBufferByte;
import java.util.Random;

import main.MyBufferedImage;

public class GrainProcessor extends Processor{
	public GrainProcessor(byte id, int begX, int begY, int endX, int endY, MyBufferedImage image) {
		super(id,begX,begY,endX,endY,image);
	}
	
	@Override
	public void run() {	
		DataBufferByte db=(DataBufferByte)image.getData().getDataBuffer();
		byte[] originalpx=db.getData();
		int churroSize=image.getRaster().getDataBuffer().getSize();
		int width=image.getWidth();
		byte[] pixels = new byte[churroSize];
		int newR,newG,newB,pos;
		Random r = new Random();
			for (int i=begY; i<endY; ++i) {	
				for (int j=begX; j<endX;++j) {
					pos=i*width*3+j*3;					
					if(r.nextFloat()>0.8f) {
						pixels[pos+2]=(byte) 0;
						pixels[pos+1]=(byte) 0;
						pixels[pos]=(byte) 0;
					}else {
						newB=Byte.toUnsignedInt(originalpx[pos]);
						newG=Byte.toUnsignedInt(originalpx[pos+1]);
						newR=Byte.toUnsignedInt(originalpx[pos+2]);

						newR=(newR>255 ? 255 : newR);
						newG=(newG>255 ? 255 : newG);
						newB=(newB>255 ? 255 : newB);
						newR=(newR<0 ? 0 : newR);
						newG=(newG<0 ? 0 : newG);
						newB=(newB<0 ? 0 : newB);
						pixels[pos]=(byte) newR;
						pixels[pos+1]=(byte) newG;
						pixels[pos+2]=(byte) newB;
					}
											
				}
			}		
			image.writeData(pixels, id);
	}
	
}