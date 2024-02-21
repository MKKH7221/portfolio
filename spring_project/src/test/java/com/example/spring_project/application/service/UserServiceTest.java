package com.example.spring_project.application.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_project.domain.model.country.Country;
import com.example.spring_project.domain.model.user.NewUser;
import com.example.spring_project.domain.model.user.SearchUser;
import com.example.spring_project.domain.model.user.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private CountryService countryService;

    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void add_CanAddNewUser() {
        NewUser testUser1 = new NewUser("Sada Masashi", "via spring 123", "12345","JPN");
        userService.add(testUser1);

        Country expectedCountry = countryService.findByCode("JPN");

        User actual = userService.findAll().get(0);
        assertAll ("user",
            () -> assertEquals(testUser1.name(), actual.name()),
            () -> assertEquals(testUser1.address(), actual.address()),
            () -> assertEquals(testUser1.tel(), actual.tel()),
            () -> assertEquals(expectedCountry.name(), actual.country().name()),
            () -> assertEquals(expectedCountry.code(), actual.country().code())
        );
    }

    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void delete_CanDeleteUserById() {
        NewUser user1 = new NewUser("Sada Masashi", "via spring 123", "12345","ITA");
        userService.add(user1);

        User newUser = userService.findAll().get(0);

        userService.delete(newUser.id());
        List<User> actual = userService.findAll();
        int expected = 0;
        assertThat(actual.size(), is(expected));
    }

    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void delete_CanDeleteUserCorrectlyWhenTableHasMultipulData() {
        NewUser user1 = new NewUser("Sada Masashi 1", "via spring 123", "12345","JPN");
        NewUser user2 = new NewUser("Sada Masashi 2", "via spring 456", "12345","ITA");
        NewUser user3 = new NewUser("Sada Masashi 3", "via spring 789", "12345","GRC");
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        
        List<User> newUsers = userService.findAll();

        userService.delete(newUsers.get(0).id());
        User actual = userService.findById(newUsers.get(0).id());
        // int expected = 2;
        assertNull(actual);
    }

    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_CanFindUserUseById() {
        NewUser user = new NewUser("Sada Masashi", "via spring 123", "12345","ITA");
        userService.add(user);
        User expected = userService.findAll().get(0);
        User actual = userService.findById(expected.id());
        assertAll ("find one result",
            () -> assertEquals(expected.id(), actual.id()),
            () -> assertEquals(expected.name(), actual.name()),
            () -> assertEquals(expected.address(), actual.address()),
            () -> assertEquals(expected.tel(), actual.tel()),
            () -> assertEquals(expected.country().name(), actual.country().name()),
            () -> assertEquals(expected.country().code(), actual.country().code())
        );
    }
    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_CanFindUserByIdWhenTableHasMultipulData() {
        NewUser user1 = new NewUser("Sada Masashi 1", "via spring 123", "12345","GRC");
        NewUser user2 = new NewUser("Sada Masashi 2", "via spring 456", "12345","ESP");
        NewUser user3 = new NewUser("Sada Masashi 3", "via spring 789", "12345","JPN");
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        List<User> newUsers = userService.findAll();
        System.out.println(newUsers.size());
    
        User actual = userService.findById(newUsers.get(2).id());
        assertAll ("find one result",
            () -> assertEquals(newUsers.get(2).id(), actual.id()),
            () -> assertEquals(newUsers.get(2).name(), actual.name()),
            () -> assertEquals(newUsers.get(2).address(), actual.address()),
            () -> assertEquals(newUsers.get(2).tel(), actual.tel()),
            () -> assertEquals(newUsers.get(2).country().name(), actual.country().name()),
            () -> assertEquals(newUsers.get(2).country().code(), actual.country().code())
        );
    }

    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void update_CanUpdateUser_Name_Adderss_tel_country() {
        NewUser user1 = new NewUser("Sada Masashi", "via spring 123", "12345","ESP");
        userService.add(user1);
        User newUser = userService.findAll().get(0);

        Country newCountry = countryService.findByCode("ITA");
        User expected = new User(newUser.id(), 
                                "Francesco Assisi",
                                "via october 123", 
                                "5678910", 
                                newCountry);

        userService.update(expected);

        User actual = userService.findById(expected.id());
        assertAll ("find one result",
            () -> assertEquals(expected.id(), actual.id()),
            () -> assertEquals(expected.name(), actual.name()),
            () -> assertEquals(expected.address(), actual.address()),
            () -> assertEquals(expected.tel(), actual.tel()),
            () -> assertEquals(expected.country().name(), actual.country().name()),
            () -> assertEquals("ITA", actual.country().code())
        );
    }

// ---------------
// ---------------
    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void update_CanUpdateUserInformationCorrectllyWhenTableHasMultipulData() {
        NewUser user = new NewUser("Sada Masashi 1", "via spring 123", "12345","ESP");
        NewUser user1 = new NewUser("Sada Masashi 2", "via spring 123", "12345","JPN");
        NewUser user2 = new NewUser("Sada Masashi 3", "via spring 123", "12345","ITA");
        userService.add(user);
        userService.add(user1);
        userService.add(user2);
        User newUser = userService.findAll().get(1);

        User expected = new User(newUser.id(), 
        "Francesco Assisi",
        "via october 123", 
        "12345", 
        countryService.findByCode("ITA"));
        userService.update(expected);

        User actual = userService.findById(expected.id());
        assertAll ("find one result",
            () -> assertEquals(expected.id(), actual.id()),
            () -> assertEquals(expected.name(), actual.name()),
            () -> assertEquals(expected.address(), actual.address()),
            () -> assertEquals(expected.tel(), actual.tel()),
            () -> assertEquals(expected.country().name(), actual.country().name()),
            () -> assertEquals(expected.country().code(), actual.country().code())
        );
    }

    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_Condition_id() {
        NewUser user = new NewUser("Sada Masashi 1", "via spring 123", "12345","ESP");
        NewUser user1 = new NewUser("Sada Masashi 2", "via spring 123", "12345","JPN");
        NewUser user2 = new NewUser("Sada Masashi 3", "via spring 123", "12345","ITA");
        userService.add(user);
        userService.add(user1);
        userService.add(user2);
        User expected = userService.findAll().get(1);

        // SearchCondition id
        SearchUser condition = new SearchUser(expected.id(), null, null, null, null);
        List<User> actual = userService.findByCondition(condition);

        assertAll ("find one result",
            () -> assertEquals(expected.id(), actual.get(0).id()),
            () -> assertEquals(expected.name(), actual.get(0).name()),
            () -> assertEquals(expected.address(), actual.get(0).address()),
            () -> assertEquals(expected.tel(), actual.get(0).tel()),
            () -> assertEquals(expected.country().name(), actual.get(0).country().name()),
            () -> assertEquals(expected.country().code(), actual.get(0).country().code())
        );
    }

    @Test
    @Sql("/test_insert_user_list.sql")
    void findById_Condition_name() {
        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.name().equals("Masashi Sada")).collect(Collectors.toList());
        System.out.println(targetUserList);
        // 検索結果
        // ("Masashi Sada", "via Osaka 456, Osaka", "123457890","JPN");
        // ("Masashi Sada", "via Kyoto 456, Tokyo", "123457890","JPN");
        String expected1 = createExpectedUserToString(targetUserList.get(0));
        String expected2 = createExpectedUserToString(targetUserList.get(1));

        SearchUser condition = new SearchUser(null, "Masashi Sada", null, null, null);
        List<User> actual = userService.findByCondition(condition);
        Integer expected = 2; 
        assertThat(actual.size(), is(expected));
        assertThat(actual.get(0).toString(), is(expected1));
        assertThat(actual.get(1).toString(), is(expected2));

    }
    @Test
    @Sql("/test_insert_user_list.sql")
    void findById_Condition_address() {
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Nalendora Modhi", "via winter 123, New Delhi", "678912345","IND");

        User targetUser = userService.findAll().get(6);
        // SearchCondition id
        SearchUser condition = new SearchUser(null, targetUser.name(), null, null, null);
        List<User> actual = userService.findByCondition(condition);
        Integer expectedSize = 1; 
        String expected = createExpectedUserToString(targetUser); 
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected));
    }
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_Condition_tel_123457890() {
        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.tel().equals("123457890")).collect(Collectors.toList());

// INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Masashi Sada", "via Osaka 456, Osaka", "123457890","JPN");
// INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Shinji Tanimura", "via Tokyo 456, Tokyo", "123457890","JPN");
// INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Masashi Sada", "via Kyoto 456, Tokyo", "123457890","JPN");

        SearchUser condition = new SearchUser(null, null, null, "123457890", null);
        List<User> actual = userService.findByCondition(condition);
        Integer expectedSize = 3; 
        String expected1 = createExpectedUserToString(targetUserList.get(0)); 
        String expected2 = createExpectedUserToString(targetUserList.get(1)); 
        String expected3 = createExpectedUserToString(targetUserList.get(2)); 
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected1));
        assertThat(actual.get(1).toString(), is(expected2));
        assertThat(actual.get(2).toString(), is(expected3));
    }
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_Condition_country_ITA() {
        List<User> userList = userService.findAll();
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Galileo Galilei", "via winter 123, Rome", "678912345","ITA");
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Leonardo da Vinci", "via summer 789, Firenze", "789123456","ITA");        
        List<User> targetUserList = userList.stream().
            filter(user -> user.country().code().equals("ITA")).collect(Collectors.toList());

        SearchUser condition = new SearchUser(null, null, null, null, "ITA");
        List<User> actual = userService.findByCondition(condition);

        Integer expectedSize = 2; 
        String expected1 = createExpectedUserToString(targetUserList.get(0)); 
        String expected2 = createExpectedUserToString(targetUserList.get(1)); 
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected1));
        assertThat(actual.get(1).toString(), is(expected2));
    }
    // id_name_address_tel_country
    @Test
    @Sql("/test_insert_user_list.sql")
    void findById_Condition_all() {
        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.name().equals("Galileo Galilei")).
            filter(user -> user.address().equals("via winter 123, Rome")).
            filter(user -> user.tel().equals("678912345")).
            filter(user -> user.country().code().equals("ITA")).
            collect(Collectors.toList());

        // 検索対象ユーザ
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Galileo Galilei", "via winter 123, Rome", "678912345","ITA");
        SearchUser condition = new SearchUser(targetUserList.get(0).id(), 
            targetUserList.get(0).name(), 
            targetUserList.get(0).address(), 
            targetUserList.get(0).tel(), 
            targetUserList.get(0).country().code());
        
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1;
        String expected = createExpectedUserToString(targetUserList.get(0));

        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected));
    }

    // id_name
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_Condition_IdAndName() {
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Nalendora Modhi", "via winter 123, New Delhi", "678912345","IND");
        // 検索対象ユーザ
        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.name().equals("Nalendora Modhi")).
            collect(Collectors.toList());
        // 検索条件指定
        SearchUser condition = new SearchUser(targetUserList.get(0).id(),targetUserList.get(0).name(), null, null, null);
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1; 
        String expected = createExpectedUserToString(targetUserList.get(0));
        
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected));
    }

    // address_country
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_Condition_AddressAndCountry() {
        // 検索対象ユーザ
        //  ("Masashi Sada", "via Osaka 456, Osaka", "123457890","JPN");
        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.address().equals("via Osaka 456, Osaka")).
            filter(user -> user.country().code().equals("JPN")).
            collect(Collectors.toList());
        // 検索条件指定
        SearchUser condition = new SearchUser(null, null, "via Osaka 456, Osaka", null, "JPN");
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1; 
        String expected = createExpectedUserToString(targetUserList.get(0));
        
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected));
    }

    // id_name_address
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_Condition_IdNameAddress() {
        // 検索対象ユーザ
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Galileo Galilei", "via winter 123, Tange", "678912345","ESP");
        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.name().equals("Galileo Galilei")).
            filter(user -> user.address().equals("via winter 123, Tange")).
            collect(Collectors.toList());

        SearchUser condition = new SearchUser(targetUserList.get(0).id(), targetUserList.get(0).name(), 
            targetUserList.get(0).address(), null, null);
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1; 
        String expected = createExpectedUserToString(targetUserList.get(0));
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected));
    }

    // name_address_tel
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_Condition_NameAddressTel() {
        // 検索対象ユーザ
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Mahatoma Gandhi", "via winter 123, Rishikesh", "338912345","IND");
        List<User> userList = userService.findAll();
        List<User> expected = userList.stream().
            filter(user -> user.name().equals("Mahatoma Gandhi")).
            filter(user -> user.address().equals("via winter 123, Rishikesh")).
            filter(user -> user.tel().equals("338912345")).
            collect(Collectors.toList());

        SearchUser condition = new SearchUser(null, expected.get(0).name(), 
            expected.get(0).address(), expected.get(0).tel(), null);
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1; 
        String expected1 = createExpectedUserToString(expected.get(0));
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected1));
    }

    // address_tel_coutry
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_Condition_AddressTelCountry() {
        // 検索対象ユーザ
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Mahatoma Gandhi", "via winter 123, Rishikesh", "338912345","IND");
        List<User> userList = userService.findAll();
        List<User> expected = userList.stream().
            filter(user -> user.address().equals("via winter 123, Rishikesh")).
            filter(user -> user.tel().equals("338912345")).
            filter(user -> user.country().code().equals("IND")).
            collect(Collectors.toList());

        SearchUser condition = new SearchUser(null, null, 
            expected.get(0).address(), expected.get(0).tel(), expected.get(0).country().code());
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1; 
        String expected1 = createExpectedUserToString(expected.get(0));
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected1));
    }

    private String createExpectedUserToString(User user) {

        return "User[id=" + user.id() 
        + ", name=" + user.name() 
        + ", address="+ user.address() 
        + ", tel=" + user.tel() 
        +", country=Country[name="+ user.country().name()
                        +", code="+user.country().code()+"]]";

    }
}
