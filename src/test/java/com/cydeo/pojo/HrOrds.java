package com.cydeo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class HrOrds {

    private List<HrOrdsRegions> items;
    private boolean hasMore;
    private int limit;
    private int offset;
    private int count;

}
