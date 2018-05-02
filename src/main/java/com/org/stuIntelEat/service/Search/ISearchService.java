package com.org.stuIntelEat.service.Search;

import java.util.List;

public interface ISearchService {
    /**
     * 获取补全建议关键词
     */
    ServiceResult<List<String>> suggest(String prefix);

    /**
     * 聚合特定小区的房间数
     */
    ServiceResult<Long> aggregateDistrictHouse(String title, String author, String massages);

}
