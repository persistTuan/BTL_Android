package com.tlu.edu.vn.ht63.btl_nhom10.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserWithOrders {
    @Embedded
    public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "userCreatorId"
    )
    public List<Order> orders;
}
