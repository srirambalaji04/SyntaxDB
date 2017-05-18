package com.shiru.syntaxdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.shiru.syntaxdb.api.request.GetLanguagesRequest;
import com.shiru.syntaxdb.api.response.bean.LanguagesRsp;
import com.shiru.syntaxdb.utils.KEYS;
import com.shiru.syntaxdb.utils.SDBService;
import com.shiru.syntaxdb.utils.UiUtility;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SDB.SplashActivity";
    private SpiceManager spiceManager = new SpiceManager(SDBService.class);
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        spiceManager.start(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        showAnim();
        dialog = UiUtility.getDialog(this);
        dialog.show();
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
        public void onRequestFailure(SpiceException spiceException) {
            Log.e(TAG, String.valueOf(spiceException.getCause()));
            if (spiceException instanceof NoNetworkException) {
                Toast.makeText(SplashActivity.this, "Please Switch ON the INTERNET", Toast.LENGTH_LONG).show();
                SplashActivity.this.finish();
            }
        }

        @Override
        public void onRequestSuccess(LanguagesRsp languagesRsp) {
            if (languagesRsp != null) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra(KEYS.KEY_LANGUAGE, languagesRsp);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();
            }
        }
    }
}
