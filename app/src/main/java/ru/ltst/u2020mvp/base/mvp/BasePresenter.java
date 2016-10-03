package ru.ltst.u2020mvp.base.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public abstract class BasePresenter<V extends BaseView> {
    private static final String BF_ON_RESULT_OBJECT = "BasePresenter.on.result.object";


    private WeakReference<V> view = null;

    /**
     * Load has been called for the current {@link #view}.
     */

    private boolean loaded;

    private OnActivityResult onActivityResult;


    public final void takeView(V view) {
        if (view == null) throw new NullPointerException("new view must not be null");

        if (this.view != null) dropView(this.view.get());

        this.view = new WeakReference<>(view);
        if (!loaded) {
            loaded = true;
            onLoad(onActivityResult);
            onActivityResult = null;
        }
    }

    public final void dropView(V view) {
        if (view == null) throw new NullPointerException("dropped view must not be null");
        loaded = false;
        this.view = null;
        onDestroy();
    }

    public boolean hasView() {
        return view != null;
    }

    protected final V getView() {
        if (view == null) throw new NullPointerException("getView called when view is null. Ensure takeView(View view) is called first.");
        return view.get();
    }

    protected void onLoad(OnActivityResult onActivityResult) {
        if (onActivityResult != null) {
            onResult(onActivityResult);
        }
    }

    protected void onDestroy() {
    }

    protected void onRestore(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(BF_ON_RESULT_OBJECT)) {
            onActivityResult = savedInstanceState.getParcelable(BF_ON_RESULT_OBJECT);
        }
    }

    protected void onResult(OnActivityResult onActivityResult) {
    }

    protected void onSave(@NonNull Bundle outState) {
        if (onActivityResult != null) {
            outState.putParcelable(BF_ON_RESULT_OBJECT, onActivityResult);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<Integer> integers = requestCodes();
        if (resultCode == Activity.RESULT_OK && integers.contains(requestCode)) {
            onActivityResult = new OnActivityResult(requestCode, resultCode, data);
            if (hasView()) {
                onResult(onActivityResult);
                onActivityResult = null;
            }
        }
    }

    protected ArrayList<Integer> requestCodes() {
        return new ArrayList<>();
    }

    public void onNetworkConnectionStateChanged(boolean isConnected) {
    }

    public static final class OnActivityResult implements Parcelable {
        private int requestCode;
        private int resultCode;
        private Intent data;

        public OnActivityResult(int requestCode, int resultCode, Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }

        public int getRequestCode() {
            return requestCode;
        }

        public Intent getData() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            OnActivityResult that = (OnActivityResult) o;

            if (requestCode != that.requestCode) return false;
            if (resultCode != that.resultCode) return false;
            return data != null ? data.equals(that.data) : that.data == null;

        }

        @Override
        public int hashCode() {
            int result = requestCode;
            result = 31 * result + resultCode;
            result = 31 * result + (data != null ? data.hashCode() : 0);
            return result;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.requestCode);
            dest.writeInt(this.resultCode);
            dest.writeParcelable(this.data, flags);
        }

        protected OnActivityResult(Parcel in) {
            this.requestCode = in.readInt();
            this.resultCode = in.readInt();
            this.data = in.readParcelable(Intent.class.getClassLoader());
        }

        public static final Parcelable.Creator<OnActivityResult> CREATOR = new Parcelable.Creator<OnActivityResult>() {
            @Override
            public OnActivityResult createFromParcel(Parcel source) {
                return new OnActivityResult(source);
            }

            @Override
            public OnActivityResult[] newArray(int size) {
                return new OnActivityResult[size];
            }
        };
    }
}
