package com.twt.service.wenjin.ui.answer.detail;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.AnswerDetailInteractor;
import com.twt.service.wenjin.interactor.NotificationInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/3/29.
 */
@Module(
        injects = AnswerDetailActivity.class,
        addsTo = AppModule.class,
        library = true
)
public class AnswerDetailModule {

    private AnswerDetailView mAnswerDetailView;

    public AnswerDetailModule(AnswerDetailView answerDetailView) {
        this.mAnswerDetailView = answerDetailView;
    }

    @Provides @Singleton
    public AnswerDetailView provideAnswerView() {
        return mAnswerDetailView;
    }

    @Provides @Singleton
    public AnswerDetailPresenter provideAnswerPresenter(AnswerDetailView answerDetailView, AnswerDetailInteractor answerDetailInteractor,
                                                        NotificationInteractor notificationInteractor) {
        return new AnswerDetailPresenterImpl(answerDetailView, answerDetailInteractor, notificationInteractor);
    }
}
