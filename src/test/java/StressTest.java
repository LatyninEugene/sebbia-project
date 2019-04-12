import controlers.JDBCUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

public class StressTest extends Thread {

    TestServlets ts = new TestServlets();
    static ArrayList<StressTest> list = new ArrayList<>();
    @Test
    public void testLocalHost(){
        for (int i = 0; i < 100; i++) {
            StressTest t = new StressTest();
            list.add(t);
            t.start();
        }
        while (list.size() != 0){
            try {
                for (StressTest t : list) {
                    if(!t.isAlive()){list.remove(t); }
                }
            }catch (Exception e){}
        }
    }


    @Override
    public void run() {
        ts.checkValid();
    }
}
