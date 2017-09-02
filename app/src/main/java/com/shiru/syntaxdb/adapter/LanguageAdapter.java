package com.shiru.syntaxdb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.bean.Language;

import java.util.List;

/**
 * Created by shiru on 1/16/2017.
 */
public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ListViewHolder> {

    private List<Language> languages;

    public LanguageAdapter(List<Language> languages) {
        this.languages = languages;
    }

    @Override
    public LanguageAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LanguageAdapter.ListViewHolder holder, int position) {
        Language language = languages.get(position);
        holder.title.setText(language.getName());
        holder.lang.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        //        private ImageView langLogo;
        private TextView title;
        private TextView lang;

        public ListViewHolder(View itemView) {
            super(itemView);
//            langLogo = (ImageView) itemView.findViewById(R.id.lang_logo);
            title = (TextView) itemView.findViewById(R.id.lang_txt);
            lang = (TextView) itemView.findViewById(R.id.refer_txt);

        }
    }
}
