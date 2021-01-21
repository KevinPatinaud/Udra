package Statistic;

import udra.Udra;

public class Udra_stat_findBestCompetitor {
	

	private final static String strCompetitor = "COMPETITOR";
	private final static String strCompetitor_1 = "COMPETITOR_1";
	private final static String strCompetitor_2 = "COMPETITOR_2";
	private final static String strCourse = "COURSE";
	private final static String strResultat = "RESULTAT";
	private final static String strNmbCourse = "NmbCourse";
	private final static String strNmbVictoryCompet1 = "NmbVictoryCompet1";
	private final static String strnmbVictoire = "nmbVictoire";
	private final static String strNmbTotalAdversaire = "nmbTotalAdversaire";
	
	
	
	// Cet algortihme permet de trouver le meilleur compétiteur
	
	// Exemple :
	// Nous voulons ordonner les chevaux d'un groupe par leur ordre de rapidité. (colonne compétitor)
	// Plusieures courses sont organisées mais tous les chevaux ne concours pas à toutes les courses. (colonne identifiant course)
	// Les resultats des courses sont indiquées dans une troisième colonne (colonne resultatCourse)
	// Cependant le meilleur résultat peut etre enregistré de deux facon (parametre smallerIsBetter) : le cheval qui a fait le plus petit temps (True) ou le cheval qui a laissé le plus de concurrents derièrre (False)
	
	public static Udra findbestCompetitor(Udra Uin , int colCompetitor, int colIdCourse, int colResultCourse, boolean smallerIsBetter)
	{
		Udra jdd = new Udra(strCompetitor, strCourse, strResultat);

		//recopie les données 
		for (int i = 0 ; i < Uin.sizeRow(); i++)
		{
			jdd.insertALine(Uin.getString(colCompetitor, i) , Uin.getString(colIdCourse, i) , Uin.getString(colResultCourse, i));
		}
		
		//jdd.display();

		
		
		

		Udra listCompetitor = new Udra(strCompetitor); // Liste de tous les compétiteurs (chevaux)
		for (int i = 0 ; i < jdd.sizeRow(); i++)
		{
			listCompetitor.insertALine(jdd.getString(strCompetitor, i));
		}
		listCompetitor.delete_duplicate_row();
		
		//listCompetitor.display();
		
		
		
		
		
		
		Udra listCourse = new Udra(strCourse); // contient la liste de toutes les courses
		for (int i = 0 ; i < jdd.sizeRow(); i++)
		{
			listCourse.insertALine(jdd.getString(strCourse, i));
		}
		listCourse.delete_duplicate_row();
		
		//listCourse.display();
		
		
		
		//Cette feuille de match rassemble l'ensemble des duo de cheval ainsi que leur nombre d'affrontement et le nombre de victoire pour le premier cheval
		Udra resultatMatch = new Udra(strCompetitor_1, strCompetitor_2, strNmbVictoryCompet1, strNmbCourse);
		
		for (int i = 0 ; i < listCompetitor.sizeRow(); i++)
		{
			for (int j = 0 ; j < listCompetitor.sizeRow(); j++)
			{
				if ( i!= j)
				{
					resultatMatch.insertALine(listCompetitor.getString(0, i) , listCompetitor.getString(0, j) , 0 , 0);
				}
			}
		}
		
		//resultatMatch.display();
		
		
		//__________________________________________________________________________________________________________________________________________________________________
		// compte le nombre de fois ou chaque duo de cheval a été mis en compétition et releve le nombre de fois ou le compétieur 1 a gagné
		//Pour chaque duo
		for (int itDuo = 0 ; itDuo < resultatMatch.sizeRow(); itDuo++)
		{
			String competitor1 = resultatMatch.getString(strCompetitor_1, itDuo);
			String competitor2 = resultatMatch.getString(strCompetitor_2, itDuo);
			
			
			System.out.println("\n\n" + competitor1 + " VS " + competitor2 + "\n\n");
			
			//Pour chaque course
			for (int itCourse = 0 ; itCourse < listCourse.sizeRow() ; itCourse++ )
			{
				String course = listCourse.getString(strCourse, itCourse);
				Double resultatCompetitor1 = null;
				Double resultatCompetitor2 = null;
				
				//Parcours le JDD
				for (int iJDD = 0 ; iJDD < jdd.sizeRow(); iJDD++)
				{
					if ( jdd.getString(strCourse, iJDD).equalsIgnoreCase(course) &&  jdd.getString(strCompetitor, iJDD).equalsIgnoreCase(competitor1))
					{
						resultatCompetitor1 = jdd.getDbl(strResultat, iJDD);
					}
					if ( jdd.getString(strCourse, iJDD).equalsIgnoreCase(course) &&  jdd.getString(strCompetitor, iJDD).equalsIgnoreCase(competitor2))
					{
						resultatCompetitor2 = jdd.getDbl(strResultat, iJDD);
					}
				}
				
				//si les deux compétiteurs (chevaux) ont courrus sur la même course
				if ( resultatCompetitor1 != null && resultatCompetitor2 != null)
				{
					//Alors on enregistre le resultat
					resultatMatch.setAvalue(strNmbCourse, itDuo, resultatMatch.getDbl(strNmbCourse, itDuo) + 1);
					
					if ((smallerIsBetter && resultatCompetitor1 < resultatCompetitor2) || (! smallerIsBetter && resultatCompetitor1 > resultatCompetitor2) )
					{
						System.out.println(competitor1 + " gagne : " + course);
						resultatMatch.setAvalue(strNmbVictoryCompet1, itDuo, resultatMatch.getDbl(strNmbVictoryCompet1, itDuo) + 1);
					}
					
				}
			}
			
		}
		
		//resultatMatch.display();
		

		//__________________________________________________________________________________________________________________________________________________________________
		// Interprete les resultats des courses
		// La meilleureet la plus fiable des solutions pour obtenir des résultats fiables est que chaque compétiteur se soient au moins affrontés une fois
		
		
		// Si tous les compétiteurs se sont rencontrés au moins une fois
		
		boolean tousAffrontes = true;
		for (int i = 0 ; i < resultatMatch.sizeRow() ; i++)
		{
			if ( resultatMatch.getDbl(strNmbCourse, i) == 0)
			{
				tousAffrontes = false;
			}
		}
		
		if ( tousAffrontes)
		{
			listCompetitor.insertAColumn(strnmbVictoire, null);
			listCompetitor.insertAColumn(strNmbTotalAdversaire, listCompetitor.sizeRow() - 1);
			
			// compte pour chaque duo combien de fois le cheval n°1 (competitor 1) a gagné sur l'ensemble des courses effectué avec ce duo
			for (int itCompetitor = 0 ; itCompetitor < listCompetitor.sizeRow(); itCompetitor++)
			{
				String currentCompetitor = listCompetitor.getString(strCompetitor, itCompetitor);
				int nmbVictory = 0;
				for (int iRes = 0 ; iRes < resultatMatch.sizeRow(); iRes++)
				{
					if ( resultatMatch.getString(strCompetitor_1, iRes).equalsIgnoreCase(currentCompetitor))
					{
						if ( resultatMatch.getDbl(strNmbVictoryCompet1, iRes) >  resultatMatch.getDbl(strNmbCourse, iRes) / 2) 
						{
							nmbVictory++;
						}
					}
				}
				listCompetitor.setAvalue(strnmbVictoire, itCompetitor , nmbVictory);
			}

			listCompetitor.orderDescBy(strnmbVictoire);
			//listCompetitor.display();
					
		}
		
		return listCompetitor;
	}
	
	

}
