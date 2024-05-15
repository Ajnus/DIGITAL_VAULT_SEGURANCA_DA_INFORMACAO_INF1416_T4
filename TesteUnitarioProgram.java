import java.io.File;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.util.Scanner;
import java.lang.StringBuffer;
public class TesteUnitarioProgram {
    public static void main(String[] args){
        //Provider p = new org.bouncycastle.jce.provider.BouncyCastleProvider();

        TOTP cobaia_auth = null;
        try{
        String testeKey = "codigoSecreto12345";
        String chaveCriptada = new Base32(Base32.Alphabet.BASE32, true, true).toString(testeKey.getBytes());

        cobaia_auth = new TOTP(chaveCriptada, 30);
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
        PrivateKey chaveUsuario = RestoreValidateSuite.RestorePrivateKey(keyFile, fraseSecreta);
        boolean result = RestoreValidateSuite.Validate(envelope, assinatura, encriptado, certificado, chaveUsuario);
        System.out.println("teste de validação de assinatura: "+ result);
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
