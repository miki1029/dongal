package com.dongal.api.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Freddi
 */
@Data
public class Response {
    private Serializable data;
//    private List<Map<String, Serializable>> data = new ArrayList<>();

/*    public void addData(Map<String, Serializable> data) {
        this.data.add(data);
    }*/
}
