package cn.fengyunxiao.nest.util;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GoogleAuthenticator {
    // taken from Google pam docs - we probably don't need to mess with these
    private static final int SECRET_SIZE = 10;

    private static final String SEED = "g8GjEvTbW5oVSV7avLBdwIHqGlUYNzKFI7izOF8GwLDVKs2m0QN7vxRs2im5MDaNCWGmcD2rvcZx";

    private static final String RANDOM_NUMBER_ALGORITHM = "SHA1PRNG";

    // 最多可偏移的时间 3 - 17
    private int window_size = 3;

    private void setWindowSize(int s) {
        if (s >= 1 && s <= 17)
            window_size = s;
    }

    public String generateSecretKey() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance(RANDOM_NUMBER_ALGORITHM);
            sr.setSeed(Base64.decodeBase64(SEED));
            byte[] buffer = sr.generateSeed(SECRET_SIZE);
            Base32 codec = new Base32();
            byte[] bEncodedKey = codec.encode(buffer);
            return new String(bEncodedKey);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public boolean check_code(String secret, long code, long timeMsec) {
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secret);
        // convert unix msec time into a 30 second "window"
        // this is per the TOTP spec (see the RFC for details)
        long t = (timeMsec / 1000L) / 30L;
        // Window is used to check codes generated in the near past.
        // You can use this value to tune how far you're willing to go.
        for (int i = -window_size; i <= window_size; ++i) {
            long hash;
            try {
                hash = verify_code(decodedKey, t + i);
                System.out.println(hash);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            if (hash == code) {
                return true;
            }
        }
        return false;
    }

    private static int verify_code(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            // We are dealing with signed bytes:
            // we just keep the first byte.
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }

/*
    public static void main(String[] args) {
        GoogleAuthenticator ga = new GoogleAuthenticator();

        String secret = ga.generateSecretKey();
        System.out.println("secret : "+secret);

        ga.setWindowSize(5);
        ga.check_code("ZVRBH5YPC4SORU4L", 123123, System.currentTimeMillis());
    }
*/

}

