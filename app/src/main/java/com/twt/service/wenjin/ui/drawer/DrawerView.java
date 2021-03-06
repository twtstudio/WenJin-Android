package com.twt.service.wenjin.ui.drawer;

/**
 * Created by M on 2015/3/20.
 */
public interface DrawerView {

    void closeDrawer();

    void updateUserInfo();

    void setSelectedItemColor(int position);

    void sendDrawerItemClickedEvent(int position);

    void startSettingsActivity();

    void startFeedbackActivity();

    void startLoginActivity();

}
