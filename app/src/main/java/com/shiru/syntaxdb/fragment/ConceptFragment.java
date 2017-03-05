package com.shiru.syntaxdb.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.dao.DatabaseDao;
import com.shiru.syntaxdb.utils.KEYS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConceptListener} interface
 * to handle interaction events.
 * Use the {@link ConceptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConceptFragment extends Fragment {

    public static final String TAG = "SDB.ConceptFragment";

    private TextView syntaxTxt;
    private TextView notesTxt;
    private TextView exampleTxt;
    private ConceptListener mListener;


    public static ConceptFragment newInstance(Concept concept) {

        ConceptFragment fragment = new ConceptFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEYS.KEY_CONCEPT, concept);
        fragment.setArguments(args);
        return fragment;
    }

    public ConceptFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_concept, container, false);
        findViewsById(view);
        final Concept concept = getArguments().getParcelable(KEYS.KEY_CONCEPT);
        setConcept(concept);

        final Button saveBtn = (Button) view.findViewById(R.id.save_offline_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOffline(concept);
            }
        });
        return view;
    }

    private void findViewsById(View view) {
        syntaxTxt = (TextView) view.findViewById(R.id.syntax_txt);
        notesTxt = (TextView) view.findViewById(R.id.notes_txt);
        exampleTxt = (TextView) view.findViewById(R.id.example_txt);
    }

    private void setConcept(Concept concept) {
        syntaxTxt.setText(concept.getSyntax());
        notesTxt.setText(concept.getNotes());
        exampleTxt.setText(concept.getExample());
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void saveOffline(Concept concept){
        DatabaseDao dao = new DatabaseDao(getContext());
        if (dao.insertConcept(concept)){
            Toast.makeText(getContext(), "Saved Concept" +  concept.getName() +"to offline", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "Failed to save offline", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ConceptListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
