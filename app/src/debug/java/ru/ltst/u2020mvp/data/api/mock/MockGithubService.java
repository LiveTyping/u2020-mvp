package ru.ltst.u2020mvp.data.api.mock;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.adapter.rxjava.Result;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.Calls;
import retrofit2.mock.MockRetrofit;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.data.api.GithubService;
import ru.ltst.u2020mvp.data.api.Order;
import ru.ltst.u2020mvp.data.api.SearchQuery;
import ru.ltst.u2020mvp.data.api.Sort;
import ru.ltst.u2020mvp.data.api.model.RepositoriesResponse;
import ru.ltst.u2020mvp.data.api.model.Repository;
import ru.ltst.u2020mvp.util.EnumPreferences;
import rx.Observable;

@ApplicationScope
public final class MockGithubService implements GithubService {
    private final BehaviorDelegate<GithubService> delegate;
    private final SharedPreferences preferences;
    private final Map<Class<? extends Enum<?>>, Enum<?>> responses = new LinkedHashMap<>();

    @Inject
    MockGithubService(MockRetrofit mockRetrofit, SharedPreferences preferences) {
        this.delegate = mockRetrofit.create(GithubService.class);
        this.preferences = preferences;

        // Initialize mock responses.
        loadResponse(MockRepositoriesResponse.class, MockRepositoriesResponse.SUCCESS);
    }

    /**
     * Initializes the current response for {@code responseClass} from {@code SharedPreferences}, or
     * uses {@code defaultValue} if a response was not found.
     */
    private <T extends Enum<T>> void loadResponse(Class<T> responseClass, T defaultValue) {
        responses.put(responseClass, EnumPreferences.getEnumValue(preferences, responseClass, //
                responseClass.getCanonicalName(), defaultValue));
    }

    public <T extends Enum<T>> T getResponse(Class<T> responseClass) {
        return responseClass.cast(responses.get(responseClass));
    }

    public <T extends Enum<T>> void setResponse(Class<T> responseClass, T value) {
        responses.put(responseClass, value);
        EnumPreferences.saveEnumValue(preferences, responseClass.getCanonicalName(), value);
    }

    @Override
    public Observable<Result<RepositoriesResponse>> repositories(SearchQuery query,
                                                                 Sort sort, Order order) {
        RepositoriesResponse response = getResponse(MockRepositoriesResponse.class).response;

        if (response.items != null) {
            // Don't modify the original list when sorting.
            ArrayList<Repository> items = new ArrayList<>(response.items);
            SortUtil.sort(items, sort, order);
            response = new RepositoriesResponse(items);
        }

        return delegate.returning(Calls.response(response)).repositories(query, sort, order);
    }
}
