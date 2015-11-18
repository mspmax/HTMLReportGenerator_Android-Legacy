package com.maxino.pandlk.htmlreportgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.maxino.pandlk.htmlreportgenerator.requestdummyvalues.Request;
import com.maxino.pandlk.htmlreportgenerator.requestdummyvalues.RequestAttachment;
import com.maxino.pandlk.htmlreportgenerator.requestdummyvalues.RequestContact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * Created by pandlk on 11/16/2015.
 */
public class DatabaseManager extends SQLiteOpenHelper {
    private static final String TAG = DatabaseManager.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "request_api";

    // Request table name
    public static final String TABLE_REQUEST = "request";
    // Request Contacts table name
    public static final String TABLE_REQUEST_CONTACT = "request_contact";
    // Request Attachments table name
    public static final String TABLE_REQUEST_ATTACHMENT = "request_attachment";

    // Request Table Columns names
    public static final String KEY_REQUEST_ID = "request_id";
    public static final String KEY_PLACE_ID = "place_id";
    public static final String KEY_GLOBAL_NAME = "global_name";
    public static final String KEY_ORIGIN = "origin";
    public static final String KEY_CONTRACT_ID = "contract_id";
    public static final String KEY_CONTRACT_VERSION = "contract_version";
    public static final String KEY_PLACE_ID_TO_BILL = "place_id_to_bill";
    public static final String KEY_STATUS = "status";
    public static final String KEY_PERSON_ID_OWNER = "person_id_owner";
    public static final String KEY_TYPE = "type";
    public static final String KEY_PRIORITY = "priority";
    public static final String KEY_CURRENCY = "currency";
    public static final String KEY_LOCAL_CODE = "local_code";
    public static final String KEY_ADDRESS_ID = "address_id";
    public static final String KEY_TEAM = "team";
    public static final String KEY_WORK_TYPE = "work_type";
    public static final String KEY_PROJECT_MANAGER = "project_manager";
    public static final String KEY_ORDER_RESPONSIBLE = "order_responsible";
    public static final String KEY_ORDER_DATE = "order_date";
    public static final String KEY_INQUIRY_DATE = "inquiry_date";
    public static final String KEY_PLANNED_START = "planned_start";
    public static final String KEY_PLANNED_END = "planned_end";
    public static final String KEY_CREATED_AT = "created_at";

    // Request Contacts Table Columns names
    public static final String KEY_CONTACT_ID = "contact_id";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EXTENSION = "extension";
    public static final String KEY_ALT_PHONE = "alt_phone";
    public static final String KEY_HOME_PHONE = "home_phone";
    public static final String KEY_FAX = "fax";
    public static final String KEY_EMAIL_ADDRESS = "email_address";
    public static final String KEY_MOBILE_PHONE = "mobile_phone";
    public static final String KEY_ROLE_TYPE = "role_type";
    public static final String KEY_ADDRESS = "address";


    // Request Attachments Table Columns names
    public static final String KEY_ATTACHMENT_ID = "attachment_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PATH = "path";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_ACTIVE = "active";
    public static final String KEY_FILE_TYPE = "fileType";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Request Table
        String CREATE_REQUEST_TABLE = "CREATE TABLE " + TABLE_REQUEST + "("
                + KEY_REQUEST_ID + " INTEGER PRIMARY KEY,"
                + KEY_PLACE_ID + " TEXT,"
                + KEY_GLOBAL_NAME + " TEXT,"
                + KEY_ORIGIN + " TEXT,"
                + KEY_CONTRACT_ID + " TEXT,"
                + KEY_CONTRACT_VERSION + " TEXT,"
                + KEY_PLACE_ID_TO_BILL + " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_PERSON_ID_OWNER + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_PRIORITY + " TEXT,"
                + KEY_CURRENCY + " TEXT,"
                + KEY_LOCAL_CODE + " TEXT,"
                + KEY_ADDRESS_ID + " TEXT,"
                + KEY_TEAM + " TEXT,"
                + KEY_WORK_TYPE + " TEXT,"
                + KEY_PROJECT_MANAGER + " TEXT,"
                + KEY_ORDER_RESPONSIBLE + " TEXT,"
                + KEY_ORDER_DATE + " TEXT,"
                + KEY_INQUIRY_DATE + " TEXT,"
                + KEY_PLANNED_START + " TEXT,"
                + KEY_PLANNED_END + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_REQUEST_TABLE);

        // Request Contact Table
        String CREATE_REQUEST_CONTACT_TABLE = "CREATE TABLE " + TABLE_REQUEST_CONTACT + "("
                + KEY_CONTACT_ID + " INTEGER PRIMARY KEY,"
                + KEY_REQUEST_ID + " TEXT,"
                + KEY_LAST_NAME + " TEXT,"
                + KEY_FIRST_NAME + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_EXTENSION + " TEXT,"
                + KEY_ALT_PHONE + " TEXT,"
                + KEY_HOME_PHONE + " TEXT,"
                + KEY_FAX + " TEXT,"
                + KEY_EMAIL_ADDRESS + " TEXT,"
                + KEY_MOBILE_PHONE + " TEXT,"
                + KEY_ROLE_TYPE + " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_REQUEST_CONTACT_TABLE);


        // Request Attachments Table
        String CREATE_REQUEST_ATTACHMENT_TABLE = "CREATE TABLE " + TABLE_REQUEST_ATTACHMENT + "("
                + KEY_ATTACHMENT_ID + " INTEGER PRIMARY KEY,"
                + KEY_REQUEST_ID + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_PATH + " TEXT,"
                + KEY_TYPE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_STATUS + " TEXT,"
                + KEY_ACTIVE + " TEXT,"
                + KEY_FILE_TYPE + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_REQUEST_ATTACHMENT_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST_CONTACT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST_ATTACHMENT);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     */
    public void addRequest(Request request) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_ID, request.getPlaceId());
        values.put(KEY_GLOBAL_NAME, request.getGlobalName());
        values.put(KEY_ORIGIN, request.getOrigin());
        values.put(KEY_CONTRACT_ID, request.getContractId());
        values.put(KEY_CONTRACT_VERSION, request.getContractVersion());
        values.put(KEY_PLACE_ID_TO_BILL, request.getPlaceIdToBill());
        values.put(KEY_STATUS, request.getStatus());
        values.put(KEY_PERSON_ID_OWNER, request.getPersonIdOwner());
        values.put(KEY_TYPE, request.getType());
        values.put(KEY_PRIORITY, request.getPriority());
        values.put(KEY_CURRENCY, request.getCurrency());
        values.put(KEY_LOCAL_CODE, request.getLocalCode());
        values.put(KEY_ADDRESS_ID, request.getAddressId());
        values.put(KEY_TEAM, request.getTeam());
        values.put(KEY_WORK_TYPE, request.getWorkType());
        values.put(KEY_PROJECT_MANAGER, request.getProjectManager());
        values.put(KEY_ORDER_RESPONSIBLE, request.getOrderResponsible());
        values.put(KEY_ORDER_DATE, request.getOrderDate());
        values.put(KEY_INQUIRY_DATE, request.getInquiryDate());
        values.put(KEY_PLANNED_START, request.getPlannedStart());
        values.put(KEY_PLANNED_END, request.getPlannedEnd());
        values.put(KEY_CREATED_AT, request.getCreatedAt());

        // Inserting Row
        long id = db.insert(TABLE_REQUEST, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New Request inserted into sqlite: " + id);
    }

    public void addRequestContact(RequestContact requestContact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REQUEST_ID, requestContact.getRequestId());
        values.put(KEY_LAST_NAME, requestContact.getLastName());
        values.put(KEY_FIRST_NAME, requestContact.getFirstName());
        values.put(KEY_PHONE, requestContact.getPhone());
        values.put(KEY_EXTENSION, requestContact.getExtension());
        values.put(KEY_ALT_PHONE, requestContact.getAltPhone());
        values.put(KEY_HOME_PHONE, requestContact.getHomePhone());
        values.put(KEY_FAX, requestContact.getFax());
        values.put(KEY_EMAIL_ADDRESS, requestContact.getEmailAddress());
        values.put(KEY_MOBILE_PHONE, requestContact.getMobilePhone());
        values.put(KEY_ROLE_TYPE, requestContact.getRoleType());
        values.put(KEY_STATUS, requestContact.getStatus());
        values.put(KEY_ADDRESS, requestContact.getAddress());
        values.put(KEY_CREATED_AT, requestContact.getCreatedAt());

        // Inserting Row
        long id = db.insert(TABLE_REQUEST_CONTACT, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New Request Contact inserted into sqlite: " + id);
    }

    public void addRequestAttachment(RequestAttachment requestAttachment) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REQUEST_ID, requestAttachment.getRequestId());
        values.put(KEY_NAME, requestAttachment.getName());
        values.put(KEY_PATH, requestAttachment.getPath());
        values.put(KEY_TYPE, requestAttachment.getType());
        values.put(KEY_DESCRIPTION, requestAttachment.getDescription());
        values.put(KEY_STATUS, requestAttachment.getStatus());
        values.put(KEY_ACTIVE, requestAttachment.getActive());
        values.put(KEY_FILE_TYPE, requestAttachment.getFileType());
        values.put(KEY_CREATED_AT, requestAttachment.getCreatedAt());

        // Inserting Row
        long id = db.insert(TABLE_REQUEST_ATTACHMENT, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New Request Attachment inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     */
    public Hashtable<String, String> getValues(String requestQuery) {
        Hashtable<String, String> request = new Hashtable<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(requestQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                request.put(cursor.getColumnName(i), cursor.getString(i));
            }
        }
        cursor.close();
        db.close();
        // return request
        Log.d(TAG, "Fetching values from Sqlite: " + request.toString());

        return request;
    }


    public ArrayList<Hashtable<String, String>> getValueList(String selectQuery) {
        ArrayList<Hashtable<String, String>> valueList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            do {
                Hashtable<String, String> request = new Hashtable<>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    request.put(cursor.getColumnName(i), cursor.getString(i));
                }
                valueList.add(request);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return request
        Log.d(TAG, "Fetching values from Sqlite: " + valueList.toString());

        return valueList;
    }

    /**
     * Re crate database Delete all tables and create them again
     */
    public void deleteRequests() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_REQUEST, null, null);
        db.close();

        Log.d(TAG, "Deleted all Request info from sqlite");
    }

    public void deleteRequestContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_REQUEST_CONTACT, null, null);
        db.close();

        Log.d(TAG, "Deleted all Request Contact info from sqlite");
    }

    public void deleteRequestAttachments() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_REQUEST_ATTACHMENT, null, null);
        db.close();

        Log.d(TAG, "Deleted all Request Attachment info from sqlite");
    }
}
