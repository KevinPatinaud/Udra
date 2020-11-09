package graphique;

import java.awt.Color;

import java.awt.Font;

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

public abstract class DrawingUdra extends JFrame {

	public UdraPanel Panel = null;

	protected boolean visible;

	// At the creation we can choose to display or not the frame
	public DrawingUdra()
	{
		visible = true;
		Initialize();
	}

	public DrawingUdra(boolean display)
	{
		visible = display;
		Initialize();
	}

	protected abstract void Initialize();

	public void addButton(int x, int y, String type_position, String nom, Runnable rn)
	{
		Panel.addButton(x, y, type_position, nom, rn);
	}

	public void addText(int x, int y, String type_position, String text)
	{
		Panel.addText(x, y, type_position, text);
	}

	public void addText(int x, int y, String type_position, String text, Color c)
	{
		Panel.addText(x, y, type_position, text, c);
	}

	public void addText(int x, int y, String type_position, String text, Color c, Font f)
	{
		Panel.addText(x, y, type_position, text, c, f);
	}

	
	// Save the frame into a JPG file
	public void SaveAsJPG(String FileName, boolean closeTheFrame)
	{
		// take a pause to let GUI drawed
		try {

			Thread.sleep(1000);

		} catch (InterruptedException e1) {
		}

		// code to transforme the Jpanel into image

		BufferedImage bI = new BufferedImage(Panel.getWidth(), Panel.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);

		Panel.paint(bI.getGraphics());

		try {

			File f = new File(FileName + ".jpg");

			FileOutputStream fichier = new FileOutputStream(f);

			ImageIO.write(bI, "jpg", fichier);

			fichier.close();

		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		// close the frame

		if (closeTheFrame)

			this.dispose();

	}

	// research a specific String into an array

	protected int find(ArrayList<String> data, String occurence)

	{

		int result = -1;

		for (int i = 0; i < data.size(); i++)

		{

			if (data.get(i).equals(occurence))

				result = i;

		}

		return result;

	}

	// reorganise the new udra to take the good form
	public static Udra TakeOnly(Udra UdraIn, int... NumColumn)
	{

		// the number of column is must to be 3 : X , value and constraint
		Udra UdraOut = new Udra();

		// copy the incomming udra column after column
		int ColumnActu = 0;

		for (int column : NumColumn)
		{

			// if the column is defined
			if (column != -1)
			{

				UdraOut.insertAColumn(UdraIn.getTitle().get(column).toString(), "");

				// if it's the first column so we insert a new line for each new data
				for (int i = 0; i < UdraIn.sizeRow(); i++)
				{

					if (i >= UdraOut.sizeRow() - 1)
						UdraOut.insertALine();

					UdraOut.setAvalue(ColumnActu, i, UdraIn.get(column, i));
				}
			}

			else // the column isn't define we add an empty data
			{

				UdraOut.insertAColumn(String.valueOf(ColumnActu), "");

			}

			ColumnActu++;

		}

		// clean le udra de sortie

		UdraOut.deleteEmptyRow();

		return UdraOut;

	}

	public void setIcon(String pathToFileOnDisk)
	{

		ImageIcon img = new ImageIcon(pathToFileOnDisk);

		setIconImage(img.getImage());

	}

	public void setRelatif(boolean val)
	{
		Panel.setRelatif(val);
	}

	
	public void setTransparenceBoiteMessageprct(int transparenceBoiteMessage) {
		((Udra_Line_Panel)	Panel).setTransparenceBoiteMessageprct(transparenceBoiteMessage);
	}

}
