package com.pagatodo.apolo.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.data.model.Promotor;
import com.pagatodo.apolo.ui.base.factoryactivities.BaseActivity;
import com.pagatodo.apolo.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {

    private float m_downX;
    boolean isPageError = false;
    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.main_content_browser) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.ll_error_network) View error_network;
    private Promotor mPromotor = new Promotor();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //Setting Activity ToolBar reference
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        //Enabling Tool Bar Back Arrow Function
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        ButterKnife.bind(this);
        mPromotor = pref.getCurrentPromotor();

        if (TextUtils.isEmpty(Constants.URL_REGISTER_WEB + mPromotor.getID_Promotor())) {
            finish();
        }

        initWebView();


    }

    @Override
    protected int setIdMainView() {
        return 0;
    }

    @Override
    protected int setIdContainerFragments() {
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void initWebView() {

        webView.loadUrl(Constants.URL_REGISTER_WEB + mPromotor.getID_Promotor());

        if(error_network != null){
            error_network.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isPageError = false;
                    initWebView();
                }
            });
        }

        webView.setWebChromeClient(new MyWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
                error_network.setVisibility(View.GONE);
                isPageError = false;
                invalidateOptionsMenu();
            }
            // evita que los enlaces se abran fuera nuestra aplicacion en el navegador de android
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();

                if (isPageError){
                    error_network.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                    webView.clearAnimation();
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
                invalidateOptionsMenu();
                isPageError = true;
            }
            @Override public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                isPageError = true;
            }

        });
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);

        webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        m_downX = event.getX();
                    }
                    break;
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        event.setLocation(m_downX, event.getY());
                    }
                    break;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showView(MenuActivity.class);
        finish();
    }

    private class MyWebChromeClient extends WebChromeClient {
        Context context;
        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }
    }
}
