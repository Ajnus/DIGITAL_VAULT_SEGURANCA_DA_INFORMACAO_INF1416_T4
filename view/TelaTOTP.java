package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

public class TelaTOTP {
    private static JFrame tela;
    private static int tentativas;
    private final int tentativasMax = 3;
    public TelaTOTP(){
        JFrame tela = new JFrame("Codigo Digital - Autenticação");
        JPanel painel = new JPanel(new BoxLayout(tela, BoxLayout.Y_AXIS));

        JPanel linha1  = new JPanel(new BoxLayout(painel, BoxLayout.X_AXIS));
        JPanel linha2  = new JPanel(new BoxLayout(painel, BoxLayout.X_AXIS));

        {
        JLabel TOTP = new JLabel("TOTP:");
        JTextField codigoCampo = new JTextField(10);
        linha1.add(TOTP);
        linha1.add(codigoCampo);
        }

        {
        JButton OK = new JButton("OK");
        JButton LIMPAR = new JButton("LIMPAR");
        linha2.add(OK);
        linha2.add(LIMPAR);
        }

        painel.add(linha1);
        painel.add(linha2);
        tela.add(painel);

        tela.setSize(200, 300);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }
}
