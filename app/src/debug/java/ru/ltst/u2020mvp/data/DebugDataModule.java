package ru.ltst.u2020mvp.data;

import android.app.Application;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.net.InetSocketAddress;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.mock.NetworkBehavior;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.IsInstrumentationTest;
import ru.ltst.u2020mvp.data.api.DebugApiModule;
import ru.ltst.u2020mvp.data.api.oauth.AccessToken;
import ru.ltst.u2020mvp.data.prefs.InetSocketAddressPreferenceAdapter;
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
    IntentFactory provideIntentFactory(@IsMockMode boolean isMockMode,
                                       @CaptureIntents Preference<Boolean> captureIntents) {
        return new DebugIntentFactory(IntentFactory.REAL, isMockMode, captureIntents);
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(Application app,
                                     Preference<InetSocketAddress> networkProxyAddress) {
        return DataModule.createOkHttpClient(app)
                .sslSocketFactory(createBadSslSocketFactory())
                .proxy(InetSocketAddressPreferenceAdapter.createProxy(networkProxyAddress.get()))
                .build();
    }

    @Provides
    @ApplicationScope
    @ApiEndpoint
    Preference<String> provideEndpointPreference(RxSharedPreferences prefs) {
        return prefs.getString("debug_endpoint", ApiEndpoints.MOCK_MODE.url);
    }

    @Provides
    @ApplicationScope
    @IsMockMode
    boolean provideIsMockMode(@ApiEndpoint Preference<String> endpoint,
                              @IsInstrumentationTest boolean isInstrumentationTest) {
        // Running in an instrumentation forces mock mode.
        return isInstrumentationTest || ApiEndpoints.isMockMode(endpoint.get());
    }

    @Provides
    @ApplicationScope
    @NetworkDelay
    Preference<Long> provideNetworkDelay(RxSharedPreferences prefs) {
        return prefs.getLong("debug_network_delay", 2000L);
    }

    @Provides
    @ApplicationScope
    @NetworkFailurePercent
    Preference<Integer> provideNetworkFailurePercent(RxSharedPreferences prefs) {
        return prefs.getInteger("debug_network_failure_percent", 3);
    }

    @Provides
    @ApplicationScope
    @NetworkVariancePercent
    Preference<Integer> provideNetworkVariancePercent(RxSharedPreferences prefs) {
        return prefs.getInteger("debug_network_variance_percent", 40);
    }

    @Provides
    @ApplicationScope
    Preference<InetSocketAddress> provideNetworkProxyAddress(RxSharedPreferences preferences) {
        return preferences.getObject("debug_network_proxy", InetSocketAddressPreferenceAdapter.INSTANCE);
    }

    @Provides
    @ApplicationScope
    @CaptureIntents
    Preference<Boolean> provideCaptureIntentsPreference(RxSharedPreferences prefs) {
        return prefs.getBoolean("debug_capture_intents", DEFAULT_CAPTURE_INTENTS);
    }

    @Provides
    @ApplicationScope
    @AnimationSpeed
    Preference<Integer> provideAnimationSpeed(RxSharedPreferences prefs) {
        return prefs.getInteger("debug_animation_speed", DEFAULT_ANIMATION_SPEED);
    }

    @Provides
    @ApplicationScope
    @PicassoDebugging
    Preference<Boolean> providePicassoDebugging(RxSharedPreferences prefs) {
        return prefs.getBoolean("debug_picasso_debugging", DEFAULT_PICASSO_DEBUGGING);
    }

    @Provides
    @ApplicationScope
    @PixelGridEnabled
    Preference<Boolean> providePixelGridEnabled(RxSharedPreferences prefs) {
        return prefs.getBoolean("debug_pixel_grid_enabled", DEFAULT_PIXEL_GRID_ENABLED);
    }

    @Provides
    @ApplicationScope
    @PixelRatioEnabled
    Preference<Boolean> providePixelRatioEnabled(RxSharedPreferences prefs) {
        return prefs.getBoolean("debug_pixel_ratio_enabled", DEFAULT_PIXEL_RATIO_ENABLED);
    }

    @Provides
    @ApplicationScope
    @SeenDebugDrawer
    Preference<Boolean> provideSeenDebugDrawer(RxSharedPreferences prefs) {
        return prefs.getBoolean("debug_seen_debug_drawer", DEFAULT_SEEN_DEBUG_DRAWER);
    }

    @Provides
    @ApplicationScope
    @ScalpelEnabled
    Preference<Boolean> provideScalpelEnabled(RxSharedPreferences prefs) {
        return prefs.getBoolean("debug_scalpel_enabled", DEFAULT_SCALPEL_ENABLED);
    }

    @Provides
    @ApplicationScope
    @ScalpelWireframeEnabled
    Preference<Boolean> provideScalpelWireframeEnabled(RxSharedPreferences prefs) {
        return prefs.getBoolean("debug_scalpel_wireframe_drawer", DEFAULT_SCALPEL_WIREFRAME_ENABLED);
    }

    @Provides
    @ApplicationScope
    Picasso providePicasso(OkHttpClient client, NetworkBehavior behavior,
                           @IsMockMode boolean isMockMode, Application app) {
        Picasso.Builder builder = new Picasso.Builder(app).downloader(new OkHttp3Downloader(client));
        if (isMockMode) {
            builder.addRequestHandler(new MockRequestHandler(behavior, app.getAssets()));
        }
        builder.listener((picasso, uri, exception) -> Timber.e(exception, "Error while loading image %s", uri));
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
                    return new X509Certificate[0];
                }
            };
            context.init(null, new TrustManager[]{permissive}, null);
            return context.getSocketFactory();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
