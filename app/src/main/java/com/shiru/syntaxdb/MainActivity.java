package com.shiru.syntaxdb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsById();
        LanguagesRsp rsp = getIntent().getParcelableExtra(KEYS.KEY_LANGUAGE);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, LanguagesFragment.newInstance(rsp.getLanguages()), LanguagesFragment.TAG)
                .commit();
        handleToolbar();
    }

    private void findViewsById() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        container = (ViewGroup) findViewById(R.id.headings_container);
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
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);

/*        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager fm = getSupportFragmentManager();
                int count = fm.getBackStackEntryCount() - 1;
                Log.d(TAG, "called" + count);
                String tag = getSupportFragmentManager().getBackStackEntryAt(count).getName();
                View view = container.findViewWithTag(tag);
                if (view == null) {
                    container.addView(getView(tag));
                } else {
                    container.removeView(view);
                }
            }
        });*/
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

    private View getView(final String text) {
        View view = getLayoutInflater().inflate(R.layout.text1, null, false);
        TextView title = (TextView) view.findViewById(R.id.content);
        title.setText(text);
        view.setTag(text);
        return view;
    }

    @Override
    public void onLanguageSelected(Language language) {
        CategoriesFragment fragment = CategoriesFragment.newInstance(language);
        addFragment(fragment, language.getName(), true, false, true);
    }

    @Override
    public void onCategorySelected(Language language, Category category) {
        ConceptsFragment fragment = ConceptsFragment.newInstance(category);
        addFragment(fragment, category.getName(), true, false, true);
    }

    @Override
    public void onConceptSelected(Concept concept) {
        ConceptFragment fragment = ConceptFragment.newInstance(concept);
        addFragment(fragment, concept.getName(), true, false, true);
    }
}
