package com.zillowconnect.scraper;

import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class WebScraper 
{
    Document doc;
    public WebScraper(String url) {
        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String get_address() {
        try {
            return doc.select("head title").text();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Elements get_units() {
        try {
            HashMap<String, int[]> units_map = new HashMap<String, int[]>();
            Elements sectionContainer = doc.select("div.sectionContainer");
            Elements units = sectionContainer.select("div.priceBedRangeInfo");
            Elements features = doc.select("#amenitiesSection ul.combinedAmenitiesList li.specInfo");
            
            for (Element unit : units) {
                Elements num_rent = unit.select("h3.ModelLabel");
                String key = num_rent.select("span.modelName").text();
                int[] val = new int[11];

                Elements details = unit.select("h4.detailsLabel > span.detailsTextWrapper");
                details = details.get(0).select("span:not([^class])");
                for (int i = 0; i < details.size(); i++) {
                    String value = details.get(i).text();
                    if (i == 0 && value.equals("Studio")) {
                        val[0] = 1;
                    } else if (i == 2) {
                        String[] parts = value.split(" ");
                        val[3] = Integer.parseInt(parts[0].replace(",", ""));
                    } else {
                        val[i] = value.charAt(0) - '0';
                    }
                }

                String rent_label = num_rent.select("span.rentLabel").text();
                int rent = Integer.parseInt(rent_label.substring(1).replace(",", ""));
                val[2] = rent;
                units_map.put(key, val);
            }
            
            return null;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public print_units(Map<String, int[]> units) {
        for (Map.Entry<String, int[]> entry : units.entrySet()) {
            String key = entry.getKey();
            int [] value = entry.getValue();
            System.out.println(key);
            System.out.println(Arrays.toString(value));
        }
    }

    public static void main(String[] args) {
        String url = "https://www.apartments.com/582-dorchester-st-boston-ma/4q7zlhh/";
        WebScraper ws = new WebScraper(url);
        System.out.println(ws.get_units());
    }
}
