package BMP;
import java.awt.AWTEvent;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;


import udra.Udra;



public class PanelBMP extends JPanel {


	private Udra image;
	private boolean editMode;
	private Point posStart = null;
	private Point posMouseActu = null;
	
	

	
    
	public PanelBMP(Udra image, boolean editMode) {
		super(); 
		
		this.image = image;
		this.editMode = editMode;
		
		
		
		
		addMouseListener(new MouseAdapter() {

		 	
		 	public void mouseClicked(MouseEvent e) {
		 		
		 		//Pour rogner si on clique gauche deux fois, on selectionne		 		
			 	if(e.getClickCount()==2 && e.getButton() == MouseEvent.BUTTON1)
			 	{
					//enregistre la dernièrre position de la sourie
					posMouseActu = new Point(e.getX() , e.getY());
					
					//appel la fonction de rognage
			 		rogner();
			 	}
			 	
			 	//si l'on droite une fois , on déselectionne tout
		 		 if( e.getButton() == MouseEvent.BUTTON3){
			 			posStart = null;
						repaint();
		 			 
		 	       }
		 	}
		 	});
		
		
		

		addKeyListener(new KeyListener()
				{
						 public void keyTyped(KeyEvent e) {			    }
						 
					    public void keyPressed(KeyEvent e) {					  
					    	
					    	//si l'on appuis sur la barre d'espace, on lance la fonction de rognage
					        if ( e.getKeyCode() == 32)
					        {
						 		rogner();
					        }
					    }
					    
					    public void keyReleased(KeyEvent e) {	    } 
					}
				);
		
		
		
		
	addMouseMotionListener(new MouseMotionListener() {
				
				
			public void mouseMoved(MouseEvent e) {
				//récupère le focus
				setFocusable(true);
				requestFocusInWindow(true);

				//enregistre la dernièrre position de la sourie
				posMouseActu = new Point(e.getX() , e.getY());
				
				//si le mode édition est renseigné, on redessine l'image à chaque mouvement
				if ( editMode)
				{
					repaint();
				}
			}

			public void mouseDragged(MouseEvent arg0) {		}
				
	});
	
		
	}
	
	
	
	
	
	
	private void rogner()
	{
		if ( posStart == null)
 		{
 			//au premier clique on enregistre la position
 			posStart = new Point((int)posMouseActu.getX() , (int)posMouseActu.getY());
 			posMouseActu = posStart;
 			repaint();
 		}
 		else
 		{
 			//au second on sauvegarde 
 			System.out.println( "Rogne\nx1 : " + (int) posStart.getX() + " \ny1 : " + (int) posStart.getY() + "\nx2 : " + (int)posMouseActu.getX() + "\ny2 : " + (int)posMouseActu.getY());
 			Udra imageRogne = image.bmpRogner((int) posStart.getX() , (int) posStart.getY() ,(int)posMouseActu.getX() , (int)posMouseActu.getY());
 			
 			String date = (new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")).format(new Date());
 			imageRogne.bmpWriteFile("image" + date + ".bmp");
 			
 			System.out.println("Image sauvegardee sous : " + System.getProperty("user.dir") + "\\image" + date + ".bmp");
 			
 			//et on réinitialise
 			posStart = null;
			repaint();
 		}
 			
	}
	
	
	
	
	
	
	
	
	//cette méthode dessinne le contenue de la fenètre
	  public void paintComponent(Graphics g) {

		//dessine le fond de la fenetre
	    g.setColor(Color.WHITE);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	     

	    //dessine l'image
	    for (int l = 0 ; l< image.sizeColumn() ; l++)
	    {
		    for (int h = 0 ; h< image.sizeRow() ; h++)
		    {
		    	try
		    	{
			    	g.setColor((Color) image.get(l, h));
			    	g.drawRect(l, h, 1, 1);
		    	}catch(Exception e) {}
		    }
	    }
	    
	    
	    
	    
	    //si le mode d'edition est activé déssine la selection
	    if ( editMode && posStart != null)
	    {
	    	g.setColor( Color.BLUE );
	    	g.drawRect( (int) posStart.getX() , (int) posStart.getY() , (int) ( posMouseActu.getX() -  posStart.getX()) , (int) ( posMouseActu.getY() - posStart.getY() ) );
	    }
	    
	    
	    
	  }
	  
	  
	  
	  
	  
	  


	
}
