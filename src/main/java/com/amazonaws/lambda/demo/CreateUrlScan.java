package com.amazonaws.lambda.demo;

public class CreateUrlScan {
	String ethBaseUrl = "https://api.etherscan.io/api";
	String bscBaseUrl = "https://api.bscscan.com/api";

	/**
	 * 各チェーンに合わせた、アドレスからの取引リストを取得するURLを返します。
	 * @param chain  チェーン名
	 * @param address　　アドレス
	 * @return　GETで叩くURLを返します。
	 */
	public String getListNormal(String chain,String address) {

		ApiKey key = new ApiKey();
		StringBuffer urlSb = new StringBuffer("");
		String strUrl = "";

		switch (chain) {
		//Etherscanに投げるURLの生成
		case "eth":
			urlSb.append(ethBaseUrl);
			urlSb.append("?module=account");
			urlSb.append("&action=txlist");
			urlSb.append("&address=" + address);
			urlSb.append("&startblock=0");
			urlSb.append("&endblock=99999999");
			urlSb.append("&page=1");
			urlSb.append("&offset=10000");
			urlSb.append("&sort=asc");
			urlSb.append("&apikey=" + key.getEthApiKey());
			strUrl = urlToString(urlSb);
			break;
		//BSCscanに投げるURLの生成
		case "bsc":
			urlSb.append(bscBaseUrl);
			urlSb.append("?module=account");
			urlSb.append("&action=txlist");
			urlSb.append("&address=" + address);
			urlSb.append("&startblock=0");
			urlSb.append("&endblock=9999999999999");
			urlSb.append("&page=1");
			urlSb.append("&offset=10000");
			urlSb.append("&sort=asc");
			urlSb.append("&apikey=" + key.getBscApiKey());
			strUrl = urlToString(urlSb);
			break;
		}
		return strUrl;
	}

	/**
	 * StringBufferからStringに型変換を行います。
	 * @param sb　StringBuffer
	 * @return　url String
	 */
	private static String urlToString(StringBuffer sb) {
		String url = sb.toString();
		System.out.println(url);
		return url;
	}
}