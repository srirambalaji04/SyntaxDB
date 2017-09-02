package com.shiru.syntaxdb.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.adapter.ConceptsAdapter;
import com.shiru.syntaxdb.api.request.GetAllSearchesRequest;
import com.shiru.syntaxdb.api.response.bean.ConceptsRsp;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.dao.DatabaseDao;
import com.shiru.syntaxdb.databinding.FragmentSearchConceptsBinding;
import com.shiru.syntaxdb.listener.ItemClickSupport;
import com.shiru.syntaxdb.listener.ToolbarListener;
import com.shiru.syntaxdb.utils.ExceptionHandler;
import com.shiru.syntaxdb.utils.SDBService;
import com.shiru.syntaxdb.views.ToolbarView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragmentListener} interface
 * to handle interaction events.
 * Use the {@link SearchConceptsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchConceptsFragment extends Fragment implements ToolbarListener {
    public static final String TAG = "SearchConceptsFragment";

    private SearchFragmentListener mListener;
    private FragmentSearchConceptsBinding binding;
    private SpiceManager spiceManager = new SpiceManager(SDBService.class);

    private List<Concept> concepts;

    public SearchConceptsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchConceptsFragment.
     */
    public static SearchConceptsFragment newInstance() {
        SearchConceptsFragment fragment = new SearchConceptsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (concepts == null)
            concepts = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        concepts = null;
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_concepts, container, false);
        binding.setPresenter(new SearchPresenter());
        setupToolbar();
        setAdapter();
        binding.searchEtxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(TAG, "onEditorAction: " + actionId);
                switch (actionId) {
                    case EditorInfo.IME_ACTION_GO:
                        getConcepts(binding.searchEtxt.getText().toString());
//                        sendSearchRequest(binding.searchEtxt.getText().toString());
                        break;
                }
                return true;
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getContext());
    }

    @Override
    public void onStop() {
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
        super.onStop();
    }

    private void setupToolbar() {
        new ToolbarView(binding.toolbar.realToolbar,
                getString(R.string.search), R.drawable.ic_back_arrow, this);
    }

    private void setAdapter() {
        binding.conceptsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.conceptsRecyclerview.setAdapter(new ConceptsAdapter(getContext(), concepts));
        ItemClickSupport.addTo(binding.conceptsRecyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Concept concept = concepts.get(position);
                mListener.onConceptSelcted(concept);
            }
        });
    }

    private void updateAdatper(List<Concept> concepts) {
        this.concepts.clear();
        this.concepts.addAll(concepts);
        binding.conceptsRecyclerview.getAdapter().notifyDataSetChanged();

        int visible = concepts.isEmpty() ? View.GONE : View.VISIBLE;
        int visino = concepts.isEmpty() ? View.VISIBLE : View.GONE;
        binding.conceptsRecyclerview.setVisibility(visible);
        binding.noDataTxt.setVisibility(visino);
    }

    private void sendSearchRequest(String querytring) {
        GetAllSearchesRequest request = new GetAllSearchesRequest(querytring);
        SearchesListener listener = new SearchesListener();
        spiceManager.execute(request, listener);
        binding.searchLoader.setVisibility(View.VISIBLE);
    }

    private void getConcepts(String querytring) {
        GetConceptsTask task = new GetConceptsTask();
        task.execute(querytring.split(" "));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SearchFragmentListener) {
            mListener = (SearchFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SearchFragmentListener");
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface SearchFragmentListener {

        void onConceptSelcted(Concept concept);
    }

    public class SearchPresenter implements View.OnClickListener {

/*        public TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: " + s + count);
                if (s.toString().length() > 3 && spiceManager.getPendingRequestCount() == 0) {
                    sendSearchRequest(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };*/

        @Override
        public void onClick(View v) {

        }
    }

    private class SearchesListener implements RequestListener<ConceptsRsp> {

        @Override
        public void onRequestFailure(SpiceException e) {
            Log.d(TAG, "onRequestFailure: " + e.toString());
            binding.searchLoader.setVisibility(View.GONE);
            ExceptionHandler.handleListenerException(e, getActivity().findViewById(R.id.container));
        }

        @Override
        public void onRequestSuccess(ConceptsRsp conceptsRsp) {
            binding.searchLoader.setVisibility(View.GONE);
            if (conceptsRsp != null) {
                Log.d(TAG, "onRequestSuccess: " + conceptsRsp.toString());
                updateAdatper(conceptsRsp.getConcepts());
            }
        }
    }

    private class GetConceptsTask extends AsyncTask<String, Void, List<Concept>> {

        @Override
        protected List<Concept> doInBackground(String... params) {
            DatabaseDao dao = DatabaseDao.getInstance(getContext());
            List<Concept> concepts = dao.getSearchConcepts(params[0]);
            return concepts;
        }

        @Override
        protected void onPostExecute(List<Concept> concepts) {
            super.onPostExecute(concepts);
            updateAdatper(concepts);
        }
    }
}
