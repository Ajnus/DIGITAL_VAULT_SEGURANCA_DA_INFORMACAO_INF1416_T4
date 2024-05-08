import java.io.File;
import java.security.PublicKey;
import java.security.PrivateKey;

public class TesteUnitarioProgram {
    public static void main(){

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
        PrivateKey chaveUsuario = RestoreValidateSuite.RestorePrivateKey(keyFile, fraseSecreta);
        boolean result = RestoreValidateSuite.Validate(envelope, assinatura, encriptado, certificado, chaveUsuario);
    }
}
