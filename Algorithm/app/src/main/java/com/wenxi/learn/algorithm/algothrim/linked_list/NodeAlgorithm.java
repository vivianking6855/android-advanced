package com.wenxi.learn.algorithm.algothrim.linked_list;

import com.wenxi.learn.algorithm.algothrim.base.IAlgorithm;

/**
 * The type Node man.
 */
public class NodeAlgorithm implements IAlgorithm{


    /**
     * Add list node.
     * add two link, combine their single number to another new link
     * such as 0,1,2,3,4,5
     * with    0,0,1,2,3,5,9
     * will create 0,1,3,5,7,0,0,1
     *
     * @return the node
     */
    public Node addList() {

        Node head1 = new Node(0);
        Node head2 = new Node(0);
        mockListData(head1, head2);

        return addList(head1, head2);
    }

    private Node addList(Node head1, Node head2) {
        int n1 = 0; // list1 data
        int n2 = 0; // list2 data
        int n = 0; // current node data
        int ca = 0; // ca = n/10 进位
        Node node = null; // current node
        Node pre = null; // last node, to link node
        Node head = null; // head node

        while (head1 != null || head2 != null) {
            n1 = head1 == null ? 0 : head1.data;
            n2 = head2 == null ? 0 : head2.data;
            n = n1 + n2 + ca;
            node = new Node(n % 10);

            if (pre == null) {
                pre = node;
                head = node;
            } else {
                pre.next = node;
                pre = node;
            }

            ca = n / 10;
        }// end while

        // if last one add >10, ca =1, add new one node
        if (ca == 1) {
            node = new Node(ca);
            pre.next = node;
        }

        return head;
    }

    private void mockListData(Node head1, Node head2) {
        Node h1 = head1;
        Node h2 = head2;
        int[] one = new int[]{1, 2, 3, 4, 5};
        int[] two = new int[]{0, 1, 2, 3, 5, 9};
        for (int i : one) {
            h1.next = new Node(i);
            h1 = h1.next;
        }
        for (int i : two) {
            h2.next = new Node(i);
            h2 = h2.next;
        }
    }

    @Override
    public void startAlgorithm() {

    }
}
