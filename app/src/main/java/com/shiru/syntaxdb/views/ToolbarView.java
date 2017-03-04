package com.shiru.syntaxdb.views;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shiru.syntaxdb.listener.ToolbarListener;

/**
 * Created by shiru on 1/23/2017.
 */
public class ToolbarView implements Toolbar.OnClickListener {

    private Toolbar toolbar;
    private String title;
    private int navIcon;
    private ToolbarListener mListener;

    public ToolbarView(Toolbar toolbar, String title, int navIcon, ToolbarListener listener) {
        this.toolbar = toolbar;
        this.title = title;
        this.navIcon = navIcon;
        this.mListener = listener;
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar.setNavigationIcon(navIcon);
        toolbar.setNavigationOnClickListener(this);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(ContextCompat.getColor(toolbar.getContext(), android.R.color.white));
    }

    @Override
    public void onClick(View v) {
        mListener.onNavigationClick(v);
    }
}
