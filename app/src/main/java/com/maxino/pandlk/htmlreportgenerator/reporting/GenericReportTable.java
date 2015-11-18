package com.maxino.pandlk.htmlreportgenerator.reporting;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by pandlk on 11/16/2015.
 */
public class GenericReportTable {

    public String name;
    public List<Row> rows;


    public GenericReportTable() {
        setTblRows(new ArrayList<Row>());
    }

    public void addRow(Row row){
        this.rows.add(row);
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Row> getTblRows() {
        return rows;
    }

    public void setTblRows(List<Row> tblRows) {
        this.rows = tblRows;
    }

    public class Row{
        public String tableName;
        public Hashtable<String, String> fields;
        public Row parentRow;
        public Hashtable<String, GenericReportTable> childtable;

        public Row() {
            setFields(new Hashtable<String, String>());
            setChildTable(new Hashtable<String, GenericReportTable>());
        }

        public void AddField(String fieldName, String value)
        {
            fields.put(fieldName, value);
        }

        public void AddChildTable(GenericReportTable table)
        {
            this.childtable.put(table.getName(), table);
        }

        //getters and setters
        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public Row getParentRow() {
            return parentRow;
        }

        public void setParentRow(Row parentRow) {
            this.parentRow = parentRow;
        }

        public Hashtable<String, String> getFields() {
            return fields;
        }

        public void setFields(Hashtable<String, String> fields) {
            this.fields = fields;
        }

        public Hashtable<String, GenericReportTable> getChildTable() {
            return childtable;
        }

        public void setChildTable(Hashtable<String, GenericReportTable> childTable) {
            childtable = childTable;
        }
    }

}
