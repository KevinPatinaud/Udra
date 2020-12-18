package Library;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import udra.Udra;

public class Udra_Lib_Display_modif {

	
	 private boolean Windows_is_display = true;
	 private boolean recreer =false;
	 private int posX_interface = 0;
	 private int posY_interface = 0;
	 private int height = 500;
	 private int width = 500;
	
	    
	 
	 
		
		public static void display_With_Modification_Ability(  Udra udra_in ,  final String ... TitleFrame )
		{
			Udra_Lib_Display_modif udra_display = new Udra_Lib_Display_modif();
			udra_display.display_With_Modification_Ability_start(udra_in, TitleFrame);
		}
		
	 
	 
	 
	    protected void display_With_Modification_Ability_start( Udra udra_in ,  final String ... TitleFrame) // display the array to a graphical user interface, it's possible to give a title to the array
	    {
	    	Windows_is_display = true;
	   	 	recreer =false;
	    	
	        display_With_Modification_AbilityAction( udra_in , TitleFrame );
	    
	        while ( recreer )
	        {
	            display_With_Modification_AbilityAction( udra_in , TitleFrame );
	        }
	            
	    }
	    
	    
	    
	    
	    public void display_With_Modification_AbilityAction( final Udra udra_in , final String ... TitleFrame) // display the array to a graphical user interface, it's possible to give a title to the array
	    {
	        
	    	Windows_is_display = true;

	        
	        final DefaultTableModel Liste = new DefaultTableModel() { //tranform the array to TableModel
	            
	            
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
	        
	        
	        
	        
	        
	        Runnable r = new Runnable() {

	            
	            public void run() {
	               
	                final JTable table = new JTable();
	                table.setModel(Liste);
	                final JFrame frame = new JFrame();
	                if (TitleFrame.length > 0)
	                    frame.setTitle(TitleFrame[0]);
	                else
	                    frame.setTitle(udra_in.getName());
	                frame.add(new JScrollPane(table));
	                
	                //create the button panel
	                JPanel pan = new JPanel();
	                JButton valider = new JButton("Valider");
	                valider.addActionListener(    new ActionListener(){

	                    public void actionPerformed(ActionEvent arg0) {
	                        CellEditor c = table.getCellEditor();
	                        boolean stop=false;
	                        if (c!=null) stop=c.stopCellEditing();
	                        
	                        
	                        frame.dispose();
	                        Windows_is_display = false;
	                        recreer = false;

	                    }         

	                  });
	                pan.add(valider);
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
	                
	                
	                
	                JButton nouvColumn = new JButton("Add a new column");
	                nouvColumn.addActionListener(    new ActionListener(){

	                    public void actionPerformed(ActionEvent arg0) {

	                    	String nameNewColumn =  JOptionPane.showInputDialog("Please set the name of the new column : ");;
	                    	boolean columnCreated = false;
	                    	
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
	                
	                frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
	                frame.getContentPane().add(pan, BorderLayout.SOUTH);	                
	                frame.pack();
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame.setLocation(posX_interface, posY_interface);
	                frame.setSize(width, height);
	                
	                frame.setVisible(true);             
	            }
	        };
   
	        EventQueue.invokeLater(r);
	        
	        while ( Windows_is_display  )
	        {
	            try {    Thread.sleep( 100 );    } catch (InterruptedException e) {    }
	        }
	        
	    }	
	
	
	
	
	
	
}
