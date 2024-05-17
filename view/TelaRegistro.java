package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

// -------------------------
// Jam Ajna Soares - 2211689
// Olavo Lucas     - 1811181
// -------------------------

public class TelaRegistro {
  private static String listaNaoPermitida;

  private static JFrame tela;

  protected TelaRegistro(boolean firstTime){

    listaNaoPermitida ="0123456789876543210";

    tela = new JFrame("Registro");

    JPanel painel = new JPanel(new BoxLayout(tela,BoxLayout.Y_AXIS));

    JPanel cabecalho = new JPanel(new BoxLayout(painel, BoxLayout.Y_AXIS));
    JPanel estatistica = new JPanel(new BoxLayout(painel, BoxLayout.Y_AXIS));
    JPanel corpo = new JPanel(new BoxLayout(painel, BoxLayout.Y_AXIS));

    {
    JPanel emailComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    JLabel email = new JLabel("email:");
    JTextField emailCampo = new JTextField(256);
    emailComponent.add(email);
    emailComponent.add(emailCampo);
    corpo.add(emailComponent);
    }

    {
    JPanel certificadoComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    JLabel certificado = new JLabel("certificado:");
    JTextField certificadoCampo = new JTextField(256);
    certificadoComponent.add(certificado);
    certificadoComponent.add(certificadoCampo);
    corpo.add(certificadoComponent);
    }

    {
    JPanel chaveComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    JLabel arquivoChave = new JLabel("Chave privada");
    JTextField chaveCampo = new JTextField(256);
    chaveComponent.add(arquivoChave);
    chaveComponent.add(chaveCampo);
    corpo.add(chaveComponent);
    }

    {
    JPanel fraseComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    JLabel fraseSecreta = new JLabel("Frase Secreta:");
    JTextField fraseCampo = new JTextField(256);
    fraseComponent.add(fraseSecreta);
    fraseComponent.add(fraseCampo);
    corpo.add(fraseComponent);
    }

    {
    JLabel grupo = new JLabel("Grupo:");
    JComboBox<String> grupoComboBox = new JComboBox<String>();
    JPanel grupoComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    grupoComponent.add(grupo);
    grupoComponent.add(grupoComboBox);
    corpo.add(grupoComponent);
    }

    {
    JLabel senha = new JLabel("Senha:");
    JTextField senhaCampo = new JTextField(10);
    JPanel senhaComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    senhaComponent.add(senha);
    senhaComponent.add(senhaCampo);
    corpo.add(senhaComponent);
    }

    {
    JButton ok = new JButton("ok");
    JButton volta = new JButton("voltar");
    JPanel bottomButtonsComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    bottomButtonsComponent.add(ok);
    bottomButtonsComponent.add(volta);
    corpo.add(bottomButtonsComponent);
    }

    {
      //adiciona componentes do cabeçalho
      //JPanel usuario
      //Jpanel Grupo
      //JPanel email
    }

    {
      //adiciona componente <<Total de usuários no banco>> do estatistica
      //JPanel numeroAcesso
    }

    painel.add(cabecalho);
    painel.add(estatistica);
    painel.add(corpo);

    tela.add(painel);
    tela.setSize(200, 300);
    tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    tela.setVisible(true);
  }

  protected TelaRegistro(){this(false);}

}

