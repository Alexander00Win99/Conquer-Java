package com.conquer_java.spring.pojo;

import java.util.*;

public class Student {
    private int id;
    private String name;
    private Date birthdate;
    private Address hometown;
    private String[] courses;
    private Map<String, String> scores;
    private Map<String, String> creditCards;
    private List<String> books;
    private List<String> songs;
    private List<String> movies;
    private List<String> sports;
    private List<String> games;
    private Set<List<String>> hobbies;
    private Properties info;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Address getHometown() {
        return hometown;
    }

    public void setHometown(Address hometown) {
        this.hometown = hometown;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }

    public Map<String, String> getScores() {
        return scores;
    }

    public void setScores(Map<String, String> scores) {
        this.scores = scores;
    }

    public Map<String, String> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Map<String, String> creditCards) {
        this.creditCards = creditCards;
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

    public List<String> getGames() {
        return games;
    }

    public void setGames(List<String> games) {
        this.games = games;
    }

    public Set<List<String>> getHobbies() {
        return hobbies;
    }

    public void setHobbies(Set<List<String>> hobbies) {
        this.hobbies = hobbies;
    }

    public Properties getInfo() {
        return info;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", hometown=" + hometown.toString() +
                ", courses=" + Arrays.toString(courses) +
                ", scores=" + scores +
                ", creditCards=" + creditCards +
                ", books=" + books +
                ", songs=" + songs +
                ", movies=" + movies +
                ", sports=" + sports +
                ", games=" + games +
                ", hobbies=" + hobbies +
                ", info=" + info +
                '}';
    }
}
