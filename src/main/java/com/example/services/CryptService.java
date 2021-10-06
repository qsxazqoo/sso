package com.example.services;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
@Service
public class CryptService {

	Cipher ecipher;
	  Cipher dcipher;
	  String mykeyString="D3D81E715B5A0F1CE453CDE9613D9964";
	  
	  Base64 base64 = new Base64();
	  
	  public CryptService() throws Exception {
		  SecretKey key=new SecretKeySpec(mykeyString.getBytes(),"AES");
	    ecipher = Cipher.getInstance("AES");
	    dcipher = Cipher.getInstance("AES");
	    ecipher.init(Cipher.ENCRYPT_MODE, key);
	    dcipher.init(Cipher.DECRYPT_MODE, key);
	  }

	  public String encrypt(String str) throws Exception {
		    // Encode the string into bytes using utf-8
		    byte[] utf8 = str.getBytes("UTF8");

		    // Encrypt
		    byte[] enc = ecipher.doFinal(utf8);

		    // Encode bytes to base64 to get a string
		    return  base64.encodeToString(enc);
		  }

		  public String decrypt(String str) throws Exception {
		    // Decode base64 to get bytes
		    byte[] dec =base64.decode(str);

		    byte[] utf8 = dcipher.doFinal(dec);

		    // Decode using utf-8
		    return new String(utf8, "UTF8");
		  }
}
