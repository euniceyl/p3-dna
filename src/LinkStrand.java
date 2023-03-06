import java.util.*;

public class LinkStrand implements IDnaStrand {

    private class Node {
        String info;
        Node next;
        private Node(String x, Node node) {
            info = x;
            next = node;
        }
    }

    private Node myFirst, myLast;
    private long mySize;
    private int myAppends;
    private int myIndex;
    private Node myCurrent;
    private int myLocalIndex;

    public LinkStrand() {
        this("");
    }

    public LinkStrand(String w) {
        initialize(w);
    }

    @Override
    public long size() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'size'");
        return mySize;
    }

    @Override
    public void initialize(String source) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'initialize'");
        myFirst = new Node(source);
        myLast = myFirst;
        mySize = source.length();
        myAppends = 0;
        myIndex = 0;
        myCurrent = myFirst;
        myLocalIndex = 0;
    }

    @Override
    public IDnaStrand getInstance(String source) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
        return new LinkStrand(source);
    }

    @Override
    public IDnaStrand append(String dna) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'append'");
        Node n = new Node(dna);
        myLast.next = n;
        myLast = myLast.next;
        mySize = mySize + dna.length();
        myAppends = myAppends + 1;
        
        return this;
    }

    @Override
    public String toString() {
        StringBuilder strand = new StringBuilder();
        myCurrent = myFirst;
        
        while (myCurrent != null) {
            strand.append(myCurrent.info);
            myCurrent = myCurrent.next;
        }

        return strand.toString();
    }

    @Override
    public IDnaStrand reverse() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'reverse'");
        Node myCurrent = myFirst;
        LinkStrand revStrand = new LinkStrand();

        while (myCurrent != null) {
            StringBuilder s = new StringBuilder(myCurrent.info);
            String revString = s.reverse();
            Node rev = new Node(revString.toString());

            revStrand.mySize = revStrand.mySize + rev.info.length();
            rev.next = revStrand.myFirst;
            revStrand.myFirst = rev;
            myCurrent = myCurrent.next;
        }

        return revStrand;
    }

    @Override
    public int getAppendCount() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'getAppendCount'");
        return myAppends;
    }

    @Override
    public char charAt(int index) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'charAt'");
        if (index < 0 || index >= this.mySize) {
            throw new IndexOutOfBoundsException();
        }

        if (index <= myIndex) {
            myIndex = 0;
            myLocalIndex = 0;
            myCurrent = myFirst;
        }

        while (index != myIndex) {
            if (myCurrent.next != null && myCurrent.info.length() <= myLocalIndex) {
                myCurrent = myCurrent.next;
                myIndex = myIndex + 1;
                myLocalIndex = 0;
            }
            else {
                myIndex = myIndex + 1;
                myLocalIndex = myLocalIndex + 1;
            }
        }

        return myCurrent.info.charAt(myLocalIndex);
    }
    
}
