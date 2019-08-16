package graphique;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import udra.Udra;
import udra.udra_value;





// Cette classe représente l'ensemble de la fenetre

public class Drawing_Relief_Map extends JFrame{

	private Udra_Relief_Panel panel;
	private String colorMinValue ;
	private String colorMaxValue ;
	
	
	// At the creation we can choose to display or not the frame
	public Drawing_Relief_Map(Udra udraToDraw , String colorMinValueIn , String colorMaxValueIn )
	{

		setSize(800 , 500 );
		setLocationRelativeTo(null);
		setVisible(true);
		
		colorMinValue = colorMinValueIn;
		colorMaxValue = colorMaxValueIn;
		
		// The panel is the graphical zone of the frame
		Udra colorMap =  calculColorMapping(udraToDraw, colorMinValue , colorMaxValue);
		
		panel = new Udra_Relief_Panel( udraToDraw , colorMap);
		setContentPane(panel);
		
	}
	
	
	
	
	
	public static Udra calculColorMapping( Udra udraToConvert , String colorMinValue , String colorMaxValue)
	{
		Udra listOfValue = new Udra ("Values");
		
		//Get the value each case of udra to convert
		for (int x = 1 ; x < udraToConvert.sizeColumn() ; x ++ )
		{
			for (int y = 0 ; y < udraToConvert.sizeRow() ; y++)
			{
				listOfValue.insertALine(udraToConvert.getDbl(x, y));
			}
		}
		
		//Insure the unicity of each value
		listOfValue.delete_duplicate_row();
		
		//order the values
		listOfValue.orderAscBy(0);
		
		//Calcul the color for each value
		colorMinValue = colorMinValue.replace("#", "");
		double RedValMin = Integer.parseInt(String.valueOf( colorMinValue.charAt(0) ) +  String.valueOf(colorMinValue.charAt(1)),16);  
		double GreenValMin = Integer.parseInt(String.valueOf( colorMinValue.charAt(2) ) +  String.valueOf(colorMinValue.charAt(3)),16);  
		double BlueValMin = Integer.parseInt(String.valueOf( colorMinValue.charAt(4) ) +  String.valueOf(colorMinValue.charAt(5)),16);  


		colorMaxValue = colorMaxValue.replace("#", "");
		double RedValMax = Integer.parseInt(String.valueOf( colorMaxValue.charAt(0) ) +  String.valueOf(colorMaxValue.charAt(1)),16);  
		double GreenValMax = Integer.parseInt(String.valueOf( colorMaxValue.charAt(2) ) +  String.valueOf(colorMaxValue.charAt(3)),16);  
		double BlueValMax = Integer.parseInt(String.valueOf( colorMaxValue.charAt(4) ) +  String.valueOf(colorMaxValue.charAt(5)),16);
		
		listOfValue.insertAColumn("COLOR");
		
		for (int i = 0 ; i < listOfValue.sizeRow() ; i++)
		{
			double valMin = listOfValue.getDbl(0, 0);
			double valMax = listOfValue.getDbl(0, listOfValue.sizeRow() - 1);
			
			 int codeRed = 0;
			 if ( RedValMin < RedValMax)
				 codeRed = (int) ( Math.abs( RedValMin - RedValMax) * (( listOfValue.getDbl(0, i) - valMin) / (valMax - valMin)) + RedValMin );
			 else
				 codeRed = (int) ( Math.abs( RedValMin - RedValMax) * ( 1 - (( listOfValue.getDbl(0, i) - valMin) / (valMax - valMin))) + RedValMax );

			 

			 int codeGreen = 0;
			 if ( GreenValMin < GreenValMax)
				 codeGreen = (int) ( Math.abs( GreenValMin - GreenValMax) * ( ( listOfValue.getDbl(0, i) - valMin) / (valMax - valMin)) + GreenValMin );
			 else
				 codeGreen = (int) ( Math.abs( GreenValMin - GreenValMax) * ( 1 - (( ( listOfValue.getDbl(0, i) - valMin) / (valMax - valMin)))) + GreenValMax );
			 

			 

			 int codeBlue = 0;
			 if ( BlueValMin < BlueValMax)
				 codeBlue = (int) ( Math.abs( BlueValMin - BlueValMax) * ( ( listOfValue.getDbl(0, i) - valMin) / (valMax - valMin)) + BlueValMin );
			 else
				 codeBlue = (int) ( Math.abs( BlueValMin - BlueValMax) * ( 1 - ( ( listOfValue.getDbl(0, i) - valMin) / (valMax - valMin))) + BlueValMax );
			 

			 
			 listOfValue.setAvalue("COLOR", i,  new Color( codeRed, codeGreen, codeBlue));

		}
		
		

		//Set the color of each case in fonction of is value
		Udra mappingResult = new Udra();
		
		for (int x = 1 ; x < udraToConvert.sizeColumn() ; x ++ )
		{
			mappingResult.insertAColumn( udraToConvert.getTitle().get(x) );
			
			for (int y = 0 ; y < udraToConvert.sizeRow() ; y++)
			{
				//only at the first we add a new line
				if ( x == 1)
				{
					mappingResult.insertALine();
				}
				
				double val = udraToConvert.getDbl(x, y);
				
				//find the color of the case
				for (int iColor = 0 ; iColor < listOfValue.sizeRow() ; iColor++)
				{
					if ( listOfValue.getDbl("Values", iColor) == val )
					{
						mappingResult.setAvalue(x - 1, y, listOfValue.get("COLOR", iColor) );
					}
				}
				
			}
		}
				
		
		return mappingResult;
	}
	
}
