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

public class Drawing_Line_Udra extends DrawingUdra {

	// Pour accélerer la vitesse des calculs on utiles directement l'emplamcement
	// des colonne sans passer par leur nom

	public static final int ColValue = 0;
	public static final int ColData = 1;
	public static final int ColColor = 2;
	public static final int colValueDisplay = 3;
	public static final String colValueDisplayStr = "VALUE_DISPLAY_DRAWING_LINE";

	// At the creation we can choose to display or not the frame

	public Drawing_Line_Udra()
	{
		super();
	}

	public Drawing_Line_Udra(boolean display)
	{
		super(display);
	}

	protected void Initialize()
	{
		setSize(800, 500);
		setLocationRelativeTo(null);
		setVisible(visible);

		// The panel is the graphical zone of the frame
		Panel = new Udra_Line_Panel();
		setContentPane(Panel);
		Panel.erase();
	}

	
	public Drawing_Line_Udra add(Udra in)
	{
		add(in, Udra_Line_Panel.ColValue, Udra_Line_Panel.ColData, Udra_Line_Panel.ColColor);
		return this;
	}

	
	
	public Drawing_Line_Udra add(Udra in, String colData, String colValue)
	{

		return add(in, in.get_the_index_of_title_from_his_Name(colData),
				in.get_the_index_of_title_from_his_Name(colValue));

	}

	public Drawing_Line_Udra add(Udra in, int colData, int colValue)

	{

		Udra nvUdra = Udra.copyFrom(in);

		nvUdra.insertAColumn("Color", Color.BLACK);

		add(nvUdra, colData, colValue, nvUdra.sizeColumn() - 1);

		return this;

	}

	public Drawing_Line_Udra add(Udra in, String colData, String colValue, Color colColor)
	{

		Udra nouveau = in.copyFrom(in);

		nouveau.insertAColumn("The color", colColor);

		return add(nouveau, nouveau.get_the_index_of_title_from_his_Name(colData),
				nouveau.get_the_index_of_title_from_his_Name(colValue), nouveau.sizeColumn() - 1);

	}

	public Drawing_Line_Udra add(Udra in, String colData, String colValue, String colColor)

	{

		return add(in, in.get_the_index_of_title_from_his_Name(colData),
				in.get_the_index_of_title_from_his_Name(colValue), in.get_the_index_of_title_from_his_Name(colColor));

	}

	public Drawing_Line_Udra add(Udra in, int colData, int colValue, int colColor)
	{
		Udra nvUdra = Udra.copyFrom(in);

		nvUdra.insertAColumn(colValueDisplayStr); // cette colonne indique la valeur à afficher (utile pour l'echelle logarithmique)

		// transforme les valeurs qui ne sont des nombres en mode invisible

		for (int i = 0; i < nvUdra.sizeRow(); i++)
		{

			if (!nvUdra.isNumber(colValue, i))
			{
				nvUdra.setAvalue(colData, i, "Don't display");
				nvUdra.setAvalue(colColor, i, Color.WHITE);
			}

			nvUdra.setAvalue(colValueDisplayStr, i, nvUdra.get(colValue, i));
		}


		nvUdra = TakeOnly(nvUdra, colValue, colData, colColor,	nvUdra.get_the_index_of_title_from_his_Name(colValueDisplayStr));

		Panel.setUdra(nvUdra);
		Panel.erase();
		return this;
	}

	public Drawing_Line_Udra setLogarithme(boolean logActif)
	{

		((Udra_Line_Panel) Panel).setLogarithme(logActif);

		Panel.erase();

		return this;

	}

	// add a Udra to the frame, without argument we consider the Udra respect the
	// normalization

	@Deprecated

	public void setUdra(Udra UdraIn)

	{

		setUdra(UdraIn, Udra_Line_Panel.ColData, Udra_Line_Panel.ColValue, Udra_Line_Panel.ColColor);

	}

	// add a Udra to the frame, without argument we consider the Udra respect the
	// normalization

	@Deprecated

	public void setUdra(Udra UdraIn, Color... Listcolors)

	{

		setUdra(UdraIn, Udra_Line_Panel.ColData, Udra_Line_Panel.ColValue, Udra_Line_Panel.ColColor, Listcolors);

	}

	// add a Udra to the frame
	@Deprecated
	public void setUdra(Udra UdraIn, String colValue)
	{

		setUdra(UdraIn, UdraIn.get_the_index_of_title_from_his_Name(colValue));

	}

	@Deprecated
	public void setUdra(Udra UdraIn, int colValue)
	{

		setUdra(UdraIn, colValue, -1);

	}

	// add a Udra to the frame
	@Deprecated
	public void setUdra(Udra UdraIn, String colValue, String colInfo)
	{

		setUdra(UdraIn, UdraIn.get_the_index_of_title_from_his_Name(colValue),
				UdraIn.get_the_index_of_title_from_his_Name(colInfo));

	}

	@Deprecated
	public void setUdra(Udra UdraIn, int colValue, int colInfo)
	{
		setUdra(UdraIn, colValue, colInfo, -1);
	}

	// add a Udra to the frame
	@Deprecated
	public void setUdra(Udra UdraIn, String colValue, String colInfo, Color color)
	{

		setUdra(UdraIn, UdraIn.get_the_index_of_title_from_his_Name(colValue),
				UdraIn.get_the_index_of_title_from_his_Name(colInfo), color);

	}

	@Deprecated
	public void setUdra(Udra UdraIn, int colValue, int colInfo, Color color)
	{

		// reorganise the new Udra

		Udra nvUdra = Udra.copyFrom(TakeOnly(UdraIn, colValue, colInfo));

		nvUdra.insertAColumn("color", Color.black);

		// parcours le nouveau Udra pour définir les couleurs

		for (int i = 0; i < nvUdra.sizeRow(); i++)

		{

			nvUdra.setAvalue(Udra_Line_Panel.ColColor, i, color);

		}

		Panel.setUdra(nvUdra);

		Panel.erase();

	}

	// add a Udra to the frame

	@Deprecated

	public void setUdra(Udra UdraIn, String colValue, String colInfo, String colConstrainte, Color... Listcolors)

	{

		setUdra(UdraIn, UdraIn.get_the_index_of_title_from_his_Name(colValue),
				UdraIn.get_the_index_of_title_from_his_Name(colInfo),
				UdraIn.get_the_index_of_title_from_his_Name(colConstrainte), Listcolors);

	}

	@Deprecated

	public void setUdra(Udra UdraIn, int colValue, int colInfo, int colConstrainte, Color... Listcolors)

	{

		// reorganise the new Udra

		Udra nvUdra = Udra.copyFrom(TakeOnly(UdraIn, colValue, colInfo, colConstrainte));

		ArrayList<String> ContraintesConnues = new ArrayList<String>();

		for (int i = 0; i < nvUdra.sizeRow(); i++)

		{

			// If the constraint isn't already know

			if (find(ContraintesConnues, nvUdra.get(Udra_Line_Panel.ColColor, i).toString()) == -1)

			{

				// We add to the constraint list

				ContraintesConnues.add(nvUdra.get(Udra_Line_Panel.ColColor, i).toString());

			}

			// replace the constraint by her color

			if (find(ContraintesConnues, nvUdra.get(Udra_Line_Panel.ColColor, i).toString()) < Listcolors.length)

			{

				nvUdra.setAvalue(Udra_Line_Panel.ColColor, i,
						Listcolors[find(ContraintesConnues, nvUdra.get(Udra_Line_Panel.ColColor, i).toString())]);

			}

			else

				nvUdra.setAvalue(Udra_Line_Panel.ColColor, i, Color.black);

		}

		Panel.setUdra(nvUdra);

		Panel.erase();

	}

	@Deprecated

	public void setUdra2(Udra UdraIn, String colValue, String colInfo, String colConstrainte,
			ContrainteColor... ListContrainte)

	{

		setUdra2(UdraIn, UdraIn.get_the_index_of_title_from_his_Name(colValue),
				UdraIn.get_the_index_of_title_from_his_Name(colInfo),
				UdraIn.get_the_index_of_title_from_his_Name(colConstrainte), ListContrainte);

	}

	@Deprecated

	public void setUdra2(Udra UdraIn, int colValue, int colInfo, int colConstrainte, ContrainteColor... ListContrainte)

	{

		// reorganise the new Udra

		Udra nvUdra = Udra.copyFrom(TakeOnly(UdraIn, colValue, colInfo, colConstrainte));

		// parcours le nouveau Udra pour définir les couleurs

		for (int i = 0; i < nvUdra.sizeRow(); i++)
		{

			// parcours les contraintes
			for (int it_contrainte = 0; it_contrainte < ListContrainte.length; it_contrainte++)
			{

				// compare la contrainte du Udra à notre liste de contraintes
				if (nvUdra.get(Udra_Line_Panel.ColColor, i).toString().equals(ListContrainte[it_contrainte].contrainte))

				{

					nvUdra.setAvalue(Udra_Line_Panel.ColColor, i, ListContrainte[it_contrainte].color);

				}

			}

		}

		// control qu'il n'y ai plus de string dans la colonne des couleurs
		for (int i = 0; i < nvUdra.sizeRow(); i++)
		{

			if (nvUdra.get(Udra_Line_Panel.ColColor, i) instanceof String)
				nvUdra.setAvalue(Udra_Line_Panel.ColColor, i, Color.BLACK);

		}

		Panel.setUdra(nvUdra);

		Panel.erase();

	}

	


	
	
}
