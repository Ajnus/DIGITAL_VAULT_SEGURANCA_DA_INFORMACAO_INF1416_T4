package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.SystemControler;

// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------


public class TelaPrincipal{
    private static final TelaPrincipal janela = new TelaPrincipal();
    private static JFrame tela;

    private static JLabel usuarioNome;
    private static JLabel grupoNome;
    private static JLabel email;
    private static JLabel numAcesso;

    public static TelaPrincipal getJanela(){return janela;}
    public JFrame getTela(){return tela;}
    public void setUsuario(String nome){usuarioNome.setText("Usuario: " + nome);}
    public void setGrupo(String nome){grupoNome.setText("Grupo: " + nome);}
    public void setEmail(String nome){email.setText("email: "+ nome);}
    public void setAcessos(int qtd){numAcesso.setText("Numero de acessos: "+ qtd);}

    private TelaPrincipal(){
        tela = new JFrame("Tela Principal");
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));
        JPanel estatistica = new JPanel();
        estatistica.setLayout(new BoxLayout(estatistica, BoxLayout.Y_AXIS));
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));

        {
        //adicionar elementos do cabecalho
        usuarioNome = new JLabel("Usuario: ");
        grupoNome = new JLabel("Grupo: ");
        email = new JLabel("Email: ");
        cabecalho.add(usuarioNome);
        cabecalho.add(grupoNome);
        cabecalho.add(email);

        //adiciona elemento <<total de acesso do  usuário>> da estatistica
        numAcesso = new JLabel("Numero de acessos: ");
        estatistica.add(numAcesso);
        }

        {
        JButton cadastro = new JButton("cadastrar usuário");
        cadastro.addActionListener(ActionEvent -> {
            SystemControler.Switch("TelaCadastro");
        });

        JButton consultar = new JButton("consultar arquivos");
        consultar.addActionListener(ActionEvent -> {
            SystemControler.Switch("TelaListarAcesso");
        });

        JButton sair = new JButton("sair");
        sair.addActionListener(ActionEvent -> {
            SystemControler.Switch("TelaSaida");
        });
        corpo.add(cadastro);
        corpo.add(consultar);
        corpo.add(sair);
        }

        painel.add(cabecalho);
        painel.add(estatistica);
        painel.add(corpo);
        tela.add(painel);
        tela.setSize(200, 300);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }
}
