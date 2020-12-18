/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallel;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eslam Alaa
 */
public class api_manger {

    URL url;

    public api_manger(URL url) {
        this.url = url;
    }

    private String get_data() {
        String inline = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                Scanner scanner = new Scanner(url.openStream());
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }
                scanner.close();
//                System.out.println(inline);
            }
        } catch (Exception ex) {
            Logger.getLogger(api_manger.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inline;
    }

    public ArrayList<data> json_to_object() {
        String inline = get_data();
        ArrayList<data> array_of_articles = new ArrayList<>();
        JSONObject obj = new JSONObject(inline);
        JSONArray arr = obj.getJSONArray("articles");
        for (int i = 0; i < arr.length(); i++) {
            String title = arr.getJSONObject(i).get("title").toString();
            String content = arr.getJSONObject(i).get("content").toString();
            if (!title.equals("null") && !content.equals("null")) {
                data article = new data();
                article.title = title;
                article.content = content;
                array_of_articles.add(article);
            }
        }
        return array_of_articles;
    }

    public String display_json() {
        String result = "";
        ArrayList<data> array_of_articles = json_to_object();
        for (data article : array_of_articles) {
            result += ("title: " + article.title + "\n\n" + article.content + "\n----------------------------------------------\n");
        }
        return result;
    }
}
