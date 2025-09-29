package project_2;
import java.util.*;

//Observer interface
interface Observer {
 void update(String news);
}

//Subject
class NewsAgency {
 private List<Observer> observers = new ArrayList<>();
 
 public void addObserver(Observer o) { 
     observers.add(o); 
 }
 
 public void notifyObservers(String news) {
     for (Observer o : observers) {
         o.update(news);
     }
 }
}

//Concrete Observer
class NewsChannel implements Observer {
 private String name;
 
 public NewsChannel(String name) { 
     this.name = name; 
 }
 
 @Override
 public void update(String news) {
     System.out.println(name + " received update: " + news);
 }
}

//Main Demo class (must match file name)
public class behaviour {
 public static void main(String[] args) {
     NewsAgency agency = new NewsAgency();
     
     agency.addObserver(new NewsChannel("BBC"));
     agency.addObserver(new NewsChannel("CNN"));
     
     agency.notifyObservers("Breaking News: Design Patterns are powerful!");
 }
}
