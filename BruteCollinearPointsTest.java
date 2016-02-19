package com.sarahkim;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;


/**
 * Created by sarahbkim on 2/19/16.
 */
public class BruteCollinearPointsTest {
    private BruteCollinearPoints b;
    private Point[] points = new Point[5];

    @Before
    public void setUp() throws Exception {
        int i=0;
        Random ran = new Random();

        while(i<5) {
            int x = (ran.nextInt(65536)-32768);
            int y = (ran.nextInt(65536)-32768);
            points[i] = new Point(x, y);
            i++;
        }
        b = new BruteCollinearPoints(points);
    }
    @Test
    public void initTest() throws Exception {
        System.out.println(b.slopes);
    }
}