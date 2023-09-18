package com.amazonaws.lambda.demo;

import java.util.LinkedHashMap;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

//AWS Lambdaを実行するクラス

public class LambdaFunctionHandler implements RequestHandler<Object, String> {

	//Object inputでAWSからの様々な情報を取得します。
    @Override
    public String handleRequest(Object input, Context context) {
    	context.getLogger().log("Input: " + input);

		//URLに引数で記載された、チェーン名とアドレス名を抽出
    	LinkedHashMap query = null;
    	LinkedHashMap queryPramete = null;
    	if (input instanceof LinkedHashMap) {
    		query = (LinkedHashMap) input;

    		queryPramete = (LinkedHashMap) query.get("queryStringParameters");

    	}
			String chain = (String) queryPramete.get("chain");
			String address = (String) queryPramete.get("address");

    	//ログ出力
    	context.getLogger().log("queryPramete: " + queryPramete);
    	context.getLogger().log("chain: " + chain);
    	context.getLogger().log("address: " + address);

    	//APIアドレスの生成
    	CreateUrlScan Create = new CreateUrlScan();
    	String url = Create.getListNormal(chain, address);

    	//GETでAPI叩く
    	String httpResponse = HttpRequest.runSample(url);

    	String jsonResponse = null;
    	//JSON解析し、必要データを抽出。ガス代計算
    	if ("eth".equals(chain)) {
    		jsonResponse = ReadScanJson.readJsonEth(httpResponse);
    	}
    	if ("bsc".equals(chain)) {
    		jsonResponse = ReadScanJson.readJsonBsc(httpResponse);
    	}
    	return jsonResponse;
    }

}

