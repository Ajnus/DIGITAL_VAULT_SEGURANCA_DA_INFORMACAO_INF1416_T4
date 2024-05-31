// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

package controler;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;

import javax.crypto.SecretKey;

import model.Sistema;
import view.TelaListarAcesso;
import view.TelaPrincipal;
import view.TelaRegistro;
import view.TelaSaida;
import view.TelaSenha;
import view.TelaTOTP;
import view.TelaEntrada;

import javax.swing.JFrame;

// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

public class SystemControler {
    private static final Sistema sistema = Sistema.getInstance();
    private static SystemControler controle;
    private static JFrame telaAtual;

    private static TelaPrincipal principalTela;
    private static TelaRegistro cadastroTela;
    private static TelaTOTP TOTPTela;
    private static TelaListarAcesso consultaTela;
    private static TelaEntrada entradaTela;
    private static TelaSaida saidaTela;
    private static TelaSenha senhaTela;

    private static SecretKey chaveAcessoUsuario;
    private static String nomeUsuario;

    public void setKey(SecretKey chave){chaveAcessoUsuario=chave;}
    public void setNome(String nome){nomeUsuario=nome;}
    protected static String getNome(){return nomeUsuario;}

    public static SystemControler Start(){
        if(controle==null){controle = new SystemControler(true);}
        return controle;
    }

    public static void dropSession(){
        chaveAcessoUsuario=null;
        Switch("TelaEntrada");
    }

    private SystemControler(boolean primeira){
        principalTela = TelaPrincipal.getJanela();
        principalTela.setVisibility(true);

        cadastroTela = TelaRegistro.getJanela();
        cadastroTela.set(primeira);
        cadastroTela.setVisibility(true);

        TOTPTela = TelaTOTP.getJanela();
        TOTPTela.setVisibility(true);

        consultaTela = TelaListarAcesso.getJanela();
        consultaTela.setVisibility(true);

        entradaTela = TelaEntrada.getJanela();
        entradaTela.setVisibility(true);

        senhaTela = TelaSenha.getJanela();
        senhaTela.setVisibility(true);

        saidaTela = TelaSaida.getJanela();
        saidaTela.setVisibility(true);

        if (primeira){
            telaAtual = cadastroTela.getTela();
            telaAtual.setVisible(true);
        }else{
            telaAtual = entradaTela.getTela();
            telaAtual.setVisible(true);
        }
    }
    private static void TurnOff(){sistema.shutDown();}
    public static Logger getSistemaLogger(){return sistema.log;}

    public static void Switch(String tela){
        if (telaAtual == null){SystemControler.Start();}
        switch(tela){
            case "TelaListarAcesso":
                telaAtual = consultaTela.getTela();
                break;
            case "TelaPrincipal":
                if (principalTela == null){System.out.println("PROBLEMA COLOSSAL");}
                telaAtual = principalTela.getTela();
                break;
            case "TelaRegistro":
                cadastroTela.set(false);
                telaAtual = cadastroTela.getTela();
                break;
            case "TelaEntrada":
                telaAtual = entradaTela.getTela();
                break;
            case "TelaSaida":
                telaAtual = saidaTela.getTela();
                break;
            case "TelaSenha":
                telaAtual = senhaTela.getTela();
                break;
            case "TelaTOTP":
                telaAtual = TOTPTela.getTela();
                break;
            case "TelaCadastro":
                telaAtual = cadastroTela.getTela();
                break;
            default:
                break;
        }
        telaAtual.setVisible(true);
    }

}