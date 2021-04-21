public class BucketNodeData implements Comparable<BucketNodeData> {
    public String key;
    public String value;

    public BucketNodeData (String key, String value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public int compareTo (BucketNodeData b) {
      return this.key.compareTo(b.key);
    }
}