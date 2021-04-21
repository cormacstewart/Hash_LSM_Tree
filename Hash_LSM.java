import java.io.*;

public class Hash_LSM {
  public final int TABLE_SIZE = 10; //number of values to take before flushing to disk
  public BucketNode[] c0; //c0 is a hash table that writes to disk when it reaches capacity
  public int count; //count stores the current number of values in c0

  //variables for file io
  

  public Hash_LSM () {
    c0 = new BucketNode[TABLE_SIZE]; //initialize c0
    count = 0; //initialize count

    for (int i = 0; i < TABLE_SIZE; i++) {

    }
  }
}