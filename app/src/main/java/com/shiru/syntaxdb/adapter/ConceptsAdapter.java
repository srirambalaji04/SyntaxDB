package com.shiru.syntaxdb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.bean.Concept;

import java.util.List;

/**
 * Created by shiru on 2/18/2017.
 */
public class ConceptsAdapter extends RecyclerView.Adapter<ConceptsAdapter.ListViewHolder> {

    private Context context;
    private List<Concept> concepts;

    public ConceptsAdapter(Context context, List<Concept> concepts) {
        this.context = context;
        this.concepts = concepts;
    }

    @Override
    public ConceptsAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ConceptsAdapter.ListViewHolder holder, int position) {
        Concept concept = concepts.get(position);
        holder.title.setText(concept.getName());
    }

    @Override
    public int getItemCount() {
        return concepts.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public ListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.lang_txt);
        }
    }

}
