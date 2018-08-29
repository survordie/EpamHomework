package com.epam.homework.Spiral;

import org.junit.Test;

import static com.epam.homework.Spiral.SpiralRead.showArrayBySpiral;

public class SpiralReadTest {

    @Test
    public void test(){
        int[][] arrX = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}};
        int[][] arrY = {{1,2,3,4},{5,6,7,8},{9,10,11,12}, {13,14,15,16}};

        showArrayBySpiral(arrX);
        System.out.println("--------------");
        showArrayBySpiral(arrY);
    }
}
