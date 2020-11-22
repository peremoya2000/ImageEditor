package imageProcessors;
import java.awt.image.DataBufferByte;

import main.MyBufferedImage;

public class SharpenningProcessor extends Processor{
	public SharpenningProcessor(byte id, int begX, int begY, int endX, int endY, MyBufferedImage image) {
		super(id,begX,begY,endX,endY,image);
	}
	
	@Override
	public void run() {	
		DataBufferByte db=(DataBufferByte)image.getData().getDataBuffer();
		byte[] originalpx=db.getData();
		int churroSize=image.getRaster().getDataBuffer().getSize();
		int width=image.getWidth();
		byte[] pixels = new byte[churroSize];
		byte[][] conv= {{0,-1,0},{-1,5,-1},{0,-1,0}};
		int[][]trans= {{-width*3-3,-width*3,-width*3+3},{-3,0,3},{width*3-3,width*3,width*3+3}};
		int newR,newG,newB,pos,otherPos;
			for (int i=begY; i<endY; ++i) {
				for (int j=begX;j<endX; ++j) {
					pos=i*width*3+j*3;		
					newR=newG=newB=0;		
					for(byte k=0; k<3;++k) {
						for(byte l=0;l<3;++l) {
							otherPos=pos+trans[k][l];							
							otherPos=(otherPos<0 ? 0 : otherPos);
							otherPos=Math.min(otherPos, churroSize-3);
							newR+=Byte.toUnsignedInt(originalpx[otherPos+2])*conv[k][l];
							newG+=Byte.toUnsignedInt(originalpx[otherPos+1])*conv[k][l];
							newB+=Byte.toUnsignedInt(originalpx[otherPos])*conv[k][l];
						}
					}
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
