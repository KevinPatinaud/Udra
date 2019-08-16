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

 

 

 

public class Udra_Square_Panel extends UdraPanel {

               

                //Parametres générique

                public static final int ColData = 0;

                public static final int ColValue = 1;

                public static final int ColColor = 2;

               

                private Double valMaxY_define = null;

               

               

               

                public Udra_Square_Panel() {

                               super();

                }

               

               

               

               

               

                //cette méthode dessinne le contenue de la fenètre

                  public void paintComponent(Graphics g) {

                                

                                 int espaceHautFenetre = 20;

                                

                                   refreshParametre();

                               //dessine le fond de la fenetre

                    g.setColor(Color.WHITE);

                    g.fillRect(0, 0, this.getWidth(), this.getHeight());

                    

 

                                 if ( UdrasList.size() < 1 )

                                                 return ;

                   

 

                    Udra myUdra =  UdrasList.get(0);

              

                    

                  //parcours les valeurs pour savoir si il existe des valeurs négatives

                    int hBottom = 20;

                    ValueMaxY = 0;

                    for (int it = 0 ; it < myUdra.sizeRow() ; it++)

                    {

                               //si l'udra contient des valeurs négatives on place la barre de suport du graphique au centre

                               if ( myUdra.getDbl(ColValue, it) < 0)

                                               hBottom = getHeight() / 2;

                              

                               //on prend la valeur max en absolut

                               ValueMaxY = ( Math.abs(myUdra.getDbl(ColValue, it)) > ValueMaxY) ? Math.abs(myUdra.getDbl(ColValue, it)) : ValueMaxY;

                    }

                   

                    

                    

               //recherche l'info la plus lognue a afficher

               double info_plus_longue = 0;

               for (int it = 0 ; it < myUdra.sizeRow() ; it++ )

                               if ( g.getFontMetrics().stringWidth(myUdra.get(ColData, it).toString())  > info_plus_longue)

                                               info_plus_longue = g.getFontMetrics().stringWidth(myUdra.get(ColData, it).toString());

                   

                    //parcours les valeurs

                    for (int it = 0 ; it < myUdra.sizeRow() ; it++)

                    {

                               //si la case à une couleur on l'affiche

                               if ( myUdra.get(ColColor, it) != null )

                               {

                                               g.setColor((Color) myUdra.get(ColColor, it));     

                               }

                              

                               //Afin de ne pas subir les approximation des calculs les résultat sont directements utilisés sans etres stockés.

                              

                               //calcul l'échelle vertical

                               double scale = ( getHeight() - espaceHautFenetre /* haut de la fenetre */ - hBottom /* ligne de base */) / ValueMaxY ;

                              

                               //calcul la hauteur de la barre

                               int heightCol =  (int) ( myUdra.getDbl(ColValue, it) * scale );

                              

                               //on calcul la position de la prochainne barre afin d'y coller au mieux

                               int colonneActu = (int) (it * getWidth()  * zoom / myUdra.sizeRow()) - decalX ;

                               int nextCol = (int) ( ( it + 1 ) * getWidth() * zoom / myUdra.sizeRow()  - it * getWidth()  * zoom / myUdra.sizeRow()) - 1 /* ecarte entre chaque colonne */;

                               nextCol = (nextCol < 1) ? 1 : nextCol;

                              

                               //dessinne la barre

                               int y1 = (heightCol > 0 ) ?  (int) (( getHeight() - hBottom ) - heightCol) : hBottom;

                               int y2 = (heightCol > 0 ) ? heightCol :  - heightCol ;

                               g.fillRect(colonneActu , y1 , nextCol, y2 );

                              

                              

                               //affiche les infos sur les barres               

                               g.setColor(Color.black);

                               double largeurColonne = getWidth()  * zoom / myUdra.sizeRow();

                               if ( largeurColonne > info_plus_longue )

                               {

                                               String messageCol = myUdra.get(ColData , it ).toString();

                                               g.drawString( messageCol , (int) ( colonneActu +  ( largeurColonne / 2) - g.getFontMetrics().stringWidth(messageCol) / 2 ) , getHeight() - 10);

                               }

                              

                              

                              

                               //dessine la ligne de base

                               g.setColor(Color.BLACK);

                               g.drawLine(0, getHeight() - hBottom -1, getWidth(), getHeight() - hBottom - 1);

                              

                                //Affiche les infos du curseur

                                   if (MousePos != null )

                                               {

               

                                               int indice = (int) ((MousePos.x + decalX)/ (getWidth()  * zoom / myUdra.sizeRow()) );

                                              

                                              

                                               //recupère les messages

                                               String messageValue = "Data : " + myUdra.get(ColData, indice);

                                               String message = "Value : " + myUdra.get(ColValue , indice);

                                              

                                              

                                              

                                               //calcul la longeur de la boite de dialogue

                                               int decalBoitedialX = 0;

                                               int decalBoitedialY = 0;

                                               int Longueurboite = ((  g.getFontMetrics().stringWidth(message) > g.getFontMetrics().stringWidth(messageValue) ) ?  g.getFontMetrics().stringWidth(message)  :  g.getFontMetrics().stringWidth(messageValue) ) + 6;

 

                                               if (MousePos.x + Longueurboite > getWidth() )

                                                              decalBoitedialX = - Longueurboite;

                                                             

                                               if (MousePos.y - 30 > 0)

                                                              decalBoitedialY =  - 25;

                                               else

                                                              decalBoitedialX += 15;

                                              

                                              

                                              

                                               g.setColor(new Color(255, 229, 204 , 100));

                                                               g.fillRect(MousePos.x + decalBoitedialX, MousePos.y + decalBoitedialY, Longueurboite , 25);

                                                               g.setColor(new Color(170 , 170 , 170));

                                                               g.drawString(message, MousePos.x + 3 + decalBoitedialX, MousePos.y + decalBoitedialY + 13);

                                                               g.drawString(messageValue, MousePos.x + 3 + decalBoitedialX, MousePos.y + decalBoitedialY + 23);

                                              

                                               }

                             

                    }

                   

                    g.setColor(Color.BLACK);

                    g.drawString("Max : " + String.valueOf( ValueMaxY ) , 0, 10);

 

                   

 

                                 generic_paintComponent(g);

                  }

                 

                  

                  

                  

                  

                  

                  

                  

                  protected int get_Pos_On_Screen_From_Y_value(double Yvalue)

                 {

                               return (int)(this.getHeight() - (Yvalue - ValueMinY) * EchelleY) - decalY;

                  }

 

                 

                  

                  protected void refreshEchelleY()

                  {

                                 EchelleY = this.getHeight() / (ValueMaxY - ValueMinY) * zoom ;

                  }

                 

                  

               

                private void refreshValueMaxY()

                {

 

                               if ( valMaxY_define != null )

                               {

                                               ValueMaxY = valMaxY_define;

                               }

                               else

                               {

                                               if (UdrasList.size() > 0)

                                               {

                                                   Udra myUdra =  UdrasList.get(0);

                                                   if ( myUdra != null)

                                                               for (int it = 0 ; it < myUdra.sizeRow() ; it++)

                                                                   {

                                                                                              if( myUdra.getDbl(ColValue, it) > ValueMaxY )

                                                                                                             ValueMaxY = myUdra.getDbl(ColValue, it) ;

                                                                   }

                                               }

                               }

                }

               

               

               

               

                private void refreshValueMinY()

                {

                              

                                  

                }

                                

 

                 

                  

 

                  //on ajoute un udra au graphique

                  public void setUdra (Udra nvUdra)

                  {

                                 UdrasList = new ArrayList<Udra>();

                                 UdrasList.add(nvUdra);

                  }

                 

                  

                  //Efface le contenu

                  public void erase(){

                    repaint();

                  }

 

 

 

                  //rafraichit tout les paramètres permttant le dessin, évite de rééxécuter tout les calculs

                  public void refreshParametre()

                  {

                                 refreshValueMaxY();

                                 refreshValueMinY();

                                 refreshEchelleY();

                  }

 

 

 

                public void set_valMaxY_define( Double val)

                {

                               valMaxY_define = val;

                }

 

 

 

 

 

 

               

}

 