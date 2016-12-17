package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
		Map<String, Object> headers = new HashMap<String, Object>();
		if (q.contains(" ")){
			String[] query = q.split(" ");
			String keyword = query[0];
			String max = query[1];
			headers.put("CamelTwitterKeywords", keyword);
			if (max != null){
				String numberTweets = max.split(":")[1];
				headers.put("CamelTwitterCount", Integer.parseInt(numberTweets));
			}
		}else{
			headers.put("CamelTwitterKeywords", q);
		}
        return producerTemplate.requestBodyAndHeaders("direct:search","",headers);
    }
}