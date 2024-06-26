package com.tlu.edu.vn.ht63.btl_nhom10.Model.Api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Row {
    @SerializedName("elements")
    private List<Element> elements;

    public List<Element> getElements() {
        return elements;
    }
}
