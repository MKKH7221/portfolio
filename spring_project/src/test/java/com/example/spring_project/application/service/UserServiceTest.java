package com.example.spring_project.application.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_project.application.errors.UserNotFoundException;
import com.example.spring_project.domain.model.country.Country;
import com.example.spring_project.domain.model.user.Address;
import com.example.spring_project.domain.model.user.Id;
import com.example.spring_project.domain.model.user.Name;
import com.example.spring_project.domain.model.user.NewUser;
import com.example.spring_project.domain.model.user.SearchUser;
import com.example.spring_project.domain.model.user.Tel;
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
    void add_success() {
        NewUser testUser1 = new NewUser(
            new Name("Sada Masashi"), 
            new Address("via spring 123"), 
            new Tel("12345"),
            new Country("", "JPN"));

        userService.add(testUser1);

        Country expectedCountry = countryService.findByCode("JPN");

        User actual = userService.findAll().get(0);
        assertAll ("user",
            () -> assertEquals(testUser1.name().value(), actual.name().value()),
            () -> assertEquals(testUser1.address().value(), actual.address().value()),
            () -> assertEquals(testUser1.tel().value(), actual.tel().value()),
            () -> assertEquals(expectedCountry.name(), actual.country().name()),
            () -> assertEquals(expectedCountry.code(), actual.country().code())
        );
    }

    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void delete_success() {
        NewUser user1 = new NewUser(
            new Name("Sada Masashi"), 
            new Address("via spring 123"), 
            new Tel("12345"),
            new Country("", "ITA"));
        userService.add(user1);
        User newUser = userService.findAll().get(0);
        userService.delete(newUser.id().value());
        List<User> actual = userService.findAll();
        int expected = 0;
        assertThat(actual.size(), is(expected));
    }

    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void delete_success_with_multipul_data() {
        NewUser user1 = new NewUser(new Name("Sada Masashi 1"), new Address("via spring 123"), new Tel("12345"),new Country("","JPN"));
        NewUser user2 = new NewUser(new Name("Sada Masashi 2"), new Address("via spring 456"), new Tel("12345"),new Country("","ITA"));
        NewUser user3 = new NewUser(new Name("Sada Masashi 3"), new Address("via spring 789"), new Tel("12345"),new Country("","GRC"));
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        
        List<User> newUsers = userService.findAll();
        userService.delete(newUsers.get(0).id().value());

        assertThrows(UserNotFoundException.class, () -> {
            userService.findById(newUsers.get(0).id().value());
        });
    }

    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_success() {
        NewUser user = new NewUser(new Name("Sada Masashi"), new Address("via spring 123"), new Tel("12345"),new Country("","ITA"));
        userService.add(user);
        User expected = userService.findAll().get(0);
        User actual = userService.findById(expected.id().value());
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
    void findById_exception_userNotFound() {

        assertThrows(UserNotFoundException.class, () -> {
            userService.findById(9999);
        });
    }

    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_success_WhenTableHasMultipulData() {
        NewUser user1 = new NewUser(new Name("Sada Masashi 1"), new Address("via spring 123"), new Tel("12345"),new Country("", "JPN"));
        NewUser user2 = new NewUser(new Name("Sada Masashi 2"), new Address("via spring 456"), new Tel("12345"),new Country("", "ITA"));
        NewUser user3 = new NewUser(new Name("Sada Masashi 3"), new Address("via spring 789"), new Tel("12345"),new Country("", "GRC"));
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        List<User> newUsers = userService.findAll();
        System.out.println(newUsers.size());
    
        User actual = userService.findById(newUsers.get(2).id().value());
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
    void update_success_CanUpdateUser_Name_Adderss_tel_country() {
        NewUser user1 = new NewUser(new Name("Sada Masashi 1"), new Address("via spring 123"), new Tel("12345"),new Country("", "JPN"));
        userService.add(user1);
        User newUser = userService.findAll().get(0);

        Country newCountry = countryService.findByCode("ITA");
        User expected = new User(newUser.id(), 
                                new Name("Francesco Assisi"),
                                new Address("via october 123"), 
                                new Tel("5678910"), 
                                newCountry);

        userService.update(expected);

        User actual = userService.findById(expected.id().value());
        assertAll ("find one result",
            () -> assertEquals(expected.id(), actual.id()),
            () -> assertEquals(expected.name(), actual.name()),
            () -> assertEquals(expected.address(), actual.address()),
            () -> assertEquals(expected.tel(), actual.tel()),
            () -> assertEquals(expected.country().name(), actual.country().name()),
            () -> assertEquals("ITA", actual.country().code())
        );
    }

@Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void update_success_CanUpdateUserCorrectllyWhenTableHasMultipulData() {
        NewUser user1 = new NewUser(new Name("Sada Masashi 1"), new Address("via spring 123"), new Tel("12345"), new Country("", "JPN"));
        NewUser user2 = new NewUser(new Name("Sada Masashi 2"), new Address("via spring 456"), new Tel("12345"), new Country("", "ITA"));
        NewUser user3 = new NewUser(new Name("Sada Masashi 3"), new Address("via spring 789"), new Tel("12345"), new Country("", "GRC"));
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        User newUser = userService.findAll().get(1);

        User expected = new User(newUser.id(), 
            new Name("Francesco Assisi"),
            new Address("via october 123"), 
            new Tel("12345") , 
        countryService.findByCode("ITA"));
        userService.update(expected);

        User actual = userService.findById(expected.id().value());
        assertAll ("find one result",
            () -> assertEquals(expected.id(), actual.id()),
            () -> assertEquals(expected.name(), actual.name()),
            () -> assertEquals(expected.address(), actual.address()),
            () -> assertEquals(expected.tel(), actual.tel()),
            () -> assertEquals(expected.country().name(), actual.country().name()),
            () -> assertEquals(expected.country().code(), actual.country().code())
        );
    }

    // Search Condition Test
    @Test
    @Sql("/test.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findByCondition_Condition_id() {
        NewUser user1 = new NewUser(new Name("Sada Masashi 1"), new Address("via spring 123"), new Tel("12345"), new Country("", "JPN"));
        NewUser user2 = new NewUser(new Name("Sada Masashi 2"), new Address("via spring 456"), new Tel("12345"), new Country("", "ITA"));
        NewUser user3 = new NewUser(new Name("Sada Masashi 3"), new Address("via spring 789"), new Tel("12345"), new Country("", "GRC"));
        userService.add(user1);
        userService.add(user2);
        userService.add(user3);
        User expected = userService.findAll().get(1);

        // SearchCondition id
        SearchUser condition = new SearchUser(expected.id().value(), null, null, null, null);
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
    void findByCondition_Condition_name() {
        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.name().value().contains("Masashi")).
            collect(Collectors.toList());

        System.out.println(targetUserList);
        String expected1 = targetUserList.get(0).toString();
        String expected2 = targetUserList.get(1).toString();

        SearchUser condition = new SearchUser(null, "Masashi", null, null, null);
        List<User> actual = userService.findByCondition(condition);
        Integer expected = 2; 
        assertThat(actual.size(), is(expected));
        assertThat(actual.get(0).toString(), is(expected1));
        assertThat(actual.get(1).toString(), is(expected2));

    }
    @Test
    @Sql("/test_insert_user_list.sql")
    void findByCondition_Condition_address() {

        String address = "Tokyo";
        List<User> userList = userService.findAll();

        List<User> targetUserList = userList.stream().
            filter(user -> user.address().value().contains(address))
                .collect(Collectors.toList());
        String expected1 = targetUserList.get(0).toString();
        String expected2 = targetUserList.get(1).toString();

        // SearchCondition id
        SearchUser condition = new SearchUser(null, null, address, null, null);
        List<User> actual = userService.findByCondition(condition);
        Integer expectedSize = 2; 
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected1));
        assertThat(actual.get(1).toString(), is(expected2));
    }
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findByCondition_Condition_tel_123457890() {
        String searchTel = "123457";
        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.tel().value().contains(searchTel)).collect(Collectors.toList());

        SearchUser condition = new SearchUser(null, null, null, searchTel, null);
        List<User> actual = userService.findByCondition(condition);
        Integer expectedSize = 3; 
        String expected1 = targetUserList.get(0).toString(); 
        String expected2 = targetUserList.get(1).toString(); 
        String expected3 = targetUserList.get(2).toString(); 
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected1));
        assertThat(actual.get(1).toString(), is(expected2));
        assertThat(actual.get(2).toString(), is(expected3));
    }
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findByCondition_Condition_country_ITA() {
        String country = "ITA";
        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.country().code().equals(country)).collect(Collectors.toList());

        SearchUser condition = new SearchUser(null, null, null, null, country);
        List<User> actual = userService.findByCondition(condition);

        Integer expectedSize = 2; 
        String expected1 = targetUserList.get(0).toString(); 
        String expected2 = targetUserList.get(1).toString(); 
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected1));
        assertThat(actual.get(1).toString(), is(expected2));
    }
    // id_name_address_tel_country
    @Test
    @Sql("/test_insert_user_list.sql")
    void findByCondition_Condition_all() {
        String name = "Galileo";
        String address = "Rome";
        String tel = "678";
        String country = "ITA";

        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.name().value().contains(name)).
            filter(user -> user.address().value().contains(address)).
            filter(user -> user.tel().value().contains(tel)).
            filter(user -> user.country().code().equals(country)).
            collect(Collectors.toList());

        // 検索対象ユーザ
        SearchUser condition = new SearchUser(targetUserList.get(0).id().value(), name, address, tel, country); 
        
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1;
        String expected = targetUserList.get(0).toString();
        
        System.out.println("actual:" + actual.get(0).toString());
        System.out.println("expect:" + expected);
 
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected));
    }

    // id_name
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findByCondition_Condition_IdAndName() {
        String name = "Modhi";
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Nalendora Modhi", "via winter 123, New Delhi", "678912345","IND");
        // 検索対象ユーザ
        List<User> userList = userService.findAll();
        List<User> targetUserList = userList.stream().
            filter(user -> user.name().value().contains(name)).
            collect(Collectors.toList());
        // 検索条件指定
        SearchUser condition = new SearchUser(targetUserList.get(0).id().value(),name, null, null, null);
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1; 
        String expected = targetUserList.get(0).toString();
        
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected));
    }

    // address_country
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findByCondition_Condition_AddressAndCountry() {
        // 検索対象ユーザ
        //  ("Masashi Sada", "via Osaka 456, Osaka", "123457890","JPN");
        String address = "Osaka";
        String country = "JPN";
        List<User> userList = userService.findAll();
        for (User u : userList) {
            System.out.println(u.toString());
        }

        List<User> targetUserList = userList.stream().
            filter(user -> user.address().value().contains(address)).
            filter(user -> user.country().code().equals(country)).
            collect(Collectors.toList());
            System.out.println("targetUserList :" + targetUserList.size());
            
        // 検索条件指定
        SearchUser condition = new SearchUser(null, null, address, null, country);
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1; 
        String expected = targetUserList.get(0).toString();        
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected));
    }

    // id_name_address
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findByCondition_Condition_IdNameAddress() {
        String name = "Galileo";
        String address = "Tange";
        List<User> userList = userService.findAll();
        List<User> expected = userList.stream().
            filter(user -> user.name().value().contains(name)).
            filter(user -> user.address().value().contains(address)).
            collect(Collectors.toList());

        SearchUser condition = new SearchUser(expected.get(0).id().value(), name, address, null, null);
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1; 
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected.get(0).toString()));
    }

    // name_address_tel
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findByCondition_Condition_NameAddressTel() {
        String name ="Gandhi";
        String address ="Rishikesh";
        String tel = "912";

        // 検索対象ユーザ
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Mahatoma Gandhi", "via winter 123, Rishikesh", "338912345","IND");
        List<User> userList = userService.findAll();
        List<User> expected = userList.stream().
            filter(user -> user.name().value().contains(name)).
            filter(user -> user.address().value().contains(address)).
            filter(user -> user.tel().value().contains(tel)).
            collect(Collectors.toList());

        SearchUser condition = new SearchUser(null, name, address, tel, null);
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1; 
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected.get(0).toString()));
    }

    // address_tel_coutry
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findByCondition_Condition_AddressTelCountry() {
        String address = "Rishikesh";
        String tel = "891";
        String country = "IND";
        // 検索対象ユーザ
        // INSERT INTO testdb.user( name, address, tel, country ) VALUES ("Mahatoma Gandhi", "via winter 123, Rishikesh", "338912345","IND");
        List<User> userList = userService.findAll();
        List<User> expected = userList.stream().
            filter(user -> user.address().value().contains(address)).
            filter(user -> user.tel().value().contains(tel)).
            filter(user -> user.country().code().equals(country)).
            collect(Collectors.toList());

        SearchUser condition = new SearchUser(null, null, address, tel, country);
        // 検索    
        List<User> actual = userService.findByCondition(condition);
        // 検証
        Integer expectedSize = 1; 
        String expected1 = expected.get(0).toString();
        assertThat(actual.size(), is(expectedSize));
        assertThat(actual.get(0).toString(), is(expected1));
    }

    // 更新時のエラー　 存在しないユーザーID　
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void updateError_userNotFound() {
        User user = new User (
                    new Id(9999), 
                    new Name("Test User"), 
                    new Address("12 Test Address"), 
                    new Tel ("1234498432"), 
                    new Country("Japan", "JPN"));

        assertThrows(UserNotFoundException.class, () -> {
            userService.update(user);
        });
    }

    // 削除時のエラー 存在しないユーザーID　
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void delete_Error_userNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.delete(9999);
        });
    }

    // ID検索時のエラー 存在しないユーザーID　
    @Test
    @Sql("/test_insert_user_list.sql") // @Sql()の中には`src/main/resources`以下のパスを書く
    void findById_Error_userNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.findById(9999);
        });
    }


    
}
