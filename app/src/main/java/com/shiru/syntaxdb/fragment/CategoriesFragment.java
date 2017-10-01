package com.shiru.syntaxdb.fragment;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.octo.android.robospice.SpiceManager;
import com.shiru.syntaxdb.R;
import com.shiru.syntaxdb.adapter.ExpandableCategoryAdapter;
import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.bean.Language;
import com.shiru.syntaxdb.dao.DatabaseDao;
import com.shiru.syntaxdb.databinding.FragmentCategoriesBinding;
import com.shiru.syntaxdb.listener.ToolbarListener;
import com.shiru.syntaxdb.utils.KEYS;
import com.shiru.syntaxdb.utils.SDBService;
import com.shiru.syntaxdb.utils.UiUtility;
import com.shiru.syntaxdb.views.ToolbarView;

import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.shiru.syntaxdb.fragment.CategoriesFragment.CategoryListener} interface
 * to handle interaction events.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment implements ToolbarListener, ExpandableCategoryAdapter.ExpandableChildClickListener {

    public static final String TAG = "SDB.CategoriesFragment";

    private FragmentCategoriesBinding binding;
    private RecyclerView cateList;
    private SpiceManager spiceManager = new SpiceManager(SDBService.class);
    private AlertDialog dialog;
    private RecyclerViewExpandableItemManager mRecyclerViewExpandableItemManager;

    private CategoryListener mListener;

    public CategoriesFragment() {
        // Required empty public constructor
    }

    public static CategoriesFragment newInstance(Language language) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEYS.KEY_LANGUAGE, language);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mListener = (CategoryListener) context;
        } else {
            throw new RuntimeException("implement the listener in the host activity");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false);
        findViewsById(binding.getRoot());
        setupToolbar();

        if (getArguments().containsKey(KEYS.KEY_LANGUAGE)) {
            Language language = (Language) getArguments().getParcelable(KEYS.KEY_LANGUAGE);
            setTitle(language);
            GetCatesTask task = new GetCatesTask();
            task.execute(language);
        }


        return binding.getRoot();
    }

    private void findViewsById(View view) {
        cateList = (RecyclerView) view.findViewById(R.id.cate_list);
    }

/*    private void sendRequest() {
        Language language = getArguments().getParcelable(KEYS.KEY_LANGUAGE);
        GetCategoriesRequest request = new GetCategoriesRequest(language);
        CategoriesListener listener = new CategoriesListener();
        spiceManager.execute(request, listener);
    }*/

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getContext());
        dialog = UiUtility.getDialog(getContext());
        dialog.show();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (spiceManager.isStarted()) {
            spiceManager.shouldStop();
        }
    }

    /*private void setAdapterOld(final List<Category> categories) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories);
        final Language language = getArguments().getParcelable(KEYS.KEY_LANGUAGE);
        cateList.setLayoutManager(new LinearLayoutManager(getContext()));
        cateList.setAdapter(categoryAdapter);

        ItemClickSupport.addTo(cateList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mListener.onCategorySelected(language, categories.get(position));
                    }
                }, 200);

            }
        });
    }*/

    private void setAdapter(HashMap<Category, List<Concept>> cateMap) {
        RecyclerView.Adapter mWrappedAdapter;

        ExpandableCategoryAdapter categoryAdapter = new ExpandableCategoryAdapter(cateMap);
        categoryAdapter.setConceptClickListener(this);
//        final Language language = getArguments().getParcelable(KEYS.KEY_LANGUAGE);
        cateList.setLayoutManager(new LinearLayoutManager(getContext()));

        mRecyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(null);
        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(categoryAdapter);
        cateList.setAdapter(mWrappedAdapter);
        mRecyclerViewExpandableItemManager.attachRecyclerView(cateList);
        cateList.setHasFixedSize(false);

    }

    private void setupToolbar() {
        ToolbarView view = new ToolbarView(binding.toolbar.realToolbar, getString(R.string.app_name), R.drawable.ic_back_arrow, this);
    }

    private void setTitle(Language selectedLanguage) {
        binding.setTitle(selectedLanguage.getName());
    }

    @Override
    public void onNavigationClick(View view) {
        getFragmentManager().popBackStack();
    }

    /**
     * From new RecyclerView
     */
    @Override
    public void onChildClick(Concept concept) {
        mListener.onConceptSelected(concept);
    }

    public interface CategoryListener {
        void onCategorySelected(Language language, Category category);

        void onConceptSelected(Concept concept);
    }

    /*    private class CategoriesListener implements RequestListener<CategoriesRsp> {

            @Override
            public void onRequestFailure(SpiceException e) {
                Log.e(TAG, "onRequestFailure" + e.toString());
                ExceptionHandler.handleListenerException(e, getActivity().findViewById(R.id.container));
                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onRequestSuccess(CategoriesRsp categoriesRsp) {
                setAdapter(categoriesRsp.getCategoryList());
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        }


    private class GetCategoriesTask extends AsyncTask<Language, Void, List<Category>> {

        @Override
        protected List<Category> doInBackground(Language... params) {
            DatabaseDao dao = DatabaseDao.getInstance(getContext());
            List<Category> categories = dao.getCategoriesOfLanguage(params[0]);
            Log.d(TAG, "langs : " + categories.toString());
            return categories;

        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            super.onPostExecute(categories);
            setAdapter(categories);
            if (dialog.isShowing())
                dialog.dismiss();
        }
    } */

    private class GetCatesTask extends AsyncTask<Language, Void, HashMap<Category, List<Concept>>> {

        @Override
        protected HashMap<Category, List<Concept>> doInBackground(Language... params) {
            DatabaseDao dao = DatabaseDao.getInstance(getContext());
            List<Category> categories = dao.getCategoriesOfLanguage(params[0]);
            HashMap<Category, List<Concept>> cateMap = new HashMap<Category, List<Concept>>();
            for (Category category : categories) {
                List<Concept> concepts = dao.getConceptForCategories(category);
                cateMap.put(category, concepts);
            }

            Log.d(TAG, "cateMap : " + cateMap.toString());
            return cateMap;

        }

        @Override
        protected void onPostExecute(HashMap<Category, List<Concept>> cateMap) {
            super.onPostExecute(cateMap);
            setAdapter(cateMap);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

        }
    }

}
