package com.example.spring_project.presentation.controller;


import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import com.example.spring_project.domain.model.user.Name;
import com.example.spring_project.domain.model.user.NewUser;
import com.example.spring_project.domain.model.user.Tel;
import com.example.spring_project.domain.model.user.User;
import com.example.spring_project.domain.model.user.UserRepository;
import com.example.spring_project.domain.model.user.Address;
import com.example.spring_project.domain.model.user.Id;
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
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String SYSTEM_ERROR = "systemError";
    private final String USER_NOT_FOUND_ERROR = "userNotFound";
    

    // @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testEdit_canGetUser() throws Exception {
        List<User> list = service.findAll();

        User expected = list.get(3);
        mockMvc.perform(get("/edit/"+ expected.id().value())
                // .contentType(MediaType.APPLICATION_JSON)
                // .content(objectMapper.writeValueAsString(expected.id()))
            )
            .andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.id.value").value(expected.id().value()))
            .andExpect(jsonPath("$.name.value").value(expected.name().value()))
            .andExpect(jsonPath("$.address.value").value(expected.address().value()))
            .andExpect(jsonPath("$.tel.value").value(expected.tel().value()))
            .andExpect(jsonPath("$.country.name").value(expected.country().name()))
            .andExpect(jsonPath("$.country.code").value(expected.country().code()));
    }
   
    // @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testEdit_error_UnexistedEndPoint() throws Exception {
        mockMvc.perform(get("/edit/"))
            .andDo(print())
            .andExpect(status().is(404));
    }
    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testEdit_error_validation_id_notExists() throws Exception {
         mockMvc.perform(get("/edit/500"))
            .andDo(print())
            .andExpect(status().is(500))
            .andExpect(content().string(containsString(this.SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Id.ID_CHECK_ERROR)));
    }
    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testEdit_error_validation_id_maxLength() throws Exception {
        mockMvc.perform(get("/edit/" + 100000))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(this.SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Id.MAX_LENGTH_ERROR)));
    }

    @SuppressWarnings("null")   
    @Test
    @Sql("/test_insert_user_list.sql")
    void testEdit_error_id_is_string() throws Exception {
        mockMvc.perform(get("/edit/aaaaa"))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(this.SYSTEM_ERROR)))
            .andExpect(content().string(containsString("Failed to convert value of type")));
    }

    
    // -------------------
    // Delete
    // -------------------
    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testDelete_canDeleteUser() throws Exception {
        User user = service.findAll().get(0);
        mockMvc.perform((delete("/delete"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user.id())))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @SuppressWarnings("null")
    @Test
    void testDelete_error_validation_id_notExists() throws Exception {
        Id testId = new Id(9999);
        
        mockMvc.perform(delete("/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testId)))
           .andDo(print())
           .andExpect(status().is(500))
           .andExpect(content().string(containsString(SYSTEM_ERROR)))
           .andExpect(content().string(containsString(Id.ID_CHECK_ERROR)));
    }

    @SuppressWarnings("null")
    @Test
    void testDelete_error_validation_id_maxLength() throws Exception {
        String id = "{\"value\":999999999}";
        mockMvc.perform(delete("/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(id))
           .andDo(print())
           .andExpect(status().is5xxServerError())
           .andExpect(content().string(containsString(SYSTEM_ERROR)))
           .andExpect(content().string(containsString(Id.MAX_LENGTH_ERROR)));
    }

    @SuppressWarnings("null")
    @Test
    void testDelete_systemError_id_isNull() throws Exception {
        // JSON parse error
        String id = "{\"value\":}";
        mockMvc.perform(delete("/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(id))
           .andDo(print())
           .andExpect(status().is5xxServerError())
           .andExpect(content().string(containsString(SYSTEM_ERROR)));
    }

    /*
     * Init
     */
    @Test
    void testInit_canGetCountryList()  throws Exception {
        mockMvc.perform(get("/init"))
           .andDo(print())
           .andExpect(status().isOk());
    }

    @Test
    void test404_endpointDoesNotExists() throws Exception {
        mockMvc.perform(get("/xxxx"))
           .andDo(print())
           .andExpect(status().isNotFound());
    }

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testUpdate_canUpdateUser() throws Exception {
        User user = service.findAll().get(4);
        System.out.println(user.toString());
        User stub = new User(
             user.id(),
             new Name("New User"), 
             new Address ("New address 345"), 
             new Tel("3023898787"), 
             new Country("India", "IND"));

        String expected = objectMapper.writeValueAsString(stub);
        mockMvc.perform(patch("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(expected)
                )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString(expected)));
    }

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testUpdate_error_validation_id_maxLength() throws Exception {
        String testdata = "{\"id\":{\"value\":9999999},\"name\":{\"value\":\"New User\"},\"address\":{\"value\":\"New address 345\"},\"tel\":{\"value\":\"3023898787\"},\"country\":{\"name\":\"India\",\"code\":\"IND\"}}";

        mockMvc.perform(patch("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testdata)
                )
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Id.ID_CHECK_ERROR)))
            .andExpect(content().string(containsString(Id.MAX_LENGTH_ERROR)));
    }

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testUpdate_error_validation_id_name() throws Exception {
        String testdata = "{\"id\":{\"value\":9999999},\"name\":{\"value\":\"New User!!!\"},\"address\":{\"value\":\"New address 345\"},\"tel\":{\"value\":\"3023898787\"},\"country\":{\"name\":\"India\",\"code\":\"IND\"}}";

        mockMvc.perform(patch("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testdata)
                )
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Id.ID_CHECK_ERROR)))
            .andExpect(content().string(containsString(Id.MAX_LENGTH_ERROR)))
            .andExpect(content().string(containsString(Name.PATTERN_ERROR)));
    }

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testUpdate_error_validation_id_name_address() throws Exception {
        
        String testdata = "{\"id\":{\"value\":9999999},"
            + "\"name\":{\"value\":\"New User!!!\"},"
            + "\"address\":{\"value\":\"New address 345, New address 345, New address 345, New address 345, New address 345, New address 345, New address!!!!\"},"
            + "\"tel\":{\"value\":\"3023898787\"},"
            + "\"country\":{\"name\":\"India\",\"code\":\"IND\"}}";

        mockMvc.perform(patch("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testdata)
                )
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Id.ID_CHECK_ERROR)))
            .andExpect(content().string(containsString(Id.MAX_LENGTH_ERROR)))
            .andExpect(content().string(containsString(Name.PATTERN_ERROR)))
            .andExpect(content().string(containsString(Address.LENGTH_ERROR)))
            .andExpect(content().string(containsString(Address.PATTERN_ERROR)));
    }

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testUpdate_error_validation_id_name_address_country() throws Exception {
        
        String testdata = "{\"id\":{\"value\":9999999},"
            + "\"name\":{\"value\":\"New User!!!\"},"
            + "\"address\":{\"value\":\"New address 345, New address 345, New address 345, New address 345, New address 345, New address 345, New address!!!!\"},"
            + "\"tel\":{\"value\":\"3023898787\"},"
            + "\"country\":{\"name\":\"India\",\"code\":\"\"}}";

        mockMvc.perform(patch("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testdata)
                )
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Id.ID_CHECK_ERROR)))
            .andExpect(content().string(containsString(Id.MAX_LENGTH_ERROR)))
            .andExpect(content().string(containsString(Name.PATTERN_ERROR)))
            .andExpect(content().string(containsString(Address.LENGTH_ERROR)))
            .andExpect(content().string(containsString(Address.PATTERN_ERROR)))
            .andExpect(content().string(containsString(Country.NOT_EMPTY_ERROR)));
    }
    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testSearch_canGetUserList_noCriteria() throws Exception {
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

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testSearch_canGetUserList_criteria_name_address() throws Exception {
        mockMvc.perform(get("/search")
                .param("name", "Sada")
                .param("address", "Tokyo"))
             .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[*].name.value", Matchers.everyItem(Matchers.containsString("Sada"))))
            .andExpect(jsonPath("$[*].address.value", Matchers.everyItem(Matchers.containsString("Tokyo")))
            );
    }

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testSearch_canGetUserList_criteria_country() throws Exception {

        mockMvc.perform(get("/search")
                .param("countryCode", "ITA"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[*].country.code", Matchers.everyItem(Matchers.containsString("ITA")))
            );
    }

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testSearch_canGetUserList_criteria_all() throws Exception {

        mockMvc.perform(get("/search")
                .param("name", "Galileo")
                .param("address", "Rome")
                .param("tel", "67891")
                .param("countryCode", "ITA"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[*].name.value", Matchers.everyItem(Matchers.containsString("Galileo"))))
            .andExpect(jsonPath("$[*].address.value", Matchers.everyItem(Matchers.containsString("Rome"))))
            .andExpect(jsonPath("$[*].tel.value", Matchers.everyItem(Matchers.containsString("67891"))))
            .andExpect(jsonPath("$[*].country.code", Matchers.everyItem(Matchers.containsString("ITA")))
            );
    }

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testSearch_noErrorOcceredWhenResultIs0() throws Exception {

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

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testAdd_canAddUser() throws Exception {

        NewUser newUser = new NewUser(
            new Name("New User"),
            new Address("Via Del Monumento, 4, 27012 Certosa di Pavia Pavia"),
            new Tel("8544453435"),
            new Country("", "ITA"));

        System.out.println(objectMapper.writeValueAsString(newUser));
        
        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newUser)))
            .andDo(print())
            .andExpect(status().isOk());
    }    

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testAdd_error_validation_name() throws Exception {

        String test = "{\"name\":{\"value\":\"New User@1!#$~=¥?><^|&\"},\"address\":{\"value\":\"Via Del Monumento, 4, 27012 Certosa di Pavia Pavia\"},\"tel\":{\"value\":\"8544453435\"},\"country\":{\"name\":\"\",\"code\":\"ITA\"}}";

        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(test))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Name.SIZE_ERROR)))
            .andExpect(content().string(containsString(Name.PATTERN_ERROR)));
    }    

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testAdd_error_validation_name_address() throws Exception {

        String test = "{\"name\":{\"value\":\"New User!% New User$% New User=^'\"},\"address\":{\"value\":\"Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$, Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$,Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$,Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$,Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$\"},\"tel\":{\"value\":\"8544453435\"},\"country\":{\"name\":\"\",\"code\":\"ITA\"}}";

        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(test))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Name.SIZE_ERROR)))
            .andExpect(content().string(containsString(Name.PATTERN_ERROR)))
            .andExpect(content().string(containsString(Address.LENGTH_ERROR)))
            .andExpect(content().string(containsString(Address.PATTERN_ERROR)));
    }    

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testAdd_error_validation_name_address_tel() throws Exception {

        String test = "{\"name\":{\"value\":\"New User!% New User$% New User=^'\"},\"address\":{\"value\":\"Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$, Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$,Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$,Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$,Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$\"},\"tel\":{\"value\":\"8544453435s-\"},\"country\":{\"name\":\"\",\"code\":\"ITA\"}}";

        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(test))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Name.SIZE_ERROR)))
            .andExpect(content().string(containsString(Name.PATTERN_ERROR)))
            .andExpect(content().string(containsString(Address.LENGTH_ERROR)))
            .andExpect(content().string(containsString(Address.PATTERN_ERROR)))
            .andExpect(content().string(containsString(Tel.SIZE_ERROR)))
            .andExpect(content().string(containsString(Tel.PATTERN_ERROR)));
            // .andExpect(content().string(containsString(Address.PATTERN_ERROR)));
    }    


    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testAdd_error_validation_name_address_tel_country() throws Exception {

        String test = "{\"name\":{\"value\":\"New User!% New User$% New User=^'\"},\"address\":{\"value\":\"Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$, Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$,Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$,Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$,Via Del Monumento, 4, 27012 Certosa di Pavia Pavia|&%$\"},\"tel\":{\"value\":\"8544453435s-\"},\"country\":{\"name\":\"\",\"code\":\"\"}}";

        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(test))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Name.SIZE_ERROR)))
            .andExpect(content().string(containsString(Name.PATTERN_ERROR)))
            .andExpect(content().string(containsString(Address.LENGTH_ERROR)))
            .andExpect(content().string(containsString(Address.PATTERN_ERROR)))
            .andExpect(content().string(containsString(Tel.SIZE_ERROR)))
            .andExpect(content().string(containsString(Tel.PATTERN_ERROR)))
            .andExpect(content().string(containsString(Country.NOT_EMPTY_ERROR)))
            .andExpect(content().string(containsString(Country.LENGTH_ERROR)));
    }    
    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testAdd_error_validation_name_isEmpty() throws Exception {

        String test = "{\"name\":{\"value\":\"\"},"
            +"\"address\":{\"value\":\"Via Del Monumento, 4, 27012 Certosa di Pavia Pavia\"},"
            +"\"tel\":{\"value\":\"8544453435\"},"
            +"\"country\":{\"name\":\"\",\"code\":\"ITA\"}}";

        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(test))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Name.NOT_BLANK_ERROR)));
    }
    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testAdd_error_validation_address_isEmpty() throws Exception {

        String test = "{\"name\":{\"value\":\"New User\"},"
            +"\"address\":{\"value\":\"\"},"
            +"\"tel\":{\"value\":\"8544453435\"},"
            +"\"country\":{\"name\":\"\",\"code\":\"ITA\"}}";

        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(test))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Address.NOT_BLANK_ERROR)));
    }
    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testAdd_error_validation_tel_isEmpty() throws Exception {

        String test = "{\"name\":{\"value\":\"New User\"},"
            +"\"address\":{\"value\":\"Via Del Monumento, 4, 27012 Certosa di Pavia Pavia\"},"
            +"\"tel\":{\"value\":\"\"},"
            +"\"country\":{\"name\":\"\",\"code\":\"ITA\"}}";

        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(test))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Tel.NOT_BLANK_ERROR)));
    }

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testAdd_error_validation_country_notExists() throws Exception {

        String test = "{\"name\":{\"value\":\"New User\"},"
            +"\"address\":{\"value\":\"Via Del Monumento, 4, 27012 Certosa di Pavia Pavia\"},"
            +"\"tel\":{\"value\":\"8544453435\"},"
            +"\"country\":{\"name\":\"\",\"code\":\"AAA\"}}";

        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(test))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Country.NOT_EXISTS_ERROR)));
    }    

    @SuppressWarnings("null")
    @Test
    @Sql("/test_insert_user_list.sql")
    void testAdd_error_validation_country_isEmpty() throws Exception {

        String test = "{\"name\":{\"value\":\"New User\"},"
            +"\"address\":{\"value\":\"Via Del Monumento, 4, 27012 Certosa di Pavia Pavia\"},"
            +"\"tel\":{\"value\":\"8544453435\"},"
            +"\"country\":{\"name\":\"\",\"code\":\"\"}}";

        mockMvc.perform(post("/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(test))
            .andDo(print())
            .andExpect(status().is5xxServerError())
            .andExpect(content().string(containsString(SYSTEM_ERROR)))
            .andExpect(content().string(containsString(Country.NOT_EMPTY_ERROR)));
    }


}
