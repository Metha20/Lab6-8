package com.example.secureauthapp;

import com.example.secureauthapp.controller.SecureAuthAppController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

// Import static helpers สำหรับการ Act และ Assert
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SecureAuthAppController.class) //
public class SecureAuthAppControllerTest {

    @Autowired
    private MockMvc mvc; //

    // 1. ทดสอบหน้า Home (/)
    @Test
    public void shouldReturnHomePage() throws Exception {
        // Act & Assert (รันและตรวจสอบผล)
        mvc.perform(get("/")) 
           .andExpect(status().isOk()) // ตรวจสอบว่า HTTP Status คือ 200
           .andExpect(view().name("index")); // ตรวจสอบว่าเรียกใช้ไฟล์ index.html
    }

    // 2. ทดสอบหน้า Registration (/registration)
    @Test
    public void shouldReturnRegistrationPage() throws Exception {
        // Act & Assert
        mvc.perform(get("/registration"))
           .andExpect(status().isOk())
           .andExpect(view().name("registration")); // ตรวจสอบว่าเรียกใช้ไฟล์ registration.html
    }
}