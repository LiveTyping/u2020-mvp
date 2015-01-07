package ru.ltst.u2020mvp.ui.navigation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ActivityConnector<AttachedObject> {

    @Nullable
    private AttachedObject attachedObject;
    // cause nextActivity.onStart() is called before prevActivity.onStop()
    // we need to explicitly store nextAttachedObject
    @Nullable
    private AttachedObject nextAttachedObject;

    public final void attach(@NonNull AttachedObject object) {
        if (this.attachedObject != null) {
            nextAttachedObject = object;
            return;
        }
        this.attachedObject = object;
    }

    public final void detach() {
        if (nextAttachedObject != null) {
            attachedObject = nextAttachedObject;
            nextAttachedObject = null;
        } else {
            attachedObject = null;
            nextAttachedObject = null;
        }
    }

    @Nullable
    protected AttachedObject getAttachedObject() {
        if (nextAttachedObject != null && attachedObject != null) {
            // if in transient state (prevActivity not detached)
            return nextAttachedObject;
        }
        return attachedObject;
    }
}
