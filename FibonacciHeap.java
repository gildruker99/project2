/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
    /**Pointer to the minimum node in the heap*/
    private HeapNode minNode;

    /**Pointer to the first node in the heap*/
    private HeapNode firstNode;
    /**Number of nodes in the heap*/
    private int NumOfNodes;
    public int nonMarked;


    /**
     * public boolean isEmpty()
     *
     * Returns true if and only if the heap is empty.
     *
     */
    public boolean isEmpty()
    {
        return minNode == null;
    }

    /**
     * public HeapNode insert(int key)
     *
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     *
     * Returns the newly created node.
     */
    public HeapNode insert(int key) {
        HeapNode newNode = new HeapNode(key);
        if (!isEmpty()){
            newNode.right = this.firstNode;
            newNode.left = this.firstNode.left;
            this.firstNode.left.right = newNode;
            this.firstNode.left = newNode;
            this.firstNode = newNode;
            if (newNode.key < minNode.key){
                this.minNode = newNode;
            }
        }
        else {
            this.minNode = newNode;
        }
        this.NumOfNodes++;
        this.nonMarked++;
        return newNode;
    }

    /**
     * public void deleteMin()
     *
     * Deletes the node containing the minimum key.
     *
     */
    public void deleteMin()
    {
        return; // should be replaced by student code

    }

    /**
     * public HeapNode findMin()
     *
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     *
     */
    public HeapNode findMin() {
        if (!isEmpty()){
            return this.minNode;
        }
        return null;
    }

    /**
     * public void meld (FibonacciHeap heap2)
     *
     * Melds heap2 with the current heap.
     *
     */
    public void meld (FibonacciHeap heap2)
    {
        return; // should be replaced by student code
    }

    /**
     * public int size()
     *
     * Returns the number of elements in the heap.
     *
     */
    public int size()
    {
        return this.NumOfNodes;
    }

    /**
     * public int[] countersRep()
     *
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * (Note: The size of of the array depends on the maximum order of a tree.)
     *
     */
    public int[] countersRep()
    {
        int[] arr = new int[100];
        return arr; //	 to be replaced by student code
    }

    /**
     * public void delete(HeapNode x)
     *
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     *
     */
    public void delete(HeapNode x) {
        //make x Node the smallest
        decreaseKey(x,x.key-Integer.MIN_VALUE); /**NOT SURE if this correct */
        //delete those node
        deleteMin();
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     *
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta)
    {
        return; // should be replaced by student code
    }

    /**
     * public int nonMarked()
     *
     * This function returns the current number of non-marked items in the heap
     */
    public int nonMarked()
    {
        return -232; // should be replaced by student code
    }

    /**
     * public int potential()
     *
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     *
     * In words: The potential equals to the number of trees in the heap
     * plus twice the number of marked nodes in the heap.
     */
    public int potential()
    {
        return -234; // should be replaced by student code
    }

    /**
     * public static int totalLinks()
     *
     * This static function returns the total number of link operations made during the
     * run-time of the program. A link operation is the operation which gets as input two
     * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
     * tree which has larger value in its root under the other tree.
     */
    public static int totalLinks()
    {
        return -345; // should be replaced by student code
    }

    /**
     * public static int totalCuts()
     *
     * This static function returns the total number of cut operations made during the
     * run-time of the program. A cut operation is the operation which disconnects a subtree
     * from its parent (during decreaseKey/delete methods).
     */
    public static int totalCuts()
    {
        return -456; // should be replaced by student code
    }

    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     *
     * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     *
     * ###CRITICAL### : you are NOT allowed to change H.
     */
    public static int[] kMin(FibonacciHeap H, int k)
    {
        int[] arr = new int[100];
        return arr; // should be replaced by student code
    }

    /**
     * public class HeapNode
     *
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in another file.
     *
     */
    public static class HeapNode{

        public int key;
        /**Node parent */
        private HeapNode parent;
        /**Node First child*/
        private HeapNode child;
        /**Node Right Sibling Node*/
        private HeapNode right;
        /**Node Left Sibling Node*/
        private HeapNode left;
        /**Number of children of this node*/
        private int degree;
        /**True if this node has had a child removed since the
         * node was added to its parent
         * default == false*/
        private boolean mark;
        public HeapNode(int key) {
            this.key = key;
            right = this;
            left = this;
        }

        public int getKey() {
            return this.key;
        }
    }
}
