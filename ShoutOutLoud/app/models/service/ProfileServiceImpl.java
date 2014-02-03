package models.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.Constants;
import models.utils.DBUtils;
import play.Logger;

public class ProfileServiceImpl implements ProfileService {

	private SearchService searchService = new SearchServiceImpl();
	
	@Override
	public void follow(String sourceHandle, String targetHandle) {
		long srcUid = searchService.searchUidForTwitterHandle(sourceHandle);
		long tgtUid = searchService.searchUidForTwitterHandle(targetHandle);
		
		Connection dbConn = DBUtils.getDBConnection();

		String sql = "INSERT INTO " + Constants.FOLLOWING_TBL + "(src_uid, tgt_uid) VALUES (?, ?)";
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(sql);
			preparedSql.setLong(1, srcUid);
			preparedSql.setLong(2, tgtUid);
			preparedSql.executeUpdate();
		} catch (SQLException e) {
			Logger.error("Failed to insert follow relationship for user " + sourceHandle);
			e.printStackTrace();
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
	}

}
