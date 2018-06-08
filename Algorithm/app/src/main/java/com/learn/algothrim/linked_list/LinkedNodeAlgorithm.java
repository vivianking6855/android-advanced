package com.learn.algothrim.linked_list;

import android.util.Log;

import com.learn.algothrim.base.IAlgorithm;

import java.util.Stack;

import static com.learn.utils.Const.LOG_TAG;

/**
 * The type Node man.
 */
public class LinkedNodeAlgorithm implements IAlgorithm {

    /**
     * Gets instance with inner static class way
     *
     * @return the instance
     */
    public static LinkedNodeAlgorithm getInstance() {
        return LinkedNodeAlgorithm.Holder.INSTANCE;
    }

    private LinkedNodeAlgorithm() {
    }

    private static class Holder {
        private static final LinkedNodeAlgorithm INSTANCE = new LinkedNodeAlgorithm();
    }


    //--------------------------------------相加链表-------------------------------------------------

    /**
     * 两个单链表生成相加链表
     * summary two linked listed.
     * combine their single number to another new link
     * such as 0,1,2,3,4,5
     * with    0,0,1,2,3,5,9
     * will create 0,1,3,5,7,0,0,1
     *
     * @return the node
     */
    private void addTwoLinks() {

        Node head1 = new Node(0);
        Node head2 = new Node(0);
        mockTwoListData(head1, head2);
        print("addTwoLinks 1. ", head1);
        print("addTwoLinks 2. ", head2);

        print("addTwoLinks 3. ", addTwoLinks(head1, head2));
    }

    private Node addTwoLinks(Node head1, Node head2) {
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

            head1 = head1 == null ? null : head1.next;
            head2 = head2 == null ? null : head2.next;
        }// end while

        // if last one add >10, ca =1, add new one node
        if (ca == 1) {
            node = new Node(ca);
            pre.next = node;
        }

        return head;
    }

    /**
     * mack data two linked list
     */
    private void mockTwoListData(Node head1, Node head2) {
        int[] one = new int[]{1, 2, 3, 4, 5};
        int[] two = new int[]{0, 1, 2, 3, 5, 9};
        for (int i : one) {
            head1.next = new Node(i);
            head1 = head1.next;
        }
        for (int i : two) {
            head2.next = new Node(i);
            head2 = head2.next;
        }
    }
    //-------------------------------相加链表结束---------------------------------------------

    //------------------------------Palindrome 回文-----------------------------------------------
    private void testPalindrome() {
        Node head = new Node(0);
        mockPalindromeData(head);
        print("testPalindrome ", head);
        Log.d(LOG_TAG, "isPalindrome " + isPalindrome(head));
    }

    /**
     * mack palindrome data
     */
    private void mockPalindromeData(Node head) {
        int[] one = new int[]{1, 2, 3, 2, 1, 0};
        for (int i : one) {
            head.next = new Node(i);
            head = head.next;
        }
    }

    private boolean isPalindrome(Node head) {
        if (head == null) {
            return false;
        }

        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            if (cur.data != stack.pop().data) {
                return false;
            }
            cur = cur.next;
        }

        return true;
    }

    //------------------------------Palindrome END-----------------------------------------------

    /**
     * 遍历单链表，打印所有data
     *
     * @param prefix log prefix
     * @param head   the head
     */
    private void print(String prefix, Node head) {
        if (head == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(50)
                .append(prefix)
                .append("Node is [");
        Node h = head;
        while (h != null) {
            sb.append(h.data).append(",");
            h = h.next;
        }
        sb.replace(sb.length() - 1, sb.length(), "]");
        Log.d(LOG_TAG, sb.toString());
    }

    @Override
    public void startAlgorithm() {
        LinkedNodeAlgorithm.getInstance().addTwoLinks();

        LinkedNodeAlgorithm.getInstance().testPalindrome();
    }

}
