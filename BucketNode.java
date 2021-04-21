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

  public String toString() {
    BucketNode node = this;
    String retVal = "";
    while (node != null) {
      retVal += "Key: " + node.data.key + ", Value: " + node.data.value + " -> ";
      node = node.next;
    }
    return retVal;
  }

  private class BucketNodeData {
    public String key;
    public String value;

    public BucketNodeData (String key, String value) {
      this.key = key;
      this.value = value;
    }
  }
}