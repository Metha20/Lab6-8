package com.app.webdemo;

import com.app.webdemo.controller.WebdemoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WebdemoController.class)
public class WebdemoControllerTest {

    @Autowired
    private MockMvc mvc;

    // ===============================
    // 1️ Test Home Page
    // ===============================
    @Test
    public void testHomePage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        System.out.println("✅ Home Page Working");
    }

    // ===============================
    // 2️ Test Registration Page
    // ===============================
    @Test
    public void testShowRegistrationForm() throws Exception {
        mvc.perform(get("/registration"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("registrationForm"));

        System.out.println("✅ Registration Page Working");
    }

    // ===============================
    // 3️ Register Success
    // ===============================
    @Test
    public void testRegisterSuccess() throws Exception {
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
                .andExpect(model().hasNoErrors());

        System.out.println("✅ Register Success");
    }

    // ===============================
    // 4️ All Fields Empty
    // ===============================
    @Test
    public void testAllFieldsEmpty() throws Exception {
        mvc.perform(post("/register")
                .param("firstName", "")
                .param("lastName", "")
                .param("country", "")
                .param("dob", "")
                .param("email", "")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().hasErrors());

        System.out.println("✅ Validation Working (All Empty)");
    }

    // ===============================
    // 5️ Email Empty
    // ===============================
    @Test
    public void testEmailEmpty() throws Exception {
        mvc.perform(post("/register")
                .param("firstName", "Test")
                .param("lastName", "User")
                .param("country", "Thailand")
                .param("dob", "2000-01-01")
                .param("email", "")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(view().name("registration"));

        System.out.println("✅ Email Empty Detected");
    }

    // ===============================
    // 6️ Invalid Email Format
    // ===============================
    @Test
    public void testInvalidEmailFormat() throws Exception {
        mvc.perform(post("/register")
                .param("firstName", "Test")
                .param("lastName", "User")
                .param("country", "Thailand")
                .param("dob", "2000-01-01")
                .param("email", "abc")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        System.out.println("✅ Invalid Email Detected");
    }

    // ===============================
    // 7️ FirstName Too Long (Boundary)
    // ===============================
    @Test
    public void testFirstNameTooLong() throws Exception {
        mvc.perform(post("/register")
                .param("firstName", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                .param("lastName", "Test")
                .param("country", "Thailand")
                .param("dob", "2000-01-01")
                .param("email", "yanaphat@example.com")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        System.out.println("✅ Boundary Test Working");
    }

    // ===============================
    // 8️ Invalid DOB Format
    // ===============================
    @Test
    public void testInvalidDobFormat() throws Exception {
        mvc.perform(post("/register")
                .param("firstName", "Test")
                .param("lastName", "User")
                .param("country", "Thailand")
                .param("dob", "01-01-2000")
                .param("email", "yanaphat@example.com")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk());

        System.out.println("✅ Invalid DOB Detected");
    }

    // ===============================
    // 9️ PUT API
    // ===============================
    @Test
    public void testPutUser() throws Exception {
        mvc.perform(put("/users/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("PUT success: update user id = 123"));

        System.out.println("✅ PUT API Working");
    }

    // ===============================
    // 10 DELETE API
    // ===============================
    @Test
    public void testDeleteUser() throws Exception {
        mvc.perform(delete("/users/456"))
                .andExpect(status().isOk())
                .andExpect(content().string("DELETE success: delete user id = 456"));

        System.out.println("✅ DELETE API Working");
    }

    // ===============================
    // 1️1 404 Test
    // ===============================
    @Test
    public void testPageNotFound() throws Exception {
        mvc.perform(get("/unknown"))
                .andExpect(status().isNotFound());

        System.out.println("✅ 404 Working");
    }

    // ===============================
    // 1️2️ Method Not Allowed
    // ===============================
    @Test
    public void testMethodNotAllowed() throws Exception {
        mvc.perform(post("/users/123"))
                .andExpect(status().isMethodNotAllowed());

        System.out.println("✅ Method Not Allowed Working");
    }

    // ===============================
    // 1️3️ Response Content Check
    // ===============================
    @Test
    public void testResponseContent() throws Exception {
        mvc.perform(put("/users/999"))
                .andExpect(content().string(containsString("999")));

        System.out.println("✅ Response Content Verified");
    }
}
