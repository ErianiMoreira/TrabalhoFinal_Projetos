
package br.com.moreira.jovencio.GerenciamentoAcessos.views;

import javax.swing.JButton;
import javax.swing.JPasswordField;

/**
 *
 * @author marlan/eriani
 */
public class AlterarSenhaView extends javax.swing.JInternalFrame {

	/** Creates new form AlterarSenhaView */
	public AlterarSenhaView() {
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

        lblNovaSenha = new javax.swing.JLabel();
        pswNovaSenha = new javax.swing.JPasswordField();
        lblConfirmarNovaSenha = new javax.swing.JLabel();
        pswConfirmarNovaSenha = new javax.swing.JPasswordField();
        btnAlterarSenha = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        lblNovaSenha.setText("Nova Senha");

        pswNovaSenha.setText("jPasswordField1");
        pswNovaSenha.setPreferredSize(new java.awt.Dimension(360, 40));

        lblConfirmarNovaSenha.setText("Confirmar Nova Senha");

        pswConfirmarNovaSenha.setText("jPasswordField1");
        pswConfirmarNovaSenha.setPreferredSize(new java.awt.Dimension(360, 40));

        btnAlterarSenha.setText("Alterar");
        btnAlterarSenha.setPreferredSize(new java.awt.Dimension(120, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblNovaSenha))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(pswNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblConfirmarNovaSenha))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(pswConfirmarNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAlterarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lblNovaSenha)
                .addGap(6, 6, 6)
                .addComponent(pswNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblConfirmarNovaSenha)
                .addGap(6, 6, 6)
                .addComponent(pswConfirmarNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAlterarSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterarSenha;
    private javax.swing.JLabel lblConfirmarNovaSenha;
    private javax.swing.JLabel lblNovaSenha;
    private javax.swing.JPasswordField pswConfirmarNovaSenha;
    private javax.swing.JPasswordField pswNovaSenha;
    // End of variables declaration//GEN-END:variables

	public JButton getBtnAlterarSenha() {
		return btnAlterarSenha;
	}

	public JPasswordField getPswNovaSenha() {
		return pswNovaSenha;
	}

	public JPasswordField getPswConfirmarNovaSenha() {
		return pswConfirmarNovaSenha;
	}

}
