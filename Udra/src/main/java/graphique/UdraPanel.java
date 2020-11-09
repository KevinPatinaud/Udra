
package graphique;

import java.awt.AWTEvent;

import java.awt.Button;

import java.awt.Color;

import java.awt.Cursor;

import java.awt.Font;

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

public abstract class UdraPanel extends JPanel {

	// Parametres generique
	ArrayList<Udra> UdrasList = new ArrayList<Udra>();

	// paramatres sp�cifiques

	// gestion du zoom
	protected double zoom = 1;
	protected int decalX = 0;
	protected int decalY = 0;
	protected Point MousePress = null;
	protected static final double forceDuZoom = 1.2;
	protected Point MousePos = null;
	protected boolean relatif = false;
	protected int transparenceBoiteMessagePrct = 100;
	
	// Parametre static �vite de relance � chaque fois les calculs
	protected double ValueMinY = 0;
	protected double ValueMaxY = 0;
	protected double EchelleY = 0;

	// Elements graphique compl�mentaires

	// le type de position d�signe le type de mesure x y :

	// % signifie que x et y sont calculer en fonction de la taille de la fentre en
	// %

	// et fx signifie que x et y sont fixe

	protected Udra list_text = new Udra("x", "y", "type_position", "text", "color", "font");
	protected Udra list_button = new Udra("x", "y", "type_position", "text", "action");
	protected int default_width_btn = 90;
	protected int default_height_btn = 30;

	
	
	public UdraPanel() {

		addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {

				if (e.getWheelRotation() == -1)
					zoomer(e);

				else if (e.getWheelRotation() == 1)
					dezoomer(e);

			}

		});

		addMouseMotionListener(new MouseMotionListener() {

			public void mouseMoved(MouseEvent e) {

				// R�cup�re la position du cureseur � chaque mouvement et redessine la
				// page

				MousePos = new Point(e.getX(), e.getY());

				erase();

				boolean onBtn = false;

				for (int i = 0; i < list_button.sizeRow(); i++)
				{

					if (list_button.get("type_position", i).toString().equals("fx"))

					{

						if (e.getX() > list_button.getDbl("x", i)
								&& e.getX() < list_button.getDbl("x", i) + default_width_btn
								&& e.getY() > list_button.getDbl("y", i)
								&& e.getY() < list_button.getDbl("y", i) + default_height_btn)

							onBtn = true;

					}

					else

					{

						if (e.getX() > list_button.getDbl("x", i) * getWidth() / 100
								&& e.getX() < (list_button.getDbl("x", i) * getWidth() / 100 + default_width_btn)
								&& e.getY() > list_button.getDbl("y", i) * getHeight() / 100
								&& e.getY() < (list_button.getDbl("y", i) * getHeight() / 100 + default_height_btn))

							onBtn = true;

					}

				}

				if (onBtn)

					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

				else

					setCursor(Cursor.getDefaultCursor());

			}

			public void mouseDragged(MouseEvent e) {

				// TODO Auto-generated method stub
				if (MousePress != null)
				{

					decalX = decalX + MousePress.x - e.getX();

					decalY = decalY + MousePress.y - e.getY();

					controlDecal();

					erase();

					MousePos = new Point(e.getX(), e.getY());

					MousePress = new Point(e.getX(), e.getY());

				}

			}

		});

		addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1)
				{
					if (e.getButton() == MouseEvent.BUTTON1) {
						System.out.println("gauche");
					}

					if (e.getButton() == MouseEvent.BUTTON3) {
						System.out.println("droit");
					}
				}
				if (e.getClickCount() == 2)
				{

					if (e.getButton() == MouseEvent.BUTTON1) {
						zoomer(e);
					}

					if (e.getButton() == MouseEvent.BUTTON3) {
						dezoomer(e);
					}

				}

				for (int i = 0; i < list_button.sizeRow(); i++)
				{

					if (list_button.get("type_position", i).toString().equals("fx"))
					{

						if (e.getX() > list_button.getDbl("x", i)
								&& e.getX() < list_button.getDbl("x", i) + default_width_btn
								&& e.getY() > list_button.getDbl("y", i)
								&& e.getY() < list_button.getDbl("y", i) + default_height_btn)

							((Runnable) list_button.get("action", i)).run();

					}
					else
					{

						if (e.getX() > list_button.getDbl("x", i) * getWidth() / 100
								&& e.getX() < (list_button.getDbl("x", i) * getWidth() / 100 + default_width_btn)
								&& e.getY() > list_button.getDbl("y", i) * getHeight() / 100
								&& e.getY() < (list_button.getDbl("y", i) * getHeight() / 100 + default_height_btn))

							((Runnable) list_button.get("action", i)).run();

					}

				}

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
			}

			public void mousePressed(MouseEvent e) {
				MousePress = new Point(e.getX(), e.getY());
			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (MousePress != null)
				{

					decalX = decalX + MousePress.x - e.getX();
					decalY = decalY + MousePress.y - e.getY();
					controlDecal();
					MousePress = null;
					erase();
				}

			}

		});

	}

	public void addButton(int x, int y, String type_position, String nom, Runnable rn)
	{
		list_button.insertALine(x, y, type_position, nom, rn);
	}

	public void addText(int x, int y, String type_position, String text)
	{
		Font f = null;
		Color c = Color.black;
		list_text.insertALine(x, y, type_position, text, c, f);

	}

	public void addText(int x, int y, String type_position, String text, Color c)
	{
		Font f = null;
		list_text.insertALine(x, y, type_position, text, c, f);
	}

	public void addText(int x, int y, String type_position, String text, Color c, Font f)
	{

		list_text.insertALine(x, y, type_position, text, c, f);

	}

	public void setRelatif(boolean val)

	{

		relatif = val;

	}

	public void forcerZoom(double nouvelleValeur)

	{

		zoom = nouvelleValeur;

		controlDecal();

		erase();

	}

	protected void zoomer(MouseEvent e)
	{

		// le zoom maximum ext limit� � 1 million car au del� des probl�mes de
		// calcul apparraissent

		if (zoom < 1000000)

		{

			// augmente les d�calage en fonction du nouveau zoom

			decalX = (int) ((e.getX() + decalX) * forceDuZoom - (e.getX()));

			decalY = -(int) (((getHeight() - e.getY()) - decalY) * forceDuZoom - (getHeight() - e.getY()));

			zoom = zoom * forceDuZoom;

			controlDecal();

			erase();

		}

	}

	protected void dezoomer(MouseEvent e)
	{

		// diminue les d�calage en fonction du nouveau zoom

		decalX = (int) ((e.getX() + decalX) / forceDuZoom - (e.getX()));

		decalY = -(int) (((getHeight() - e.getY()) - decalY) / forceDuZoom - (getHeight() - e.getY()));

		// si le d�zoom est inf�rieur ou retourne sur le zoom initial, pas de soucis
		// on peut d�zoomer

		if (zoom / forceDuZoom >= 1)

			zoom = zoom / forceDuZoom;

		else

		{

			zoom = 1;

			decalX = 0;

			decalY = 0;

		}

		controlDecal();

		erase();

	}

	public void controlDecal()
	{

		refreshParametre();

		if (decalX <= 0)

			decalX = 0;

		if (decalX > (getWidth() * zoom) - getWidth())

			decalX = (int) ((getWidth() * zoom) - getWidth());

		if (get_Pos_On_Screen_From_Y_value(ValueMinY) < getHeight())

			decalY = 0;

		if (get_Pos_On_Screen_From_Y_value(ValueMaxY) > 0)

			decalY = (int) (this.getHeight() - (ValueMaxY - ValueMinY) * EchelleY);

	}

	protected int get_Pos_On_Screen_From_Y_value(double Yvalue)
	{
		return (int) (this.getHeight() - (Yvalue - ValueMinY) * EchelleY) - decalY;
	}

	protected void refreshEchelleY()
	{
		EchelleY = this.getHeight() / (ValueMaxY - ValueMinY) * zoom;
	}

	protected double GetEchelleX(Udra UdraIn)
	{
		double res = getWidth() / (double) (UdraIn.sizeRow() - 1) * zoom;
		// UdraIn.Display();
		return res;
	}

	// on ajoute un udra au graphique

	public void setUdra(Udra nvUdra)
	{

		UdrasList.add(nvUdra);

	}

	public void generic_paintComponent(Graphics g)
	{

		// dessine les text rajout�s

		for (int i = 0; i < list_text.sizeRow(); i++)

		{

			g.setColor(Color.black);

			if (list_text.get("type_position", i).toString().equals("fx"))

			{

				g.setFont((Font) list_text.get("font", i));

				g.setColor((Color) list_text.get("color", i));

				g.drawString(list_text.get("text", i).toString(), ((Integer) list_text.get("x", i)),
						(Integer) list_text.get("y", i));

			}

			else

			{

				g.setFont((Font) list_text.get("font", i));

				g.setColor((Color) list_text.get("color", i));

				g.drawString(list_text.get("text", i).toString(), (int) (list_text.getDbl("x", i) * getWidth() / 100),
						(int) (list_text.getDbl("y", i) * getHeight() / 100));

			}

		}

		// dessine les bouttons rajout�s

		for (int i = 0; i < list_button.sizeRow(); i++)

		{

			g.setFont(new Font("Comic Sans MS", Font.BOLD, 13));

			if (list_button.get("type_position", i).toString().equals("fx"))

			{

				g.setColor(Color.GRAY);

				g.fillRoundRect(((Integer) list_button.get("x", i)), (Integer) list_button.get("y", i),
						default_width_btn, default_height_btn, 15, 15);

				g.setColor(Color.white);

				g.drawString(list_button.get("text", i).toString(), ((Integer) list_button.get("x", i)) + 10,
						(Integer) list_button.get("y", i) + 20);

			}
			else
			{

				g.setColor(Color.GRAY);

				g.fillRoundRect((int) (list_button.getDbl("x", i) * getWidth() / 100),
						(int) (list_button.getDbl("y", i) * getHeight() / 100), default_width_btn, default_height_btn,
						15, 15);

				g.setColor(Color.white);

				g.drawString(list_button.get("text", i).toString(),
						(int) (list_button.getDbl("x", i) * getWidth() / 100) + 10,
						(int) (list_button.getDbl("y", i) * getHeight() / 100) + 20);

			}

		}

	}

	//Affiche en transparence la boite de mesage
	public void setTransparenceBoiteMessageprct(int transparenceBoiteMessage) {
		this.transparenceBoiteMessagePrct = transparenceBoiteMessage * 255 / 100;
	}

	
	
	// Efface le contenu
	public void erase() {
		repaint();
	}

	// rafraichit tout les param�tres permttant le dessin, �vite de
	// rexeccuter tout les calculs

	public abstract void refreshParametre();

}
