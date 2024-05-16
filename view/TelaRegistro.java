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

  private static JPanel cabecalho;
  private static JPanel estatistica;
  private static JPanel corpo;

  private static JPanel emailComponent;
  private static JPanel certificadoComponent;
  private static JPanel chaveComponent;
  private static JPanel fraseComponent;
  private static JPanel grupoComponent;
  private static JPanel senhaComponent;
  private static JPanel bottomButtonsComponent;

  private TelaRegistro(){
    listaNaoPermitida ="0123456789876543210";

    tela = new JFrame("Registro");

    JPanel painel = new JPanel(new BoxLayout(tela,BoxLayout.Y_AXIS));

    cabecalho = new JPanel(new BoxLayout(painel, BoxLayout.Y_AXIS));
    estatistica = new JPanel(new BoxLayout(painel, BoxLayout.Y_AXIS));
    corpo = new JPanel(new BoxLayout(painel, BoxLayout.Y_AXIS));

    {
    JLabel email = new JLabel("email:");
    JTextField emailCampo = new JTextField(256);
    emailComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    emailComponent.add(email);
    emailComponent.add(emailCampo);
    }

    {
    JLabel certificado = new JLabel("certificado:");
    JTextField certificadoCampo = new JTextField(256);
    certificadoComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    certificadoComponent.add(certificado);
    certificadoComponent.add(certificadoCampo);
    }

    {
    JLabel arquivoChave = new JLabel("Chave privada");
    JTextField chaveCampo = new JTextField(256);
    chaveComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    chaveComponent.add(arquivoChave);
    chaveComponent.add(chaveCampo);
    }

    {
    JLabel fraseSecreta = new JLabel("Frase Secreta:");
    JTextField fraseCampo = new JTextField(256);
    fraseComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    fraseComponent.add(fraseSecreta);
    fraseComponent.add(fraseCampo);
    }

    {
    JLabel grupo = new JLabel("Grupo:");
    JComboBox<String> grupoComboBox = new JComboBox<String>();
    grupoComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    grupoComponent.add(grupo);
    grupoComponent.add(grupoComboBox);
    }

    {
    JLabel senha = new JLabel("Senha: ");
    JTextField senhaCampo = new JTextField(10);
    senhaComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    senhaComponent.add(senha);
    senhaComponent.add(senhaCampo);
    }

    {
    JButton ok = new JButton("ok");
    JButton volta = new JButton("voltar");
    bottomButtonsComponent = new JPanel(new BoxLayout(corpo, BoxLayout.X_AXIS));
    bottomButtonsComponent.add(ok);
    bottomButtonsComponent.add(volta);
    }

    {
      //adiciona componentes do cabeçalho
    }

    {
      //adiciona componente <<Total de usuários no banco>> do estatistica
    }

    corpo.add(emailComponent);
    corpo.add(certificadoComponent);
    corpo.add(chaveComponent);
    corpo.add(fraseComponent);
    corpo.add(grupoComponent);
    corpo.add(senhaComponent);
    corpo.add(bottomButtonsComponent);

    
    painel.add(cabecalho);
    painel.add(estatistica);
    painel.add(corpo);

    tela.add(painel);
    tela.setSize(200, 300);
    tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    tela.setVisible(true);
  }

}

