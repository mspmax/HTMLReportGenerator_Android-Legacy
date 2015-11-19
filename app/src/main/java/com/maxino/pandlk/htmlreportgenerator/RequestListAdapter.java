package com.maxino.pandlk.htmlreportgenerator;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by pandlk on 11/16/2015.
 */
public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder> {

    private ArrayList<Hashtable<String, String>> mDataset;
    Activity mCxt;
    private OnReportListner OnReportListner;

    // Provide a suitable constructor (depends on the kind of dataset)
    public RequestListAdapter(ArrayList<Hashtable<String, String>> myDataset,Activity mCxt) {
        mDataset = myDataset;
        this.mCxt=mCxt;
        OnReportListner = (OnReportListner) mCxt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTxtRequestId.setText(mDataset.get(position).get(DatabaseManager.KEY_REQUEST_ID));
        holder.mTxtRequestStatus.setText(mDataset.get(position).get(DatabaseManager.KEY_STATUS));
        holder.mTxtPersonIdOwner.setText(mDataset.get(position).get(DatabaseManager.KEY_PERSON_ID_OWNER));
        holder.mTxtPlace.setText(mDataset.get(position).get(DatabaseManager.KEY_PLACE_ID));
        holder.mTxtOrderDate.setText(mDataset.get(position).get(DatabaseManager.KEY_ORDER_DATE));

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (!isLongClick) {
                    OnReportListner.openReport(mDataset.get(position).get(DatabaseManager.KEY_REQUEST_ID));
                }
            }
        });
    }

    public interface OnReportListner {
        void openReport(String requestId);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void swapItems(ArrayList<Hashtable<String, String>> items) {
        this.mDataset = items;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        // each data item is just a string in this case
        public TextView mTxtRequestId;
        public TextView mTxtRequestStatus;
        public TextView mTxtPersonIdOwner;
        public TextView mTxtPlace;
        public TextView mTxtOrderDate;

        private ItemClickListener clickListener;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
            mTxtRequestId = (TextView) v.findViewById(R.id.txtRequestId);
            mTxtRequestStatus = (TextView) v.findViewById(R.id.txtStatus);
            mTxtPersonIdOwner = (TextView) v.findViewById(R.id.txtPersonIdOwner);
            mTxtPlace = (TextView) v.findViewById(R.id.txtPlace);
            mTxtOrderDate = (TextView) v.findViewById(R.id.txtOrderDate);

        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onClick(v, getPosition(), true);
            return true;
        }
    }


}
