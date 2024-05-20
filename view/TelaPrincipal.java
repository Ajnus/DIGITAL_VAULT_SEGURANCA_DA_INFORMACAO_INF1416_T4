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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import controler.SystemControler;

public class TelaPrincipal {
    private static TelaPrincipal janela;
    private static JFrame tela;

    private static JLabel usuarioNome;
    private static JLabel grupoNome;
    private static JLabel email;
    private static JLabel numAcesso;

    public static TelaPrincipal getJanela() {
        if (janela == null) {
            janela = new TelaPrincipal();
        }
        return TelaPrincipal.janela;
    }

    public JFrame getTela() {
        if (tela == null) {
            janela = new TelaPrincipal();
        }
        return TelaPrincipal.tela;
    }

    public void setVisibility(boolean onOff) {
        tela.setVisible(onOff);
    }

    public void setUsuario(String nome) {
        usuarioNome.setText("Usuario: " + nome);
    }

    public void setGrupo(String nome) {
        grupoNome.setText("Grupo: " + nome);
    }

    public void setEmail(String nome) {
        email.setText("Email: " + nome);
    }

    public void setAcessos(int qtd) {
        numAcesso.setText("Numero de acessos: " + qtd);
    }

    private TelaPrincipal() {
        tela = new JFrame("Tela Principal");
        /*
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        JPanel cabecalho = new JPanel();
        cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));
        JPanel estatistica = new JPanel();
        estatistica.setLayout(new BoxLayout(estatistica, BoxLayout.Y_AXIS));
        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS)); 
        */
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        {
        //adicionar elementos do cabecalho
        usuarioNome = new JLabel("Usuario: ");
        grupoNome = new JLabel("Grupo: ");
        email = new JLabel("Email: ");
        /*
        cabecalho.add(usuarioNome);
        cabecalho.add(grupoNome);
        cabecalho.add(email);        
        */
        
        //adiciona elemento <<total de acesso do  usuário>> da estatistica
        numAcesso = new JLabel("Numero de acessos: ");

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(usuarioNome, gbc);

        gbc.gridy++;
        painel.add(grupoNome, gbc);

        gbc.gridy++;
        painel.add(email, gbc);

        gbc.gridy++;
        painel.add(numAcesso, gbc);

        //estatistica.add(numAcesso);
        }

        {
        JButton cadastro = new JButton("Cadastrar Usuário");
        cadastro.addActionListener(ActionEvent -> {
            tela.setVisible(false);
            SystemControler.Switch("TelaCadastro");
        });

        JButton consultar = new JButton("Consultar Arquivos");
        consultar.addActionListener(ActionEvent -> {
            tela.setVisible(false);
            SystemControler.Switch("TelaListarAcesso");
        });

        JButton sair = new JButton("Sair");
        sair.addActionListener(ActionEvent -> {
            tela.setVisible(false);
            SystemControler.Switch("TelaSaida");
        });
        /*
        corpo.add(cadastro);
        corpo.add(consultar);
        corpo.add(sair);
        */

        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(cadastro, gbc);

        gbc.gridy++;
        painel.add(consultar, gbc);

        gbc.gridy++;
        painel.add(sair, gbc);
        }

        /*        
        painel.add(cabecalho);
        painel.add(estatistica);
        painel.add(corpo); 
        */

        tela.add(painel);
        tela.setSize(300, 400); // tela.setSize(200, 300);
        tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tela.setLocationRelativeTo(null); // Centraliza a janela na tela
        tela.setVisible(true);
    }
}
