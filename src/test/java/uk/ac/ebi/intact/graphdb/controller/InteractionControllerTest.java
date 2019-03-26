package uk.ac.ebi.intact.graphdb.controller;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anjali on 26/03/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class InteractionControllerTest {

    @Value("${graph.db.rest.service.url}")
    private String graphDBServerUrl;

    @Before
    public void checkIfServerIsUp() throws Exception {
        boolean condition = true;
        try {

            URL siteURL = new URL(graphDBServerUrl);
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(3000);
            connection.connect();
            int code = connection.getResponseCode();
            Assert.assertEquals("Graph DB Web Service response code is incorrect", 200, code);
        } catch (ConnectException e) {
            System.err.println("Graph DB Web Service Seems to be Down, please start it");
            condition = false;
        } catch (Exception e) {
            System.err.println("Exception while connecting to graph db server");
            condition = false;
        }
        Assume.assumeTrue(condition);
    }

    @Test
    public void exportInteraction() {

        List<String> interactionAcsToTest = new ArrayList<>();
        interactionAcsToTest.add("EBI-10052707");
        interactionAcsToTest.add("EBI-10048599");
        interactionAcsToTest.add("EBI-10049314");
        interactionAcsToTest.add("EBI-10054743");
        interactionAcsToTest.add("EBI-1005174");
        interactionAcsToTest.add("EBI-1004945");
        for (String interactionAc : interactionAcsToTest) {
            try {
                String jsonMimeType = "application/json";
                HttpUriRequest request = new HttpGet(graphDBServerUrl + "/graph/interaction/export?ac=" + interactionAc);
                HttpResponse response = HttpClientBuilder.create().build().execute(request);

                Assert.assertNotNull("Response is null", response);
                Assert.assertNotNull("Response status line is null", response.getStatusLine());
                Assert.assertEquals("Response Code is wrong", 200, response.getStatusLine().getStatusCode());

                HttpEntity httpEntity = response.getEntity();
                String mimeType = ContentType.getOrDefault(httpEntity).getMimeType();

                Assert.assertEquals(jsonMimeType, mimeType);

                String actualJsonString = EntityUtils.toString(httpEntity);

                JSONParser parser = new JSONParser();
                //Use JSONObject for simple JSON and JSONArray for array of JSON.
                JSONObject data = (JSONObject) parser.parse(
                        new FileReader("./src/test/resources/json/" + interactionAc + "_mi-json.json"));//path to the JSON file.
                String expectedJsonString = data.toJSONString();

                JSONAssert.assertEquals(
                        expectedJsonString, actualJsonString, JSONCompareMode.LENIENT);

                // use below if you need to ignore fields
            /*JSONAssert.assertEquals(expectedJsonString, actualJsonString,
                    new CustomComparator(JSONCompareMode.LENIENT,
                            new Customization("data.participants.id", (o1, o2) -> true),
                            new Customization("a", (o1, o2) -> true)
                    ));*/

            } catch (IOException e) {
                System.err.println("Exception while connecting to Graph DB Web Service");
                e.printStackTrace();
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
                System.err.println("Exception while compating json");
            } catch (ParseException parseException) {
                parseException.printStackTrace();
                System.err.println("Exception while parsing expectedJsonFile");
            }
        }
    }


}
