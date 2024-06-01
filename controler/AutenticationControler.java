// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

package controler;

import model.TOTP;
import model.RestoreValidateSuite;
import model.Base32;
import model.Authentication;

public class AutenticationControler {
    //
    public static boolean AuthenticateStep1(String nomeUsuario){
        boolean result = false;

        //verifique se nome de usuário está no banco
        {

        }

        if (result){

        }
        else{

        }
        return result;
    }
    public static boolean AuthenticateStep2(String[] possibilidades){
        boolean result = false;
        String nomeUsuario = SystemControler.getNome();
        String[] senhasPossiveis = new String[(int) Math.pow(2,possibilidades.length)];

        String[] escolhas = new String[possibilidades.length];
        for(int i = 0; i<possibilidades.length; i++){
            String[] opcao;
            if (possibilidades[i].matches("ou"))
            {
                opcao = new String[2];
                opcao[0] = possibilidades[i].substring(0,1);
                opcao[1] = possibilidades[i].substring(-1);
            }
            else
            {
                opcao = new String[1];
                opcao[0] = possibilidades[i];
            }
            //escolhas[i] = opcao;
        }
        //verifique a senha do usuário
        {

            for(int i = 0; i < senhasPossiveis.length; i++)
            {

            }
        }

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
