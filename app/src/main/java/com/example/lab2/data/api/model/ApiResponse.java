//package com.example.lab2.data.api.model;
//
//
//public class ApiResponse {
//    private String message;
////    private String data;
//private String token;
//
//    // Getters and setters
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
////    public String getData() {
////        return data;
////    }
////
////    public void setData(String data) {
////        this.data = data;
////    }
//public String getToken() {
//    return token;
//}
//
//    public void setToken(String token) {
//        this.token = token;
//    }
//
//}

package com.example.lab2.data.api.model;

public class ApiResponse {
    private String message;
    private String access_token; // Match the JSON field name
    private User user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Nested User class for the user object in the response
    public static class User {
        private int id;
        private String name;
        private String email;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}