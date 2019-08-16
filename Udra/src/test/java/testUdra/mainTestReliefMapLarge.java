package testUdra;

import udra.Udra;

public class mainTestReliefMapLarge {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Udra jdd = new Udra ( );

		int large = 200;
		int haut = 100;
		
		for (int i = 0 ; i < large; i++)
			jdd.insertAColumn(String.valueOf(i));
		
		for (int i = 0 ; i < haut ; i++)
			jdd.insertALine();
		
		for ( int x = 0 ; x < large; x++)
		{
			for (int y = 0 ; y < haut; y++)
			{
				//set the name of each line
				if (x == 0)
				{
					jdd.setAvalue(x, y, y);
				}
				else
				{
					//calcul the value of each case
					jdd.setAvalue(x, y, x * y);
				}
			}
		}
		

		
		jdd.draw_relief_map("#ffffff" , "#0000ff");
	}

}
