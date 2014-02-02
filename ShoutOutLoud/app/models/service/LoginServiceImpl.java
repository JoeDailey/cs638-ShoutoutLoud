package models.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import play.Logger;
import models.Constants;
import models.utils.DBUtils;
import models.Profile;

public class LoginServiceImpl implements LoginService {

    private SearchService searchService = new SearchServiceImpl();

	// Performs the authentication and login steps
	@Override
	public boolean login(String handle, String password){
		return authenticate(handle, password);
	}

    @Override
    public Profile register(String email, String full_name, String handle, String password, String location){
        Connection dbConn = DBUtils.getDBConnection();
        Profile profile = null;

        // Step 1 - Make sure Profile for handle does not already exist
        String loginExistsSQL = "SELECT handle FROM " + Constants.USER_TBL + " WHERE handle = ?";
        PreparedStatement loginExistsPreparedSQL = null;
        try{
            loginExistsPreparedSQL = dbConn.prepareStatement(loginExistsSQL);
            loginExistsPreparedSQL.setString(1, handle);
            ResultSet loginExistsResultSet = loginExistsPreparedSQL.executeQuery();

            if(loginExistsResultSet.next()){
                throw new SQLException(); // login already exists
            }
        } catch (SQLException e){
            Logger.error("Profile already exists for handle: " + handle);
            e.printStackTrace();
        }

        // Step 2 - Insert profile into profile table
        long profileId = searchService.searchMaxUserId() + 1;
        String profileInsertSQL = "INSERT INTO " + Constants.USER_TBL + "(id, handle, full_name, email, password, location) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedSQL = null;
        try {
            preparedSQL = dbConn.prepareStatement(profileInsertSQL);
            preparedSQL.setLong(1, profileId);
            preparedSQL.setString(2, handle);
            preparedSQL.setString(3, full_name);
            preparedSQL.setString(4, email);
            preparedSQL.setString(5, password);
            preparedSQL.setString(6, location);
            preparedSQL.executeUpdate();

            profile = new Profile(profileId, full_name, email, handle, location);
        } catch (SQLException e){
            Logger.error("Failed to insert profile for handle: " + handle);
            e.printStackTrace();
        }

        DBUtils.cleanDBResources(dbConn, preparedSQL);
        return profile;
    }

	@Override
        public boolean authenticate(String handle, String password){
            Connection dbConn = DBUtils.getDBConnection();
            boolean isDBOpSuccess = false;

            String userAuthenticateSQL = "SELECT handle FROM " + Constants.USER_TBL + " WHERE password = ?";
            PreparedStatement preparedSql = null;
            try{
                preparedSql = dbConn.prepareStatement(userAuthenticateSQL);
                preparedSql.setString(1, password);
                ResultSet results = preparedSql.executeQuery();

                if(results.next()){ 
                    isDBOpSuccess = true; // results set contains matching handle and password
                }
            } catch (SQLException e) {
                Logger.error("Failed to authenticate handle: " + handle + " with password: " + password);
                e.printStackTrace();
            }
            DBUtils.cleanDBResources(dbConn, preparedSql);
            return isDBOpSuccess;
        }

}
