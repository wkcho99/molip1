package com.example.week1.ui.main;

public class PhoneBook{
    private String id;
    private String name;
    private String tel;
    private Long photo;
    private Long personId;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Long getPhoto() {
        return photo;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public Long getPersonId() {
        return personId;
    }

    public PhoneBook(String id, String name, String tel, Long photo_id, Long person_id) {
        this.id = id;
        this.name = name;
        this.tel = tel;
        this.photo = photo_id;
        this.personId = person_id;
    }
}

