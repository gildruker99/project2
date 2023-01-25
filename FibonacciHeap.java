
/**
 * FibonacciHeap
 * <p>
 * An implementation of a Fibonacci Heap over integers.
 * 
 * Students : Omer Sheffi, id : 205923154
 *            Gil Drucker id :  211990940
 */
public class FibonacciHeap {
    /**
     * Number of totalCuts so far in the heap
     */
    public static int totalCuts;
    /**
     * Number of totalLinks so far in the heap
     */
    public static int totalLinks;
    /**
     * Pointer to the minimum node in the heap
     */
    public HeapNode minNode;
    /**
     * number of Trees in the Heap
     */
    public int numOfTrees;
    /**
     * Number of nodes in the heap
     */
    public int numOfNodes;
    public int Marked;
    /**
     * Pointer to the first node in the heap
     */
    private HeapNode firstNode;

    /**
     * public static int totalLinks()
     * <p>
     * This static function returns the total number of link operations made during the
     * run-time of the program. A link operation is the operation which gets as input two
     * trees of the same rank, and generates a tree of rank bigger by one, by hanging the
     * tree which has larger value in its root under the other tree.
     */
    public static int totalLinks() {
        return totalLinks;
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

    public HeapNode getFirst(){
        return this.firstNode;
    }

    public int getNumOfNodes() {
        return numOfNodes;
    }

    public HeapNode getMinNode() {
        return minNode;
    }

    public int getNumOfTrees() {
        return numOfTrees;
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
        if (k == 0 || H.isEmpty()) {
            return new int[0];
        }

        FibonacciHeap heapForKMin = new FibonacciHeap();
        int[] kMinNodes = new int[k];
        kMinNodes[0] = H.minNode.key;
        if (k == 1) {
            return kMinNodes;
        }

        HeapNode currMin = H.minNode; // hold the node in H with the same key as the min in heapForKMin.
        HeapNode minChild = currMin.child; // hold one of the children of tempMin.
        heapForKMin.KminHelper(minChild.key, minChild);
        minChild = minChild.right;
        while (minChild != H.minNode.child) {
            heapForKMin.KminHelper(minChild.key, minChild);
            minChild = minChild.right;
        }
        int c = 1;
        kMinNodes = heapForKMin.makeKArr(c,k);
        kMinNodes[0] = H.minNode.key;
        return kMinNodes;
    }

    public int[] makeKArr(int i,int k){
        int[]toRet = new int[k];
        while (i < k) {
            HeapNode curr = this.minNode;
            toRet[i] = curr.key;
            this.deleteMin();
            if(curr.kmin.child!=null){
                HeapNode toInsert  = curr.kmin.child;
                int toInsertKey = toInsert.key;
                this.KminHelper(toInsertKey,toInsert);
                toInsert = toInsert.right;
                while (toInsert.key!=toInsertKey){
                    this.KminHelper(toInsert.key,toInsert);
                    toInsert = toInsert.right;
                }
            }
        i+=1;
        }
        return toRet;
    }


    public void KminHelper(int key,HeapNode toCheck){
        HeapNode toInsert  = new HeapNode(key);
        toInsert.kmin = toCheck;
        if(this.isEmpty()){
            this.firstNode = toInsert;
            toInsert.left = toInsert;
            toInsert.right = toInsert;
            this.minNode = toInsert;
        }
        else{
            toInsert.right = this.firstNode;
            toInsert.left = this.firstNode.left;
            this.firstNode.left.right = toInsert;
            this.firstNode.left = toInsert;
            this.firstNode = toInsert;
            if(this.minNode.key > toInsert.key) {
                this.minNode = toInsert;
            }
        }
        this.numOfNodes+=1;
        this.numOfTrees+=1;
    }
    /**
     * public boolean isEmpty()
     * <p>
     * Returns true if and only if the heap is empty.
     * <p>
     * O(1) complexity
     */
    public boolean isEmpty() {
        return this.numOfNodes == 0;
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
            if (newNode.key < this.minNode.key) {
                this.minNode = newNode;
            }
        } else {
            this.minNode = newNode;
            this.firstNode = newNode;
            newNode.right = newNode;
            newNode.left = newNode;
        }
        this.numOfNodes++;
        this.numOfTrees += 1;
        return newNode;
    }

    public void setEmpty() {
        this.numOfNodes = 0;
        this.minNode = null;
        this.firstNode = null;
        this.numOfTrees = 0;
    }

    /**
     * public void deleteMin()
     * <p>
     * Deletes the node containing the minimum key.
     */
    public void deleteMin() {
        if (isEmpty()) {
            return;
        }
        if (this.numOfNodes == 1) {
            this.setEmpty();
            return;
        }
        if (this.numOfTrees == 1) {
            this.deleteOneMin();
            return;
        }
        if (this.minNode.child == null) {
            this.delete1();
            return;
        }
        this.delete2();
    }

    /**
     * few helpers
     */

    public void helper(HeapNode x) {
        if (x.mark) {
            x.mark = false;
            this.Marked -= 1;
        }
    }

    public void deleteOneMin() {
        HeapNode toDel = this.minNode;
        this.firstNode = toDel.child;
        HeapNode x = toDel.child;
        this.helper(x);
        x = x.right;
        while (x != toDel.child) {
            this.helper(x);
            x.parent = null;
            x = x.right;
        }
        toDel.child.parent = null;
        toDel.child = null;
        this.numOfNodes -= 1;
        this.numOfTrees = toDel.degree;
        this.findMinHelp();
        this.consolidate();
    }

    public void delete1() {
        HeapNode min = this.minNode;
        min.right.left = min.left;
        min.left.right = min.right;
        if (this.firstNode == min) {
            this.firstNode = min.next();
        }
        this.numOfNodes -= 1;
        this.numOfTrees -= 1;
        min.left = min;
        min.right = min;
        this.findMinHelp();
        this.consolidate();
    }

    public void delete2() {
        HeapNode toDel = this.minNode;
        HeapNode nextNode = toDel.right;
        HeapNode pervNode = toDel.left;
        toDel.child.left.right = nextNode;
        toDel.child.left = pervNode;
        nextNode.left = toDel.child.left;
        pervNode.right = toDel.child;
        this.numOfNodes -= 1;
        this.numOfTrees += toDel.degree - 1;
        if (this.firstNode == toDel) {
            this.firstNode = toDel.child;
        }
        HeapNode x = toDel.child;
        this.helper(x);
        x = x.right;
        while (x != toDel.child) {
            this.helper(x);
            x.parent = null;
            x = x.right;
        }
        toDel.child.parent = null;
        toDel.child = null;
        this.findMinHelp();
        this.consolidate();
    }

    public HeapNode[] getRoots() {
        HeapNode[] toRet = new HeapNode[this.numOfTrees];
        HeapNode x = this.firstNode;
        toRet[0] = x;
        x = x.right;
        int i = 1;
        while (x != this.firstNode) {
            toRet[i] = x;
            x = x.right;
            i++;
        }
        return toRet;
    }

    /**
     * Consolidates the trees in the heap by joining trees of equal
     * degree until there are no more trees of equal degree in the
     * root list.
     */
    private void consolidate() {
        HeapNode[] roots = getRoots();
        int size = (int) Math.floor(Math.log(this.numOfNodes) / Math.log(2));
        HeapNode[] x = new HeapNode[2 * (size + 1)];
        for (HeapNode heapNode : roots) {
            if (x[heapNode.degree] == null) {
                heapNode.right = heapNode;
                heapNode.left = heapNode;
                x[heapNode.degree] = heapNode;
            } else {
                heapNode.left = heapNode;
                heapNode.right = heapNode;
                while (x[heapNode.degree] != null) {
                    heapNode = this.link(heapNode, x[heapNode.degree]);
                    totalLinks += 1;
                    x[heapNode.degree - 1] = null;
                }
                x[heapNode.degree] = heapNode;
            }
        }
        this.HeapHelper(x);
    }

    public void HeapHelper(HeapNode[] x) {
        HeapNode l = null;
        int treesCounter = 0;
        HeapNode minNode = null;
        HeapNode firstNode = null;
        for (HeapNode heapNode : x) {
            if (heapNode != null) {
                if (treesCounter == 0) {
                    l = heapNode;
                    firstNode = heapNode;
                    minNode = heapNode;
                    treesCounter += 1;
                } else {
                    l.right = heapNode;
                    heapNode.left = l;
                    heapNode.right = firstNode;
                    if (heapNode.key < minNode.key) {
                        minNode = heapNode;
                    }
                    firstNode.left = heapNode;
                    treesCounter += 1;
                    l = heapNode;
                }
            }
        }
        this.firstNode = firstNode;
        this.minNode = minNode;
        this.numOfTrees = treesCounter;
    }

    /**
     * public HeapNode findMin()
     * <p>
     * Returns the node of the heap whose key is minimal, or null if the heap is empty.
     * <p>
     * O(1) complexity
     */
    public HeapNode findMin() {
        return this.minNode;
    }

    public void findMinHelp() {
        this.minNode = this.firstNode;
        HeapNode start = this.firstNode;
        start = start.right;
        while (start != this.firstNode) {
            if (start.key < this.minNode.key) {
                this.minNode = start;
            }
            start = start.right;
        }
    }

    /**
     * public void meld (FibonacciHeap heap2)
     * <p>
     * Melds heap2 with the current heap.
     */
    public void addSecVals(FibonacciHeap sec) {
        this.numOfNodes += sec.numOfNodes;
        this.Marked += sec.Marked;
        this.numOfTrees += sec.numOfTrees;
    }

    public void meld(FibonacciHeap heap2) {
        if (heap2.isEmpty()) {
            return;
        }
        if (this.isEmpty()) {
            this.firstNode = heap2.firstNode;
            this.minNode = heap2.minNode;
        } else {
            HeapNode secF = heap2.firstNode;
            HeapNode secL = heap2.firstNode.left;
            heap2.firstNode.left.right = this.firstNode;
            heap2.firstNode.left = this.firstNode.left;
            this.firstNode.left.right = secF;
            this.firstNode.left = secL;
            if (heap2.minNode.key < this.minNode.key) {
                this.minNode = heap2.minNode;
            }

        }
        this.addSecVals(heap2);
    }

    /**
     * public int size()
     * <p>
     * Returns the number of elements in the heap.
     * <p>
     * O(1) complexity
     */
    public int size() {
        return this.numOfNodes;
    }

    /**
     * public int[] countersRep()
     * <p>
     * Return an array of counters. The i-th entry contains the number of trees of order i in the heap.
     * (Note: The size of of the array depends on the maximum order of a tree.)
     */
    public int biggestDegree(HeapNode y) {
        int biggest = y.degree;
        y = y.right;
        while(y!=this.firstNode){
            if(y.degree>biggest){
                biggest = y.degree;
            }
            y=y.right;
        }
        return biggest;
    }

    public int[] countersRep() {
        if (isEmpty()){
            return new int[0];
        }
        if (this.numOfNodes == 1) {
            int[] x = new int[1];
            x[0] = 1;
            return x;
        }
        HeapNode y = this.firstNode;
        int biggest = this.biggestDegree(y);
        int[] x = new int[biggest+1];
        HeapNode temp = this.firstNode;
        x[temp.degree] = x[temp.degree]+1;
        temp = temp.right;
        while (temp!=this.firstNode){
            int tempDeg = temp.degree;
            x[tempDeg]= x[tempDeg]+1;
            temp = temp.right;
        }
        return x;
    }

    /**
     * public void delete(HeapNode x)
     * <p>
     * Deletes the node x from the heap.
     * It is assumed that x indeed belongs to the heap.
     */
    public void delete(HeapNode x) {
        //make x Node the smallest
        decreaseKey(x, x.key - this.minNode.key + 10);
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
        if (y != null) {
            if (x.key <= y.key) {
                this.cascadingCut(x, y);
            }
        }
        if (x.key < this.minNode.key) {
            this.minNode = x;
        }
    }

    /**
     * public int nonMarked()
     * <p>
     * This function returns the current number of non-marked items in the heap
     * O(1) complexity
     */
    public int nonMarked() {
        return this.numOfTrees - this.Marked;
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
        return numOfTrees + 2 * (this.Marked); // should be replaced by student code
    }

    /**
     * x is the child to be removed from the tree
     * the reverse of link operation
     * O(1) time complexity
     *
     * @param x child to be removed
     */
    public void cut(HeapNode x, HeapNode y) {
        x.parent = null;
        if (x.mark) {
            this.Marked -= 1;
        }
        x.mark = false;
        y.degree -= 1;
        totalCuts += 1;
        numOfTrees += 1;
        if (y.child == x) {
            y.child = x.right;
        }
        if (x.right == x) {
            y.child = null;
        } else {
            x.left.right = x.right;
            x.right.left = x.left;
        }
        x.left = this.firstNode.left;
        this.firstNode.left.right = x;
        this.firstNode.left = x;
        x.right = this.firstNode;
        this.firstNode = x;
    }


    /**
     * performs a cascading cut operation
     * cut "this" from its parent
     * and then do the same until the parent is Unmarked
     * O(log(n)) time complexity
     */
    public void cascadingCut(HeapNode x, HeapNode y) {
        cut(x, y);
        if (y.parent != null) {
            if (!y.mark) {
                y.mark = true;
                this.Marked += 1;
            } else {
                cascadingCut(y, y.parent);
            }
        }
    }

    public HeapNode linkhelper(HeapNode parent, HeapNode child) {
        parent.right = child.child;
        child.child.left.right = parent;
        parent.left = child.child.left;
        child.child.left = parent;
        child.degree += 1;
        child.child = parent;
        parent.parent = child;
        if (child.mark) {
            child.mark = false;
        }
        return child;
    }

    public HeapNode link(HeapNode parent, HeapNode child) {
        numOfTrees -= 1;
        if (parent.degree == 0) {
            if (parent.key > child.key) {
                child.child = parent;
                parent.parent = child;
                child.degree += 1;
                return child;
            } else {
                parent.child = child;
                child.parent = parent;
                parent.degree += 1;
                return parent;
            }
        }
        if (parent.key > child.key) {
            return linkhelper(parent, child);
        } else {
            return linkhelper(child, parent);
        }


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
         * Number of children of this node
         */
        public int degree;
        public HeapNode kmin;
        /**
         * Node parent
         */
        protected HeapNode parent;
        /**
         * Node First child
         */
        protected HeapNode child;
        /**
         * Node Right Sibling Node
         */
        protected HeapNode right;
        /**
         * Node Left Sibling Node
         */
        protected HeapNode left;
        /**
         * True if this node has had a child removed since the
         * node was added to its parent
         * default == false
         */
        protected boolean mark;


        public HeapNode(int key) {
            this.key = key;
            right = this;
            left = this;
            parent = null;
            degree = 0;
            mark = false;
            this.kmin = null;
        }

        public int getKey() {
            return this.key;
        }

        public HeapNode next() {
            return (this.right);
        }

        public int getDegree() {
            return (this.degree);
        }

        public boolean isMark() {
            return (this.mark);
        }

        public HeapNode prev() {
            return (this.left);
        }

        public HeapNode getParent() {
            return (this.parent);
        }

        public HeapNode getChild() {
            return child;
        }


    }
}
