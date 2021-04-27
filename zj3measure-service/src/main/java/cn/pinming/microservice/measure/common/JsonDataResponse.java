package cn.pinming.microservice.measure.common;

import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 兼容前端的一种数据返回格式
 * Created by jin on 2020-03-06.
 */
public class JsonDataResponse {

    private final Map<String, Object> entity = new LinkedHashMap<>(4);

    public JsonDataResponse(String key, Object value) {
        this.entity.put(key, value);
    }

    private static final JsonDataResponse SUCCESS = new JsonDataResponse("data", "success");

    public JsonDataResponse addData(String key, Object data) {
        this.entity.put(key, data);
        return this;
    }

    public ResponseEntity<?> toResponseEntity() {
        return ResponseEntity.ok(this.entity);
    }

    public static JsonDataResponse defaultSuccess() {
        return SUCCESS;
    }

    public static JsonDataResponse defaultData(Object data) {
        return new JsonDataResponse("data", data);
    }

    public static JsonDataResponse defaultData(String key, Object data) {
        return new JsonDataResponse(key, data);
    }

    public JsonDataResponse addSuccess() {
        this.entity.put("data", "success");
        return this;
    }
}
