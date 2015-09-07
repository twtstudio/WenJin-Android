package com.twt.service.wenjin.ui.answer.detail;

import com.twt.service.wenjin.bean.Answer;

/**
 * Created by M on 2015/3/29.
 */
public interface AnswerDetailView {

    void showProgressBar();

    void hideProgressBar();

    void bindAnswerData(Answer answer);

    void bindTitle(String title);

    void toastMessage(String msg);

    void setAgree(int argVoteState, int agreeCount);

    void setDisAgree(int argVoteState);

    void setAgreeCount(int agreeCount);

    void setThank(boolean isThank);

    void startProfileActivity();

    void startCommentActivity();

    void startQuestionActivity();

}
