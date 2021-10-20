//package backend.controller.unittest;
//
//import backend.DemoApplication;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = DemoApplication.class)
//@WebAppConfiguration
//public abstract class UnitTest {
//
//    protected MockMvc mockMvc;
//
//    @Autowired
//    WebApplicationContext webApplicationContext;
//
//    protected void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//    }
//
//}
