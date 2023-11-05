class Node {
    int height;
    SaleRecord key;
    Node left, right;

    Node(SaleRecord d) {
        key = d;
        height = 1;
    }
}
