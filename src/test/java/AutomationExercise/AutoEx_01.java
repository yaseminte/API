package AutomationExercise;

import base_urls.AutomationExBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class AutoEx_01 extends AutomationExBaseUrl {
    /*
 Given
     https://automationexercise.com/api/productsList
 When
     User sends a GET Request to the url
 Then
     HTTP Status Code should be 200
 And
     Content Type should be "text/html; charset=utf-8"
 And
     Status Line should be HTTP/1.1 200 OK
 And
      There must be 12 Women, 9 Men, 13 Kids usertype in products
   */

    @Test
    public void name() {
        //Set the Url
        spec.pathParam("first", "productsList");

        Response response = given().spec(spec).when().get("/{first}");
        //response.prettyPrint();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(response.getStatusCode(),200, "status kod hatali");
        softAssert.assertEquals(response.getContentType(),"text/html; charset=utf-8", "content type hatali");
        softAssert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK", "status line hatali");
        softAssert.assertAll();

        JsonPath json = response.jsonPath();
       // json.prettyPrint();

        List<String> userTypeList = json.getList("products.category.usertype.usertype");
        System.out.println(userTypeList.toString());

        int womenSayisi =0;
        int menSayisi =0;
        int kidsSayisi =0;

        for (int i = 0; i < userTypeList.size(); i++) {
            if (userTypeList.get(i).equals("Women")) womenSayisi++;
            if (userTypeList.get(i).equals("Men")) menSayisi++;
            if (userTypeList.get(i).equals("Kids")) kidsSayisi++;
            }
        System.out.println(womenSayisi);
        System.out.println(menSayisi);
        System.out.println(kidsSayisi);

        softAssert.assertEquals(12,womenSayisi);
        softAssert.assertEquals(9,menSayisi);
        softAssert.assertEquals(13,kidsSayisi);
        softAssert.assertAll();

        //List<String> usertype = jsonPath.getList("products.category.usertype.usertype");
        assertEquals(12, userTypeList.stream().filter(t -> t.equals("Women")).count());
        assertEquals(9, userTypeList.stream().filter(t -> t.equals("Men")).count());
        assertEquals(13, userTypeList.stream().filter(t -> t.equals("Kids")).count());
    }

}



