package com.epam.homework.Spiral;

public class SpiralRead{

//	public static void main(String[] args){
//
//
//		int[][] arrX = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}};
//		int[][] arrY = {{1,2,3,4},{5,6,7,8},{9,10,11,12}, {13,14,15,16}};
//
//		showArrayBySpiral(arrX);
//		System.out.println("--------------");
//		showArrayBySpiral(arrY);
//	}
	
	public static void showArrayBySpiral(int[][] arr){
		int numberTurn, offsetX, offsetY, lengthX, lengthY, sell, numberOfSell;
		
		numberTurn = 0;
		numberOfSell = 0;
		lengthX = arr[0].length;
		lengthY = arr.length;
		
		//System.out.println(lengthX + lengthY);
		while (numberTurn < lengthX * lengthY){
			
			//Increase turn
			numberTurn++;
			
			//Output a top side
			for(sell = numberTurn - 1; sell < lengthX - numberTurn + 1; sell++){
				System.out.println(arr[numberTurn - 1][sell]);
				numberOfSell++;
			}
			
			//Output a right side
			for(sell = numberTurn; sell < lengthY - numberTurn + 1; sell++){
				System.out.println(arr[sell][lengthX - numberTurn]);
				numberOfSell++;
			}
			
			//Output a bottom side
			for(sell = lengthY - numberTurn - 1; sell >= numberTurn - 1; sell--){
				System.out.println(arr[lengthY - numberTurn][sell]);
				numberOfSell++;
			}
			
			//Outpur a left side
			for(sell = lengthY - numberTurn - 1; sell >= numberTurn; sell--){
				System.out.println(arr[sell][numberTurn - 1]);
				numberOfSell++;
			}
		}
	}
}