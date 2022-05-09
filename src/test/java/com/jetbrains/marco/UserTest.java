package com.jetbrains.marco;


import com.jetbrains.util.Resources;
import com.jetbrains.util.Xml;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xmlunit.assertj.XmlAssert;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserTest {

    //@Mock
    User user;

    @BeforeEach
    void setup() {
      //  user = new User("Marco", 37, false, LocalDate.now().minusYears(37));
        System.out.println("Setup was called");
    }

    @AfterEach
    void cleanup() {
        user = null;
        System.out.println("Cleanup was called");
    }

    @Test
    @DisplayName("User should be at least 18")
    void user_should_be_at_least_18() {
      assertThat(user.age()).isGreaterThanOrEqualTo(18);

    /*  assertThat(user.blocked())
              .as("User %s should be blocked", user.name())
              .isTrue();*/
        assertThatJson(user).isEqualTo("{\"name\":\"Marco\",\"age\":37,\"blocked\":false,\"birthDate\":[1985, 5, 4]}");

        XmlAssert.assertThat( "<a><b attr=\"abc\"></b></a>").nodesByXPath("//a/b/@attr").exist();


    }

    @ParameterizedTest
    // @ValueSource(ints = {20, 50, 80})
    @CsvFileSource(resources = "/friends.csv", numLinesToSkip = 1)
    //@EnumSource()
    void all_friends_should_at_least_be_18(String name, int age) {
        assertThat(age).isGreaterThanOrEqualTo(18);
    }

    @TestFactory
    Collection<DynamicTest> dynamicTestsCreatedThroughCode() {
        List<Xml> xmls = Resources.toStrings("users.*\\.xml");

        return xmls.stream().map(xml -> DynamicTest.dynamicTest(xml.name(), () -> {
            XmlAssert.assertThat(xml.content()).hasXPath("/users/user/name");
        })).collect(Collectors.toList());
    }

    @Test
    void user_should_be_marco() {
        assertThat(user.name()).startsWith("Mar");
    }
}
