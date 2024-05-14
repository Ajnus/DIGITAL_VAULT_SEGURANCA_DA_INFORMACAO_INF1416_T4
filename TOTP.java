import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
public class TOTP {
    private byte [] key = null;
    private long timeStepInSeconds = 30;
    // Construtor da classe. Recebe a chave secreta em BASE32 e o intervalo
    // de tempo a ser adotado (default = 30 segundos). Deve decodificar a
    // chave secreta e armazenar em key. Em caso de erro, gera Exception.
    public TOTP(String base32EncodedSecret, long timeStepInSeconds)
    throws Exception {

        boolean checkAlphabet = base32EncodedSecret.contains("[?!"+Base32.Alphabet.BASE32+"]+") &
        base32EncodedSecret.contains("[?!"+Base32.Alphabet.BASE32HEX+"]+");

        if (checkAlphabet) {
            System.err.println("String não esta de acordo com a base 32");
            System.exit(1);
        }
        Base32 base = new Base32(Base32.Alphabet.BASE32, true, true);
        key = base.fromString(base32EncodedSecret);

        this.timeStepInSeconds = timeStepInSeconds;
    }
    // Recebe o HASH HMAC-SHA1 e determina o código TOTP de 6 algarismos
    // decimais, prefixado com zeros quando necessário.
    private String getTOTPCodeFromHash(byte[] hash) {
        String result = null;
        //extrair bytes significativos
        int offset = hash[19] & 0xf;
        int bin_code = (hash[offset] & 0x7f) << 24 |
            (hash[offset] & 0xff) << 16 |
            (hash[offset] & 0xff) << 8 |
            (hash[offset] & 0xff) << 0;

        result = String.format("%06d", bin_code % (1000000));
        //https://datatracker.ietf.org/doc/html/rfc4226#section-5.4
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
        } catch(Exception e) {
            System.err.println("Erro no usa do algoritmo SHA1 na criacao do HMAC");
        }
        return result;
    }
    // Recebe o intervalo de tempo e executa o algoritmo TOTP para produzir
    // o código TOTP. Usa os métodos auxiliares getTOTPCodeFromHash e HMAC_SHA1.
    private String TOTPCode(long timeInterval) {
        long timeStepInMiliseconds = 1000 * timeStepInSeconds;
        long numIntervalos = timeInterval / timeStepInMiliseconds;

        byte[] counter = new byte[]{
            (byte) ((numIntervalos >> 56) & 0xff),
            (byte) ((numIntervalos >> 48) & 0xff),
            (byte) ((numIntervalos >> 40) & 0xff),
            (byte) ((numIntervalos >> 32) & 0xff),
            (byte) ((numIntervalos >> 24) & 0xff),
            (byte) ((numIntervalos >> 16) & 0xff),
            (byte) ((numIntervalos >> 8) & 0xff),
            (byte) ((numIntervalos >> 0) & 0xff)
        };

        byte[] HMAC_hash = HMAC_SHA1(counter, key);
        String result = getTOTPCodeFromHash(HMAC_hash);
        System.out.println(result);
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

        boolean result = false;
        long margimErro = this.timeStepInSeconds *1000;
        long currentTime = new Date().getTime();
        String TOTP1 = TOTPCode(currentTime);
        String TOTP2 = TOTPCode(currentTime - margimErro);
        String TOTP3 = TOTPCode(currentTime + margimErro);
        result = TOTP1.equals(inputTOTP) || TOTP2.equals(inputTOTP) || TOTP3.equals(inputTOTP);
        return result;
    }
}
