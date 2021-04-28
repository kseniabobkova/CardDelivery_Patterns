package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Data;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;



    public final class DataGenerator {

        private DataGenerator() {
        }

        public static String getCity() {
        List<String> cityList = Arrays.asList( "Санкт-Петербург", "Москва","Казань","Петрозаводск", "Краснодар","Владимир","Воронеж","Калининград", "Кострома","Нижний Новгород","Великий Новгород","Псков","Ростов-на-Дону","Тверь","Ярославль","Севастополь");
        Random random = new Random();
        String city = cityList.get(random.nextInt(cityList.size()));

        return city;
    }

        public static String getDate(int daysToAdd) {
        LocalDate date = LocalDate.now().plusDays(daysToAdd);
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

        public static String getName() {
        Faker faker = new Faker(new Locale("ru"));
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return name;
    }

        public static String getPhone() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }

}
