
import java.util.Random;
import org.json.simple.Json2T;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public class ExampleBuildJson2T {

    public static void main(String[] args) {
        Json2T jsonMe = Json2T.cre().putObj(
                "firstName", "Thien",
                "lastName", "Pham",
                "andress", Json2T.cre().putObj(
                        "stressAndress", "La Mat",
                        "district", "Hung Yen",
                        "city", "Hung Yen",
                        "state", 0,
                        "poscalCode", 100000
                ),
                "age", 25,
                "phoneNumbers", Json2T.cre().putArr(
                        Json2T.cre().putObj(
                                "type", "home",
                                "number", "0941661235" 
                        ),
                        Json2T.cre().putObj(
                                "type", "mobile",
                                "number", "0941661235"
                        )                        
                ),
                "sex", "male",
                "salary", 1200.0,
                "workDone", Json2T.cre().putArr(
                        "dev",
                        "tester",
                        "graber",
                        "designer"
                )
        );
        System.out.println(jsonMe.toStr());
        
        
        ///
        Object[] o = new Object[100];
        for(int i=0; i<o.length; i++){
            o[i] = new Random().nextInt(100);
        }
        Json2T json = Json2T.cre().putObj(
                "numLength", o.length,
                "numRand", Json2T.cre().putArr(o)
        );
        System.out.println(json.toStr());
        
        
        ///
    }
}
