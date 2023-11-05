import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class myAVL {
    Node root;

    int limit;

    myAVL(int myLimit) {
        limit = myLimit;
    }

    // return true if date a is earlier than b
    public boolean compare(String a, String b) throws ParseException {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = dateFormat.parse(a);
            Date date2 = dateFormat.parse(b);

            return date1.before(date2);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception if the input strings are not in the correct format
            return false;
        }
    }

    // A utility function to get the height of the tree
    int height(Node N) {
        if (N == null)
            return 0;

        return N.height;
    }

    // A utility function to get maximum of two integers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
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

    // A utility function to left rotate subtree rooted with x
    // See the diagram given above.
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

    // Get Balance factor of node N
    int getBalance(Node N) {
        if (N == null)
            return 0;

        return height(N.left) - height(N.right);
    }

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

    // A utility function to print preorder traversal
    // of the tree.
    // The function also prints height of every node
    void preOrder(Node node) {
        if (node != null) {

            System.out.println(node.key.getDate());
            preOrder(node.left);
            preOrder(node.right);
        }

    }

    protected Node insert(Node node, SaleRecord key) {

        /* 1. Perform the normal BST insertion */
        if (node == null) {
            return (new Node(key));
        }

        try {
            // compare method not working right here, list of dates is not sorted (may have
            // something to do with program7 class)
            if (compare(node.key.getDate(), key.getDate())) {
                // System.out.println(node.key.getDate() + " " + key.getDate());
                node.left = insert(node.left, key);
            } else if (compare(key.getDate(), node.key.getDate())) {

                node.right = insert(node.right, key);
            }

            else { // Duplicate keys not allowed
                return node;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /* 2. Update height of this ancestor node */
        node.height = 1 + max(height(node.left),
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
                } else if (compare(key.getDate(), node.right.key.getDate())) {
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