
package br.com.moreira.jovencio.GerenciamentoAcessos.views;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author marlan/eriani
 */
public class ManterUsuariosView extends javax.swing.JInternalFrame {

	/** Creates new form ManterUsuariosView */
	public ManterUsuariosView() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblListaUsuarios = new javax.swing.JTable();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblPossuiNotificacaoNaoLida = new javax.swing.JLabel();
        cboxPossuiNotificacaoNaoLida = new javax.swing.JComboBox<>();
        btnFiltrar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Manter Usuários");
        setMinimumSize(new java.awt.Dimension(1200, 350));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(1200, 350));

        jScrollPane1.setMinimumSize(new java.awt.Dimension(1014, 160));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1014, 160));
        jScrollPane1.setViewportView(null);

        tblListaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nome", "Data de Cadastro", "Notificações Enviadas", "Notificações Lidas", "Autorizado?", "Ações"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblListaUsuarios.setMinimumSize(new java.awt.Dimension(1014, 90));
        tblListaUsuarios.setOpaque(false);
        tblListaUsuarios.setPreferredSize(new java.awt.Dimension(1014, 1080));
        tblListaUsuarios.setRequestFocusEnabled(false);
        tblListaUsuarios.setRowHeight(40);
        jScrollPane1.setViewportView(tblListaUsuarios);
        if (tblListaUsuarios.getColumnModel().getColumnCount() > 0) {
            tblListaUsuarios.getColumnModel().getColumn(0).setResizable(false);
            tblListaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(6);
            tblListaUsuarios.getColumnModel().getColumn(6).setMinWidth(366);
        }

        lblNome.setText("Nome");

        txtNome.setPreferredSize(new java.awt.Dimension(360, 40));

        lblPossuiNotificacaoNaoLida.setText("Possui Notificação não lida?");

        cboxPossuiNotificacaoNaoLida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Sim", "Não" }));
        cboxPossuiNotificacaoNaoLida.setPreferredSize(new java.awt.Dimension(360, 40));

        btnFiltrar.setText("Filtrar");
        btnFiltrar.setPreferredSize(new java.awt.Dimension(120, 40));

        btnLimpar.setText("Limpar");
        btnLimpar.setPreferredSize(new java.awt.Dimension(120, 40));

        btnNovo.setText("Novo");
        btnNovo.setPreferredSize(new java.awt.Dimension(120, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNome)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(lblPossuiNotificacaoNaoLida)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(cboxPossuiNotificacaoNaoLida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 220, Short.MAX_VALUE)
                                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPossuiNotificacaoNaoLida)
                    .addComponent(lblNome))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboxPossuiNotificacaoNaoLida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFiltrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JComboBox<String> cboxPossuiNotificacaoNaoLida;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPossuiNotificacaoNaoLida;
    private javax.swing.JTable tblListaUsuarios;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables

	public JButton getBtnFiltrar() {
		return btnFiltrar;
	}

	public JButton getBtnLimpar() {
		return btnLimpar;
	}

	public JButton getBtnNovo() {
		return btnNovo;
	}

	public JComboBox<String> getCboxPossuiNotificacaoNaoLida() {
		return cboxPossuiNotificacaoNaoLida;
	}

	public JTable getTblListaUsuarios() {
		return tblListaUsuarios;
	}

	public JTextField getTxtNome() {
		return txtNome;
	}

}
