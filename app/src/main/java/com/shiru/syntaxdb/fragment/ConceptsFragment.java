package com.shiru.syntaxdb.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.adapter.ConceptsAdapter;
import com.shiru.syntaxdb.api.request.GetConceptsRequest;
import com.shiru.syntaxdb.api.response.bean.ConceptsRsp;
import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.databinding.FragmentConceptsBinding;
import com.shiru.syntaxdb.listener.ItemClickSupport;
import com.shiru.syntaxdb.listener.ToolbarListener;
import com.shiru.syntaxdb.utils.KEYS;
import com.shiru.syntaxdb.utils.SDBService;
import com.shiru.syntaxdb.utils.UiUtility;
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
    private SpiceManager manager = new SpiceManager(SDBService.class);
    private ConceptsInteractionListener mListener;
    private AlertDialog dialog;

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
        if (getArguments().containsKey(KEYS.KEY_CATEGORY))
            setTitle((Category) getArguments().getParcelable(KEYS.KEY_CATEGORY));
        sendRequest();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        manager.start(getContext());
        dialog = UiUtility.getDialog(getContext());
        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (manager.isStarted()) {
            manager.shouldStop();
        }
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
        ToolbarView view = new ToolbarView(binding.toolbar.realToolbar, getString(R.string.app_name), R.drawable.ic_back_arrow, this);
    }

    private void setTitle(Category category) {
        binding.setTitle(category.getLanguagelink() + " | " + category.getName());
    }

    private void sendRequest() {
        Category category = this.getArguments().getParcelable(KEYS.KEY_CATEGORY);
        GetConceptsRequest request = new GetConceptsRequest(category);
        ConceptsRequestListener listener = new ConceptsRequestListener();
        manager.execute(request, listener);
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
    }

    private class ConceptsRequestListener implements RequestListener<ConceptsRsp> {

        @Override
        public void onRequestFailure(SpiceException spiceException) {
            Toast.makeText(ConceptsFragment.this.getContext(),
                    (CharSequence) spiceException.getCause(), Toast.LENGTH_LONG).show();
            if (dialog.isShowing())
                dialog.dismiss();
        }

        @Override
        public void onRequestSuccess(ConceptsRsp conceptsRsp) {
            if (conceptsRsp != null) {
                setAdapter(conceptsRsp.getConcepts());
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        }
    }

}
