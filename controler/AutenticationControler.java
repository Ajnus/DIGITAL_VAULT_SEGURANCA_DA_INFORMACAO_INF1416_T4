// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

package controler;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;

import model.TOTP;
import model.RestoreValidateSuite;
import model.Base32;
import model.Authentication;

public class AutenticationControler {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    //log(Level level, String msg, Object[] params)
    public static boolean AuthenticateStep1(String nomeUsuario){
        boolean result = false;

        //verifique se nome de usuário está no banco
        {}

        return result;
    }
    public static boolean AuthenticateStep2(String[] possibilidades){
        boolean result = false;
        String nomeUsuario = SystemControler.getNome();
        //verifique a senha do usuário
        {}

        return result;
    }
    public static boolean Authenticate(String fraseSecreta){
        boolean result = false;

        //verifique a fraseSecreta do administrador
        {}

        return result;
    }
    public static boolean AuthenticateRegister(String novoUsuario){
        boolean result = false;
        //verifique cada detalhe do cadastro separado no entra chaves abaixo
        {}

        return result;
    }
    public static boolean AuthenticateTOTP(String password){
        boolean result = false;
        //chame a classe TOTP para verificar a password recebida
        try{
            TOTP checker = new TOTP(Base32.Alphabet.BASE32, 30);
            result = checker.validateCode(password);
        }catch(Exception e){
            System.err.println("Erro na terceira etapa de aplicação");
            return false;
        }

        return result;
    }
    public static boolean AuthenticateFileAccess(String codeName){
        boolean result = false;
        //chame a classe TOTP para verificar a password recebida
        {}

        return result;
    }
}
