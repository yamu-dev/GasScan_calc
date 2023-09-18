package com.amazonaws.lambda.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;

/**
 *  DynamoDBと接続する機能を提供するクラス
 * @author m.k
 */
public  class DynamodbAccessObject {

	/**
	 * DynamoDB接続定義
	 */
	private final static AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.standard()
			.withRegion("ap-northeast-1")
			.build();

	/**
	 * 検索条件作成
	 * @param value 検索条件の値
	 * @return 検索条件
	 */
	private static Condition makeHashCondition(String value) {
		return new Condition().withComparisonOperator(ComparisonOperator.EQ)
				.withAttributeValueList(new AttributeValue(value));
	}

	/**
	 * DynamoDBから指定されたテーブル名、
	 * @param tableName 検索先テーブル名
	 * @param hashName hashキー名
	 * @param value 検索条件の値
	 * @return
	 */
	public static List<Map<String, AttributeValue>> findHashKey(String tableName, String hashName, String value) {
		QueryRequest request = new QueryRequest(tableName);

		Map<String, Condition> keyConditions = new HashMap<String, Condition>();
		keyConditions.put(hashName, makeHashCondition(value));
		request.setKeyConditions(keyConditions);
		QueryResult result = dynamoDB.query(request);

		return result.getItems();
	}

}