package json2t;

import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/*
    @Name       :   Json2T
    @Author     :   ThienDepTraii
    @Version    :   1.0.0
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
        json = json.replaceAll("\\n\\b\\t\\s", "");
        obj = JSONValue.parse(json);
    }

    //
    public Json2T key(String key) {
        JSONObject jsonObj = isInstanceOfJsonObject() ? (JSONObject) this.obj : null;
        return new Json2T(jsonObj != null ? jsonObj.get(key) : null);
    }

    public Json2T index(int index) {
        JSONArray jsonArray = isInstanceOfJsonArray() ? (JSONArray) this.obj : null;
        return new Json2T(jsonArray != null ? jsonArray.get(index) : null);
    }

    //
    public Json2T[][] toPairObjs() {
        JSONObject jsonObject = isInstanceOfJsonObject() ? (JSONObject) this.obj : null;
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

    //
    public Json2T[] toObjs() {
        JSONArray jsonArray = isInstanceOfJsonArray() ? (JSONArray) obj : null;
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
        JSONArray jsonArray = isInstanceOfJsonArray() ? (JSONArray) obj : null;
        if (jsonArray == null) {
            return null;
        }
        String[] strings = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            strings[i] = jsonArray.get(i).toString();
        }
        return strings;
    }

    public int[] toInts() {
        JSONArray jsonArray = isInstanceOfJsonObject() ? (JSONArray) obj : null;
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

    public double[] toDoubles() {
        JSONArray jsonArray = isInstanceOfJsonObject() ? (JSONArray) obj : null;
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

    //
    public String toJsonStr() {
        return isInstanceOfJsonObject() ? ((JSONObject) obj).toJSONString() : obj.toString();
    }

    public String toStr() {
        if (this.obj == null) {
            return null;
        }
        return this.obj.toString();
    }

    public Object toObj() {
        return this.obj;
    }

    public int toInt() {
        try {
            return Integer.parseInt(this.obj.toString());
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

    //
    private boolean isInstanceOfJsonObject() {
        if (this.obj == null) {
            return false;
        }
        return this.obj instanceof JSONObject;
    }

    private boolean isInstanceOfJsonArray() {
        if (this.obj == null) {
            return false;
        }
        return this.obj instanceof JSONArray;
    }
}
