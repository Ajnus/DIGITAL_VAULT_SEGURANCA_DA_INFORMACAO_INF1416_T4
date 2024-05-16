package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TelaSaida {

    private static JFrame tela;

    private static JPanel cabecalho;
    private static JPanel estatistica;
    private static JPanel corpo;

    public TelaSaida(){

        tela = new JFrame("Saida");

        JPanel painel = new JPanel(new BoxLayout(tela, BoxLayout.Y_AXIS));

        cabecalho = new JPanel(new BoxLayout(painel, BoxLayout.Y_AXIS));
        estatistica = new JPanel(new BoxLayout(painel, BoxLayout.Y_AXIS));
        corpo = new JPanel(new BoxLayout(painel, BoxLayout.Y_AXIS));

        {
            //adicionar elementos do cabecalho
        }

        {
            //adiciona elemento <<total de acesso do  usuário>> da estatistica
        }

        {
        JLabel titulo = new JLabel("Saída do sistema:");
        JLabel mensagem = new JLabel("Pressione o botão Encerrar Sessão ou o botão\r\nEncerrar Sistema para confirmar.");
        JButton EncerrarSessao = new JButton("Encerrar Sessão");
        JButton EncerrarSistema = new JButton("Encerrar Sistema");
        JButton Voltar = new JButton("Voltar");
        JPanel linha1 = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));

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

        tela.setSize(200, 300);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }
}
