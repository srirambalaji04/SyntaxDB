package com.shiru.syntaxdb.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.adapter.ConceptsAdapter;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.databinding.FragmentRecentsBinding;
import com.shiru.syntaxdb.listener.ItemClickSupport;
import com.shiru.syntaxdb.listener.ToolbarListener;
import com.shiru.syntaxdb.utils.SharedPreference;
import com.shiru.syntaxdb.views.ToolbarView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecentsListener} interface
 * to handle interaction events.
 * Use the {@link RecentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecentsFragment extends Fragment implements ToolbarListener {

    public static final String TAG = "RecentsFragment";

    private RecentsListener mListener;
    private FragmentRecentsBinding binding;

    public RecentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecentsFragment.
     */
    public static RecentsFragment newInstance() {
        RecentsFragment fragment = new RecentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recents, container, false);
        setupToolbar();
        setAdapter();
        return binding.getRoot();
    }

    private void setupToolbar() {
        ToolbarView view = new ToolbarView(
                binding.toolbar.realToolbar, "Recents", R.drawable.ic_back_arrow, this);
        view.setMenu(R.menu.toolbar_menu);
    }

    private void setAdapter() {
        final List<Concept> concepts = new ArrayList<>(SharedPreference.getRecents());
        binding.recentsList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recentsList.setAdapter(new ConceptsAdapter(getContext(), concepts));
        int listVisi = concepts.isEmpty() ? View.GONE : View.VISIBLE;
        int nodatavisi = concepts.isEmpty() ? View.VISIBLE : View.GONE;

        binding.recentsList.setVisibility(listVisi);
        binding.noDataTxt.setVisibility(nodatavisi);

        ItemClickSupport.addTo(binding.recentsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Concept concept = concepts.get(position);
                mListener.onRecentSelected(concept);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecentsListener) {
            mListener = (RecentsListener) context;
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
    public interface RecentsListener {

        void onRecentSelected(Concept concept);
    }
}
