public class Hash_LSM_Timer {
  public static void main (String[] args) {
    Control ctrl = new Control();
    Hash_LSM lsm = new Hash_LSM();

    //timer variables
    long startTime;
    long endTime;
    double elapsed;

    //search test variables
    String[] ctrlKeys = new String[1000];
    String[] lsmKeys = new String[1000];
    int count = 0;

    startTime = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      String key = RandomString.getAlphaNumericString(5);
      String val = RandomString.getAlphaNumericString(3);
      if (i%100 == 0) {
        ctrlKeys[count] = key;
        count++;
      }
      ctrl.insert(key, val);
    }
    endTime = System.currentTimeMillis();
    elapsed = (double)(endTime - startTime)/1000;
    System.out.println("Control insert time: " + elapsed);

    count = 0;
    startTime = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      String key = RandomString.getAlphaNumericString(5);
      String val = RandomString.getAlphaNumericString(3);
      if (i%100 == 0) {
        lsmKeys[count] = key;
        count++;
      }
      lsm.insert(key, val);
    }
    endTime = System.currentTimeMillis();
    elapsed = (double)(endTime - startTime)/1000;
    System.out.println("LSM insert time: " + elapsed);

    //test control search
    startTime = System.currentTimeMillis();
    for (int i = 0; i < 1000; i++) {
      ctrl.search(ctrlKeys[i]);
    }
    endTime = System.currentTimeMillis();
    elapsed = (double)(endTime - startTime)/1000;
    System.out.println("Control search time: " + elapsed);

    //test lsm search
    startTime = System.currentTimeMillis();
    for (int i = 0; i < 1000; i++) {
      lsm.search(lsmKeys[i]);
    }
    endTime = System.currentTimeMillis();
    elapsed = (double)(endTime - startTime)/1000;
    System.out.println("LSM search time: " + elapsed);
  }
}