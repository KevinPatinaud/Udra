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

public class Udra_Line_Panel extends UdraPanel {

	// Parametres générique

	// Pour accélerer la vitesse des calculs on utiles directement l'emplamcement
	// des colonne sans passer par leur nom

	public static final int ColValue = 0;

	public static final int ColData = 1;

	public static final int ColColor = 2;

	public static final int colValueDisplay = 3;

	public Udra_Line_Panel() {

		super();

	}

	// permet de forcer un niveau de zoom depuis l'extérieur

	public void forcerZoom(double nouvelleValeur)

	{

		decalX = 100000;

		zoom = nouvelleValeur;

		controlDecal();

		erase();

		// centre sur la dernière valeur Y

		double dernier_point = ((Udra) UdrasList.get(0)).getDbl(ColValue, ((Udra) UdrasList.get(0)).sizeRow() - 1);

		decalY = (int) -(((dernier_point - ValueMinY) * EchelleY) - getHeight() / 2);

		controlDecal();

		erase();

	}

	// cette méthode dessinne le contenue de la fenètre

	public void paintComponent(Graphics g) {

		refreshParametre();

		// dessine le fond de la fenetre

		g.setColor(Color.white);

		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// dessinne la courbe

		g.setColor(Color.black);

		for (int itUdra = 0; itUdra < UdrasList.size(); itUdra++)

		{

			Udra UdraActu = (Udra) UdrasList.get(itUdra);

			double EchelleX = GetEchelleX(UdraActu); // à calculer pour chaque Udra

			// Ne charge uniquement que les points présents à l'écran

			int XDepCrb = (int) (decalX / EchelleX);

			int XFinCrb = (int) ((decalX + getWidth()) / EchelleX) + 1;

			XFinCrb = (XFinCrb > UdraActu.sizeRow() - 1) ? UdraActu.sizeRow() - 1 : XFinCrb;

			for (int i = XDepCrb; i < XFinCrb; i++)

			{

				g.setColor((Color) (UdraActu.get(ColColor, i)));

				if (UdraActu.isNumber(ColValue, i) && UdraActu.isNumber(ColValue, i + 1))// control que les données ne
																							// soit pas null

				{

					g.drawLine((int) (i * EchelleX) - decalX,
							get_Pos_On_Screen_From_Y_value(UdraActu.getDbl(ColValue, i)),
							(int) ((i + 1) * EchelleX) - decalX,
							get_Pos_On_Screen_From_Y_value(UdraActu.getDbl(ColValue, i + 1)));

					if (EchelleX > 10) // affiche les intersections de droites si le zoom est suffisant

					{

						g.fillOval((int) (((i) * EchelleX) - decalX) - 3,
								get_Pos_On_Screen_From_Y_value(UdraActu.getDbl(ColValue, i)) - 3, 6, 6);

						g.fillOval((int) (((i + 1) * EchelleX) - decalX) - 3,
								get_Pos_On_Screen_From_Y_value(UdraActu.getDbl(ColValue, i + 1)) - 3, 6, 6);

					}

				}

				// si il s'agit d'un multi nombre (courbe discontinue)

				if (UdraActu.isMultiNumber(ColValue, i) && UdraActu.isMultiNumber(ColValue, i + 1))

				{

					g.drawLine((int) (i * EchelleX) - decalX,
							get_Pos_On_Screen_From_Y_value(UdraActu.getDblFromMulti(ColValue, i, 1)),
							(int) ((i + 1) * EchelleX) - decalX,
							get_Pos_On_Screen_From_Y_value(UdraActu.getDblFromMulti(ColValue, i + 1, 0)));

					if (EchelleX > 10) // affiche les intersections de droites si le zoom est suffisant

					{

						g.fillOval((int) (((i) * EchelleX) - decalX) - 3,
								get_Pos_On_Screen_From_Y_value(UdraActu.getDblFromMulti(ColValue, i, 1)) - 3, 6, 6);

						g.fillOval((int) (((i + 1) * EchelleX) - decalX) - 3,
								get_Pos_On_Screen_From_Y_value(UdraActu.getDblFromMulti(ColValue, i + 1, 0)) - 3, 6, 6);

					}

				}

			}

		}

		// Affiche les infos du curseur

		if (MousePos != null)

		{

			int PointMini = -1;

			int UdraMini = -1;

			double distMini = 1000; // définit une distance minimale à respectée, remplace un if

			String MessageEAfficher = "";

			int UdraSize = UdrasList.size();

			for (int itUdra = 0; itUdra < UdraSize; itUdra++)

			{

				Udra UdraActu = (Udra) UdrasList.get(itUdra);

				double EchelleX = GetEchelleX(UdraActu);

				int XDepCrb = (int) (decalX / EchelleX);

				int XFinCrb = (int) ((decalX + getWidth()) / EchelleX) + 3;

				XFinCrb = (XFinCrb > UdraActu.sizeRow()) ? UdraActu.sizeRow() : XFinCrb;

				for (int i = XDepCrb; i < XFinCrb; i++)

				{

					if (UdraActu.isNumber(ColValue, i))

					{

						if (UdraActu.getDbl(ColValue, i) != null)

						{

							double distActu = ((double) MousePos.x - (i * EchelleX - decalX))
									* ((double) MousePos.x - (i * EchelleX - decalX)) +

									((double) MousePos.y - get_Pos_On_Screen_From_Y_value(UdraActu.getDbl(ColValue, i)))
											* ((double) MousePos.y
													- get_Pos_On_Screen_From_Y_value(UdraActu.getDbl(ColValue, i)));

							if (distActu < distMini)

							{

								distMini = distActu;

								PointMini = i;

								UdraMini = itUdra;

								MessageEAfficher = UdraActu.getDbl(colValueDisplay, i).toString();

							}

						}

					}

					if (UdraActu.isMultiNumber(ColValue, i))

					{

						if (UdraActu.getDblFromMulti(ColValue, i, 0) != null)

						{

							double distActu = ((double) MousePos.x - (i * EchelleX - decalX))
									* ((double) MousePos.x - (i * EchelleX - decalX)) +

									((double) MousePos.y
											- get_Pos_On_Screen_From_Y_value(UdraActu.getDblFromMulti(ColValue, i, 0)))
											* ((double) MousePos.y - get_Pos_On_Screen_From_Y_value(
													UdraActu.getDblFromMulti(ColValue, i, 0)));

							if (distActu < distMini)

							{

								distMini = distActu;

								PointMini = i;

								UdraMini = itUdra;

								MessageEAfficher = UdraActu.getDblFromMulti(ColValue, i, 0).toString();

							}

						}

						if (UdraActu.getDblFromMulti(ColValue, i, 1) != null)

						{

							double distActu = ((double) MousePos.x - (i * EchelleX - decalX))
									* ((double) MousePos.x - (i * EchelleX - decalX)) +

									((double) MousePos.y
											- get_Pos_On_Screen_From_Y_value(UdraActu.getDblFromMulti(ColValue, i, 1)))
											* ((double) MousePos.y - get_Pos_On_Screen_From_Y_value(
													UdraActu.getDblFromMulti(ColValue, i, 1)));

							if (distActu < distMini)

							{

								distMini = distActu;

								PointMini = i;

								UdraMini = itUdra;

								MessageEAfficher = UdraActu.getDblFromMulti(ColValue, i, 1).toString();

							}

						}

					}

				}

			}

			if (PointMini != -1) // les points mini ont été trouvés

			{

				// recupère les messages

				String msgX = ((Udra) UdrasList.get(UdraMini)).get(ColData, PointMini).toString();

				// calcul la longeur de la boite de dialogue

				int decalBoitedialX = 0;

				int decalBoitedialY = 0;

				int Longueurboite = ((MessageEAfficher.length() > msgX.length()) ? MessageEAfficher.length()
						: msgX.length()) * 8;

				if (MousePos.x + Longueurboite > getWidth())

					decalBoitedialX = -Longueurboite;

				if (MousePos.y - 30 > 0)

					decalBoitedialY = -25;

				else

					decalBoitedialX += 15;

				g.setColor(new Color(255, 229, 204, 100));

				g.fillRect(MousePos.x + decalBoitedialX, MousePos.y + decalBoitedialY, Longueurboite, 25);

				g.setColor(new Color(170, 170, 170));

				g.drawString(MessageEAfficher, MousePos.x + 3 + decalBoitedialX, MousePos.y + decalBoitedialY + 13);

				g.drawString(msgX, MousePos.x + 3 + decalBoitedialX, MousePos.y + decalBoitedialY + 23);

			}

		}

		// Trace l'axe d'origine (0)

		g.setColor(Color.red);

		g.drawLine(0, (int) (this.getHeight() - (0 - ValueMinY) * EchelleY) - decalY, this.getWidth(),
				(int) (this.getHeight() - (0 - ValueMinY) * EchelleY) - decalY);

		// dessine l'axe Y

		g.setColor(Color.black);

		g.drawString(String.valueOf((getHeight() - decalY) * 1 / EchelleY), 0, 10);

		g.drawString(String.valueOf((-decalY) * 1 / EchelleY), 0, getHeight());

		generic_paintComponent(g);

	}

	protected int get_Pos_On_Screen_From_Y_value(double Yvalue)

	{

		return (int) (this.getHeight() - (Yvalue - ValueMinY) * EchelleY) - decalY;

	}

	protected void refreshEchelleY()

	{

		EchelleY = this.getHeight() / (ValueMaxY - ValueMinY) * zoom;

	}

	private void refreshValueMaxY()

	{

		ValueMaxY = 0;

		for (int itUdra = 0; itUdra < UdrasList.size(); itUdra++)

		{

			for (int i = 0; i < ((Udra) UdrasList.get(itUdra)).sizeRow(); i++)

			{

				if (((Udra) UdrasList.get(itUdra)).isNumber(ColValue, i))

					if (((Udra) UdrasList.get(itUdra)).getDbl(ColValue, i) > ValueMaxY)

						ValueMaxY = ((Udra) UdrasList.get(itUdra)).getDbl(ColValue, i);

				// si c'est un multi chiffre

				if (((Udra) UdrasList.get(itUdra)).isMultiNumber(ColValue, i))

				{

					if (((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 0) != null)

						if (((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 0) > ValueMaxY)

							ValueMaxY = ((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 0);

					if (((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 1) != null)

						if (((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 1) > ValueMaxY)

							ValueMaxY = ((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 1);

				}

			}

		}

	}

	private void refreshValueMinY()

	{

		if (relatif)

			ValueMinY = ((Udra) UdrasList.get(0)).getDbl(ColValue, 0);

		for (int itUdra = 0; itUdra < UdrasList.size(); itUdra++)

		{

			for (int i = 0; i < ((Udra) UdrasList.get(itUdra)).sizeRow(); i++)

			{

				// si c'est un double simple

				if (((Udra) UdrasList.get(itUdra)).isNumber(ColValue, i))

					if (((Udra) UdrasList.get(itUdra)).getDbl(ColValue, i) < ValueMinY)

						ValueMinY = ((Udra) UdrasList.get(itUdra)).getDbl(ColValue, i);

				// si c'est un multi chiffre

				if (((Udra) UdrasList.get(itUdra)).isMultiNumber(ColValue, i))

					if (((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 0) != null)

						if (((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 0) < ValueMinY)

							ValueMinY = ((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 0);

				if (((Udra) UdrasList.get(itUdra)).isMultiNumber(ColValue, i))

					if (((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 1) != null)

						if (((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 1) < ValueMinY)

							ValueMinY = ((Udra) UdrasList.get(itUdra)).getDblFromMulti(ColValue, i, 1);

			}

		}

	}

	protected double GetEchelleX(Udra UdraIn)

	{

		double res = getWidth() / (double) (UdraIn.sizeRow() - 1) * zoom;

		// UdraIn.Display();

		return res;

	}

	// on ajoute un Udra au graphique

	public void setUdra(Udra nvUdra)

	{

		UdrasList.add(nvUdra);

	}

	// Efface le contenu

	public void erase() {

		repaint();

	}

	// rafraichit tout les paramètres permttant le dessin, évite de rééxécuter tout
	// les calculs

	public void refreshParametre()

	{

		refreshValueMaxY();

		refreshValueMinY();

		refreshEchelleY();

	}

	public void setLogarithme(boolean logActif) {

		if (!logActif)

		{

			// pour chaque Udra

			for (int it_udra = 0; it_udra < UdrasList.size(); it_udra++)

			{

				// pour chaque ligne de chaque Udra

				for (int i = 0; i < ((Udra) UdrasList.get(it_udra)).sizeRow(); i++)

				{

					// on recopie les value du display dans les données de construction du graphique

					((Udra) UdrasList.get(it_udra)).setAvalue(ColValue, i,
							((Udra) UdrasList.get(it_udra)).get(colValueDisplay, i));

				}

			}

		}

		if (logActif)

		{

			// recherche la valeur minimale

			Double valMin = null;

			// pour chaque Udra

			for (int it_udra = 0; it_udra < UdrasList.size(); it_udra++)

			{

				// pour chaque ligne de chaque Udra

				for (int i = 0; i < ((Udra) UdrasList.get(it_udra)).sizeRow(); i++)

				{

					// on calcul les points de graphique

					if (((Udra) UdrasList.get(it_udra)).isNumber(colValueDisplay, i)
							&& ((Udra) UdrasList.get(it_udra)).getDbl(colValueDisplay, i) > 0)

					{

						double pt = ((Udra) UdrasList.get(it_udra)).getDbl(colValueDisplay, i);

						if (valMin == null || pt < valMin)

							valMin = pt;

					}

				}

			}

			// effectue les calculs

			// pour chaque Udra

			for (int it_udra = 0; it_udra < UdrasList.size(); it_udra++)

			{

				// pour chaque ligne de chaque Udra

				for (int i = 0; i < ((Udra) UdrasList.get(it_udra)).sizeRow(); i++)

				{

					// on calcul les points de graphique

					if (((Udra) UdrasList.get(it_udra)).isNumber(colValueDisplay, i)
							&& ((Udra) UdrasList.get(it_udra)).getDbl(colValueDisplay, i) > 0)

					{

						double pt = ((Udra) UdrasList.get(it_udra)).getDbl(colValueDisplay, i);

						((Udra) UdrasList.get(it_udra)).setAvalue(ColValue, i, Math.log(pt / valMin));

					}

					else

					{

						((Udra) UdrasList.get(it_udra)).setAvalue(ColValue, i, null);

					}

				}

			}

		}

	}

}
