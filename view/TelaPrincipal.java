package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TelaPrincipal {
    private static JFrame tela;

    private static JPanel cabecalho;
    private static JPanel estatistica;
    private static JPanel corpo;

    public TelaPrincipal(){
        tela = new JFrame("Tela Principal");
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
        corpo = new JPanel(new BoxLayout(tela, BoxLayout.Y_AXIS));
        JButton cadastro = new JButton("cadastrar usuário");
        JButton consultar = new JButton("consultar arquivos");
        JButton sair = new JButton("sair");
        corpo.add(cadastro);
        corpo.add(consultar);
        corpo.add(sair);
        }

        tela.add(cabecalho);
        tela.add(estatistica);
        tela.add(corpo);
    }
}
