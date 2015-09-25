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
import retrofit.mock.NetworkBehavior;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.IsInstrumentationTest;
import ru.ltst.u2020mvp.data.api.DebugApiModule;
import ru.ltst.u2020mvp.data.prefs.BooleanPreference;
import ru.ltst.u2020mvp.data.prefs.IntPreference;
import ru.ltst.u2020mvp.data.prefs.LongPreference;
import ru.ltst.u2020mvp.data.prefs.NetworkProxyPreference;
import ru.ltst.u2020mvp.data.prefs.RxSharedPreferences;
import ru.ltst.u2020mvp.data.prefs.StringPreference;
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
    private static final boolean DEFAULT_SEEN_DEBUG_DRAWER = false; // Show debug drawer first time.
    private static final boolean DEFAULT_CAPTURE_INTENTS = true; // Capture external intents.

    @Provides
    @ApplicationScope
    RxSharedPreferences provideRxSharedPreferences(SharedPreferences preferences) {
        return RxSharedPreferences.create(preferences);
    }

    @Provides
    @ApplicationScope
    IntentFactory provideIntentFactory(@IsMockMode boolean isMockMode,
                                       @CaptureIntents BooleanPreference captureIntents) {
        return new DebugIntentFactory(IntentFactory.REAL, isMockMode, captureIntents);
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(Application app,
                                     NetworkProxyPreference networkProxy) {
        OkHttpClient client = DataModule.createOkHttpClient(app);
        client.setSslSocketFactory(createBadSslSocketFactory());
        client.setProxy(networkProxy.getProxy());
        return client;
    }

    @Provides
    @ApplicationScope
    @ApiEndpoint
    StringPreference provideEndpointPreference(SharedPreferences preferences) {
        return new StringPreference(preferences, "debug_endpoint", ApiEndpoints.MOCK_MODE.url);
    }

    @Provides
    @ApplicationScope
    @IsMockMode
    boolean provideIsMockMode(@ApiEndpoint StringPreference endpoint,
                              @IsInstrumentationTest boolean isInstrumentationTest) {
        // Running in an instrumentation forces mock mode.
        return isInstrumentationTest || ApiEndpoints.isMockMode(endpoint.get());
    }

    @Provides
    @ApplicationScope
    @NetworkDelay
    LongPreference provideNetworkDelay(
            SharedPreferences preferences) {
        return new LongPreference(preferences, "debug_network_delay", 2000);
    }

    @Provides
    @ApplicationScope
    @NetworkFailurePercent
    IntPreference provideNetworkFailurePercent(
            SharedPreferences preferences) {
        return new IntPreference(preferences, "debug_network_failure_percent", 3);
    }

    @Provides
    @ApplicationScope
    @NetworkVariancePercent
    IntPreference provideNetworkVariancePercent(
            SharedPreferences preferences) {
        return new IntPreference(preferences, "debug_network_variance_percent", 40);
    }

    @Provides
    @ApplicationScope
    NetworkProxyPreference provideNetworkProxy(SharedPreferences preferences) {
        return new NetworkProxyPreference(preferences, "debug_network_proxy");
    }

    @Provides
    @ApplicationScope
    @CaptureIntents
    BooleanPreference provideCaptureIntentsPreference(SharedPreferences preferences) {
        return new BooleanPreference(preferences, "debug_capture_intents", DEFAULT_CAPTURE_INTENTS);
    }

    @Provides
    @ApplicationScope
    @AnimationSpeed
    IntPreference provideAnimationSpeed(SharedPreferences preferences) {
        return new IntPreference(preferences, "debug_animation_speed", DEFAULT_ANIMATION_SPEED);
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
    @SeenDebugDrawer
    BooleanPreference provideSeenDebugDrawer(SharedPreferences preferences) {
        return new BooleanPreference(preferences, "debug_seen_debug_drawer", DEFAULT_SEEN_DEBUG_DRAWER);
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

    @Provides
    @ApplicationScope
    Picasso providePicasso(OkHttpClient client, NetworkBehavior behavior,
                           @IsMockMode boolean isMockMode, Application app) {
        Picasso.Builder builder = new Picasso.Builder(app).downloader(new OkHttpDownloader(client));
        if (isMockMode) {
            builder.addRequestHandler(new MockRequestHandler(behavior, app.getAssets()));
        }
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Timber.e(exception, "Error while loading image " + uri);
            }
        });
        return builder.build();
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
