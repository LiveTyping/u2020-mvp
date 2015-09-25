package ru.ltst.u2020mvp.data;

import android.content.Intent;

import ru.ltst.u2020mvp.data.prefs.BooleanPreference;
import ru.ltst.u2020mvp.ui.ExternalIntentActivity;

/**
 * An {@link IntentFactory} implementation that wraps all {@code Intent}s with a debug action, which
 * launches an activity that allows you to inspect the content.
 */
public final class DebugIntentFactory implements IntentFactory {
    private final IntentFactory realIntentFactory;
    private final boolean isMockMode;
    private final BooleanPreference captureIntents;

    public DebugIntentFactory(IntentFactory realIntentFactory, boolean isMockMode,
                              BooleanPreference captureIntents) {
        this.realIntentFactory = realIntentFactory;
        this.isMockMode = isMockMode;
        this.captureIntents = captureIntents;
    }

    @Override
    public Intent createUrlIntent(String url) {
        Intent baseIntent = realIntentFactory.createUrlIntent(url);
        if (!isMockMode || !captureIntents.get()) {
            return baseIntent;
        } else {
            return ExternalIntentActivity.createIntent(baseIntent);
        }
    }
}
