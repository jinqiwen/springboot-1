package com.org.stuIntelEat.Controller;

import com.org.stuIntelEat.base.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import com.org.stuIntelEat.service.Search.SearchServiceImpl;
import com.org.stuIntelEat.service.Search.ServiceResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
/*@ComponentScan*/
public class TestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
     @Autowired
     private SearchServiceImpl iSearchService;
    @RequestMapping(value = "queryMaterialType", method = RequestMethod.POST)
    public Object test(){
        log.info("--------------->>打印日志");
        return "hellow world";
    }

    //@RestController,返回json数据
    //@Controller，返回login.jsp页面
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request, HttpServletResponse response){

        return "login";
    }

    //无论是@RestController还是@Controller都不影响返回页面
    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");

        return mav;
    }

    @RequestMapping("/index")
    public String index(Map<String,Object> map){
        map.put("name", "Andy");
        return "index";
    }


    // 删除接口
    @GetMapping("/add/book/novel1")
    public String delete1(@RequestParam(name = "id",required = false) String id) {

        return "index1";
    }
    // 自动补全接口
    @GetMapping("rent/house/autocomplete")
    @ResponseBody
    public ApiResponse autocomplete(@RequestParam(name = "prefix",required = false) String prefix) {
        if(prefix.isEmpty()){
            return ApiResponse.ofStatus(ApiResponse.Status.BAD_REQUEST);

        }
        ServiceResult<List<String>> result=this.iSearchService.suggest(prefix);
        return ApiResponse.ofSuccess(result.getResult());
    }

}
