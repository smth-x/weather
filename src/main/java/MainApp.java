import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MainApp {
   static WebDriver driver = null;
   public static void main(String[] args) {
      System.setProperty("webdriver.chrome.driver", "C:\\tools\\chrome-driver\\chromedriver.exe");

      driver = new ChromeDriver();
      driver.get("https://sinoptik.ua/погода-днепр-303007131");

      System.out.println(getTemp(1));

      driver.close();
      System.exit(130);
   }
   public static String getTemp(int dayId) {
      if (!isDayIdCorrect(dayId)) {
         return null;
      }
      //paths and objects
      String maxPath = "//div[@id='bd"+dayId+"']/div[@class='temperature']/div[@class='max']/span";
      String minPath = "//div[@id='bd"+dayId+"']/div[@class='temperature']/div[@class='min']/span";

      //тупая проверка из-за html кода страницы
      String dayOfWeekPath;
      if (dayId==1){
         dayOfWeekPath = "//*[@id=\"bd1\"]/p[1]";
      } else {
         dayOfWeekPath = "//*[@id=\"bd"+dayId+"\"]/p[1]/a";
      }

      String datePath = "//div[@id='bd"+dayId+"']/p[@class='date ']";
      String monthPath = "//div[@id='bd"+dayId+"']/p[@class='month']";

      WebElement dayOfWeek = driver.findElement(By.xpath(dayOfWeekPath));
      WebElement date = driver.findElement(By.xpath(datePath));
      WebElement month = driver.findElement(By.xpath(monthPath));
      WebElement minTemp = driver.findElement(By.xpath(minPath));
      WebElement maxTemp = driver.findElement(By.xpath(maxPath));

      //returning result of method
      String statistic = "";
      statistic += "Day: "+dayOfWeek.getText()+", "+date.getText()+" "+month.getText()+".\n";
      statistic += "Min: " +minTemp.getText()+".\n";
      statistic += "Max: " + maxTemp.getText()+".";
      return statistic;
   }

   public static boolean isDayIdCorrect(int dayID) {
      if (dayID < 1 || dayID > 7) {
         return false;
      }
      return true;
   }
}