package com.amazonaws.lambda.demo;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.lambda.dao.DynamodbAccessObject;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

/*
各scanから収集したデータを都合の良いデータに変換する

returnするjson形式

[
	{
		"timeStamp": UNIXのタイムスタンプ
		"hash":　各アクティビティを識別するためのハッシュID
		"url":　scanでの情報が閲覧できるURL
		"from":　移動元アドレス
		"to":　移動先アドレス
		"gas":　ガス代(各通貨)
	}
	{
	}
]
*/
public class ReadScanJson {

	/**
	 * 各チェーンに合わせて、ガス代を計算します。
	 * @param response  ETH scan　から取得したJSON形式
	 * @return　上に記載しているjson(String)を返します。
	 */
	public static String readJsonEth(String response) {

		String latestCurrencyPrie = safeGetPrice("eth_price", "time", Common.getUnixtimeHourAgo59min());

		JSONObject jsonObj = new JSONObject(response);
		JSONArray result = jsonObj.getJSONArray("result");

		JSONArray jsonResponse = new JSONArray();

		for (int i = 0; i < result.length(); i++) {
			JSONObject item = result.getJSONObject(i);
			double gasPrice = Double.parseDouble(item.get("gasPrice").toString());
			double gasUsed = Double.parseDouble(item.get("gasUsed").toString());
			double weiHalf = 0.000000001;
			double gas = (gasPrice * weiHalf * weiHalf) * gasUsed;
			JSONObject json = new JSONObject();
			long time = Long.parseLong(String.valueOf(item.get("timeStamp")));
			double dollerGascost = 0;
			String currencyPrice = "";
			if (Common.getUnixtimeHourAgo59min() > time) {
				currencyPrice = safeGetPrice("eth_price", "time", time);
				dollerGascost = Double.parseDouble(currencyPrice) * gas;
			} else {
				currencyPrice = latestCurrencyPrie;
				dollerGascost = Double.parseDouble(currencyPrice) * gas;
			}
			json.put("timeStamp", time);
			json.put("hash", item.get("hash"));
			json.put("from", item.get("from"));
			json.put("to", item.get("to"));
			json.put("gas", gas);
			json.put("dollerGascost", dollerGascost);
			json.put("currencyPrice", currencyPrice);
			jsonResponse.put(i, json);
		}
		return jsonResponse.toString();
	}

	/**
	 * 各チェーンに合わせて、ガス代を計算します。
	 * @param response  BSC scan　から取得したJSON形式
	 * @return　上に記載しているjson(String)を返します。
	 */
	public static String readJsonBsc(String response) {
		String latestCurrencyPrie = safeGetPrice("bnb_price", "time", Common.getUnixtimeHourAgo59min());

		JSONObject jsonObj = new JSONObject(response);
		JSONArray result = jsonObj.getJSONArray("result");

		JSONArray jsonResponse = new JSONArray();

		for (int i = 0; i < result.length(); i++) {
			JSONObject item = result.getJSONObject(i);
			double gasPrice = Double.parseDouble(item.get("gasPrice").toString());
			double gasUsed = Double.parseDouble(item.get("gasUsed").toString());
			double weiHalf = 0.000000000000000001;
			double gas = gasPrice * gasUsed * weiHalf;
			JSONObject json = new JSONObject();
			long time = Long.parseLong(String.valueOf(item.get("timeStamp")));

			double dollerGascost = 0;
			String currencyPrice = "";
			if (Common.getUnixtimeHourAgo59min() > time) {
				currencyPrice = safeGetPrice("bnb_price", "time", time);
				dollerGascost = Double.parseDouble(currencyPrice) * gas;
			} else {
				currencyPrice = latestCurrencyPrie;
				dollerGascost = Double.parseDouble(currencyPrice) * gas;
			}
			json.put("timeStamp", time);
			json.put("hash", item.get("hash"));
			json.put("from", item.get("from"));
			json.put("to", item.get("to"));
			json.put("gas", gas);
			json.put("dollerGascost", dollerGascost);
			json.put("currencyPrice", currencyPrice);
			jsonResponse.put(i, json);
		}
		return jsonResponse.toString();
	}

	/**
	 * DBから指定された時間時点での価格を安全に取得
	 * 取得できなかった場合0を返します。
	 */
	private static String safeGetPrice(String tableName, String HashKey, long value) {
		List<Map<String, AttributeValue>> currencyPriceList = DynamodbAccessObject.findHashKey(tableName, HashKey,
				String.valueOf(Common.roundDownForUnixtime(value, 60)));
		String currencyPrice = null;
		if (currencyPriceList.size() != 0) {
			currencyPrice = currencyPriceList.get(0).get("open").getS();
		} else {
			currencyPrice = "0";
		}
		return currencyPrice;

	}

}
