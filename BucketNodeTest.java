public class BucketNodeTest {
  public static void main (String[] args) {
    BucketNode node = new BucketNode("k", "k");
    BucketNode temp = node;
    String buff = "abcdefghij";
    for (int i = 9; i >= 0; i--) {
      temp.add("" + buff.charAt(i), "" + buff.charAt(i));
      temp = temp.next;
    }
    System.out.print(node);
    System.out.println("");
    BucketNodeData[] arr = node.getSortedArray();
    for (int i = 0; i < arr.length; i++) {
      System.out.print(arr[i].key + " ");
    }
  }
}