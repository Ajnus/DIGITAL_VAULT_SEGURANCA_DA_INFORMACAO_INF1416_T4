// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

package model;

import java.util.Arrays;

import javax.security.cert.X509Certificate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Security;
import java.security.Provider;

import javax.security.cert.X509Certificate;
import javax.security.cert.CertificateException;
import java.util.Date;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

public class Authentication {
    public static boolean AuthenticatePassword(String password){
        boolean result = false;

        if (Security.getProvider("BC") == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        }

        {
        //pegue o hash do usuario no banco de dados, extraia o salt e colocque
        //String hash = 
        //byte[] salt = 
        //String hashTeste = OpenBSDBCrypt.generateCode(password.toCharArray(),salt,12);
        //result = hash.equals(hashTeste);
        //Arrays.fill(salt,(byte)0);
        }

        return result;
    }

    public static boolean CheckUniqueName(String Name){
        boolean result = false;

        {
        //da tablea usuário colete todos os nomes dos usuários e administradores registrados 
        //ou pergunte ao banco se tem uma linha contendo o nome salvo no parametro
        }

        return result;
    }

    public static boolean AuthenticateCertificate(String EnderecoCert){
        boolean result = false;
        try{
            File certificado = new File(EnderecoCert);
            FileInputStream CertificadoinputStream = new FileInputStream(certificado);
            X509Certificate certificadoX509 = X509Certificate.getInstance(CertificadoinputStream);
            certificadoX509.checkValidity();
        }catch(IOException e){
            System.err.println("Erro ao abrir certificado digital no endereço dado");
            return false;
        } catch(CertificateException e){
            System.err.println("Erro ao instanciar certificado para analise");
            return false;
        }
        return result;
    }
}
