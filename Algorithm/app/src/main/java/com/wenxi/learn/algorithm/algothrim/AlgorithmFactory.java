package com.wenxi.learn.algorithm.algothrim;

import com.wenxi.learn.algorithm.algothrim.base.IAlgorithm;
import com.wenxi.learn.algorithm.algothrim.contact.ChineseToPinYin;
import com.wenxi.learn.algorithm.algothrim.linked_list.NodeAlgorithm;
import com.wenxi.learn.algorithm.algothrim.tree.JieTiTree;

import java.util.ArrayList;
import java.util.List;

public final class AlgorithmFactory {
    private List<IAlgorithm> list = new ArrayList<>();

    public AlgorithmFactory() {
        list.add(JieTiTree.getInstance());
        list.add(ChineseToPinYin.getInstance());
        list.add(new NodeAlgorithm());
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
