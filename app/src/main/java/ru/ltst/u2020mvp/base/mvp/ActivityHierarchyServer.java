package ru.ltst.u2020mvp.base.mvp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * A "view server" adaptation which automatically hooks itself up to all activities.
 */
public interface ActivityHierarchyServer extends Application.ActivityLifecycleCallbacks {
    /**
     * An {@link ActivityHierarchyServer} which does nothing.
     */
    ActivityHierarchyServer NONE = new ActivityHierarchyServer() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityStarted(Activity activity) {
        }

        @Override
        public void onActivityResumed(Activity activity) {
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
        }
    };

    class Proxy implements ActivityHierarchyServer {
        private List<ActivityHierarchyServer> servers = new ArrayList<>();

        public void addServer(ActivityHierarchyServer server) {
            servers.add(server);
        }

        public void removeServer(ActivityHierarchyServer server) {
            servers.remove(server);
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            for (ActivityHierarchyServer server : servers) {
                server.onActivityCreated(activity, savedInstanceState);
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {
            for (ActivityHierarchyServer server : servers) {
                server.onActivityStarted(activity);
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            for (ActivityHierarchyServer server : servers) {
                server.onActivityResumed(activity);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            for (ActivityHierarchyServer server : servers) {
                server.onActivityPaused(activity);
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            for (ActivityHierarchyServer server : servers) {
                server.onActivityStopped(activity);
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            for (ActivityHierarchyServer server : servers) {
                server.onActivitySaveInstanceState(activity, outState);
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            for (ActivityHierarchyServer server : servers) {
                server.onActivityDestroyed(activity);
            }
        }
    }

    class Empty implements ActivityHierarchyServer {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
