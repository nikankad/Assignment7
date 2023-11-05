
/**
 * AVL tree implementation for storing SaleRecord objects based on their dates.
 * Author: Nikan Kadkhodazadeh
 * Version: 1
 * Github: https://github.com/nikankad/Assignment7
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class myAVL {
    Node root;
    int limit;

    /**
     * Constructor to create an AVL tree with a specified balance limit.
     *
     * @param myLimit the balance limit for the AVL tree.
     */
    myAVL(int myLimit) {
        limit = myLimit;
    }

    /**
     * Compares two date strings and returns true if the first date is earlier than
     * the second date.
     *
     * @param a the first date string.
     * @param b the second date string.
     * @return true if the first date is earlier than the second date, false
     *         otherwise.
     * @throws ParseException if the input strings are not in the correct date
     *                        format.
     */
    public boolean compare(String a, String b) throws ParseException {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = dateFormat.parse(a);
            Date date2 = dateFormat.parse(b);

            if (date1.before(date2)) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception if the input strings are not in the correct format
            return false;
        }
    }

    /**
     * Utility method to get the height of the given node.
     *
     * @param N the node to get the height from.
     * @return the height of the node.
     */
    int height(Node N) {
        if (N == null)
            return 0;

        return N.height;
    }

    /**
     * Utility method to get the maximum of two integers.
     *
     * @param a the first integer.
     * @param b the second integer.
     * @return the maximum of the two integers.
     */
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    /**
     * Performs right rotation on the subtree rooted with node y.
     *
     * @param y the root node of the subtree.
     * @return the new root of the subtree after rotation.
     */
    Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    /**
     * Performs left rotation on the subtree rooted with node x.
     *
     * @param x the root node of the subtree.
     * @return the new root of the subtree after rotation.
     */
    Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;

    }

    /**
     * Gets the balance factor of the given node.
     *
     * @param N the node to get the balance factor from.
     * @return the balance factor of the node.
     */
    int getBalance(Node N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }

    /**
     * Rebalances the AVL tree after insertion.
     *
     * @param node the root node of the subtree to be rebalanced.
     * @return the new root of the subtree after rebalancing.
     */
    private Node rebalance(Node node) {
        int balanceFactor = getBalance(node);

        // Left-heavy?
        if (balanceFactor < -1) {
            if (getBalance(node.left) <= 0) { // Case 1
                // Rotate right
                node = rightRotate(node);
            } else { // Case 2
                // Rotate left-right
                node.left = leftRotate(node.left);
                node = rightRotate(node);
            }
        }

        // Right-heavy?
        if (balanceFactor > 1) {
            if (getBalance(node.right) >= 0) { // Case 3
                // Rotate left
                node = leftRotate(node);
            } else { // Case 4
                // Rotate right-left
                node.right = rightRotate(node.right);
                node = leftRotate(node);
            }
        }

        return node;
    }

    /**
     * Performs preorder traversal of the AVL tree and prints the nodes.
     *
     * @param node the root node of the subtree to be traversed.
     */
    void preOrder(Node node) {
        if (node != null) {

            System.out.println(node.key.toString());
            preOrder(node.left);
            preOrder(node.right);
        }

    }

    /**
     * Inserts a new SaleRecord node into the AVL tree while maintaining balance.
     *
     * @param node the current root node of the subtree.
     * @param key  the SaleRecord object to be inserted.
     * @return the new root of the subtree after insertion and rebalancing.
     * @throws ParseException if the date strings cannot be parsed.
     */
    protected Node insert(Node node, SaleRecord key) throws ParseException {

        /* 1. Perform the normal BST insertion */
        if (node == null) {
            return (new Node(key));
        }

        // try {
        // compare method not working right here, list of dates is not sorted (may have
        // something to do with program7 class)
        if (compare(node.key.getDate(), key.getDate())) {

            // System.out.println(compare(node.key.getDate(), key.getDate()));
            node.left = insert(node.left, key);
        } else if (!compare(node.key.getDate(), key.getDate())) {
            node.right = insert(node.right, key);
        }

        else { // Duplicate keys not allowed
            return node;
        }
        // } catch (ParseException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();

        /* 2. Update height of this ancestor node */
        node.height = 1 +

                max(height(node.left),
                        height(node.right));

        /*
         * 3. Get the balance factor of this ancestor
         * node to check whether this node became
         * unbalanced
         */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > limit) {
            try {
                if (compare(key.getDate(), node.left.key.getDate())) {
                    return rightRotate(node);
                } else if (compare(node.left.key.getDate(), key.getDate())) {
                    node.left = leftRotate(node.left);
                    return rightRotate(node);
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (balance < -limit) {
            try {
                if (compare(key.getDate(), node.right.key.getDate())) {
                    return leftRotate(node);
                } else if (compare(node.right.key.getDate(), key.getDate())) {
                    node.right = rightRotate(node.right);
                    return leftRotate(node);
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return node;
    }
}