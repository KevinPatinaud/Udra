package BMP;
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

public class FrameBMP extends JFrame{

	PanelBMP panel;
	

	public FrameBMP(Udra image)
	{
		//par défaut le mode d'édition est désactivé
		initialise( image, false);
	}
	
	// At the creation we can choose to display or not the frame
	public FrameBMP(Udra image, boolean editMode)
	{
		initialise( image , editMode);
	}
	
	public void initialise(Udra image, boolean editMode)
	{
		setSize(image.sizeColumn() , image.sizeRow() + 30 );
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	
		// The panel is the graphical zone of the frame
		panel = new PanelBMP(image , editMode);
		setContentPane(panel);
	}
	
	
}
