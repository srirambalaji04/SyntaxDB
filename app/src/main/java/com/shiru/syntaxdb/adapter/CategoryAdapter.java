package com.shiru.syntaxdb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Concept;

import java.util.List;
import java.util.Map;

/**
 * Created by shiru on 1/23/2017.
 */
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_TITLE = 0;
    public static final int VIEW_TYPE_ITEM = 1;

    private List<Category> categories;
    private Map<Category, List<Concept>> notes;

    public CategoryAdapter(Map<Category, List<Concept>> notes) {
        this.notes = notes;
        categories = (List<Category>) notes.keySet();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (viewType == VIEW_TYPE_TITLE) {
            View view = inflater.inflate(R.layout.list_item, parent, false);
            return new ListViewHolder(view);
        } else if (viewType == VIEW_TYPE_ITEM) {
            View view = inflater.inflate(R.layout.parent_list_item, parent, false);
            return new ParentViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Category category = categories.get(position);
        if (holder instanceof ListViewHolder) {
            if (category != null) {
                ((ListViewHolder) holder).title.setText(category.getName());
                ((ListViewHolder) holder).setStatus(VIEW_TYPE_TITLE);

                //  ((ListViewHolder)holder).lang.setText(category.getLanguagelink());

            }
        } else if (holder instanceof ParentViewHolder) {
            List<Concept> concepts = notes.get(category);
            for (Concept concept : concepts) {
                ((ParentViewHolder) holder).title.setText(category.getName());
                ((ParentViewHolder) holder).lang.setText("");
            }
        }

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView lang;
        private int status;

        public ListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.lang_txt);
            lang = (TextView) itemView.findViewById(R.id.refer_txt);
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    class ParentViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView lang;

        public ParentViewHolder(View view) {
            super(view);
            title = (TextView) itemView.findViewById(R.id.cate_txt);
            lang = (TextView) itemView.findViewById(R.id.lang_txt);
        }
    }
}
