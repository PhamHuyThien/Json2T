package org.json.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    @Name       :   Json2T
    @Author     :   ThienDepTraii
    @Version    :   1.0.6
    @Library    :   JsonSimple V1.1.1
 */
public class Json2T implements Comparable<Json2T> {

    private Object obj;

    /**
     * parse chuỗi json cần đọc.
     *
     * @param json chuỗi json cần dc parse
     *
     * @return Trả về Json2T
     *
     * @see org.json.simple.Json2T
     *
     * @since 1.6.0
     */
    public static Json2T parse(String json) {
        return new Json2T(json);
    }

    /**
     * parse object json cần đọc.
     *
     * @param object object json cần dc parse lấy từ JSONValue.parse(String
     * json): Object
     *
     * @return Trả về Json2T
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public static Json2T parse(Object object) {
        return new Json2T(object);
    }

    /**
     * khởi tạo 1 Json null sẵn sàng cho việc put array hoặc object.
     *
     * @param
     *
     * @return Trả về Json2T
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public static Json2T cre() {
        return new Json2T();
    }

    //
    public Json2T() {
    }

    public Json2T(Object object) {
        this.obj = object;
    }

    public Json2T(String json) {
        obj = JSONValue.parse(json);
    }

    /**
     * truy cập đến đối tượng trong mảng.
     *
     * @param key chuỗi tên của đối tượng cần truy cập
     *
     * @return Trả về đối tượng Json2T
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T k(String key) {
        JSONObject jsonObj = toJsonObject(this.obj);
        return new Json2T(jsonObj != null ? jsonObj.get(key) : null);
    }

    /**
     * truy cập đến vị trí trong mảng.
     *
     * @param index int index cần lấy trong mảng cần truy cập
     *
     * @return Trả về đối tượng Json2T
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T i(int index) {
        JSONArray jsonArray = toJsonArray(this.obj);
        return new Json2T(jsonArray != null ? jsonArray.get(index) : null);
    }

    /**
     * truy cập đến object như javascript.
     *
     * @param query Chuỗi cần truy cập như object như javascript
     *
     * @return Trả về đối tượng Json2T
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T q(String query) {
        Object objTmp = this.obj;
        //đầu vào hợp lệ
        if (objTmp == null) {
            return this;
        }
        //query hợp lệ
        if (stringRegex("^((\\.\\w+(\\[\\d+\\])*)|((\\[\\d+\\])*))+$", query) == null) {
            return new Json2T("");
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
                    int index = Integer.parseInt(strIndex.replaceAll("\\[|\\]", ""));
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

    /**
     * tạo mảng [] chứ value.
     *
     * @param objs mảng object chứa giá trị cần tạo mảng.
     *
     * @return Trả về đối tượng JSONArray
     * 
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T putArr(Object... objs) {
        JSONArray ja = this.obj == null ? new JSONArray() : (JSONArray) this.obj;
        ja.addAll(Arrays.asList(objs));
        return new Json2T(ja);
    }
    /**
     * tạo mảng {} chứa key: value. Lưu ý cần chú ý số lượng của phần tử mảng
     * đầu vào phải chia hết cho 2 (đảm bảo đủ key và value)
     *
     * @param pair mảng object chứa giá trị cần tạo mảng.
     *
     * @return Trả về đối tượng JSONObject
     * 
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T putObj(Object... pair) {
        if (pair.length % 2 == 0) {
            JSONObject jo = this.obj == null ? new JSONObject() : (JSONObject) this.obj;
            for (int i = 0; i < pair.length; i += 2) {
                jo.put(pair[i], pair[i + 1]);
            }
            return new Json2T(jo);
        }
        return null;
    }

    /**
     * Lấy số nhỏ nhất trong mảng.
     * 
     *@return Trả về đối tượng Json2T
     * 
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T min() {
        float[] fls = toFloats();
        if (fls == null) {
            return new Json2T("");
        }
        Arrays.sort(fls);
        return new Json2T((Object) fls[0]);
    }

    /**
     * Lấy số lớn nhất trong mảng.
     * 
     *@return Trả về đối tượng Json2T
     * 
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T max() {
        float[] fls = toFloats();
        if (fls == null) {
            return new Json2T("");
        }
        Arrays.sort(fls);
        return new Json2T((Object) fls[fls.length - 1]);
    }

    /**
     * Tính tổng trong mảng.
     *
     * @return Trả về đối tượng Json2T
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T sum() {
        float[] fls = toFloats();
        if (fls == null) {
            return new Json2T("");
        }
        return new Json2T((Object) sumFloat(fls));
    }

    /**
     * Tính trung bình cộng của mảng.
     *
     * @return Trả về đối tượng Json2T
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T avg() {
        float[] fls = toFloats();
        if (fls == null) {
            return new Json2T("");
        }
        return new Json2T((Object) (sumFloat(fls) / fls.length));
    }

    /**
     * Sắp xếp mảng tăng dần.
     *
     * @return Trả về đối tượng Json2T
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T sort() {
        Json2T[] json2Ts = toObjs();
        if (json2Ts == null) {
            return new Json2T("");
        }
        Arrays.sort(json2Ts);
        return new Json2T(JSONValue.parse(arraysToString(json2Ts)));
    }

    /**
     * Đảo ngược mảng.
     *
     * @return Trả về đối tượng Json2T
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Json2T reverse() {
        Json2T[] json2Ts = toObjs();
        for (int i = 0; i < json2Ts.length / 2; i++) {
            int idMax = json2Ts.length - 1 - i;
            Json2T json2T = json2Ts[i];
            json2Ts[i] = json2Ts[idMax];
            json2Ts[idMax] = json2T;
        }
        return new Json2T(JSONValue.parse(arraysToString(json2Ts)));
    }

    /**
     * Lấy mảng key và value của một node.
     *
     * @return Trả về mảng đối tượng Json2T[][]
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
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

    /**
     * Lấy mảng tên key trong một node có key và value.
     *
     * @return Trả về mảng key trong node
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public String[] toKeys() {
        Json2T[][] json2Tses = toPairObjs();
        if (json2Tses == null) {
            return null;
        }
        String[] keys = new String[json2Tses.length];
        int i = 0;
        for (Json2T[] json2T : json2Tses) {
            keys[i++] = json2T[0].toString();
        }
        return keys;
    }

    /**
     * Lấy mảng giá trị trong một node có key và value.
     *
     * @return Trả về mảng giá trị Json2T[] trong một node
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
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

    /**
     * Lấy mảng giá trị trong một node.
     *
     * @return Trả về mảng giá trị Json2T[] trong một node
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
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

    /**
     * Lấy mảng chuỗi trong một node.
     *
     * @return Trả về mảng chuỗi trong node
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
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

    /**
     * Lấy mảng kí tự trong một node.
     *
     * @return Trả về mảng kí tự trong node
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public char[] toChars() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        char[] chars = new char[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            char charr = '0';
            try {
                charr = jsonArray.get(i).toString().toCharArray()[0];
            } catch (ArrayIndexOutOfBoundsException e) {
            }
            chars[i] = charr;
        }
        return chars;
    }

    /**
     * Lấy mảng số nguyên trong một node.
     *
     * @return Trả về mảng số thực trong node
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public int[] toInts() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        int[] ints = new int[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            int intt = 0;
            try {
                intt = Integer.parseInt(strToInt(jsonArray.get(i).toString()));
            } catch (NumberFormatException e) {
            }
            ints[i] = intt;
        }
        return ints;
    }

    /**
     * Lấy mảng số nguyên trong một node.
     *
     * @return Trả về mảng số thực trong node
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public long[] toLongs() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        long[] longs = new long[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            long longg = 0;
            try {
                longg = Long.parseLong(strToInt(jsonArray.get(i).toString()));
            } catch (NumberFormatException e) {
            }
            longs[i] = longg;
        }
        return longs;
    }

    /**
     * Lấy mảng số thực trong một node.
     *
     * @return Trả về mảng số thực trong node
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public double[] toDoubles() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        double[] doubles = new double[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            double doublee = 0;
            try {
                doublee = Double.parseDouble(jsonArray.get(i).toString());
            } catch (NumberFormatException e) {
            }
            doubles[i] = doublee;
        }
        return doubles;
    }

    /**
     * Lấy mảng số thực trong một node.
     *
     * @return Trả về mảng số thực trong node
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public float[] toFloats() {
        JSONArray jsonArray = toJsonArray(this.obj);
        if (jsonArray == null) {
            return null;
        }
        float[] floats = new float[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            float floatt = 0;
            try {
                floatt = Float.parseFloat(jsonArray.get(i).toString());
            } catch (NumberFormatException e) {
            }
            floats[i] = floatt;
        }
        return floats;
    }

    /**
     * Lấy đối tượng
     *
     * @return Trả về object
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public Object toObj() {
        return this.obj;
    }

    /**
     * Lấy chuỗi
     *
     * @return Trả về chuỗi
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public String toStr() {
        return this.obj == null ? null : this.obj.toString();
    }

    /**
     * Lấy chuỗi
     *
     * @return Trả về chuỗi
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    @Override
    public String toString() {
        return toStr();
    }

    /**
     * Lấy kí tự
     *
     * @return Trả về kí tự
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public char toChar() {
        try {
            return this.obj.toString().toCharArray()[0];
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            return '0';
        }
    }

    /**
     * Lấy số nguyên
     *
     * @return Trả về số nguyên
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public int toInt() {
        try {
            return Integer.parseInt(strToInt(this.obj.toString()));
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    /**
     * Lấy số nguyên
     *
     * @return Trả về số nguyên
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public long toLong() {
        try {
            return Long.parseLong(strToInt(this.obj.toString()));
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    /**
     * Lấy số thực
     *
     * @return Trả về số thực
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public double toDouble() {
        try {
            return Double.parseDouble(this.obj.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    /**
     * Lấy số thực
     *
     * @return Trả về số thực
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    public float toFloat() {
        try {
            return Float.parseFloat(this.obj.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    /**
     * Lấy dộ dài của mảng
     *
     * @return Trả về độ dài của mảng
     *
     * @see org.json.simple.Json2T
     *
     * @since 2.0.0.0
     */
    //
    public int length() {
        int lengthObject = isInstanceOfJsonObject(this.obj) ? toJsonObject(this.obj).size() : -1;
        int lengthArray = isInstanceOfJsonArray(this.obj) ? toJsonArray(this.obj).size() : -1;
        return lengthArray > lengthObject ? lengthArray : lengthObject;
    }

    @Override
    public int compareTo(Json2T json2T) {
        if (this.obj == null) {
            return -1;
        }
        if (json2T == null) {
            return 1;
        }
        String oThis = this.toString();
        String oThat = json2T.toString();
        if (isNumber(oThis) && isNumber(oThat)) {
            return Float.parseFloat(oThis) > Float.parseFloat(oThat) ? 1 : -1;
        } else {
            int len1 = oThis.length();
            int len2 = oThat.length();
            int lim = Math.min(len1, len2);
            char v1[] = oThis.toCharArray();
            char v2[] = oThat.toCharArray();
            int k = 0;
            while (k < lim) {
                char c1 = v1[k];
                char c2 = v2[k];
                if (c1 != c2) {
                    return c1 - c2;
                }
                k++;
            }
            return len1 - len2;
        }

    }

    //
    private static JSONObject toJsonObject(Object object) {
        return isInstanceOfJsonObject(object) ? (JSONObject) JSONValue.parse(object.toString()) : null;
    }

    private static JSONArray toJsonArray(Object object) {
        return isInstanceOfJsonArray(object) ? (JSONArray) JSONValue.parse(object.toString()) : null;
    }

    //
    private static boolean isInstanceOfJsonObject(Object object) {
        return object == null ? false : object instanceof JSONObject || object instanceof Json2T;
    }

    private static boolean isInstanceOfJsonArray(Object object) {
        return object == null ? false : object instanceof JSONArray || object instanceof Json2T;
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

    private static String strToInt(String fl) {
        if (fl.contains(".")) {
            return fl.substring(0, fl.indexOf("."));
        }
        return fl;
    }

    private static float sumFloat(float[] fls) {
        float fl = 0;
        for (int i = 0; i < fls.length / 2; i++) {
            fl += fls[i] + fls[fls.length - 1 - i];
        }
        fl += fls.length % 2 == 0 ? 0 : fls[fls.length / 2];
        return fl;
    }

    private static String arraysToString(Object[] objs) {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (Object obj : objs) {
            String str = obj == null ? "" : obj.toString().replaceAll("\"", "\\\\\"");
            stringBuilder.append("\"").append(str).append("\",");
        }

        return stringBuilder.toString().substring(0, stringBuilder.length() - 1) + "]";
    }

    private static boolean isNumber(String num) {
        try {
            float fl = Float.parseFloat(num);
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
}
