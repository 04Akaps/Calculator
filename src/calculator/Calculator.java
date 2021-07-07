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
	// 화면을 구성하는 코드이름(한줄의 텍스트 라인을 입력 또는 출력, 객체이다)
	private JTextField inputSpace;
	private String num = "";
	// 계산구현을 위해 ArrayList에 값을 담아준다.
	private ArrayList<String> equation = new ArrayList<>();
	
	public Calculator() {
		setLayout(null);
		
		inputSpace = new JTextField(); // 객체 이기떄문에 new 생성자를 사용하여 불러온다.
		inputSpace.setEditable(false);	// 버튼으로만 입력을 받을꺼기떄문에 편집 가능 여부는 불가능으로 설정
		// 배경색, 정렬위치, 글씨체를 설정
		inputSpace.setBackground(Color.white);	
		inputSpace.setHorizontalAlignment(JTextField.RIGHT);	
		inputSpace.setFont(new Font("Arial", Font.BOLD,50));	
		// 위치와 크기를 설정해주는 코드(x:8, y:10, 270*70의 크기)
		inputSpace.setBounds(8,10,270,70);	
		
		//버튼들을 담을 패넬
		JPanel buttonPanel = new JPanel();
		// 격자 형태로 배치해주는 GridLayout을 사용(가로 칸수, 세로 칸수, 좌우 간격, 상하 간격)
		buttonPanel.setLayout(new GridLayout(4,4,10,10));
		// 위치와 크기를 설정
		buttonPanel.setBounds(8,90,270,235);
		
		String button_names[] = {"C", "%", "X", "=", "7", "8", "9", "+", "4", "5", "6", "-", "1", "2", "3", "0"};
		JButton buttons[] = new JButton[button_names.length];
		
		for (int i=0; i<button_names.length; i++) {
			buttons[i] = new JButton(button_names[i]);
			buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
			// 버튼의 배경색 및 글자색을 설정
			if(button_names[i] == "C") {
				buttons[i].setBackground(Color.red);
			}else if((i>=4 && i<=6) || (i>=8 && i<=10)||(i>=12 &&i<=14)) {
				buttons[i].setBackground(Color.black);
			}else {
				buttons[i].setBackground(Color.gray);
			}
			buttons[i].setForeground(Color.white);
			buttons[i].setBorderPainted(false); // 버튼의 테두리를 제거
			buttons[i].addActionListener(new PadActionListener()); // 이벤트를 버튼에 추가
			buttonPanel.add(buttons[i]); //버튼들을 페넬에 저장
		}
		
		add(inputSpace);// 계산기의 틀을 작성한뒤 넣어준다.
		add(buttonPanel); // 계산기의 버튼들을 작성한뒤 넣어준다.
		
		// 계산기의 틀을 만드는 코드
		setTitle("계산기");
		setVisible(true);
		setSize(300,380);
		setLocationRelativeTo(null);
		setResizable(false);
		// 아래 코드를 입력해 주어야 창을 닫을떄 실행중인 프로그램도 종료 된다.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class PadActionListener implements ActionListener{
		// ActionListener : 이벤트를 주는 인터페이스
		@Override
		public void actionPerformed(ActionEvent e) {
			// 어떤 버튼이 눌렸는지 불러온다.
			String operation = e.getActionCommand();
			// 버튼을 눌렀을떄 처리해주는 코드(C 와 = 와 나머지를 구현)
			if(operation.equals("C")) {
				inputSpace.setText("");
			}	else if(operation.equals("=")) {
				String result = Double.toString(calculate(inputSpace.getText()));
				inputSpace.setText(""+result);
				num = "";
			}	else {
				inputSpace.setText(inputSpace.getText() +e.getActionCommand()); // 화면에 출력
			}
				
		}
		
	}
	
	private void fullTextParsing(String inputText) {
		equation.clear();
		
		for(int i=0; i<inputText.length(); i++) {
			char ch = inputText.charAt(i);
			// 숫자 이외에는 문자를 추가해주고 num을 초기화
			// 숫자 인 경우에는 num을 입력
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
