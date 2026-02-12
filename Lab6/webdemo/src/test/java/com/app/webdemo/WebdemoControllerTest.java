package com.app.webdemo;

import com.app.webdemo.controller.WebdemoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WebdemoController.class)
public class WebdemoControllerTest {

    @Autowired
    private MockMvc mvc;

    // ✅ หน้า Home
    @Test
    public void testHomePage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    // ✅ หน้า Registration
    @Test
    public void testShowRegistrationForm() throws Exception {
        mvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("registrationForm"));
    }

    // ✅ Register สำเร็จ
    @Test
    public void testHandleRegistrationSuccess() throws Exception {
        mvc.perform(post("/register")
                .param("firstName", "Yanaphat")
                .param("lastName", "Test")
                .param("country", "Thailand")
                .param("dob", "2000-01-01")
                .param("email", "yanaphat@example.com")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("success"))
                .andExpect(model().attribute("firstName", "Yanaphat"))
                .andExpect(model().attribute("email", "yanaphat@example.com"));
    }

    // ❌ Register มี error (email ว่าง)
    @Test
    public void testHandleRegistrationError() throws Exception {
        mvc.perform(post("/register")
                .param("firstName", "Yanaphat")
                .param("lastName", "Test")
                .param("country", "Thailand")
                .param("dob", "2000-01-01")
                .param("email", "") // ❌ email ว่าง
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    // ✅ REST API PUT
    @Test
    public void testUpdateUser() throws Exception {
        mvc.perform(put("/users/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("PUT success: update user id = 123"));
    }

    // ✅ REST API DELETE
    @Test
    public void testDeleteUser() throws Exception {
        mvc.perform(delete("/users/456"))
                .andExpect(status().isOk())
                .andExpect(content().string("DELETE success: delete user id = 456"));
    }
}
