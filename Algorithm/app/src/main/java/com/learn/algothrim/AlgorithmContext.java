package com.learn.algothrim;

import com.learn.algothrim.base.IAlgorithm;

public final class AlgorithmContext {
    /**
     * Gets instance with inner static class way
     *
     * @return the instance
     */
    public static AlgorithmContext getInstance() {
        return AlgorithmContext.Holder.INSTANCE;
    }

    private AlgorithmContext() {
    }

    private static class Holder {
        private static final AlgorithmContext INSTANCE = new AlgorithmContext();
    }

    private IAlgorithm algorithm;

    public void setAlgorithm(IAlgorithm val) {
        algorithm = val;
    }

    public void startAlgorithm() {
        if (algorithm != null) {
            algorithm.startAlgorithm();
        }
    }
}
