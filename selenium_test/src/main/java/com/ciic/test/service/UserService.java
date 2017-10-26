package com.ciic.test.service;

import com.ciic.test.bean.User;


import java.util.List;
import java.util.Map;

/**
 * Created by lixuecheng on 2017/7/4.
 */
public interface UserService {
//    List<user> selectUser(String email, String password);
//    int isUsingUser(String email, String password);
//    List<user> getUser();
//    int adduser(String name,String email,String password,String manage);
//    int jinyongUser(String id);
//    boolean isManager(String id);
//    int updateUser(String name,String email,String password,String manage,String id);
//    int updateUserNopass(String name,String email,String manage,String id);
//    List<user> getoneuser(String id);
    User getUserByEmail(String email);
    User getUserById(String id);
//    List<user> getUsersByItemid(String tid);
    List<User> getAllUsers();
    List<User> addUser(String name,String email,String manage);
    List<User> updateUserWithoutPassword(String name,String email,String manage,String id);
    List<User> deleteUserByid(String id);
    int updateLastTime(String id);
    Map<String,List<String>> getUser4Item();





}
