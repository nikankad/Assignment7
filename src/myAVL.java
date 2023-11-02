public class myAVL {
   Node root;

   //returns height of selected node (N : Node)
   int height(Node N){
    if(N == null){
        return 0;
    }

    return N.height;
   }

//  returns max of 2 numbers (a, b) : int
   int max(int a, int b){
    return(a>b) ? a:b;
   }

   // updates height of Node N
   private void updateHeight(Node N){
    int leftChildHeight = height(N.left);
    int rightChildHeight = height(N.right);

    N.height = max(leftChildHeight, rightChildHeight) + 1;
   }

   // Balance factor (R, L, 2R, 2L)
   public int balanceFactor(Node N){
        return height(N.left) - height(N.right);
   }

// rotates node N right
   private Node rotateRight(Node N){
       Node leftChild = N.left;
//diy
       return leftChild;
   }
//  rotates node N left
   private Node rotateLeft(Node N){
       Node rightChild = N.right;
//write yourself
       return rightChild;
   }
// rebalences subtree from node 
    private Node rebalance(Node N) {
        int balanceFactor = balanceFactor(N);

        // Left-heavy
        if (balanceFactor < -1) {
            if (balanceFactor(N.left) <= 0) {    // Case 1
                // Rotate right
                N = rotateRight(N);
            } else {                                // Case 2
                // Rotate left-right
                N.left = rotateLeft(N.left);
                N = rotateRight(N);
            }
        }

        // Right-heavy?
        if (balanceFactor > 1) {
            if (balanceFactor(N.right) >= 0) {    // Case 3
                // Rotate left
                N = rotateLeft(N);
            } else {                                 // Case 4
                // Rotate right-left
                N.right = rotateRight(N.right);
                N = rotateLeft(N);
            }
        }

        return N;
    }



    Node insert(Node N, int data) {
        // Step 1: Perform the normal BST insertion
        if (N == null) {
            return new Node(data);
        }

        if (data < N.data) {
            N.left = insert(N.left, data);
        } else if (data > N.data) {
            N.right = insert(N.right, data);
        } else {
            // Duplicate data is not allowed in AVL tree, so do nothing
            return N;
        }

        // Step 2: Update height of the current node
        N.height = 1 + max(height(N.left), height(N.right));

        // Step 3: Get the balance factor of this node to check if it is unbalanced
        int balanceFactor = balanceFactor(N);

        // Step 4: Perform rotations if the node is unbalanced
        // Left Heavy (Left-Left case)
        if (balanceFactor > 1 && data < N.left.data) {
            return rotateRight(N);
        }
        // Right Heavy (Right-Right case)
        if (balanceFactor < -1 && data > N.right.data) {
            return rotateLeft(N);
        }
        // Left Heavy (Left-Right case)
        if (balanceFactor > 1 && data > N.left.data) {
            N.left = rotateLeft(N.left);
            return rotateRight(N);
        }
        // Right Heavy (Right-Left case)
        if (balanceFactor < -1 && data < N.right.data) {
            N.right = rotateRight(N.right);
            return rotateLeft(N);
        }

        // If the node is balanced, return it
        return N;
    }



    void preOrder(Node N) {
        if (N != null) {
            System.out.print(N.data + " ");
            preOrder(N.left);
            preOrder(N.right);
        }
    }

    


    public static void main(String[] args) {
        myAVL tree = new myAVL();

        /* Constructing tree given in the above figure */
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 30);
        tree.root = tree.insert(tree.root, 40);
        tree.root = tree.insert(tree.root, 50);
        tree.root = tree.insert(tree.root, 25);
        
        /* The constructed AVL Tree would be
             30
            /  \
          20   40
         /  \     \
        10  25    50
        */
        System.out.println("Preorder traversal" +
                " of constructed tree is : ");
        tree.preOrder(tree.root);
    }






}