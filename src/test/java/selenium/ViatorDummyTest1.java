package selenium;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ViatorDummyTest1 {
    @Test
    public void test1() {
        String result = System.getProperty("result");
        Assert.assertTrue("pass".equals(result));
    }
}
