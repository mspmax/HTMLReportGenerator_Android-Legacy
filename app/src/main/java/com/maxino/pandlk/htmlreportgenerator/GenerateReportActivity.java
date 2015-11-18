package com.maxino.pandlk.htmlreportgenerator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.maxino.pandlk.htmlreportgenerator.reporting.GenericReportUtil;
import com.maxino.pandlk.htmlreportgenerator.requestdummyvalues.DummyContent;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;

public class GenerateReportActivity extends AppCompatActivity {
    private Context mCxt;
    private WebView mGenReportHTMLview;
    private String mGeneratedHTMLReport;
    private String[] mReportParams;
    private DummyContent dumValues;
    private String htmltemplateName = "RequestTemplate-DEMO.html";

    public final static String REP_QUERY_KEY = "rep_query_key";
    public final static String REP_CONDITIONPARAMS_KEY = "rep_conditionparams_key";
    public final static String REP_PARENTTABLE_KEY = "rep_parenttable_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mReportParams = new String[]{getIntent().getExtras().getString(DatabaseManager.KEY_REQUEST_ID)};

        initialize();
    }

    private void initialize() {
        mCxt = this;
        mGenReportHTMLview = (WebView) findViewById(R.id.repGeneratedHTMLview);
        dumValues = new DummyContent(getApplicationContext());

        GenerateReportAsync generateReportAsync = new GenerateReportAsync();
        generateReportAsync.execute(false);
    }

    private void generateReportFromTemplate(Hashtable<String, List<String>> repUtill, InputStream inStream) {

        GenericReportUtil utillObj = new GenericReportUtil(mReportParams, repUtill.get(REP_PARENTTABLE_KEY), repUtill.get(REP_QUERY_KEY), repUtill.get(REP_CONDITIONPARAMS_KEY), inStream, this);

        mGeneratedHTMLReport = utillObj.GenerateReport();

        if (mGeneratedHTMLReport == null)
            mGeneratedHTMLReport = "";

    }


    private class GenerateReportAsync extends AsyncTask<Boolean, Void, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(mCxt);

            progressDialog.setMessage("Report is being generated...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMax(100);
            progressDialog.setCancelable(false);
            progressDialog.show();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }

        }

        @Override
        protected Void doInBackground(Boolean... params) {
            if (!params[0]) {

                AssetManager assetManager = getAssets();

                InputStream input;
                try {
                    input = assetManager.open(htmltemplateName);

                    generateReportFromTemplate(dumValues.getReportUtills(), input);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mGenReportHTMLview.getSettings().setLoadWithOverviewMode(true);
            mGenReportHTMLview.getSettings().setUseWideViewPort(true);
            mGenReportHTMLview.getSettings().setSupportZoom(true);
            mGenReportHTMLview.getSettings().setBuiltInZoomControls(true);
            mGenReportHTMLview.getSettings().setJavaScriptEnabled(true);
            mGenReportHTMLview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            mGenReportHTMLview.loadDataWithBaseURL("", mGeneratedHTMLReport, "text/html", "UTF-8", "");

            progressDialog.dismiss();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }

    }

}
