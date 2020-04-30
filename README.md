# Udra


Udra pour Unique Data Representation and Analyse est une api Java qui comme son nom l'indique permet de récupérer des données depuis de nombreuses sources tel que des classeurs Excel, des bases de données, des flux Json, des API d'y effectuer des opérations diverses et d'enregistrer ces résultats dans de nombreux format tel que Json, Excel, tableauHTML.



<h1>Liste des principales fonctions proposées par Udra</h1>
      
<b>Constructor</b>

<b>chart () :</b>create an empty chart

chart myChart = new chart ();
myChart.display();

<b>chart (String ... title) :</b>create an empty chart with specificate title column

chart myChart = new chart ("title 1" , "title 2");
myChart.display();

Example :

chart (int number_Of_Column) :create an empty chart with specificate number of column

chart myChart = new chart ( 5 );
myChart.display();


<b>chart (int number_Of_Column , int number_Of_Line ) :</b>create an empty chart with specificate number of column and number of line

chart myChart = new chart ( 5 , 10 );
myChart.display();


chart copyFrom(chart) :copy a chart into an other chart

chart myChart = new chart ("title 1" , "title 2");
chart b = copyFrom( myChart );
b.display();

General methods
setName( String name ) :give the name to the chart

chart myChart = new chart ("title 1" , "title 2");
myChart.setName( "My name" );
myChart.display();

Example :

String getName() :get the name of the chart

chart myChart = new chart ("title 1" , "title 2");
myChart.setName( "My name" );
String name = myChart.getName();
reverse() :reverse rows in the chart

chart myChart = new chart ("title 1" );
myChart.setName( "Before reverse" );
myChart.InsertALine( "Line 1" );
myChart.InsertALine( "Line 2" );
myChart.InsertALine( "Line 3" );
myChart.InsertALine( "Line 4" );
myChart.InsertALine( "Line 5" );
myChart.InsertALine( "Line 6" );
myChart.InsertALine( "Line 7" );
myChart.Display();

myChart.setName( "After reverse" );
myChart.Reverse();
myChart.Display();


Example :
Before reverse
 After reverse

String replaceRequest(String request, int row) :create a request from the inital request , typcal use for SQL request

Chart is must to possed title for column, the data to replace is identifie by the name of his column
And name of column to replace is identify by [ ]
for exemple the request : INSERT INTO (...) Values('[Title 1]' , '[Title 2]');
will give : INSERT INTO (...) Values('value cell title 1' , 'value cell title 2');


Action on the chart
clear () : erase the chart

myChart.clear();
boolean isNumber( String OR int ColumnName , int RowNumber ) :test if the cell is a number

boolean test = myChart.isNumber( "title 1" , 10 );
Double getDbl(String OR int ColumnName , int RowNumber) :return the cell like a number

double myValue = myChart.getDbl("title 1" , 10 );
Object get(String OR int ColumnName , int RowNumber) :return the cell like an Object

Object myObject = myChart.get("title 1" , 10 );
setAvalue(String OR int ColumnNumber , int RowNumber , Object value) :set an Object to a cell


Chart data = new Chart ( "my data" );
data.insertALine(); // add an empty line
data.setAvalue("my data" , 0, "Hello world !");

Will be done

int sizeRow() :get the number of row

int sizeColumn() :get the number of column

ArrayList<String> getTitle () :return the list of title

changeTitle (String Original , String New) :change the name of a title

deleteEmptyRow() :delet empty row

delete_duplicate_row(String ... titleColumn ) :delete duplicate row

By default the methode doesn't respect the case,
if no title of Column are given in parametre the test will be done on evry column
delete_duplicate_row(boolean respectCase , String ... titleColumn ) :the same but you can choose to respect or not the case

switch_line(int line1 , int line2) :switch two line

int get_the_index_of_title_from_his_Name(String columnName) :get the index of column designate by his name

orderAscBy(String OR int col) :order the chart by ascendente value

The sort is execute on the column given in parametre
orderDescBy( String OR int col) :order the chart by descendente value

The sort is execute on the column given in parametre
replaceAllNullValueBy(Object newVal) :replace all empty cell by the specificate object

replaceAllValueBy(Number oldValue , Object newVal) :replace all the specificate number value by the specificate object

replaceAllValueBy(String oldValue , Object newVal) :replace all the specificate String value by the specificate object

replaceAllValueBy(Object oldValue , Object newVal) : replace all the specificate Object value by the specificate object

selectWhere(String OR int indexColumn , Object Value) :keep only the row where the value is found in the specificate column

countOccurence(String OR int indexColumn ) : Count the number of each occurence into a column


Chart data = new Chart ( "people" );

for (int i = 0 ; i < 200 ; i ++)
{
if ( Math.random() * 100 < 75)
data.insertALine("Woman");
else
data.insertALine("Man");
}

Chart occ = data.countOccurence("people");
occ.display();



Math
double getMedian( String OR int column ) : return the median of the specificate column


Action on multiple chart
merge(chart second , int OptionofMerge, String ... CoupleOfColumn) : fuse two chart

Information :
The optionofMerge allows the fuse of different way, see the next list of value for more details
1 : merge all data if they can be fused they will are but if they will stay all one into the list
2 : Keep all data of current list but only the data that could be fused to the second list
3 : Keep only fused data

each couple need to be write under this format "title current chart|title second chart"
chart difference(chart seconds, String ... CoupleOfColumn) :keep only the value which not present in the second chart

Each couple of column must be write under this format "title current chart|title second chart"
By default each column are test but we can specificate columns to test.
And case is respected

Example :
chart my_First_Chart = new chart ("title 1" );
my_First_Chart.setName( "my_First_Chart" );
my_First_Chart.InsertALine( "Line 1" );
my_First_Chart.InsertALine( "Line 2" );
my_First_Chart.InsertALine( "Line 3" );
my_First_Chart.InsertALine( "Line 4" );
my_First_Chart.InsertALine( "Line 5" );
my_First_Chart.InsertALine( "Line 6" );
my_First_Chart.InsertALine( "Line 7" );
my_First_Chart.Display();

chart my_Second_Chart = new chart ("column 1" , "col 2" );
my_Second_Chart.setName( "my_Second_Chart" );
my_Second_Chart.InsertALine( "AAA" );
my_Second_Chart.InsertALine( "BBB" );
my_Second_Chart.InsertALine( "CCC" );
my_Second_Chart.InsertALine( "Line 4" );
my_Second_Chart.InsertALine( "Line 5" );
my_Second_Chart.InsertALine( "Line 6" );
my_Second_Chart.InsertALine( "Line 7" );
my_Second_Chart.Display();

my_First_Chart.Difference(my_Second_Chart, "title 1|column 1");
my_First_Chart.Display("final state : my_First_Chart ");


Will give :

chart difference(chart seconds, boolean IgnoreCase , String ... CoupleOfColumn) :the same but you can indic if the case is respected

fuse(chart second) :fuse two chart, is easier then merge

It's easier to use then Merge but have less option,
The second chart is must to have less or same number of column

Example :
chart my_First_Chart = new chart ("title 1" );
my_First_Chart.setName( "my_First_Chart" );
my_First_Chart.InsertALine( "Line 1" );
my_First_Chart.InsertALine( "Line 2" );
my_First_Chart.InsertALine( "Line 3" );
my_First_Chart.InsertALine( "Line 4" );
my_First_Chart.InsertALine( "Line 5" );
my_First_Chart.InsertALine( "Line 6" );
my_First_Chart.InsertALine( "Line 7" );

chart my_Second_Chart = new chart ("column 1" );
my_Second_Chart.setName( "my_Second_Chart" );
my_Second_Chart.InsertALine( "AAA" );
my_Second_Chart.InsertALine( "BBB" );
my_Second_Chart.InsertALine( "CCC" );
my_Second_Chart.InsertALine( "Line 4" );
my_Second_Chart.InsertALine( "Line 5" );
my_Second_Chart.InsertALine( "Line 6" );
my_Second_Chart.InsertALine( "Line 7" );

my_First_Chart.Fused(my_Second_Chart);
my_First_Chart.Display();


Will give :


Action on row
InsertAnArrayList(ArrayList newArray) : Insert a line from an ArrayList

insertALine(Object ... Values ) : Insert a line from object value

chart myChart = new chart( "T1" , "T2" ,"T3");
myChart.insertALine ( "V1" , "V2" , "V3" );
delete_row(int RowNum) : delete the row given in parametre


Action on column
deleteColumn ( String ... Titles_To_Destroy) : Delete specific column

if nothing is given in parametre all column will be destroy
insertAColumn(String NameofNewColumn , Object ... DefaultValue) : Insert a new column into the chart

By default all cell of the new column will be get a null value, if you want to specifie wich value the cell will be get, you must to specifie in the second parametre.
keepOnly (String... Titles_To_Not_Destroy) : delete all the array exepect the speculate collumn

chart keepOnly(int ... NumColumn) : The same but column are iddentify by their index


Chart to String
String toString() : return the chart like a String


Chart data = new Chart ("index" , "character" );
data.setName("My ASCII table");

for (int i = 48 ; i < 58 ; i++)
{
data.insertALine( i , (char) i );
}

System.out.print( data.toString() );

will be done

print() : display the chart into the console like a String


Display the chart
display() : The best methods of chart to display on the screen


In this case the title of the GUI will be the name of the chart

Chart data = new Chart ("index" , "character" );
data.setName("My ASCII table");

for (int i = 0 ; i < 512 ; i++)
{
data.insertALine( i , (char) i );
}

data.display();


display(final String TitleFrame) : The same but you specifie the title of the GUI


In this case the title you specify the title of the interface

Chart data = new Chart ("index" , "character" );
data.setName("My ASCII table");

for (int i = 0 ; i < 512 ; i++)
{
data.insertALine( i , (char) i );
}

data.display("My forced Title");


display_With_Modification_Ability( String Title_of_the_interface) : Display an interface for the chart with modification ability

Display an interface where you can modify the content of the Chart

Note : indicate the title of the interface is optionnal, by default the name of the Chart is used

Chart data = new Chart ("index" , "character" );
data.setName("My ASCII table");

for (int i = 0 ; i < 512 ; i++)
{
data.insertALine( i , (char) i );
}

data.display_With_Modification_Ability();



Draw the chart in line
draw_like_line (String OR int columnData , String OR int columnValue , String OR int columnColor ) : Draw the chart like line


The columns
- Data : Give an information in the dot like timestamp, the date , ...
- Value : Give the amplitude of the dot
- Color : Give the color of the line between two dot


Action
- To zoom use the wheel of the mouse
- To move the graph press the left button of the mouse and move the cursor

Information (only if you want to know more) when you use the draw_like_line( ) method it will return a Drawing_Line_Chart object.
And this object extend the JFrame class.

Simple

//create the sinus chart
Chart sinus = new Chart ("data" , "value" , "Color" );
sinus.setName( "Chart Sinus" );
for (int i = 0 ; i < 2000 ; i++)
{
//insert sinus value
sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 100) , Color.BLUE );
}

sinus.draw_like_line("data" , "value" , "Color");



Gestion of blank

The draw( ) method will replace each line with a null value by a blank on the graph
For example : We set three empty line each six line

//create the sinus chart
Chart sinus = new Chart ("data" , "value" , "Color" );
sinus.setName( "Chart Sinus" );

for (int i = 0 ; i < 2000 ; i++)
{
//insert sinus value
if ( i % 6 > 3)
sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 100) , Color.BLACK );
else
sinus.insertALine();
}

sinus.draw_like_line("data" , "value" , "Color");



Gestion of multi Chart
This kind of graph offer the option to add multi chart on the same interface.
For this you need to use the add( ) method like below.

//create the sinus chart
Chart sinus = new Chart ("data" , "value" , "Color" );
sinus.setName( "Chart Sinus" );

//create the cosinus chart
Chart cosinus = new Chart ( "data" , "value" , "Color" );
cosinus.setName( "Chart Cosinus" );

for (int i = 0 ; i < 200 ; i++)
{
//insert sinus value
sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 10) , new Color(50 , 50 , i) );
//insert cosinus value
cosinus.insertALine( "Dot " + i , Math.cos( ((double)i) / 10) , new Color(50, i, 50) );

}

sinus.draw_like_line("data" , "value" , "Color")
.add(cosinus , "data" , "value" , "Color");



draw_like_line (String OR int columnData , String OR int columnValue ) : Draw the chart like line without need to declare the color

Do the same thing but you don't to declare the color, by default the line will be draw in black

Action
- To zoom use the wheel of the mouse
- To move the graph press the left button of the mouse and move the cursor

//create the sinus chart
Chart sinus = new Chart ("data" , "value" );
sinus.setName( "Chart Sinus" );

for (int i = 0 ; i < 2000 ; i++)
{
//insert sinus value
sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 100) );
}

sinus.draw_like_line("data" , "value" );
draw_like_line ( ) : The quickest way is also the hardest

This method is only to use if your is already formated to be draw,
In fact you can't select the columns to display so your Chart is must to respect this follow format :
First column design the data
Second column design the value
And third column design the Color
The three condition are to respect.

Action
- To zoom use the wheel of the mouse
- To move the graph press the left button of the mouse and move the cursor



//create the sinus chart
Chart sinus = new Chart ("data" , "value" , "Color" );
sinus.setName( "Chart Sinus" );
for (int i = 0 ; i < 2000 ; i++)
{
//insert sinus value
sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 100) , Color.BLUE );
}

sinus.draw_like_line( );



add (String OR int columnData , String OR int columnValue , String OR int columnColor ) : Add a curve to the graph

This kind of graph offer the option to add multi chart on the same interface.
For this you need to use the add( ) method like below.

//create the sinus chart
Chart sinus = new Chart ("data" , "value" , "Color" );
sinus.setName( "Chart Sinus" );

//create the cosinus chart
Chart cosinus = new Chart ( "data" , "value" , "Color" );
cosinus.setName( "Chart Cosinus" );

for (int i = 0 ; i < 200 ; i++)
{
//insert sinus value
sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 10) , new Color(50 , 50 , i) );
//insert cosinus value
cosinus.insertALine( "Dot " + i , Math.cos( ((double)i) / 10) , new Color(50, i, 50) );

}

sinus.draw_like_line("data" , "value" , "Color")
.add(cosinus , "data" , "value" , "Color");


Action
- To zoom use the wheel of the mouse
- To move the graph press the left button of the mouse and move the cursor



add (String OR int columnData , String OR int columnValue ) : The same but you don't select the color

SaveAsJPG (String nameOfFile , boolean close_the_frame ) : Take a picture of the graph

//create the sinus chart
Chart sinus = new Chart ("data" , "value" , "Color" );
sinus.setName( "Chart Sinus" );
for (int i = 0 ; i < 2000 ; i++)
{
//insert sinus value
sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 100) , Color.BLUE );
}

sinus.draw_like_line( ).SaveAsJPG("My file", true);



Draw the chart like a square
draw_like_square (String OR int columnData , String OR int columnValue , String OR int columnColor ) : Draw the chart like square


The columns
- Data : Give the information about the column
- Value : Give the amplitude of the column
- Color : Give the color of the column


Action
- To zoom use the wheel of the mouse
- To move the graph press the left button of the mouse and move the cursor

Information (only if you want to know more) when you use the draw_like_line( ) method it will return a Drawing_Square_Chart object.
And this object extend the JFrame class.

Chart data = new Chart ("data" , "value" , "Color" );
data.setName("My square");

for (int i = 0 ; i < 20 ; i++)
{
data.insertALine("Color " + i , Math.random() * 10 , new Color( 0 , 0 , i * 10 ));
}

data.draw_like_square( "data" , "value" , "Color");


draw_like_square (String OR int columnData ) : Draw the chart like square without need to declare the color

Do the same thing but you don't to declare the color
The first is to not use the color, by default the chart will be drawin grey
Action
- To zoom use the wheel of the mouse
- To move the graph press the left button of the mouse and move the cursor


Chart data = new Chart ("data" , "value" );
data.setName("My square");

for (int i = 0 ; i < 20 ; i++)
{
data.insertALine("Color " + i , Math.random() * 10 );
}

data.draw_like_square( "data" , "value" );




And the second way is to use random color with data.set_draw_in_multi_Color(true)

Chart data = new Chart ("data" , "value" );
data.setName("My square");

for (int i = 0 ; i < 20 ; i++)
{
data.insertALine("Color " + i , Math.random() * 10 );
}

data.set_draw_in_multi_Color(true)
data.draw_like_square( "data" , "value" );


draw_like_square ( ) : The quickest way is also the hardest

This method is only to use if your is already formated to be draw,
In fact you can't select the columns to display so your Chart is must to respect this follow format :
First column design the data
Second column design the value
And third column design the Color
The both first condition are to respect but the third is optionnal.


Action
- To zoom use the wheel of the mouse
- To move the graph press the left button of the mouse and move the cursor


Chart data = new Chart ("data" , "value" );
data.setName("My square");

for (int i = 0 ; i < 20 ; i++)
{
data.insertALine("Color " + i , Math.random() * 10 );
}

data.set_draw_in_multi_Color(true)
data.draw_like_square( );


SaveAsJPG (String nameOfFile , boolean close_the_frame ) : Take a picture of the graph

Work exactly like for the draw_like_line( ) method
Draw the chart like a pie
draw_like_pie (String OR int columnData , String OR int columnValue , String OR int columnColor ) : Draw the chart like pie


The columns
- Data : Give the information about the column
- Value : Give the amplitude of the column
- Color : Give the color of the column


Chart data = new Chart ("data" , "value" , "Color" );
data.setName("My pie");

for (int i = 0 ; i < 5 ; i++)
{
data.insertALine("Color " + i , Math.random() * 10 , new Color ( 0 , 0 , i * 50) );
}

data.draw_like_pie("data" , "value" , "Color" );



draw_like_pie (String OR int columnData , String OR int columnValue ) : Draw the chart like a pie but you don't define the color


The color is define randomously

Chart data = new Chart ("data" , "value" );
data.setName("My pie");

for (int i = 0 ; i < 5 ; i++)
{
data.insertALine("Color " + i , Math.random() * 10 );
}

data.draw_like_pie("data" , "value" );



draw_like_pie ( ) : The quickest way is also the hardest

This method is only to use if your is already formated to be draw,
In fact you can't select the columns to display so your Chart is must to respect this follow format :
First column design the data
Second column design the value
And third column design the Color
The both first condition are to respect but the third is optionnal.


Action
- To zoom use the wheel of the mouse
- To move the graph press the left button of the mouse and move the cursor


Chart data = new Chart ("data" , "value" );
data.setName("My pie");

for (int i = 0 ; i < 5 ; i++)
{
data.insertALine("Color " + i , Math.random() * 10 );
}

data.draw_like_pie( );


SaveAsJPG (String nameOfFile , boolean close_the_frame ) : Take a picture of the graph

Work exactly like for the draw_like_line( ) method
HTML
createFromHTMLtable (String Table) : create from HTML

Use the HTML specification to create the chart

DataBase
String replaceRequestForSQL(String Request, int row) : Create an SQL request from the row of the chart

Like the methods replaceRequest this method will formate the data specificly for the SQL syntax
We give the number of the row convert and the method will return this line formated
ArrayList<chart> createFromSQLDatabse(String HostName , String Base , String User , String PassWord) : create a list of chart from a database

create a list of chart from a database, one chart per table
createFromSQLTable(String HostName , String Base , String User , String PassWord , String Table) : Create the current chart from a SQL table

Implement the chart from a table,
The name of the chart will be the name of the table
clearDatabase(String HostName , String Base , String User , String PassWord) : clear the database ( to use before save )

saveChartInDataBase(String HostName , String Base , String User , String PassWord ) : Save the current chart into a database

The name of the table will be the name of the chart
saveChartInDataBase(String HostName , String Base , String User , String PassWord , String table) : The same but the name of the table is given parametre

simpleQueryToSQLDatabase (String Request, String HostName , String Base , String User , String PassWord ) : send a request to a database

showRequestSended(String Request) : Show the request sended into the consol

queryFromChartToSQLDatabase (String Request, String HostName , String Base , String User , String PassWord ) : send a request to a database


JSON
createFromJSON_URL ( String URL ) : create the chart from a webservice Json

createFromJSON_String(String JSON_String) : Create the chart from a Json String


CSV
saveAsCSV(String URLFile , boolean deletePreviousFile , String ... defaultValue) : Save the current chart into a CSV file

createFromCSVFile(String FileName ) : create the chart from a CSV file

createFromCSV_ArrayString(ArrayList<String> textCSV , String ... chartName) : create the chart from a CSV Array String

create_Multi_chart_from_CSV_Folder(String Folder) : create multi chart from multi csv file

It's use when you have a folder with lot of csv file
The current chart will contain a line per file and each line will be a chart.
CreateFromCSVFolder(String Folder) : create one chart from folder which contain a lot of CSV file

Merge all CSV file into a unique chart

Excel
saveAsExcel(String URLFile , boolean color , String ... defaultValue) : save the chart like an Excel file

You can indicate if you want a file with color or not with the second parameter Note : if the generation of the file doesn't work with the color, try without
saveAsExcel(String URLFile , short ColorHeader , short ColorBody , String ... defaultValue) : The same but you can choose which color to use

You can choose the color to use for the body and for the header.
create_Multi_chart_from_XLS_Folder(String Folder, int firstRow, int firstCell, int lastRow, int lastCell) : work exactly like the CSV method

createFromXLS(String fileURL ) : create the chart from the XLS file

createFromXLS(String fileURL , Object defaultValue) : The same but you choose the Object which be use instead of a null cell

createFromXLS(String fileURL , int FirstRow , int FirstCell , int LastRow , int LastCell , Object ... defaultValue) : The same but you choose the region into the Excel to convert in chart





<br/>
<br/>
<br/>
<br/>
<br/>

<a href="http://www.patinaud.org/Displayer.php?Categorie=Technologies&Page=Developpement&File=Udra,%20gestion%20et%20analyse%20de%20donn%C3%A9es">Projet page</a>



<br/>
<a href="http://www.patinaud.org">Author website</a>
