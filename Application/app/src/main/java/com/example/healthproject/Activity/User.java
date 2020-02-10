package com.example.healthproject.Activity;

public class User {
    public String username,email;
    public Boolean isAdmin;


            public User(){

            }

            public User(String username, String email, Boolean isAdmin){
                this.username = username;
                this.email = email;
                this.isAdmin = isAdmin;

            }

            public String getUsername(){
                return username;
            }

            public String getEmail(){
                return email;
            }

            public Boolean getIsAdmin(){return isAdmin;}
}
