package com.wenxi.learn.algorithm.algothrim.tree;

class TreeNode<T> {
    T key; //关键字(键值)
    TreeNode<T> left; //左子节点
    TreeNode<T> right; //右子节点
    TreeNode<T> parent; //父节点

    public TreeNode(T key, TreeNode<T> parent) {
        this.key = key;
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "{key:" + key + "}";
    }
}
