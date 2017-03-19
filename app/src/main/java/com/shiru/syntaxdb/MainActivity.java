package com.shiru.syntaxdb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.shiru.syntaxdb.api.response.bean.LanguagesRsp;
import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.bean.Language;
import com.shiru.syntaxdb.fragment.CategoriesFragment;
import com.shiru.syntaxdb.fragment.ConceptFragment;
import com.shiru.syntaxdb.fragment.ConceptsFragment;
import com.shiru.syntaxdb.fragment.LanguagesFragment;
import com.shiru.syntaxdb.utils.KEYS;

public class MainActivity extends AppCompatActivity implements LanguagesFragment.LanguagesListener, CategoriesFragment.CategoryListener,
        ConceptsFragment.ConceptsInteractionListener {

    public static final String TAG = "SDB.MainActivity";

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        LanguagesRsp rsp = getIntent().getParcelableExtra(KEYS.KEY_LANGUAGE);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, LanguagesFragment.newInstance(rsp.getLanguages()))
                .commit();
        handleToolbar();
    }

    private void handleToolbar() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                int count = fm.getBackStackEntryCount();
                if (count == 0) {
                    MainActivity.this.finish();
                } else {
                    fm.popBackStack();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void addFragment(Fragment fragment, String tag, boolean addtoBackStack, boolean clearBackStack, boolean hideCurrent) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment currentFragment = fm.findFragmentById(R.id.container);
        if (hideCurrent)
            ft.hide(currentFragment);

        if (addtoBackStack)
            ft.addToBackStack(tag);

        if (clearBackStack)
            fm.popBackStack();

        Fragment fragment1 = fm.findFragmentByTag(tag);

        if (fragment1 != currentFragment) {
            ft.add(R.id.container, fragment, tag);
            ft.commit();
            Log.d(TAG, "addFragment" + tag);
        }

    }

    @Override
    public void onLanguageSelected(Language language) {
        CategoriesFragment fragment = CategoriesFragment.newInstance(language);
        addFragment(fragment, CategoriesFragment.TAG, true, false, true);
    }

    @Override
    public void onCategorySelected(Language language, Category category) {
        ConceptsFragment fragment = ConceptsFragment.newInstance(category);
        addFragment(fragment, ConceptsFragment.TAG, true, false, true);
    }

    @Override
    public void onConceptSelected(Concept concept) {
        ConceptFragment fragment = ConceptFragment.newInstance(concept);
        addFragment(fragment, ConceptFragment.TAG, true, false, true);
    }
}
