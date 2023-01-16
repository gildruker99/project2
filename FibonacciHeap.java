

/**
 * FibonacciHeap
 *
 * An implementation of a Fibonacci Heap over integers.
 */
public class FibonacciHeap {
    /**
     * Pointer to the minimum node in the heap
     */
    private HeapNode minNode;

    /**
     * number of Trees in the Heap
     */
    private int numOfTrees;

    /**
     * Pointer to the first node in the heap
     */
    private HeapNode firstNode;
    /**
     * Number of nodes in the heap
     */
    private int numOfNodes;
    public int size = numOfNodes;
    /**
     * Number of unmarked nodes in the heap
     */

    public static int nonMarked;

    /**
     * Number of totalCuts so far in the heap
     */
    public static int totalCuts;

    /**
     * Number of totalLinks so far in the heap
     */
    public static int totalLinks;

    /**
     * public boolean isEmpty()
     * <p>
     * Returns true if and only if the heap is empty.
     * <p>
     * O(1) complexity
     */
    public boolean isEmpty() {
        return minNode == null;
    }

    public HeapNode getFirst(){
        return this.firstNode;
    }

    public int getNumOfNodes(){
        return (this.numOfNodes);
    }

    public int getNumOfTrees(){
        return(this.numOfTrees);
    }

    public HeapNode getMinNode(){
        return(this.minNode);
    }


    /**
     * public HeapNode insert(int key)
     * <p>
     * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
     * The added key is assumed not to already belong to the heap.
     * <p>
     * Returns the newly created node.
     * <p>
     * O(1) complexity
     */
    public HeapNode insert(int key) {
        HeapNode newNode = new HeapNode(key);
        if (!isEmpty()) {
            newNode.right = firstNode;
            newNode.left = firstNode.left;
            firstNode.left.right = newNode;
            firstNode.left = newNode;
            firstNode = newNode;
            if (newNode.key < minNode.key) {
                this.minNode = newNode;
            }
        } else {
            this.minNode = newNode;
            firstNode = newNode;
        }
        numOfNodes++;
        numOfTrees+=1;
        return newNode;
    }

    /**
     * public void deleteMin()
     * <p>
     * Deletes the node containing the minimum key.
     */
    public void deleteMin() {
        HeapNode z = minNode;
        if (z == null) {
            return;
        }
        if (z.child != null) {
            z.child.parent = null;
            //for each child of z set parent to null
            for (HeapNode x = z.child.right; x != z.child; x = x.right) {
                x.parent = null;
            }
            /**change the pointer by the instructions
             * if y1,y2,y3 are trees, and y2 is the min
             * and y2 children are x1,..,xk, well get y1,x1,..xk,y3
             */
            if(z.right == z && z.left == z && z == firstNode){
                firstNode = z.child;
            }
            HeapNode minLeft = z.left;
            HeapNode zChildLeft = z.child.left;
            z.left = zChildLeft;
            zChildLeft.right = z;
            z.child.left = minLeft;
            minLeft.right = z.child;
        }
        //remove the min node from heap
        z.left.right = z.right;
        z.right.left = z.left;

        if (z == firstNode){
            firstNode = z.right;
        }
        if (z == z.right) {
            minNode = null;
        }
        //find the new Min
        else {
            consolidate();
            HeapNode potentialMin = firstNode;
            int numOfTreesCnt = 1;
            for (HeapNode y = firstNode.right; y != firstNode; y = y.right) {
                numOfTreesCnt++;
                if (y.key < potentialMin.key) {
                    potentialMin = y;
                }
            }
            minNode = potentialMin;
            numOfTrees = numOfTreesCnt;
        }
        numOfNodes--;
        nonMarked--;
    }

    /**
     * Consolidates the trees in the heap by joining trees of equal
     * degree until there are no more trees of equal degree in the
     * root list.
     */
    private void consolidate() {
        // log bast phi of Integer.MAX_VALUE -> the most nodes we'll have
        HeapNode next;
        int maxDegree = (int) Math.floor((Math.log(size())) / Math.log((1 + Math.sqrt(5)) / 2));
        HeapNode[] degTreesArr = new HeapNode[maxDegree];
        HeapNode start = firstNode;
        HeapNode current = firstNode;
        do {
            HeapNode x = current;
            HeapNode nextCurr = current.right;
            int deg = x.degree;
            while (degTreesArr[deg] != null){
                HeapNode y = degTreesArr[deg];
                if ( y == start ){
                    start = start.right;
                }
                if (y == firstNode){
                    start = y.right;
                }
                if ( y == nextCurr){
                    nextCurr = nextCurr.right;
                }
                if (x.key > y.key){
                    HeapNode tmp = y;
                    y = x;
                    x = tmp;
                }
                y.link(x,this);
                degTreesArr[deg] = null;
                deg++;
            }
            degTreesArr[deg] = x;
            current = nextCurr;
        } while (current !=start && current !=firstNode);
        minNode = firstNode;
        for(HeapNode node : degTreesArr){
            if (node != null && node.key < minNode.key){
                minNode = node;
            }
        }
    }



    /**
     * public HeapNode findMin()
     * <p>
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     * <p>
     * O(1) complexity
     */
    public HeapNode findMin() {
        if (!isEmpty()) {
            return this.minNode;
        }
        return null;
    }



    /**
     * public void meld (FibonacciHeap heap2)
     * <p>
     * Melds heap2 with the current heap.
     */
    public void meld(FibonacciHeap heap2) {
        HeapNode thisLastNode = this.firstNode.left;
        HeapNode heap2LastNode = heap2.firstNode.left;
        thisLastNode.right = heap2.firstNode;
        heap2.firstNode.left = thisLastNode;
        heap2LastNode.right = this.firstNode;
        this.firstNode.left = heap2LastNode;
        if(this.minNode.key>heap2.minNode.key){
            this.minNode = heap2.minNode;
        }
        this.numOfNodes += heap2.numOfNodes;
        this.numOfTrees += heap2.numOfTrees;
    }

    /**
     * public int size()
     * <p>
     * Returns the number of elements in the heap.
     * <p>
     * O(1) complexity
     */
    public int size() {
        return numOfNodes;
    }

    /**
     * public int[] countersRep()
     * <p>
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * (Note: The size of of the array depends on the maximum order of a tree.)
     */
    public int[] countersRep() {
        int[] arr = new int[minNode.degree+1];
        arr[minNode.degree] += 1;
        for (HeapNode y = minNode.right; y != minNode; y = y.right){
            arr[y.degree] += 1;
        }
        return arr; //	 to be replaced by student code
    }
    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     */
    public void delete(HeapNode x) {
        //make x Node the smallest
        decreaseKey(x, x.key + Integer.MIN_VALUE);
        //delete those node
        deleteMin();
    }

    /**
     * public void decreaseKey(HeapNode x, int delta)
     * <p>
     * Decreases the key of the node x by a non-negative value delta. The structure of the heap should be updated
     * to reflect this change (for example, the cascading cuts procedure should be applied if needed).
     */
    public void decreaseKey(HeapNode x, int delta) {
        x.key -= delta;
        HeapNode y = x.parent;
        //check if x isn't root & the Heap invariant has damaged.
        if (y != null && x.key < y.key) {
            y.cut(x,this);
            y.cascadingCut(this);
        }
        if (x.key < minNode.key) {
            minNode = x;
        }
    }


    /**
     * public int nonMarked()
     * <p>
     * This function returns the current number of non-marked items in the heap
     * O(1) complexity
     */
    public int nonMarked() {
        return nonMarked;
    }

    /**
     * public int potential()
     * <p>
     * This function returns the current potential of the heap, which is:
     * Potential = #trees + 2*#marked
     * <p>
     * In words: The potential equals to the number of trees in the heap
     * plus twice the number of marked nodes in the heap.
     */
    public int potential() {
        return numOfTrees + 2*(numOfNodes-nonMarked); // should be replaced by student code
    }

    /**
     * public static int totalLinks()
     * <p>
     * This static function returns the total number of link operations made during the
     * run-time of the program. A link operation is the operation which gets as input two
     * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
     * tree which has larger value in its root under the other tree.
     */
    public static int totalLinks() {
        return totalLinks; // should be replaced by student code
    }

    /**
     * public static int totalCuts()
     * <p>
     * This static function returns the total number of cut operations made during the
     * run-time of the program. A cut operation is the operation which disconnects a subtree
     * from its parent (during decreaseKey/delete methods).
     * O(1) complexity
     */
    public static int totalCuts() {
        return totalCuts;
    }


    /**
     * public static int[] kMin(FibonacciHeap H, int k)
     * <p>
     * This static function returns the k smallest elements in a Fibonacci heap that contains a single tree.
     * The function should run in O(k*deg(H)). (deg(H) is the degree of the only tree in H.)
     * <p>
     * ###CRITICAL### : you are NOT allowed to change H.
     */
    public static int[] kMin(FibonacciHeap H, int k) {
        int[] arr = new int[100];
        return arr; // should be replaced by student code
    }

    /**
     * public class HeapNode
     * <p>
     * If you wish to implement classes other than FibonacciHeap
     * (for example HeapNode), do it in this file, not in another file.
     */
    public static class HeapNode {

        public int key;
        /**
         * Node parent
         */
        private HeapNode parent;
        /**
         * Node First child
         */
        private HeapNode child;
        /**
         * Node Right Sibling Node
         */
        private HeapNode right;
        /**
         * Node Left Sibling Node
         */
        private HeapNode left;
        /**
         * Number of children of this node
         */
        public int degree;
        /**
         * True if this node has had a child removed since the
         * node was added to its parent
         * default == false
         */
        private boolean mark;

        public HeapNode(int key) {
            this.key = key;
            right = this;
            left = this;
            parent = null;
            degree = 0;
            mark = false;
            nonMarked++;
        }

        public int getKey() {
            return this.key;
        }

        public HeapNode next(){
            return(this.right);
        }

        public int getDegree(){
            return(this.degree);
        }
        public boolean isMark(){
            return(this.mark);
        }

        public HeapNode prev(){
            return(this.left);
        }

        public HeapNode getParent(){
            return(this.parent);
        }

        public HeapNode getChild() {
            return child;
        }

        /**
         * x is the child to be removed from the tree
         * the reverse of link operation
         * O(1) time complexity
         *
         * @param x child to be removed
         */
        public void cut(HeapNode x, FibonacciHeap h) {
            //remove x from child list of y and decrement y degree.
            x.left.right = x.right;
            x.right.left = x.left;
            this.degree--;
            //if x is the only child of y
            if (x.right == x) {
                this.child = null;
            } else if (this.child == x) {
                this.child = x.right;
            }
            //add x to the Heap
            x.parent = null;
            x.mark = false;
            x.right = h.firstNode;
            x.left = h.firstNode.left;
            x.left.right = x;
            h.firstNode.left = x;
            h.firstNode = x;
            //updating static fields
            nonMarked++;
            totalCuts++;
        }
        public void cut1(HeapNode x, FibonacciHeap h){
            cascadingCut2(x,h);
        }

        public void cut2(HeapNode x, FibonacciHeap h){
            x.parent =  null;
            x.mark = false;
            this.degree--;
            if(x.next()==x){
                this.child = null;
            }
            else{
                if(this.child == x){
                    this.child = x.next();
                }
                x.left.right = x.next();
                x.right.left = x.prev();
                x.left = h.firstNode.left;
                x.right = h.firstNode;
                h.firstNode.left = x;

            }

        }


        /**
         * performs a cascading cut operation
         * cut "this" from its parent
         * and then do the same until the parent is Unmarked
         * O(log(n)) time complexity
         */
        public void cascadingCut(FibonacciHeap h) {
            HeapNode z = this.parent;
            //check if there is a parent
            if (z != null) {
                //check if y is marked
                if (this.mark) {
                    //if marked, cut it
                    z.cut(this,h);
                    z.cascadingCut(h);
                } else {
                    //if y is unmarked, set to mark
                    this.mark = true;
                    //updating static fields
                    nonMarked--;
                }
            }
        }


        public void cascadingCut2(HeapNode x,FibonacciHeap h){
            this.cut(x,h);
            if(this.parent!=null){
                if(!this.mark){
                    this.mark = true;
                }
                else{
                    this.parent.cascadingCut2(this,h);
                }
            }
        }

        /**
         * Make "this" node a child of the given parent node. All linkages
         * are updated, the degree of the parent is incremented, and
         * mark is set to false.
         *
         * @param parent the new parent node.
         */
        public HeapNode linkhelper(HeapNode parent, FibonacciHeap h) {
            //remove this from his sibling
            this.left.right = right;
            this.right.left = left;
            //make this a child of x
            this.parent = parent;
            // this will be the only child
            if (parent.child == null) {
                parent.child = this;
                this.right = this;
                this.left = this;
            } else {
                this.right = parent.child;
                this.left = parent.child.left;
                parent.child.left.right = this;
                parent.child.left = this;
                parent.child = this;
            }
            parent.degree++;
            if (this.mark) {
                this.mark = false;
                nonMarked++;
            }
            if(parent.child == h.firstNode){
                h.firstNode = parent;
            }
            totalLinks++;
            return parent;
        }

        public HeapNode link(HeapNode parent, FibonacciHeap h) {
            if (this.key < parent.key) {
                return parent.linkhelper(this,h);
            } else {
                return this.linkhelper(parent,h);
            }
        }

    }
}
