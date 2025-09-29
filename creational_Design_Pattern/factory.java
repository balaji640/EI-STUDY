package creational_Design_Pattern;

//Product
interface Shape { void draw(); }

//Concrete Products
class Circle implements Shape {
 public void draw() { System.out.println("Drawing Circle"); }
}
class Square implements Shape {
 public void draw() { System.out.println("Drawing Square"); }
}

//Factory
class ShapeFactory {
 public static Shape getShape(String type) {
     switch(type.toLowerCase()) {
         case "circle": return new Circle();
         case "square": return new Square();
         default: throw new IllegalArgumentException("Unknown shape");
     }
 }
}

//Demo
public class factory {
 public static void main(String[] args) {
     Shape s1 = ShapeFactory.getShape("circle");
     s1.draw();
     Shape s2 = ShapeFactory.getShape("square");
     s2.draw();
 }
}
