package com.wenxi.learn.algorithm.algothrim;

import com.wenxi.learn.algorithm.algothrim.base.IAlgorithm;
import com.wenxi.learn.algorithm.algothrim.contact.ChineseToPinYin;
import com.wenxi.learn.algorithm.algothrim.linked_list.LinkedNodeAlgorithm;
import com.wenxi.learn.algorithm.algothrim.tree.JieTiTree;
import com.wenxi.learn.algorithm.algothrim.tree.TreeAlgorithm;

import java.util.ArrayList;
import java.util.List;

public final class AlgorithmFactory {
    private List<IAlgorithm> list = new ArrayList<>();

    /**
     * Gets instance with inner static class way
     *
     * @return the instance
     */
    public static AlgorithmFactory getInstance() {
        return AlgorithmFactory.Holder.INSTANCE;
    }

    private AlgorithmFactory() {
        init();
    }

    private static class Holder {
        private static final AlgorithmFactory INSTANCE = new AlgorithmFactory();
    }

    private void init() {
        list.add(JieTiTree.getInstance());
        list.add(ChineseToPinYin.getInstance());
        list.add(LinkedNodeAlgorithm.getInstance());
        list.add(TreeAlgorithm.getInstance());
    }

    public IAlgorithm createAlgorithm(int position) {
        if (list == null || position > list.size()) {
            return null;
        }
        return list.get(position);
    }

    public void destroy() {
        if (list != null) {
            list.clear();
            list = null;
        }
    }
}
