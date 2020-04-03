package Library;

import udra.Udra;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class Udra_Lib_XML {

	public static Udra createFromXML(Udra udraIn, String xml)
	{
		Udra result = null; 
	
	    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      	
	    try 
	    {

	      final DocumentBuilder builder = factory.newDocumentBuilder();
		  final Document document= builder.parse(new InputSource(new StringReader( xml)));
		  final Element racine = document.getDocumentElement();

		  result = getNodeContent(racine);
		  
	    }
	    catch (Exception e){
	    	e.printStackTrace();
	    }
		  
	    return result;
	  }

	
	
	
	
	

	private static Udra getNodeContent(Node racine)
	{
		Udra content = new Udra();
	//	content.setName(racine.getNodeName());
		content.insertALine();
		
		for (int i = 0 ; i < racine.getChildNodes().getLength(); i++)
		{	
			String nomNoeud = racine.getChildNodes().item(i).getNodeName();
			
			//si la colonne n'existe pas on la créé
			if ( content.get_the_index_of_title_from_his_Name(nomNoeud)== -1)
			{
				content.insertAColumn(nomNoeud);
			}
			else //sinon on ajoute une nouvelle ligne
			{
				content.insertALine();
			}
			
			
			//si le noeud enfant a aussi des noeud enfant, on fait un appel récurssif à part si il s'agit d'une balise texte que l'on récupère directement
			if (racine.getChildNodes().item(i).hasChildNodes() 
					&& ! ( racine.getChildNodes().item(i).getChildNodes().getLength() == 1 && racine.getChildNodes().item(i).getChildNodes().item(0).getNodeType() == Node.TEXT_NODE))
			{
				Udra subContent = getNodeContent(racine.getChildNodes().item(i));
				content.setAvalue(nomNoeud, content.sizeRow() - 1, subContent);
			}
			
			//si il n'y a pas de sous noeud, alors on récupère juste la valeur
			else
			{
				content.setAvalue(nomNoeud, content.sizeRow() - 1, racine.getChildNodes().item(i).getTextContent());
			}	
		}
		
		
		
		//si plusieures lignes ont étaient créées on les concatenes dans un seul Udra
		Udra concate = new Udra();
		concate.setTitle(content.getTitle());
		concate.insertALine();
		
		
		//Pour chaque colonne regarde si une ou plusieures colonne ont étaient créées
		for (int c = 0 ; c < content.sizeColumn(); c++)
		{
			Udra donneeColonne = new Udra(content.getTitle().get(c));
			Object data = null;
				
			for (int l = 0 ; l < content.sizeRow() ; l++)
			{
				if ( content.get(c, l) != null && content.get(c, l) != "")
				{
					data = content.get(c, l);
					donneeColonne.insertALine(content.get(c, l));
				}
			}
			
			//si il n'y a qu'une seulle donnees pour cette colonne on la récupère
			if ( donneeColonne.sizeRow() < 2)
			{
				concate.setAvalue(c, 0, data);
			}
			//si on récupère l'Udra
			else
			{
				concate.setAvalue(c, 0, donneeColonne);
			}
		}
	
		return concate;
	}
	
	
	
	
	
		}

