package com.dany.bustracker;

import com.google.transit.realtime.GtfsRealtime;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");

        String url = "https://webapps.regionofwaterloo.ca/api/grt-routes/api/vehiclepositions/1";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            byte[] body = response.body().bytes();

            // Parse GTFS-Realtime Feed
            GtfsRealtime.FeedMessage feed = GtfsRealtime.FeedMessage.parseFrom(body);

            // Print vehicle positions
            for (GtfsRealtime.FeedEntity entity : feed.getEntityList()) {
                if (entity.hasVehicle()) {
                    GtfsRealtime.VehiclePosition vp = entity.getVehicle();
                    System.out.println("Vehicle ID: " + vp.getVehicle().getId());
                    if (vp.hasPosition()) {
                        System.out.println("  Lat: " + vp.getPosition().getLatitude());
                        System.out.println("  Lon: " + vp.getPosition().getLongitude());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}