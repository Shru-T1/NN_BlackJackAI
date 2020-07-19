
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.Enumeration;

import javax.swing.*;

public class NNRD extends JFrame {
	
	public HiddenNeuron[] hn;
	public OutputNeuron[] on;

	double sum, error[] = new double[18], errorAxon[] = new double[2];
	double[] iA = new double[18];
	double[] oA = new double[2];
	double[][] ddo = new double[18][4];
	double[][] ddh = new double[4][2];
	double[] errorInt = new double[4];

	double alphaTemp = 0.6;

	JTextField[] inputAxon = new JTextField[18]; // input axon
	JTextField[] hiddenAxon = new JTextField[4];;

	JTextField[] outputAxon = new JTextField[2];
	JTextField[] outputAxonT = new JTextField[2];
	JRadioButton[] inputNeuron = new JRadioButton[18];
	JRadioButton[] hiddenNeuron = new JRadioButton[4];;
	JRadioButton[] outputNeuron = new JRadioButton[18];;
	
	public Color TCUColors = new Color(77, 25, 121);

	boolean verbose = true;
	public DecimalFormat decimal = new DecimalFormat("###,###.####");
	public JPanel nn = new JPanel(new GridLayout(19, 7));

	public static void main(String args[]) {
		
		// Construct the frame
		new NNRD();

	}

	
	public static double sigmoid(double x) {
		return (1 / (1 + Math.pow(Math.E, (-1 * x))));
	}

	public NNRD() {
		setTitle("Neural Network for 21");
		setFont(new Font("Helvetica", Font.BOLD, 18));

		displayNet();

		setBounds(500, 150, 500, 700);
		setVisible(true);
		validate();

	}

	public NNRD(double[] iAfm, HiddenNeuron[] hnfm, OutputNeuron[] onfm, double[] oAfm) {
		setTitle("Neural Network for 21");
		setFont(new Font("Helvetica", Font.BOLD, 18));
		iA = iAfm;
		hn = hnfm;
		on = onfm;
		oA = oAfm;
		displayNet();
		setBounds(650, 150, 400, 600);
		setVisible(true);
		validate();

	}

	public void displayNet() {
		for (int i = 0; i < 18; i++) {
			inputAxon[i] = new JTextField();

			if (i == 0)
				inputNeuron[i] = new JRadioButton("4");
			if (i == 1)
				inputNeuron[i] = new JRadioButton("5");
			if (i == 2)
				inputNeuron[i] = new JRadioButton("6");
			if (i == 3)
				inputNeuron[i] = new JRadioButton("7");
			if (i == 4)
				inputNeuron[i] = new JRadioButton("8");
			if (i == 5)
				inputNeuron[i] = new JRadioButton("9");
			if (i == 6)
				inputNeuron[i] = new JRadioButton("10");
			if (i == 7)
				inputNeuron[i] = new JRadioButton("11");
			if (i == 8)
				inputNeuron[i] = new JRadioButton("12");
			if (i == 9)
				inputNeuron[i] = new JRadioButton("13");
			if (i == 10)
				inputNeuron[i] = new JRadioButton("14");
			if (i == 11)
				inputNeuron[i] = new JRadioButton("15");
			if (i == 12)
				inputNeuron[i] = new JRadioButton("16");
			if (i == 13)
				inputNeuron[i] = new JRadioButton("17");
			if (i == 14)
				inputNeuron[i] = new JRadioButton("18");
			if (i == 15)
				inputNeuron[i] = new JRadioButton("19");
			if (i == 16)
				inputNeuron[i] = new JRadioButton("20");
			if (i == 17)
				inputNeuron[i] = new JRadioButton("21");

			inputNeuron[i].setForeground(Color.black);

		}

		for (int i = 0; i < 2; i++) {
			outputAxon[i] = new JTextField();
			outputAxonT[i] = new JTextField();

			if (i == 0)
				outputNeuron[i] = new JRadioButton("S");
			if (i == 1)
				outputNeuron[i] = new JRadioButton("H");
			outputNeuron[i].setForeground(Color.black);
		}

		for (int i = 0; i < 4; i++) {
			hiddenAxon[i] = new JTextField("");

			if (i == 0)
				hiddenNeuron[i] = new JRadioButton("h 0");
			if (i == 1)
				hiddenNeuron[i] = new JRadioButton("h 1");
			if (i == 2)
				hiddenNeuron[i] = new JRadioButton("h 2");
			if (i == 3)
				hiddenNeuron[i] = new JRadioButton("h 3");
			hiddenNeuron[i].setForeground(Color.black);
		}
		nn.setForeground(Color.WHITE);
		nn.setBackground(TCUColors);
		add(nn);
		JLabel li = new JLabel("Input");
		JLabel lh = new JLabel("Hidden");
		JLabel lo = new JLabel("Output");

		JLabel vA = new JLabel("True");

		li.setForeground(Color.WHITE);
		lh.setForeground(Color.WHITE);
		lo.setForeground(Color.WHITE);
		vA.setForeground(Color.WHITE);
		nn.add(li);
		nn.add(new JLabel(""));
		nn.add(lh);
		nn.add(new JLabel(""));
		nn.add(lo);
		nn.add(new JLabel(""));
		nn.add(vA);
		for (int i = 0; i < 18; i++)

		{
			if (i < 2) {
				nn.add(inputAxon[i]);
				nn.add(inputNeuron[i]);
				nn.add(hiddenAxon[i]);
				nn.add(hiddenNeuron[i]);
				nn.add(outputAxon[i]);
				nn.add(outputNeuron[i]);
				nn.add(outputAxonT[i]);
			} else if (i > 1 && i < 4) {
				nn.add(inputAxon[i]);
				nn.add(inputNeuron[i]);
				nn.add(hiddenAxon[i]);
				nn.add(hiddenNeuron[i]);
				nn.add(new JLabel(""));
				nn.add(new JLabel(""));
				nn.add(new JLabel(""));
			}

			else if (i > 3) {
				nn.add(inputAxon[i]);
				nn.add(inputNeuron[i]);
				nn.add(new JLabel(""));
				nn.add(new JLabel(""));
				nn.add(new JLabel(""));
				nn.add(new JLabel(""));
				nn.add(new JLabel(""));
			}

		}

	}
	public void backProp() {
	// Backpropagation
			for (int i = 0; i < on.length; i++) {
				error[i] = (double) (oA[i] - on[i].axonValue);
				errorAxon[i] = on[i].axonValue * (1 - on[i].axonValue) * error[i];

				for (int j = 0; j < 4; j++)
					errorInt[j] = 0;
				for (int j = 0; j < 4; j++) {
					ddo[i][j] = errorAxon[i] * on[i].den[j];
					errorInt[j] = ddo[i][j] + errorInt[j];

					on[i].den[j] = on[i].den[j] + alphaTemp * ddo[i][j];

				}
				on[i].th = on[i].th + alphaTemp * error[i];
			}

			for (int i = 0; i < 4; i++)

			{
				for (int j = 0; j < 2; j++) {
					ddh[i][j] = errorInt[i] * hn[i].den[j];
					hn[i].den[j] = hn[i].den[j] + alphaTemp * ddh[i][j];
				}
				hn[i].th = hn[i].th + alphaTemp * errorInt[i];
			}

	}
	public void forwardProp() {
		// propagation

		for (int i = 0; i < hn.length; i++) {
			sum = 0;
			for (int j = 0; j < 18; j++)
				sum = sum + hn[i].den[j] * iA[j];
			hn[i].axonValue = 1 / (1 + Math.exp(-(sum + hn[i].th)));
			hiddenAxon[i].setText((decimal.format(hn[i].axonValue)));
			if (sum > hn[i].th) {
				hn[i].axon = 1;
				hiddenNeuron[i].setSelected(true);
			}
			else {
				hn[i].axon = 0;
				hiddenNeuron[i].setSelected(false);
			}

		}

		for (int i = 0; i < on.length; i++) {
			sum = 0;
			for (int j = 0; j < 4; j++)
				sum = sum + on[i].den[j] * hn[j].axon;
			on[i].axonValue = 1 / (1 + Math.exp(-(sum + on[i].th)));
			outputAxon[i].setText((decimal.format(on[i].axonValue)));
			if (sum > on[i].th) {
				on[i].axon = 1;
				outputNeuron[i].setSelected(true);
			}
			else {
				on[i].axon = 0;
				outputNeuron[i].setSelected(false);
			}

		}
		
	}

	public void setLabels() {
		for(int i = 0; i < hiddenNeuron.length; i++) {
			hiddenAxon[i].setText(Double.toString(hn[i].axonValue));
			if(hn[i].axon == 1) {
				hiddenNeuron[i].setSelected(true);
			}else {
				hiddenNeuron[i].setSelected(false);
			}
			
		}
	}

}
