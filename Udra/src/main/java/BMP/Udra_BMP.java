package BMP;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import udra.Udra;

public class Udra_BMP {

	
	//************************************ Capture l'écran *******************************************************
	
	public static void loadScreen(Udra udra_in)
	{
		udra_in.clear();
		
		try
		{
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage capture = new Robot().createScreenCapture(screenRect);

			for (int l = 0 ; l < capture.getWidth(); l ++)
				udra_in.insertAColumn("");
			
			for (int h = 0 ; h < capture.getHeight(); h ++)
				udra_in.insertALine();
			

			for (int l = 0 ; l < capture.getWidth(); l ++)
			{

				for (int h = 0 ; h < capture.getHeight(); h ++)
				{
					int clr=  capture.getRGB(l,h); 
					Color c = new Color( (clr & 0x00ff0000) >> 16 , (clr & 0x0000ff00) >> 8, clr & 0x000000ff );
					udra_in.setAvalue(l, h, c);
				}
			}
			
		}catch( Exception e) {}
		
	}
	
	

	//************************************* Lecture / ecriture fichier binaire ************************************************************

	private static  byte[] readBinaryFile(String aFileName) throws IOException {
		    Path path = Paths.get(aFileName);
		    return Files.readAllBytes(path);
		  }
	

	
	

	public static void loadFile(Udra udra_in , String destinationFile )
	{
		udra_in.clear();
		
		try {
			
			byte[] image = 	readBinaryFile(destinationFile);
			
			int offset = image[0xa] ;
			int larg = image[0x12] + image[0x13] * 256 + image[0x14] * 256 * 256 + image[0x15] * 256 * 256 * 256 ; 
			int haut = image[0x16] + image[0x17] * 256 + image[0x18] * 256 * 256 + image[0x19] * 256 * 256 * 256 ; 

			
			
			for (int l = 0 ; l< larg ;l++)
			{
				udra_in.insertAColumn("");
			}
			

			for (int h = 0 ; h < haut; h++)
			{
				udra_in.insertALine();
			}
			
			
			int bourage = 4 - (larg * 3) % 4;
			if ( bourage == 4) bourage = 0;
			
			for (int l = 0 ; l < larg ;l++)
			{
				for (int h = 0 ; h < haut ; h++)
				{	
					Color c = new Color( image[ offset + h * (larg * 3 + bourage )  + l * 3 + 2 ] & 0x000000ff ,  image[ offset + h * (larg * 3 + bourage )  + l * 3 + 1]  & 0x000000ff ,  image[ offset + h * (larg * 3 + bourage )  + l * 3 ] & 0x000000ff );
					udra_in.setAvalue(l, haut - h - 1, c);
				}
			}
			
			
			
			
		} catch (IOException e) {	e.printStackTrace();	}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	 static void writeBinaryFile(byte[] aBytes, String aFileName) throws IOException {
		    Path path = Paths.get(aFileName);
		    Files.write(path, aBytes); //creates, overwrites
		  }
		  
	 
	 
	 
	 

		

		public static void writeFile(Udra image , String destinationFile )
		{
			
			try {


				//Ecrit l'image
				int haut =  image.sizeRow();
				int larg =  image.sizeColumn() ;
				
				int bourage = 4 - (larg  * 3) % 4;
				if ( bourage == 4) bourage = 0;
				
				
				//initialise le fichier
				int tailleFichier = 54 + haut * larg * 3 + haut * bourage;
				byte[] fichier = new byte[tailleFichier ];

				for ( int i = 0 ; i < tailleFichier; i++)
					fichier[i] = 0;
				
				//BM
				fichier[0] = 0x42;
				fichier[1] = 0x4D;
				
				//offset
				int offset = 0x36;
				fichier[0x0a] = (byte) offset;

				//taille entete
				fichier[0x0e] = 0x28;
				
				//largeur OK
				fichier[0x12] = (byte) (image.sizeColumn() & 0x000000ff); 
				fichier[0x13] = (byte) ((image.sizeColumn() & 0x0000ff00) >> 8); 
				fichier[0x14] = (byte) ((image.sizeColumn() & 0x00ff0000) >> 16); 
				fichier[0x15] = (byte) ((image.sizeColumn() & 0xff000000) >> 24); 


				//hauteur OK
				fichier[0x16] = (byte) (image.sizeRow() & 0x000000ff); 
				fichier[0x17] = (byte) ((image.sizeRow() & 0x0000ff00) >> 8); 
				fichier[0x18] = (byte) ((image.sizeRow() & 0x00ff0000) >> 16); 
				fichier[0x19] = (byte) ((image.sizeRow() & 0xff000000) >> 24); 

				
				//nombre de plan
				fichier[0x1a] = 1;
				
				//profondeur
				fichier[0x1c] = 0x18;
				
				
				
				
				
				for (int l = 0 ; l < larg ;l++)
				{
					for (int h = 0 ; h < haut ; h++)
					{	
				//		Color c = new Color( image[ offset + h * (larg * 3 + bourage )  + l * 3 + 2 ] & 0x000000ff ,  image[ offset + h * (larg * 3 + bourage )  + l * 3 + 1]  & 0x000000ff ,  image[ offset + h * (larg * 3 + bourage )  + l * 3 ] & 0x000000ff );
				//		udra_in.setAvalue(l, haut - h - 1, c);
						
						Color c = (Color) image.get(l, haut - h - 1);
						fichier[ offset + h * (larg * 3 + bourage )  + l * 3 + 2 ] = (byte) (c.getRed() & 0x000000ff) ;
						fichier[ offset + h * (larg * 3 + bourage )  + l * 3 + 1 ] = (byte) (c.getGreen() & 0x000000ff) ;
						fichier[ offset + h * (larg * 3 + bourage )  + l * 3 ] = (byte) (c.getBlue() & 0x000000ff) ;
					}
				}
				
				
				writeBinaryFile( fichier , destinationFile);
				
			} catch (Exception e) {	e.printStackTrace();	}
		}

		
		
//************************************* travail l'image *********************************************************************************		
		
		public static void bmpCompress(Udra image, int compressLevel)
		{
			for (int x = image.sizeColumn() - 1; x >= 0; x -= compressLevel)
			{
				image.deleteColumn(x);
			}
			

			for (int y = image.sizeRow() - 1; y >= 0; y -= compressLevel)
			{
				image.delete_row(y);
			}
		}
	
	
		
		
		


		public static Udra rogner(Udra image, int xmin, int ymin, int xmax, int ymax) {
			Udra imageRogne = Udra.copyFrom(image);

			for (int x = imageRogne.sizeColumn() - 1; x > xmax; x-- )
			{
				imageRogne.deleteColumn( x );
			}


			for (int x = xmin; x >= 0; x-- )
			{
				imageRogne.deleteColumn( x );
			}

			for (int y = imageRogne.sizeRow() - 1; y > ymax; y-- )
			{
				imageRogne.delete_row( y );
			}


			for (int y = ymin; y >= 0; y-- )
			{
				imageRogne.delete_row( y );
			}
			
			return imageRogne;
		}
	
		
//************************************** Recherche un élément sur l'image ******************************************************************
	
	/*
	 	cherche l'élément passé en paramètre (smallPict) sur une grande image (BigPict)
	 	Cette fonction remonte les positions de toutes les occurence où l'image a été identifiée
	 	
	 		Udra bureau = new Udra();
			bureau.bmpLoadScreen();
		
			Udra logo = new Udra();
			logo.bmpLoadFile("logo.bmp");
		
			Udra logosTrouves = logo.bmpSearchOn(bureau);
			
			On peut également spécifier la zone de recherche
			
		*/
		
	public static Udra searchOn(Udra smallPict, Udra BigPict, int xMin, int yMin, int larg , int haut )
	{

		Udra positionsTrouvée = new Udra ("X" , "Y");
		
		//on parcours toute la grande image
		for (int xB = xMin ; xB < xMin + larg &&  xB < BigPict.sizeColumn() - smallPict.sizeColumn(); xB++)
		{
			for (int yB = yMin ; yB < yMin + haut && yB < BigPict.sizeRow() - smallPict.sizeRow(); yB++)
			{
				
				boolean trouve = true;
				
				//parcours la petite image, dès qu'un pixel ne correspond pas passe au pixel suivant
				for (int xS = 0 ; xS < smallPict.sizeColumn() && trouve ; xS++)
				{
					for (int yS = 0 ; yS < smallPict.sizeRow() && trouve; yS++)
					{

						Color cBig = ( (Color)BigPict.get(xB + xS, yB +yS) );
						Color cSmall = ( (Color)smallPict.get(xS, yS) );
						
						if (cBig.getBlue() != cSmall.getBlue()	|| cBig.getRed() != cSmall.getRed()	|| cBig.getGreen() != cSmall.getGreen()	)
						{
							trouve = false;
						}
						
					}
				}
				
				//enregistre les positions où a été trouvé l'image
				if ( trouve )
				{
					positionsTrouvée.insertALine(xB , yB);
				}
				
				
			}
		}
		
		return positionsTrouvée;
	}


	


	//Le taux de correponsdance est le taux minimal pour le quel une image est identifiée
	//0.9 pour que l'image ai 92% de correspondance au minimum (uniquement basé sur le nombre de pixel identique)
	
	
public static Udra searchOnApproximation(Udra smallPict, Udra BigPict, double tauxCorrespondanceNecessaire, int xMin, int yMin, int larg , int haut )
{

	Udra positionsTrouvée = new Udra ("X" , "Y");
	int nmbPixelErreurMax = (int) ((double) smallPict.sizeColumn() * (double)smallPict.sizeRow() * ( 1 - tauxCorrespondanceNecessaire));
	
	
	
	//on parcours toute la grande image
	for (int xB = xMin ; xB < xMin + larg &&  xB < BigPict.sizeColumn() - smallPict.sizeColumn(); xB++)
	{
		for (int yB = yMin ; yB < yMin + haut && yB < BigPict.sizeRow() - smallPict.sizeRow(); yB++)
		{
			
			boolean trouve = true;
			int nmbPixelErreur = 0;
			
			//parcours la petite image, dès que le taux de correspondance est passé on passe au pixel suivant
			for (int xS = 0 ; xS < smallPict.sizeColumn() && trouve ; xS++)
			{
				for (int yS = 0 ; yS < smallPict.sizeRow() && trouve; yS++)
				{

					Color cBig = ( (Color)BigPict.get(xB + xS, yB +yS) );
					Color cSmall = ( (Color)smallPict.get(xS, yS) );
					
					if (cBig.getBlue() != cSmall.getBlue()	|| cBig.getRed() != cSmall.getRed()	|| cBig.getGreen() != cSmall.getGreen()	)
					{
						nmbPixelErreur++;
					}
					
					if ( nmbPixelErreur > nmbPixelErreurMax)
					{
						trouve = false;
					}
				}
			}
			
			//enregistre les positions où a été trouvé l'image
			if ( trouve )
			{
				positionsTrouvée.insertALine(xB , yB);
			}
			
			
		}
	}
	
	return positionsTrouvée;
}

	
	
	
	//********************************************** Dessine sur l'image *******************************************************************

	public static void drawRect( Udra image , int x1 , int y1 , int x2 , int y2 , Color color , int borderSize)
	{
		
		for (int x = x1 ; x < x2  + borderSize; x++)
		{
			for (int s = 0 ; s < borderSize ; s++)
				image.setAvalue(x, y1 - s, color);
			

			for (int s = 0 ; s < borderSize ; s++)
				image.setAvalue(x, y2 + s, color);
		}

		
		for (int y = y1 ; y < y2 + borderSize ; y++)
		{

			for (int s = 0 ; s < borderSize ; s++)
				image.setAvalue(x1 - s, y, color);
			

			for (int s = 0 ; s < borderSize ; s++)
				image.setAvalue(x2 + s, y, color);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	//********************** Modification des couleurs de l'image ****************************************************

	
	public static Udra convertToBlancExtra(Udra imgOriginale)
	{
		for (int x = 0 ; x < imgOriginale.sizeColumn() ; x++)
		{
			for (int y = 0 ; y < imgOriginale.sizeRow() ; y++)
			{
				Color color = (Color) imgOriginale.get(x, y);
				
				if (  color.getBlue() > 125 || color.getGreen() > 125 || color.getRed() > 125 )
					imgOriginale.setAvalue(x, y, new Color( 255 , 255 , 255 ) );
				else
					imgOriginale.setAvalue(x, y, new Color( 0 , 0 , 0 ) );
			}
		}
		
		
		return imgOriginale;
	}
	

	
	public static Udra convertToNoiretBlanc(Udra imgOriginale)
	{
		for (int x = 0 ; x < imgOriginale.sizeColumn() ; x++)
		{
			for (int y = 0 ; y < imgOriginale.sizeRow() ; y++)
			{
				Color color = (Color) imgOriginale.get(x, y);
				
				if ( ( color.getBlue() + color.getGreen()  + color.getRed() ) / 3 > 125 )
					imgOriginale.setAvalue(x, y, new Color( 255 , 255 , 255 ) );
				else
					imgOriginale.setAvalue(x, y, new Color( 0 , 0 , 0 ) );
			}
		}
		
		
		return imgOriginale;
	}



	

	
	public static Udra convertToColorExtra(Udra imgOriginale)
	{
		for (int x = 0 ; x < imgOriginale.sizeColumn() ; x++)
		{
			for (int y = 0 ; y < imgOriginale.sizeRow() ; y++)
			{
				Color color = (Color) imgOriginale.get(x, y);
				
				int red = ( color.getRed() > 125 ) ? 255 : 0;
				int blue = ( color.getBlue() > 125 ) ? 255 : 0;
				int green = ( color.getGreen() > 125 ) ? 255 : 0;
				
				Color newColor = new Color ( red , green , blue );
				
				
				imgOriginale.setAvalue(x, y, newColor );

			}
		}
		
		
		
		return imgOriginale;
	}


	
	
	
	
	
	
	
	
	
}
