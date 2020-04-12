package eg.edu.alexu.csd.filestructure.redblacktree;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class Main {
    private static INode<Integer, String> predecessor;

    public static void main(String[] args) {
        TreeMap<Integer,String> map = new TreeMap<>();
        map.put(1,"1");
        map.put(0,"0");
        map.put(3,"3");
        map.put(6,"6");
        map.put(4,"4");
        map.put(13,"13");
        map.put(16,"16");
        map.put(10,"10");

        RedBlackTree<Integer,String> RB = new RedBlackTree();
        RB.insert(1,"1");
        RB.insert(0,"0");
        RB.insert(3,"3");
        RB.insert(6,"6");
        RB.insert(4,"4");
        RB.insert(13,"13");
        RB.insert(16,"16");
        RB.insert(10,"10");
        RBTreePrinter.print(RB.getRoot());

        System.out.println(getPredecessor(RB.getRoot(),17).getValue());
//        Set<Map.Entry<Integer,String>> set = map.entrySet();
//        for (Map.Entry<Integer,String> entry : set) System.out.println(entry.getKey()+" ");

    }

    static Comparator comparator = new comp();
    public static INode<Integer,String>getPredecessor (INode<Integer,String> root, Integer key) {
        if (root != null ) {
            if (comparator.compare(root.getKey(),key) == 0) {
                if (root.getLeftChild() != null ) {
                    INode<Integer,String> t = root.getLeftChild();
                    while (t.getRightChild() != null ) {
                        t = t.getRightChild();
                    }
                    predecessor = t;
                }
            } else if (comparator.compare(root.getKey(),key)>0) {
                getPredecessor(root.getLeftChild(),key);
            } else if (comparator.compare(root.getKey(),key)<0) {
                predecessor = root;
                getPredecessor(root.getRightChild(),key);
            }
        }
        return predecessor;
    }

    private static class comp implements Comparator<Integer>
    {
        public int compare(Integer a, Integer b)
        {
            return a.compareTo(b);
        }
    }
}
