import java.util.Scanner;

public class Hash_LSM_Driver {
  public static void main (String[] args) {
    Hash_LSM lsm = new Hash_LSM();
    Scanner sc = new Scanner(System.in);

    System.out.println("Welcome to the demo of my LSM-like data structure using hash tables");
    System.out.println("Press a to insert 10 random new records");
    System.out.println("Press s to be prompted to search for a record");
    System.out.println("Press q to quit");
    String input;

    while (!(input = sc.nextLine()).equals("q")) {
      char c = input.charAt(0);
      System.out.println("");
      switch (c) {
        case 'a':
          for (int i = 0; i < 100000; i++) {
            lsm.insert(RandomString.getAlphaNumericString(5), RandomString.getAlphaNumericString(3));
          }
          System.out.println("current state of c0:");
          //lsm.printC0();
          System.out.println("");
          break;
        case 's':
          System.out.println("Please enter the key that you would like to search for");
          String key = sc.nextLine();
          String res = lsm.search(key);
          if (res.equals("")) {
            System.out.println("No record with matching key found.");
          } else {
            System.out.println("Data corresponding to given key: " + res);
          }
          System.out.println("");
          break;
        default: 
          System.out.println("Input not recognized.");
          break;
      }
      System.out.println("Press a to insert 10 random new records");
      System.out.println("Press s to be prompted to search for a record");
      System.out.println("Press q to quit");
    }

    
  }
}