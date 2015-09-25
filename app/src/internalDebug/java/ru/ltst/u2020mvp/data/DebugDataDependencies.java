package ru.ltst.u2020mvp.data;

import retrofit.mock.NetworkBehavior;
import ru.ltst.u2020mvp.data.prefs.BooleanPreference;
import ru.ltst.u2020mvp.data.prefs.IntPreference;
import ru.ltst.u2020mvp.data.prefs.LongPreference;
import ru.ltst.u2020mvp.data.prefs.NetworkProxyPreference;
import ru.ltst.u2020mvp.data.prefs.StringPreference;

public interface DebugDataDependencies {

    @IsMockMode boolean isMockMode();
    @ApiEndpoint StringPreference networkEndpoint();
    @CaptureIntents BooleanPreference captureIntents();
    @AnimationSpeed IntPreference animationSpeed();
    @PicassoDebugging BooleanPreference picassoDebugging();
    @PixelGridEnabled BooleanPreference pixelGridEnabled();
    @PixelRatioEnabled BooleanPreference pixelRatioEnabled();
    @ScalpelEnabled BooleanPreference scalpelEnabled();
    @ScalpelWireframeEnabled BooleanPreference scalpelWireframeEnabled();
    @NetworkDelay LongPreference networkDelay();
    @NetworkFailurePercent IntPreference networkFailurePercent();
    @NetworkVariancePercent IntPreference networkVariancePercent();
    NetworkBehavior behavior();
    NetworkProxyPreference networkProxy();

}
