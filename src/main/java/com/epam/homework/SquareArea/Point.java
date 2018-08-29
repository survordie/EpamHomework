package com.epam.homework.SquareArea;

public class Point{
	private double x;
	private double y;
	
	public Point(){
		this.x = 0;
		this.y = 0;
	}
	
	public Point(double x, double y){		
			this.x = x;
			this.y = y;		
	}

	public void SetX(double x){		
		this.x = x;
	}
	
	public void SetY(double y){
		this.y = y;
	}
	
	public double GetX(){
		return this.x;
	}
	
	public double GetY(){
		return this.y;
	}
	
	@Override
	public String toString(){
		return "X: " + this.x + ", Y: " + this.y;
	}
}