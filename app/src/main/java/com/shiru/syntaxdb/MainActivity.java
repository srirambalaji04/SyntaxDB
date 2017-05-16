package com.shiru.syntaxdb;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.shiru.syntaxdb.api.response.bean.LanguagesRsp;
import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.bean.Language;
import com.shiru.syntaxdb.databinding.ActivityMainBinding;
import com.shiru.syntaxdb.fragment.CategoriesFragment;
import com.shiru.syntaxdb.fragment.ConceptFragment;
import com.shiru.syntaxdb.fragment.ConceptsFragment;
import com.shiru.syntaxdb.fragment.LanguagesFragment;
import com.shiru.syntaxdb.utils.KEYS;

public class MainActivity extends AppCompatActivity implements LanguagesFragment.LanguagesListener, CategoriesFragment.CategoryListener,
        ConceptsFragment.ConceptsInteractionListener {

    public static final String TAG = "SDB.MainActivity";

    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        LanguagesRsp rsp = getIntent().getParcelableExtra(KEYS.KEY_LANGUAGE);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, LanguagesFragment.newInstance(rsp.getLanguages()), LanguagesFragment.TAG)
                .commit();
        setupNavigationView();
    }

    private void setupNavigationView() {
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
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

    ////////////////////////////////////  I N T E R F A C E     L I  S T E N E R S \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Override
    public void onLanguageSelected(Language language) {
        CategoriesFragment fragment = CategoriesFragment.newInstance(language);
        addFragment(fragment, language.getName(), true, false, true);
    }

    @Override
    public void onNavigationClicked() {
        binding.drawerLayout.openDrawer(Gravity.START);
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
