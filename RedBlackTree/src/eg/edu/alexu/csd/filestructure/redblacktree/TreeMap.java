package eg.edu.alexu.csd.filestructure.redblacktree;
import javax.management.RuntimeErrorException;
import java.util.*;
public class TreeMap<T extends Comparable<T>, V> implements ITreeMap<T,V> {
    private IRedBlackTree<T,V> RB;
    private Comparator<T> comparator=new comp();
    private int size;
    public TreeMap(){
        RB = new RedBlackTree<>();
    }
    public TreeMap(Comparator<T> comparator){
        RB = new RedBlackTree<>();
        this.comparator = comparator;
    }
    public TreeMap(Map<T,V> map){
        RB = new RedBlackTree<T,V>();
        Set<Map.Entry<T,V>> set = map.entrySet();
        for (Map.Entry<T, V> entry : set) {
            size++;
            RB.insert(entry.getKey(), entry.getValue());
        }
    }
    public TreeMap(SortedMap<T,V> sortedMap){
        RB = new RedBlackTree<T,V>();
        Set<Map.Entry<T,V>> set = sortedMap.entrySet();
        for (Map.Entry<T, V> entry : set) {
            size++;
            RB.insert(entry.getKey(), entry.getValue());
        }
    }
    @Override
    public Map.Entry<T,V> ceilingEntry(T key) {
        if(key==null) throw new RuntimeErrorException(new Error());
        INode<T,V> root = RB.getRoot();
        if (root==null ||root.isNull()) return null;
        if(RB.contains(key)) {
            MapEntry entry = new MapEntry();
            entry.setKey(key);
            entry.setValue(RB.search(key));
            return entry;
        }
        INode<T,V> suc = getSuccessor(root,key);
        if(suc==null) return null;
        MapEntry entry=new MapEntry();
        entry.setValue(suc.getValue());
        entry.setKey(suc.getKey());
        return entry;
    }
    @Override
    public T ceilingKey(T key) {
        Map.Entry<T, V> entry = ceilingEntry(key);
        if (entry==null)return null;
        return entry.getKey();
    }
    @Override
    public void clear() {
        RB = new RedBlackTree<>();
        size=0;
    }
    @Override
    public boolean containsKey(T key) {
        if(key==null)  throw new RuntimeErrorException(new Error());
        return RB.contains(key);
    }
    @Override
    public boolean containsValue(V value) {
        if(value==null)  throw new RuntimeErrorException(new Error());
        ArrayList<Map.Entry<T,V>> arrayList =new ArrayList<>();
        fillTheArrayList(arrayList,RB.getRoot());
        for(Map.Entry<T,V> entry : arrayList){
            if (entry.getValue()==value||entry.getValue().equals(value)) return true;
        }
        return false;
    }
    @Override
    public Set<Map.Entry<T, V>> entrySet() {
        return new HashSet<>(fillTheArrayListForEntrySet(RB.getRoot()));
    }
    @Override
    public Map.Entry<T,V> firstEntry() {
        INode<T,V> root = RB.getRoot();
        if(root==null) return null;
        while (root.getLeftChild()!=null) root=root.getLeftChild();
        MapEntry entry = new MapEntry();
        entry.setValue(root.getValue());
        entry.setKey(root.getKey());
        return entry;
    }
    @Override
    public T firstKey() {
        Map.Entry<T,V> entry = firstEntry();
        if (entry==null) return null;
        else return entry.getKey();
    }
    @Override
    public Map.Entry<T,V> floorEntry(T key) {
        if(key==null) throw new RuntimeErrorException(new Error());
        if(RB.contains(key)) {
            MapEntry entry = new MapEntry();
            entry.setKey(key);
            entry.setValue(RB.search(key));
            return entry;
        }
        INode<T,V> pre = getPredecessor(RB.getRoot(),key);
        if(pre==null) return null;
        MapEntry entry=new MapEntry();
        entry.setValue(pre.getValue());
        entry.setKey(pre.getKey());
        return entry;
    }
    @Override
    public T floorKey(T key) {
        Map.Entry<T,V> entry = floorEntry(key);
        if(entry==null) return null;
        else return entry.getKey();
    }
    @Override
    public V get(T key) {
        return RB.search(key);
    }
    @Override
    public ArrayList<Map.Entry<T,V>> headMap(T toKey) {
        if(RB.getRoot()==null){return null;}
        Set<T> treeSet =  keySet();
        ArrayList<Map.Entry<T,V>> set = new ArrayList<>();
        for(T k:treeSet) {
            if(k.compareTo(toKey)>0) {
                MapEntry entry =  new MapEntry();
                entry.setValue(get(k));
                entry.setKey(k);
                set.add(entry);
            } else {
                break;
            }
        }
        return set;
    }
    @Override
    public ArrayList<Map.Entry<T,V>> headMap(T toKey, boolean inclusive) {
        if(RB.getRoot()==null)return null;
        if(inclusive==false){return headMap(toKey);}
        else{
                Set<T> treeSet =  keySet();
                ArrayList<Map.Entry<T,V>> set = new ArrayList<>();
                for (T k : treeSet) {
                    if (k.compareTo(toKey) >= 0) {
                        MapEntry entry = new MapEntry();
                        entry.setValue(get(k));
                        entry.setKey(k);
                        set.add(entry);
                    } else {
                        break;
                    }
                }
                return set;
        }
    }
    @Override
    public Set<T> keySet() {
        Set<Map.Entry<T,V>> set = entrySet();
        if(set==null) return null;
        Set<T> keySet = new HashSet<T>();
        for(Map.Entry<T,V> entry:set) keySet.add(entry.getKey());
        return keySet;
    }
    @Override
    public Map.Entry<T,V> lastEntry() {
        INode<T,V> root = RB.getRoot();
        if(root==null) return null;
        while (root.getRightChild()!=null) root=root.getRightChild();
        MapEntry entry = new MapEntry();
        entry.setValue(root.getValue());
        entry.setKey(root.getKey());
        return entry;
    }
    @Override
    public T lastKey() {
        Map.Entry<T,V> entry = lastEntry();
        if (entry==null) return null;
        else return entry.getKey();
    }
    @Override
    public Map.Entry<T,V> pollFirstEntry() {
        Map.Entry<T,V> firstEntry = firstEntry();
        if(firstEntry==null) return null;
        if (size>0 && RB.delete(firstEntry.getKey()))size--;
        return firstEntry;
    }
    @Override
    public Map.Entry<T,V> pollLastEntry() {
        Map.Entry<T,V> lastEntry = lastEntry();
        if(lastEntry==null) return null;
        if (size>0 && RB.delete(lastEntry.getKey()))size--;
        return lastEntry;
    }
    @Override
    public void put(T key, V value) {
        if(key==null) throw new RuntimeErrorException(new Error());
        if(!RB.contains(key))size++;
        RB.insert(key,value);
    }
    @Override
    public void putAll(Map<T,V> map) {
        if (map==null) throw new RuntimeErrorException(new Error());
        Set<Map.Entry<T,V>> entrySet =map.entrySet();
        for (Map.Entry<T,V> entry : entrySet) {
            if(!RB.contains(entry.getKey()))size++;
            RB.insert(entry.getKey(),entry.getValue());
        }
    }
    @Override
    public boolean remove(T key) {
        if(key==null) throw new RuntimeErrorException(new Error());
        if (size>0 && RB.delete(key)) {
            size--; return true;
        }
        return false;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public Collection<V> values() {
        ArrayList<Map.Entry<T,V>> arrayList =new ArrayList<>();
        fillTheArrayList(arrayList,RB.getRoot());
        Collection<V> collections= new ArrayList<V>();
        for (Map.Entry<T, V> tvEntry : arrayList) collections.add(tvEntry.getValue());
        return collections;
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
    private INode<T, V> predecessor= null;
    public INode<T, V> getPredecessor (INode<T,V> root, T key) {
        if (root != null ) {
            if (comparator.compare(root.getKey(),key) == 0) {
                if (root.getLeftChild() != null ) {
                    INode<T,V> t = root.getLeftChild();
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

    private class comp implements Comparator<T> {
        public int compare(T a, T b)
        {
            return a.compareTo(b);
        }
    }
    private class MapEntry implements Map.Entry<T,V>{
        private T key ;
        private V value;
        public void setKey(T key){
            this.key=key;
        }
        @Override
        public T getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }
        @Override
        public V setValue(V v) {
            this.value=v;
            return v;
        }
    }

    private ArrayList<Map.Entry<T,V>> fillTheArrayListForEntrySet(INode<T,V> root) {
        ArrayList<Map.Entry<T,V>> arrayList = new ArrayList<>();
        Stack<INode<T,V>> stack=new Stack<>();
             while (!root.isNull()&&!stack.empty()){
                while (root.isNull()){
                    stack.push(root);
                    root=root.getLeftChild();
                }
                root=stack.peek();
                stack.pop();
                MapEntry entry =  new MapEntry();
                entry.setValue(root.getValue());
                entry.setKey(root.getKey());
                arrayList.add(entry);
                root=root.getRightChild();
        }
        return arrayList;
    }

    private void fillTheArrayList(ArrayList<Map.Entry<T,V>> arrayList,INode<T,V> root){
        if(root !=  null) {
            fillTheArrayList(arrayList,root.getLeftChild());
            MapEntry entry =  new MapEntry();
            entry.setValue(root.getValue());
            entry.setKey(root.getKey());
            arrayList.add(entry);
            fillTheArrayList(arrayList,root.getRightChild());
        }
    }
}
