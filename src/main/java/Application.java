public class Application {
   public static void main(String[] args) {
      System.out.println(Utils.getMoreInfoTemp("днепр", 0));
      System.out.println(Utils.getMoreInfoTemp("киев", 0));

      System.out.println(Utils.getRangeTemp("днепр", 0));
      System.out.println(Utils.getRangeTemp("киев", 0));
   }
}
