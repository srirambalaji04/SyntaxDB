package com.shiru.syntaxdb;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.shiru.syntaxdb.bean.Category;
import com.shiru.syntaxdb.bean.Concept;
import com.shiru.syntaxdb.bean.Language;
import com.shiru.syntaxdb.databinding.ActivityMainBinding;
import com.shiru.syntaxdb.fragment.AboutFragment;
import com.shiru.syntaxdb.fragment.CategoriesFragment;
import com.shiru.syntaxdb.fragment.ConceptFragment;
import com.shiru.syntaxdb.fragment.ConceptsFragment;
import com.shiru.syntaxdb.fragment.LanguagesFragment;
import com.shiru.syntaxdb.fragment.RecentsFragment;
import com.shiru.syntaxdb.fragment.SearchConceptsFragment;

public class MainActivity extends AppCompatActivity implements LanguagesFragment.LanguagesListener, CategoriesFragment.CategoryListener,
        ConceptsFragment.ConceptsInteractionListener, SearchConceptsFragment.SearchFragmentListener, RecentsFragment.RecentsListener {

    public static final String TAG = "SDB.MainActivity";

    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        LanguagesRsp rsp = getIntent().getParcelableExtra(KEYS.KEY_LANGUAGE);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, LanguagesFragment.newInstance())
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
        binding.navigationMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.recents:
                        RecentsFragment recentsFragment = RecentsFragment.newInstance();
                        addFragment(recentsFragment, RecentsFragment.TAG, true, false, true);
                        break;
                    case R.id.search:
                        SearchConceptsFragment fragment = SearchConceptsFragment.newInstance();
                        addFragment(fragment, SearchConceptsFragment.TAG, true, false, true);
                        break;
                    case R.id.mail_to_dev:
                        sendMail();
                        break;

                }
                closeDrawer();
                return true;
            }
        });
    }

    private void closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    private void sendMail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
        intent.setType("message/rfc822");
        String[] mails = {"srirambalaji04@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, mails);
        intent.putExtra(Intent.EXTRA_SUBJECT, "FEEDBACK OR SUGGESTION");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        Intent mailer = Intent.createChooser(intent, "Open with Email");
        startActivity(mailer);
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
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onMenuClick() {
        AboutFragment fragment = AboutFragment.newInstance(null, null);
        addFragment(fragment, AboutFragment.TAG, true, false, true);
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

    @Override
    public void onConceptSelcted(Concept concept) {
        onConceptSelected(concept);
    }

    @Override
    public void onRecentSelected(Concept concept) {
        onConceptSelected(concept);
    }
}
