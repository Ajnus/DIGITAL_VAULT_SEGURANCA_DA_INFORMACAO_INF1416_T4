// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

package view;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ExecutionException;

import controler.SystemControler;
import storage.Grupos;

import javax.swing.JComboBox;

public class TelaRegistro {
  private static TelaRegistro janela;
  private static JFrame tela;

  private static JTextField[] listaRespostas;
  private static JComboBox<String> grupoComboBox;

  private static JLabel usuarioNome;
  private static JLabel grupoNome;
  private static JLabel email;
  private static JLabel numUsuarios;

  public void set(boolean firstTime) {
    if (firstTime) {
      // grupoComboBox.setSelectedItem(grupo);
    } else {
      // grupoComboBox.setSelectedItem(grupo);
    }
    grupoComboBox.setEnabled(!firstTime);
  }

  public void setVisibility(boolean onOff) {
    tela.setVisible(onOff);
  }

  public static TelaRegistro getJanela() {
    if (janela == null) {
      janela = new TelaRegistro();
    }
    return janela;
  }

  public JFrame getTela() {
    if (tela == null) {
      janela = new TelaRegistro();
    }
    return tela;
  }

  public void setUsuario(String nome) {
    usuarioNome.setText("Usuario: " + nome);
  }

  public void setGrupo(String nome) {
    grupoNome.setText("Grupo: " + nome);
  }

  public void setEmail(String nome) {
    email.setText("email: " + nome);
  }

  public void setAcessos(int qtd) {
    numUsuarios.setText("Numero de acessos: " + qtd);
  }

  private TelaRegistro() {

    String listaNaoPermitida = "0123456789876543210";

    tela = new JFrame("Registro");

    JPanel painel = new JPanel();
    painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

    JPanel cabecalho = new JPanel();
    cabecalho.setLayout(new BoxLayout(cabecalho, BoxLayout.Y_AXIS));
    JPanel estatistica = new JPanel();
    estatistica.setLayout(new BoxLayout(estatistica, BoxLayout.Y_AXIS));
    JPanel corpo = new JPanel();
    corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));

    listaRespostas = new JTextField[5];

    {
      // adicionar elementos do cabecalho e estatistica
      usuarioNome = new JLabel("Usuario: ");
      grupoNome = new JLabel("Grupo: ");
      email = new JLabel("Email: ");
      cabecalho.add(usuarioNome);
      cabecalho.add(grupoNome);
      cabecalho.add(email);

      // adiciona componente <<Total de usuários no banco>> do estatistica
      numUsuarios = new JLabel("Usuarios no sistema: ");
      estatistica.add(numUsuarios);
    }

    {
      JPanel emailComponent = new JPanel();
      emailComponent.setLayout(new BoxLayout(emailComponent, BoxLayout.X_AXIS));
      JLabel email = new JLabel("email:");
      JTextField emailCampo = new JTextField(256);

      listaRespostas[0] = emailCampo;
      emailComponent.add(email);
      emailComponent.add(emailCampo);
      corpo.add(emailComponent);
    }

    {
      JPanel certificadoComponent = new JPanel();
      certificadoComponent.setLayout(new BoxLayout(certificadoComponent, BoxLayout.X_AXIS));
      JLabel certificado = new JLabel("certificado:");
      JTextField certificadoCampo = new JTextField(256);

      listaRespostas[1] = certificadoCampo;
      certificadoComponent.add(certificado);
      certificadoComponent.add(certificadoCampo);
      corpo.add(certificadoComponent);
    }

    {
      JPanel chaveComponent = new JPanel();
      chaveComponent.setLayout(new BoxLayout(chaveComponent, BoxLayout.X_AXIS));
      JLabel arquivoChave = new JLabel("Chave privada");
      JTextField chaveCampo = new JTextField(256);

      listaRespostas[2] = chaveCampo;
      chaveComponent.add(arquivoChave);
      chaveComponent.add(chaveCampo);
      corpo.add(chaveComponent);
    }

    {
      JPanel fraseComponent = new JPanel();
      fraseComponent.setLayout(new BoxLayout(fraseComponent, BoxLayout.X_AXIS));
      JLabel fraseSecreta = new JLabel("Frase Secreta:");
      JTextField fraseCampo = new JTextField(256);

      listaRespostas[3] = fraseCampo;
      fraseComponent.add(fraseSecreta);
      fraseComponent.add(fraseCampo);
      corpo.add(fraseComponent);
    }

    {
      JLabel grupo = new JLabel("Grupo:");
      grupoComboBox = new JComboBox<String>();
      JPanel grupoComponent = new JPanel();
      grupoComponent.setLayout(new BoxLayout(grupoComponent, BoxLayout.X_AXIS));

      grupoComponent.add(grupo);
      grupoComponent.add(grupoComboBox);
      corpo.add(grupoComponent);
    }

    {
      JLabel senha = new JLabel("Senha:");
      JTextField senhaCampo = new JTextField(10);
      JPanel senhaComponent = new JPanel();
      senhaComponent.setLayout(new BoxLayout(senhaComponent, BoxLayout.X_AXIS));

      listaRespostas[4] = senhaCampo;
      senhaComponent.add(senha);
      senhaComponent.add(senhaCampo);
      corpo.add(senhaComponent);
    }

    {
      JButton ok = new JButton("ok");
      ok.addActionListener(ActionEvent -> {

      });

      JButton volta = new JButton("voltar");
      volta.addActionListener(ActionEvent -> {

      });

      JPanel bottomButtonsComponent = new JPanel();
      bottomButtonsComponent.setLayout(new BoxLayout(bottomButtonsComponent, BoxLayout.X_AXIS));

      bottomButtonsComponent.add(ok);
      bottomButtonsComponent.add(volta);
      corpo.add(bottomButtonsComponent);
    }

    painel.add(cabecalho);
    painel.add(estatistica);
    painel.add(corpo);

    tela.getContentPane().add(painel);
    tela.setSize(400, 600);
    tela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    tela.setVisible(true);
  }

  private void carregarGrupos() {
    SwingWorker<List<String>, Void> worker = new SwingWorker<List<String>, Void>() {
      @Override
      protected List<String> doInBackground() throws Exception {
        return Grupos.GetGrupos();
      }

      @Override
      protected void done() {
        try {
          List<String> grupos = get();
          for (String grupo : grupos) { 
            grupoComboBox.addItem(grupo);
          }
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace   ();
          JOptionPane.showMessageDialog(tela, "Erro ao carregar grupos", "Erro", JOptionPane.ERROR_MESSAGE);
        }
      }
    };
    worker.execute();
  }

}
