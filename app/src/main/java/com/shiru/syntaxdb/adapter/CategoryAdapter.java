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
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ListViewHolder> {

    private List<Category> categories;
    private Map<Category, List<Concept>> notes;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public CategoryAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.ListViewHolder holder, int position) {
        Category category = categories.get(position);
        if (category != null) {
            holder.title.setText(category.getName());
            holder.lang.setText(category.getLanguagelink());
        }


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView lang;

        public ListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.lang_txt);
            lang = (TextView) itemView.findViewById(R.id.refer_txt);
        }
    }
}
