package com.shiru.syntaxdb.views;

import android.content.Context;
import android.support.annotation.MenuRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.shiru.syntaxdb.listener.ToolbarListener;
import com.shiru.syntaxdb.utils.SDBUtil;

/**
 * Created by shiru on 1/23/2017.
 */
public class ToolbarView implements Toolbar.OnClickListener, Toolbar.OnMenuItemClickListener {

    private Toolbar toolbar;
    private String title;
    private int navIcon;
    private ToolbarListener mListener;
    private Context mContext;

    public ToolbarView(Toolbar toolbar, String title, int navIcon, ToolbarListener listener) {
        this.toolbar = toolbar;
        this.toolbar.setOnMenuItemClickListener(this);
        mContext = this.toolbar.getContext();
        this.title = title;
        this.navIcon = navIcon;
        this.mListener = listener;
        setupToolbar();
    }

    public void setMenu(@MenuRes int menuResId) {
        toolbar.inflateMenu(menuResId);
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        SDBUtil.Share(mContext);
        return true;
    }
}
