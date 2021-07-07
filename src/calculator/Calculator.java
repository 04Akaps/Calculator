package calculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame{
	// ȭ���� �����ϴ� �ڵ��̸�(������ �ؽ�Ʈ ������ �Է� �Ǵ� ���, ��ü�̴�)
	private JTextField inputSpace;
	private String num = "";
	// ��걸���� ���� ArrayList�� ���� ����ش�.
	private ArrayList<String> equation = new ArrayList<>();
	
	public Calculator() {
		setLayout(null);
		
		inputSpace = new JTextField(); // ��ü �̱⋚���� new �����ڸ� ����Ͽ� �ҷ��´�.
		inputSpace.setEditable(false);	// ��ư���θ� �Է��� �������⋚���� ���� ���� ���δ� �Ұ������� ����
		// ����, ������ġ, �۾�ü�� ����
		inputSpace.setBackground(Color.white);	
		inputSpace.setHorizontalAlignment(JTextField.RIGHT);	
		inputSpace.setFont(new Font("Arial", Font.BOLD,50));	
		// ��ġ�� ũ�⸦ �������ִ� �ڵ�(x:8, y:10, 270*70�� ũ��)
		inputSpace.setBounds(8,10,270,70);	
		
		//��ư���� ���� �г�
		JPanel buttonPanel = new JPanel();
		// ���� ���·� ��ġ���ִ� GridLayout�� ���(���� ĭ��, ���� ĭ��, �¿� ����, ���� ����)
		buttonPanel.setLayout(new GridLayout(4,4,10,10));
		// ��ġ�� ũ�⸦ ����
		buttonPanel.setBounds(8,90,270,235);
		
		String button_names[] = {"C", "%", "X", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0"};
		JButton buttons[] = new JButton[button_names.length];
		
		for (int i=0; i<button_names.length; i++) {
			buttons[i] = new JButton(button_names[i]);
			buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
			// ��ư�� ���� �� ���ڻ��� ����
			if(button_names[i] == "C") {
				buttons[i].setBackground(Color.red);
			}else if((i>=4 && i<=6) || (i>=8 && i<=10)||(i>=12 &&i<=14)) {
				buttons[i].setBackground(Color.black);
			}else {
				buttons[i].setBackground(Color.gray);
			}
			buttons[i].setForeground(Color.white);
			buttons[i].setBorderPainted(false); // ��ư�� �׵θ��� ����
			buttons[i].addActionListener(new PadActionListener()); // �̺�Ʈ�� ��ư�� �߰�
			buttonPanel.add(buttons[i]); //��ư���� ��ڿ� ����
		}
		
		add(inputSpace);// ������ Ʋ�� �ۼ��ѵ� �־��ش�.
		add(buttonPanel); // ������ ��ư���� �ۼ��ѵ� �־��ش�.
		
		// ������ Ʋ�� ����� �ڵ�
		setTitle("����");
		setVisible(true);
		setSize(300,380);
		setLocationRelativeTo(null);
		setResizable(false);
		// �Ʒ� �ڵ带 �Է��� �־�� â�� ������ �������� ���α׷��� ���� �ȴ�.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class PadActionListener implements ActionListener{
		// ActionListener : �̺�Ʈ�� �ִ� �������̽�
		@Override
		public void actionPerformed(ActionEvent e) {
			// � ��ư�� ���ȴ��� �ҷ��´�.
			String operation = e.getActionCommand();
			// ��ư�� �������� ó�����ִ� �ڵ�(C �� = �� �������� ����)
			if(operation.equals("C")) {
				inputSpace.setText("");
			}	else if(operation.equals("=")) {
				String result = Double.toString(calculate(inputSpace.getText()));
				inputSpace.setText(""+result);
				num = "";
			}	else {
				inputSpace.setText(inputSpace.getText() +e.getActionCommand()); // ȭ�鿡 ���
			}
				
		}
		
	}
	
	private void fullTextParsing(String inputText) {
		equation.clear();
		
		for(int i=0; i<inputText.length(); i++) {
			char ch = inputText.charAt(i);
			// ���� �̿ܿ��� ���ڸ� �߰����ְ� num�� �ʱ�ȭ
			// ���� �� ��쿡�� num�� �Է�
			if(ch == '-' || ch == '+' || ch == '%' || ch == 'X') {
				equation.add(num);
				num = "";
				equation.add(ch +"");
			}else {
				num = num +ch;
			}
		}
		equation.add(num);
	}
	
	public double calculate(String inputText) {
		fullTextParsing(inputText);
		
		double prev = 0;
		double current = 0;
		String mode = " ";
				
		for(String s : equation) {
			if(s.equals("+")){
				mode = "add";
			} else if(s.equals("X")) {
				mode = "mul";
			} else if(s.equals("%")) {
				mode = "div";
			} else if(s.equals("-")) {
				mode = "sub";
			} else {
				current = Double.parseDouble(s);
				if(mode == "add") {
					prev += current;
				} else if(mode =="sub") {
					prev -= current;
				} else if(mode =="mul") {
					prev *= current;
				} else if(mode =="div") {
					prev /= current;
				} else {
					prev = current;
				}
			}
		}
		return prev;
	}
	
	public static void main(String[] args) {
		new Calculator();
	}

}
