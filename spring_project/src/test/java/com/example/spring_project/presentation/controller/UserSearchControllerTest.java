package com.example.spring_project.presentation.controller;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hamcrest.Matchers;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_project.application.service.UserService;
import com.example.spring_project.domain.model.country.Country;
import com.example.spring_project.domain.model.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/test_insert_user_list.sql")
    void testEdit_ユーザーが正しく取得することができること() throws Exception {
        List<User> list = service.findAll();

        User expected = list.get(3);
        System.out.println(expected.address());

        mockMvc.perform(get("/edit/"+expected.id()))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(expected.id()))
            .andExpect(jsonPath("$.name").value(expected.name()))
            .andExpect(jsonPath("$.address").value(expected.address()))
            .andExpect(jsonPath("$.tel").value(expected.tel()))
            .andExpect(jsonPath("$.country.name").value(expected.country().name()))
            .andExpect(jsonPath("$.country.code").value(expected.country().code()));
    }
   
    @Test
    @Sql("/test_insert_user_list.sql")
    void testEdit_ユーザーが取得できず例外発生すること() throws Exception {
        String expected = "{\"errorCode\":\"userNotFound\",\"message\":\"The user was not found.\"}";
         mockMvc.perform(get("/edit/500"))
            .andDo(print())
            .andExpect(status().is(404))
            .andExpect(content().string(containsString(expected))
        );
    }
    @Test
    @Sql("/test_insert_user_list.sql")
    void testEdit_予期せぬエラーが発生する() throws Exception {
         mockMvc.perform(get("/edit/"+"a"))
            .andDo(print())
            .andExpect(status().is(500));
    }
    
    @Test
    @Sql("/test_insert_user_list.sql")
    void testDelete_ユーザーが正しく削除できること() throws Exception {
        User user = service.findAll().get(0);
        mockMvc.perform(delete("/delete/" + user.id()))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @SuppressWarnings("null")
    @Test
    void testDelete_ユーザーが取得できず例外発生すること() throws Exception {
        String expected = "{\"errorCode\":\"userNotFound\",\"message\":\"The user was not found.\"}";
        mockMvc.perform(delete("/delete/500"))
           .andDo(print())
           .andExpect(status().is(404))
           .andExpect(content().string(containsString(expected)));
    }

    @Test
    void testInit_国リストが取得できること()  throws Exception {
        mockMvc.perform(get("/init"))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    void test404_存在しないエンドポイントを指定してnotFoundが返却されること() throws Exception {
        mockMvc.perform(get("/xxxx"))
           .andDo(print())
           .andExpect(status().isNotFound());
    }

    @Test
    @Sql("/test_insert_user_list.sql")
    void testUpdate_ユーザーが正しく更新されること() throws Exception {

        User user = service.findAll().get(4);
        System.out.println(user.toString());
        User stub = new User(user.id(),
             "New User", 
             "New address 345", 
             "3023898787", 
             new Country("India", "IND"));

        String expected = objectMapper.writeValueAsString(stub);
        mockMvc.perform(post("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stub))
                )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expected)));
    }

    @Test
    @Sql("/test_insert_user_list.sql")
    void testSearch_emptyCriteria() throws Exception {
        mockMvc.perform(get("/search")
                .param("id", "")
                .param("name", "")
                .param("address", "")
                .param("tel", "")
                .param("countryCode", ""))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(8)));
    }

    @Test
    @Sql("/test_insert_user_list.sql")
    void testSearch_Criteria_Name_Address() throws Exception {
        mockMvc.perform(get("/search")
                .param("id", "")
                .param("name", "Sada")
                .param("address", "Tokyo")
                .param("tel", "")
                .param("countryCode", ""))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[*].name", Matchers.everyItem(Matchers.containsString("Sada"))))
            .andExpect(jsonPath("$[*].address", Matchers.everyItem(Matchers.containsString("Tokyo")))
            );
    }

    @Test
    @Sql("/test_insert_user_list.sql")
    void testSearch_Criteria_Country() throws Exception {

        mockMvc.perform(get("/search")
                .param("id", "")
                .param("name", "")
                .param("address", "")
                .param("tel", "")
                .param("countryCode", "ITA"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[*].country.code", Matchers.everyItem(Matchers.containsString("ITA")))
            );
    }

    @Test
    @Sql("/test_insert_user_list.sql")
    void testSearch_Criteria_All() throws Exception {

        mockMvc.perform(get("/search")
                .param("id", "")
                .param("name", "Galileo")
                .param("address", "Rome")
                .param("tel", "67891")
                .param("countryCode", "ITA"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[*].country.code", Matchers.everyItem(Matchers.containsString("ITA")))
            );
    }

    @Test
    @Sql("/test_insert_user_list.sql")
    void testSearch_検索結果が0件の場合でもエラーにならないこと() throws Exception {

        mockMvc.perform(get("/search")
                .param("id", "99999")
                .param("name", "Galileo")
                .param("address", "Rome")
                .param("tel", "67891")
                .param("countryCode", "ITA"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));
    }


}
