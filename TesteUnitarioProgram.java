import java.io.File;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.util.Scanner;

public class TesteUnitarioProgram {
    public static void main(String[] args){
        //Provider p = new org.bouncycastle.jce.provider.BouncyCastleProvider();

        TOTP cobaia_auth = null;
        try{
        cobaia_auth = new TOTP("codigoSecreto", 30);
        String codigo = cobaia_auth.generateCode();
        System.out.println("Codigo de autenticacao do teste: " + codigo);
        cobaia_auth.validateCode(codigo);
        Scanner scan = new Scanner(System.in);
        System.out.println("Entre codigo de autenticacao da cobaia");
        String codigoTerceiro = scan.nextLine();
        if (cobaia_auth.validateCode(codigoTerceiro)){
            System.out.println("TOTP funcionando como esperado");
        }else{
            System.out.println("Erro na autenticacao");
        }
        scan.close();
        }catch(Exception e){System.err.println("Erro no teste de TOTP");System.exit(1);}

        String pathAsd = "Pacote-T4/Files/index.asd";
        String pathEnv = "Pacote-T4/Files/index.env";
        String pathEnc = "Pacote-T4/Files/index.enc";
        String pathCrt = "Pacote-T4/Keys/admin-x509.crt";
        File assinatura = new File(pathAsd);
        File envelope = new File(pathEnv);
        File encriptado = new File(pathEnc);
        File certificado = new File(pathCrt);

        String pathkey = "Pacote-T4/Keys/admin-pkcs8-aes.pem";
        File keyFile = new File(pathkey);
        String fraseSecreta = "admin";

        PublicKey chavePublica = RestoreValidateSuite.RestorePublicKey(certificado);
        //System.out.println("Public key: "+ new Base32(Base32.Alphabet.BASE32,true,true).toString(chavePublica.getEncoded()));
        PrivateKey chaveUsuario = RestoreValidateSuite.RestorePrivateKey(keyFile, fraseSecreta);
        //boolean result = RestoreValidateSuite.Validate(envelope, assinatura, encriptado, certificado, chaveUsuario);
    }
}
