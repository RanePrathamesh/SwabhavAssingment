package com.aurionpro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.aurionpro.model.User;
import com.aurionpro.utils.DatabaseSource;

public class UserDaoImpl implements IUserDao {

    private Connection connection;

    public UserDaoImpl() {
        connection = DatabaseSource.getinstance().getConnection();
    }

    @Override
    public boolean registerUser(User user) {
        String userInsert = "insert into users(first_name, last_name, email, mobile_number, age, gender, address, is_active, is_first_login, role) values(?,?,?,?,?,?,?,?,?,?)";
        String authInsert = "insert into auth(username, password, user_id) values(?,?,?)";
        String accountInsert = "INSERT INTO accounts(user_id, account_number, balance, is_approved) VALUES(?,?,?,?)";
        try {
           
            PreparedStatement stmt = connection.prepareStatement(userInsert, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getMobileNumber());
            stmt.setInt(5, user.getAge());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getAddress());
            stmt.setBoolean(8, user.isActive());       
            stmt.setBoolean(9, user.isFirstLogin());  
            stmt.setString(10, user.getRole());    

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();// this will give the keys generated after the inserton on the data . like we nedded userid.
                if (rs.next()) {
                    int userId = rs.getInt(1);

                   // we are inserting in authentication
                    PreparedStatement authenticationStmt = connection.prepareStatement(authInsert);
                    authenticationStmt.setString(1, user.getFirstName().toLowerCase());
                    authenticationStmt.setString(2, user.getFirstName().toLowerCase() + "@123");
                    authenticationStmt.setInt(3, userId);

                    authenticationStmt.executeUpdate();
                    // Insert into accounts table (account_number is NULL, approval pending)
                    PreparedStatement accStmt = connection.prepareStatement(accountInsert);
                    accStmt.setInt(1, userId);
                    String tempAccountNumber = "PENDING-";
                    accStmt.setString(2, tempAccountNumber);

                    accStmt.setDouble(3, 0.00);
                    accStmt.setBoolean(4, false);
                    accStmt.executeUpdate();
                   
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error occurred.."+e.getMessage());
        }

        return false;
    }

    @Override
    public boolean isEmailOrMobileExists(String email, String mobileNumber) {
        String query = "SELECT user_id FROM users WHERE email = ? OR mobile_number = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, mobileNumber);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // If a result exists, either email or mobile already exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        String query = "select * from users where email = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("mobile_number"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("address")
                );
                user.setUserId(rs.getInt("user_id"));
                user.setActive(rs.getBoolean("is_active"));
                user.setFirstLogin(rs.getBoolean("is_first_login"));
                user.setRole(rs.getString("role"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                return user;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public User checkLoginCredintials(String username ,String password) {
		User user = null;

		String sql = "select u.* from users u join auth a on a.user_id = u.user_id where a.username= ? and password =?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					 user = new User(
							 resultSet.getString("first_name"),
							 resultSet.getString("last_name"),
							 resultSet.getString("email"),
							 resultSet.getString("mobile_number"),
							 resultSet.getInt("age"),
							 resultSet.getString("gender"),
							 resultSet.getString("address")
		                );
		                user.setUserId(resultSet.getInt("user_id"));
		                user.setActive(resultSet.getBoolean("is_active"));
		                user.setFirstLogin(resultSet.getBoolean("is_first_login"));
		                user.setRole(resultSet.getString("role"));
		                user.setCreatedAt(resultSet.getTimestamp("created_at"));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
	@Override
	public boolean updateUser(User user) {
	    String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, mobile_number = ?, age = ?, gender = ?, address = ? WHERE user_id = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, user.getFirstName());
	        stmt.setString(2, user.getLastName());
	        stmt.setString(3, user.getEmail());
	        stmt.setString(4, user.getMobileNumber());
	        stmt.setInt(5, user.getAge());
	        stmt.setString(6, user.getGender());
	        stmt.setString(7, user.getAddress());
	        stmt.setInt(8, user.getUserId());

	        return stmt.executeUpdate() > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public boolean checkPassword(int userId, String currentPassword) {
	    String sql = "SELECT password FROM auth WHERE user_id = ?";
	    try (
	         PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setInt(1, userId);
	        ResultSet rs = ps.executeQuery();
	        if (rs.next()) {
	            return rs.getString("password").equals(currentPassword); 
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	public void updatePassword(int userId, String newPassword) {
	    String sql = "UPDATE auth SET password = ? WHERE user_id = ?";
	    try (
	         PreparedStatement ps = connection.prepareStatement(sql)) {
	        ps.setString(1, newPassword);
	        ps.setInt(2, userId);
	        ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
