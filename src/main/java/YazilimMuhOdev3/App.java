/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package YazilimMuhOdev3;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;
import static spark.Spark.port;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// app
public class App {
    public String getGreeting() {
        return "Exam Calculate";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        Logger logger = LogManager.getLogger(App.class);
        logger.error("Error App");

        int port = Integer.parseInt(System.getenv("PORT"));
        port(port);
        logger.error("port number" + port);

        get("/compute",
                (rq, rs) -> {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("result", "not computed yet!");
                    return new ModelAndView(map, "compute.mustache");
                },
                new MustacheTemplateEngine()

        );

        post("/compute", (req, res) -> {

            String vize_not = req.queryParams("vize_not");
            java.util.Scanner vize_not_sc = new java.util.Scanner(vize_not);
            vize_not_sc.useDelimiter("[;\r\n]+");

            java.util.ArrayList<Integer> vize_not_list = new java.util.ArrayList<>();
            while (vize_not_sc.hasNext()) {
                int value = Integer.parseInt(vize_not_sc.next().replaceAll("\\s", ""));
                vize_not_list.add(value);
            }

            vize_not_sc.close();

            int final_not = Integer.parseInt(req.queryParams("final_not"));
            int proje_not = Integer.parseInt(req.queryParams("proje_not"));
            int gecme_not = Integer.parseInt(req.queryParams("gecme_not"));

            Boolean result = App.calculateExam(vize_not_list, final_not, proje_not, gecme_not);
            Map<String, Boolean> map = new HashMap<String, Boolean>();
            map.put("result", result);
            return new ModelAndView(map, "compute.mustache");

        },
                new MustacheTemplateEngine());

    }

    public static boolean calculateExam(ArrayList<Integer> vize_not_list, int final_not, int proje_not, int gecme_not) {
        int list_size = vize_not_list.size();
        int toplam = 0;
        double not_ortalamasi = 0;
        double vize_yuzde = 0.40, final_yuzde = 0.30, proje_yuzde = 0.30;
        if (vize_not_list.size() > 0) {
            for (int i = 0; i < list_size; i++) {
                toplam += vize_not_list.get(i);
            }
        } else {
            return false;
        }
        not_ortalamasi = ((toplam / list_size) * vize_yuzde) + (final_not * final_yuzde) + (proje_not * proje_yuzde);
        if (not_ortalamasi >= gecme_not) {
            return true;
        }
        return false;
    }
}