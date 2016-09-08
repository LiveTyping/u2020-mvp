package ru.ltst.u2020mvp.ui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.mattprecious.telescope.Lens;
import com.mattprecious.telescope.TelescopeLayout;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.ltst.u2020mvp.ApplicationScope;
import ru.ltst.u2020mvp.R;
import ru.ltst.u2020mvp.base.mvp.ViewContainer;
import ru.ltst.u2020mvp.data.LumberYard;
import ru.ltst.u2020mvp.ui.bugreport.BugReportLens;

@ApplicationScope
public final class TelescopeViewContainer implements ViewContainer {
    private final LumberYard lumberYard;
    private final Preference<Boolean> seenTelescopeDialog;

    @Inject
    public TelescopeViewContainer(LumberYard lumberYard, RxSharedPreferences preferences) {
        this.lumberYard = lumberYard;
        this.seenTelescopeDialog = preferences.getBoolean("internal-seen-telescope-dialog", false);
    }

    @BindView(R.id.telescope_container)
    TelescopeLayout telescopeLayout;

    @Override
    public ViewGroup forActivity(final Activity activity) {
        activity.setContentView(R.layout.internal_activity_frame);
        ButterKnife.bind(this, activity);

        TelescopeLayout.cleanUp(activity); // Clean up any old screenshots.
        telescopeLayout.setLens(new BugReportLens(activity, lumberYard));

        // If you have not seen the telescope dialog before, show it.
        if (!seenTelescopeDialog.get()) {
            telescopeLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (activity.isFinishing()) {
                        return;
                    }

                    seenTelescopeDialog.set(true);
                    showTelescopeDialog(activity);
                }
            }, 1000);
        }

        return telescopeLayout;
    }

    public void showTelescopeDialog(final Activity activity) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        TelescopeLayout content =
                (TelescopeLayout) inflater.inflate(R.layout.telescope_tutorial_dialog, null);
        final AlertDialog dialog =
                new AlertDialog.Builder(activity).setView(content).setCancelable(false).create();

        content.setLens(new Lens() {
            @Override
            public void onCapture(File file) {
                dialog.dismiss();

                Context toastContext = new ContextThemeWrapper(activity, android.R.style.Theme_DeviceDefault_Dialog);
                LayoutInflater toastInflater = LayoutInflater.from(toastContext);
                Toast toast = Toast.makeText(toastContext, "", Toast.LENGTH_SHORT);
                View toastView = toastInflater.inflate(R.layout.telescope_tutorial_toast, null);
                toast.setView(toastView);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        dialog.show();
    }
}