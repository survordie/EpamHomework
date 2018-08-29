package com.epam.homework.SquareArea;

import com.epam.homework.SquareArea.Square;

public class SquareArea {
//	public static void main(String[] args){
//		Square sq1 = new Square();
//		Point a = new Point(0,0);
//		Point b = new Point (3,3);
//		Square sq2 = new Square(a, b);
//
//		System.out.println(sq1);
//		System.out.println("Area of rectangle is " + Area(sq1));
//		//		Square.Normalization(sq1);
//
//		System.out.println(sq2);
//		System.out.println("Area of rectangle is " + Area(sq2));
//		//		Square.Normalization(sq2);
//
//		System.out.println("Cross area is: " + CrossArea(sq1,sq2));
//
//		//sq1 = new Square(new Point(0,3),new Point(2,1));
//		sq1 = new Square(new Point(-3,-3),new Point(-4,-4));
//		a = new Point(1,2);
//		b = new Point(3,0);
//		sq2 = new Square(a,b);
//
//		System.out.println("Cross area is: " + CrossArea(sq1,sq2));
//	}
	
	public static double Area(Square sq){
		double result = 0;
		double sideA = 0;
		double sideB = 0;
		
		sideA = sq.GetA().GetX() - sq.GetB().GetX();
		sideB = sq.GetA().GetY() - sq.GetB().GetY();
		result = Math.abs(sideA * sideB);
		
		return result;
	}
	
	public static double CrossArea(Square sq1, Square sq2){
		
		Square.Normalization(sq1);
		Square.Normalization(sq2);
		
		//coords for square1
		double square1X1 = sq1.GetA().GetX();
		double square1X2 = sq1.GetB().GetX();
		double square1Y1 = sq1.GetB().GetY();
		double square1Y2 = sq1.GetA().GetY();
		
		//coords for square2
		double square2X1 = sq2.GetA().GetX();
		double square2X2 = sq2.GetB().GetX();
		double square2Y1 = sq2.GetB().GetY();
		double square2Y2 = sq2.GetA().GetY();
		
		//System.out.println(square1X1 + " " + square1X2 + " " + square2X1 + " " + square2X2);
		
		//Check for a crossing
		if(((square2X1 < square1X1 && square2X1 < square1X2) && (square2X2 < square1X1 && square2X2 < square1X2)) || ((square2X1 > square1X1 && square2X1 > square1X2) && 
		(square2X2 > square1X1 && square2X2 > square1X2))){
			System.out.println("There is no crossing.");
			return 0;
		}
		if(((square2Y1 < square1Y1 && square2Y1 < square1Y2) && (square2Y2 < square1Y1 && square2Y2 < square1Y2)) || ((square2Y1 > square1Y1 && square2Y1 > square1Y2) &&		(square2Y2 > square1Y1 && square2Y2 > square1Y2))){
			System.out.println("There is no crossing.");
			return 0;
		}
		
		double result = 0;
		double sideA = Math.min(square1X2, square2X2) - Math.max(square1X1, square2X1);
		double sideB = Math.min(square1Y2, square2Y2) - Math.max(square1Y1, square2Y1);
		result = Math.abs(sideA * sideB);
		
		return result;
	}
}