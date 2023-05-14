package vn.vietdefi.services.gochie.logic;

import vn.vietdefi.util.string.StringUtil;

public class HashLogic {
    public static String generateSignature(String data, String secretCode){
        String signedData = new StringBuilder(data)
                .append("|")
                .append(secretCode).toString();
        String hash = StringUtil.sha256(signedData).substring(0, 10);
        return hash;
    }
}
