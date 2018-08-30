package com.epam.homework.SquareArea;

/**
 * Class for describing square object
 */
public class Square {
    private Point a;
    private Point b;

    public Square() {
        this.a = new Point();
        this.b = new Point(1, 1);
    }

    public Square(Point p1, Point p2) {
        this.a = p1;
        this.b = p2;
    }

    public void SetA(Point point) {
        this.a = point;
    }

    public void SetB(Point point) {
        this.b = point;
    }

    public Point GetA() {
        return this.a;
    }

    public Point GetB() {
        return this.b;
    }

    @Override
    public String toString() {
        return "Point A: " + this.a + "; Point B: " + this.b;
    }

    //function for normalizing points(Top left point and right bottom point)
    public static void Normalization(Square sq) {

        Point a = sq.GetA();
        Point b = sq.GetB();
        double min = a.GetX();
        double max = b.GetX();

        if (min > max) {
            a.SetX(max);
            b.SetX(min);
        }
        min = a.GetY();
        max = b.GetY();
        if (min < max) {
            a.SetY(max);
            b.SetY(min);
        }

    }
}