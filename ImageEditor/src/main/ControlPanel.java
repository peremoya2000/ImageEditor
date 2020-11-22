package main;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.filechooser.FileNameExtensionFilter;
///Code for the buttons and their functionality
public class ControlPanel extends JPanel{
	private MyBufferedImage image=null;
	private MyBufferedImage result=null;
	private int valR=0,valG=0,valB=0;
	private JSlider sliderR, sliderG, sliderB;
	private JFileChooser chooser;
	public ControlPanel(Viewer v,MyBufferedImage i) {
		this.image=i;
		JButton button = new JButton("B/W");
		button.setBounds(35, 644, 100, 80);
		button.addActionListener( new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){	    	
		        result=v.applyBlackWhite();
		        v.setImg1(result);
		        v.repaint();
				try {
					Thread.sleep(100);
					v.repaint();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		    }
		});
		this.add(button);
		
		JButton grain = new JButton("GRAIN");
		grain.setBounds(145, 644, 100, 80);
		grain.addActionListener( new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){	    	
		        result=v.applyGrain();
		        v.setImg1(result);
		        v.repaint();
		    }
		});
		this.add(grain);
		
		JButton sharpen = new JButton("SHARPEN");
		sharpen.setBounds(255, 644, 100, 80);
		sharpen.addActionListener( new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){	    	
		       result=v.applySharpenning(valR,valG,valB);
		        v.setImg1(result);
		        v.repaint();
				try {
					Thread.sleep(100);
					v.repaint();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		    }
		});
		this.add(sharpen);
		
		
		JButton blur = new JButton("BLUR");
		blur.setBounds(365, 644, 100, 80);
		blur.addActionListener( new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){	    	
		        result=v.applyBlur(valR,valG,valB);
		        v.setImg1(result);
		        v.repaint();
		        result=v.applyBlur(valR,valG,valB);
		        v.setImg1(result);
				try {
					Thread.sleep(100);
					v.repaint();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		    }
		});
		this.add(blur);
		
		JPanel sliders= new JPanel();
		sliders.setLayout(new BoxLayout(sliders, BoxLayout.Y_AXIS));
		sliders.setBounds(505,644,200, 72);
		
		
		sliderR = new JSlider(-128, 128);
		sliderG = new JSlider(-128, 128);
		sliderB = new JSlider(-128, 128);
		
		sliderR.setBounds(0,0,200, 24);
		sliderG.setBounds(0,24,200, 24);
		sliderB.setBounds(0,48,200, 24);
		
		sliderR.setBackground(Color.RED);
		sliderG.setBackground(Color.GREEN);
		sliderB.setBackground(Color.BLUE);
		
		sliders.add(sliderR);
		sliders.add(sliderG);
		sliders.add(sliderB);
				
		this.add(sliders);
		
		JButton apply = new JButton("APPLY");
		apply.setBounds(735, 644, 100, 80);
		apply.addActionListener( new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){	    	
		    	valR = sliderR.getValue();
		    	valG = sliderG.getValue();
		    	valB = sliderB.getValue();
		    	result=v.applyColorFX(valR,valG,valB);
		        v.setImg1(result);
		        sliderR.setValue(0);
		        sliderG.setValue(0);
		        sliderB.setValue(0);
		        v.repaint();
				try {
					Thread.sleep(100);
					v.repaint();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		    }
		});
		this.add(apply);
		
		JButton resetb = new JButton("RESET");
		resetb.setBounds(845, 644, 100, 80);
		resetb.addActionListener( new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){	    	
		        result=v.reset();
		        v.setImg1(result);
		        sliderR.setValue(0);
		        sliderG.setValue(0);
		        sliderB.setValue(0);
		        v.repaint();	        
		    }
		});
		this.add(resetb);
		
		JButton loadbutton = new JButton("LOAD");
		loadbutton.setBounds(955, 644, 100, 80);
		loadbutton.addActionListener( new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){
		    	chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg", "jpeg");
			    chooser.setFileFilter(filter);
			    filter = new FileNameExtensionFilter("PNG Images", "png");
			    chooser.addChoosableFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			       System.out.println("File opened: "+chooser.getSelectedFile().getName());
			       try {
			    	   BufferedImage temp=ImageIO.read(chooser.getSelectedFile());
			    	   if(!temp.getColorModel().hasAlpha() && temp.getWidth()<6001) {
			    		   image=new MyBufferedImage(temp);	
			    		   v.setImg0(image);
			    		   v.calculateAspect();
			    		   v.reset();
			    		   result=v.applyColorFX(valR,valG,valB);
			    		   v.setImg1(result);
			    		   v.repaint();
			    	   }else {
			    		   System.out.println("Invalid image");
			    	   }
			       } catch (IOException e1) {
			    	   System.out.println("Invalid image");
			    	   System.out.println(e1.getMessage());
			       }
			    }
		    }
		});
		this.add(loadbutton);
		
		JButton savebutton = new JButton("SAVE");
		savebutton.setBounds(1065, 644, 100, 80);
		savebutton.addActionListener( new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e){
		    	chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG Images", "jpg");
			    chooser.setFileFilter(filter);
			    filter = new FileNameExtensionFilter("PNG Images", "png");
			    chooser.addChoosableFileFilter(filter);
			    if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			       System.out.println("Saving file: "+chooser.getSelectedFile().getName());
			       try {
			    	   ImageIO.write(result, "jpg", chooser.getSelectedFile());
			    	   v.repaint();
			       } catch (IOException e1) {
			    	   System.out.println("Invalid image");
			    	   System.out.println(e1.getMessage());
			       }
			    }
		    }
		});
		this.add(savebutton);
		
	}
	
	public void myPaint() {
		this.paint(this.getGraphics());
	}
}
