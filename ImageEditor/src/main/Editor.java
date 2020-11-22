package main;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Editor extends JFrame{
	private MyBufferedImage image=null;
	private MyBufferedImage result=null;
	public static void main(String[] args) {		
		Editor e = new Editor();
	}	
	public MyBufferedImage getImage() {
		return image;
	}
	public void setImage(MyBufferedImage image) {
		this.image = image;
	}
	public MyBufferedImage getResult() {
		return result;
	}
	public void setResult(MyBufferedImage result) {
		this.result = result;
	}

	public Editor() {
		this.image=null;
		this.result=null;
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		try {
			BufferedImage temp=ImageIO.read(new File("logo.jpg"));
			image= new MyBufferedImage(temp);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		result=new MyBufferedImage(deepCopy(image));
		Viewer v = new Viewer(image,result);
        this.addComponentListener(new ComponentAdapter() {  
            public void componentResized(ComponentEvent evt) {
            	v.setImageWidth();
            }
        });
		this.setSize(result.getWidth()*2, 768);
		v.setSize(result.getWidth()*2,result.getHeight());
		ControlPanel cp = new ControlPanel(v,image);
		cp.setSize(result.getWidth()*2,result.getHeight()+100);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 0.7;
		this.add(v, c);
		c.gridy = 1;
		c.weighty = 0.3;
		this.add(cp, c);
		cp.myPaint();
				
		Graphics g = v.getGraphics();
		g.drawImage(image, 0, 0,image.getWidth(),image.getHeight(), null);
		g.drawImage(result, image.getWidth(), 0,result.getWidth(),result.getHeight(), null);
		
	}
	
	//Make a clean copy of an image on a new adress
	public BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, cm.isAlphaPremultiplied(), null);
	}
}
