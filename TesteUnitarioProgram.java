// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

import java.io.File;
import java.security.PublicKey;

import model.Base32;
import model.RestoreValidateSuite;
import model.TOTP;
import model.Base32.Alphabet;

import java.security.PrivateKey;

import java.lang.StringBuffer;

public class TesteUnitarioProgram {
    public static void main(String[] args){

        TOTP cobaia_auth = null;
        try{
        String testeKey = "codigoSecreto12345";
        String chaveCriptada = new Base32(Base32.Alphabet.BASE32, false, false).toString(testeKey.getBytes());

        cobaia_auth = new TOTP(chaveCriptada, 30);

        String teste = cobaia_auth.generateCode();
        System.out.println(teste);

        //cobaia_auth.validateCode(codigo);

        //if (cobaia_auth.validateCode(codigoTerceiro)){
        //    System.out.println("TOTP funcionando como esperado");
        //}else{
        //    System.out.println("Erro na autenticacao");
        //}
        //scan.close();

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
        String nomeArquivo = "";

        PublicKey chavePublica = RestoreValidateSuite.RestorePublicKey(certificado);
        PrivateKey chaveUsuario = RestoreValidateSuite.RestorePrivateKey(keyFile, fraseSecreta);
        RestoreValidateSuite.DecryptFile(envelope, assinatura, encriptado, certificado, chaveUsuario, nomeArquivo);
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
