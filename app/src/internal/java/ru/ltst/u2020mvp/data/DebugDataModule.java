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
import ru.ltst.u2020mvp.data.prefs.StringPreference;
import ru.ltst.u2020mvp.ui.ApplicationScope;
import timber.log.Timber;

@Module(includes = { DataModule.class, DebugApiModule.class})
public final class DebugDataModule {

    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient(Application app) {
        OkHttpClient client = DataModule.createOkHttpClient(app);
        client.setSslSocketFactory(createBadSslSocketFactory());
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
