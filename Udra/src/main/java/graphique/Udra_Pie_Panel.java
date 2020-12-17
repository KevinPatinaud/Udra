package graphique;
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
import java.util.ArrayList;

import javax.swing.JPanel;


import udra.Udra;
import udra.Udra;



public class Udra_Pie_Panel extends UdraPanel {
	
	//Parametres générique
	public static final int ColData = 0; 
	public static final int ColValue = 1; 
	public static final int ColColor = 2; 
	
	private Double valMaxY_define = null; 
	private double process = 0;
	
	
	public Udra_Pie_Panel() {
		super();
	}
	
	
	
	
	
	//cette méthode dessinne le contenue de la fenètre
	  public void paintComponent(Graphics g) {

		  
		//dessine le fond de la fenetre
	    g.setColor(Color.WHITE);
	    g.fillRect(0, 0, this.getWidth(), this.getHeight());
	     

		  if ( UdrasList.size() < 1 )
			  return ;
	    
		  //récupère le udra
	    Udra myUdra =  UdrasList.get(0);
    	
	    
	    
	    
	    

	    
	   //Affiche la légende
	    for (int i = 0 ; i < myUdra.sizeRow() ; i++)
	    {

	   // 	g.setColor(Color.BLACK);
	   // 	g.drawRect((int) (getWidth() * 0.81),  20 *  (i + 1) - 10, 10, 10);
	    	g.setColor((Color) myUdra.get(ColColor, i));
	    	g.fillRect((int) (getWidth() * 0.81),  20 * (i + 1) - 10, 10, 10);
	    	

		    g.setColor(Color.BLACK);
	    	g.drawString(myUdra.get(ColData, i).toString(), (int) (getWidth() * 0.81) + 15,  20 *  (i + 1));
	    	
	    }
		    
	    
	    //dessine
	    double largeurTotal = getWidth();
	    double hauteurTotal =  getHeight();
	    double largeur_legende = largeurTotal * 0.2;
	    double largeur_cercle = largeurTotal - largeur_legende - 20;
	    double hauteur_cercle = hauteurTotal - 20;
	    
	    //La zone dessin doit etre carré
	    if ( largeur_cercle > hauteur_cercle )
	    	largeur_cercle = hauteur_cercle;
	    else
	    	hauteur_cercle = largeur_cercle;
	    
	    
	    
	    //calcul de l'angle actuel
	    double angleActu =  360 * process;
	    
	    //calcul du total des valeurs
	    double total = 0;
	    
	    for (int i = 0 ; i < myUdra.sizeRow() ; i++)
	    	total += myUdra.getDbl(ColValue, i);
	    
	    
	    //dessinne l'ensemble des quartils inférieur
	    double index = 0;
	    double index_before = 0;
	    for (int i = 0 ; i < myUdra.sizeRow() ; i++)
	    {
	    	
		    g.setColor( (Color) myUdra.get(ColColor, i));
		    
		    index_before = index;
	    	index += myUdra.getDbl(ColValue, i);
	    	
	    	
	    	if ( index < total * process ) // le quartil est plein
	    	{
	    		
	    		
	    		//echelle : 360 / total des valeurs
	    		int angle_index_before = (int) ( index_before * 360 / total );
	    		int angleIndex = (int) ( ( index_before + myUdra.getDbl(ColValue, i ) ) * 360 / total );
	    	    g.fillArc(10, 10, (int)largeur_cercle, (int)hauteur_cercle, angleIndex + 90,  ( angle_index_before - angleIndex ) );

	    	   
	    	}
	    
	    	
	    	//dessine le quartil en cours
	    	else
	    	{
	    		int angle_index_before = (int) ( index_before * 360 / total );
	    		g.fillArc(10, 10, (int)largeur_cercle, (int)hauteur_cercle, angle_index_before + 90 ,  (int) ( angleActu - angle_index_before) );
	    		break;
	    	}
	    	
	    }

	    
		  generic_paintComponent(g);	    
	  }


	  
	  



	@Override
	public void refreshParametre() {
		// TODO Auto-generated method stub		
	}




	public void draw() {
		// affiche dynamique le graphique
		for (double i = 0 ; i < 1 ; i += 0.01)
		{
			process = i;
			  erase();
			
			//take a pause to let GUI drawed
			  try {		Thread.sleep(10);		} catch (InterruptedException e1) {		}
			  
		}
		
		process = 1;
		  erase();
		
	}







	
}
