import java.util.Arrays;

public class BucketNode {
  public BucketNodeData data;
  public BucketNode next;

  public BucketNode (String key, String value) {
    BucketNodeData temp = new BucketNodeData(key, value);
    this.data = temp;
    this.next = null;
  }

  public void add (String key, String value) {
    BucketNode node = new BucketNode(key, value);
    this.next = node;
  }

  public BucketNodeData[] getSortedArray() {
    BucketNode cur = this;
    int count = 0;
    while (cur != null) {
      count++;
      cur = cur.next;
    }

    BucketNodeData[] arr = new BucketNodeData[count];
    cur = this;
    count = 0;
    while (cur != null) {
      arr[count] = cur.data;
      count++;
      cur = cur.next;
    }
    Arrays.sort(arr);
    return arr;
  }

  public String toString() {
    BucketNode node = this;
    String retVal = "";
    while (node != null) {
      retVal += "Key: " + node.data.key + ", Value: " + node.data.value + " -> ";
      node = node.next;
    }
    return retVal;
  }
}