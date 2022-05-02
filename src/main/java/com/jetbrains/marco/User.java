package com.jetbrains.marco;

import java.time.LocalDate;

public record User(String name, Integer age, Boolean disabled, LocalDate born) {
}
