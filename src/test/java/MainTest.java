import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class MainTest {

    @Test
    public void testBigDec(){
        TreeMap<BigDecimal, BigDecimal> fees = new TreeMap<BigDecimal, BigDecimal>();
        BigDecimal onePointFive = new BigDecimal("1.5");
        BigDecimal two = new BigDecimal("2");
        BigDecimal twoPointFive = new BigDecimal("2.5");
        BigDecimal one = new BigDecimal("1");
        BigDecimal three = new BigDecimal("3");
        BigDecimal threePointFive = new BigDecimal("3.5");
        fees.put(two, three);
        fees.put(three, two);
        fees.put(one, twoPointFive);
        System.out.println("sorted map keys: ");
        for (BigDecimal key: fees.keySet()
             ) {
            System.out.println(key);
        }
        System.out.println("sorted map values: ");
        for (BigDecimal value: fees.values()
             ) {
            System.out.println(value);
        }

        fees.put(new BigDecimal("10"), new BigDecimal("5.00"));
        fees.put(new BigDecimal("5"), new BigDecimal("2.50"));
        fees.put(new BigDecimal("3"), new BigDecimal("2.00"));
        fees.put(new BigDecimal("2"), new BigDecimal("1.50"));
        fees.put(new BigDecimal("1"), new BigDecimal("1.00"));
        fees.put(new BigDecimal("0.5"), new BigDecimal("0.70"));
        fees.put(new BigDecimal("0.2"), new BigDecimal("0.50"));

    }
    @Test
    public void testPostalCodeValidator(){
//        FileReader
    }
}
