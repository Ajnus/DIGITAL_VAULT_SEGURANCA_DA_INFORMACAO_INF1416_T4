package model;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

import java.nio.ByteOrder;
import java.security.Security;
import java.security.Provider;
// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

public class TOTP {
    private byte [] key = null;
    private long timeStepInSeconds = 30;
    // Construtor da classe. Recebe a chave secreta em BASE32 e o intervalo
    // de tempo a ser adotado (default = 30 segundos). Deve decodificar a
    // chave secreta e armazenar em key. Em caso de erro, gera Exception.
    public TOTP(String base32EncodedSecret, long timeStepInSeconds)
    throws Exception {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
          }

        boolean checkAlphabet = base32EncodedSecret.contains("[?!" + Base32.Alphabet.BASE32 + "]+");
        if (checkAlphabet) {
            System.err.println("String não esta de acordo com BASE32");
            System.exit(1);
        }

        Base32 base = new Base32(Base32.Alphabet.BASE32, false, false);
        key = base.fromString(base32EncodedSecret);

        this.timeStepInSeconds = timeStepInSeconds;
    }
    // Recebe o HASH HMAC-SHA1 e determina o código TOTP de 6 algarismos
    // decimais, prefixado com zeros quando necessário.
    private String getTOTPCodeFromHash(byte[] hash) {
        String result = null;
        //extrair bytes significativos
        //https://datatracker.ietf.org/doc/html/rfc4226#section-5.4
        int offset = hash[19] & 0xf;
        int bin_code = (hash[offset] & 0x7f) << 24 |
            (hash[offset+1] & 0xff) << 16 |
            (hash[offset+2] & 0xff) << 8 |
            (hash[offset+3] & 0xff) << 0;

        result = String.format("%06d", bin_code % (1000000));
        return result;
    }
    // Recebe o contador e a chave secreta para produzir o hash HMAC-SHA1.
    private byte[] HMAC_SHA1(byte[] counter, byte[] keyByteArray) {
        byte[] result = null;
        try{
            Mac hmacsha1 = Mac.getInstance("HmacSHA1");
            SecretKeySpec chave = new SecretKeySpec(keyByteArray, "HmacSHA1");

            hmacsha1.init(chave);

            result = hmacsha1.doFinal(counter);
            chave.destroy();
        } catch(Exception e) {
            System.err.println("Erro na criacao do HMAC do TOTP");
        }
        return result;
    }
    // Recebe o intervalo de tempo e executa o algoritmo TOTP para produzir
    // o código TOTP. Usa os métodos auxiliares getTOTPCodeFromHash e HMAC_SHA1.
    public String TOTPCode(long timeInterval) {
        long numIntervalos = timeInterval / 1000 / timeStepInSeconds;

        byte[] counter = null;
        {
        if (ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN)){
            counter = new byte[]{
                (byte) ((numIntervalos >> 56) & 0xff),
                (byte) ((numIntervalos >> 48) & 0xff),
                (byte) ((numIntervalos >> 40) & 0xff),
                (byte) ((numIntervalos >> 32) & 0xff),
                (byte) ((numIntervalos >> 24) & 0xff),
                (byte) ((numIntervalos >> 16) & 0xff),
                (byte) ((numIntervalos >>  8) & 0xff),
                (byte) ((numIntervalos >>  0) & 0xff)
            };
        }

        else{
            counter = new byte[]{
                (byte) ((numIntervalos >>  0) & 0xff),
                (byte) ((numIntervalos >>  8) & 0xff),
                (byte) ((numIntervalos >> 16) & 0xff),
                (byte) ((numIntervalos >> 24) & 0xff),
                (byte) ((numIntervalos >> 32) & 0xff),
                (byte) ((numIntervalos >> 40) & 0xff),
                (byte) ((numIntervalos >> 48) & 0xff),
                (byte) ((numIntervalos >> 56) & 0xff)
            };
        }
        }

        byte[] HMAC_hash = HMAC_SHA1(counter, key);

        System.out.println(timeInterval);
        System.out.println(numIntervalos);
        System.out.println(HexCodeString(key));
        System.out.println(HexCodeString(counter));
        System.out.println(HexCodeString(HMAC_hash));

        String result = getTOTPCodeFromHash(HMAC_hash);

        numIntervalos = 0;
        for (int i = 0; i < HMAC_hash.length; i++){
            HMAC_hash[i] = (byte) 0;
        }
        for (int i=0;i<counter.length;i++){
            counter[i] = (byte)0;
        }

        return result;
    }
    // Método que é utilizado para solicitar a geração do código TOTP.
    public String generateCode() {
        return TOTPCode(new Date().getTime());
    }
    // Método que é utilizado para validar um código TOTP (inputTOTP).
    // Deve considerar um atraso ou adiantamento de 30 segundos no
    // relógio da máquina que gerou o código TOTP.
    public boolean validateCode(String inputTOTP) {
        try{
            Integer.parseInt(inputTOTP);
        }catch(Exception e){return false;}

        long margemErro = this.timeStepInSeconds * 1000;
        long currentTime = new Date().getTime();
        String TOTP1 = TOTPCode(currentTime - margemErro);
        String TOTP2 = TOTPCode(currentTime);
        String TOTP3 = TOTPCode(currentTime + margemErro);

        System.out.println(TOTP1);
        System.out.println(TOTP2);
        System.out.println(TOTP3);

        boolean result = TOTP1.equals(inputTOTP) || TOTP2.equals(inputTOTP) || TOTP3.equals(inputTOTP);

        margemErro = 0;
        currentTime = 0;
        TOTP1 = "";
        TOTP2 = "";
        TOTP3 = "";

        return result;
    }

    public static String HexCodeString(byte[] hexCode)
	{
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < hexCode.length; i++)
		{
			String hex = Integer.toHexString(0x0100 + (hexCode[i] & 0x00FF)).substring(1);
			buf.append((hex.length() < 2 ? "0" : "") + hex);
		}

		return buf.toString();
	}
}
