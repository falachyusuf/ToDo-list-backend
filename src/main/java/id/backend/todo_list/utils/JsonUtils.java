package id.backend.todo_list.utils;

import com.google.gson.Gson;

public class JsonUtils {

    private static final Gson gson = new Gson();

    public static String convertToString(Object object) {
        return gson.toJson(object);
    }
}
