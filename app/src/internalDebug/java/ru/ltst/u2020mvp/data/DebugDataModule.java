package ru.ltst.u2020mvp.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import retrofit.MockRestAdapter;
import ru.ltst.u2020mvp.data.api.DebugApiModule;
import ru.ltst.u2020mvp.data.api.LoggingInterceptor;
import ru.ltst.u2020mvp.data.prefs.BooleanPreference;
import ru.ltst.u2020mvp.data.prefs.IntPreference;
import ru.ltst.u2020mvp.data.prefs.NetworkProxyPreference;
import ru.ltst.u2020mvp.data.prefs.RxSharedPreferences;
import ru.ltst.u2020mvp.data.prefs.StringPreference;
import ru.ltst.u2020mvp.ui.ApplicationScope;
import rx.Observable;
import timber.log.Timber;

@Module(includes = {DataModule.class, DebugApiModule.class})
public final class DebugDataModule {
    private static final int DEFAULT_ANIMATION_SPEED = 1; // 1x (normal) speed.
    private static final boolean DEFAULT_PICASSO_DEBUGGING = false; // Debug indicators displayed
    private static final boolean DEFAULT_PIXEL_GRID_ENABLED = false; // No pixel grid overlay.
    private static final boolean DEFAULT_PIXEL_RATIO_ENABLED = false; // No pixel ratio overlay.
    private static final boolean DEFAULT_SCALPEL_ENABLED = false; // No crazy 3D view tree.
    private static final boolean DEFAULT_SCALPEL_WIREFRAME_ENABLED = false; // Draw views by

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(Application app, LoggingInterceptor loggingInterceptor) {
        OkHttpClient client = DataModule.createOkHttpClient(app);
        client.setSslSocketFactory(createBadSslSocketFactory());
        client.interceptors().add(loggingInterceptor);
        return client;
    }

    @Provides @ApplicationScope
    @AnimationSpeed
    IntPreference provideAnimationSpeed(SharedPreferences preferences) {
        return new IntPreference(preferences, "debug_animation_speed", DEFAULT_ANIMATION_SPEED);
    }

    @Provides @ApplicationScope
    RxSharedPreferences provideRxSharedPreferences(SharedPreferences preferences) {
        return RxSharedPreferences.create(preferences);
    }

    @Provides @ApplicationScope
    NetworkProxyPreference provideNetworkProxy(SharedPreferences preferences) {
        return new NetworkProxyPreference(preferences, "debug_network_proxy");
    }

    @Provides
    @ApplicationScope
    @ApiEndpoint
    StringPreference provideEndpointPreference(SharedPreferences preferences) {
        return new StringPreference(preferences, "debug_endpoint", ApiEndpoints.MOCK_MODE.url);
    }

    @Provides
    @ApplicationScope
    @ru.ltst.u2020mvp.data.IsMockMode
    boolean provideIsMockMode(@ApiEndpoint StringPreference endpoint) {
        return ApiEndpoints.isMockMode(endpoint.get());
    }

    @Provides
    @ApplicationScope
    Picasso providePicasso(OkHttpClient client, MockRestAdapter mockRestAdapter,
                           @IsMockMode boolean isMockMode, Application app) {
        Picasso.Builder builder = new Picasso.Builder(app).downloader(new OkHttpDownloader(client));
        if (isMockMode) {
            builder.addRequestHandler(new MockRequestHandler(mockRestAdapter, app.getAssets()));
        }
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Timber.e(exception, "Error while loading image " + uri);
            }
        });
        return builder.build();
    }

    @Provides
    @ApplicationScope
    @PicassoDebugging
    BooleanPreference providePicassoDebugging(SharedPreferences preferences) {
        return new BooleanPreference(preferences, "debug_picasso_debugging", DEFAULT_PICASSO_DEBUGGING);
    }

    @Provides
    @ApplicationScope
    @PixelGridEnabled
    BooleanPreference providePixelGridEnabled(SharedPreferences preferences) {
        return new BooleanPreference(preferences, "debug_pixel_grid_enabled",
                DEFAULT_PIXEL_GRID_ENABLED);
    }

    @Provides
    @ApplicationScope
    @PixelGridEnabled
    Observable<Boolean> provideObservablePixelGridEnabled(RxSharedPreferences preferences) {
        return preferences.getBoolean("debug_pixel_grid_enabled", DEFAULT_PIXEL_GRID_ENABLED);
    }

    @Provides
    @ApplicationScope
    @PixelRatioEnabled
    BooleanPreference providePixelRatioEnabled(SharedPreferences preferences) {
        return new BooleanPreference(preferences, "debug_pixel_ratio_enabled",
                DEFAULT_PIXEL_RATIO_ENABLED);
    }

    @Provides
    @ApplicationScope
    @PixelRatioEnabled
    Observable<Boolean> provideObservablePixelRatioEnabled(RxSharedPreferences preferences) {
        return preferences.getBoolean("debug_pixel_ratio_enabled", DEFAULT_PIXEL_RATIO_ENABLED);
    }

    @Provides
    @ApplicationScope
    @ScalpelEnabled
    BooleanPreference provideScalpelEnabled(SharedPreferences preferences) {
        return new BooleanPreference(preferences, "debug_scalpel_enabled", DEFAULT_SCALPEL_ENABLED);
    }

    @Provides
    @ApplicationScope
    @ScalpelEnabled
    Observable<Boolean> provideObservableScalpelEnabled(RxSharedPreferences preferences) {
        return preferences.getBoolean("debug_scalpel_enabled", DEFAULT_SCALPEL_ENABLED);
    }

    @Provides
    @ApplicationScope
    @ScalpelWireframeEnabled
    BooleanPreference provideScalpelWireframeEnabled(SharedPreferences preferences) {
        return new BooleanPreference(preferences, "debug_scalpel_wireframe_drawer",
                DEFAULT_SCALPEL_WIREFRAME_ENABLED);
    }

    @Provides
    @ApplicationScope
    @ScalpelWireframeEnabled
    Observable<Boolean> provideObservableScalpelWireframeEnabled(RxSharedPreferences preferences) {
        return preferences.getBoolean("debug_scalpel_wireframe_drawer",
                DEFAULT_SCALPEL_WIREFRAME_ENABLED);
    }

    private static SSLSocketFactory createBadSslSocketFactory() {
        try {
            // Construct SSLSocketFactory that accepts any cert.
            SSLContext context = SSLContext.getInstance("TLS");
            TrustManager permissive = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            context.init(null, new TrustManager[]{permissive}, null);
            return context.getSocketFactory();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
