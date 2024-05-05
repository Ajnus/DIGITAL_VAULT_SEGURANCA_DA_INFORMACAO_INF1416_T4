import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Base64;

import javax.security.cert.X509Certificate;
import javax.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class RestoreValidateSuite {
    //ainda a ser testado
    public static boolean Validate(byte[] digitalEnvelope, byte[] digitalSignature, byte[] arquivoENCriptografado, X509Certificate certificadoUsuario, PrivateKey chaveUsuario){
        boolean result = false;

        //File myFile = new File(FILE_NAME);
        //byte[] byteArray = new byte[(int) myFile.length()];
        //try (FileInputStream inputStream = new FileInputStream(myFile)) {
        //   inputStream.read(byteArray);
        //}

        byte[] semente = null;
        {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, chaveUsuario);
        semente = cipher.doFinal(digitalEnvelope);
        }

        FileInputStream arquivoDecriptografado = null;
        {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(semente);
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, secureRandom);
        SecretKey KAES = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, KAES);
        arquivoDecriptografado = cipher.doFinal(arquivoENCriptografado);
        }

        {
        //get Certificate of file's owner and get public key
        //checkSignature and save on result
        }

        return result;
    }
    //ainda a ser testado
    public static PrivateKey RestorePrivateKey(byte[] keyFile, String fraseSecreta){

        byte[] Kprivate = null;
        {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(fraseSecreta.getBytes());
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, secureRandom);
        SecretKey KAES = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, KAES);
        Kprivate = cipher.doFinal(keyFile);
        }

        PKCS8EncodedKeySpec detalheChave = null;
        {
        byte[] decodedBytes = Base64.getDecoder().decode(Kprivate);
        String chavePrivadaDecodificada = new String(decodedBytes);
        detalheChave = new PKCS8EncodedKeySpec(chavePrivadaDecodificada.getBytes());
        }

        PrivateKey chave = null;
        try {

        //keyspec
        KeyFactory keyFac = KeyFactory.getInstance("AES");
        chave = keyFac.generatePrivate(detalheChave);

        }catch(NoSuchAlgorithmException e){
            System.err.println("algoritmo AES não está disponível");
        }

        return chave;
    }
    //ainda a ser testado
    public static PublicKey RestorePublicKey(InputStream CertificadoDigital){

        PublicKey chave = null;
        try{
            X509Certificate certificado = X509Certificate.getInstance(CertificadoDigital);
            chave = certificado.getPublicKey();
        }catch(CertificateException e){
            System.err.println("Erro no acesso do certificado digital");
            //o que façço aqui?
        }

        return chave;
    }

}
