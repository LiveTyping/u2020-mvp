package ru.ltst.u2020mvp.ui;

import android.app.Activity;
import android.view.ViewGroup;

import com.mattprecious.telescope.TelescopeLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.data.LumberYard;
import ru.ltst.u2020mvp.ui.bugreport.BugReportLens;

@ApplicationScope
public final class TelescopeAppContainer implements AppContainer {
    private final LumberYard lumberYard;

    @Inject
    public TelescopeAppContainer(LumberYard lumberYard) {
        this.lumberYard = lumberYard;
    }

    @InjectView(R.id.telescope_container)
    TelescopeLayout telescopeLayout;

    @Override
    public ViewGroup get(Activity activity) {
        activity.setContentView(R.layout.internal_activity_frame);
        ButterKnife.inject(this, activity);

        TelescopeLayout.cleanUp(activity); // Clean up any old screenshots.
        telescopeLayout.setLens(new BugReportLens(activity, lumberYard));

        return telescopeLayout;
    }
}
