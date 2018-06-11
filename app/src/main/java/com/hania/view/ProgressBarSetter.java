package com.hania.view;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

class ProgressBarSetter implements Runnable {

    private Activity activity;

    private ProgressBar progressBar;

    ProgressBarSetter(Activity activity, ProgressBar progressBar) {
        this.activity = activity;
        this.progressBar = progressBar;
    }

    @Override
    public void run() {
        activity.runOnUiThread(new Thread(() -> progressBar.setVisibility(View.VISIBLE)));
    }
}
