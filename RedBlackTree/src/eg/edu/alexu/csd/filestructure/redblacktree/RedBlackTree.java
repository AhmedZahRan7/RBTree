package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RedBlackTree <T extends Comparable<T>, V> implements IRedBlackTree<T,V> {
    private INode<T, V> root;
    @Override
    public INode<T, V> getRoot() {
        return root;
    }
    @Override
    public boolean isEmpty() {
        return (root == null || root.isNull());
    }
    @Override
    public void clear() {
        root = null;
    }
    @Override
    public V search(T key) {
        if(key==null) throw new RuntimeErrorException(new Error());
        if (isEmpty()) return null;
        INode<T, V> result = searchRecursion(root, key);
        if (result == null || result.isNull()) return null;
        else return result.getValue();
    }
    @Override
    public boolean contains(T key) {
        if(key==null) throw new RuntimeErrorException(new Error());
        V result = search(key);
        return (result != null);
    }
    @Override
    public void insert(T key, V value) {
        if(key==null || value==null) throw new RuntimeErrorException(new Error());
        INode<T, V> inserted = new Node<>();
        if (search(key)==null) {
            inserted.setValue(value);
            inserted.setKey(key);
            inserted.setColor(INode.RED);

            if (root == null) initializeRoot(inserted);
            else {
                insertThisRed(inserted,root);
                fixTreeAfterInsert(inserted);
            }
        }
       else {
           inserted = searchRecursion(root,key);
           inserted.setValue(value);
        }

    }
    @Override
    public boolean delete(T key) {
        if(key==null) throw new RuntimeErrorException(new Error());
        INode<T,V> deleted = searchRecursion(root,key);
        if (deleted == null) return false;
        else {
            deleteThisNode(deleted);
            return true;
        }
    }

    private INode<T, V> searchRecursion(INode<T, V> node, T key) {
        if (node == null || node.isNull() || key==null) return null;
        if (key.compareTo(node.getKey()) == 0) return node;
        if (key.compareTo(node.getKey()) < 0) return searchRecursion(node.getLeftChild(), key);
        if (key.compareTo(node.getKey()) > 0) return searchRecursion(node.getRightChild(), key);
        return null;
    }
    private void initializeRoot(INode<T, V> inserted) {
        this.root = inserted;
        root.setColor(INode.BLACK);
    }
    private void insertThisRed(INode<T, V> inserted , INode<T,V> root) {
        if (inserted.getKey().compareTo(root.getKey()) < 0) {
            if (root.getLeftChild() == null) {
                root.setLeftChild(inserted);
                inserted.setParent(root);
                return;
            } else insertThisRed(inserted,root.getLeftChild());
        }

        if (inserted.getKey().compareTo(root.getKey()) > 0) {
            if (root.getRightChild() == null) {
                root.setRightChild(inserted);
                inserted.setParent(root);
            } else {
                insertThisRed(inserted,root.getRightChild());
            }
        }
    }
    private void fixTreeAfterInsert(INode<T, V> inserted) {
        if(inserted == root) {
            inserted.setColor(INode.BLACK);
            return;
        }
        INode<T, V> parent = inserted.getParent();
        INode<T, V> grandParent = parent.getParent();
        if (parent.getColor() == INode.BLACK) return;
        INode<T, V> uncle;
        if (grandParent.getLeftChild() == parent) uncle = grandParent.getRightChild();
        else uncle = grandParent.getLeftChild();
        if (uncle != null && uncle.getColor() == INode.RED) { //case 1 Recolor the parent and uncle to black and grandParent to red
            // and recursively fix grandParent
            case1Recolor(parent, uncle, grandParent);
            fixTreeAfterInsert(grandParent);
        } else { // uncle is black عنصرية دى يا كباتن
            boolean IamRight = (inserted == parent.getRightChild());
            boolean parentIsRight = (parent == grandParent.getRightChild());
            if (IamRight && !parentIsRight) {//rotate left
                inserted= rotateLeft(parent);
                parent=inserted;
                grandParent= parent.getParent();
                IamRight=false;
            }
            else if (!IamRight && parentIsRight) {//rotate right
                inserted= rotateRight(parent);
                parent=inserted;
                grandParent= parent.getParent();
                IamRight=true;
            }
            // now we convert case 2 into case 3
            if(IamRight && parentIsRight) {
                if (grandParent.getParent() == null) {
                    root = case3RR(parent, grandParent);
                } else {
                    case3RR(parent, grandParent);
                }
            }
            else if(!IamRight && !parentIsRight) {
                if (grandParent.getParent() == null) {
                    root = case3LL(parent, grandParent);
                } else {
                    case3LL(parent, grandParent);
                }
            }
        }
    }
    private void case1Recolor(INode<T, V> parent, INode<T, V> uncle, INode<T, V> grand) {
        parent.setColor(INode.BLACK);
        uncle.setColor(INode.BLACK);
        grand.setColor(INode.RED);
    }
    private INode<T, V> case3LL(INode<T, V> parent, INode<T, V> grand) {
        grand.setColor(INode.RED);
        parent.setColor(INode.BLACK);
        return rotateRight(grand);
    }
    private INode<T, V> case3RR(INode<T, V> parent, INode<T, V> grand) {
        grand.setColor(INode.RED);
        parent.setColor(INode.BLACK);
        return rotateLeft(grand);
    }
    private INode<T, V> rotateLeft(INode<T, V> node) {
        if(node.getParent()!=null ) {
            boolean right = (node.getParent().getRightChild() == node);
            if (right) node.getParent().setRightChild(node.getRightChild());
            else node.getParent().setLeftChild(node.getRightChild());
        }
        INode<T, V> leftOfRight = node.getRightChild().getLeftChild();
        node.getRightChild().setLeftChild(node);
        node.getRightChild().setParent(node.getParent());
        node.setParent(node.getRightChild());
        node.setRightChild(leftOfRight);
        if (leftOfRight!=null) leftOfRight.setParent(node);
        return node.getParent();
    }
    private INode<T, V> rotateRight(INode<T, V> node) {
        if(node.getParent()!=null ) {
            boolean right = (node.getParent().getRightChild() == node);
            if (right) node.getParent().setRightChild(node.getLeftChild());
            else node.getParent().setLeftChild(node.getLeftChild());
        }

        INode<T, V> rightOfLeft = node.getLeftChild().getRightChild();
        node.getLeftChild().setRightChild(node);
        node.getLeftChild().setParent(node.getParent());
        node.setParent(node.getLeftChild());
        node.setLeftChild(rightOfLeft);
        if (rightOfLeft!=null) rightOfLeft.setParent(node);
        return node.getParent();
    }

    private void deleteThisNode(final INode<T, V> node) {
        if (node.getRightChild() == null || node.getLeftChild() == null) {
            this.goToHell(node);
            return;
        }
        INode<T, V> suc = this.getSuccessor(this.root, node.getKey());
        node.setKey(suc.getKey());
        node.setValue(suc.getValue());
        this.goToHell(suc);
    }
    private void goToHell(final INode<T, V> node) {
        INode<T, V> parent = node.getParent();
        INode<T, V> left = node.getLeftChild();
        INode<T, V> right = node.getRightChild();

        boolean nodeColor=node.getColor(),
                leftColor=(left==null)?INode.BLACK:left.getColor(),
                rightColor=(right==null)?INode.BLACK:right.getColor(),
                nodeIsRightOfItsParent=(parent!=null&&parent.getRightChild()==node);

        if(parent==null){// it's root
            if (right!=null) root=right;
            else root=left;
            if(root!=null)root.setParent(null);
            return;
        }
        if(nodeColor==INode.RED){ //case I node is red
            hellCaseI(left,right,parent,nodeIsRightOfItsParent);
        }
        else if(leftColor==INode.RED||rightColor==INode.RED){//case II node is black but has a red child
            hellCaseII(left,right,parent,leftColor,rightColor,nodeIsRightOfItsParent);
        }
        else { //case III node is black and both child are black
            hellCaseIII(left,right,parent,nodeIsRightOfItsParent);
        }
    }
    private INode<T, V> successor= null;
    public INode<T, V> getSuccessor(INode<T, V> root, T key) {
        if (root != null) {
            if (root.getKey() == key) {
                if (root.getRightChild() != null) {
                    INode<T, V> t = root.getRightChild();
                    while (t.getLeftChild() != null) {
                        t = t.getLeftChild();
                    }
                    successor = t;
                }
            } else if (root.getKey().compareTo(key)>0) {
                successor = root;
                getSuccessor(root.getLeftChild(), key);
            } else if (root.getKey().compareTo(key)<0) {
                getSuccessor(root.getRightChild(),key);
            }
        }
        return successor;
    }

    private void hellCaseI(INode<T, V> left,INode<T, V>right,INode<T, V>parent,boolean nodeIsRightOfItsParent){
        if(left==null&&right==null){//case Ia
            if(nodeIsRightOfItsParent) parent.setRightChild(null);
            else parent.setLeftChild(null);
        }
        else if(left==null){//case Ib
            if(nodeIsRightOfItsParent) parent.setRightChild(right);
            else parent.setLeftChild(right);
            right.setParent(parent);
        }
        else if(right==null){//case Ic
            if(nodeIsRightOfItsParent) parent.setRightChild(left);
            else parent.setLeftChild(left);
            left.setParent(parent);
        }
    }
    private void hellCaseII(INode<T, V> left,INode<T, V>right,INode<T, V>parent,
                            boolean leftColor,boolean rightColor,boolean nodeIsRightOfItsParent){
        if(leftColor==INode.RED){
            if(nodeIsRightOfItsParent) parent.setRightChild(left);
            else parent.setLeftChild(left);
            left.setColor(INode.BLACK);
            left.setParent(parent);
        }
        else if(rightColor==INode.RED){
            if(nodeIsRightOfItsParent) parent.setRightChild(right);
            else parent.setLeftChild(right);
            right.setColor(INode.BLACK);
            right.setParent(parent);
        }
    }
    private void hellCaseIII(INode<T, V> left,INode<T, V>right,INode<T, V>parent,boolean nodeIsRightOfItsParent){
        if(left==null&&right==null){ //case IIIa
            if(nodeIsRightOfItsParent) parent.setRightChild(null);
            else parent.setLeftChild(null);
            fixDoubleBlackNode(parent,nodeIsRightOfItsParent);
        }
        else if(left!=null){//case IIIb
            if(nodeIsRightOfItsParent) parent.setRightChild(left);
            else parent.setLeftChild(left);
            left.setParent(parent);
            fixDoubleBlackNode(parent,nodeIsRightOfItsParent);
        }
        else {//case IIIc
            if(nodeIsRightOfItsParent) parent.setRightChild(right);
            else parent.setLeftChild(right);
            right.setParent(parent);
            fixDoubleBlackNode(parent,nodeIsRightOfItsParent);
        }
    }
    private void fixDoubleBlackNode(INode<T,V> parent,boolean right){
        if(parent==null)  //now its root
            return;
        boolean editRoot = (parent.getParent()==null);
        //parent is a node that it's(right or left) is double black which may be null
        INode<T,V> doubleBlack = right? parent.getRightChild(): parent.getLeftChild();
        INode<T,V> sibling = !right? parent.getRightChild(): parent.getLeftChild();
        if(sibling==null) throw new Error("Can't Happen"); //as parent has double black at one of its ends so the other end can't be null
        if (sibling.getColor()==INode.RED) {//case C
            parent.setColor(INode.RED);
            sibling.setColor(INode.BLACK);
            if(right) if(editRoot)root=rotateRight(parent);
                    else rotateRight(parent);
            else if(editRoot)root=rotateLeft(parent);
            else rotateLeft(parent);

            fixDoubleBlackNode(parent,right);
        }

        else if((sibling.getRightChild()==null || sibling.getRightChild().getColor()==INode.BLACK)&&
                        (sibling.getLeftChild()==null || sibling.getLeftChild().getColor()==INode.BLACK)){ //sibling is black with 2 black childs .. case B
            sibling.setColor(INode.RED);
            if(parent.getColor()==INode.RED) parent.setColor(INode.BLACK);
            else fixDoubleBlackNode(parent.getParent(),
                    parent.getParent()!=null&&parent.getParent().getRightChild()==parent );
        }
        // now remaining case A that has 4 sub-cases
        // 1) I am left and sibling has left red child
        // 2) I am left and sibling has right red child
        // 3) I am right and sibling has right red child
        // 4) I am right and sibling has left red child
        else if(!right&&sibling.getLeftChild()!=null&&
                sibling.getLeftChild().getColor()==INode.RED
                && (sibling.getRightChild()==null||sibling.getRightChild().getColor()==INode.BLACK)){
            sibling.setColor(INode.RED);
            sibling.getLeftChild().setColor(INode.BLACK);
            rotateRight(sibling);
            // now its sub-case 2
            if(editRoot) root = subCaseSolver(parent, false);
            else subCaseSolver(parent,false);
        }
        else if (!right && sibling.getRightChild()!=null && sibling.getRightChild().getColor()==INode.RED){
            if(editRoot) root = subCaseSolver(parent, false);
            else subCaseSolver(parent, false);
        }
        else if(right&&sibling.getRightChild()!=null&&
                sibling.getRightChild().getColor()==INode.RED
                && (sibling.getLeftChild()==null||sibling.getLeftChild().getColor()==INode.BLACK)){
            sibling.setColor(INode.RED);
            sibling.getRightChild().setColor(INode.BLACK);
            rotateLeft(sibling);
            // now its sub-case 4
            if(editRoot) root = subCaseSolver(parent, true);
            else subCaseSolver(parent, true);
        }
        else if(right && sibling.getLeftChild()!=null && sibling.getLeftChild().getColor()==INode.RED){
            if(editRoot) root =subCaseSolver(parent, true);
            else subCaseSolver(parent,true);
        }

    }

    private INode<T, V> subCaseSolver(INode<T,V> parent,boolean right){
        if(right){
            parent.getLeftChild().getLeftChild().setColor(INode.BLACK);
            parent.getLeftChild().setColor(parent.getColor());
            parent.setColor(INode.BLACK);
            return rotateRight(parent);
        }
        else {
            parent.getRightChild().getRightChild().setColor(INode.BLACK);
            parent.getRightChild().setColor(parent.getColor());
            parent.setColor(INode.BLACK);
            return rotateLeft(parent);
        }
    }
}