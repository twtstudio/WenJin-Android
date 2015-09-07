package com.twt.service.wenjin.ui.question;

import com.twt.service.wenjin.bean.QuestionResponse;

/**
 * Created by M on 2015/3/27.
 */
public interface OnGetQuestionCallback {

    void onGetQuestionSuccess(QuestionResponse questionResponse);

    void onGetQuestionFailure(String errorString);

}
