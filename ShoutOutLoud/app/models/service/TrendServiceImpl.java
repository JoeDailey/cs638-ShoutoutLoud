package models.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import play.Logger;
import models.Constants;
import models.Trend;
import models.utils.DBUtils;

import com.google.common.collect.Lists;

public class TrendServiceImpl implements TrendService {

	@Override
	public List<Trend> getTrends() {
		Connection dbConn = DBUtils.getDBConnection();
		List<Trend> topNTrends = Lists.newArrayList();
		
		String sql = "SELECT keyword FROM " + Constants.TREND_TBL + " ORDER BY count LIMIT ?";
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(sql);
			preparedSql.setInt(1, Constants.NUM_TRENDS_TO_SHOW);
			ResultSet results = preparedSql.executeQuery();
			while(results.next()) {
				topNTrends.add(new Trend(results.getString("keyword"), Integer.parseInt(results.getString("count"))));
			}
		} catch (SQLException e) {
			Logger.error("Failed to fetch top N trends ..");
			e.printStackTrace();
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
		return topNTrends;
	}

	@Override
	public void insertTrend(Trend trend) {
		Connection dbConn = DBUtils.getDBConnection();
		String sql = "INSERT INTO " + Constants.TREND_TBL + "(keyword, count) VALUES (?, ?)";
		
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(sql);
			preparedSql.setString(1, trend.getKeyword());
			preparedSql.setInt(2, trend.getCount());
			
			preparedSql.executeUpdate();
		} catch (SQLException e) {
			Logger.error("Failed to fetch top N trends ..");
			e.printStackTrace();
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
	}

	@Override
	public void deleteStaleTrends() {
		Connection dbConn = DBUtils.getDBConnection();
		String sql = "DELETE FROM " + Constants.TREND_TBL;
		
		PreparedStatement preparedSql = null;
		try {
			preparedSql = dbConn.prepareStatement(sql);
			preparedSql.executeUpdate();
		} catch (SQLException e) {
			Logger.error("Failed to delete trends table ..");
			e.printStackTrace();
		}
		
		DBUtils.cleanDBResources(dbConn, preparedSql);
	}

}
