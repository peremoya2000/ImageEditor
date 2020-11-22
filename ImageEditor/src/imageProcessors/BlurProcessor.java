package imageProcessors;
import java.awt.image.DataBufferByte;

import main.MyBufferedImage;

public class BlurProcessor extends Processor{
	public BlurProcessor(byte id, int begX, int begY, int endX, int endY, MyBufferedImage image) {
		super(id,begX,begY,endX,endY,image);
	}
	
	@Override
	public void run() {	
		DataBufferByte db=(DataBufferByte)image.getData().getDataBuffer();
		byte[] originalpx=db.getData();
		int churroSize=image.getRaster().getDataBuffer().getSize();
		int width=image.getWidth();
		byte[] pixels = new byte[churroSize];
		int[][]trans= {{-width*3-3,-width*3,-width*3+3},{-3,0,3},{width*3-3,width*3,width*3+3}};
		int newR,newG,newB,pos,otherPos;
		for (byte pass=0; pass<4; ++pass) {
			for (int i=begY; i<endY; ++i) {
				for (int j=begX;j<endX; ++j) {
					pos=i*width*3+j*3;		
					newR=newG=newB=0;		
					for(byte k=0; k<3;++k) {
						for(byte l=0;l<3;++l) {
							otherPos=pos+trans[k][l];							
							otherPos=(otherPos<0 ? 0 : otherPos);
							otherPos=Math.min(otherPos, churroSize-3);
							newR+=Byte.toUnsignedInt(originalpx[otherPos+2]);
							newG+=Byte.toUnsignedInt(originalpx[otherPos+1]);
							newB+=Byte.toUnsignedInt(originalpx[otherPos]);
						}
					}
					newR=(newR/9>255 ? 255 : newR/9);
					newG=(newG/9>255 ? 255 : newG/9);
					newB=(newB/9>255 ? 255 : newB/9);
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
