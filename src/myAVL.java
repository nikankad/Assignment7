public class myAVL {
   Node root;


   int height(Node N){
    if(N == null){
        return 0;
    }

    return N.height;
   }

   int max(int a, int b){
    return(a>b) ? a:b;
   }

   private void updateHeight(Node N){

    int leftChildHeight = height(N.left);
    int rightChildHeight = height(N.right);

    N.height = max(leftChildHeight, rightChildHeight) + 1;
   }

   public int balanceFactor(Node N){
        return height(N.right) - height(N.left);
   }


   private Node rotateRight(Node N){
       Node leftChild = N.left;

       N.left = leftChild.right;

       leftChild.right = N;

       updateHeight(N);
       updateHeight(leftChild);

       return leftChild;
   }

   private Node rotateLeft(Node N){
       Node rightChild = N.right;

       N.right = rightChild.left;

       rightChild.left = N;

       updateHeight(N);
       updateHeight(rightChild);

       return rightChild;
   }

    private Node rebalance(Node node) {
        int balanceFactor = balanceFactor(node);

        // Left-heavy?
        if (balanceFactor < -1) {
            if (balanceFactor(node.left) <= 0) {    // Case 1
                // Rotate right
                node = rotateRight(node);
            } else {                                // Case 2
                // Rotate left-right
                node.left = rotateLeft(node.left);
                node = rotateRight(node);
            }
        }

        // Right-heavy?
        if (balanceFactor > 1) {
            if (balanceFactor(node.right) >= 0) {    // Case 3
                // Rotate left
                node = rotateLeft(node);
            } else {                                 // Case 4
                // Rotate right-left
                node.right = rotateRight(node.right);
                node = rotateLeft(node);
            }
        }

        return node;
    }



    Node insert(Node N, int data){

       if(N == null){
           return new Node(data);
       }

       if(data < N.data) {
           N.left = insert(N.left, data);
       } else if (data > N.data) {
           N.right = insert(N.right, data);
       }else{
           return N;
       }

       N.height = 1 + max(height(N.left), height(N.right));


       int balance = balanceFactor(N);

        // are 4 cases Left Left Case
        if (balance > 1 && data < N.left.data)
            return rotateRight(N);

        // Right Right Case
        if (balance < -1 && data > N.right.data)
            return rotateLeft(N);

        // Left Right Case
        if (balance > 1 && data > N.left.data) {
            N.left = rotateLeft(N.left);
            return rotateRight(N);
        }

        // Right Left Case
        if (balance < -1 && data < N.right.data) {
            N.right = rotateRight(N.right);
            return rotateLeft(N);
        }


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