// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controler.SystemControler;

import javax.swing.JTable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaListarAcesso {
    private static final TelaListarAcesso janela = new TelaListarAcesso();
    private static JFrame tela;

    private static JLabel usuarioNome;
    private static JLabel grupoNome;
    private static JLabel email;
    private static JLabel numAcesso;

    public static TelaListarAcesso getJanela(){return janela;}
    public JFrame getTela(){return tela;}
    public void setUsuario(String nome){usuarioNome.setText("Usuario: " + nome);}
    public void setGrupo(String nome){grupoNome.setText("Grupo: " + nome);}
    public void setEmail(String nome){email.setText("email: "+ nome);}
    public void setAcessos(int qtd){numAcesso.setText("Numero de acessos: "+ qtd);}

    private TelaListarAcesso(){
        tela = new JFrame();
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel,BoxLayout.Y_AXIS));
        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(new BoxLayout(cabecalho,BoxLayout.Y_AXIS));
        JPanel estatistica = new JPanel();
        estatistica.setLayout(new BoxLayout(estatistica,BoxLayout.Y_AXIS));
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo,BoxLayout.Y_AXIS));
        JTable listaArquivo;

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
        JLabel pastaEndereco = new JLabel("caminho da pasta:");
        JTextField campoPasta = new JTextField(255);
        JPanel pasta = new JPanel();
        pasta.setLayout(new BoxLayout(pasta, BoxLayout.X_AXIS));
        pasta.add(pastaEndereco); pasta.add(campoPasta);

        JLabel FraseSecreta = new JLabel("FraseSecreta:");
        JPasswordField CampoFrase = new JPasswordField(255);
        JPanel frase = new JPanel();
        frase.setLayout(new BoxLayout(frase, BoxLayout.X_AXIS));
        frase.add(FraseSecreta); frase.add(CampoFrase);

        JButton Listar = new JButton("Listar");
//        Listar.addActionListener(ActionEvent -> {
//
//        });

        listaArquivo = new JTable();

        JButton Voltar = new JButton("Voltar");
        Voltar.addActionListener(ActionEvent -> {
            SystemControler.Switch("TelaPrincipal");
        });

        corpo.add(pasta);
        corpo.add(frase);
        corpo.add(Listar);
        corpo.add(listaArquivo);
        corpo.add(Voltar);
        }

        painel.add(cabecalho);
        painel.add(estatistica);
        painel.add(corpo);

        tela.add(painel);
        tela.setSize(400, 600);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }


}
