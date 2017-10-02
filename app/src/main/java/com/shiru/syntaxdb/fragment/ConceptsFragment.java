package com.shiru.syntaxdb.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.adapter.ConceptsAdapter;
import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.dao.DatabaseDao;
import com.shiru.syntaxdb.databinding.FragmentConceptsBinding;
import com.shiru.syntaxdb.listener.ItemClickSupport;
import com.shiru.syntaxdb.listener.ToolbarListener;
import com.shiru.syntaxdb.utils.KEYS;
import com.shiru.syntaxdb.views.ToolbarView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.shiru.syntaxdb.fragment.ConceptsFragment.ConceptsInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConceptsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConceptsFragment extends Fragment implements ToolbarListener {

    public static final String TAG = "SDB.ConceptsFragment";

    private FragmentConceptsBinding binding;
    private RecyclerView mRecyclerView;
    private ConceptsInteractionListener mListener;

    public ConceptsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ConceptsFragment.
     */
    public static ConceptsFragment newInstance(Category category) {
        ConceptsFragment fragment = new ConceptsFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEYS.KEY_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_concepts, container, false);
        findViewsById(binding.getRoot());
        setupToolbar();
        if (getArguments().containsKey(KEYS.KEY_CATEGORY)) {
            Category category = (Category) getArguments().getParcelable(KEYS.KEY_CATEGORY);
            setTitle(category);
            GetConceptsTask task = new GetConceptsTask();
            task.execute(category);
        }

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void findViewsById(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.concepts_list);
    }

    private void setAdapter(final List<Concept> conceptList) {
        ConceptsAdapter adapter = new ConceptsAdapter(getContext(), conceptList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onConceptSelected(conceptList.get(position));
                    }
                }, 200);
            }
        });
    }

    private void setupToolbar() {
        ToolbarView view = new ToolbarView(binding.toolbar.realToolbar, getString(R.string.app_name), R.drawable.ic_back_arrow, this, getActivity());
        view.setMenu(R.menu.toolbar_menu);
    }

    private void setTitle(Category category) {
        binding.setTitle(category.getLanguagelink() + " | " + category.getName());
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (ConceptsInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ConceptListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onNavigationClick(View view) {
        getFragmentManager().popBackStack();
    }

    @Override
    public void onMenuClick(MenuItem item) {
        mListener.onMenuClick();
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
    public interface ConceptsInteractionListener {
        void onConceptSelected(Concept concept);

        void onMenuClick();
    }

    private class GetConceptsTask extends AsyncTask<Category, Void, List<Concept>> {

        @Override
        protected List<Concept> doInBackground(Category... params) {
            DatabaseDao dao = DatabaseDao.getInstance(getContext());
            List<Concept> concepts = dao.getConceptForCategories(params[0]);
            Log.d(TAG, "langs : " + concepts.toString());
            return concepts;
        }

        @Override
        protected void onPostExecute(List<Concept> concepts) {
            super.onPostExecute(concepts);
            setAdapter(concepts);
        }
    }

}
