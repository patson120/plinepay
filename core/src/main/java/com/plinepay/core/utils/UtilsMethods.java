package com.plinepay.core.utils;

import com.plinepay.core.entities.AbstractEntity;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public class UtilsMethods {

    /**
     *
     * @param texte
     * @return
     */
    public static String buildSha1(String texte) {
        try {
            byte[] bytesOfMessage = texte.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] digest = md.digest(bytesOfMessage);

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < digest.length; i++) {
                if ((0xff & digest[i]) < 0x10) {
                    sb.append("0").append(Integer.toHexString((0xFF & digest[i])));
                } else {
                    sb.append(Integer.toHexString(0xFF & digest[i]));
                }
            }
            String sha1 = sb.toString();
            return sha1;
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UtilsMethods.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public static String buildMd5(String texte) {
        try {
            byte[] bytesOfMessage = texte.getBytes("UTF-8");

            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytesOfMessage);

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < digest.length; i++) {
                if ((0xff & digest[i]) < 0x10) {
                    sb.append("0").append(Integer.toHexString((0xFF & digest[i])));
                } else {
                    sb.append(Integer.toHexString(0xFF & digest[i]));
                }
            }
            String md5 = sb.toString();
            return md5;

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
            Logger.getLogger(UtilsMethods.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    public static String generatePassword() {
        String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowercase = "abcdefghijklmnopqrstuvwxyz";
        String numeric = "0123456789";
        String special = "@#$%&*-=+";
        //String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        String pwd = RandomStringUtils.random(1, uppercase + lowercase)
                + RandomStringUtils.random(1, uppercase)
                + RandomStringUtils.random(2, lowercase)
                + RandomStringUtils.random(2, numeric)
                + RandomStringUtils.random(1, special)
                + RandomStringUtils.random(2, uppercase + lowercase + numeric + special);
        return pwd;
    }


    // Méthode de création des clés 
    public static String generateUniqueCode(int length) {
		String codeInt = RandomStringUtils.random(length, "1234567890");
		String codeString = RandomStringUtils.random(length,
				"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890");
		return codeInt + "-" + codeString + "-" + generatePassword();
	}

    // Générer le code OTP
    public static String generateOtpCode(){
        return RandomStringUtils.random(5, "0123456789");
    }

    /**
     *
     * @param num
     * @param digits
     * @return
     */
    public static String intToString(int num, int digits) {
        String output = Integer.toString(num);
        while (output.length() < digits) {
            output = "0" + output;
        }
        return output;
    }

    /**
     *
     * @param creation
     * @param entity
     * @param entityStatut
     */
    public static void loadCreationAttributes(Boolean creation, AbstractEntity entity, Boolean entityStatut) {

        entity.setEntityState(entityStatut);
        entity.setLastDateUpdate(System.currentTimeMillis());
        if (creation) {
            entity.setFlag(Boolean.TRUE);
            entity.setDateCreation(System.currentTimeMillis());
        } else {
            if (entity.getFlag() == null) {
                entity.setFlag(Boolean.TRUE);
            }
        }
        entity.setUserCreation("SYSTEM_IN");
        entity.setLastUserUpdate("SYSTEM_IN");
    }

    /**
     *
     * @param entity
     */
    public static void createID(AbstractEntity entity) {
        if (entity.getId() == null || entity.getId().toString().trim().isEmpty()) {
            entity.setId(UUID.randomUUID());
        }
    }

    /**
     *
     * @param value
     * @return
     */
    public static Boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Envoi des mails via CAMOO Solution temporaire avant l'intrduction de
     * Kafka. Les paramètres CAMOO ne passent pas via JavaMail
     *
     * @param name
     * @param surname
     * @param email
     * @param subject
     * @param message
     */
    public static void sendMailThroughCAMOO(String name, String surname, String email, String subject, String message) {
        /*try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("name", name)
                    .addFormDataPart("surname", surname)
                    .addFormDataPart("email", email)
                    .addFormDataPart("subject", subject)
                    .addFormDataPart("message", message)
                    .build();
            Request request = new Request.Builder()
                    .url("https://plinepay.com/sendMail.php")
                    .method("POST", body)
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println("Message envoyé : " + response.code());
        } catch (IOException ex) {
            Logger.getLogger(UtilsMethods.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

}
