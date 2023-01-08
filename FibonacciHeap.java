/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
    /**Pointer to the minimum node in the heap*/
    private HeapNode minNode;

    /**Pointer to the first node in the heap*/
    public static HeapNode firstNode;
    /**Number of nodes in the heap*/
    public static int numOfNodes;
    /**Number of unmarked nodes in the heap*/

    public static int nonMarked;

    /**Number of totalCuts so far in the heap*/
    public static int totalCuts;


    /**
     * public boolean isEmpty()
     *
     * Returns true if and only if the heap is empty.
     *
     * O(1) complexity
     */
    public boolean isEmpty()
    {
        return minNode != null;
    }

    /**
     * public HeapNode insert(int key)
     *
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     *
     * Returns the newly created node.
     *
     * O(1) complexity
     */
    public HeapNode insert(int key) {
        HeapNode newNode = new HeapNode(key);
        if (isEmpty()){
            newNode.right = firstNode;
            newNode.left = firstNode.left;
            firstNode.left.right = newNode;
            firstNode.left = newNode;
            firstNode = newNode;
            if (newNode.key < minNode.key){
                this.minNode = newNode;
            }
        }
        else {
            this.minNode = newNode;
            firstNode = newNode;
        }
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
     * O(1) complexity
     */
    public HeapNode findMin() {
        if (isEmpty()){
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
     * O(1) complexity
     */
    public int size()
    {
        return numOfNodes;
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
        decreaseKey(x,x.key+Integer.MIN_VALUE);
        //delete those node
        deleteMin();
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     *
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        x.key -= delta;
        HeapNode y = x.parent;
        //check if x isn't root & the Heap invariant has damaged.
        if (y != null && x.key < y.key){
            y.cut(x);
            y.cascadingCut();
        }
        if (x.key < minNode.key){
            minNode = x;
        }
    }


    /**
     * public int nonMarked()
     *
     * This function returns the current number of non-marked items in the heap
     * O(1) complexity
     */
    public int nonMarked()
    {
        return nonMarked;
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
     * O(1) complexity
     */
    public static int totalCuts()
    {
        return totalCuts;
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
            parent = null;
            degree = 0;
            mark = false;
            nonMarked++;
            numOfNodes++;

        }

        public int getKey() {
            return this.key;
        }

        /**
         * x is the child to be removed from the tree
         * the reverse of link operation
         * O(1) time complexity
         * @param x child to be removed
         */
        public void cut(HeapNode x){
            //remove x from child list of y and decrement y degree.
            x.left.right = x.right;
            x.right.left = x.left;
            this.degree--;
            //if x is the only child of y
            if (x.right == x){
                this.child = null;
            } else if (this.child == x) {
                this.child = x.right;
            }
            //add x to the Heap
            x.parent = null;
            x.mark = false;
            x.right = firstNode;
            x.left = firstNode.left;
            x.left.right = x;
            firstNode.left = x;
            firstNode = x;
            //updating static fields
            nonMarked++;
            totalCuts++;
        }

        /**
         * performs a cascading cut operation
         * cut "this" from its parent
         * and then do the same until the parent is Unmarked
         * O(log(n)) time complexity
         */
        public void cascadingCut() {
            HeapNode z = this.parent;
            //check if there is a parent
            if (z != null){
                //check if y is marked
                if (this.mark){
                    //if marked, cut it
                    z.cut(this);
                    z.cascadingCut();
                }
                else{
                    //if y is unmarked, set to mark
                    this.mark = true;
                    //updating static fields
                    nonMarked--;
                }
            }
        }
        /**
         * Make "this" node a child of the given parent node. All linkages
         * are updated, the degree of the parent is incremented, and
         * mark is set to false.
         *
         * @param  parent  the new parent node.
         */
        public void linkhelper(HeapNode parent){
            //remove this from his sibling
            this.left.right = right;
            this.right.left = left;
            //make this a child of x
            this.parent = parent;
            // this will be the only child
            if (parent.child == null){
                parent.child = this;
                this.right = this;
                this.left = this;
            }
            else{
                this.right = parent.child;
                this.left = parent.child.left;
                parent.child.left.right = this;
                parent.child.left = this;
                parent.child = this;
            }
            parent.degree++;
            if (this.mark){
                this.mark =false;
                nonMarked++;
            }
        }

        public void link(HeapNode parent){
            if (this.key < parent.key){
                parent.linkhelper(this);
            }
            else {
                this.linkhelper(parent);
            }
        }
    }
}
