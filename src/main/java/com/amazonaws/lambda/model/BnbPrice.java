package com.amazonaws.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "bnb_price")
public class BnbPrice {
	private String time;
	private String close;
	private String high;
	private String low;
	private String open;
	private String volume;

	@DynamoDBHashKey(attributeName = "time")
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@DynamoDBAttribute(attributeName = "close")
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	@DynamoDBAttribute(attributeName = "high")
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	@DynamoDBAttribute(attributeName = "low")
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	@DynamoDBAttribute(attributeName = "open")
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	@DynamoDBAttribute(attributeName = "volume")
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
}
