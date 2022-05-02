package com.jetbrains.marco;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xmlunit.assertj.XmlAssert;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
class UserTest {

    private static User marco;

    @BeforeEach
     void setup() {
	   marco = new User("Marco", 18, false, LocalDate.now().minusYears(18));
	   System.out.println("setup was called");
    }

    @Test
    @DisplayName("Marco should be 18 years old, or he cannot go to the movies")

    void user_should_be_18_years() {
	   assertThat(marco.age()).isGreaterThanOrEqualTo(18);
	   assertThat(marco.disabled()).as("check %s's account status", marco.name()).isFalse();
	   assertThat(marco.name()).startsWith("Mar");

	   assertThatJson(marco).isEqualTo("{\"name\":\"Marco\",\"age\":18,\"disabled\":false,\"born\":[2004,5,2]}");
	   XmlAssert.assertThat( "<a><b attr=\"abc\"></b></a>").nodesByXPath("//a/b/@attr").exist();
    }


    @ParameterizedTest()
    @CsvFileSource(resources = "/friends.csv", numLinesToSkip = 1)
    //@CsvSource("lisa,18"")
    //@ValueSource(ints = {20, 25, 30})
    void marcos_friends_should_be_older_than_18(String name, Integer age) {
	   assertThat(age).isGreaterThanOrEqualTo(18).as(name + " is older than marco!");
    }

    @TestFactory
    Collection<DynamicTest> dynamicTests() {
	   final ResourceUtil util = new ResourceUtil();
	   final List<String> xmlPaths = util.getXMLs("/");
	   List<DynamicTest> result = new ArrayList<>();
	   xmlPaths.forEach(xml -> result.add(DynamicTest.dynamicTest(xml, () -> XmlAssert.assertThat(StringUtil.readAsString(xml)).hasXPath("/users/user/name"))));
	   return result;
    }



    @AfterEach
    void shutdown() {
	   marco = null;
    }

}
