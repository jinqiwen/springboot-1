package com.org.stuIntelEat.service.Search;

import com.google.common.collect.Lists;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchServiceImpl implements  ISearchService{
    public static final Logger logger = LoggerFactory.getLogger(ISearchService.class);
    private static  final  String INDEX_NAME="book";
    private  static  final  String INDEX_TYPE="novel";
    private  static  final  String INDEX_TITLE="title";
    private  static  final  String INDEX_AUTHOUR="author";
    private  static  final  String INDEX_MASSAGES="massages";
    @Autowired
    private TransportClient esclient;
    @Override
    public ServiceResult<List<String >> suggest(String prefix){
        CompletionSuggestionBuilder suggestion= SuggestBuilders.completionSuggestion("suggest").prefix(prefix).size(5);
        SuggestBuilder suggestBuilder=new SuggestBuilder();
        suggestBuilder.addSuggestion("autocompelete" ,suggestion);
        SearchRequestBuilder requestBuilder= this.esclient.prepareSearch(INDEX_NAME)
                .setTypes(INDEX_TYPE)
                .suggest(suggestBuilder);
   logger.info(requestBuilder.toString());
        SearchResponse response=requestBuilder.get();
        Suggest suggest= response.getSuggest();
        if(suggest==null ){
            return ServiceResult.of(new ArrayList<>());
        }
        Suggest.Suggestion result=suggest.getSuggestion("autocompelete");
        int maxSuggest=0;
        Set<String> suggestSet=new HashSet<>();
        for(Object term : result.getEntries()){
            if(term instanceof CompletionSuggestion.Entry){
                CompletionSuggestion.Entry item=(CompletionSuggestion.Entry) term;
                if(item.getOptions().isEmpty()){
                    continue;
                }
               for(CompletionSuggestion.Entry.Option option:item.getOptions()){
                    String tip=option.getText().string();
                    if(suggestSet.contains(tip)){
                        continue;
                    }
                    suggestSet.add(tip);
                    maxSuggest++;
               }
            }
            if(maxSuggest>5){
                break;
            }

        }
        List<String> suggests= Lists.newArrayList(suggestSet.toArray(new String[]{}));
         return ServiceResult.of(suggests);
    }

    /**
     * 聚合特定小区的房间数
     */
    @Override
    public ServiceResult<Long> aggregateDistrictHouse(String title, String author, String massages){
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .filter(QueryBuilders.termQuery(INDEX_TITLE,title))
                .filter(QueryBuilders.termQuery(INDEX_AUTHOUR,author))
                .filter(QueryBuilders.termQuery(INDEX_MASSAGES,massages));
           SearchRequestBuilder requestBuilder=this.esclient.prepareSearch(INDEX_NAME)
                   .setTypes(INDEX_TYPE)
                   .setQuery(boolQueryBuilder)
                   .addAggregation(AggregationBuilders.terms(massages).field(massages)).setSize(0);
          logger.info(requestBuilder.toString());
          SearchResponse response=requestBuilder.get(requestBuilder.toString());
          if(response.status()== RestStatus.OK){
             Terms terms= response.getAggregations().get(INDEX_MASSAGES);
    if(terms.getBuckets()!=null&&!terms.getBuckets().isEmpty()){
        return  ServiceResult.of(terms.getBucketByKey(massages).getDocCount()) ;
    }else{
        logger.warn("Failed to Aggregate for "+INDEX_MASSAGES);
    }

          }
        return ServiceResult.of(0L);
    }
}
