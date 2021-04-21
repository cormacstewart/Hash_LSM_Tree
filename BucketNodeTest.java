public class BucketNodeTest {
  public static void main (String[] args) {
    BucketNode node = new BucketNode("1", "1");
    BucketNode temp = node;
    for (int i = 2; i < 11; i++) {
      BucketNode insert = new BucketNode("" + i, "" + i);
      temp.next = insert;
      temp = temp.next;
    }
    System.out.print(node);
  }
}