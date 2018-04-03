import java.util.HashMap;
import java.util.LinkedList;

public class DisjointSetForest<T> implements DisjointSet<T> {

  private HashMap<T, Node<T>> nodeReference;

  public DisjointSetForest() {
    nodeReference = new HashMap<>();
  }

  public void makeSet(T data) {
    if (nodeReference.containsKey(data)) {
      System.err.println("Duplicate element added. Ignoring.");
    }

    Node<T> root = new Node<>(null, data, 0);
    nodeReference.put(data, root);
  }

  public void union(T data1, T data2) {
    T root1 = find(data1);
    T root2 = find(data2);

    if (root1.equals(root2)) {
      // same set
      return;
    }

    Node<T> root1Node = nodeReference.get(root1);
    Node<T> root2Node = nodeReference.get(root2);

    // 1 and 2 are not in the same set. merge them via union by rank
    if (root1Node.rank < root2Node.rank) {
      root1Node.parent = root2Node;

    } else if (root1Node.rank > root2Node.rank) {
      root2Node.parent = root1Node;

    } else {
      root2Node.parent = root1Node;
      root1Node.rank++;
    }
  }

  public T find(T data) {
    Node<T> node = nodeReference.get(data);

    if (node.parent == null) {
      return data;

    } else {
      node.parent = nodeReference.get(find(node.parent.data));
      return node.parent.data;
    }
  }

  public String toString() {
    HashMap<T, LinkedList<T>> rootNode = new HashMap<>();

    for (T data : nodeReference.keySet()) {
      T root = find(data);

      if (!rootNode.containsKey(root)) {
        rootNode.put(root, new LinkedList<T>());
      }

      rootNode.get(root).add(data);
    }

    return rootNode.toString();
  }

  public class Node<R> {
    public Node<R> parent;
    public R data;
    public int rank;

    public Node(Node<R> parent, R data, int rank) {
      this.parent = parent;
      this.data = data;
      this.rank = rank;
    }

    public String toString() {
      return data.toString();
    }
  }

  public static void main(String[] args) {
    String poke1 = "pikachu";
    String poke2 = "bulbasaur";
    String poke3 = "charmander";
    String poke4 = "squirtle";
    String elf1 = "galadriel";
    String elf2 = "legolas";
    DisjointSetLinkedList<String> set = new DisjointSetLinkedList<>();
    set.makeSet(poke1);
    set.makeSet(poke2);
    set.makeSet(poke3);
    set.makeSet(poke4);
    set.makeSet(elf1);
    set.makeSet(elf2);
    System.out.println(set);
    // {squirtle=[squirtle], bulbasaur=[bulbasaur], legolas=[legolas],
    // pikachu=[pikachu], charmander=[charmander], galadriel=[galadriel]}

    set.union(poke1, poke2);
    System.out.println(set.find(poke1) + " " + set.find(poke2));
    // pikachu pikachu
    System.out.println(set);
    // {squirtle=[squirtle], legolas=[legolas], pikachu=[bulbasaur, pikachu],
    // charmander=[charmander], galadriel=[galadriel]}

    set.union(poke3, poke4);
    System.out.println(set);
    // {legolas=[legolas], pikachu=[bulbasaur, pikachu], charmander=[squirtle,
    // charmander], galadriel=[galadriel]}

    set.union(poke1, poke3);
    set.union(elf1, elf2);
    System.out.println(set);
    // {pikachu=[squirtle, bulbasaur, pikachu, charmander], galadriel=[legolas,
    // galadriel]}
  }
}
