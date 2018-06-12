package kh.captcha;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class Captcha {

	public String getKey() throws Exception {
		String clientId = "b1x57Qzi1_Vz2R7PMsgB";//애플리케이션 클라이언트 아이디값";
		String clientSecret = "vdQHhBLP9W";//애플리케이션 클라이언트 시크릿값";

		String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("X-Naver-Client-Id", clientId);
		con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
		int responseCode = con.getResponseCode();
		BufferedReader br;
		if(responseCode==200) { // 정상 호출
	
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else {  // 에러 발생
			
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		
		}
		br.close();
		System.out.println(response.toString());
		JsonParser parser = new JsonParser();
		JsonObject jsonObj = parser.parse(response.toString()).getAsJsonObject();
		String keyValue = jsonObj.get("key").getAsString();
		System.out.println(keyValue);
		return keyValue;
	}

	public String getImage(String keyValue, String dirPath) throws Exception {
		System.out.println(keyValue+" : 111");
		String result =null;
		String clientId = "b1x57Qzi1_Vz2R7PMsgB";//애플리케이션 클라이언트 아이디값";
		String clientSecret = "vdQHhBLP9W";//애플리케이션 클라이언트 시크릿값";

		String key = keyValue; // https://openapi.naver.com/v1/captcha/nkey 호출로 받은 키값
		String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("X-Naver-Client-Id", clientId);
		con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
		int responseCode = con.getResponseCode();
		BufferedReader br;
		if(responseCode==200) {
			// 정상 호출
			InputStream is = con.getInputStream();
			int read = 0;
			byte[] bytes = new byte[1024];
			// 랜덤한 이름으로 파일 생성
			String tempname = Long.valueOf(new Date().getTime()).toString();
			File f = new File(dirPath+tempname + ".jpg");

			f.createNewFile();
			OutputStream outputStream = new FileOutputStream(f);
			while ((read =is.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			result = tempname + ".jpg";
			System.out.println(f.getAbsolutePath());
			is.close();
		} else {  // 에러 발생
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println(response.toString());
		}
		System.out.println(result);
		return result;

	}

	public boolean comp_result(String keyValue, String user_value) throws Exception  {

		System.out.println(keyValue);
		System.out.println(user_value);
		String clientId = "b1x57Qzi1_Vz2R7PMsgB";//애플리케이션 클라이언트 아이디값";
		String clientSecret = "vdQHhBLP9W";//애플리케이션 클라이언트 시크릿값";

		String code = "1"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
		String key = keyValue; // 캡차 키 발급시 받은 키값
		String value = user_value; // 사용자가 입력한 캡차 이미지 글자값
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code +"&key="+ key + "&value="+ value;
		System.out.println(apiURL);

		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("X-Naver-Client-Id", clientId);
		con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
		int responseCode = con.getResponseCode();
		BufferedReader br;
		if(responseCode==200) { // 정상 호출
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));

		} else {  // 에러 발생
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();
		System.out.println(response.toString());

		JsonParser parser = new JsonParser();
		JsonObject jsonObj = parser.parse(response.toString()).getAsJsonObject();
		boolean result = jsonObj.get("result").getAsBoolean();
		return result;
	}

//	public static void main(String[] args) {
//		try {
//		Captcha cap = new Captcha();
//		String keyresult = cap.getKey();
//		String imageresult = cap.getImage(keyresult);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
// }
}


