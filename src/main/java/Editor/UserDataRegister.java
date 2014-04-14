package Editor;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import NewInterface.ProjectMainView;
import UserRegistration.PostReqest;
import UserRegistration.UserDTO;

public class UserDataRegister extends JFrame {
	private JPanel contentPane;
	private PostReqest postReqest;
	private JButton btnCommit;
	private JTextField loginTxt;
	private JTextField passTxt;
	private final JLabel lblUserLogin = new JLabel("User login");
	private static ProjectMainView projectMainView; 
	
	private static volatile UserDataRegister userDataRegister = null;

	private UserDataRegister(final ProjectMainView projectMainView) {

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setBounds(100, 100, 290, 170);
		Toolkit toolkt = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkt.getScreenSize();
		this.setLocation(screenSize.width / 4, screenSize.height / 4);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		btnCommit = new JButton("commit");
		btnCommit.setBounds(79, 96, 117, 25);
		contentPane.add(btnCommit);

		loginTxt = new JTextField();
		loginTxt.setBounds(150, 6, 114, 19);
		contentPane.add(loginTxt);
		loginTxt.setColumns(10);

		passTxt = new JTextField();
		passTxt.setBounds(150, 36, 114, 19);
		contentPane.add(passTxt);
		passTxt.setColumns(10);

		JLabel passLbl = new JLabel("Password Label");
		lblUserLogin.setBounds(12, 0, 125, 33);
		contentPane.add(lblUserLogin);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(12, 39, 46, 14);
		contentPane.add(lblPassword);
		this.setVisible(true);

		btnCommit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					UserDTO user = new UserDTO();
					user.setLogin(loginTxt.getText());
					user.setPassword((passTxt.getText()));

					postReqest = new PostReqest(user);
					int status = postReqest.sendData();
					loginTxt.setText("");
					passTxt.setText("");
					if (status == 201) {
						new JOptionPane().showMessageDialog(null, "Success !");
						projectMainView.updateTable();
					} else {
						new JOptionPane().showMessageDialog(null,
								"Account could not been create !");
					}
				} catch (Exception ex) {
					new JOptionPane().showMessageDialog(null,
							"Account could not been create !");
					ex.printStackTrace();
				}
			}
		});
	}

	public static UserDataRegister getInstance(ProjectMainView projectMainView) {
		UserDataRegister.projectMainView = projectMainView; // to tez powinno byc zrobione przez Spring

		if (userDataRegister == null || !userDataRegister.isDisplayable()) {
			userDataRegister = new UserDataRegister(projectMainView);
			return userDataRegister;
		} else {
			return null;
		}
	}
}
