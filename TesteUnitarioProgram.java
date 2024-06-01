// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PublicKey;

import model.Base32;
import model.RestoreValidateSuite;
import model.TOTP;
import model.Base32.Alphabet;

import java.security.PrivateKey;

import java.lang.StringBuffer;

public class TesteUnitarioProgram {
    public static void main(String[] args) {

        // TOTP cobaia_auth = null;
        // try{
        // String testeKey = "codigoSecreto12345";
        // String chaveCriptada = new Base32(Base32.Alphabet.BASE32, false,
        // false).toString(testeKey.getBytes());

        // cobaia_auth = new TOTP(chaveCriptada, 30);

        // String teste = cobaia_auth.generateCode();
        // System.out.println(teste);

        // cobaia_auth.validateCode(codigo);

        // if (cobaia_auth.validateCode(codigoTerceiro)){
        // System.out.println("TOTP funcionando como esperado");
        // }else{
        // System.out.println("Erro na autenticacao");
        // }
        // scan.close();

        // }catch(Exception e){System.err.println("Erro no teste de
        // TOTP");System.exit(1);}

        String fraseSecreta = "user01"; // TO DO: by linha de comando
        String pathkey = "SafeVault/Keys/user01-pkcs8-aes.pem"; // TO DO: by linha de comando
        String pathCrt = "SafeVault/Keys/user01-x509.crt"; // TO DO: by linha de comando
        String pathFiles = "SafeVault/Files"; // TO DO: by linha de comando
        String filePrefixString = "XXYYZZ00"; // TO DO: by linha de comando
        String baseFile = pathFiles + "/" + filePrefixString; // TO DO: by linha de comando

        // System.err.println(baseFile);

        String pathAsd = baseFile + ".asd";
        String pathEnc = baseFile + ".enc";
        String pathEnv = baseFile + ".env";

        File keyFile = new File(pathkey);
        File certificado = new File(pathCrt);
        File assinatura = new File(pathAsd);
        File encriptado = new File(pathEnc);
        File envelope = new File(pathEnv);

        // printBinaryFileContent(keyFile);
        // printFileContent(certificado);
        // printBinaryFileContent(assinatura);
        // printFileContent(encriptado);
        // printFileContent(envelope);

        String nomeArquivo = baseFile + ".new";

        PrivateKey chaveUsuario = RestoreValidateSuite.RestorePrivateKey(keyFile, fraseSecreta);
        PublicKey chavePublica = RestoreValidateSuite.RestorePublicKey(certificado);

        System.out.println("\nChave Privada: " + chaveUsuario.toString());
        System.out.println("\nChave Pública: " + chavePublica.toString());
        RestoreValidateSuite.DecryptFile(envelope, assinatura, encriptado,
                certificado, chaveUsuario, nomeArquivo);
    }

    public static String HexCodeString(byte[] hexCode) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < hexCode.length; i++) {
            String hex = Integer.toHexString(0x0100 + (hexCode[i] & 0x00FF)).substring(1);
            buf.append((hex.length() < 2 ? "0" : "") + hex);
        }

        return buf.toString();
    }

    public static void printBinaryFileContent(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            int byteRead;
            int count = 0;
            StringBuilder sb = new StringBuilder();
            while ((byteRead = fis.read()) != -1) {
                sb.append(String.format("%02x", byteRead));
                count++;
                if (count % 2 == 0) {
                    sb.append(" ");
                }
                if (count % 8 == 0) {
                    sb.append(" ");
                }
                if (count % 16 == 0) {
                    sb.append("\n");
                }
            }
            System.out.println(sb.toString().trim());
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public static void printFileContent(File file) {
        try (FileInputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

}
