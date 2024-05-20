// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.SystemControler;
import controler.AutenticationControler;

public class TelaEntrada {
    private static TelaEntrada janela;
    private static SystemControler comunicador;
    private static JFrame tela;
    private static JTextField campoNome;

    public static TelaEntrada getJanela(){
        if(janela==null){janela = new TelaEntrada();}
        return janela;
    }
    public JFrame getTela(){
        if(tela==null){janela = new TelaEntrada();}
        return tela;
    }
    public void setVisibility(boolean onOff){
        tela.setVisible(onOff);
    }

    private TelaEntrada(){
        tela = new JFrame("Cofre Digital - Autenticação");
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        {
        JLabel loginText = new JLabel("login name:");
        campoNome = new JTextField(256);
        JPanel linha1 = new JPanel();
        linha1.setLayout(new BoxLayout(linha1, BoxLayout.X_AXIS));
        linha1.add(loginText);linha1.add(campoNome);
        painel.add(linha1);
        }

        {
        JButton ok = new JButton("OK");
        ok.addActionListener(ActionEvent -> {
            //verifica se o nome existe na tabela usuario
            //caso positivo troca para telaSenha
            //caso negativo: apaga o campo e notifica o usuario
            boolean result = AutenticationControler.AuthenticateStep1(campoNome.getText());
            if (result){
                SystemControler.Switch("TelaSenha");
            }
            else{
                campoNome.setText("");
            }
        });
        JButton limpar = new JButton("LIMPAR");
        limpar.addActionListener(ActionEvent -> {
            TelaEntrada.campoNome.setText("");
        });
        JPanel linha2 = new JPanel();
        linha2.setLayout(new BoxLayout(linha2, BoxLayout.X_AXIS));
        linha2.add(ok);linha2.add(limpar);
        painel.add(linha2);
        }

        tela.add(painel);

        tela.setSize(400, 600);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }
}
