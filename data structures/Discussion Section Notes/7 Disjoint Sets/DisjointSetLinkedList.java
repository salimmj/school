import java.util.HashMap;
import java.util.LinkedList;

public class DisjointSetLinkedList<T> implements DisjointSet<T> {

  private HashMap<T, Node<T>> nodeReference;

  public DisjointSetLinkedList() {
    nodeReference = new HashMap<>();
  }

  public void makeSet(T data) {
    if (nodeReference.containsKey(data)) {
      System.err.println("Duplicate element added. Ignoring.");
    }

    Node<T> root = new Node<>(null, data);
    nodeReference.put(data, root);
  }

  public void union(T data1, T data2) {
    Node<T> data1Node = nodeReference.get(data1);

    T root2 = find(data2);
    Node<T> root2Node = nodeReference.get(root2);

    root2Node.prev = data1Node;
  }

  public T find(T data) {
    Node<T> node = nodeReference.get(data);
    if (node.prev == null) {
      return data;
    } else {
      return find(node.prev.data);
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
    public Node<R> prev;
    public R data;

    public Node(Node<R> prev, R data) {
      this.prev = prev;
      this.data = data;
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
