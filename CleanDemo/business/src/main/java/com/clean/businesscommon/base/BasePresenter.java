package com.clean.businesscommon.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * The type Base presenter.
 *
 * @param <V> the type parameter
 */
public abstract class BasePresenter<V> {

    /**
     * The weak reference of V in MVP.
     */
    protected Reference<V> mOuterWeakRef;

    /**
     * Attach reference.
     *
     * @param ref the ref
     */
    public void attachReference(V ref) {
        mOuterWeakRef = new WeakReference<>(ref);
    }

    /**
     * Detach reference.
     */
    public void detachReference() {
        if (mOuterWeakRef != null) {
            mOuterWeakRef.clear();
            mOuterWeakRef = null;
        }
    }

    /**
     * Gets outer reference.
     *
     * @return the outer reference
     */
    protected V getOuterReference() {
        return mOuterWeakRef.get();
    }

    /**
     * Is out reference active boolean.
     *
     * @return the boolean
     */
    public boolean isOutReferenceActive() {
        return mOuterWeakRef != null && mOuterWeakRef.get() != null;
    }
}
