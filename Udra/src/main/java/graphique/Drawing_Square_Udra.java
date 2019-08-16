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

public class Drawing_Square_Udra extends DrawingUdra{

	
	// At the creation we can choose to display or not the frame
	public Drawing_Square_Udra()
	{
		super();
	}
	
	public Drawing_Square_Udra(boolean display)
	{
		super( display );
	}
	
	protected void Initialize()
	{
		setSize(800 , 500 );
		setLocationRelativeTo(null);
		setVisible(visible);
	
		// The panel is the graphical zone of the frame
		Panel = new Udra_Square_Panel();
		setContentPane(Panel);
	}
	
	
	
	
	


	
	//add a Udra to the frame, without argument we consider the Udra respect the normalization
	public void setUdra(Udra UdraIn )
	{
		setUdra( UdraIn , Udra_Square_Panel.ColData ,  Udra_Square_Panel.ColValue, Udra_Square_Panel.ColColor  );
	}
	
	
	public void setUdra( Udra UdraIn , int col_data , int col_value )
	{
		
		Udra in = Udra.copyFrom(UdraIn);
		in =  TakeOnly(in , col_data , col_value ) ;
		in.insertAColumn("Color");
		in.setName(UdraIn.getName());
		

		
		for (int i = 0 ; i < in.sizeRow() ; i++)
		{
			in.setAvalue(Udra_Square_Panel.ColColor, i, Color.LIGHT_GRAY);
		}
		
		setUdra( in , Udra_Square_Panel.ColData ,  Udra_Square_Panel.ColValue, Udra_Square_Panel.ColColor  );
	}

	
	
	public void setUdra( Udra UdraIn , int col_data , int col_value , int col_color)
	{
		Panel.setUdra(  TakeOnly(UdraIn , col_data , col_value , col_color )  );
		Panel.erase();
		setTitle(UdraIn.getName());
	}

	
	
	
	public void set_valMaxY( Double val)
	{
		((Udra_Square_Panel) Panel).set_valMaxY_define(val);
	}
	

}
