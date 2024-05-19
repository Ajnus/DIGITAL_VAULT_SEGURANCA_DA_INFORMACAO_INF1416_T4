package view;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPasswordField;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

public class TelaSenha {
    private static final TelaSenha janela = new TelaSenha();
    private static JFrame tela;

    public static TelaSenha getJanela(){return janela;}
    public JFrame getTela(){return tela;}

    private TelaSenha(){
        tela = new JFrame("Cofre Digital - Autenticação");
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        JPanel campo = new JPanel();
        campo.setLayout(new BoxLayout(campo, BoxLayout.X_AXIS));
        JPanel teclado = new JPanel();
        teclado.setLayout(new BoxLayout(teclado, BoxLayout.X_AXIS));
        {
            JLabel texto = new JLabel("Senha Pessoal:");
            JPasswordField senhaCampo = new JPasswordField(10);
            campo.add(texto);
            campo.add(senhaCampo);
        }

        {
        JPanel tecladoSobreCarregado = new JPanel();
        tecladoSobreCarregado.setLayout(new GridLayout(2, 3));
        JPanel confirmaLimpa = new JPanel();
        confirmaLimpa.setLayout(new GridLayout(2, 1));

        ArrayList<String> algarismos = new ArrayList<>(Arrays.asList("0","1","2","3","4","5","6","7","8","9"));
        Collections.shuffle(algarismos);
        for(int i=0;i<5;i++){
            JButton tecla = new JButton(algarismos.get(i) + " ou " + algarismos.get(i+1));
            tecladoSobreCarregado.add(tecla);
        }
        JButton vazio = new JButton();
        vazio.setEnabled(false);
        tecladoSobreCarregado.add(vazio);

        JButton CONFIRMAR = new JButton("CONFIRMAR");
        JButton LIMPAR = new JButton("LIMPAR");

        confirmaLimpa.add(CONFIRMAR);
        confirmaLimpa.add(LIMPAR);

        teclado.add(tecladoSobreCarregado);
        teclado.add(confirmaLimpa);
        }

        painel.add(campo);
        painel.add(teclado);

        tela.add(painel);
        tela.setSize(400, 600);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }
}
