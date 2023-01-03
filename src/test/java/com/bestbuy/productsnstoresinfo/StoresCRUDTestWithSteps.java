package com.bestbuy.productsnstoresinfo;

import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoresCRUDTestWithSteps {

    static String name = "Store_1" + TestUtils.getRandomValue();
    static String type = "Type_1" + TestUtils.getRandomValue();
    static String address = "123 Stoneridge";
    static String address2 = "Bay Lane";
    static String city = "Los Angeles";
    static String state = "California";
    static String zip = "90007";
    static double lat = 34.052235;
    static double lng = -118.243683;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static HashMap<String, Object> services = new HashMap<>();
    static int storeId;

    @Steps
    StoreSteps storeSteps;

    @Title("T1-This will create a new store")
    @Test
    public void test001(){
        ValidatableResponse response = storeSteps.createStore(name,type,address,address2,city,state,zip,lat,lng,hours,services).statusCode(201);
        storeId = response.log().all().extract().path("id");
        System.out.println(storeId);
    }

    @Title("T2-Verify if the store was added to the list")
    @Test
    public void test002(){
        HashMap<String, Object> storeMap = storeSteps.extractStoreByID(storeId);
        Assert.assertThat(storeMap, hasValue(name));
    }

    @Title("T3-Update the store details and verify the updated information")
    @Test
    public void test003(){
        name = name +"_updated";
        storeSteps.updateStore(name,type,address,address2,city,state,zip,lat,lng,hours,services,storeId);
        //verifying if the information has been updated
        HashMap<String, Object> productMap = storeSteps.extractStoreByID(storeId);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("T4-Delete the store and verify if the store has been deleted")
    @Test
    public void test004(){
        storeSteps.deletingStoreById(storeId).statusCode(200);

        storeSteps.getStoreById(storeId).statusCode(500);
    }

}
