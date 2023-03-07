import java.util.*;

public class LinkStrand implements IDnaStrand {

    private class Node {
        String info;
        Node next;

        Node (String info) {
            this.info = info;
        }

        Node(String x, Node node) {
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
        Node x = myFirst;
        
        while (x != null) {
            strand.append(x.info);
            x = x.next;
        }

        return strand.toString();
    }

    @Override
    public IDnaStrand reverse() {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'reverse'");
        Node x = myFirst;
        LinkStrand revStrand = new LinkStrand();

        while (x != null) {
            StringBuilder s = new StringBuilder(x.info);
            String revString = s.reverse().toString();
            Node rev = new Node(revString);

            revStrand.mySize = revStrand.mySize + rev.info.length();
            rev.next = revStrand.myFirst;
            revStrand.myFirst = rev;
            x = x.next;
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
        // System.out.println(index);
        // System.out.println(this.toString());
        //System.out.println(myIndex +" "+ mySize + " " + myLocalIndex + " " + index);
        //System.out.println(myCurrent.info.length());

        if (index < 0 || index >= this.mySize) {
            throw new IndexOutOfBoundsException();
        }

        if (index <= myIndex) {
            myIndex = 0;
            myLocalIndex = 0;
            myCurrent = myFirst;
        }

        myLocalIndex = myLocalIndex + (index - myIndex);
        myIndex = index;

        while (myCurrent != myLast && myLocalIndex >= myCurrent.info.length()) {
            myLocalIndex = myLocalIndex - myCurrent.info.length();
            myCurrent = myCurrent.next;
        }

        return myCurrent.info.charAt(myLocalIndex);
    }
    
}
