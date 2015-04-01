package ru.ltst.u2020mvp.base.navigation.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.LinkedList;

public class ActivityConnector<AttachedObject> {
    private LinkedList<WeakReference<AttachedObject>> linkedList = new LinkedList<>();
    private WeakReference<AttachedObject> attachedObjectRef;

    public final void attach(@NonNull AttachedObject object) {
        final WeakReference<AttachedObject> weakReference = new WeakReference<>(object);
        if (attachedObjectRef != null) {
            linkedList.offer(weakReference);
            return;
        }
        attachedObjectRef = weakReference;
    }

    public final void detach() {
        if (linkedList.isEmpty()) {
            attachedObjectRef = null;
        } else {
            attachedObjectRef = linkedList.poll();
        }
    }

    @Nullable
    protected AttachedObject getAttachedObject() {
        if (!linkedList.isEmpty())
            return linkedList.getLast().get();
        if (attachedObjectRef == null)
            return null;
        return attachedObjectRef.get();
    }
}
