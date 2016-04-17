package com.thoughtworks.solomonsmines.api;

import java.util.List;

public class ListResponseWrapper extends BaseResponseWrapper {
    private List<?> dataList;
    private Long totalCount;

    public ListResponseWrapper(List<?> dataList) {
        this.dataList = dataList;
        this.totalCount = Long.valueOf(dataList.size());
    }

    public List<?> getDataList() {
        return dataList;
    }

    public void setDataList(List<?> dataList) {
        this.dataList = dataList;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}