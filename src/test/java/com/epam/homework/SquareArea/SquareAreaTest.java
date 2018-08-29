package com.epam.homework.SquareArea;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SquareAreaTest {

    @Test
    public void squareTest(){

        Square sq1 = new Square();

        assertEquals(1.0, SquareArea.Area(sq1), 0.1);

        Point a = new Point(0,0);
        Point b = new Point (3,3);
        Square sq2 = new Square(a, b);

        assertEquals(9.0, SquareArea.Area(sq2), 0.1);

        assertEquals(1.0, SquareArea.CrossArea(sq1,sq2), 0.1);

        //sq1 = new Square(new Point(0,3),new Point(2,1));
        sq1 = new Square(new Point(-3,-3),new Point(-4,-4));
        a = new Point(1,2);
        b = new Point(3,0);
        sq2 = new Square(a,b);

        assertEquals(0.0, SquareArea.CrossArea(sq1,sq2), 0.1);
    }
}
