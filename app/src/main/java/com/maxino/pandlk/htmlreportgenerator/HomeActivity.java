package com.maxino.pandlk.htmlreportgenerator;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.maxino.pandlk.htmlreportgenerator.requestdummyvalues.DummyContent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

public class HomeActivity extends AppCompatActivity implements RequestListAdapter.OnReportListner {

    private TextView mTxtListEmptyMsg;
    private RequestListAdapter mRequestAdapter;
    private ArrayList<Hashtable<String, String>> mRequestDataList = new ArrayList<>();
    private DatabaseManager mDb;
    private static final String REQUEST_SELECT_QUERY = "select * from request";
    private static final String TAG = HomeActivity.class.getSimpleName();
    public static String ATTACHMENT_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/com.pandlk.htmlgenerator/attachments";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDb = new DatabaseManager(getApplicationContext());


        initialize();
    }

    @Override
    protected void onResume() {
        if (mRequestAdapter != null) {
            mRequestDataList = mDb.getValueList(REQUEST_SELECT_QUERY);

            if (mRequestDataList != null && mRequestDataList.size() > 0) {
                mTxtListEmptyMsg.setVisibility(View.GONE);
            } else {
                mTxtListEmptyMsg.setVisibility(View.VISIBLE);
            }

            mRequestAdapter.swapItems(mRequestDataList);
        }


        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    private void clearUpDummyValues() {
        mDb.deleteRequests();
        mDb.deleteRequestAttachments();
        mDb.deleteRequestContacts();

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void createAttachments(String templateName) {
        // Get the directory for the user's public pictures directory.
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), templateName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //create dummy attachments from raw
        createDummyAttachmentFiles(folder.getPath(), "attachment_1.jpg", R.raw.attachment_1);
        createDummyAttachmentFiles(folder.getPath(), "attachment_2.jpg", R.raw.attachment_2);
        createDummyAttachmentFiles(folder.getPath(), "attachment_3.jpg", R.raw.attachment_3);
        createDummyAttachmentFiles(folder.getPath(), "attachment_4.jpg", R.raw.attachment_4);
        createDummyAttachmentFiles(folder.getPath(), "attachment_5.jpg", R.raw.attachment_5);
        createDummyAttachmentFiles(folder.getPath(), "attachment_6.jpg", R.raw.attachment_6);
        createDummyAttachmentFiles(folder.getPath(), "attachment_7.jpg", R.raw.attachment_7);
        createDummyAttachmentFiles(folder.getPath(), "attachment_8.jpg", R.raw.attachment_8);
        createDummyAttachmentFiles(folder.getPath(), "attachment_9.jpg", R.raw.attachment_9);
        createDummyAttachmentFiles(folder.getPath(), "attachment_10.jpg", R.raw.attachment_10);
    }

    private void createDummyAttachmentFiles(String path, String fileName, int rawRes) {
        File file_ = new File(path, fileName);

        if (!file_.exists()) {
            try {
                InputStream is = getResources().openRawResource(rawRes);
                OutputStream os = new FileOutputStream(file_);
                byte[] data = new byte[is.available()];
                is.read(data);
                os.write(data);
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initialize() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        } else {
            if (isExternalStorageReadable() && isExternalStorageWritable()) {
                createAttachments("com.pandlk.htmlgenerator/attachments");
            }
        }

        clearUpDummyValues();

        DummyContent dummValues = new DummyContent(getApplicationContext());
        dummValues.createRequests();
        dummValues.createRequestContacts();
        dummValues.createAttachments();

        RecyclerView recycleList = (RecyclerView) findViewById(R.id.requestList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleList.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycleList.setHasFixedSize(true);
        mTxtListEmptyMsg = (TextView) findViewById(R.id.txtEmptyListMsg);

        mRequestDataList = mDb.getValueList(REQUEST_SELECT_QUERY);

        if (mRequestDataList != null && mRequestDataList.size() > 0) {
            mTxtListEmptyMsg.setVisibility(View.GONE);
        } else {
            mTxtListEmptyMsg.setVisibility(View.VISIBLE);
        }

        mRequestAdapter = new RequestListAdapter(mRequestDataList, this);
        recycleList.setAdapter(mRequestAdapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (isExternalStorageReadable() && isExternalStorageWritable()) {
                        createAttachments("com.pandlk.htmlgenerator/attachments");
                    }

                } else {

                    Toast.makeText(HomeActivity.this, "The attachment images in the report will not be visible.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about) {
            //if server response is not erroneous clear the metxtNoOfBags field.
            DialogFragment aboutFragment = new AboutUsDialog();
            aboutFragment.show(getSupportFragmentManager(), "about us");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openReport(String requestId) {
        Intent reportIntent = new Intent(this, GenerateReportActivity.class);
        reportIntent.putExtra(DatabaseManager.KEY_REQUEST_ID, requestId);
        startActivity(reportIntent);
    }
}
