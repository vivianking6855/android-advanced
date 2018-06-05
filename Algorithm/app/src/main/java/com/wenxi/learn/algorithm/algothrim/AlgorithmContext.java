package com.wenxi.learn.algorithm.algothrim;

import com.wenxi.learn.algorithm.algothrim.base.IAlgorithm;

public final class AlgorithmContext {
    private IAlgorithm algorithm;

    public void setAlgorithm(IAlgorithm val){
        algorithm = val;
    }

    public void startAlgorithm(){
       if(algorithm != null){
           algorithm.startAlgorithm();
       }
    }
}
