package Model;

import dao.ClienteDao;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class TelaPrincipalController extends Application implements Initializable {

    private ClienteDao cliDao = new ClienteDao();
    static Stage stage1 = new Stage();

    public static Stage getStage() { // Entrega a janela
        return stage1;
    }

    public static void setStage(Stage stage) {
        TelaPrincipalController.stage1 = stage;
    }

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private AnchorPane CadastroCliente;

    @FXML
    private Button btncadastro;

    @FXML
    private TableColumn<Cliente, String> cpf_col;

    @FXML
    private TextField inputCPFCli;

    @FXML
    private TextField inputNomeCli;

    @FXML
    private TextField inputPassaporteCli;

    @FXML
    private TextField inputRGCli;

    @FXML
    private TableColumn<Cliente, String> nome_col;

    @FXML
    private TableColumn<Cliente, String> passaporte_col;

    @FXML
    private TableColumn<Cliente, String> rg_col;

    @FXML
    private TableView<Cliente> tb_cliente;

    @FXML
    private TableColumn<Cliente, Void> editar_col;

    @FXML
    private TableColumn<Cliente, Void> excluir_col;

    @FXML
    private TextField inputPesquisa;

    @FXML
    private TabPane menu;

    @FXML
    void cadastro(ActionEvent e) throws Exception {

        /*
         * Faço uma verificação para saber se os campos são vazios
         */
        if (!inputNomeCli.getText().isEmpty()
                && !inputCPFCli.getText().isEmpty()
                && !inputPassaporteCli.getText().isEmpty()
                && !inputRGCli.getText().isEmpty()) {

            Cliente cli = new Cliente();

            // Atribuo os valores dos inputs aos atributos do Cliente
            cli.setNome(inputNomeCli.getText());
            cli.setCPF(inputCPFCli.getText());
            cli.setPassaporte(inputPassaporteCli.getText());
            cli.setRG(inputRGCli.getText());

            ClienteDao conexao = new ClienteDao();

            // Verifico se a inserção foi verdadeira
            if (conexao.inserir(cli) == true) {
                JOptionPane.showMessageDialog(null, "Dados inseridos com sucesso!");

                // Código para dar um reload na tela
                // Especifica o local em que está localizado a minha interface gráfica
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TelaPrincipal.fxml"));
                // Carrega a minha interface
                Parent root = (Parent) loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow(); // Dá o reload
                stage.setScene(scene);
                stage.show();

            } else {
                JOptionPane.showMessageDialog(null, "ERROR", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "INSIRA OS DADOS CORRETAMENTE!", "Erro", JOptionPane.WARNING_MESSAGE);
        }
    }

    public Cliente C;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        inicializar_colunas();

        tb_cliente.setOnMouseClicked((MouseEvent) -> {
            this.C = tb_cliente.getSelectionModel().getSelectedItem();
            if (!tb_cliente.getItems().isEmpty()) {
                // Há itens na tabela, adicionar botões
                for (TableColumn column : tb_cliente.getColumns()) {
                    if (column.getText().equals("Editar")) {
                        // Coluna de ações já existe, não adicionar novamente
                        return;
                    }
                }
                TableColumn actionColumn = new TableColumn("Editar");
                actionColumn.setCellValueFactory(new PropertyValueFactory<>("dummy"));
                Callback<TableColumn<TableModel, String>, TableCell<TableModel, String>> cellFactory = new Callback<TableColumn<TableModel, String>, TableCell<TableModel, String>>() {
                    @Override
                    public TableCell<TableModel, String> call(final TableColumn<TableModel, String> param) {
                        final TableCell<TableModel, String> cell = new TableCell<TableModel, String>() {
                            final Button button = new Button("Editar");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction(event -> {
                                        try {
                                            String nomePadrao = C.getNome();
                                            TextInputDialog dialog = new TextInputDialog(nomePadrao);
                                            dialog.setTitle("Atualização de dados");
                                            dialog.setHeaderText("Digite as informações necessárias:");
                                            dialog.setContentText("Nome");

                                            // Exibe o diálogo e aguarda a entrada do usuário
                                            Optional<String> result = dialog.showAndWait();

                                            // Verifica se o usuário digitou algum valor e armazena em uma variável
                                            if (result.isPresent()) {
                                                Cliente c = new Cliente();
                                                c.setNome(result.get());
                                                c.setCPF(C.getCPF());
                                                System.out.println("Nome digitado: " + c.getNome());
                                                cliDao.atualizar(c);
                                                preencherTabela();
                                            }

                                        } catch (Exception ex) {
                                            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE,
                                                    null, ex);
                                        }
                                    });
                                    setGraphic(button);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
                actionColumn.setCellFactory(cellFactory);
                tb_cliente.getColumns().add(actionColumn);
            }
            if (!tb_cliente.getItems().isEmpty()) {
                // Há itens na tabela, adicionar botões
                for (TableColumn column : tb_cliente.getColumns()) {
                    if (column.getText().equals("Excluir")) {
                        // Coluna de ações já existe, não adicionar novamente
                        return;
                    }
                }
                
                TableColumn actionColumn = new TableColumn("Excluir");
                actionColumn.setCellValueFactory(new PropertyValueFactory<>("dummy"));
                Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>> cellFactory = new Callback<TableColumn<Cliente, Void>, TableCell<Cliente, Void>>() {
                    @Override
                    public TableCell<Cliente, Void> call(final TableColumn<Cliente, Void> param) {
                        final TableCell<Cliente, Void> cell = new TableCell<Cliente, Void>() {
                            final Button button = new Button("Excluir");

                            @Override
                            public void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    button.setOnAction(event -> {
                                        try {
                                            ClienteDao dao = new ClienteDao();
                                            int rowIndex = getIndex();
                                            Cliente cliente = tb_cliente.getItems().get(rowIndex);
                                            String cpf = cliente.getCPF();

                                            int resposta = JOptionPane.showConfirmDialog(null,
                                                    "Tem certeza que deseja excluir o cliente?",
                                                    "Confirmação de exclusão", JOptionPane.YES_NO_OPTION);

                                            if (resposta == JOptionPane.YES_OPTION) {
                                                if (dao.excluir(cpf)) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "Exclusão realizada com sucesso!");
                                                    preencherTabela();
                                                } else {
                                                    JOptionPane.showMessageDialog(null, "ERROR!");
                                                }
                                            } else if (resposta == JOptionPane.NO_OPTION) {
                                                // código para não excluir o cliente
                                            }
                                        } catch (SQLException ex) {
                                            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE,
                                                    null, ex);
                                        }
                                    });

                                    setGraphic(button);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
                actionColumn.setCellFactory(cellFactory);
                tb_cliente.getColumns().add(actionColumn);
            }
        });
        try {
            preencherTabela();
        } catch (SQLException ex) {
            Logger.getLogger(TelaPrincipalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/TelaPrincipal.fxml"));

        Scene scene = new Scene(root);
        stage.setTitle("AeroPoint");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void inicializar_colunas() {
        nome_col.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        cpf_col.setCellValueFactory(new PropertyValueFactory<>("CPF"));
        rg_col.setCellValueFactory(new PropertyValueFactory<>("RG"));
        passaporte_col.setCellValueFactory(new PropertyValueFactory<>("Passaporte"));
    }

    public void atualizar_tabela() throws SQLException {
        ClienteDao dao = new ClienteDao();
        tb_cliente.setItems(FXCollections.observableArrayList(dao.listar_clientes()));
    }

    List<Cliente> lista_cli;

    public void preencherTabela() throws SQLException {
        ClienteDao cliDao = new ClienteDao();

        lista_cli = cliDao.listar_clientes();
        tb_cliente.setItems(FXCollections.observableArrayList(lista_cli));
        lista_cli = cliDao.listar_clientes();
        
        // Filtro
        ObservableList<Cliente> clientes = FXCollections.observableArrayList(lista_cli);
        FilteredList<Cliente> filteredClientes = new FilteredList<>(clientes);

        inputPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredClientes.setPredicate(cliente -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (cliente.getCPF().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filtro correspondente
                } else {
                    return false; // Filtro não correspondente
                }
            });
        });

        tb_cliente.setItems(filteredClientes);
    }
}
