//Laboration 4- Kalkylator
//David Nilsson Löfvall
//Dalo1300
//Lärare: Mikael Nilsson / Robert Jonsson
/*Detta är GUI-kalkylator som tar 2 operander separerat med en operator för beräkning. Alla knappar är inte aktiva eller
 * fungerar såsom tänkt i skrivande stund men det som är behövligt för laborationen är i bruk. */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Detta är GUI-kalkylator som tar 2 operander separerat med en operator för beräkning. Alla knappar är inte aktiva eller
 * fungerar såsom tänkt i skrivande stund men det som är behövligt för laborationen är i bruk.
 */

/**
 * @author David
 *
 */
public class Calculator extends JFrame  {
	
	//Privata medlemmar
	//Lägger in alla knappar i en 2D array
	private static final String [][] calcbuttons= {
			{"<-","CE", "C", "+"},
			{"7", "8","9","-"},
			{"4", "5", "6","/"},
			{"1", "2", "3","*"},
			{"0", ".", ",", "="}	
			
	};
	
	
	private static final Font calcFont = new Font(Font.MONOSPACED, Font.BOLD, 20);
	
	
	private JPanel calculator;
	private JPanel mainFrame;

	private JTextField result;
	private int operand1, operand2, totalSum,counter;
	private String op, previousTask;
	private Boolean operatorInPlace= false;
	
	
	public Calculator(){
	super("Kalkylator");
	
	ActionListener numbers= new ActionListener() {
		@Override
		
		public void actionPerformed(ActionEvent e) {
			//Lagrar knappens "tecke" i en sträng
			String input= e.getActionCommand();
			
			String outResult=null;

			//Kontroller av knappinnehåll och ifall de innehåller annat än siffror ska detta hanteras
			if(input.contains("+")|| input.contains("-")|| input.contains("/")||input.contains("*"))
			{
				previousTask= result.getText();

					if(operatorInPlace)
					{
						
						result.setText("Too many operators");
					}
					else
					{
						
						operand1= Integer.parseInt(result.getText());
						op= input;
						operatorInPlace= true;
						input= result.getText()+op;
						result.setText(input);
						if(counter==0)
						{
							counter++;
						}
						
					}
			}
			
			else if(input.contains("CE"))
			{
				result.setText(previousTask);
			}
			
			else if(input.contains("C"))
			{
				result.setText("");
				operand1=0;
				operand2=0;
				operatorInPlace=false;
				totalSum=0;
				counter=0;
				op="";
			}
			else if(input.contains("="))
			{
			 try{
				if(operatorInPlace&& counter==2)
				{
					totalSum= calculate (operand1,op, operand2);
					if(op=="/" && operand2==0)
					{
						throw new IllegalArgumentException("Divide by zero");
					}
					outResult=Integer.toString(totalSum);
					//JOptionPane.showMessageDialog(mainFrame, totalSum);
					result.setText(outResult);
					operatorInPlace=false;
					counter--;
					operand1= totalSum;
					operand2=0;
					op="";
					
				}
			 }
			 catch(Exception a)
			 {
				 counter--;
				 op="";
				 operand2=0;
				 operatorInPlace=false;
				 result.setText("Can't divide "+a.getMessage());
			 }
			}
		
			else if(counter==1)
			{
				operand2= Integer.parseInt(input);
				result.setText(result.getText()+input);
				counter++;
				
			}
			
			else 
			{
				result.setText(result.getText()+input);
			
			}
		
	}
		
	};
	
	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	calculator= new JPanel(new GridLayout(calcbuttons.length, calcbuttons[0].length ));
	
	//Skapar en JButton för varje element i arrayen
	for(int i=0; i<calcbuttons.length; i++)
	{
		for (int j = 0; j <calcbuttons[i].length; j++) {
            JButton btn = new JButton(calcbuttons[i][j]);
            calculator.setFont(calcFont);
            
            calculator.add(btn);

            btn.addActionListener(numbers);
         }
		
	}
	
	result= new JTextField(10);
	result.setHorizontalAlignment(SwingConstants.RIGHT);
	result.setFont(calcFont);
	result.setEditable(false);
	
	mainFrame= new JPanel(new BorderLayout());
	mainFrame.add(result,BorderLayout.PAGE_START);
	mainFrame.add(calculator);

	super.add(mainFrame);
	
	pack();
	setLocation(500, 500);
	setVisible(true);
	
	
	
	}
	//Själva beräkningasfunktionen
	public int calculate(int op1, String oper, int op2){
		char newOper;
		newOper=oper.charAt(0);
		int finalResult = 0;
		switch(newOper)
		{
			case '+': finalResult=op1+op2;
			break;
			case '-': finalResult=op1-op2;
			break;
			case '*': finalResult= op1*op2;
			break;
			case '/': finalResult=op1/op2;
			break;
		}

		return finalResult;
	}
	

}