import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;

import java.io.InputStream;

import java.util.Base64;

import javax.security.cert.X509Certificate;
import javax.security.cert.CertificateException;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class RestoreValidateSuite {
    //em construção
    public static boolean Validate(String digitalEnvelope, String digitalSignature, String arquivoENCriptografado){
        return false;
    }
    //em construção
    private static PrivateKey RestorePrivateKey(String keyFileString, String fraseSecreta, String salt){

        byte[] Kprivate = null;
        {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, secureRandom);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE);
        Kprivate = cipher.doFinal();
        }

        SecretKeySpec detalheChave = null;
        {
        //Decoder Base64
        //byte[] chave privada
        //Encoded KeySpec: PKCS8
        }

        PrivateKey chave = null;
        {
        //keyspec
        KeyFactory keyFac = KeyFactory.getInstance("AES");
        chave = keyFac.generatePrivate(detalheChave);
        }

        return chave;
    }
    //ainda para ser testada
    private static PublicKey RestorePublicKey(InputStream CertificadoDigital){

        PublicKey chave = null;
        try{
            X509Certificate certificado = X509Certificate.getInstance(CertificadoDigital);
            chave = certificado.getPublicKey();
        }catch(CertificateException e){
            System.err.println("Erro no acesso do certificado digital");
        }

        return chave;
    }

}
