package com.twt.service.wenjin.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import com.squareup.otto.Subscribe;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.event.DrawerItemClickedEvent;
import com.twt.service.wenjin.support.BusProvider;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.drawer.DrawerFragment;
import com.twt.service.wenjin.ui.explore.ExploreFragment;
import com.twt.service.wenjin.ui.home.HomeFragment;
import com.twt.service.wenjin.ui.login.LoginActivity;
import com.twt.service.wenjin.ui.topic.TopicFragment;
import com.twt.service.wenjin.ui.user.UserFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity implements MainView {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String[] DRAWER_TITLES = ResourceHelper.getStringArrays(R.array.drawer_list_items);

    @Inject
    MainPresenter mMainPresenter;

    @InjectView(R.id.navigation_drawer_layout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    private DrawerFragment mDrawerFragment;
    private HomeFragment mHomeFragment;
    private ExploreFragment mExploreFragment;
    private TopicFragment mTopicFragment;
    private UserFragment mUserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerFragment = (DrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mDrawerFragment.setUp(R.id.main_container, mDrawerLayout, toolbar);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container, new HomeFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getBusInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getBusInstance().unregister(this);
    }

    @Subscribe
    public void OnDrawerItemClicked(DrawerItemClickedEvent event) {
        LogHelper.v(LOG_TAG, "clicked position: " + event.getPosition());
        mMainPresenter.onNavigationDrawerItemSelected(event.getPosition());
    }

    @Override
    protected List<Object> getModlues() {
        return Arrays.<Object>asList(new MainModule(this));
    }

    @Override
    public void replaceFragment(int position) {
        LogHelper.v(LOG_TAG, "switch to: " + position);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (position) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                fragment = mHomeFragment;
                break;
            case 1:
                if (mExploreFragment == null) {
                    mExploreFragment = new ExploreFragment();
                }
                fragment = mExploreFragment;
                break;
            case 2:
                if (mTopicFragment == null) {
                    mTopicFragment = new TopicFragment();
                }
                fragment = mTopicFragment;
                break;
            case 3:
                if (mUserFragment == null) {
                    mUserFragment = new UserFragment();
                }
                fragment = mUserFragment;
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }

    @Override
    public void setMainTitle(int position) {
        getSupportActionBar().setTitle(DRAWER_TITLES[position]);
    }

    @Override
    public void startNewActivity(int position) {
        LogHelper.v(LOG_TAG, "start new activity: " + position);
        switch (position) {
            case 4:
                LogHelper.v(LOG_TAG, "start setting activity");
                break;
            case 5:
                LogHelper.v(LOG_TAG, "start help activity");
                break;
            case 6:
                LogHelper.v(LOG_TAG, "user logout");
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
