package controler;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;

import model.TOTP;
// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

public class AutenticationControler {
    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    //log(Level level, String msg, Object[] params)
    public static boolean Authenticate(String nomeUsuario, String password){
        boolean result = false;

        //verifique nome de usuário
        {}
        //verifique a senha
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
        {}
        {}

        return result;
    }
    public static boolean AuthenticateTOTP(String password){
        boolean result = false;
        //chame a classe TOTP para verificar a password recebida
        {}

        return result;
    }
    public static boolean AuthenticateFileAccess(String codeName){
        boolean result = false;
        //chame a classe TOTP para verificar a password recebida
        {}

        return result;
    }
}
