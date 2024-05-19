package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import javafx.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.SystemControler;

// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------


public class TelaPrincipal{
    private static final TelaPrincipal janela = new TelaPrincipal();
    private static JFrame tela;
    private static SystemControler comunicador;

    public static TelaPrincipal getJanela(){return janela;}
    public JFrame getTela(){return tela;}

    private TelaPrincipal(){
        tela = new JFrame("Tela Principal");
        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));
        JPanel estatistica = new JPanel();
        estatistica.setLayout(new BoxLayout(estatistica, BoxLayout.Y_AXIS));
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(tela, BoxLayout.Y_AXIS));
        {
            //adiciona elementos do cabeçalho
            //JPanel usuario
            //Jpanel Grupo
            //JPanel email
        }

        {
            //adiciona elemento <<total de acessos do usuario>> da estatistica
            //JPanel numeroAcesso
        }

        {
        JButton cadastro = new JButton("cadastrar usuário");
        //cadastro.addActionListener(new ActionListener() { 
        //    public void actionPerformed(ActionEvent e) { 
        //        comunicador.switch("TelaRegistro");
        //    }
        //});

        JButton consultar = new JButton("consultar arquivos");
        //consultar.addActionListener(new ActionListener() { 
        //    public void actionPerformed(ActionEvent e) { 
        //        comunicador.switch("TelaConsulta");
        //    }
        //});

        JButton sair = new JButton("sair");
        //sair.addActionListener(new ActionListener() { 
        //    public void actionPerformed(ActionEvent e) { 
        //        comunicador.switch("TelaSaida");
        //    }
        //});

        }

        tela.add(cabecalho);
        tela.add(estatistica);
        tela.add(corpo);
        tela.setSize(400, 600);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }
}
