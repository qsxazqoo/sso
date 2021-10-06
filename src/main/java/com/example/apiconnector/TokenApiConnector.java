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
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//@Repository
//public class TokenApiConnector {
//
//	// 發token
//	public String sendToken(String username) {
//		try {
//			URL url = new URL("http://127.0.0.1:8080/demo-0.0.1-SNAPSHOT/token/send");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestProperty("Content-Type", "application/json; utf-8");
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true); // 允許輸出流，即允許上傳
//
//			OutputStream os = conn.getOutputStream();
//			JsonObject data = new JsonObject();
////			data.addProperty("username",username);
////			String dataString = data.toString();
//			os.write(username.getBytes(), 0, username.getBytes().length);
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
//				System.out.println("result     " + result);
////				JsonObject data_obj = JsonParser.parseString(result).getAsJsonObject();
////				System.out.println(data_obj);
//				return result;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//
//		}
//	}
//
//	// 取token內的username
//	public String extractUsername(String jwt) {
//		try {
//			URL url = new URL("http://127.0.0.1:8082/token/extractUsername");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestProperty("Content-Type", "application/json; utf-8");
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true); // 允許輸出流，即允許上傳
//
//			OutputStream os = conn.getOutputStream();
////				JsonObject data = new JsonObject();
////				data.addProperty("jwt",username);
////				String dataString = data.toString();
//			os.write(jwt.getBytes(), 0, jwt.getBytes().length);
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
//				System.out.println("result     " + result);
////					JsonObject data_obj = JsonParser.parseString(result).getAsJsonObject();
////					System.out.println(data_obj);
//				return result;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//
//		}
//	}
//
//	// 檢查token是否過期
//	public String validateToken(String jwt, String username) {
//		try {
//			URL url = new URL("http://127.0.0.1:8082/token/validateToken");
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestProperty("Content-Type", "application/json; utf-8");
//			conn.setRequestMethod("POST");
//			conn.setDoOutput(true); // 允許輸出流，即允許上傳
//
//			OutputStream os = conn.getOutputStream();
//			JsonObject data = new JsonObject();
//			data.addProperty("jwt", jwt);
//			data.addProperty("username", username);
//			String dataString = data.toString();
//			System.out.println(dataString);
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
//				System.out.println("result     " + result);
////							JsonObject data_obj = JsonParser.parseString(result).getAsJsonObject();
////							System.out.println(data_obj);
//				return result;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//
//		}
//	}
//}