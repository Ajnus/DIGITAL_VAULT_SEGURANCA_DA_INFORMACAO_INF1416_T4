// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

package model;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
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
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;

import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.security.auth.DestroyFailedException;
import javax.security.auth.Destroyable;

import java.nio.channels.FileChannel;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.security.cert.CertificateFactory;
import java.io.ByteArrayInputStream;
import java.math.BigInteger;

// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------
public class RestoreValidateSuite {

    public static boolean Validate(File digitalEnvelope, File digitalSignature, File arquivoENCriptografado,
            File certificadoUsuario, PrivateKey chaveUsuario) {
        boolean result = false;
        int tamNameENV = digitalEnvelope.getName().length();
        int tamNameASD = digitalSignature.getName().length();
        int tamNameENC = arquivoENCriptografado.getName().length();
        int tamNameCRT = certificadoUsuario.getName().length();

        if (!digitalEnvelope.getName().substring(tamNameENV - 4).equals(".env")) {
            System.err.println("Envelope digital invalido");
            return false;
        }

        if (!arquivoENCriptografado.getName().substring(tamNameENC - 4).equals(".enc")) {
            System.err.println("Arquivo encriptografado invalido");
            return false;
        }

        if (!digitalSignature.getName().substring(tamNameASD - 4).equals(".asd")) {
            System.err.println("Assinatura digital invalido");
            return false;
        }

        if (!certificadoUsuario.getName().substring(tamNameCRT - 4).equals(".crt")) {
            System.err.println("Certificado invalido");
            return false;
        }

        String codenameENV = digitalEnvelope.getName().substring(0, tamNameENV - 4);
        String codenameENC = arquivoENCriptografado.getName().substring(0, tamNameENC - 4);
        String codenameASD = digitalSignature.getName().substring(0, tamNameASD - 4);
        if (!(codenameENV.equals(codenameENC) && codenameENC.equals(codenameASD))) {
            System.err.println("Envelope, Encriptado ou Assinatura são de diferentes arquivos");
            return false;
        }

        byte[] semente = null;
        {
            byte[] EnvelopeArray = byteFromFile(digitalEnvelope);
            semente = Decriptar("RSA/ECB/PKCS1Padding", EnvelopeArray, chaveUsuario);

            Arrays.fill(EnvelopeArray, (byte) 0);
        }

        byte[] arquivoDecriptografado = null;
        try {
            byte[] ENCriptedArray = byteFromFile(arquivoENCriptografado);

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(semente);
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, secureRandom);
            SecretKey KAES = keyGen.generateKey();

            // System.err.println("VALIDATE, KAES: " + KAES);

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, KAES);
            arquivoDecriptografado = cipher.doFinal(ENCriptedArray);

            Arrays.fill(ENCriptedArray, (byte) 0);

            /*
             * TO DO ?
             * if (KAES instanceof Destroyable) {
             * System.out.println("É DESTROYABLE\n");
             * KAES.destroy();
             * }
             */
        } catch (BadPaddingException e) {
            System.err.println("Erro no uso do padding na decriptografia do envelope");
            e.printStackTrace();
            System.exit(1);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algorithmo na decriptografia do envelope não encontrado");
            e.printStackTrace();
            System.exit(1);
        } catch (NoSuchPaddingException e) {
            System.err.println("Padding na decriptografia do envelope não encontrado");
            e.printStackTrace();
            System.exit(1);
        } catch (InvalidKeyException e) {
            System.err.println("Chave Invalida na decriptografia do envelope");
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalBlockSizeException e) {
            System.err.println("Array de bytes foi feita de maneira incorreta");
            e.printStackTrace();
            System.exit(1);
        } /*
           * catch (DestroyFailedException e) {
           * System.err.println(
           * "VALIDATE: Erro no processo de limpeza das variáveis locais no processo de obtenção de chave"
           * );
           * e.printStackTrace();
           * System.exit(1); TO DO?
           */

        try {
            byte[] CertificateArray = byteFromFile(certificadoUsuario);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificado = (X509Certificate) certificateFactory
                    .generateCertificate(new ByteArrayInputStream(CertificateArray));

            Signature assinaturaVerificacao = Signature.getInstance("SHA1withRSA");
            assinaturaVerificacao.initSign(chaveUsuario);
            assinaturaVerificacao.update(arquivoDecriptografado);
            byte[] assinaturaTeste = assinaturaVerificacao.sign();

            assinaturaVerificacao.initVerify(certificado.getPublicKey());
            assinaturaVerificacao.update(arquivoDecriptografado);
            result = assinaturaVerificacao.verify(assinaturaTeste);

            Arrays.fill(CertificateArray, (byte) 0);
            Arrays.fill(assinaturaTeste, (byte) 0);

        } catch (CertificateException e) {
            System.err.println("Certificado Invalido");
            e.printStackTrace();
            System.exit(1);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algorithmo de criptografia não encontrado");
            e.printStackTrace();
            System.exit(1);
        } catch (InvalidKeyException e) {
            System.err.println("chave invalida");
            e.printStackTrace();
            System.exit(1);
        } catch (SignatureException e) {
            System.err.println("erro na classe Signature");
            e.printStackTrace();
            System.exit(1);
        }

        {
            Arrays.fill(arquivoDecriptografado, (byte) 0);
            Arrays.fill(semente, (byte) 0);
        }

        return result;
    }

    public static PrivateKey RestorePrivateKey(File keyFile, String fraseSecreta) {
        byte[] KPriv = null;
        try {
            byte[] keyArray = byteFromFile(keyFile);

            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(fraseSecreta.getBytes());
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256, secureRandom);
            SecretKey KAES = keyGen.generateKey();

            // System.out.println("RESTOREPRIVATEKEY, KAES: " + KAES);

            KPriv = Decriptar("AES/ECB/PKCS5Padding", keyArray, KAES);
            String editKeydata = new String(KPriv);

            // System.out.println("RESTOREPRIVATEKEY, editKeydata: " + editKeydata);

            editKeydata = editKeydata.replaceAll("-----BEGIN PRIVATE KEY-----", "");
            editKeydata = editKeydata.replaceAll("-----END PRIVATE KEY-----", "");
            editKeydata = editKeydata.replaceAll("\\s", "");

            // System.out.println("RESTOREPRIVATEKEY, editKeydata:\n" + editKeydata);

            KPriv = editKeydata.getBytes();

            Arrays.fill(keyArray, (byte) 0);

            /*
             * TO DO ?
             * if (KAES instanceof Destroyable) {
             * System.out.println("É DESTROYABLE\n");
             * KAES.destroy();
             * }
             */
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algoritmo de geração de chave simétrica não encontrado");
            e.printStackTrace();
            System.exit(1);
        } /*
           * catch (DestroyFailedException e) {
           * System.err.println(
           * "RESTOREPRIVATEKEY: Erro no processo de limpeza das variáveis locais no processo de obtenção de chave"
           * );
           * e.printStackTrace();
           * System.exit(1); TO DO?
           */

        PKCS8EncodedKeySpec detalheChave = null;

        Decoder decodificador = Base64.getDecoder();
        byte[] decodedBytes = new byte[KPriv.length];
        int result = decodificador.decode(KPriv, decodedBytes); // ?
        detalheChave = new PKCS8EncodedKeySpec(decodedBytes);

        Arrays.fill(decodedBytes, (byte) 0);

        PrivateKey chave = null;
        try {
            KeyFactory keyFac = KeyFactory.getInstance("RSA");
            chave = keyFac.generatePrivate(detalheChave);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("algoritmo RSA não está disponível");
            e.printStackTrace();
            System.exit(1);
        } catch (InvalidKeySpecException e) {
            System.err.println();
            e.printStackTrace();
            System.exit(1);
        }

        return chave;
    }

    public static PublicKey RestorePublicKey(File CertificadoDigital) {

        PublicKey KPub = null;
        byte[] certificateArray = new byte[(int) CertificadoDigital.length()];
        try (FileInputStream inputStream = new FileInputStream(CertificadoDigital)) {
            inputStream.read(certificateArray);
        } catch (FileNotFoundException e) {
            System.err.println("Certificado Digital não encontrado");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificado = (X509Certificate) certificateFactory
                    .generateCertificate(new ByteArrayInputStream(certificateArray));
            Principal CAcertificate = certificado.getIssuerX500Principal();
            BigInteger serial = certificado.getSerialNumber();
            String algoritmoAss = certificado.getSigAlgName();
            String algoritmoOID = certificado.getSigAlgOID();
            // certificado.verify(chaveCA);

            System.out.println("CAcertificate:\n" + CAcertificate);
            System.out.println("serial: " + serial);
            System.out.println("algoritmoAss: " + algoritmoAss);
            System.out.println("algoritmoOID: " + algoritmoOID);

            certificado.checkValidity();

            KPub = certificado.getPublicKey();
        } catch (CertificateException e) {
            System.err.println("Erro no acesso do certificado digital");
            // o que façço aqui?
        }

        return KPub;
    }

    private static byte[] byteFromFile(File arquivo) {
        byte[] dataArray = new byte[(int) arquivo.length()];
        try (FileInputStream inputStream = new FileInputStream(arquivo)) {
            inputStream.read(dataArray);
        } catch (FileNotFoundException e) {
            System.err.println("arquivo não encontrado");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Houve erro na leitura do arquivo");
            e.printStackTrace();
            System.exit(1);
        }
        return dataArray;
    }

    private static byte[] Decriptar(String tipo, byte[] dataArray, Key chave) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(tipo);
            cipher.init(Cipher.DECRYPT_MODE, chave);
            result = cipher.doFinal(dataArray);
        } catch (BadPaddingException e) {
            System.err.println("Erro no uso do padding na decriptografia do envelope");
            e.printStackTrace();
            System.exit(1);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algorithmo na decriptografia do envelope não encontrado");
            e.printStackTrace();
            System.exit(1);
        } catch (NoSuchPaddingException e) {
            System.err.println("Padding na decriptografia do envelope não encontrado");
            e.printStackTrace();
            System.exit(1);
        } catch (InvalidKeyException e) {
            System.err.println("Chave Invalida na decriptografia do envelope");
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalBlockSizeException e) {
            System.err.println("Array de bytes foi feita de maneira incorreta");
            e.printStackTrace();
            System.exit(1);
        }
        return result;
    }

    public static void DecryptFile(File digitalEnvelope, File digitalSignature, File arquivoENCriptografado,
            File certificadoUsuario, PrivateKey chaveUsuario, String Endereco_file) {
        boolean validacao = Validate(digitalEnvelope, digitalSignature, arquivoENCriptografado, certificadoUsuario,
                chaveUsuario);
        if (!validacao) {
            return;
        }
        byte[] semente = null;
        {
            byte[] EnvelopeArray = byteFromFile(digitalEnvelope);
            semente = Decriptar("RSA/ECB/PKCS1Padding", EnvelopeArray, chaveUsuario);
            Arrays.fill(EnvelopeArray, (byte) 0);
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

            /*
             * TO DO ?
             * if (KAES instanceof Destroyable) {
             * System.out.println("É DESTROYABLE\n");
             * KAES.destroy();
             * }
             */
        } catch (BadPaddingException e) {
            System.err.println("Erro no uso do padding na decriptografia do envelope");
            e.printStackTrace();
            System.exit(1);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algorithmo na decriptografia do envelope não encontrado");
            e.printStackTrace();
            System.exit(1);
        } catch (NoSuchPaddingException e) {
            System.err.println("Padding na decriptografia do envelope não encontrado");
            e.printStackTrace();
            System.exit(1);
        } catch (InvalidKeyException e) {
            System.err.println("Chave Invalida na decriptografia do envelope");
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalBlockSizeException e) {
            System.err.println("Array de bytes foi feita de maneira incorreta");
            e.printStackTrace();
            System.exit(1);
        } /*
           * catch (DestroyFailedException e) {
           * System.err.println(
           * "RESTOREPRIVATEKEY: Erro no processo de limpeza das variáveis locais no processo de obtenção de chave"
           * );
           * e.printStackTrace();
           * System.exit(1); TO DO?
           */

        // write decrypted file with FileChannel
        try {
            File arquivoDecriptado = new File(Endereco_file);
            FileOutputStream writer = new FileOutputStream(arquivoDecriptado, false);
            FileChannel channel = writer.getChannel();
            ByteBuffer buff = ByteBuffer.wrap(arquivoDecriptografado);
            channel.write(buff);
            channel.close();
            writer.close();
            buff.clear();
            System.out.println("\n" + arquivoDecriptografado);
        } catch (FileNotFoundException e) {
            System.err.println("Não foi possivel disponibilizar o arquivo decriptografado");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Erro na escrita do arquivo decriptografado");
            e.printStackTrace();
            System.exit(1);
        }

    }
}
