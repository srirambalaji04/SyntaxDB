package com.shiru.syntaxdb.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Concept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shiru on 10/1/2017.
 */

public class ExpandableCategoryAdapter extends AbstractExpandableItemAdapter<ExpandableCategoryAdapter.CategoryGroupViewHolder, ExpandableCategoryAdapter.CategoryChildViewHolder> {

    public static final String TAG = "ExpandableCategoryAdapt";

    private HashMap<Category, List<Concept>> mDataSource;
    private ExpandableChildClickListener mListener;
    private Context mContext;

    // NOTE: Make accessible with short name
    private interface Expandable extends ExpandableItemConstants {
    }

    public interface ExpandableChildClickListener {
        void onChildClick(Concept concept);
    }

    public void setConceptClickListener(ExpandableChildClickListener listener) {
        mListener = listener;
    }

    public ExpandableCategoryAdapter(HashMap<Category, List<Concept>> mDataSource, Context mContext) {
        this.mDataSource = mDataSource;
        this.mContext = mContext;
        setHasStableIds(true);

    }

    class CategoryBaseViewHolder extends AbstractExpandableItemViewHolder {
        CategoryBaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    class CategoryGroupViewHolder extends CategoryBaseViewHolder {
        private TextView groupTitleTxt;
        private TextView groupLangTxt;

        CategoryGroupViewHolder(View itemView) {
            super(itemView);
            groupTitleTxt = (TextView) itemView.findViewById(R.id.cate_txt);
            groupLangTxt = (TextView) itemView.findViewById(R.id.lang_txt);
        }
    }

    class CategoryChildViewHolder extends CategoryBaseViewHolder {

        private TextView childTitleTxt;
        private TextView childLangTxt;
        private ImageView navIcon;

        CategoryChildViewHolder(View itemView) {
            super(itemView);
            childTitleTxt = (TextView) itemView.findViewById(R.id.lang_txt);
            childLangTxt = (TextView) itemView.findViewById(R.id.refer_txt);
            navIcon = (ImageView) itemView.findViewById(R.id.arrow_icn);
        }
    }

    @Override
    public int getGroupCount() {
        return mDataSource.keySet().size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        List<Category> group = new ArrayList<>(mDataSource.keySet());
        Category category = group.get(groupPosition);
        return mDataSource.get(category).size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        List<Category> group = new ArrayList<>(mDataSource.keySet());
        Category category = group.get(groupPosition);
        return category.getId();
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        List<Category> group = new ArrayList<>(mDataSource.keySet());
        Category category = group.get(groupPosition);
        Concept concept = mDataSource.get(category).get(childPosition);
        return Integer.parseInt(concept.getId());
    }

    @Override
    public CategoryGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.parent_list_item, parent, false);
        return new CategoryGroupViewHolder(view);
    }

    @Override
    public CategoryChildViewHolder onCreateChildViewHolder(ViewGroup parent, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        view.setBackgroundColor(ContextCompat.getColor(parent.getContext(), R.color.colorPrimary));
        return new CategoryChildViewHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(CategoryGroupViewHolder holder, int groupPosition, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        List<Category> categories = new ArrayList<>(mDataSource.keySet());
        Category category = categories.get(groupPosition);

        holder.groupTitleTxt.setText(category.getName());
        holder.groupLangTxt.setText(category.getLanguagelink());
        Log.d(TAG, "cateMap01: " + mDataSource.toString());
    }

    @Override
    public void onBindChildViewHolder(CategoryChildViewHolder holder, int groupPosition, int childPosition, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        List<Category> categories = new ArrayList<>(mDataSource.keySet());
        Category category = categories.get(groupPosition);

        List<Concept> concepts = mDataSource.get(category);
        final Concept concept = concepts.get(childPosition);

        holder.childTitleTxt.setText(concept.getName());
        holder.childTitleTxt.setTextColor(ContextCompat.getColor(mContext, android.R.color.white));
        holder.navIcon.setColorFilter(ContextCompat.getColor(mContext, android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        holder.childLangTxt.setVisibility(View.GONE);

        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onChildClick(concept);
                }
            }
        });
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(CategoryGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        return true;
    }
}
