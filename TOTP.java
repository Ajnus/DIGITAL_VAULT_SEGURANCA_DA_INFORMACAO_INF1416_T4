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
        this.timeStepInSeconds = timeStepInSeconds;
        Base32 base = new Base32(Base32.Alphabet.BASE32, true, true);
        key = base.fromString(base32EncodedSecret);
    }
    // Recebe o HASH HMAC-SHA1 e determina o código TOTP de 6 algarismos
    // decimais, prefixado com zeros quando necessário.
    private String getTOTPCodeFromHash(byte[] hash) {

    }
    // Recebe o contador e a chave secreta para produzir o hash HMAC-SHA1.
    private byte[] HMAC_SHA1(byte[] counter, byte[] keyByteArray) {
        byte[] result = null;
        try{
            Mac hmacsha1 = Mac.getInstance("SHA1");
            SecretKeySpec chave = new SecretKeySpec(keyByteArray, 0, 20, "SHA1");
            hmacsha1.init(chave);
            result = hmacsha1.doFinal(counter);
        } catch(Exception e) {
            Sistema.err.println("Erro no usao do algoritmo SHA1 na criação do HMAC");
        }
        return result;
    }
    // Recebe o intervalo de tempo e executa o algoritmo TOTP para produzir
    // o código TOTP. Usa os métodos auxiliares getTOTPCodeFromHash e HMAC_SHA1.
    private String TOTPCode(long timeInterval) {
        long numIntervalos = timeInterval / 1000 / this.timeStepInSeconds;
    }
    // Método que é utilizado para solicitar a geração do código TOTP.
    public String generateCode() {
        return TOTPCode(new Date().getTime());
    }
    // Método que é utilizado para validar um código TOTP (inputTOTP).
    // Deve considerar um atraso ou adiantamento de 30 segundos no
    // relógio da máquina que gerou o código TOTP.
    public boolean validateCode(String inputTOTP) {
        boolean result = false;
        boolean TOTP1;
        boolean TOTP2;
        boolean TOTP3;
        return result;
    }
}