# Udra


Udra pour Unique Data Representation and Analyse est une api Java qui comme son nom l'indique permet de récupérer des données depuis de nombreuses sources tel que des classeurs Excel, des bases de données, des flux Json, des API d'y effectuer des opérations diverses et d'enregistrer ces résultats dans de nombreux format tel que Json, Excel, tableauHTML.


<div class="col-md-9 ts-padding img-block-right">
    <div class="feature-content">

<h1>Liste des principales fonctions proposées par Udra</h1>
        <h3>
            <i class="fa fa-minus-circle" aria-hidden="true"></i> Constructor   </h3>
        <div id="Chapitre1" class="panel-collapse in" style="height: auto;">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  chart () </b> :create an empty chart    </div>
            <div id="Descript1" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               chart myChart = new chart (); <br />
                                               myChart.display(); <br /><img src="/images/Udraempty_chart.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  chart (String ... title) </b> :create an empty chart with specificate title column   </div>
            <div id="Descript2" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               chart myChart = new chart ("title 1" , "title 2"); <br />
                                               myChart.display();
											   <br /><br /><b>Example : </b><br /><img src="/images/Udrachart_title.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  chart (int number_Of_Column) </b> :create an empty chart with specificate number of column   </div>
            <div id="Descript3" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               chart myChart = new chart ( 5 ); <br />
                                               myChart.display();<br /><br /><img src="/images/Udra5.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  chart (int number_Of_Column , int number_Of_Line ) </b> :create an empty chart with specificate number of column and number of line   </div>
            <div id="Descript4" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               chart myChart = new chart ( 5 , 10 ); <br />
                                               myChart.display(); <br /><br /><img src="/images/Udra5_10.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  chart copyFrom(chart) </b> :copy a chart into an other chart   </div>
            <div id="Descript5" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               chart myChart = new chart ("title 1" , "title 2"); <br />
                                               chart b = copyFrom( myChart );<br />
                                               b.display();
                               </div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i> General methods  </h3>
        <div id="Chapitre2" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  setName( String name ) </b> :give the name to the chart   </div>
            <div id="Descript6" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               chart myChart = new chart ("title 1" , "title 2"); <br />
                                               myChart.setName( "My name" );<br />
                                               myChart.display();
											   <br /><br /><b> Example : </b><br /><img src=" /images/UdrasetName.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  String getName() </b> :get the name of the chart   </div>
            <div id="Descript7" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               chart myChart = new chart ("title 1" , "title 2");  <br />
                                               myChart.setName( "My name" );<br />
                                               String name = myChart.getName();
                               </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  reverse() </b> :reverse rows in the chart   </div>
            <div id="Descript8" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               chart myChart = new chart ("title 1" ); <br />
                                                               myChart.setName( "Before reverse" );<br />
                                                               myChart.InsertALine( "Line 1" );<br />
                                                               myChart.InsertALine( "Line 2" );<br />
                                                               myChart.InsertALine( "Line 3" );<br />
                                                               myChart.InsertALine( "Line 4" );<br />
                                                               myChart.InsertALine( "Line 5" );<br />
                                                               myChart.InsertALine( "Line 6" );<br />
                                                               myChart.InsertALine( "Line 7" );<br />
                                                               myChart.Display();<br /><br />
                                                               myChart.setName( "After reverse" );<br />
                                                               myChart.Reverse();<br />
                                                               myChart.Display();<br /><br /><br /><b>Example : </b><br />
											   Before reverse<br /><img src="/images/Udrabefore_reverse.PNG" class="img-responsive" alt="" />
											   After reverse<br /><img src="/images/UdraAfter_reverse.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  String replaceRequest(String request, int row) </b> :create a request from the inital request , typcal use for SQL request    </div>
            <div id="Descript9" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               Chart is must to possed title for column, the data to replace is identifie by the name of his column<br />
                                               And name of column to replace is identify by [ ]<br />
                                               for exemple the request : INSERT INTO (...) Values('[Title 1]' , '[Title 2]'); <br />
                                               will give : INSERT INTO (...) Values('value cell title 1' , 'value cell title 2');<br /><br /></div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>  Action on the chart   </h3>
        <div id="Chapitre3" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  clear () </b> : erase the chart   </div>
            <div id="Descript10" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												myChart.clear(); </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  boolean isNumber( String OR int ColumnName , int RowNumber ) </b> :test if the cell is a number   </div>
            <div id="Descript11" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												boolean test = myChart.isNumber( "title 1" , 10 ); </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  Double getDbl(String OR int ColumnName , int RowNumber) </b> :return the cell like a number   </div>
            <div id="Descript14" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												double myValue = myChart.getDbl("title 1" , 10 ); </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  Object get(String OR int ColumnName , int RowNumber) </b> :return the cell like an Object   </div>
            <div id="Descript16" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												Object myObject = myChart.get("title 1" , 10 ); </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  setAvalue(String OR int ColumnNumber , int RowNumber , Object value) </b> :set an Object to a cell   </div>
            <div id="Descript18" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                    <br />
												Chart data = new Chart ( "my data" );<br />
												data.insertALine(); // add an empty line<br />
												data.setAvalue("my data" , 0, "Hello world !");	<br /><br />
												Will be done <br /><img src="/images/Udraset_value.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  int sizeRow() </b> :get the number of row    </div>
            <div id="Descript20" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  int sizeColumn() </b> :get the number of column   </div>
            <div id="Descript21" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  ArrayList&lt;String&gt; getTitle () </b> :return the list of title   </div>
            <div id="Descript22" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  changeTitle (String Original , String New) </b> :change the name of a title   </div>
            <div id="Descript23" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  deleteEmptyRow() </b> :delet empty row   </div>
            <div id="Descript24" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  delete_duplicate_row(String ... titleColumn ) </b> :delete duplicate row    </div>
            <div id="Descript25" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               By default the methode doesn't respect the case, <br />
                                               if no title of Column are given in parametre the test will be done on evry column
                               </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  delete_duplicate_row(boolean respectCase , String ... titleColumn ) </b> :the same but you can choose to respect or not the case   </div>
            <div id="Descript26" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  switch_line(int line1 , int line2) </b> :switch two line   </div>
            <div id="Descript27" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  int get_the_index_of_title_from_his_Name(String columnName) </b> :get the index of column designate by his name   </div>
            <div id="Descript28" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  orderAscBy(String OR int col) </b> :order the chart by ascendente value   </div>
            <div id="Descript29" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               The sort is execute on the column given in parametre
                               </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  orderDescBy( String OR int col) </b> :order the chart by descendente value   </div>
            <div id="Descript31" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               The sort is execute on the column given in parametre
                               </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  replaceAllNullValueBy(Object newVal) </b> :replace all empty cell by the specificate object   </div>
            <div id="Descript33" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  replaceAllValueBy(Number oldValue , Object newVal) </b> :replace all the specificate number value by the specificate object   </div>
            <div id="Descript34" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  replaceAllValueBy(String oldValue , Object newVal) </b> :replace all the specificate String value by the specificate object   </div>
            <div id="Descript35" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  replaceAllValueBy(Object oldValue , Object newVal) </b> : replace all the specificate Object value by the specificate object   </div>
            <div id="Descript36" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  selectWhere(String OR int  indexColumn , Object Value) </b> :keep only the row where the value is found in the specificate column   </div>
            <div id="Descript37" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  countOccurence(String OR int  indexColumn ) </b> : Count the number of each occurence into a column   </div>
            <div id="Descript372" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                    <br />
													Chart data = new Chart ( "people" );<br /><br />
													for (int i = 0 ; i &lt; 200 ; i ++)<br />
													{<br />
														if ( Math.random() * 100  &lt; 75)<br />
															data.insertALine("Woman");<br />
														else<br />
															data.insertALine("Man");<br />
													}<br /><br />
													Chart occ = data.countOccurence("people");<br />
														occ.display();<br /><br /><img src="/images/Udraoccurence.PNG" class="img-responsive" alt="" /></div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>  Math  </h3>
        <div id="Chapitre4" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  double getMedian( String OR int column ) </b> : return the median of the specificate column   </div>
            <div id="Descript39" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>  Action on multiple chart  </h3>
        <div id="Chapitre5" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  merge(chart second , int OptionofMerge, String ... CoupleOfColumn) </b> : fuse two chart  </div>
            <div id="Descript41" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               Information : <br />
                                               The optionofMerge allows the fuse of different way, see the next list of value for more details <br />
                                               1 : merge all data if they can be fused they will are but if they will stay all one into the list<br />
                                               2 : Keep all data of current list but only the data that could be fused to the second list<br />
                                               3 : Keep only fused data<br /><br />
                                               each couple need to be write under this format "title current chart|title second chart"  <br /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  chart difference(chart seconds, String ... CoupleOfColumn) </b> :keep only the value which not present in the second chart   </div>
            <div id="Descript42" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               Each couple of column must be write under this format "title current chart|title second chart" <br />
                                               By default each column are test but we can specificate columns to test. <br />
                                               And case is respected <br /><br />
                                               Example : <br />
												
                                               chart my_First_Chart = new chart ("title 1" );  <br />
                                               my_First_Chart.setName( "my_First_Chart" ); <br />
                                               my_First_Chart.InsertALine( "Line 1" ); <br />
                                               my_First_Chart.InsertALine( "Line 2" ); <br />
                                               my_First_Chart.InsertALine( "Line 3" ); <br />
                                               my_First_Chart.InsertALine( "Line 4" ); <br />
                                               my_First_Chart.InsertALine( "Line 5" ); <br />
                                               my_First_Chart.InsertALine( "Line 6" ); <br />
                                               my_First_Chart.InsertALine( "Line 7" ); <br />
                                               my_First_Chart.Display(); <br /><br />
                                               chart my_Second_Chart = new chart ("column 1" , "col 2" );  <br />
                                               my_Second_Chart.setName( "my_Second_Chart" ); <br />
                                               my_Second_Chart.InsertALine( "AAA" ); <br />
                                               my_Second_Chart.InsertALine( "BBB" ); <br />
                                               my_Second_Chart.InsertALine( "CCC" ); <br />
                                               my_Second_Chart.InsertALine( "Line 4" ); <br />
                                               my_Second_Chart.InsertALine( "Line 5" ); <br />
                                               my_Second_Chart.InsertALine( "Line 6" ); <br />
                                               my_Second_Chart.InsertALine( "Line 7" ); <br />
                                               my_Second_Chart.Display(); <br /><br />
                                               my_First_Chart.Difference(my_Second_Chart, "title 1|column 1");  <br />
                                               my_First_Chart.Display("final state : my_First_Chart "); <br /><br /><br />
                                               Will give : <br /><img src="/images/Udradiff_first.PNG" class="img-responsive" alt="" /><img src="/images/Udradiff_second.PNG" class="img-responsive" alt="" /><img src="/images/Udradiff_result.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  chart difference(chart seconds, boolean IgnoreCase , String ... CoupleOfColumn) </b> :the same but you can indic if the case is respected   </div>
            <div id="Descript421" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  fuse(chart second) </b> :fuse two chart, is easier then merge   </div>
            <div id="Descript43" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                               It's easier to use then Merge but have less option,<br />
                                               The second chart is must to have less or same number of column<br /><br />
                                               Example :<br />
												
                                               chart my_First_Chart = new chart ("title 1" ); <br />
                                               my_First_Chart.setName( "my_First_Chart" );<br />
                                               my_First_Chart.InsertALine( "Line 1" );<br />
                                               my_First_Chart.InsertALine( "Line 2" );<br />
                                               my_First_Chart.InsertALine( "Line 3" );<br />
                                               my_First_Chart.InsertALine( "Line 4" );<br />
                                               my_First_Chart.InsertALine( "Line 5" );<br />
                                               my_First_Chart.InsertALine( "Line 6" );<br />
                                               my_First_Chart.InsertALine( "Line 7" );<br /><br />
                                               chart my_Second_Chart = new chart ("column 1"  ); <br />
                                               my_Second_Chart.setName( "my_Second_Chart" );<br />
                                               my_Second_Chart.InsertALine( "AAA" );<br />
                                               my_Second_Chart.InsertALine( "BBB" );<br />
                                               my_Second_Chart.InsertALine( "CCC" );<br />
                                               my_Second_Chart.InsertALine( "Line 4" );<br />
                                               my_Second_Chart.InsertALine( "Line 5" );<br />
                                               my_Second_Chart.InsertALine( "Line 6" );<br />
                                               my_Second_Chart.InsertALine( "Line 7" );<br /><br />
                                               my_First_Chart.Fused(my_Second_Chart);<br />
                                               my_First_Chart.Display();<br /><br /><br />
                                               Will give : <br /><img src="/images/UdraFuse.PNG" class="img-responsive" alt="" /></div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>             
                 Action on row
 </h3>
        <div id="Chapitre51" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>   InsertAnArrayList(ArrayList newArray) </b> : Insert a line from an ArrayList   
                </div>
            <div id="Desc50" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  insertALine(Object ... Values )  </b> : Insert a line from object value  
                </div>
            <div id="Desc51" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               chart myChart = new chart( "T1" , "T2" ,"T3"); <br />
                                                               myChart.insertALine ( "V1" , "V2" , "V3" );
                                                 </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  delete_row(int RowNum)  </b> : delete the row given in parametre  
                </div>
            <div id="Desc52" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>             
                 Action on column
 </h3>
        <div id="Chapitre6" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  deleteColumn ( String ... Titles_To_Destroy)  </b> :  Delete specific column 
                </div>
            <div id="Desc61" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               if nothing is given in parametre all column will be destroy
                                                              
                                                 </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  insertAColumn(String NameofNewColumn , Object ... DefaultValue)  </b> : Insert a new column into the chart  
                </div>
            <div id="Desc62" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               By default all cell of the new column will be get a null value, if you want to specifie wich value the cell will be get, you must to specifie in the second parametre.
                                                              
                                                 </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  keepOnly (String... Titles_To_Not_Destroy)   </b> :  delete all the array exepect the speculate collumn  
                </div>
            <div id="Desc63" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  chart keepOnly(int ... NumColumn)  </b> : The same but column are iddentify by their index  
                </div>
            <div id="Desc64" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>            
                 Chart to String
 </h3>
        <div id="Chapitre7" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>   String toString() </b> : return the chart like a String 
                </div>
            <div id="Desc65" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                    <br />
													Chart data = new Chart ("index" , "character" );<br />
													data.setName("My ASCII table");<br /><br />
													for (int i = 48 ; i &lt; 58 ; i++)<br />
													{<br />
														data.insertALine( i , (char) i );<br />
													}<br /><br />
													System.out.print(  data.toString() );<br /><br />
													will be done<br /><img src="/images/UdratoString.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b> print()   </b> : display the chart into the console like a String 
                </div>
            <div id="Desc66" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>            
                 Display the chart
 </h3>
        <div id="Chapitre71" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  display()  </b> : The best methods of chart to display on the screen 
                </div>
            <div id="Desc67" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                    <br />
                                                  In this case the title of the GUI will be the name of the chart<br /><br />
													Chart data = new Chart ("index" , "character" );<br />
													data.setName("My ASCII table");<br /><br />
													for (int i = 0 ; i &lt; 512 ; i++)<br />
													{<br />
														data.insertALine( i , (char) i );<br />
													}<br /><br />
													data.display();<br /><br /><img src="/images/UdraDisplay_ascii.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>   display(final String TitleFrame) </b> : The same but you specifie the title of the GUI 
                </div>
            <div id="Desc68" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                    <br />
                                                  In this case the title you specify the title of the interface<br /><br />
													Chart data = new Chart ("index" , "character" );<br />
													data.setName("My ASCII table");<br /><br />
													for (int i = 0 ; i &lt; 512 ; i++)<br />
													{<br />
														data.insertALine( i , (char) i );<br />
													}<br /><br />
													data.display("My forced Title");<br /><br /><img src="/images/Udradisplay_title.PNG" class="img-responsive" alt="" /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  display_With_Modification_Ability( String Title_of_the_interface)  </b> : Display an interface for the chart with modification ability 
                </div>
            <div id="Desc69" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                   Display an interface where you can modify the content of the Chart<br /><br />
												  Note : indicate the title of the interface is optionnal, by default the name of the Chart is used<br /><br />
													Chart data = new Chart ("index" , "character" );<br />
													data.setName("My ASCII table");<br /><br />
													for (int i = 0 ; i &lt; 512 ; i++)<br />
													{<br />
														data.insertALine( i , (char) i );<br />
													}<br /><br />
													data.display_With_Modification_Ability();<br /><br /><img src="/images/Udradisp_modif.PNG" class="img-responsive" alt="" /></div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>            
                 Draw the chart in line
 </h3>
        <!-- div début du chapitre -->
        <div id="ChapitreDraw1" class="panel-collapse collapse">
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  draw_like_line (String OR int columnData , String OR int columnValue , String OR int columnColor )  </b> : Draw the chart like line  
			</div>
            <!-- description méthode -->
            <div id="DescDraw1" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                    <br />
                    <b>The columns </b>
                    <br />
					- Data : Give an information in the dot like timestamp, the date , ...<br />
					- Value : Give the amplitude of the dot<br />
					- Color : Give the color of the line between two dot<br /><br /><br /><b>Action </b><br />
					- To zoom use the wheel of the mouse<br />
					- To move the graph press the left button of the mouse and move the cursor<br /><br /><b>Information </b>(only if you want to know more) when you use the draw_like_line( ) method it will return a Drawing_Line_Chart object. <br />
					And this object extend the JFrame class.<br /><br /><b>Simple</b><br /><br />
					//create the sinus chart<br />
					Chart sinus = new Chart ("data" , "value" , "Color" );<br />
					sinus.setName( "Chart Sinus" );<br />
					for (int i = 0 ; i &lt; 2000 ; i++)<br />
					{<br />
					//insert sinus value<br />
					sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 100)  , Color.BLUE );<br />
					}<br /><br />
					sinus.draw_like_line("data" , "value" , "Color");<br /><br /><img src="/images/UdraLine_Simple.PNG" class="img-responsive" alt="" /><br /><br /><b>Gestion of blank</b><br /><br />
					The draw( ) method will replace each line with a null value by a blank on the graph<br />
					For example : We set three empty line each six line<br /><br />
					//create the sinus chart<br />
					Chart sinus = new Chart ("data" , "value" , "Color" );<br />
					sinus.setName( "Chart Sinus" );<br /><br />
					for (int i = 0 ; i &lt; 2000 ; i++)<br />
					{<br />
					//insert sinus value<br />
					if ( i % 6 &gt; 3)<br />
					sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 100)  , Color.BLACK );<br />
					else<br />
					sinus.insertALine();<br />
					}<br /><br />
					sinus.draw_like_line("data" , "value" , "Color");<br /><br /><img src="/images/UdraLine_blank.PNG" class="img-responsive" alt="" /><br /><br /><b>Gestion of multi Chart</b><br />
					This kind of graph offer the option to add multi chart on the same interface.<br />
					For this you need to use the add( ) method like below.<br /><br />
				   //create the sinus chart<br />
					Chart sinus = new Chart ("data" , "value" , "Color" );<br />
					sinus.setName( "Chart Sinus" );<br /><br />
					//create the cosinus chart<br />
					Chart cosinus = new Chart ( "data" , "value" , "Color" );<br />
					cosinus.setName( "Chart Cosinus" );<br /><br />
					for (int i = 0 ; i &lt; 200 ; i++)<br />
					{<br />
						//insert sinus value<br />
						sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 10)  , new Color(50 , 50 , i) );<br />
					//insert cosinus value<br />
						cosinus.insertALine(  "Dot " + i , Math.cos( ((double)i) / 10) ,  new Color(50, i, 50)  );<br /><br />
					}<br /><br />
					sinus.draw_like_line("data" , "value" , "Color")<br />
						.add(cosinus , "data" , "value" , "Color");<br /><br /><br /><img src="/images/UdraLine_Multi_Chart.PNG" class="img-responsive" alt="" /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  draw_like_line (String OR int columnData , String OR int columnValue )  </b> : Draw the chart like line without need to declare the color  
			</div>
            <!-- description méthode -->
            <div id="DescDraw2" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
					
					Do the same thing but you don't to declare the color, by default the line will be draw in black<br /><br /><b>Action </b><br />
					- To zoom use the wheel of the mouse<br />
					- To move the graph press the left button of the mouse and move the cursor<br /><br />
					
				   		//create the sinus chart<br />
						Chart sinus = new Chart ("data" , "value" );<br />
						sinus.setName( "Chart Sinus" );<br /><br />
						for (int i = 0 ; i &lt; 2000 ; i++)<br />
						{<br />
							//insert sinus value<br />
							sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 100) );<br />
						}<br /><br />
						sinus.draw_like_line("data" , "value" );<br /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  draw_like_line ( )  </b> : The quickest way is also the hardest 
			</div>
            <!-- description méthode -->
            <div id="DescDraw3" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
					
					This method is only to use if your is already formated to be draw,<br />
					In fact you can't select the columns to display so your Chart is must to respect this follow format :<br />
					First column design the data<br />
					Second column design the value<br />
					And third column design the Color<br />
					The three condition are to respect.<br /><br /><b>Action </b><br />
					- To zoom use the wheel of the mouse<br />
					- To move the graph press the left button of the mouse and move the cursor<br /><br /><br /><br />
					//create the sinus chart<br />
					Chart sinus = new Chart ("data" , "value" , "Color" );<br />
					sinus.setName( "Chart Sinus" );<br />
					for (int i = 0 ; i &lt; 2000 ; i++)<br />
					{<br />
					//insert sinus value<br />
					sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 100)  , Color.BLUE );<br />
					}<br /><br />
					sinus.draw_like_line( );<br /><br /><img src="/images/UdraLine_Simple.PNG" class="img-responsive" alt="" /><br /><br /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  add (String OR int columnData , String OR int columnValue , String OR int columnColor )  </b> : Add a curve to the graph 
			</div>
            <!-- description méthode -->
            <div id="DescDraw4" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
					
					This kind of graph offer the option to add multi chart on the same interface.<br />
					For this you need to use the add( ) method like below.<br /><br />
				   //create the sinus chart<br />
					Chart sinus = new Chart ("data" , "value" , "Color" );<br />
					sinus.setName( "Chart Sinus" );<br /><br />
					//create the cosinus chart<br />
					Chart cosinus = new Chart ( "data" , "value" , "Color" );<br />
					cosinus.setName( "Chart Cosinus" );<br /><br />
					for (int i = 0 ; i &lt; 200 ; i++)<br />
					{<br />
						//insert sinus value<br />
						sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 10)  , new Color(50 , 50 , i) );<br />
					//insert cosinus value<br />
						cosinus.insertALine(  "Dot " + i , Math.cos( ((double)i) / 10) ,  new Color(50, i, 50)  );<br /><br />
					}<br /><br />
					sinus.draw_like_line("data" , "value" , "Color")<br />
						.add(cosinus , "data" , "value" , "Color");<br /><br /><br /><b>Action </b><br />
					- To zoom use the wheel of the mouse<br />
					- To move the graph press the left button of the mouse and move the cursor<br /><br /><br /><img src="/images/UdraLine_Multi_Chart.PNG" class="img-responsive" alt="" /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  add (String OR int columnData , String OR int columnValue  )  </b> : The same but you don't select the color 
			</div>
            <!-- description méthode -->
            <div id="DescDraw5" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                    <!-- 2 div pour la fin de la description de la méthode -->
                </div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  SaveAsJPG (String nameOfFile , boolean close_the_frame  )  </b> : Take a picture of the graph 
			</div>
            <!-- description méthode -->
            <div id="DescDraw51" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
					
				//create the sinus chart<br />
					Chart sinus = new Chart ("data" , "value" , "Color" );<br />
					sinus.setName( "Chart Sinus" );<br />
					for (int i = 0 ; i &lt; 2000 ; i++)<br />
					{<br />
					//insert sinus value<br />
					sinus.insertALine( "Dot " + i , Math.sin( ((double)i) / 100)  , Color.BLUE );<br />
					}<br /><br />
					sinus.draw_like_line( ).SaveAsJPG("My file", true);<br /><br /><img src="/images/UdraLine_Simple.PNG" class="img-responsive" alt="" /><br /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- div du chapitre -->
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>            
                 Draw the chart like a square
 </h3>
        <!-- div début du chapitre -->
        <div id="ChapitreDraw2" class="panel-collapse collapse">
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  draw_like_square (String OR int columnData , String OR int columnValue , String OR int columnColor )  </b> : Draw the chart like square  
			</div>
            <!-- description méthode -->
            <div id="DescDraw7" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                    <br />
                    <b>The columns </b>
                    <br />
					- Data : Give the information about the column<br />
					- Value : Give the amplitude of the column<br />
					- Color : Give the color of the column<br /><br /><br /><b>Action </b><br />
					- To zoom use the wheel of the mouse<br />
					- To move the graph press the left button of the mouse and move the cursor<br /><br /><b>Information </b>(only if you want to know more) when you use the draw_like_line( ) method it will return a Drawing_Square_Chart object. <br />
					And this object extend the JFrame class.<br /><br />
					
					Chart data = new Chart ("data" , "value" , "Color" ); <br />
					data.setName("My square");<br /><br />
					for (int i = 0 ; i &lt; 20 ; i++)<br />
					{<br />
						data.insertALine("Color " + i , Math.random() * 10 , new Color( 0 , 0 , i * 10 ));<br />
					}<br /><br />
					data.draw_like_square( "data" , "value" , "Color");<br /><br /><img src="/images/UdraSquare_simple.PNG" class="img-responsive" alt="" /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  draw_like_square (String OR int columnData )  </b> : Draw the chart like square without need to declare the color  
			</div>
            <!-- description méthode -->
            <div id="DescDraw8" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
					
					Do the same thing but you don't to declare the color<br />
					The first is to not use the color, by default the chart will be drawin grey
					
					<br /><b>Action </b><br />
					- To zoom use the wheel of the mouse<br />
					- To move the graph press the left button of the mouse and move the cursor<br /><br /><br />
					
					Chart data = new Chart ("data" , "value"  ); <br />
					data.setName("My square");<br /><br />
					for (int i = 0 ; i &lt; 20 ; i++)<br />
					{<br />
						data.insertALine("Color " + i , Math.random() * 10  );<br />
					}<br /><br />
					data.draw_like_square( "data" , "value" );<br /><br /><img src="/images/UdraSquare_grey.PNG" class="img-responsive" alt="" /><br /><br /><br />		
				   And the second way is to use random color with <b>data.set_draw_in_multi_Color(true)</b><br /><br />		
					
					
					Chart data = new Chart ("data" , "value"  ); <br />
					data.setName("My square");<br /><br />
					for (int i = 0 ; i &lt; 20 ; i++)<br />
					{<br />
						data.insertALine("Color " + i , Math.random() * 10  );<br />
					}<br /><br />
					data.set_draw_in_multi_Color(true)<br />
					data.draw_like_square( "data" , "value" );<br /><br /><img src="/images/UdraSquare_multi_color.PNG" class="img-responsive" alt="" /><br /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  draw_like_square ( )  </b> : The quickest way is also the hardest 
			</div>
            <!-- description méthode -->
            <div id="DescDraw9" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
					
					This method is only to use if your is already formated to be draw,<br />
					In fact you can't select the columns to display so your Chart is must to respect this follow format :<br />
					First column design the data<br />
					Second column design the value<br />
					And third column design the Color<br />
					The both first condition are to respect but the third is optionnal.<br /><br /><br /><b>Action </b><br />
					- To zoom use the wheel of the mouse<br />
					- To move the graph press the left button of the mouse and move the cursor<br /><br /><br />					
					
					Chart data = new Chart ("data" , "value"  ); <br />
					data.setName("My square");<br /><br />
					for (int i = 0 ; i &lt; 20 ; i++)<br />
					{<br />
						data.insertALine("Color " + i , Math.random() * 10  );<br />
					}<br /><br />
					data.set_draw_in_multi_Color(true)<br />
					data.draw_like_square(  );<br /><br /><img src="/images/UdraSquare_multi_color.PNG" class="img-responsive" alt="" /><br /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  SaveAsJPG (String nameOfFile , boolean close_the_frame  )  </b> : Take a picture of the graph 
			</div>
            <!-- description méthode -->
            <div id="DescDraw10" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
					
							Work exactly like for the draw_like_line( ) method
				   
			 <!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- div du chapitre -->
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>            
                 Draw the chart like a pie
 </h3>
        <!-- div début du chapitre -->
        <div id="ChapitreDraw3" class="panel-collapse collapse">
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  draw_like_pie (String OR int columnData , String OR int columnValue , String OR int columnColor )  </b> : Draw the chart like pie  
			</div>
            <!-- description méthode -->
            <div id="DescDraw12" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                    <br />
                    <b>The columns </b>
                    <br />
					- Data : Give the information about the column<br />
					- Value : Give the amplitude of the column<br />
					- Color : Give the color of the column<br /><br /><br />

					
						
					
					Chart data = new Chart ("data" , "value" , "Color" ); <br />
					data.setName("My pie");<br /><br />
					for (int i = 0 ; i &lt; 5 ; i++)<br />
					{<br />
						data.insertALine("Color " + i , Math.random() * 10 , new Color ( 0 , 0 , i * 50)  );<br />
					}<br /><br />
					data.draw_like_pie("data" , "value" , "Color" );<br /><br /><br /><img src="/images/UdraPie_Color.PNG" class="img-responsive" alt="" /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  draw_like_pie (String OR int columnData , String OR int columnValue  )  </b> : Draw the chart like a pie but you don't define the color  
			</div>
            <!-- description méthode -->
            <div id="DescDraw13" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                    <br />
					The color is define randomously<br /><br />
					
					Chart data = new Chart ("data" , "value" ); <br />
					data.setName("My pie");<br /><br />
					for (int i = 0 ; i &lt; 5 ; i++)<br />
					{<br />
						data.insertALine("Color " + i , Math.random() * 10  );<br />
					}<br /><br />
					data.draw_like_pie("data" , "value"  );<br /><br /><br /><img src="/images/UdraPie_random_color.PNG" class="img-responsive" alt="" /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  draw_like_pie ( )  </b> : The quickest way is also the hardest 
			</div>
            <!-- description méthode -->
            <div id="DescDraw14" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
					
					This method is only to use if your is already formated to be draw,<br />
					In fact you can't select the columns to display so your Chart is must to respect this follow format :<br />
					First column design the data<br />
					Second column design the value<br />
					And third column design the Color<br />
					The both first condition are to respect but the third is optionnal.<br /><br /><br /><b>Action </b><br />
					- To zoom use the wheel of the mouse<br />
					- To move the graph press the left button of the mouse and move the cursor<br /><br /><br />					
					
					Chart data = new Chart ("data" , "value"  ); <br />
					data.setName("My pie");<br /><br />
					for (int i = 0 ; i &lt; 5 ; i++)<br />
					{<br />
						data.insertALine("Color " + i , Math.random() * 10  );<br />
					}<br /><br />
					data.draw_like_pie(  );<br /><br /><img src="/images/UdraPie_multi_color.PNG" class="img-responsive" alt="" /><br /><!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- Titre de la méthode -->
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  SaveAsJPG (String nameOfFile , boolean close_the_frame  )  </b> : Take a picture of the graph 
			</div>
            <!-- description méthode -->
            <div id="DescDraw102" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
					
							Work exactly like for the draw_like_line( ) method
				   
			 <!-- 2 div pour la fin de la description de la méthode --></div>
            </div>
            <!-- div du chapitre -->
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>             
                 HTML
 </h3>
        <div id="Chapitre8" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  createFromHTMLtable (String Table)  </b> : create from HTML  
                </div>
            <div id="Desc811" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               Use the HTML specification to create the chart
                                                              
                                                 </div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>             
                 DataBase
 </h3>
        <div id="Chapitre9" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  String replaceRequestForSQL(String Request, int row)   </b> : Create an SQL request from the row of the chart  
                </div>
            <div id="Desc90" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                                              Like the methods replaceRequest this method will formate the data specificly for the SQL syntax <br />
                                                                              We give the number of the row convert and the method will return this line formated<br /></div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  ArrayList&lt;chart&gt; createFromSQLDatabse(String HostName , String Base , String User , String PassWord)  </b> : create a list of chart from a database  
                </div>
            <div id="Desc91" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               create a list of chart from a database, one chart per table
                                                              
                                                 </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  createFromSQLTable(String HostName , String Base , String User , String PassWord , String Table)  </b> : Create the current chart from a SQL table   
                </div>
            <div id="Desc92" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               Implement the chart from a table, <br />
                                                               The name of the chart will be the name of the table
                                                 </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b> clearDatabase(String HostName , String Base , String User , String PassWord)   </b> : clear the database ( to use before save ) 
                </div>
            <div id="Desc93" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  saveChartInDataBase(String HostName , String Base , String User , String PassWord )  </b> : Save the current chart into a database  
                </div>
            <div id="Desc94" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               The name of the table will be the name of the chart
                                                              
                                                 </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  saveChartInDataBase(String HostName , String Base , String User , String PassWord , String table)  </b> :  The same but the name of the table is given parametre 
                </div>
            <div id="Desc95" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  simpleQueryToSQLDatabase (String Request, String HostName , String Base , String User , String PassWord )  </b> : send a request to a database  
                </div>
            <div id="Desc96" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  showRequestSended(String Request)  </b> : Show the request sended into the consol  
                </div>
            <div id="Desc97" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  queryFromChartToSQLDatabase (String Request, String HostName , String Base , String User , String PassWord )  </b> : send a request to a database  
                </div>
            <div id="Desc99" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>           
                 JSON
 </h3>
        <div id="Chapitre10" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  createFromJSON_URL ( String URL )  </b> : create the chart from a webservice Json  
                </div>
            <div id="Desc101" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  createFromJSON_String(String JSON_String)  </b> : Create the chart from a Json String  
                </div>
            <div id="Desc104" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>           
                 CSV
 </h3>
        <div id="Chapitre11" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b> saveAsCSV(String URLFile , boolean deletePreviousFile , String ... defaultValue)   </b> : Save the current chart into a CSV file  
                </div>
            <div id="Desc105" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b> createFromCSVFile(String FileName )   </b> : create the chart from a CSV file  
                </div>
            <div id="Desc106" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  createFromCSV_ArrayString(ArrayList&lt;String&gt; textCSV , String ... chartName)  </b> : create the chart from a CSV Array String 
                </div>
            <div id="Desc107" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  create_Multi_chart_from_CSV_Folder(String Folder)  </b> : create multi chart  from multi csv file 
                </div>
            <div id="Desc108" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               It's use when you have a folder with lot of csv file <br />
                                                               The current chart will contain a line per file and each line will be a chart.
                                                              
                                                 </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b> CreateFromCSVFolder(String Folder)   </b> : create one chart from folder which contain a lot of CSV file  
                </div>
            <div id="Desc109" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               Merge all CSV file into a unique chart
                                                              
                                                 </div>
            </div>
        </div>
        <br />
        <h3>
            <i class="fa fa-plus-circle" aria-hidden="true"></i>           
                 Excel
 </h3>
        <div id="Chapitre12" class="panel-collapse collapse">
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  saveAsExcel(String URLFile , boolean color , String ... defaultValue)  </b> : save the chart like an Excel file  
                </div>
            <div id="Desc110" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               You can indicate if you want a file with color or not with the second parameter
                                                               Note : if the generation of the file doesn't work with the color, try without
                                                 </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  saveAsExcel(String URLFile , short ColorHeader , short ColorBody , String ... defaultValue)  </b> : The same but you can choose which color to use  
                </div>
            <div id="Desc111" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
												
                                                               You can choose the color to use for the body and for the header.
                                                              
                                                 </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  create_Multi_chart_from_XLS_Folder(String Folder, int firstRow, int firstCell, int lastRow, int lastCell)  </b> : work exactly like the CSV method  
                </div>
            <div id="Desc112" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  createFromXLS(String fileURL )  </b> : create the chart from the XLS file   
                </div>
            <div id="Desc113" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b>  createFromXLS(String fileURL , Object defaultValue)  </b> : The same but you choose the Object which be use instead of a null cell   
                </div>
            <div id="Desc114" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
            <div style="margin-left: 25px; font-size:16px;">
                <i class="fa fa-plus-circle" aria-hidden="true"></i>
                <b> createFromXLS(String fileURL , int FirstRow , int FirstCell , int LastRow , int LastCell , Object ... defaultValue)   </b> : The same but you choose the region into the Excel to convert in chart  
                </div>
            <div id="Desc115" class="panel-collapse collapse">
                <div style="margin-left: 50px;">
                    <br />
                </div>
            </div>
        </div>
    </div>
</div>