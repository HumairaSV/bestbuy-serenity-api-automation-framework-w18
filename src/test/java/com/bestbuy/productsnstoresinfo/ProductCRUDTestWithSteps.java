package com.bestbuy.productsnstoresinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)
public class ProductCRUDTestWithSteps extends TestBase {

    static String name = "Evergreen AA Batteries pack of" + TestUtils.getRandomValue();
    static String type = "HardGood";
    static double price = 7.99;
    static int shipping = 0;
    static String upc = "AZ45" + TestUtils.getRandomValue();
    static String description = "Compatible with all electronic devices supporting AA batteries" + TestUtils.getRandomValue();
    static String manufacturer = "Evergreen";
    static String model = "EN4590AA";
    static String url = "http://www.bestbuy.com/site/evergreen-aa-batteries-6-pack/79900.p";
    static String image = "image unavailable";
    static int productId;

    @Steps
    ProductSteps productSteps;

    @Title("T1-This will create a new product")
    @Test
    public void test001() {
        ValidatableResponse response = productSteps.createProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        response.log().all().statusCode(201);
        productId = response.log().all().extract().path("id");
        System.out.println(productId);
    }

    @Title("T2-Verify if the product was added to the list")
    @Test
    public void test002() {
        HashMap<String, Object> productMap = productSteps.extractProductByID(productId);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("T3-Update the product details and verify the updated information")
    @Test
    public void test003() {
        name = name +"_updated";
        productSteps.updateProduct(name,type,price,shipping,upc,description,manufacturer,model,url,image,productId);
        //verifying if the information has been updated
        HashMap<String, Object> productMap = productSteps.extractProductByID(productId);
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("T4-Delete the product and verify if the product has been deleted")
    @Test
    public void test004(){
        productSteps.deletingProductById(productId).statusCode(200);

        productSteps.getProductById(productId).statusCode(500);
    }

}



