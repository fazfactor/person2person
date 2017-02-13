package com.appbazooka.planeteves;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class OAuthBasenew {

    private static char[] map1 = new char[64];
    private static final String PARAMETER_SEPARATOR = "&";
    private static final String NAME_VALUE_SEPARATOR = "=";

    static {
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            map1[i++] = c;
        }
        for (char c = 'a'; c <= 'z'; c++) {
            map1[i++] = c;
        }
        for (char c = '0'; c <= '9'; c++) {
            map1[i++] = c;
        }
        map1[i++] = '+';
        map1[i++] = '/';
    }

    private HashMap<String, String> convertStringParamToMap(String parameter) {
        if (parameter != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            String[] keyValue;
            for (String param : parameter.split("&")) {
                keyValue = param.split("=");
                if (keyValue.length > 1) {
                    map.put(keyValue[0], keyValue[1]);
                }
            }
            return map;
        }
        return null;
    }

    private String getNormalizedURLString(String url) {
        try {
            return new URI(url).normalize().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String urlencode(String original) {
        try {
           
            return URLEncoder.encode(original, "utf-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateSignatureBaseString(String method, String url, LinkedHashMap<String, String> getOrPostParams) {
       //generating signature base string
    	StringBuilder buffer = new StringBuilder();
        buffer.append(method);
     
        buffer.append("&");
        buffer.append(urlencode(getNormalizedURLString(url)));
     
        buffer.append("&");
        
        String qparam = "";

        Set<Map.Entry<String, String>> entry = getOrPostParams.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entry.iterator();
     
        while (iterator.hasNext()) {
            Map.Entry<String, String> tempEntry = iterator.next();
            if (iterator.hasNext()) {
                qparam += tempEntry.getKey() + "=" + tempEntry.getValue() + "&";
         
            } else {
                qparam += tempEntry.getKey() + "=" + tempEntry.getValue();
         
            }
        }
     
        try {
            buffer.append(URLEncoder.encode(qparam, "UTF-8"));
     
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = buffer.toString();
     
        return result;
    }

    private String getTimestamp() {
    
    	Date obj=new Date();
    	long time=obj.getTime();
    	System.out.println("timestamp"+ time);
        return "" + new Date().getTime();
    }

    public String getNonce() {
    	Date obj=new Date();
    	long time=obj.getTime();
    	System.out.println("oauth_nonce"+ time);
        return "" + new Date().getTime();

    }

    public String getFinalUrl(String url, String consumerKey, String secretKey, String method) {
        String output = "";

        LinkedHashMap<String, String> requestTokenHash = new LinkedHashMap<String, String>();
        requestTokenHash.put("oauth_consumer_key", consumerKey);
        requestTokenHash.put("oauth_nonce", getNonce());
        requestTokenHash.put("oauth_signature_method", "HMAC-SHA1");
        requestTokenHash.put("oauth_timestamp", getTimestamp());
        requestTokenHash.put("oauth_version", "1.0");
        requestTokenHash.put("oauth_signature", generateOAuthSignature(generateSignatureBaseString(method, url, requestTokenHash), secretKey));



        Set<Map.Entry<String, String>> entrySet = requestTokenHash.entrySet();
        final StringBuilder result = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : entrySet) {
                final String encodedName = URLEncoder.encode(entry.getKey(), "UTF-8");
                       final String value = entry.getValue();
       
                final String encodedValue = value != null ? URLEncoder.encode(value, "UTF-8") : "";
                         if (result.length() > 0) {
                    result.append(PARAMETER_SEPARATOR);
                }
                result.append(encodedName);
                result.append(NAME_VALUE_SEPARATOR);
                result.append(encodedValue);
            

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String finalUrl = url + "?" + result.toString();
      

        return finalUrl;

    }

    public static String hmacSha1Digest(String original, String key) {
        return hmacSha1Digest(original.getBytes(), key.getBytes());
    }

    /**
     * 
     * @param original
     * @param key
     * @return null if fails
     */
    public static String hmacSha1Digest(byte[] original, byte[] key) {
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(key, "HmacSHA1"));
            byte[] rawHmac = mac.doFinal(original);
            return new String(encode(rawHmac));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static char[] encode(byte[] in) {
        return encode(in, in.length);
    }

    public static char[] encode(byte[] in, int iLen) {
        int oDataLen = (iLen * 4 + 2) / 3;       
        int oLen = ((iLen + 2) / 3) * 4;        
        char[] out = new char[oLen];
        int ip = 0;
        int op = 0;
        while (ip < iLen) {
            int i0 = in[ip++] & 0xff;
            int i1 = ip < iLen ? in[ip++] & 0xff : 0;
            int i2 = ip < iLen ? in[ip++] & 0xff : 0;
            int o0 = i0 >>> 2;
            int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
            int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
            int o3 = i2 & 0x3F;
            out[op++] = map1[o0];
            out[op++] = map1[o1];
            out[op] = op < oDataLen ? map1[o2] : '=';
            op++;
            out[op] = op < oDataLen ? map1[o3] : '=';
            op++;
        }
     
        return out;
    }

    private static String generateOAuthSignature(String signatureBaseString, String secretKey) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(urlencode(secretKey));
     
        buffer.append("&");
        buffer.append(urlencode(""));
        
        return hmacSha1Digest(signatureBaseString, buffer.toString());
    }
   
 
   
}
