package com.admitone.ordermgmt.web.search;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.admitone.ordermgmt.model.CustomerOrderEntity;
import com.admitone.ordermgmt.service.OrderMgmtService;

@Controller
public class SearchController {

	private Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	OrderMgmtService orderMgmtService;
	
	@GetMapping("/searchRequest")
	public String getSearchRequest(Model model) {        
        model.addAttribute("searchInput", new SearchInput());        
    	return "searchRequest";
    }
	
	@PostMapping("/searchResults")
    public String getSearchResults(Model model, @ModelAttribute SearchInput input) {
		
		log.info("...startID " + input.getStartID());
		log.info("...endID " + input.getEndID());
				
		Iterable<CustomerOrderEntity> orders = orderMgmtService.findByEventIdBetween(input.getStartID(), input.getEndID());
		
		//for (CustomerOrderEntity order : orders) {
		//	log.info ("order: " + order.getUsername());
		//}
		
		model.addAttribute("orders", orders);
		
        return "searchResults";
    }
	
}
