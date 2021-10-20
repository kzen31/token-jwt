package backend.controller.forgetpassword;

import backend.config.Config;

import java.util.HashMap;
import java.util.Map;

public class TemplateReqRes {

    public static Config config = new Config();

    public static Map notFound(String obj) {
        Map map = new HashMap();
        map.put(config.getCode(), config.code_notFound);
        map.put(config.getMessage(), obj);
        return map;
    }

    public static Map isRequired(String obj) {
        Map map = new HashMap();
        map.put(config.getCode(), config.codeRequired);
        map.put(config.getMessage(), obj);
        return map;
    }

    public static Map template1(Object obj) {
        Map map = new HashMap();
        try {
            map.put("data", obj);
            map.put(config.getCode(), config.code_sukses);
            map.put(config.getMessage(), config.message_sukses);
            return map;

        } catch (Exception e) {
            System.out.println("eror template1 =" + e);
            map.put(config.getCode(), config.code_server);
            map.put(config.getMessage(), e.getMessage());
            return map;
        }
    }
}
