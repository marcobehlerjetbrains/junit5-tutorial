package com.jetbrains.marco;

import java.io.InputStream;
import java.util.Scanner;

public class StringUtil {

    public static String readAsString(String path) {
	   try (final InputStream is = UserTest.class.getResourceAsStream("/" + path)) {
		  Scanner s = new Scanner(is).useDelimiter("\\A");
		  String result = s.hasNext() ? s.next() : "";
		  return result;
	   } catch (Exception e) {
		  throw new IllegalStateException(e);
	   }
    }
}
