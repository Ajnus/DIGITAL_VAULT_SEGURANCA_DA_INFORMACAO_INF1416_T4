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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controler.SystemControler;


public class TelaSaida {

    private static final TelaSaida janela = new TelaSaida();
    private static JFrame tela;

    private static JLabel usuarioNome;
    private static JLabel grupoNome;
    private static JLabel email;
    private static JLabel numAcesso;


    public static TelaSaida getJanela(){return janela;}
    public JFrame getTela(){return tela;}
    public void setUsuario(String nome){usuarioNome.setText("Usuario: " + nome);}
    public void setGrupo(String nome){grupoNome.setText("Grupo: " + nome);}
    public void setEmail(String nome){email.setText("email: "+ nome);}
    public void setAcessos(int qtd){numAcesso.setText("Numero de acessos: "+ qtd);}

    private TelaSaida(){

        tela = new JFrame("Saida");

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
        JLabel titulo = new JLabel("Saída do sistema:");
        JLabel mensagem = new JLabel("Pressione o botão Encerrar Sessão ou o botão\r\nEncerrar Sistema para confirmar.");

        JButton EncerrarSessao = new JButton("Encerrar Sessão");
        EncerrarSessao.addActionListener(ActionEvent -> {

        });

        JButton EncerrarSistema = new JButton("Encerrar Sistema");
        EncerrarSistema.addActionListener(ActionEvent -> {

        });

        JButton Voltar = new JButton("Voltar");
        Voltar.addActionListener(ActionEvent -> {
            tela.setVisible(false);
            SystemControler.Switch("TelaPrincipal");
        });

        JPanel linha1 = new JPanel();
        linha1.setLayout(new BoxLayout(linha1, BoxLayout.X_AXIS));
        linha1.add(EncerrarSessao);
        linha1.add(EncerrarSistema);

        corpo.add(titulo);
        corpo.add(mensagem);
        corpo.add(linha1);
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
