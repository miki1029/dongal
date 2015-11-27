package com.dongal.api.response;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Freddi
 */
@Data
public class Response {
    private List<Map<String, Serializable>> data = new ArrayList<Map<String, Serializable>>();

    public void addData(Map<String,Serializable> data) {
            this.data.add(data);
        }
}
