package com.conquer_java.springboot.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

// JavaConfig绑定配置文件，可以采取多种方式
@Component
@ConfigurationProperties(prefix = "person")
//@PropertySource(value = "classpath:alex.properties")
@Validated //数据校验
public class Person {
    //@Value("${姓名}")
    private String name;
    //@Value("${性别}")
    private String gender;
    private Date birthdate;
    private Map<String, String> creditCards;
    private Set<List<String>> hobbies;
    private List<String> books;
    private List<String> songs;
    private List<String> movies;
    private List<String> sports;
    private Dog dog;
    @Email(regexp = "[a-zA-Z_]{1,}[0-9]{0,}@qq.com", message = "邮箱格式不符合要求！")
    private String username;
    private String password;

    public Person() {}

    public Person(String name, String gender, Date birthdate, Map<String, String> creditCards, Set<List<String>> hobbies, List<String> books, List<String> songs, List<String> movies, List<String> sports, Dog dog) {
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.creditCards = creditCards;
        this.hobbies = hobbies;
        this.books = books;
        this.songs = songs;
        this.movies = movies;
        this.sports = sports;
        this.dog = dog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Map<String, String> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Map<String, String> creditCards) {
        this.creditCards = creditCards;
    }

    public Set<List<String>> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<List<String>> hobbies) {
        this.hobbies = hobbies;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    public List<String> getSongs() {
        return songs;
    }

    public void setSongs(List<String> songs) {
        this.songs = songs;
    }

    public List<String> getMovies() {
        return movies;
    }

    public void setMovies(List<String> movies) {
        this.movies = movies;
    }

    public List<String> getSports() {
        return sports;
    }

    public void setSports(List<String> sports) {
        this.sports = sports;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate=" + birthdate +
                ", creditCards=" + creditCards +
                ", hobbies=" + hobbies +
                ", books=" + books +
                ", songs=" + songs +
                ", movies=" + movies +
                ", sports=" + sports +
                ", dog=" + dog +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
