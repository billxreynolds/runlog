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
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.admitone.ordermgmt.service.OrderMgmtService;
import com.admitone.ordermgmt.web.api.OrderMgmtConstants;

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
        
    // positive test
    MvcResult result = mockMvc.perform(
          get("/api/purchase")
       .contentType(MediaType.APPLICATION_FORM_URLENCODED)
       .param("username", username)
       .param("eventId", eventId)
       .param("numTickets", numTickets)
       .accept(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk())
       .andReturn();
    
    System.out.println("testPurchase response: " + result.getResponse().getContentAsString());    
    Assert.assertTrue(result.getResponse().getContentAsString().contains(OrderMgmtConstants.SUCCESS_CODE));
    
    // negative test - invalid user specified
    result = mockMvc.perform(
            get("/api/purchase")
         .contentType(MediaType.APPLICATION_FORM_URLENCODED)
         .param("username", "nouser")
         .param("eventId", eventId)
         .param("numTickets", numTickets)
         .accept(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andReturn();
    
    System.out.println("testPurchase response: " + result.getResponse().getContentAsString());   
    Assert.assertTrue(result.getResponse().getContentAsString().contains(OrderMgmtConstants.ERROR_CODE));
    
    // negative test - invalid event specified
    result = mockMvc.perform(
            get("/api/purchase")
         .contentType(MediaType.APPLICATION_FORM_URLENCODED)
         .param("username", username)
         .param("eventId", "99")
         .param("numTickets", numTickets)
         .accept(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andReturn();
    
    System.out.println("testPurchase response: " + result.getResponse().getContentAsString());   
    Assert.assertTrue(result.getResponse().getContentAsString().contains(OrderMgmtConstants.ERROR_CODE));
    
    // negative test - invalid event specified
    result = mockMvc.perform(
            get("/api/purchase")
         .contentType(MediaType.APPLICATION_FORM_URLENCODED)
         .param("username", username)
         .param("eventId", eventId)
         .param("numTickets", "-8")
         .accept(MediaType.APPLICATION_JSON))
         .andExpect(status().isOk())
         .andReturn();
    
    System.out.println("testPurchase response: " + result.getResponse().getContentAsString());   
    Assert.assertTrue(result.getResponse().getContentAsString().contains(OrderMgmtConstants.ERROR_CODE));
  }      
  
  @Test
  public void testCancel () throws Exception {
        
    String username = "bill";
    String eventId = "1";
    String numTickets = "4";
        
    MvcResult result = mockMvc.perform(
          get("/api/cancel")
       .contentType(MediaType.APPLICATION_FORM_URLENCODED)
       .param("username", username)
       .param("eventId", eventId)
       .param("numTickets", numTickets)
       .accept(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk())
       .andReturn();
    
    System.out.println("testCancel Response: " + result.getResponse().getContentAsString());   
    Assert.assertTrue(result.getResponse().getContentAsString().contains(OrderMgmtConstants.SUCCESS_CODE));
  
    // TODO: add negative tests
  } 
  

  @Test
  public void testExchange () throws Exception {
        
    String username = "bob";
    String fromEvent = "1";
    String toEvent = "3";
    String numTickets = "2";
        
    MvcResult result = mockMvc.perform(
          get("/api/exchange")
       .contentType(MediaType.APPLICATION_FORM_URLENCODED)
       .param("username", username)
       .param("fromEventId", fromEvent)
       .param("toEventId", toEvent)
       .param("numTickets", numTickets)
       .accept(MediaType.APPLICATION_JSON))
       .andExpect(status().isOk())
       .andReturn();
    
    System.out.println("testExchange Response: " + result.getResponse().getContentAsString());   
    Assert.assertTrue(result.getResponse().getContentAsString().contains(OrderMgmtConstants.SUCCESS_CODE));
  
    // TODO: add negative tests
  }    

  @Test
  public void testApplication () throws Exception {
        
	/*  TODO: login, enter search request, process results
    String username = "admin";
    String password = "admin";
    
    MvcResult result = mockMvc.perform(
          post("/login")
       .contentType(MediaType.APPLICATION_FORM_URLENCODED)
       .param("username", username)
       .param("password", password))
       .andExpect(status().isOk())
       .andReturn();
    
    // 
    System.out.println("testApplication Response: " + result.getResponse().getContentAsString());
    */        
  }      
  
}
