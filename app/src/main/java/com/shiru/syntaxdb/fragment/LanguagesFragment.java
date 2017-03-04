package com.shiru.syntaxdb.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.adapter.LanguageAdapter;
import com.shiru.syntaxdb.bean.Language;
import com.shiru.syntaxdb.listener.ItemClickSupport;
import com.shiru.syntaxdb.listener.ToolbarListener;
import com.shiru.syntaxdb.utils.KEYS;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.shiru.syntaxdb.fragment.LanguagesFragment.LanguagesListener} interface
 * to handle interaction events.
 * Use the {@link LanguagesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LanguagesFragment extends Fragment implements ToolbarListener {

    public static final String TAG = "SDB.LanguagesFragment";
    private List<Language> languages;
    private LanguagesListener mListener;
    private RecyclerView mRecyclerView;

    public static LanguagesFragment newInstance(List<Language> languages) {
        LanguagesFragment fragment = new LanguagesFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEYS.KEY_LANGUAGE, (ArrayList<? extends Parcelable>) languages);
        fragment.setArguments(args);
        return fragment;
    }

    public LanguagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mListener = (LanguagesListener) context;
        } else {
            throw new RuntimeException("implement listener in the host activity");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_languages, container, false);
        findViewsById(view);
        setAdapter();

        Log.d(TAG, "onCreateView" + languages);
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Language language = languages.get(position);
                        mListener.onLanguageSelected(language);
                    }
                }, 200);
            }
        });
        return view;
    }

    private void findViewsById(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.lang_list);
    }

    private void setAdapter() {
        languages = getArguments().getParcelableArrayList(KEYS.KEY_LANGUAGE);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LanguageAdapter adapter = new LanguageAdapter(languages);
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onNavigationClick(View view) {
        getFragmentManager().popBackStack();
    }

    public interface LanguagesListener {
        void onLanguageSelected(Language language);
    }

}
