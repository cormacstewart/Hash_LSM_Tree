public class Hash_LSM_Driver {
  public static void main (String[] args) {
    Hash_LSM lsm = new Hash_LSM();
    lsm.insert("sdlfhvbl", "a");
    lsm.insert("sd", "b");
    lsm.insert("wesjfn", "c");
    lsm.insert("qwj", "d");
    lsm.insert("wekjhf48", "e");
    lsm.insert("dln", "f");
    lsm.insert("a", "g");
    lsm.insert("sd.fkn", "h");
    lsm.insert(";eofhrwi", "i");
    lsm.insert("ergeg", "g");
    lsm.insert("elkkufh", "h");
    lsm.insert("wejhf", "i");
    lsm.insert("qweqwef", "j");
    lsm.insert("htryjku", "k");
    lsm.insert("uuipp;o", "l");
    lsm.insert("bfgdfgh", "m");
    lsm.insert("bfgbg", "n");
    lsm.insert("csdf", "o");
    lsm.insert("vfdrr", "p");
    lsm.insert("cdasdff", "q");
    lsm.insert("ergrhyuj", "r");
    System.out.println(lsm.search("ergrhyuj"));
  }
}