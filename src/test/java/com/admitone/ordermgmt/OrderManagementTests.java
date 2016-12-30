package com.admitone.ordermgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.admitone.ordermgmt.service.OrderMgmtService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class OrderManagementTests extends AbstractTestNGSpringContextTests {
  
		
  @Autowired
  private WebApplicationContext wac;

  @Autowired 
  OrderMgmtService orderMgmtService;
  
  private MockMvc mockMvc;
  
  @BeforeMethod
  public void setupClient () {
   mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }
  
  @Test
  public void testPurchase () throws Exception {
        
    String username = "bill";
    String eventId = "1";
    String numTickets = "8";
        
    MvcResult result = mockMvc.perform(
          get("/purchase")
       .contentType(MediaType.APPLICATION_FORM_URLENCODED)
       .param("username", username)
       .param("eventId", eventId)
       .param("numTickets", numTickets)
       .accept(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk())
       .andReturn();
    
    // TODO: parse response, test for success message, query orders table for newly created order    
    System.out.println("Response: " + result.getResponse().getContentAsString());        
  }      

  // TODO: add unit tests for service methods
       
}
