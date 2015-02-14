package gui;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton cancelButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoginWindow dialog = new LoginWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LoginWindow() {
		setBounds(100, 100, 369, 212);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		{
			usernameField = new JTextField();
			usernameField.setColumns(10);
		}
		{
			passwordField = new JPasswordField();
		}
		
		JLabel lblUsername = new JLabel("Username");
		
		JLabel lblPassword = new JLabel("Password");
		JLabel lblWelcomeToBudget = new JLabel("Welcome to Budget Manager v0.1");
		lblWelcomeToBudget.setFont(new Font("Arial", Font.BOLD, 16));
		{
			loginButton = new JButton("Login");
			getRootPane().setDefaultButton(loginButton);
		}
		{
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
		}
		JLabel lblPleaseLoginTo = new JLabel("Please login to continue");
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblWelcomeToBudget)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblUsername)
										.addComponent(lblPassword))
									.addGap(18)
									.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(usernameField, GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
										.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPanel.createSequentialGroup()
											.addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
											.addGap(79)
											.addComponent(cancelButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
							.addContainerGap(335, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblPleaseLoginTo)
							.addContainerGap(287, Short.MAX_VALUE))))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblWelcomeToBudget)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblPleaseLoginTo)
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUsername)
						.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(loginButton)
						.addComponent(cancelButton))
					.addGap(73))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
