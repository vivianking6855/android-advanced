package com.learn.algothrim.tree;

import com.learn.algothrim.base.IAlgorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * The type Tree algorithm.
 * 递归对于很多数据结构来说，就是深度优先，比如二叉树，图
 * 参考：
 * 【数据结构与算法04】二叉树 https://blog.csdn.net/eson_15/article/details/51138663
 * Android程序员面试会遇到的算法 part 1:
 * https://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650825031&idx=1&sn=f07a913f332f5ddf2054f7ace0b9ceaa&chksm=80b7b5d9b7c03ccff47951986df91202f5a13a438dc42d05836e3e6591ecbce713e062a92fbd&mpshare=1&scene=1&srcid=0425QfT8ZVAfLXthl49OhkJB#rd
 */
public class TreeAlgorithm implements IAlgorithm {

    /**
     * Start algorithm.
     */
    @Override
    public void startAlgorithm() {
        TreeNode<String> root = new TreeNode("A", null);
        mockData(root);

        // 广度优先，队列
        printTravelsalOneLine(root);
        //printTravelsal(root);

        // 深度优先，递归
        //printPreoderTree(root);
        //printInoderTree(root);
        //printPostTree(root);
    }

    /**
     * Gets instance with inner static class way
     *
     * @return the instance
     */
    public static TreeAlgorithm getInstance() {
        return TreeAlgorithm.Holder.INSTANCE;
    }

    private TreeAlgorithm() {
    }

    private static class Holder {
        private static final TreeAlgorithm INSTANCE = new TreeAlgorithm();
    }

    //-----------深度优先，前序，中序，后序（递归）--------------------

    /**
     * 前序
     *
     * @param root the root
     */
    private void printPreoderTree(TreeNode root) {
        if (root == null) {
            return;
        }
        System.out.print("Pre " + root.toString());
        printPreoderTree(root.left);
        printPreoderTree(root.right);
    }

    /**
     * 中序
     *
     * @param root the root
     */
    private void printInoderTree(TreeNode root) {
        if (root == null) {
            return;
        }
        printInoderTree(root.left);
        System.out.print("In " + root.toString());
        printInoderTree(root.right);
    }

    /**
     * 后序
     *
     * @param root the root
     */
    private void printPostTree(TreeNode root) {
        if (root == null) {
            return;
        }
        printPostTree(root.left);
        printPostTree(root.right);
        System.out.print("Post " + root.toString());
    }

    /**
     * A
     * /        \
     * B          C
     * /    \     /   \
     * D     E    F     G
     *
     * @param root the root
     */
    private void mockData(TreeNode root) {
        TreeNode<String> B = new TreeNode<>("B", root);
        TreeNode<String> D = new TreeNode<>("D", B);
        TreeNode<String> E = new TreeNode<>("E", B);
        B.left = D;
        B.right = E;

        TreeNode<String> C = new TreeNode<>("C", root);
        TreeNode<String> F = new TreeNode<>("F", C);
        TreeNode<String> G = new TreeNode<>("G", C);
        C.left = F;
        C.right = G;

        root.left = B;
        root.right = C;
    }

    /**
     * 广度/宽度优先：使用Queue/链表，先入先出特性实现
     */
    private void printTravelsal(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.toString());
            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }

    /**
     * 广度/宽度优先：使用Queue/链表，先入先出特性实现
     * 同一层打印在一起，下一层换行
     */
    private void printTravelsalOneLine(TreeNode root) {
        if (root == null) {
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode last = root; // 当前层最后节点
        TreeNode next_last = root; // 下一层最后节点
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            System.out.print(current.toString());
            //Log.d(LOG_TAG,current.toString());
            if (current.left != null) {
                queue.add(current.left);
                next_last = current.left;
            }
            if (current.right != null) {
                queue.add(current.right);
                next_last = current.right;
            }
            // 如果是当前层最后一个，换行并更新last
            if (current == last) {
                System.out.println();
                last = next_last;
            }
        }
    }
}
