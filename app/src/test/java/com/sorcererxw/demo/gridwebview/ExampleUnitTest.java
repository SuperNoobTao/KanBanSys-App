package com.sorcererxw.demo.gridwebview;

import com.google.gson.Gson;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetUrls() throws Exception {
        WebClient client = WebClient.getInstance();

        client.getUrls("111")
                .subscribe(webData -> {
                    Gson gson = new Gson();
                    System.out.println(gson.toJson(webData));
                });
    }

    @Test
    public void testMqtt() throws Exception {
        MqttManager mqttManager = new MqttManager();
        mqttManager.connect();
    }
}