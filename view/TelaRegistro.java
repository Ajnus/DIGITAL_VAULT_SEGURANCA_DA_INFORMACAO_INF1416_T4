package view;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;

// -------------------------
// Jam Ajna Soares - 2211689
// Olavo Lucas     - 1811181
// -------------------------

public class TelaRegistro {
  private static JFrame tela;

  private static JPanel cabecalho;
  private static JPanel estatistica;
  private static JPanel corpo;

  private static JLabel email;
  private static JLabel certificado;
  private static JLabel arquivoChave;
  private static JLabel FraseSecreta;
  private static JLabel Grupo;
  private static JLabel Senha;

  private static JButton ok;
  private static JButton volta;

  private static int tentativas;
  private static String listaNaoPermitida = "0123456789876543210";

  private TelaRegistro(){
    JFrame tela = new JFrame("Registro");
    JLabel label = new JLabel("Grupo");
    JButton volta = new JButton("voltar");
    JTextField caixaTexto = new JTextField(256);
    JTextField senha = new JTextField(10);

    tela.setSize(200, 300);
    tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    tela.setVisible(true);
  }

}

textField.addKeyListener(new KeyAdapter() {
  public void keyTyped(KeyEvent e) {
    char c = e.getKeyChar();
    if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
      e.consume();  // if it's not a number, ignore the event
    }
  };
}

