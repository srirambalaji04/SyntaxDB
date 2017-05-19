package com.shiru.syntaxdb;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.shiru.syntaxdb.api.request.GetLanguagesRequest;
import com.shiru.syntaxdb.api.response.bean.LanguagesRsp;
import com.shiru.syntaxdb.databinding.ActivitySplashBinding;
import com.shiru.syntaxdb.utils.KEYS;
import com.shiru.syntaxdb.utils.SDBService;
import com.shiru.syntaxdb.utils.UiUtility;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SDB.SplashActivity";
    private ActivitySplashBinding binding;
    private SpiceManager spiceManager = new SpiceManager(SDBService.class);
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
        sendRequest();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
    }

    private void sendRequest() {
        GetLanguagesRequest request = new GetLanguagesRequest();
        LanguagesListener listener = new LanguagesListener();
        spiceManager.execute(request, listener);
    }

    private class LanguagesListener implements RequestListener<LanguagesRsp> {

        @Override
        public void onRequestFailure(SpiceException e) {
            binding.reqPbar.setVisibility(View.GONE);
            Log.e(TAG, String.valueOf(e.getCause()));
            if (e instanceof NoNetworkException) {
                UiUtility.showSnackBar(findViewById(R.id.root_layout), "Switch on the internet to work");
                SplashActivity.this.finish();
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
    }
}
