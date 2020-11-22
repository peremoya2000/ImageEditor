package imageProcessors;
import java.awt.image.DataBufferByte;

import main.MyBufferedImage;

public class FXProcessor extends Processor{
	private int valR,valG,valB;
	public FXProcessor(byte id, int begX, int begY, int endX, int endY, MyBufferedImage image, int valR, int valG, int valB) {
		super(id,begX,begY,endX,endY,image);
		this.valR=valR;
		this.valG=valG;
		this.valB=valB;
	}
	
	@Override
	public void run() {		
		DataBufferByte db=(DataBufferByte)image.getData().getDataBuffer();
		byte[] originalpx=db.getData();
		int churroSize=image.getRaster().getDataBuffer().getSize();
		int width=image.getWidth();
		byte[] pixels = new byte[churroSize];
		int newR,newG,newB,pos;
		
			for (int i=begY; i<endY; ++i) {
				for (int j=begX;j<endX; ++j) {
					pos=i*width*3+j*3;
					newR=Byte.toUnsignedInt(originalpx[pos+2])+valR;
					newG=Byte.toUnsignedInt(originalpx[pos+1])+valG;
					newB=Byte.toUnsignedInt(originalpx[pos])+valB;

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
		image.writeData(pixels, id);
	}
	
}
