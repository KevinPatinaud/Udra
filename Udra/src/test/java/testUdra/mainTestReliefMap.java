package testUdra;

import udra.Udra;

public class mainTestReliefMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Udra jdd = new Udra ("Glace / mois" , "Janvier" ,  "F�vrier" ,  "Mars" ,  "Avril" ,  "Mai" ,  "Juin" ,  "Juillet" ,  "Aout" ,  "Septembre" ,  "October" ,  "Novembre" ,  "Decembre"  );

		jdd.insertALine("Fraise" , 		1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1);
		jdd.insertALine("Vanille" , 	1 , 1 , 1 , 1 , 1 , 2 , 1 , 1 , 1 , 1 , 1 , 1);
		jdd.insertALine("Chocolat" , 	1 , 1 , 1 , 1 , 2 , 3 , 2 , 1 , 1 , 1 , 1 , 1);
		jdd.insertALine("Pistache" , 	1 , 1 , 1 , 2 , 3 , 4 , 3 , 2 , 1 , 1 , 1 , 1);
		jdd.insertALine("Framboise" , 	1 , 1 , 1 , 1 , 2 , 3 , 2 , 1 , 1 , 1 , 1 , 1);
		jdd.insertALine("Litchi" , 		1 , 1 , 1 , 1 , 1 , 2 , 1 , 1 , 1 , 1 , 1 , 1);
		jdd.insertALine("Peche" , 		1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1 , 1);
		
		jdd.display();
		

		
		jdd.draw_relief_map("#ffffff" , "#2DA0ED");
	}

}
