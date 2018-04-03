
public interface DisjointSet<T> {
  public void makeSet(T data);

  public void union(T data1, T data2);

  public T find(T data);
}
