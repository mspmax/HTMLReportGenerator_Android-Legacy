package com.maxino.pandlk.htmlreportgenerator.requestdummyvalues;

import android.content.Context;
import android.os.Environment;
import android.provider.ContactsContract;

import com.maxino.pandlk.htmlreportgenerator.DatabaseManager;
import com.maxino.pandlk.htmlreportgenerator.GenerateReportActivity;
import com.maxino.pandlk.htmlreportgenerator.HomeActivity;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by pandlk on 11/16/2015.
 */
public class DummyContent {

    Context mCxt;
    DatabaseManager mDb;

    public DummyContent(Context appContext) {
        this.mCxt = appContext;
        mDb = new DatabaseManager(appContext);
    }

    //Create Request records
    public void createRequests() {
        ArrayList<Request> reqList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            Request req = new Request("",
                    "Dummy Place" + i,
                    "INTERNATIONAL",
                    "SRI LANKA",
                    "17828" + i,
                    "VER 1.0." + i,
                    "Colombo " + i,
                    "OPEN",
                    "PANDLK" + i,
                    "ORDER",
                    "HIGH",
                    "LKR",
                    "LK",
                    "No.12, Best Place, Colombo, Sri Lanka",
                    "COLOMBO GEEKS",
                    "Tech Breakdown",
                    "Rawana Mahasen",
                    "Pandukabaya",
                    "10/12/258" + i,
                    "4/4/201" + i,
                    "28/29/206" + i,
                    "12/12/208" + i,
                    "14/05/201" + i);
            reqList.add(req);
        }
        for (Request req : reqList) {
            mDb.addRequest(req);
        }

    }

    public void createRequestContacts() {
        ArrayList<RequestContact> reqList = new ArrayList<>();

        //request id 1
        for (int i = 1; i < 3; i++) {
            RequestContact reqContact = new RequestContact("", "1", "De Silva", "Mahason", "+94785115697", "1458", "0144478566", "01125874785", "0147852266", "testhtml@maxworld.com", "0147776565", "ADMIN", "ON ROUTE", "Colombo", "11/11/2017");
            reqList.add(reqContact);
        }

        //request id 2
        for (int i = 1; i < 4; i++) {
            RequestContact reqContact = new RequestContact("", "2", "De Silva", "Mahason", "+94785115697", "1458", "0144478566", "01125874785", "0147852266", "testhtmlsuper@maxworld.com", "0147776565", "ADMIN", "ON ROUTE", "Colombo", "11/11/2017");
            reqList.add(reqContact);
        }

        //request id 3
        for (int i = 1; i < 6; i++) {
            RequestContact reqContact = new RequestContact("", "3", "De Silva", "Mahason", "+94785115697", "1458", "0144478566", "01125874785", "0147852266", "testhtmlcheck@maxworld.com", "0147776565", "ADMIN", "ON ROUTE", "Colombo", "11/11/2017");
            reqList.add(reqContact);
        }

        //request id 4
        for (int i = 1; i < 5; i++) {
            RequestContact reqContact = new RequestContact("", "4", "De Silva", "Mahason", "+94785115697", "1458", "0144478566", "01125874785", "0147852266", "testhtmlbad@maxworld.com", "0147776565", "ADMIN", "ON ROUTE", "Colombo", "11/11/2017");
            reqList.add(reqContact);
        }

        for (RequestContact req : reqList) {
            mDb.addRequestContact(req);
        }

    }

    public void createAttachments(){
        ArrayList<RequestAttachment> reqAttachmentsList = new ArrayList<>();
        int j = 1;
        for (int i = 1; i < 5; i++) {
            RequestAttachment requestAttachment1 = new RequestAttachment("",
                     i+"",
                    "attachment_"+j+".jpg",
                    HomeActivity.ATTACHMENT_DIRECTORY+"/attachment_"+j+".jpg",
                    "IMAGE",
                    "Toy Product - "+j,
                    "NEW",
                    "Y",
                    "image/jpg",
                    "11/2/2014");
            reqAttachmentsList.add(requestAttachment1);

            RequestAttachment requestAttachment2 = new RequestAttachment("",
                    i+"",
                    "attachment_"+(j+1)+".jpg",
                    HomeActivity.ATTACHMENT_DIRECTORY+"/attachment_"+(j+1)+".jpg",
                    "IMAGE",
                    "Toy Product - "+(j+1),
                    "NEW",
                    "Y",
                    "image/jpg",
                    "11/2/2014");
            reqAttachmentsList.add(requestAttachment2);

            j=j+2;
        }
        for (RequestAttachment requestAttachment : reqAttachmentsList) {
            mDb.addRequestAttachment(requestAttachment);
        }
    }

    public Hashtable<String, List<String>> getReportUtills() {
        Hashtable<String, List<String>> repUtill = new Hashtable<>();

        //Report Query List
        List<String> queryList = new ArrayList<>();
        queryList.add("select * from request where request_id = '{0}'");
        queryList.add("select * from request_contact where request_id = '{0}'");
        queryList.add("select * from request_attachment where request_id = '{0}'");

        repUtill.put(GenerateReportActivity.REP_QUERY_KEY,queryList);

        List<String> parentTableList = new ArrayList<>();

        //Parent Table List
        parentTableList.add(null);
        parentTableList.add("REQUEST");
        parentTableList.add("REQUEST");

        repUtill.put(GenerateReportActivity.REP_PARENTTABLE_KEY, parentTableList);

        //Conditions Param List
        List<String> conditionsParamList = new ArrayList<>();
        conditionsParamList.add(null);
        conditionsParamList.add("request.request_id");
        conditionsParamList.add("request.request_id");

        repUtill.put(GenerateReportActivity.REP_CONDITIONPARAMS_KEY, conditionsParamList);

        return repUtill;
    }


}
