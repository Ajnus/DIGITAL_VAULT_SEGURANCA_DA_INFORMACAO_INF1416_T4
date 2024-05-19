package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

public class TelaSaida {

    private static final TelaSaida janela = new TelaSaida();
    private static JFrame tela;


    public static TelaSaida getJanela(){return janela;}
    public JFrame getTela(){return tela;}

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
        //JPanel usuario
        //Jpanel Grupo
        //JPanel email
        }

        {
        //adiciona elemento <<total de acesso do  usuário>> da estatistica
        //JPanel NumAcesso
        }

        {
        JLabel titulo = new JLabel("Saída do sistema:");
        JLabel mensagem = new JLabel("Pressione o botão Encerrar Sessão ou o botão\r\nEncerrar Sistema para confirmar.");

        JButton EncerrarSessao = new JButton("Encerrar Sessão");

        JButton EncerrarSistema = new JButton("Encerrar Sistema");

        JButton Voltar = new JButton("Voltar");

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
