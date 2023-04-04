
class DLList<T> {
    // =======================================
    // (internal) DLListNode Class
    // =======================================
    private class Node<T> {
        // data members
        public T data;
        public Node<T> previous;
        public Node<T> next;

        // overloaded constructor
        public Node(T value) {
            data = value;
            previous = null;
            next = null;
        }
        // member functions (Methods)
        /*
         * public T getData(){
         * return data;
         * }
         * 
         * public Node<T> getPrevious(){
         * return previous;
         * }
         * 
         * public Node<T> getNext(){
         * return next;
         * }
         * 
         * public void setPrevious(Node<T> prev){
         * previous = prev;
         * }
         * 
         * public void setNext(Node<T> n){
         * next = n;
         * }
         */
    }

    // =======================================
    // data members
    private Node<T> head;
    private Node<T> tail;
    public Node<T> current;
    int size = 0;
    int index = -1;

    // =======================================
    // member functions (Methods)
    // =======================================
    // deafalt constructor
    public DLList() {
        clear();
    }

    // copy constructor (deep copy -> two separete lists with the exact same info in
    // the,, simply seperate)
    public DLList(DLList<T> other) {
        this.head = other.head;
        this.tail = other.tail;
        this.current = other.current;
        this.size = other.size;
        this.index = other.index;
    }

    // clear list method, its purpose is to set front to null, back to null, current
    // to null, size to 0, and index to -1
    public void clear() {
        head = null;
        tail = null;
        current = null;
        size = 0;
        index = -1;
    }

    // get size method
    public int getSize() {
        return size;
    }

    // get index method

    public int getIndex() {
        return index;
    }

    // is empty method

    public boolean isEmpty() {
        return (getSize() == 0);

    }

    // is at first node method, is current reference poiting at first node?

    public boolean atFirst() {
        return getIndex() == 0;
    }

    // is at last node method

    public boolean atLast() {
        return (getIndex() == getSize() - 1);
    }

    // get data at current method return data or reference to data

    public T getData() {

        if (!isEmpty()) {
            return current.data;
        } else {
            return null;
        }
    }

    // set data at current method, take something of type T and return a reference
    // if successful

    public T setData(T x) {

        if (!isEmpty()) {

            current.data = x;

            return x;

        }

        else {

            return null;

        }

    }

    // seek to first node Method

    public boolean first() {

        return seek(0);

    }

    // seek to the next node Method, if fail return false

    public boolean next() {

        return seek(getIndex() + 1);

    }

    // seek to previous node Method

    public boolean previous() {

        return seek(getIndex() - 1);

    }

    // go to last node

    public boolean last() {

        return seek(getIndex());

    }

    // seek method

    public boolean seek(int loc) {
        // local var
        boolean retval = false;
        // test is the list empty?
        if (isEmpty()) {
            retval = false;
        }
        // is loc in range?
        else if (loc < 0 || loc >= getSize()) {
            retval = false;
        }
        // is loc == 0?
        else if (loc == 0) {
            current = head;
            index = 0;
            retval = true;
        }
        // is loc == last index?
        else if (loc == getSize() - 1) {
            current = tail;
            index = getSize() - 1;
            retval = true;
        }
        // is loc < current index then
        if (loc < getIndex()) {
            for (; getIndex() != loc; index--) { // no initialization step, stopping condition is when current index is
                                                 // = to location, if not move to previous node
                current = current.previous;
            }
            retval = true;
        }
        // is loc > current index?
        else if (loc > getIndex()) {
            for (; getIndex() != loc; index++)
                current = current.next;
            retval = true;
        }
        // else ... loc is at the current index ... nothing to do
        else
            retval = true;
        return (retval);
    }

    // insert first method

    public boolean insertFirst(T item) {
        seek(0);
        return (insertAt(item));
    }

    // insert at current location (loc) method

    public boolean insertAt(T item) {
        boolean valid = false;
        Node<T> node = new Node<T>(item);
        if(size==0) {
            valid=true;
            head=node;
            current=node;
            tail=node;
            index++;
            size++;
        }
        else if(index==0) {
            valid=true;
            node.next=head;
            node.previous=null;
 
            if(head!=null) {
            head.previous=node;
            }
            head=node;
            size++;
            current=node;
            
        } else {
            node.next = current;
            node.previous = current.previous;
            current.previous.next = node;
            current.previous = node;
            current = node;
            size++;
        }
        return valid;

    }

    // insert last method

    public boolean insertLast(T item) {
        seek(size - 1);
        return (insertAt(item));
    }

    // delete first method

    public boolean deleteFirst() {
        return delete(head.next);
    }

    // delete at current location method

    public boolean delete(Node<T> current) {
        if (isEmpty()) {
            return false;
        }
        if (index == 0) {
            current = current.next;
            size--;
            head.next = null;
            head.previous = null;
            head = current;
            return true;
        } else if (index == size - 1) {
            current = current.previous;
            tail.previous = null;
            tail.next = null;
            tail = current;
            size--;
            index--;
            return true;
        } else {
            current.previous.next = current.next;
            current.next.previous = current.previous;
            current = current.next;
            size--;
            return true;
        }
    }

    // delete last method

    public boolean deleteLast() {
        return delete(tail.previous);
    }

    public void printList() {
        for (int i = 0; i < size; i++) {
            seek(i);
            System.out.println(current.data);
        }

    }

}