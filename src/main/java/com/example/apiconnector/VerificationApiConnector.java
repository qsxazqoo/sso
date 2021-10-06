//package com.example.apiconnector;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import org.springframework.stereotype.Repository;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//@Repository
//public class VerificationApiConnector {
//
//	//驗證帳號密碼
//	public JsonObject checkUsers(String account) {
//		try {
//			URL url = new URL("http://127.0.0.1:8080/demo-0.0.1-SNAPSHOT/verification/login");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestProperty("Content-Type", "application/json; utf-8");
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true); // 允許輸出流，即允許上傳
//
//			OutputStream os = conn.getOutputStream();
//			JsonObject data = new JsonObject();
//			data.addProperty("username",account);
//			String dataString = data.toString();
//			os.write(dataString.getBytes(), 0, dataString.getBytes().length);
//			os.close();
//
//			// Getting the response code
//			int responsecode = conn.getResponseCode();
//			if (responsecode != 200) {
//				throw new RuntimeException("HttpResponseCode: " + responsecode);
//			} else {
//
//				String result = "";
//				String line = "";
//				InputStreamReader thisInputStreamReader = new InputStreamReader(conn.getInputStream());
//				BufferedReader reader = new BufferedReader(thisInputStreamReader);
//
//				while ((line = reader.readLine()) != null) {
//					result += line;
//				}
//				reader.close();
//				thisInputStreamReader.close();
//				conn.getInputStream().close();
//				conn.disconnect();
//				System.out.println("result     "+result);
//				JsonObject data_obj = JsonParser.parseString(result).getAsJsonObject();
//				System.out.println(data_obj);
//				return data_obj;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//
//		}
//	}
//}