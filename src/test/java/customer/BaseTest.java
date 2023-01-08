package customer;

import data.TestData;
import helper.CommonHelper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;

public class BaseTest implements TestData {
    CommonHelper helper = CommonHelper.getInstance();
    SoftAssertions softly = new SoftAssertions();

    @AfterEach
    void softAssert(){
        softly.assertAll();
    }
}
