package json2t;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/*
    @Name       :   Json2T
    @Author     :   ThienDepTraii
    @Version    :   1.0.4
    @Library    :   JsonSimple V1.1.1
 */

public class Json2T {

    private final Object obj;

    //
    public static Json2T parse(String json) {
        return new Json2T(json);
    }

    public static Json2T parse(Object object) {
        return new Json2T(object);
    }

    //
    public Json2T(Object object) {
        this.obj = object;
    }

    public Json2T(String json) {
        obj = JSONValue.parse(json);
    }

    //
    public Json2T k(String key) {
        JSONObject jsonObj = toJsonObject(this.obj);
        return new Json2T(jsonObj != null ? jsonObj.get(key) : null);
    }

    public Json2T i(int index) {
        JSONArray jsonArray = toJsonArray(this.obj);
        return new Json2T(jsonArray != null ? jsonArray.get(index) : null);
    }

    public Json2T q(String query) {
        final Object objectNull = null;
        Object objTmp = this.obj;
        //đầu vào hợp lệ
        if (objTmp == null) {
            return this;
        }
        //query hợp lệ
        if (stringRegex("^((\\.\\w+(\\[\\d+\\])*)|((\\[\\d+\\])*))+$", query) == null) {
            return new Json2T(objectNull);
        }
        String[] querys = query.split("\\.");
        for (String q : querys) {
            if (q.equals("")) {
                continue;
            }
            if (q.contains("[")) { // of array
                if (!q.startsWith("[")) { //có name thì truy cập
                    String[] names = stringRegex("^(\\w+)\\[", q); // lấy tên
                    String name = names[0].replace("[", "");
                    objTmp = toJsonObject(objTmp).get(name); //đọc object xong mấy lấy index
                }
                String[] strIndexs = stringRegex("\\[(\\d+)\\]+", q);
                for (String strIndex : strIndexs) {
                    int index = Integer.parseInt(strIndex.substring(1, strIndex.length()-1));
                    JSONArray jsonArrayTmp = toJsonArray(objTmp);
                    objTmp = jsonArrayTmp != null ? jsonArrayTmp.get(index) : null;
                }
            } else { //of object
                JSONObject jsonObjectTmp = toJsonObject(objTmp);
                objTmp = jsonObjectTmp != null ? jsonObjectTmp.get(q) : null;
            }
        }
        return new Json2T(objTmp);
    }

    //
    public Json2T[][] toPairObjs() {
        JSONObject jsonObject = toJsonObject(this.obj);
        if (jsonObject == null) {
            return null;
        }
        Json2T[][] jsonSimpleses = new Json2T[jsonObject.size()][2];
        Iterator iterator = jsonObject.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            jsonSimpleses[i][0] = new Json2T(pair.getKey());
            jsonSimpleses[i++][1] = new Json2T(pair.getValue());
        }
        return jsonSimpleses;
    }

    public String[] toKeys() {
        Json2T[][] json2Tses = toPairObjs();
        if (json2Tses == null) {
            return null;
        }
        String[] keys = new String[json2Tses.length];
        int i = 0;
        for (Json2T[] json2T : json2Tses) {
            keys[i++] = json2T[0].toStr();
        }
        return keys;
    }

    public Json2T[] toValues() {
        Json2T[][] json2Tses = toPairObjs();
        if (json2Tses == null) {
            return null;
        }
        Json2T[] values = new Json2T[json2Tses.length];
        int i = 0;
        for (Json2T[] json2T : json2Tses) {
            values[i++] = json2T[1];
        }
        return values;
    }

    //
    public Json2T[] toObjs() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        Json2T[] jsonSimples = new Json2T[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonSimples[i] = new Json2T(jsonArray.get(i));
        }
        return jsonSimples;
    }

    //
    public String[] toStrs() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        String[] strings = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            strings[i] = jsonArray.get(i).toString();
        }
        return strings;
    }

    public char[] toChars() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        char[] chars = new char[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            char charr = '_';
            try {
                charr = jsonArray.get(i).toString().toCharArray()[0];
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            chars[i] = charr;
        }
        return chars;
    }

    public int[] toInts() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        int[] ints = new int[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            int intt = -1;
            try {
                intt = Integer.parseInt(jsonArray.get(i).toString());
            } catch (NumberFormatException e) {
            }
            ints[i] = intt;
        }
        return ints;
    }

    public long[] toLongs() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        long[] longs = new long[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            long longg = -1;
            try {
                longg = Long.parseLong(jsonArray.get(i).toString());
            } catch (NumberFormatException e) {
            }
            longs[i] = longg;
        }
        return longs;
    }

    public double[] toDoubles() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        double[] doubles = new double[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            double doublee = -1;
            try {
                doublee = Double.parseDouble(jsonArray.get(i).toString());
            } catch (NumberFormatException e) {
            }
            doubles[i] = doublee;
        }
        return doubles;
    }

    public float[] toFloats() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        float[] floats = new float[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            float floatt = -1;
            try {
                floatt = Float.parseFloat(jsonArray.get(i).toString());
            } catch (NumberFormatException e) {
            }
            floats[i] = floatt;
        }
        return floats;
    }

    //    
    public Object toObj() {
        return this.obj;
    }

    public String toStr() {
        return this.obj == null ? null : this.obj.toString();
    }

    public char toChar() {
        try {
            return this.obj.toString().toCharArray()[0];
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return '_';
        }
    }

    public int toInt() {
        try {
            return Integer.parseInt(this.obj.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    public long toLong() {
        try {
            return Long.parseLong(this.obj.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    public double toDouble() {
        try {
            return Double.parseDouble(this.obj.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    public float toFloat() {
        try {
            return Float.parseFloat(this.obj.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }
    }

    public int length() {
        int lengthObject = isInstanceOfJsonObject(this.obj) ? toJsonObject(this.obj).size() : -1;
        int lengthArray = isInstanceOfJsonArray(this.obj) ? toJsonArray(this.obj).size() : -1;
        return lengthArray > lengthObject ? lengthArray : lengthObject;
    }

    //
    private static JSONObject toJsonObject(Object object) {
        return isInstanceOfJsonObject(object) ? (JSONObject) object : null;
    }

    private static JSONArray toJsonArray(Object object) {
        return isInstanceOfJsonArray(object) ? (JSONArray) object : null;
    }

    //
    private static boolean isInstanceOfJsonObject(Object object) {
        return object == null ? false : object instanceof JSONObject;
    }

    private static boolean isInstanceOfJsonArray(Object object) {
        return object == null ? false : object instanceof JSONArray;
    }

    //
    private static String[] stringRegex(String regex, String input) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);

        ArrayList<String> alMatch = new ArrayList<>();
        while (m.find()) {
            alMatch.add(m.group());
        }
        String[] matchs = new String[alMatch.size()];
        for (int i = 0; i < matchs.length; i++) {
            matchs[i] = alMatch.get(i);
        }
        return matchs.length == 0 ? null : matchs;
    }
}
