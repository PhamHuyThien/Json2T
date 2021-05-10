
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.thiendz.json.JJson;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class test {

    public static void main(String[] args) {
        Map<Object, Object> m1Phone = new HashMap<>();
        m1Phone.put("type", "home");
        m1Phone.put("number", "0941661235");
        Map<Object, Object> m2Phone = new HashMap<>();
        m2Phone.put("type", "fax");
        m2Phone.put("number", "0435508028");
        
        List<Object> lphone = new ArrayList<>();
        lphone.add(m1Phone);
        lphone.add(m2Phone);
        
        Map<Object, Object> mGirlFriend = new HashMap<>();
        mGirlFriend.put("name", "Thom");
        mGirlFriend.put("age", 18);
        
        Map<Object, Object> mJson = new HashMap<>();
        mJson.put("firstName", "Thien");
        mJson.put("lastName", "Pham");
        mJson.put("phoneNumbers", lphone);
        mJson.put("girlFriend", mGirlFriend);
        mJson.put("age", 20);
        mJson.put("sex", "male");
        mJson.put("salary", 1200.1);
        
        JJson jjson = JJson.cre().put(mJson);
        System.out.println(jjson);
    }
}
