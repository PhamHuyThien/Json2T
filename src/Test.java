
import java.util.Arrays;
import json2t.Json2T;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Test {
    public static void main(String[] args) {
        String regex = "^((\\.\\w+(\\[\\d+\\])*)|((\\[\\d+\\])*))+$";
        String[] matchs = Json2T.stringRegex(regex, ".a");
        System.out.println(Arrays.toString(matchs));
    }
}
