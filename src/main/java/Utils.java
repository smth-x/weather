import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDate;
import java.util.List;

public class Utils {
   static LocalDate date;
   static WebDriver driver = new ChromeDriver();
   public static String getRangeTemp(String city, int day) {
      System.setProperty("webdriver.chrome.driver", "C:\\tools\\chrome-driver\\chromedriver.exe");
      if (!isDayIdCorrect(day)) {
         return null;
      }
      int dayIdInCode = day+1;
      String pathForDriver = "https://sinoptik.ua/погода-"+city.toLowerCase();
      if (city.equalsIgnoreCase("днепр")){
         pathForDriver = pathForDriver+"-303007131";
      }
      driver.get(pathForDriver);

      //paths and objects
      String maxPath = "//div[@id='bd"+dayIdInCode+"']/div[@class='temperature']/div[@class='max']/span";
      String minPath = "//div[@id='bd"+dayIdInCode+"']/div[@class='temperature']/div[@class='min']/span";

      WebElement minTemp = driver.findElement(By.xpath(minPath));
      WebElement maxTemp = driver.findElement(By.xpath(maxPath));

      //returning result of method
      date = LocalDate.now().plusDays(day);
      String statistic = "";
      statistic += city+", "+date+"\n";
      statistic += "Min: " +minTemp.getText()+"\n";
      statistic += "Max: " + maxTemp.getText()+"";
      return statistic;
   }
   public static String getMoreInfoTemp(String city, int day){
      System.setProperty("webdriver.chrome.driver", "C:\\tools\\chrome-driver\\chromedriver.exe");
      if (!isDayIdCorrect(day)) {
         return null;
      }
      int dayIdInCode = day+1;
      date = LocalDate.now().plusDays(day);

      String pathForDriver = "https://sinoptik.ua/погода-"+city.toLowerCase();
      if (city.equalsIgnoreCase("днепр")){
         pathForDriver+="-303007131";
      }
      pathForDriver+="/"+date;
      driver.get(pathForDriver);

      //find all temperatures
      WebElement temperatureBlock = driver.findElement(By.xpath("//*[@id=\"bd"+dayIdInCode+"c\"]/div[1]/div[2]/table/tbody/tr[3]"));
      List<String> temperatures = temperatureBlock.findElements(By.tagName("td")).stream().map(WebElement::getText).toList();

      //find all times when some temperature was/will be recorded
      WebElement timeBlock = driver.findElement(By.xpath("//*[@id=\"bd"+dayIdInCode+"c\"]/div[1]/div[2]/table/tbody/tr[1]"));
      List<String> times = timeBlock.findElements(By.tagName("td")).stream().map(WebElement::getText).toList();
      times = times.stream().map(x -> x.replaceAll(" ","")).map(x->x.replaceAll(":", ".")).toList();

      StringBuilder result = new StringBuilder();
      for (int i = 0; i < temperatures.size(); i++) {
         result.append(times.get(i)).append(" : ").append(temperatures.get(i)).append("\n");
      }
      result.insert(0, city+", "+date+"\n");
      return result.toString();
   }
   public static boolean isDayIdCorrect(int dayID) {
      if (dayID < 0 || dayID > 6) {
         return false;
      }
      return true;
   }
}