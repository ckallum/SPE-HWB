package com.example.healthproject.Activity;

public class User {
    public String username,email;


            public User(){

            }

            public User(String username, String email){
                this.username = username;
                this.email = email;

            }

            public String getUsername(){
                return username;
            }

            public String getEmail(){
                return email;
            }
}
