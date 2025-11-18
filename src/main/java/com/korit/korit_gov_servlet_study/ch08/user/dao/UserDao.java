package com.korit.korit_gov_servlet_study.ch08.user.dao;

import com.korit.korit_gov_servlet_study.ch08.user.entity.User;
import com.korit.korit_gov_servlet_study.ch08.user.util.ConnectionFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static UserDao instance;

    private UserDao() {}

    public static UserDao getInstance() {
        if (instance == null) instance = new UserDao();
        return instance;
    }

    public User findUserByUsername(String username) {
        String sql = "select * from user_tb where username = ?";
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
        ) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? toUser(rs) : null;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User addUser(User user) {
        System.out.println("0     " + user.toString());
        String sql = "insert into user_tb values (0, ?, ?, ?, now())";
        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {
            System.out.println("1     " + user.toString());
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getAge());

            ps.execute();
            System.out.println("2     " + user.toString());
            try (ResultSet rs = ps.getGeneratedKeys()) {
                System.out.println("3     " + user.toString());
                if (rs.next()) {
                    System.out.println("4     " + user.toString());
                    Integer userId = rs.getInt(1);
                    System.out.println("zxcvzxcv" + userId);
                    user.setUserId(userId);
                    System.out.println("qwerqwer" + user);
                }
            }
            System.out.println(user);
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> findUserByKeyword(String keyword) {
        String sql = "select user_id,username,password,age,create_dt from user_tb where username like ?";
        List<User> foundUserList = new ArrayList<>();

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
                ){
            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    foundUserList.add(toUser(rs));
                }
            }
            return foundUserList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
//        return foundUserList;
    }

    public List<User> findAllUserList() {
        List<User> foundUserList = new ArrayList<>();
        String sql = "select user_id, username, password, age, create_dt from user_tb";

        try (
                Connection con = ConnectionFactory.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ) {
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    foundUserList.add(toUser(rs));
                    foundUserList.forEach(System.out::println);
                    System.out.println();
                }
                return foundUserList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User toUser(ResultSet rs) throws SQLException {
        return User.builder()
                .userId(rs.getInt("user_id"))
                .username(rs.getString("username"))
                .password(rs.getString("password"))
                .age(rs.getInt("age"))
                .createDt(rs.getTimestamp("create_dt").toLocalDateTime())
                .build();
    }
}
