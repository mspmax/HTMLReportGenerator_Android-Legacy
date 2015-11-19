package com.maxino.pandlk.htmlreportgenerator;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * Created by pandlk on 11/18/2015.
 */
public class AboutUsDialog extends DialogFragment {
    public AboutUsDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_about_us, container);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return rootView;
    }
}
