package com.twt.service.wenjin.ui.profile.askanswer;

import android.view.View;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.MyAnswer;
import com.twt.service.wenjin.bean.MyAnswerResponse;
import com.twt.service.wenjin.bean.MyQuestion;
import com.twt.service.wenjin.bean.MyQustionResponse;
import com.twt.service.wenjin.interactor.ProfileAskanswerInteractor;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.PrefUtils;
import com.twt.service.wenjin.support.ResourceHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/4/17.
 */
public class ProfileAskanswerPresenterImpl implements
        ProfileAskanswerPresenter,OnGetAnswerCallback,OnGetAskCallback {

    private static final String LOG_TAG = ProfileAskanswerPresenterImpl.class.getSimpleName();

    private static final String ACTION_TYPE_ASK = "ask";
    private static final String ACTION_TYPE_ANSWER = "answer";

    private ProfileAskanswerView _profileAskanswerView;
    private ProfileAskanswerInteractor _profileAskanswerInteractor;

    private boolean _isLoadMore = false;
    private int _page = 0;

    public ProfileAskanswerPresenterImpl(ProfileAskanswerView profileAskanswerView,
                                         ProfileAskanswerInteractor profileAskanswerInteractor){
        _profileAskanswerView = profileAskanswerView;
        _profileAskanswerInteractor = profileAskanswerInteractor;
    }

    @Override
    public void loadMoreItems(String type,int uid) {
        if(_isLoadMore){return;}
        _page += 1;
        _isLoadMore = true;
        _profileAskanswerView.showFooter();
        getItems(type,uid);
    }

    @Override
    public void refreshItems(String type,int uid) {
        _page = 1;
        _profileAskanswerView.showFooter();
        getItems(type,uid);
    }

    @Override
    public void onItemClicked(View v, int position) {
        switch (v.getId()){
            case R.id.tv_home_item_title:
                _profileAskanswerView.startQuestionActivity(position);
                break;
            case R.id.tv_home_item_content:
                _profileAskanswerView.startAnswerActivity(position);
                break;
        }
    }

    private void getItems(String type,int uid){

        if(type.compareTo(ACTION_TYPE_ANSWER) == 0){
            _profileAskanswerInteractor.getAnswerItems(uid,_page,10,this);
        }

        if(type.compareTo(ACTION_TYPE_ASK) == 0 ){
            _profileAskanswerInteractor.getAskItems(uid,_page,10,this);
        }
    }

    @Override
    public void onGetAnswerSuccess(MyAnswerResponse myAnswerResponse) {
        if(myAnswerResponse.total_rows > 0){
            Object myAnswerList = myAnswerResponse.rows;
            if(_isLoadMore){
                _profileAskanswerView.addListData(myAnswerList,myAnswerResponse.total_rows);
                _isLoadMore = false;
            }else{
                //_profileAskanswerView.refreshListData(myAnswerList);
            }
        }else{
            _profileAskanswerView.toastMessage(ResourceHelper.getString(R.string.no_more_information));
        }
        _profileAskanswerView.hideFooter();

    }

    @Override
    public void onGetAnswerFailed(String msg) {
        _page -= 1;
        _profileAskanswerView.toastMessage(msg);
        _isLoadMore = false;
    }

    @Override
    public void onGetMyQuestionSuccess(MyQustionResponse myQustionResponse) {

        if(myQustionResponse.total_rows > 0){
            Object myQuestions = myQustionResponse.rows;
            if(_isLoadMore){
                _profileAskanswerView.addListData(myQuestions,myQustionResponse.total_rows);
                _isLoadMore = false;
            }else{
                //_profileAskanswerView.refreshListData(myQuestions);
            }
        }else{
            _profileAskanswerView.toastMessage(ResourceHelper.getString(R.string.no_more_information));
        }
        _profileAskanswerView.hideFooter();
        _isLoadMore = false;

    }

    @Override
    public void onGetMyQuestionFailed(String msg) {
        _page -= 1;
        _profileAskanswerView.toastMessage(msg);
        _isLoadMore = false;
    }
}
