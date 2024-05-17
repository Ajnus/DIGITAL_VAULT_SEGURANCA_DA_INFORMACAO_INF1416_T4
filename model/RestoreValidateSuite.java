package model;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.Signature;
import java.security.InvalidKeyException;
import java.security.SignatureException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Base64;
import java.util.Base64.Decoder;

import javax.security.cert.X509Certificate;
import javax.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;

import java.nio.channels.FileChannel;
// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------
public class RestoreValidateSuite {
    //ainda a ser testado
    public static boolean Validate(File digitalEnvelope, File digitalSignature, File arquivoENCriptografado, File certificadoUsuario, PrivateKey chaveUsuario){
        boolean result = false;
        int tamNameENV = digitalEnvelope.getName().length();
        int tamNameASD = digitalSignature.getName().length();
        int tamNameENC = arquivoENCriptografado.getName().length();
        int tamNameCRT = certificadoUsuario.getName().length();

        if(!digitalEnvelope.getName().substring(tamNameENV-4).equals(".env")){
            System.err.println("Envelope digital invalido");
            return false;
        }

        if(!arquivoENCriptografado.getName().substring(tamNameENC-4).equals(".enc")){
            System.err.println("Arquivo encriptografado invalido");
            return false;
        }

        if(!digitalSignature.getName().substring(tamNameASD-4).equals(".asd")){
            System.err.println("Assinatura digital invalido");
            return false;
        }

        if(!certificadoUsuario.getName().substring(tamNameCRT-4).equals(".crt")){
            System.err.println("Certificado invalido");
            return false;
        }

        String codenameENV = digitalEnvelope.getName().substring(0, tamNameENV-4);
        String codenameENC = arquivoENCriptografado.getName().substring(0, tamNameENC-4);
        String codenameASD = digitalSignature.getName().substring(0, tamNameASD-4);
        if(!(codenameENV.equals(codenameENC) && codenameENC.equals(codenameASD))){
            System.err.println("Envelope, Encriptado ou Assinatura são de diferentes arquivos");
            return false;
        }

        byte[] semente = null;
        {
        byte[] EnvelopeArray = byteFromFile(digitalEnvelope);
        semente = Decriptar("RSA/ECB/PKCS1Padding", EnvelopeArray, chaveUsuario);

        }

        byte[] arquivoDecriptografado = null;
        try {
        byte[] ENCriptedArray = byteFromFile(arquivoENCriptografado);

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(semente);
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, secureRandom);
        SecretKey KAES = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, KAES);
        arquivoDecriptografado = cipher.doFinal(ENCriptedArray);
        } catch(BadPaddingException e) {
            System.err.println("Erro no uso do padding na decriptografia do envelope");
            System.exit(1);
        } catch(NoSuchAlgorithmException e) {
            System.err.println("Algorithmo na decriptografia do envelope não encontrado");
            System.exit(1);
        } catch(NoSuchPaddingException e){
            System.err.println("Padding na decriptografia do envelope não encontrado");
            System.exit(1);
        } catch(InvalidKeyException e){
            System.err.println("Chave Invalida na decriptografia do envelope");
            System.exit(1);
        } catch(IllegalBlockSizeException e){
            System.err.println("Array de bytes foi feita de maneira incorreta");
            System.exit(1);
        }

        try{
        byte[] CertificateArray = byteFromFile(certificadoUsuario);
        X509Certificate certificado = X509Certificate.getInstance(CertificateArray);

        Signature assinaturaVerificacao = Signature.getInstance("SHA1withRSA");
        assinaturaVerificacao.initSign(chaveUsuario);
        assinaturaVerificacao.update(arquivoDecriptografado);
        byte[] assinaturaTeste = assinaturaVerificacao.sign();

        assinaturaVerificacao.initVerify(certificado.getPublicKey());
        assinaturaVerificacao.update(arquivoDecriptografado);
        result = assinaturaVerificacao.verify(assinaturaTeste);
        } catch(CertificateException e){
            System.err.println("Certificado Invalido");
            System.exit(1);
        } catch(NoSuchAlgorithmException e){
            System.err.println("Algorithmo de criptografia não encontrado");
            System.exit(1);
        } catch(InvalidKeyException e){
            System.err.println("chave invalida");
            System.exit(1);
        } catch(SignatureException e){
            System.err.println("erro na classe Signature");
            System.exit(1);
        }

        return result;
    }
    //ainda a ser testado
    public static PrivateKey RestorePrivateKey(File keyFile, String fraseSecreta){
        byte[] Kprivate = null;
        try{
        byte[] keyArray = byteFromFile(keyFile);

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(fraseSecreta.getBytes());
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, secureRandom);
        SecretKey KAES = keyGen.generateKey();

        Kprivate = Decriptar("AES/ECB/PKCS5Padding", keyArray, KAES);
        String editKeydata = new String(Kprivate);
        editKeydata = editKeydata.replaceAll("-----BEGIN PRIVATE KEY-----", "");
        editKeydata = editKeydata.replaceAll("-----END PRIVATE KEY-----", "");
        editKeydata = editKeydata.replaceAll("\\s","");
        Kprivate = editKeydata.getBytes();

        } catch(NoSuchAlgorithmException e){
            System.err.println("Algoritmo de geração de chave simétrica não encontrado");
            System.exit(1);
        }

        PKCS8EncodedKeySpec detalheChave = null;
        {
        Decoder decodificador = Base64.getDecoder();
        byte[] decodedBytes = new byte[Kprivate.length];
        int result = decodificador.decode(Kprivate, decodedBytes);
        detalheChave = new PKCS8EncodedKeySpec(decodedBytes);
        }

        PrivateKey chave = null;
        try {

        KeyFactory keyFac = KeyFactory.getInstance("RSA");
        chave = keyFac.generatePrivate(detalheChave);

        }catch(NoSuchAlgorithmException e){
            System.err.println("algoritmo AES não está disponível");
            System.exit(1);
        } catch(InvalidKeySpecException e){
            System.err.println();
            System.exit(1);
        }

        return chave;
    }
    //ainda a ser testado
    public static PublicKey RestorePublicKey(File CertificadoDigital){

        PublicKey chave = null;
        byte[] certificateArray = new byte[(int) CertificadoDigital.length()];
        try (FileInputStream inputStream = new FileInputStream(CertificadoDigital)) {
           inputStream.read(certificateArray);
        } catch(FileNotFoundException e){
            System.err.println("Certificado Digital não encontrado");
        } catch(IOException e){
            System.exit(1);
        }

        //verificar integridade do certificado digital

        try{
            X509Certificate certificado = X509Certificate.getInstance(certificateArray);
            certificado.checkValidity();

            chave = certificado.getPublicKey();
        }catch(CertificateException e){
            System.err.println("Erro no acesso do certificado digital");
            //o que façço aqui?
        }

        return chave;
    }
    private static byte[] byteFromFile(File arquivo){
        byte[] dataArray = new byte[(int) arquivo.length()];
        try (FileInputStream inputStream = new FileInputStream(arquivo)) {
           inputStream.read(dataArray);
        } catch(FileNotFoundException e){
            System.err.println("arquivo não encontrado");
            System.exit(1);
        } catch(IOException e){
            System.err.println("Houve erro na leitura do arquivo");
            System.exit(1);
        }
        return dataArray;
    }
    private static byte[] Decriptar(String tipo, byte[] dataArray, Key chave){
        byte[] result = null;
        try{
            Cipher cipher = Cipher.getInstance(tipo);
            cipher.init(Cipher.DECRYPT_MODE, chave);
            result = cipher.doFinal(dataArray);
        } catch(BadPaddingException e) {
            System.err.println("Erro no uso do padding na decriptografia do envelope");
            System.exit(1);
        } catch(NoSuchAlgorithmException e) {
            System.err.println("Algorithmo na decriptografia do envelope não encontrado");
            System.exit(1);
        } catch(NoSuchPaddingException e){
            System.err.println("Padding na decriptografia do envelope não encontrado");
            System.exit(1);
        } catch(InvalidKeyException e){
            System.err.println("Chave Invalida na decriptografia do envelope");
            System.exit(1);
        } catch(IllegalBlockSizeException e){
            System.err.println("Array de bytes foi feita de maneira incorreta");
            System.exit(1);
        }
        return result;
    }

    public static void DecryptFile(File digitalEnvelope, File digitalSignature, File arquivoENCriptografado, File certificadoUsuario, PrivateKey chaveUsuario){
        boolean validacao = Validate(digitalEnvelope, digitalSignature, arquivoENCriptografado, certificadoUsuario, chaveUsuario);
        if (!validacao){
            return;
        }
        byte[] semente = null;
        {
        byte[] EnvelopeArray = byteFromFile(digitalEnvelope);
        semente = Decriptar("RSA/ECB/PKCS1Padding", EnvelopeArray, chaveUsuario);

        }

        byte[] arquivoDecriptografado = null;
        try {
        byte[] ENCriptedArray = byteFromFile(arquivoENCriptografado);

        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(semente);
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, secureRandom);
        SecretKey KAES = keyGen.generateKey();

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, KAES);
        arquivoDecriptografado = cipher.doFinal(ENCriptedArray);
        } catch(BadPaddingException e) {
            System.err.println("Erro no uso do padding na decriptografia do envelope");
            System.exit(1);
        } catch(NoSuchAlgorithmException e) {
            System.err.println("Algorithmo na decriptografia do envelope não encontrado");
            System.exit(1);
        } catch(NoSuchPaddingException e){
            System.err.println("Padding na decriptografia do envelope não encontrado");
            System.exit(1);
        } catch(InvalidKeyException e){
            System.err.println("Chave Invalida na decriptografia do envelope");
            System.exit(1);
        } catch(IllegalBlockSizeException e){
            System.err.println("Array de bytes foi feita de maneira incorreta");
            System.exit(1);
        }
        //write decrypted file with FileChannel
        //FileOutputStream writer = new FileOutputStream(Endereco_file);
        //FileChannel channel = writer.getChannel();
        //ByteBuffer buff = ByteBuffer.wrap(arquivoDecriptografado);
        //channel.write(buff);
        //channel.close();
        //writer.close();
        System.out.println(arquivoDecriptografado);
    }
}
