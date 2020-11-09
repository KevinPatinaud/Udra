package testUdra;

import graphique.Drawing_Line_Udra;
import udra.Udra;

public class mainTestDrawingLine {

	public static void main(String[] args) {
		Udra u = new Udra("A" , "B");
		u.insertALine("F" , 1);
		u.insertALine("F" , 5);
		u.insertALine("F" , 2);
		u.insertALine("F" , 7);
		u.insertALine("F" , 3);
		u.insertALine("F" , 8);
		u.insertALine("F" , 4);
		Drawing_Line_Udra dl = u.draw_like_line("A", "B");
		dl.setTransparenceBoiteMessageprct(50);
		
	}

}
