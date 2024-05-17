package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;

public class TelaListarAcesso {
    private JFrame tela;
    private static int tentativas;

    public TelaListarAcesso(){
        tela = new JFrame();
        JPanel painel = new JPanel(new BoxLayout(tela,BoxLayout.Y_AXIS));
        JPanel cabecalho = new JPanel(new BoxLayout(painel,BoxLayout.Y_AXIS));
        JPanel estatistica = new JPanel(new BoxLayout(painel,BoxLayout.Y_AXIS));
        JPanel corpo = new JPanel(new BoxLayout(painel,BoxLayout.Y_AXIS));
        JTable listaArquivo;

        {
        //adiciona componentes do cabeçalho
        //JPanel usuario
        //Jpanel Grupo
        //JPanel email
        }

        {
        //adiciona componente <<Total de acessos do usuário>> do estatistica
        //JPanel numeroAcesso
        }

        {
        JLabel pastaEndereco = new JLabel("caminho da pasta:");
        JTextField campoPasta = new JTextField(255);
        JPanel pasta = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
        pasta.add(pastaEndereco); pasta.add(campoPasta);

        JLabel FraseSecreta = new JLabel("FraseSecreta:");
        JPasswordField CampoFrase = new JPasswordField(255);
        JPanel frase = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
        frase.add(FraseSecreta); frase.add(CampoFrase);

        JButton Listar = new JButton("Listar");

        listaArquivo = new JTable();

        JButton Voltar = new JButton("Voltar");

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
        tela.setSize(200, 300);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setVisible(true);
    }
}
