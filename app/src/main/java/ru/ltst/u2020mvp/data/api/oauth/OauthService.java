package ru.ltst.u2020mvp.data.api.oauth;

import android.app.IntentService;
import android.content.Intent;

import javax.inject.Inject;

import ru.ltst.u2020mvp.ApplicationScope;

public final class OauthService extends IntentService {
    @Inject
    OauthManager oauthManager;

    public OauthService() {
        super(OauthService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        oauthManager.handleResult(intent.getData());
    }
}
