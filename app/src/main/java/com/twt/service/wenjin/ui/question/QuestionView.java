package com.twt.service.wenjin.ui.question;

import com.twt.service.wenjin.bean.QuestionResponse;

/**
 * Created by M on 2015/3/27.
 */
public interface QuestionView {

    void setAdapter(QuestionResponse questionResponse);

    void toastMessage(String msg);

    void showProgressBar();

    void hideProgressBar();

    void startAnswerActivty(int position);

}