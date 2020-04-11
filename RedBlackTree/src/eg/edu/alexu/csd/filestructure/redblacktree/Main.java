package eg.edu.alexu.csd.filestructure.redblacktree;

public class Main {
    public static void main(String[] args) {
        RedBlackTree<Integer,String> tree = new RedBlackTree<>();
        tree.insert(1,"1");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(2,"2");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(3,"3");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(4,"4");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(11,"11");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(12,"12");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(13,"13");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(14,"14");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(7,"7");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(100,"100");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(54,"54");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(-2,"-2");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(5,"5");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(0,"0");
        RBTreePrinter.print(tree.getRoot());
        tree.insert(-5,"-5");
        RBTreePrinter.print(tree.getRoot());


        System.out.println(tree.delete(1));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(2));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(3));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(4));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(5));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(-5));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(-2));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(7));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(11));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(12));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(13));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(14));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(54));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(100));
        RBTreePrinter.print(tree.getRoot());
        System.out.println(tree.delete(0));
        RBTreePrinter.print(tree.getRoot());



    }
}
