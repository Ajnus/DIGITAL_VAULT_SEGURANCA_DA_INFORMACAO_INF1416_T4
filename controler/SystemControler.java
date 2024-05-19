package controler;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;

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
    private static JFrame telaAtual;

    private static TelaPrincipal principal;
    private static TelaRegistro cadastro;
    private static TelaTOTP TOTP;
    private static TelaListarAcesso consulta;
    private static TelaEntrada entrada;
    private static TelaSaida saida;
    private static TelaSenha senha;

    public SystemControler(boolean primeira){
        principal = TelaPrincipal.getJanela();
        principal.getTela().setVisible(false);
        cadastro = TelaRegistro.getJanela();
        cadastro.chama(primeira);cadastro.getTela().setVisible(false);
        TOTP = TelaTOTP.getJanela();
        TOTP.getTela().setVisible(false);
        consulta = TelaListarAcesso.getJanela();
        consulta.getTela().setVisible(false);
        entrada = TelaEntrada.getJanela();
        entrada.getTela().setVisible(false);
        senha = TelaSenha.getJanela();
        senha.getTela().setVisible(false);
        saida = TelaSaida.getJanela();
        saida.getTela().setVisible(false);

        if (primeira){
            telaAtual = cadastro.getTela();
            cadastro.getTela().setVisible(true);
        }else{
            telaAtual = cadastro.getTela();
            cadastro.getTela().setVisible(true);
        }
    }
    private static void TurnOff(){sistema.shutDown();}
    public static Logger getSistemaLogger(){return sistema.log;}

    public static void Switch(String tela){
        telaAtual.setVisible(false);
        switch(tela){
            case "TelaListarAcesso":
                telaAtual = consulta.getTela();
                break;
            case "TelaPrincipal":
                telaAtual = principal.getTela();
                break;
            case "TelaRegistro":
                telaAtual = cadastro.chama(false).getTela();
                break;
            case "TelaRegistroInicio":
                telaAtual = cadastro.chama(true).getTela();
                break;
            case "TelaEntrada":
                telaAtual = entrada.getTela();
                break;
            case "TelaSaida":
                telaAtual = saida.getTela();
                break;
            case "TelaSenha":
                telaAtual = senha.getTela();
                break;
            case "TelaTOTP":
                telaAtual = TOTP.getTela();
                break;
            default:
                break;
        }
        telaAtual.setVisible(true);
    }

}