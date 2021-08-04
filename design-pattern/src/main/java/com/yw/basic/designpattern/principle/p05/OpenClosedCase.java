package com.yw.basic.designpattern.principle.p05;

/**
 * 开闭原则
 *
 * @author yangwei
 */
public class OpenClosedCase {
    public static void main(String[] args) {
        GraphicEditor graphicEditor = new GraphicEditor();
        graphicEditor.drawShape(new Rectangle());
        graphicEditor.drawShape(new Circle());
        graphicEditor.drawShape(new Triangle());
    }
}

//class GraphicEditor {
//    public void drawShape(Shape s) {
//        if (s.type == 1) {
//            drawRectangle(s);
//        } else if (s.type == 2) {
//            drawCircle(s);
//        } else if (s.type == 3) {
//            // 这里需要改动
//            drawTriangle(s);
//        }
//    }
//
//    private void drawRectangle(Shape r) {
//        System.out.println("绘制矩形");
//    }
//
//    private void drawCircle(Shape r) {
//        System.out.println("绘制圆形");
//    }
//    /**
//     * 这里也需要新增方法
//     */
//    private void drawTriangle(Shape r) {
//        System.out.println("绘制三角形");
//    }
//}
//
//class Shape {
//    int type;
//}
//
//class Rectangle extends Shape {
//    Rectangle() {
//        super.type = 1;
//    }
//}
//
//class Circle extends Shape {
//    Circle() {
//        super.type = 2;
//    }
//}
///**
// * 新增画三角形
// */
//class Triangle extends Shape {
//    Triangle() {
//        super.type = 3;
//    }
//}

/**
 * 改进方案：
 */
abstract class Shape {
    int type;

    abstract void draw();
}

class Rectangle extends Shape {
    Rectangle() {
        super.type = 1;
    }

    @Override
    public void draw() {
        System.out.println("绘制矩形");
    }
}

class Circle extends Shape {
    Circle() {
        super.type = 2;
    }

    @Override
    public void draw() {
        System.out.println("绘制圆形");
    }
}

class Triangle extends Shape {
    Triangle() {
        super.type = 3;
    }

    @Override
    public void draw() {
        System.out.println("绘制三角形");
    }
}

class GraphicEditor {
    public void drawShape(Shape s) {
        s.draw();
    }
}