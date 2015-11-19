# HTML5 Report Generator Framework - Android
The solution is based on the document standard HTML5 and the report template has to be self-contained.
This means that no external links are allowed in the document.
All resources used by the document like pictures and film clips has to be encoded as Base64 strings.
Template reports can be stored as regular self-contained HTML5 files.
The report generator engine allows the user to use custom SQL-queries that can be associated with the report template it self in a very simple way.

#Jmustache
Template generator engine is based on jmustache library(https://github.com/samskivert/jmustache)

#Usage
Consider following situation and the table structure,

Parent table 'request'
Child Table1 'request_contact'
Child Table2 'request_attachment'

Report query set attributes in code should be passed as a list<> object(check DummyContent class),
```java
        List<String> queryList = new ArrayList<>();
        queryList.add("select * from request where request_id = '{0}'");
        queryList.add("select * from request_contact where request_id = '{0}'");
        queryList.add("select * from request_attachment where request_id = '{0}'");
```
Conditions param list is identfied according to above query set,
```java
        List<String> conditionsParamList = new ArrayList<>();
        conditionsParamList.add(null);
        conditionsParamList.add("request.request_id");
        conditionsParamList.add("request.request_id");
```
more about conditions, 
in an instance where you get more than one condition it should be concatenated like below,
eg: 
```sql
select * from request_contact where request_id = '{0}' and request_sequence='{1}'
```

So the conditions string should look like : "request.request_id|request.sequence"

List of Parent Tables related to the children entities(Here it's one parent so it should be REQUEST for all children)
```java
        List<String> parentTableList = new ArrayList<>();
        //Parent Table List
        parentTableList.add(null);
        parentTableList.add("REQUEST");
        parentTableList.add("REQUEST");
```
Report Keys of parent, 
eg: In this case the primary key of the respective parent table, 'request' is given.
```sql
select * from request where request_id = '{0}'
```

```java
String[] reportParams= new String[]{"1234"}
```

In a composite key situation in a parent table can be like below,
```java
String[] reportParams= new String[]{"1234","564","789"}
```

#Template

Report template attributes are shown in the following HTML code(You can check out the "RerquestTemplate-DEMO.html" file in the asset folder for the full code). 
Please note that the attributes included in the template should exactly be like the column names in the respective SQL table structure !!

```html
  {{#request.rows}}
            <div>
                <table>
                    <thead>
                        <tr>
                            <td class="bold">Request ID:</td>
                            <td>{{Fields.request_id}}</td>
                            <td class="bold">Created By:</td>
                            <td>{{Fields.order_responsible}}</td>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="bold">Global Name:</td>
                            <td>{{Fields.global_name}}</td>
                            <td class="bold">Place:</td>
                            <td>{{Fields.place_id}}</td>
                        </tr>
                    </tbody>
                </table>
                
                <br>
                
                <h2> Request Contacts</h2>
                <table border="0">
                    <thead>
                        <tr>
                            <th>Contact ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Phone</th>
                            <th>Email</th>
                        </tr>
                    </thead>
                    <tbody>
                        {{#childtable.request_contact.rows}}
                        <tr>
                            <td>{{Fields.contact_id}}</td>
                            <td>{{Fields.first_name}}</td>
                            <td>{{Fields.last_name}}</td>
                            <td>{{Fields.phone}}</td>
                            <td>{{Fields.email_address}}</td>
                        </tr>
                        {{/childtable.request_contact.rows}}
                    </tbody>
                </table>
                 </div>
			
			<h2>Request Images</h2>   
                    <table class="image-table" border="0" >
                    <col style="width:auto">
                    <col style="width:50%">
 
                    <thead>
                        <tr>
                            <th>Description</th>
                            <th>Image</th>
                            
                        </tr>
                    </thead>
                    <tbody >
                         {{#childtable.request_attachment.rows}}
                        <tr>
                            <td>{{Fields.description}}</td>
                            <td ><img src="{{Fields.path-image}}" height="300" ></td>
                            
                        </tr>
                        {{/childtable.request_attachment.rows}}
                    </tbody>
                </table>

            {{/request.rows}}

```

Remember that this is a demo app and dummy data is used(the app was created within just 1.5 days so just by pass the application related bugs ;)). Base Reporting logic implementation is in package '/reporting' you can resue it, improve it or destroy it as you like !

For more info check out my linkedin profile and contact me for any assitance,guidance or whatever,
Linkedin : http://tinyurl.com/nvhkrq9

Feel free to roam around the code and feedback's are always welcome!
