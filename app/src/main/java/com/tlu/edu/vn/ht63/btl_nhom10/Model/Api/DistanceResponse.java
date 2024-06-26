package com.tlu.edu.vn.ht63.btl_nhom10.Model.Api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DistanceResponse {
    @SerializedName("destination_addresses")
    private List<String> destinationAddresses;

    @SerializedName("origin_addresses")
    private List<String> originAddresses;

    @SerializedName("rows")
    private List<Row> rows;

    @SerializedName("status")
    private String status;

    public List<Row> getRows() {
        return rows;
    }

    public String getStatus() {
        return status;
    }

    public String getDestinationAddress() {
        return destinationAddresses != null && !destinationAddresses.isEmpty()
                ? destinationAddresses.get(0) : null;
    }

    public String getOriginAddress() {
        return originAddresses != null && !originAddresses.isEmpty()
                ? originAddresses.get(0) : null;
    }


}
