package Library;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.CellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import udra.Udra;
import udra.udra_value;

public class Udra_Lib_Display {


	public static String toString( Udra udra_in)
	{
		
		if ( udra_in.getName() != null && ! udra_in.getName(). replace(" ", "").equals("")  && ! udra_in.getName().isEmpty() )
			return udra_in.getName();
		
		else
			return udra_in.getClass().getName() + '@' + Integer.toHexString(udra_in.hashCode());
		 
	}
	
	

	
	public static String getContentString( Udra udra_in) // display the array to the console
	{
		String content = "";
		 for (int i = 0 ; i < udra_in.getTitle().size() ; i++) //print the title of each column
		 {
			 content = content + (udra_in.getTitle().get(i) + "\t\t");
		 }
		 content = content + "\n";
		 for (int i = 0 ; i < udra_in.sizeRow() ; i++) //print the content of table
		 {
			 for (int j = 0 ; j < udra_in.sizeColumn() ; j++)
			 {
				 content = content + udra_in.get(j, i) + "\t\t";
			 }
			 content = content + "\n";
		 }
		 
		 return content;
	}
	
	
	public static void print( Udra udra_in) // display the array to the console
	{
		 for (int i = 0 ; i < udra_in.getTitle().size() ; i++) //print the title of each column
		 {
			 System.out.print(udra_in.getTitle().get(i) + "\t\t");
		 }
		 System.out.println("");
		 for (int i = 0 ; i < udra_in.sizeRow() ; i++) //print the content of table
		 {
			 for (int j = 0 ; j < udra_in.sizeColumn() ; j++)
			 {
				 System.out.print( udra_in.get(j, i) + "\t\t");
			 }
			 System.out.println();
		 }
	}
	
	
	
	public static void display( Udra udra_in)
	{
		display( udra_in ,null);
	}
	
	
	
	
	public static void display(final Udra udra_in ,final String TitleFrame) // display the array to a graphical user interface, it's possible to give a title to the array
	{
		

    	
    	final TableModel Liste = new TableModel() { //tranform the array to TableModel
			
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {			}
			
			public void removeTableModelListener(TableModelListener l) {			}
			
			public boolean isCellEditable(int rowIndex, int columnIndex) {	return true;	}
			
			public Object getValueAt(int rowIndex, int columnIndex) {
				if(columnIndex == 0)
					return rowIndex;
				
				if( udra_in.get(columnIndex-1, rowIndex) instanceof udra_value)
					return ((udra_value) udra_in.get(columnIndex-1, rowIndex)).display();
					
				return udra_in.get(columnIndex-1, rowIndex) ;
			}
			
			
			public int getRowCount() {
				return udra_in.sizeRow();
			}
			
			
			public String getColumnName(int columnIndex) {
				if (columnIndex == 0)
					return "N°";
				return udra_in.getTitle().get(columnIndex-1);
			}
			
			
			public int getColumnCount() {
				return udra_in.getTitle().size()+1;
			}
			
			
			public Class<?> getColumnClass(int columnIndex) {		
				return Object.class;	
				}
			
			
			public void addTableModelListener(TableModelListener l) {		}
		};
    	
    	
    	
        Runnable r = new Runnable() {

            
            public void run() {
               try
               {
                JTable table = new JTable();
                table.setModel(Liste);

                JFrame frame = new JFrame();
                if (TitleFrame != null)
                	frame.setTitle(TitleFrame);
                else
                	frame.setTitle(udra_in.getName());
                
                //Permet de configurer les scrollbar
                JScrollPane scrollPanel = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            //    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                frame.add(scrollPanel);
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
               }
               catch ( Exception e)
               {
            	//   e.printStackTrace();
               }
            }
        };

        
        EventQueue.invokeLater(r);
        
    }

	
	
	

	
	public static void displayDynamic( final Udra udra_in ,final String TitleFrame) // display the array to a graphical user interface, it's possible to give a title to the array
	{

    	
    	final TableModel Liste = new TableModel() { //tranform the array to TableModel
			
			
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {			}
			
			
			public void removeTableModelListener(TableModelListener l) {			}
			
			
			public boolean isCellEditable(int rowIndex, int columnIndex) {	return true;	}
			
			
			public Object getValueAt(int rowIndex, int columnIndex) {
				if(columnIndex == 0)
					return rowIndex;
				return udra_in.get(columnIndex, rowIndex);
			}
			
			
			public int getRowCount() {
				return udra_in.sizeRow();
			}
			
			
			public String getColumnName(int columnIndex) {
				if (columnIndex == 0)
					return "N°";
				return udra_in.getTitle().get(columnIndex-1);
			}
			
			
			public int getColumnCount() {
				return udra_in.getTitle().size()+1;
			}
			
			
			public Class<?> getColumnClass(int columnIndex) {		
				return Object.class;	
				}
			
			
			public void addTableModelListener(TableModelListener l) {		}
		};
    	
    	
    	
        Runnable r = new Runnable() {

            
            public void run() {
               
                JTable table = new JTable();
                table.setModel(Liste);

                JFrame frame = new JFrame();
                if (TitleFrame != null)
                	frame.setTitle(TitleFrame);
                else
                	frame.setTitle(udra_in.getName());
                	
                frame.add(new JScrollPane(table));
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);             
            }
        };

        
        EventQueue.invokeLater(r);
        
    }
	
	

	
	
}
