/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qt.common;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.Set;
import java.util.function.Function;

/**
 *
 * @author Administrator
 */
public class DataWareHouse {

    private final JSONObject coreData;
    private final Map<String, Set<String>> keyMaps;

    public DataWareHouse() {
        this.coreData = new JSONObject();
        this.keyMaps = new HashMap<>();
    }

    public DataWareHouse(JSONObject parseObject) {
        this.coreData = new JSONObject(parseObject);
        this.keyMaps = new HashMap<>();
    }

    public void putkeyMap(String baseKey, String... mapKeys) {
        this.keyMaps.put(baseKey, Set.of(mapKeys));
    }

    public boolean putAll(Map data) {
        if (data != null) {
            this.coreData.putAll(data);
            return true;
        }
        return false;
    }

    public <T> List<T> getListJsonArray(String key) {
        return cvtArrays2List(getJSONArray(key));
    }

    public <T> List<T> cvtArrays2List(JSONArray array) {
        List<T> result = new ArrayList<>();
        if (isNull(array)) {
            return result;
        }
        for (Object elem : array) {
            if (elem != null) {
                result.add((T) elem);
            }
        }
        return result;
    }

    public String getString(String key) {
        if (coreData.containsKey(key)) {
            return coreData.getString(key);
        } else {
            return getWithMapkeys(key, (t) -> coreData.getString(key), null);
        }
    }

    public String[] getArrays(String key, String regex) {
        String val = coreData.getString(key);
        if (val != null) {
            String[] arr = val.split(regex);
            String[] newArr = new String[arr.length];
            for (int index = 0; index < arr.length; index++) {
                newArr[index] = arr[index].trim();
            }
            return newArr;
        }
        return null;
    }

    public Integer getInteger(String key, int defaultValue) {
        Integer value = getInteger(key);
        return value == null ? defaultValue : value;
    }

    public Double getDouble(String key, double defaultValue) {
        Double value = getDouble(key);
        return value == null ? defaultValue : value;
    }

    public Integer getInteger(String key) {
        try {
            Integer value = coreData.getInteger(key);
            if (value != null) {
                return value;
            } else {
                return getWithMapkeys(key, (t) -> coreData.getInteger(key), Integer.valueOf(getString(key)));
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Double getDouble(String key) {
        try {
            Double value = coreData.getDouble(key);
            if (value != null) {
                return value;
            } else {
                return getWithMapkeys(key, (t) -> {
                    return coreData.getDouble(key);
                }, Double.valueOf(getString(key)));
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public JSONObject getJson(String key) {
        Object value = coreData.get(key);
        if (value instanceof JSONObject jSONObject) {
            return jSONObject;
        }
        return null;
    }

    public JSONObject toJson() {
        return (JSONObject) coreData.clone();
    }

    public JSONObject toJsonNonClone() {
        return coreData;
    }

    public JSONArray getJSONArray(String key) {
        return coreData.getJSONArray(key);
    }

    public Object put(String key, Object value) {
        return this.coreData.put(key, value);
    }

    public List<String> getListSlip(String key, String regex) {
        if (getArrays(key, regex) == null) {
            return new ArrayList<>();
        }
        return Arrays.asList(getArrays(key, regex));
    }

    public <T> List<T> getList(String key) {
        List<T> result = new ArrayList<>();
        var arr = coreData.getJSONArray(key);
        if (arr != null) {
            for (var object : arr) {
                result.add((T) object);
            }
        } else {
            Set<String> vals;
            for (String baseKey : keyMaps.keySet()) {
                vals = keyMaps.get(baseKey);
                if (this.coreData.containsKey(baseKey) && vals.contains(key)) {
                    arr = coreData.getJSONArray(baseKey);
                    for (var object : arr) {
                        result.add((T) object);
                    }
                }
            }
        }
        return result;
    }

    public void clearkeyMaps() {
        this.keyMaps.clear();
    }

    public void clear() {
        this.coreData.clear();
    }

    public Long getLong(String key) {
        try {
            Long value = coreData.getLong(key);
            if (value != null) {
                return value;
            } else {
                return getWithMapkeys(key, (t) -> coreData.getLong(t), Long.valueOf(getString(key)));
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        try {
            if (coreData.containsKey(key)) {
                return coreData.getBoolean(key);
            } else {
                return getWithMapkeys(key, (t) -> coreData.getBoolean(t), defaultValue);
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public Object get(String key) {
        if (coreData.containsKey(key)) {
            return coreData.get(key);
        } else {
            Set<String> value;
            for (String baseKey : keyMaps.keySet()) {
                value = keyMaps.get(baseKey);
                if (this.coreData.containsKey(baseKey) && value.contains(key)) {
                    return coreData.get(baseKey);
                }
            }
        }
        return null;
    }

    public String getString(String key, String defaultVal) {
        String val = getString(key);
        return val == null ? defaultVal : val;
    }

    private <T> T getWithMapkeys(String key, Function<String, T> function, T defaulVal) {
        Set<String> vals;
        for (String baseKey : keyMaps.keySet()) {
            vals = keyMaps.get(baseKey);
            if (this.coreData.containsKey(baseKey) && vals.contains(key)) {
                return function.apply(baseKey);
            }
        }
        return defaulVal;
    }
}
