package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

import controler.SystemControler;

public class TelaEntrada {
    private static final TelaEntrada janela = new TelaEntrada();
    private static SystemControler comunicador;
    private static JFrame tela;

    public static TelaEntrada getJanela(){return janela;}
    public JFrame getTela(){return tela;}

    private TelaEntrada(){
        tela = new JFrame("Cofre Digital - Autenticação");
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        {
        JLabel loginText = new JLabel("login name:");
        JTextField nomeUsuarioCampo = new JTextField(256);
        JPanel linha1 = new JPanel();
        linha1.setLayout(new BoxLayout(linha1, BoxLayout.X_AXIS));
        linha1.add(loginText);linha1.add(nomeUsuarioCampo);
        painel.add(linha1);
        }
        
        {
        JButton ok = new JButton("OK");
        JButton limpar = new JButton("LIMPAR");
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
