// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controler.AutenticationControler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;


import controler.SystemControler;
import controler.AutenticationControler;

public class TelaTOTP {
    private static TelaTOTP janela;
    private static JFrame tela;

    private static JTextField campoCodigo;

    private static int tentativas = 0;
    private final int tentativasMax = 3;

    public static TelaTOTP getJanela(){
        if(janela==null){janela = new TelaTOTP();}
        return janela;
    }
    public JFrame getTela(){
        if(tela==null){janela = new TelaTOTP();}
        return tela;
    }
    public void setVisibility(boolean onOff){
        tela.setVisible(onOff);
    }

    private TelaTOTP(){
        tela = new JFrame("Codigo Digital - Autenticação");
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        JPanel linha1  = new JPanel();
        linha1.setLayout(new BoxLayout(linha1, BoxLayout.X_AXIS));
        JPanel linha2  = new JPanel();
        linha2.setLayout(new BoxLayout(linha2, BoxLayout.X_AXIS));

        {
        JLabel TOTP = new JLabel("TOTP:");
        campoCodigo = new JTextField(10);
        campoCodigo.addKeyListener(new KeyAdapter() {public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                e.consume();
            }
        };});
        linha1.add(TOTP);
        linha1.add(campoCodigo);
        }

        {
        JButton OK = new JButton("OK");
        OK.addActionListener(ActionEvent -> {
            //validao o codigo TOTP
            //positivo vai para a Tela principal
            //negativo limpa o campo e notifica o usuario, aumenta um no contador
            //caso o contador chegue no numero no tentativasMax, bloquei o usuario com aquele nome por 2 minutos
            boolean result = AutenticationControler.AuthenticateTOTP(campoCodigo.getText());
            if (result){
                SystemControler.Switch("TelaPrincipal");
                tentativas = 0;
            }
            else{
                tentativas++;
                if (tentativas >= tentativasMax){
                    tentativas = 0;
                    //bloqueia usuario por 2 minutos
                }
            }
            campoCodigo.setText("");
        });
        JButton LIMPAR = new JButton("LIMPAR");
        LIMPAR.addActionListener(ActionEvent -> {
            campoCodigo.setText("");
        });
        linha2.add(OK);
        linha2.add(LIMPAR);
        }

        painel.add(linha1);
        painel.add(linha2);
        tela.add(painel);

        tela.setSize(400, 600);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }
}
