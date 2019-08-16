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



// Cette classe représente l'ensemble de la fenetre

public class Drawing_Pie_Udra extends DrawingUdra{

	
	// At the creation we can choose to display or not the frame
	public Drawing_Pie_Udra()
	{
		super();
	}
	
	public Drawing_Pie_Udra(boolean display)
	{
		super( display );
	}
	
	protected void Initialize()
	{
		setSize(800 , 500 );
		setLocationRelativeTo(null);
		setVisible(visible);
	
		// The panel is the graphical zone of the frame
		Panel = new Udra_Pie_Panel();
		setContentPane(Panel);
	}
	
	
	
	
	


	
	//add a udra to the frame, without argument we consider the udra respect the normalization
	public void setUdra(Udra udraIn )
	{
		setUdra( udraIn , Udra_Pie_Panel.ColData ,  Udra_Pie_Panel.ColValue, Udra_Pie_Panel.ColColor  );
	}
	
	
	public void setUdra( Udra udraIn , int col_data , int col_value )
	{
		
		Udra in = Udra.copyFrom(udraIn);
		in =  TakeOnly(in , col_data , col_value ) ;
		in.insertAColumn("Color");
		in.setName(udraIn.getName());
		

		
		for (int i = 0 ; i < in.sizeRow() ; i++)
		{
			in.setAvalue(Udra_Pie_Panel.ColColor, i, new Color((int) ( Math.random() * 255 ), (int) ( Math.random() * 255 ), (int) ( Math.random() * 255 )));
		}
		
		setUdra( in , Udra_Pie_Panel.ColData ,  Udra_Pie_Panel.ColValue, Udra_Pie_Panel.ColColor  );
	}

	
	
	public void setUdra( Udra udraIn , int col_data , int col_value , int col_color)
	{
		Panel.setUdra(  TakeOnly(udraIn , col_data , col_value , col_color )  );
		setTitle(udraIn.getName());
		((Udra_Pie_Panel) Panel).draw();
	}

	
	
	
	

}
