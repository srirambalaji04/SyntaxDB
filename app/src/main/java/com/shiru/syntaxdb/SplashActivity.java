package com.shiru.syntaxdb;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.octo.android.robospice.SpiceManager;
import com.shiru.syntaxdb.databinding.ActivitySplashBinding;
import com.shiru.syntaxdb.utils.SDBService;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SDB.SplashActivity";
    private ActivitySplashBinding binding;
    private SpiceManager spiceManager = new SpiceManager(SDBService.class);
    private Thread thread;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
        binding.reqPbar.setVisibility(View.VISIBLE);
        startThread();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
    }

    private void startThread() {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                move();
            }
        };

        thread.start();
    }

    private void move() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        SplashActivity.this.startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (thread) {
                thread.notifyAll();
            }
        }
        return true;
    }

/*    private class LanguagesListener implements RequestListener<LanguagesRsp> {

        @Override
        public void onRequestFailure(SpiceException e) {
            binding.reqPbar.setVisibility(View.GONE);
            Log.e(TAG, String.valueOf(e.getCause()));
            if (e instanceof NoNetworkException) {
                UiUtility.showSnackBar(findViewById(R.id.root_layout), "Switch on the internet to work");
                findViewById(R.id.root_layout).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SplashActivity.this.finish();
                    }
                }, 1000);

            }
        }

        @Override
        public void onRequestSuccess(LanguagesRsp languagesRsp) {
            binding.reqPbar.setVisibility(View.GONE);
            if (languagesRsp != null) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra(KEYS.KEY_LANGUAGE, languagesRsp);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }
    }*/
}
