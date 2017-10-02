package com.shiru.syntaxdb.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.databinding.FragmentConceptBinding;
import com.shiru.syntaxdb.listener.ToolbarListener;
import com.shiru.syntaxdb.utils.KEYS;
import com.shiru.syntaxdb.utils.SharedPreference;
import com.shiru.syntaxdb.views.ToolbarView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConceptListener} interface
 * to handle interaction events.
 * Use the {@link ConceptFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConceptFragment extends Fragment implements ToolbarListener {

    public static final String TAG = "SDB.ConceptFragment";

    private FragmentConceptBinding binding;


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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_concept, container, false);
        findViewsById(binding.getRoot());
        final Concept concept = getArguments().getParcelable(KEYS.KEY_CONCEPT);
        setConcept(concept);
        setupToolbar();

        return binding.getRoot();
    }

    private void findViewsById(View view) {
        TextView exampleTxt = (TextView) view.findViewById(R.id.example_txt);
        TextView headingTitle = (TextView) view.findViewById(R.id.heading_txt);
    }

    @Override
    public void onResume() {
        super.onResume();
        Concept concept = getArguments().getParcelable(KEYS.KEY_CONCEPT);
        SharedPreference.saveRecent(concept);

    }

    private void setupToolbar() {
        ToolbarView view = new ToolbarView(binding.toolbar.realToolbar, getString(R.string.app_name), R.drawable.ic_back_arrow, this, getActivity());
        view.setMenu(R.menu.toolbar_menu);
    }

    private void setConcept(Concept concept) {
        binding.setConcept(concept);
        binding.setTitle(concept.getLanguageLink() + " | " + concept.getName());
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ConceptListener mListener = null;
    }

/*    private void saveOffline(Concept concept) {
        Toast.makeText(getContext(), "Coming soon", Toast.LENGTH_SHORT).show();
    }*/

    @Override
    public void onNavigationClick(View view) {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onMenuClick(MenuItem item) {

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
        void onMenuClick();
    }

}
