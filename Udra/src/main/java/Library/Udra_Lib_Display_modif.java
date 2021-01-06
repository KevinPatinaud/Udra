package Library;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import udra.Udra;

public class Udra_Lib_Display_modif implements ChangeListener, PropertyChangeListener {

	
	 private boolean Windows_is_display = true;
	 private boolean recreer =false;
	 private int posX_interface = 0;
	 private int posY_interface = 0;
	 private int height = 500;
	 private int width = 500;
	 private JFrame frame;
 	 
	 //top d'affichage des bouttons
	 private boolean showBtn_AddANewLine = false;
 	 private boolean showBtn_AddANewColumn = false;
 	 
 	 
 	//Permet de gérer les colonnes fixes
 	private JTable main;
 	private JTable fixed;
 	private JScrollPane scrollPane;
	 


	 public static int getId_NO_BUTTON()
	 {
		 return 0;
	 }
 	 
	 public static int getId_ALL_BUTTON()
	 {
		 return getIdBtn_ADD_A_NEW_LINE() + getIdBtn_ADD_A_NEW_COLUMN();
	 }  
	 public static int getIdBtn_ADD_A_NEW_LINE()
	 {
		 return 1;
	 }
	    
	 public static int getIdBtn_ADD_A_NEW_COLUMN()
	 {
		 return 2;
	 }
	 
	 
		
		public static void display_With_Modification_Ability(  Udra udra_in ,  String TitleFrame, int buttonToDisplay, int nmbColumnToFixe )
		{
			Udra_Lib_Display_modif udra_display = new Udra_Lib_Display_modif();
			udra_display.display_With_Modification_Ability_start(udra_in, TitleFrame, buttonToDisplay, nmbColumnToFixe);
		}
		
	 
	 
	 
	    protected void display_With_Modification_Ability_start( Udra udra_in ,  String  TitleFrame, int buttonToDisplay, int nmbColumnToFixe) // display the array to a graphical user interface, it's possible to give a title to the array
	    {
	    	Windows_is_display = true;
	   	 	recreer =false;
	    	
	        display_With_Modification_AbilityAction( udra_in , TitleFrame , buttonToDisplay, nmbColumnToFixe);
	    
	        while ( recreer )
	        {
	            display_With_Modification_AbilityAction( udra_in , TitleFrame , buttonToDisplay, nmbColumnToFixe);
	        }
	            
	    }
	    
	    
	    
	    
	    public void display_With_Modification_AbilityAction( final Udra udra_in , String TitleFrame, int buttonToDisplay, int nmbColumnToFixe) // display the array to a graphical user interface, it's possible to give a title to the array
	    {
	        
	    	Windows_is_display = true;

	    	//Gère les tops d'affichage des bouttons
	    	 if( buttonToDisplay == getId_ALL_BUTTON())
             {
	    		 showBtn_AddANewLine = true;
	    		 showBtn_AddANewColumn = true;
             }
	    	 
	    	 int cmptBtn = buttonToDisplay;
	    	 
	    	 if ( cmptBtn >= getIdBtn_ADD_A_NEW_COLUMN())
	    	 {
	    		 showBtn_AddANewColumn = true;
	    		 cmptBtn = cmptBtn - getIdBtn_ADD_A_NEW_COLUMN();
	    	 }

	    	 
	    	 if ( cmptBtn >= getIdBtn_ADD_A_NEW_LINE())
	    	 {
	    		 showBtn_AddANewLine = true;
	    		 cmptBtn = cmptBtn - getIdBtn_ADD_A_NEW_LINE();
	    	 }

	    	 
	    	 
	    	 
	        
	    	//tranform the Udra to TableModel
	        final DefaultTableModel grille = new DefaultTableModel() { 
	            
	            
	            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {    
	            	udra_in.setAvalue(columnIndex, rowIndex, aValue);
	            }
	             
	            
	            public void removeTableModelListener(TableModelListener l) {            }
	            
	            
	            public boolean isCellEditable(int rowIndex, int columnIndex) {    return true;    }
	            
	            
	            public Object getValueAt(int rowIndex, int columnIndex) {
	                return udra_in.get(columnIndex, rowIndex);
	            }
	            
	            
	            public int getRowCount() {
	                return udra_in.sizeRow();
	            }
	            
	            
	            public String getColumnName(int columnIndex) {
	                return udra_in.getTitle().get(columnIndex);
	            }
	            
	            
	            public int getColumnCount() {
	                return udra_in.sizeColumn();
	            }
	            
	            
	            public Class<?> getColumnClass(int columnIndex) {        
	                return Object.class;    
	                }
	            
	            
	            public void addTableModelListener(TableModelListener l) {        }
	        };
	        
	        
	        
	        
	   
	               

            //cré la frame (fenetre)
            frame = new JFrame();
            if (TitleFrame != null)
                frame.setTitle(TitleFrame);
            else
                frame.setTitle(udra_in.getName());


            //on cré une premierre grille dynamique
            main = new JTable();
            main.setModel(grille);
	        main.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    		main.setAutoCreateColumnsFromModel( false );
    		main.addPropertyChangeListener( this );

    		this.scrollPane = new JScrollPane( main );
    		

            
            //create the button panel
            JPanel pan = new JPanel();
            JButton valider = new JButton("Valider");
            valider.addActionListener(    new ActionListener(){

                public void actionPerformed(ActionEvent arg0) {
                    CellEditor c = main.getCellEditor();
                    boolean stop=false;
                    if (c!=null) stop=c.stopCellEditing();
                    
                    
                    frame.dispose();
                    Windows_is_display = false;
                    recreer = false;

                }         

              });
            pan.add(valider);
            
            if( showBtn_AddANewLine )
            {
                JButton nouvLigne = new JButton("Add a new line");
                nouvLigne.addActionListener(    new ActionListener(){

                    public void actionPerformed(ActionEvent arg0) {

                    	udra_in.insertALine();

                        frame.dispose();
                        Windows_is_display = false;
                        recreer = true;
                        posX_interface = (int) frame.getLocation().getX();
                        posY_interface = (int) frame.getLocation().getY();
                        width = frame.getWidth();
                        height = frame.getHeight();
                        

                    }         

                  });
                
                
                pan.add(nouvLigne);
            }
            
            

            if( showBtn_AddANewColumn)
            {
                JButton nouvColumn = new JButton("Add a new column");
                nouvColumn.addActionListener(    new ActionListener(){

                    public void actionPerformed(ActionEvent arg0) {

                    	String nameNewColumn =  JOptionPane.showInputDialog("Please set the name of the new column : ");;
                    	boolean columnCreated = false;
                    	
                    	if ( nameNewColumn != null)
                    	{
	                    	while (! columnCreated  && ! nameNewColumn.equals(""))
	                    	{
	                    		if ( udra_in.get_the_index_of_title_from_his_Name(nameNewColumn) == -1)
		                    	{
		                    		udra_in.insertAColumn( nameNewColumn, "" );
		                    		columnCreated = true;
		                    	}
		                    	else
		                    	{
		                    		nameNewColumn =  JOptionPane.showInputDialog("Sorry this column already exist, please set another name : ");	
		                    	}
	                    	}
                    	}
                        frame.dispose();
                        Windows_is_display = false;
                        recreer = true;
                        posX_interface = (int) frame.getLocation().getX();
                        posY_interface = (int) frame.getLocation().getY();
                        width = frame.getWidth();
                        height = frame.getHeight();
                        

                    }         
                  });
                
                
                pan.add(nouvColumn);
            }
            
            
            //___________________________________________________________________________________________________________________
            // PARTIE GERANT L OPTION DES COLONNES FIXES
            
            if ( nmbColumnToFixe > 0)
            {
	    		//  Use the existing table to create a new table sharing
	    		//  the DataModel and ListSelectionModel
	
	    		int totalColumns = main.getColumnCount();
	
	    		// La Jtable fixed contiendra les colonnes fixes
	    		fixed = new JTable();
	    		fixed.setAutoCreateColumnsFromModel( false );
	    		fixed.setModel( main.getModel() );
	    		fixed.setSelectionModel( main.getSelectionModel() );
	    		fixed.setFocusable( false );
	    		
	    		
	    		
	    		//  Remove the fixed columns from the main table and add them to the fixed table
	    		for (int i = 0; i < nmbColumnToFixe; i++)
	    		{
	    	        TableColumnModel columnModel = main.getColumnModel();
	    	        TableColumn column = columnModel.getColumn( 0 );
	        	    columnModel.removeColumn( column );
	    			fixed.getColumnModel().addColumn( column );
	    		}
	
	    		
	    		//  Add the fixed table to the scroll pane
	
	            fixed.setPreferredScrollableViewportSize(fixed.getPreferredSize());
	    		scrollPane.setRowHeaderView( fixed );
	    		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixed.getTableHeader());
	
	    		
	
	    		scrollPane.getRowHeader().addChangeListener( this );
	    		
	            
	            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            }
            //______________________________________________________________________________________________________________________
            // PARTIE GERANT L OPTION SANS COLONNES FIXES 
            else
            {
            	frame.getContentPane().add(new JScrollPane(main), BorderLayout.CENTER);
            }
            
            //______________________________________________________________________________________________________________________
            
            
            frame.getContentPane().add(pan, BorderLayout.SOUTH);	                
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocation(posX_interface, posY_interface);
            frame.setSize(width, height);
            
            frame.setVisible(true);  
       


            
	        
	        while ( Windows_is_display  )
	        {
	            try {    Thread.sleep( 100 );    } catch (InterruptedException e) {    }
	        }
	        
	    }	
	
	
	
	
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
		
		/*
		 *  Return the table being used in the row header
		 */
		public JTable getFixedTable()
		{
			return fixed;
		}
	//
	//  Implement the ChangeListener
	//
		public void stateChanged(ChangeEvent e)
		{
			//  Sync the scroll pane scrollbar with the row header
			JViewport viewport = (JViewport) e.getSource();
			scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
		}
	//
	//  Implement the PropertyChangeListener
	//
		public void propertyChange(PropertyChangeEvent e)
		{
			//  Keep the fixed table in sync with the main table

			if ("selectionModel".equals(e.getPropertyName()))
			{
				fixed.setSelectionModel( main.getSelectionModel() );
			}

			if ("model".equals(e.getPropertyName()))
			{
				fixed.setModel( main.getModel() );
			}
		}
		
	
	
}
