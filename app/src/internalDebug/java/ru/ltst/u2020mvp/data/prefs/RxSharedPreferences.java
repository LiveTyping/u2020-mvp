package ru.ltst.u2020mvp.data.prefs;

import android.content.SharedPreferences;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.Subscriptions;

import static android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import static rx.android.internal.Preconditions.checkNotNull;

public final class RxSharedPreferences {
    public static RxSharedPreferences create(SharedPreferences sharedPreferences) {
        return new RxSharedPreferences(sharedPreferences);
    }

    private final SharedPreferences sharedPreferences;
    private final Observable<String> changedKeys;

    private RxSharedPreferences(final SharedPreferences sharedPreferences) {
        this.sharedPreferences = checkNotNull(sharedPreferences, "sharedPreferences == null");
        this.changedKeys = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                final OnSharedPreferenceChangeListener listener = (sharedPreferences1, key) -> subscriber.onNext(key);

                Subscription subscription = Subscriptions.create(() -> sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener));
                subscriber.add(subscription);

                sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
            }
        }).share();
    }

    public Observable<String> getString(String key) {
        return getString(key, null);
    }

    public Observable<String> getString(String key, final String defaultValue) {
        return changedKeys.filter(matchesKey(key))
                .startWith(key)
                .map(changedKey -> sharedPreferences.getString(changedKey, defaultValue));
    }

    public Action1<String> setString(final String key) {
        return value -> sharedPreferences.edit().putString(key, value).apply();
    }

    public Observable<Boolean> getBoolean(String key) {
        return getBoolean(key, null);
    }

    public Observable<Boolean> getBoolean(String key, final Boolean defaultValue) {
        return changedKeys.filter(matchesKey(key))
                .startWith(key)
                .map(changedKey -> sharedPreferences.getBoolean(changedKey, defaultValue));
    }

    public Action1<Boolean> setBoolean(final String key) {
        return value -> sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public Observable<Integer> getInt(String key) {
        return getInt(key, null);
    }

    public Observable<Integer> getInt(String key, final Integer defaultValue) {
        return changedKeys.filter(matchesKey(key))
                .startWith(key)
                .map(changedKey -> sharedPreferences.getInt(changedKey, defaultValue));
    }

    private static Func1<String, Boolean> matchesKey(final String key) {
        return value -> key.equals(value);
    }
}
