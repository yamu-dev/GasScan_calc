package com.amazonaws.lambda.demo;

//ApiKeyを返すクラス
//使用するAPIKEYの数も、保管場所も未定な為定義しただけ

public class ApiKey {
	private String ethApiKey = "ZT4SRPJC1ZYXVPBZWVXZUZ8Y72G512245T";
	private String bscApiKey = "AVRKZXIU4IC67M13TAJ4GQMZI6HKR9QRRD";

	public String getEthApiKey() {
		return ethApiKey;
	}
	public void setEthApiKey(String ethApiKey) {
		this.ethApiKey = ethApiKey;
	}
	public String getBscApiKey() {
		return bscApiKey;
	}
	public void setBscApiKey(String bscApiKey) {
		this.bscApiKey = bscApiKey;
	}
}
