package com.maxino.pandlk.htmlreportgenerator.reporting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.maxino.pandlk.htmlreportgenerator.DatabaseManager;
import com.maxino.pandlk.htmlreportgenerator.HomeActivity;
import com.maxino.pandlk.htmlreportgenerator.R;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by pandlk on 11/16/2015.
 */
public class GenericReportUtil {

    private String[] reportParams; //params should be concatenated with a '|' this will be arguments sent by the activity
    private List<String> parentTableNameList;
    private List<String> queryList;
    private List<String> conditionParamsList;
    private String templateFilePath;
    private Context context;
    private HashMap<String, String> mCustSignatureInfo;
    private HashMap<String, String> mTechSignatureInfo;
    private DatabaseManager mDb;
    private InputStream templateStream;


    public static final String REP_SIG_TABLE_NAME = "table_name";
    public static final String REP_SIG_MOBILE_PATH = "mobile_path";
    public static final String REP_SIG_FILE_EXTENSION = "extension";
    public static final String REP_SIG_SIGNER = "signer";
    public static final String REP_SIG_TYPE = "signature_type";

    public GenericReportUtil(String[] reportParams,
                             List<String> parentTableList,
                             List<String> queryList,
                             List<String> conditionParamsList,
                             String templateFilePath,
                             Context context,
                             HashMap<String, String> mCustSignatureInfo,
                             HashMap<String, String> mTechSignatureInfo) {
        this.setReportParams(reportParams);
        this.setConditionParamsList(conditionParamsList);
        this.setQueryList(queryList);
        this.setParentTableList(parentTableList);
        this.templateFilePath = templateFilePath;
        this.context = context;
        this.mCustSignatureInfo = mCustSignatureInfo;
        this.mTechSignatureInfo = mTechSignatureInfo;

    }

    public GenericReportUtil(String[] reportParams,
                             List<String> parentTableList,
                             List<String> queryList,
                             List<String> conditionParamsList,
                             InputStream inputStream,
                             Context context) {
        this.setReportParams(reportParams);
        this.setConditionParamsList(conditionParamsList);
        this.setQueryList(queryList);
        this.setParentTableList(parentTableList);
        this.context = context;
        this.templateStream = inputStream;

    }

    public String GenerateReport() {
        mDb = new DatabaseManager(context);
        if(templateFilePath!=null) {
            File htmlTemplate = new File(templateFilePath);
            if ((htmlTemplate.exists() && htmlTemplate.isFile() && htmlTemplate.length() < 150) || !htmlTemplate.exists()) {
                return "Template file " + templateFilePath + " could not be found";
            } else if (!htmlTemplate.isFile()) {
                return "Template attachment could not be found";
            } else {
                GenericReportTable rootTable = new GenericReportTable();
                ExecuteReportQuery(rootTable, queryList.get(0), getReportParams(),
                        null);
                int depth = 0;
                GenerateReportData(rootTable, depth);

                //TODO Signature support not enabled here.
                //addSignatureToReport(rootTable);

                return generateHTMLReport(rootTable, htmlTemplate);
            }
        }else{
            GenericReportTable rootTable = new GenericReportTable();
            ExecuteReportQuery(rootTable, queryList.get(0), getReportParams(),
                    null);
            int depth = 0;
            GenerateReportData(rootTable, depth);

            //TODO Signature support not enabled here.
            //addSignatureToReport(rootTable);

            return generateHTMLReport(rootTable, templateStream);
        }
    }

    private void addSignatureToReport(GenericReportTable rootTable) {
        List<HashMap<String, String>> mSignatureInfo = new ArrayList<>();
        mSignatureInfo.add(mCustSignatureInfo);
        mSignatureInfo.add(mTechSignatureInfo);

        for (int j = 0; j < mSignatureInfo.size(); j++) {
            if (mSignatureInfo.get(j) != null && !mSignatureInfo.get(j).isEmpty()) {
                String tableName = mSignatureInfo.get(j).get(
                        REP_SIG_TABLE_NAME);
                GenericReportTable test = new GenericReportTable();
                test.setName(tableName);

                GenericReportTable.Row row = test.new Row();

                row.AddField(
                        "user_def19-image",
                        GetConvertedImageFromPath(
                                mSignatureInfo
                                        .get(j)
                                        .get(REP_SIG_MOBILE_PATH),
                                mSignatureInfo
                                        .get(j)
                                        .get(REP_SIG_FILE_EXTENSION)));
                row.AddField(
                        "signer",
                        mSignatureInfo.get(j).get(
                                REP_SIG_SIGNER));

                List<GenericReportTable.Row> sigRows = new ArrayList<>();
                sigRows.add(row);

                test.setTblRows(sigRows);

                rootTable.getTblRows().get(0).getChildTable()
                        .put(tableName, test);
            }
        }

    }

    private String generateHTMLReport(GenericReportTable rootTable, File htmlTemplate) {
        String htmlTemplateOutput = null;
        //Scanner scnr = new Scanner(inStr);
        Scanner scnr = null;
        try {
            scnr = new Scanner(htmlTemplate);
        } catch (FileNotFoundException e) {
            Toast.makeText(context, R.string.CGenericReportUtil_1 + templateFilePath + R.string.CGenericReportUtil_2, Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        String htmltxt = "";
        while (scnr.hasNextLine()) {
            htmltxt = htmltxt + scnr.nextLine();
        }

        scnr.close();

        Template tmpl = Mustache.compiler().defaultValue("").compile(htmltxt);

        HashMap<String, Object> tableMap = new HashMap<>();

        tableMap.put(rootTable.name, rootTable);
        htmlTemplateOutput = tmpl.execute(tableMap);

        return htmlTemplateOutput;

    }

    private String generateHTMLReport(GenericReportTable rootTable, InputStream inStream) {
        String htmlTemplateOutput = null;
        //Scanner scnr = new Scanner(inStr);
        Scanner scnr = null;
        scnr = new Scanner(inStream);

        String htmltxt = "";
        while (scnr.hasNextLine()) {
            htmltxt = htmltxt + scnr.nextLine();
        }

        scnr.close();

        Template tmpl = Mustache.compiler().defaultValue("").compile(htmltxt);

        HashMap<String, Object> tableMap = new HashMap<>();

        tableMap.put(rootTable.name, rootTable);
        htmlTemplateOutput = tmpl.execute(tableMap);

        return htmlTemplateOutput;

    }


    //Generate Report Data Recursively
    private void GenerateReportData(GenericReportTable table, int level) {
        level++;
        for (GenericReportTable.Row row : table.getTblRows()) {
            for (int i = 0; i < queryList.size(); i++) {
                if (table.getName().equalsIgnoreCase(parentTableNameList.get(i))) {

                    GenericReportTable newTable = new GenericReportTable();

                    String[] whereCondition = conditionParamsList.get(i).toLowerCase().split("\\|"); //multiple where clauses broken sperately and put into an array eg: request.request_id|request.name
                    String[] param = new String[whereCondition.length];

                    GenericReportTable.Row searchRow = row;

                    for (int j = 0; j < whereCondition.length; j++) {
                        String keyValue = whereCondition[j];
                        String tableName = keyValue.substring(0, keyValue.indexOf('.')).toLowerCase();
                        String fieldName = keyValue.substring(keyValue.indexOf('.') + 1);

                        do {
                            if (tableName.equalsIgnoreCase(searchRow.getTableName())) {
                                param[j] = searchRow.getFields().get(fieldName);
                                break;
                            }
                            param[j] = "null";
                            searchRow = searchRow.getParentRow();
                        }
                        while (!(searchRow.getParentRow() == null));
                    }


                    ExecuteReportQuery(newTable, queryList.get(i), param, row);
                    row.AddChildTable(newTable);

                    if (level < (queryList.size() - 1)) {
                        GenerateReportData(newTable, level);
                    }


                }
            }
        }
    }

    private String executeReadyQuery(String[] conditionParam, String query) {
        String formattedQuery = null;

        String escapedQuery = query.replace("'", "''");

        try {
            formattedQuery = MessageFormat.format(escapedQuery, (Object[]) conditionParam);
        } catch (IllegalArgumentException e) {

            e.printStackTrace();
        }

        return formattedQuery;
    }

    //Execute SQL Query and process the result.
    private void ExecuteReportQuery(GenericReportTable table, String query, String[] conditionParam, GenericReportTable.Row parentRow) {
        String queryFormatted;
        List<Hashtable<String, String>> queryResult = new ArrayList<Hashtable<String, String>>();
        table.setName(GetTableNameFromQuery(query));

        queryFormatted = executeReadyQuery(conditionParam, query);

        queryResult = mDb.getValueList(queryFormatted);

        if (queryResult != null && queryResult.size() > 0) {
            for (int i = 0; i < queryResult.size(); i++) {
                GenericReportTable.Row row = table.new Row();
                row.setTableName(table.getName());
                row.setParentRow(parentRow);

                Hashtable<String, String> queryReslutRow = queryResult.get(i);

                Set<String> keys = queryResult.get(i).keySet();
                for (String key : keys) {
                    String tempKey = key;
                    String nameKey = key;
                    if (tempKey.contains(".")) {
                        nameKey = tempKey.substring(key.indexOf(".") + 1);
                    }
                    String value = queryReslutRow.get(key);

                    String extension = null;
                    extension = GetFileExtension(value);
                    if (extension != null && (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("gif"))) {
                        row.AddField(nameKey + "-image", GetConvertedImageFromPath(value, extension));
                    } else {
                        row.AddField(nameKey, value);
                    }
                }

                table.addRow(row);
            }

        }
    }

    private String GetFileExtension(String attachmentPath) {
        String[] splitAttachmentpath = attachmentPath.split("\\.");

        String extension = null;
        if (splitAttachmentpath != null && splitAttachmentpath.length > 0) {
            extension = splitAttachmentpath[splitAttachmentpath.length - 1].toLowerCase(new Locale("en"));
        }

        return extension;

    }

    private String GetConvertedImageFromPath(String attachmentPath, String extension) {

        String formattedStr = "";
        if (attachmentPath != null) {

            String temp1 = attachmentPath.replace("\\\\", "/");
            String temp2 = temp1.replace("\\", "/");
            String[] splitFileName = temp2.split("/");

            if (splitFileName != null && splitFileName.length > 0) {
                String fileName = splitFileName[splitFileName.length - 1];

                MimeTypeMap mime = MimeTypeMap.getSingleton();
                String contentType = mime.getMimeTypeFromExtension(extension);
                String filePath = HomeActivity.ATTACHMENT_DIRECTORY +"/"+ fileName;

                try {
                    File f = new File(filePath);
                    if (f.exists() && !f.isDirectory()) {
                        Bitmap bmp = decodeSampledBitmapFromPath(filePath, 500, 500);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.JPEG, 70, bos);

                        String baseImage = encodeBase64(bos.toByteArray());
                        formattedStr = String.format("data:%s;base64,%s", contentType, baseImage);
                    }

                    f = null;

                } catch (FileNotFoundException e) {

                } catch (Exception e) {

                } finally {
                    if (formattedStr == null || formattedStr.isEmpty()) {
                        formattedStr = String.format("data:%s;base64,%s", contentType, "");
                    }
                }
            }
        }

        return formattedStr;

    }

    public static String encodeBase64(byte[] byteout) {
        return Base64.encodeToString(byteout, 0);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromPath(String filePath, int reqWidth, int reqHeight) throws FileNotFoundException {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    private String GetTableNameFromQuery(String query) {
        String[] spliArr = query.toLowerCase(new Locale("en")).split(" from ");

        String tableNameStr = spliArr[1];
        String table = null;

        if (tableNameStr.contains(" ")) {
            if (tableNameStr.indexOf(" ") == 0) {
                tableNameStr.replaceFirst(" ", "");
            }
            table = tableNameStr.substring(0, tableNameStr.indexOf(" "));
        } else {
            table = tableNameStr;
        }

        table.trim();

        return table;
    }

    //Getters and Setters

    public List<String> getParentTableList() {
        return parentTableNameList;
    }


    public void setParentTableList(List<String> parentTableList) {
        this.parentTableNameList = parentTableList;
    }


    public List<String> getQueryList() {
        return queryList;
    }


    public void setQueryList(List<String> queryList) {
        this.queryList = queryList;
    }


    public List<String> getConditionParamsList() {
        return conditionParamsList;
    }


    public void setConditionParamsList(List<String> conditionParamsList) {
        this.conditionParamsList = conditionParamsList;
    }


    private String[] getReportParams() {
        return reportParams;
    }


    private void setReportParams(String[] reportParams) {
        this.reportParams = reportParams;
    }


}
