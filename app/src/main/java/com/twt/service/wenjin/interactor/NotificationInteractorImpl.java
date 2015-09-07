package com.twt.service.wenjin.interactor;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.NotificationNumInfo;
import com.twt.service.wenjin.bean.NotificationResponse;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.main.OnGetNotificationNumberInfoCallback;
import com.twt.service.wenjin.ui.notification.readlist.OnGetNotificationListCallback;
import com.twt.service.wenjin.ui.notification.readlist.OnMarkallCallback;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Green on 15-6-22.
 */
public class NotificationInteractorImpl implements NotificationInteractor {
    private static final String LOG_TAG = NotificationInteractorImpl.class.getSimpleName();

    @Override
    public void getNotificationNumberInfo(long argTimestampNow, final OnGetNotificationNumberInfoCallback callback) {
        ApiClient.getNotificationsNumberInfo(argTimestampNow, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                LogHelper.v(LOG_TAG, response.toString());
                super.onSuccess(statusCode, headers, response);
                try {
                    int errorCode = response.getInt(ApiClient.RESP_ERROR_CODE_KEY);
                    switch (errorCode) {
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            NotificationNumInfo numInfo = gson.fromJson(
                                    response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(), NotificationNumInfo.class);
                            callback.onGetNotificationNumberInfoSuccess(numInfo);
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onGetNotificationNumberInfoFailed(ApiClient.RESP_ERROR_MSG_KEY);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                //LogHelper.v(LOG_TAG, errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                LogHelper.v(LOG_TAG, responseString);

            }
        });
    }

    @Override
    public void getNotificationList(int argPage,int type, final OnGetNotificationListCallback callback) {
        ApiClient.getNotificationsList(argPage, type , new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                LogHelper.v(LOG_TAG, response.toString());
                super.onSuccess(statusCode, headers, response);
                try{
                    int errorCode = response.getInt(ApiClient.RESP_ERROR_CODE_KEY);
                    switch (errorCode){
                        case ApiClient.SUCCESS_CODE:
                            Gson gson = new Gson();
                            NotificationResponse notificationResponse = gson.fromJson(
                                    response.getJSONObject(ApiClient.RESP_MSG_KEY).toString(), NotificationResponse.class);
                            callback.onGetNotificationListSuccess(notificationResponse);
                            break;
                        case ApiClient.ERROR_CODE:
                            callback.onGetNotificationListFailed(ApiClient.RESP_ERROR_MSG_KEY);
                            break;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                LogHelper.v(LOG_TAG, responseString);
            }
        });
    }

    @Override
    public void setNotificationMarkasread(int argNotificationId) {
        ApiClient.setNotificationsMarkasread(argNotificationId, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, response.toString());

                try{
                    int errorCode = response.getInt(ApiClient.RESP_ERROR_CODE_KEY);
                    switch (errorCode){
                        case ApiClient.SUCCESS_CODE:
                            break;
                        case ApiClient.ERROR_CODE:
                            break;
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                LogHelper.v(LOG_TAG, responseString);
            }
        });
    }

    @Override
    public void setNotificationMarkAllasread(final OnMarkallCallback callback) {
        ApiClient.setNotificationsMarkAllasread(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LogHelper.v(LOG_TAG, response.toString());

                try {
                    int errorCode = response.getInt(ApiClient.RESP_ERROR_CODE_KEY);
                    switch (errorCode) {
                        case ApiClient.SUCCESS_CODE:
                            callback.onMarkAllNotificationSuccess();
                            break;
                        case ApiClient.ERROR_CODE:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                LogHelper.v(LOG_TAG, responseString);
            }
        });

    }
}
