import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Model extends View implements Serializable {

	private Controller c;
	BufferedWriter writer;
	BufferedReader reader;
	String filename;
	JFileChooser chooser = new JFileChooser();

	private int playerCardCount;
	private int dealerCardCount;
	private Deck deck = new Deck();

	int card1, card2, dealCard1;
	Random ran = new Random();

	int card3, card4, card5;

	int dealCard2, dealCard3, dealCard4, dealCard5;

	Card c1, c2, c3, c4, c5, dC1, dC2, dC3, dC4, dC5;

	int initialSum;
	int dealerSum;
	int hit1Sum, hit2Sum, hit3Sum;
	int currentSum;

	LinkedList<String> stm = new LinkedList<String>();

	String currentState = "";
	Double hitPercent;

	double punishment = 0.95;
	double reward = 1.07;

	double standReward = 0.93;
	double standPunishment = 1.05;

	boolean AIPlaying = false;
	boolean nnBool = false;
	boolean gameOver = false;

	private NNRD nn;
	private HiddenNeuron h0, h1, h2, h3;

	int AICount;

	public HiddenNeuron[] hn;
	public OutputNeuron[] on;

	public Model() {
	}

	public void playFunc() {
		gameOver = false;

		currentState = "";
		initialSum = 0;
		dealerSum = 0;

		winOrLose.setText("");

		sysCard1.setText("");
		sysCard2.setText("");
		sysCard3.setText("");
		sysCard4.setText("");
		sysCard5.setText("");

		oppCard1.setText("");
		oppCard2.setText("");
		oppCard3.setText("");
		oppCard4.setText("");
		oppCard5.setText("");

		playerCardCount = 2;
		dealerCardCount = 1;

		playB.setEnabled(false);
		hitB.setEnabled(true);
		standB.setEnabled(true);
		playAIB.setEnabled(false);
		playAI100B.setEnabled(false);

		card1 = ran.nextInt(51);

		card2 = ran.nextInt(51);

		while (card2 == card1) { // Make Sure Card isnt duplicate
			card2 = ran.nextInt(51);
		}

		dealCard1 = ran.nextInt(51);
		while (dealCard1 == card1 | dealCard1 == card2) {
			dealCard1 = ran.nextInt(51);
		}

		// Assign cards

		c1 = deck.cards[card1];
		c2 = deck.cards[card2];

		dC1 = deck.cards[dealCard1];

		initialSum = c1.getValue() + c2.getValue();
		currentSum = initialSum;
		dealerSum = dC1.getValue();
		sumLab.setText(Integer.toString(initialSum));

		// CARD 1
		if (!c1.getName().equals(Integer.toString(c1.getValue()))) {
			oppCard1.setText("<html><h1>" + c1.getName() + "</h1><br/>" + Integer.toString(c1.getValue()) + "</html>");

		} else {
			oppCard1.setText("<html><h1>" + c1.getName() + "</h1></html>");
		}
		// CARD 2
		if (!c2.getName().equals(Integer.toString(c2.getValue()))) {
			oppCard2.setText("<html><h1>" + c2.getName() + "</h1><br/>" + Integer.toString(c2.getValue()) + "</html>");

		} else {
			oppCard2.setText("<html><h1>" + c2.getName() + "</h1></html>");
		}

		// DEALER CARD
		if (!dC1.getName().equals(Integer.toString(dC1.getValue()))) {
			sysCard1.setText(
					"<html><h1>" + dC1.getName() + "</h1><br/>" + Integer.toString(dC1.getValue()) + "</html>");

		} else {
			sysCard1.setText("<html><h1>" + dC1.getName() + "</h1></html>");
		}
		if (initialSum == 21) {
			gameOver = true;

			winOrLose.setText("YOU WON! BLACK JACK");
			hitB.setEnabled(false);
			standB.setEnabled(false);
			playB.setEnabled(true);
			playAIB.setEnabled(true);
			playAI100B.setEnabled(true);
		}
		if (initialSum > 21) {
			gameOver = true;

			winOrLose.setText("YOU BUSTED!");
			hitB.setEnabled(false);
			standB.setEnabled(false);
			playB.setEnabled(true);
			playAIB.setEnabled(true);
			playAI100B.setEnabled(true);

		}
		if (nnBool == true && initialSum != 22) {
			int but = initialSum - 4;
			nn.inputNeuron[but].setSelected(true);
			nn.inputAxon[but].setText("1");
			nn.iA[but] = 1;
			for (int i = 0; i < 18; i++) {
				if (i != but) {
					nn.inputNeuron[i].setSelected(false);
					nn.inputAxon[i].setText("0");
					nn.iA[i] = 0;
				}
			}

			nn.forwardProp();
			if (initialSum <= 10) {
				nn.oA[0] = 0;
				nn.oA[1] = 1;
				nn.outputAxonT[0].setText("0");
				nn.outputAxonT[1].setText("1");
			} else {
				nn.outputAxonT[0].setText("");
				nn.outputAxonT[1].setText("");
			}
		}
	}

	public void AIPlay500() {
		AICount = 0;

		for (int i = 0; i < 500; i++) {
			AIPlay();
		}

		System.out.println("Won " + Integer.toString(AICount) + " out of 500");
	}

	public void AIPlay() {

		playFunc();
		// Get HIT/STAND AXON VALUES
		double sVal = nn.on[0].axonValue;
		double hVal = nn.on[1].axonValue;

		while (!gameOver) {
			sVal = nn.on[0].axonValue;
			hVal = nn.on[1].axonValue;

			if (hVal > sVal) {
				hitFunc();
			} else {

				standFunc();
			}
		}

	}

	public void hitFunc() {
		playerCardCount++;

		String[] state;
		switch (playerCardCount) {
		case 3:
			card3 = ran.nextInt(51);
			while (card3 == card2) { // Make Sure Card isnt duplicate
				card3 = ran.nextInt(51);
			}
			c3 = deck.cards[card3];
			hit1Sum = currentSum + c3.getValue();
			sumLab.setText(Integer.toString(hit1Sum));
			currentSum = hit1Sum;

			if (!c3.getName().equals(Integer.toString(c3.getValue()))) {
				oppCard3.setText(
						"<html><h1>" + c3.getName() + "</h1><br/>" + Integer.toString(c3.getValue()) + "</html>");

			} else {
				oppCard3.setText("<html><h1>" + c3.getName() + "</h1></html>");
			}
			// IF NEW SUM > 21 DISABLE BUTTON AND DO END FUNCTION
			if (!sumBelowTwentyOne()) {
				hitB.setEnabled(false);
				standB.setEnabled(false);
			}
			if (hit1Sum == 21) {
				gameOver = true;

				AICount++;
				if (nnBool == true) {
					// WON BY HITTING SO YOU SHOULDVE HIT!
					nn.oA[0] = 0;
					nn.oA[1] = 1;
					nn.outputAxonT[0].setText("0");
					nn.outputAxonT[1].setText("1");
					nn.backProp();
				}

				winOrLose.setText(currentState + "YOU WON!");
				hitB.setEnabled(false);
				standB.setEnabled(false);
				playB.setEnabled(true);
				gameOver();

				break;
			}
			if (hit1Sum > 21) {
				gameOver = true;

				if (nnBool == true) {
					// LOST BY HITTING SO YOU SHOULD NOT HAVE HIT!
					nn.oA[0] = 1;
					nn.oA[1] = 0;
					nn.outputAxonT[0].setText("1");
					nn.outputAxonT[1].setText("0");
					nn.backProp();
				}

				winOrLose.setText(currentState + "YOU LOST!");
				hitB.setEnabled(false);
				standB.setEnabled(false);
				playB.setEnabled(true);
				gameOver();

				break;
			}
			if (nnBool == true) {
				int but = currentSum - 4;
				nn.inputNeuron[but].setSelected(true);
				nn.inputAxon[but].setText("1");
				nn.iA[but] = 1;
				for (int i = 0; i < 18; i++) {
					if (i != but) {
						nn.inputNeuron[i].setSelected(false);
						nn.inputAxon[i].setText("0");
						nn.iA[i] = 0;
					}
				}

				nn.forwardProp();
				if (currentSum <= 10) {
					nn.oA[0] = 0;
					nn.oA[1] = 1;
					nn.outputAxonT[0].setText("0");
					nn.outputAxonT[1].setText("1");
				} else {
					nn.outputAxonT[0].setText("");
					nn.outputAxonT[1].setText("");
				}
			}

			break;
		case 4:
			card4 = ran.nextInt(51);
			while (card4 == card3) { // Make Sure Card isnt duplicate
				card4 = ran.nextInt(51);
			}
			c4 = deck.cards[card4];
			hit2Sum = Integer.parseInt(sumLab.getText()) + c4.getValue();
			sumLab.setText(Integer.toString(hit2Sum));
			currentSum = hit2Sum;

			if (!c4.getName().equals(Integer.toString(c4.getValue()))) {
				oppCard4.setText(
						"<html><h1>" + c4.getName() + "</h1><br/>" + Integer.toString(c4.getValue()) + "</html>");

			} else {
				oppCard4.setText("<html><h1>" + c4.getName() + "</h1></html>");
			}
			// IF NEW SUM > 21 DISABLE BUTTON AND DO END FUNCTION
			if (!sumBelowTwentyOne()) {
				hitB.setEnabled(false);
				standB.setEnabled(false);
			}
			if (hit2Sum == 21) {
				gameOver = true;

				AICount++;
				if (nnBool == true) {
					// WON BY HITTING SO YOU SHOULDVE HIT!
					nn.oA[0] = 0;
					nn.oA[1] = 1;
					nn.outputAxonT[0].setText("0");
					nn.outputAxonT[1].setText("1");
					nn.backProp();
				}

				winOrLose.setText(currentState + "YOU WON!");
				hitB.setEnabled(false);
				standB.setEnabled(false);
				playB.setEnabled(true);
				gameOver();

				break;
			}
			if (hit2Sum > 21) {
				gameOver = true;

				if (nnBool == true) {
					// LOST BY HITTING SO YOU SHOULD NOT HAVE HIT!
					nn.oA[0] = 1;
					nn.oA[1] = 0;
					nn.outputAxonT[0].setText("1");
					nn.outputAxonT[1].setText("0");
					nn.backProp();
				}

				winOrLose.setText(currentState + "YOU LOST!");
				hitB.setEnabled(false);
				standB.setEnabled(false);
				playB.setEnabled(true);
				gameOver();

				break;
			}
			if (nnBool == true) {
				int but = currentSum - 4;
				nn.inputNeuron[but].setSelected(true);
				nn.inputAxon[but].setText("1");
				nn.iA[but] = 1;
				for (int i = 0; i < 18; i++) {
					if (i != but) {
						nn.inputNeuron[i].setSelected(false);
						nn.inputAxon[i].setText("0");
						nn.iA[i] = 0;
					}
				}

				nn.forwardProp();
				if (currentSum <= 10) {
					nn.oA[0] = 0;
					nn.oA[1] = 1;
					nn.outputAxonT[0].setText("0");
					nn.outputAxonT[1].setText("1");
				} else {
					nn.outputAxonT[0].setText("");
					nn.outputAxonT[1].setText("");
				}
			}

			break;
		case 5:
			card5 = ran.nextInt(51);
			while (card5 == card4) { // Make Sure Card isnt duplicate
				card5 = ran.nextInt(51);
			}
			c5 = deck.cards[card5];
			hit3Sum = Integer.parseInt(sumLab.getText()) + c5.getValue();
			sumLab.setText(Integer.toString(hit3Sum));
			currentSum = hit3Sum;

			if (!c5.getName().equals(Integer.toString(c5.getValue()))) {
				oppCard5.setText(
						"<html><h1>" + c5.getName() + "</h1><br/>" + Integer.toString(c5.getValue()) + "</html>");

			} else {
				oppCard5.setText("<html><h1>" + c5.getName() + "</h1></html>");
			}
			// IF NEW SUM > 21 DISABLE BUTTON AND DO END FUNCTION
			if (!sumBelowTwentyOne()) {
				hitB.setEnabled(false);
				standB.setEnabled(false);
			}
			if (hit3Sum == 21) {
				gameOver = true;

				AICount++;
				if (nnBool == true) {
					// WON BY HITTING SO YOU SHOULDVE HIT!
					nn.oA[0] = 0;
					nn.oA[1] = 1;
					nn.outputAxonT[0].setText("0");
					nn.outputAxonT[1].setText("1");
					nn.backProp();
				}

				winOrLose.setText(currentState + "YOU WON!");
				hitB.setEnabled(false);
				standB.setEnabled(false);
				playB.setEnabled(true);
				gameOver();

				break;

			}
			if (hit3Sum > 21) {
				gameOver = true;

				if (nnBool == true) {
					// LOST BY HITTING SO YOU SHOULD NOT HAVE HIT!
					nn.oA[0] = 1;
					nn.oA[1] = 0;
					nn.outputAxonT[0].setText("1");
					nn.outputAxonT[1].setText("0");
					nn.backProp();
				}

				winOrLose.setText(currentState + "YOU LOST!");
				hitB.setEnabled(false);
				standB.setEnabled(false);
				playB.setEnabled(true);
				gameOver();

				break;
			}
			if (nnBool == true) {
				int but = currentSum - 4;
				nn.inputNeuron[but].setSelected(true);
				nn.inputAxon[but].setText("1");
				nn.iA[but] = 1;
				for (int i = 0; i < 18; i++) {
					if (i != but) {
						nn.inputNeuron[i].setSelected(false);
						nn.inputAxon[i].setText("0");
						nn.iA[i] = 0;
					}
				}

				nn.forwardProp();
				if (currentSum <= 10) {
					nn.oA[0] = 0;
					nn.oA[1] = 1;
					nn.outputAxonT[0].setText("0");
					nn.outputAxonT[1].setText("1");
				} else {
					nn.outputAxonT[0].setText("");
					nn.outputAxonT[1].setText("");
				}
			}
			break;

		}

	}

	public Double getHitPercent() {
		String[] tokens = currentState.split("/");
		String h = tokens[2].substring(1);
		Double hitP = Double.parseDouble(h) / 100;

		return hitP;
	}

	public void standFunc() {

		hitB.setEnabled(false);
		standB.setEnabled(false);

		int playerSum = Integer.parseInt(sumLab.getText());
		dealerSum = dC1.getValue();

		while (dealerSum < 17 | (dealerSum <= playerSum && dealerSum < 21)) {
			dealerCardCount++;

			switch (dealerCardCount) {
			case 2:
				dealCard2 = ran.nextInt(51);
				while (dealCard2 == dealCard1) { // Make Sure Card isnt duplicate
					dealCard2 = ran.nextInt(51);
				}
				dC2 = deck.cards[dealCard2];
				dealerSum = dealerSum + dC2.getValue();
				if (!dC2.getName().equals(Integer.toString(dC2.getValue()))) {
					sysCard2.setText(
							"<html><h1>" + dC2.getName() + "</h1><br/>" + Integer.toString(dC2.getValue()) + "</html>");

				} else {
					sysCard2.setText("<html><h1>" + dC2.getName() + "</h1></html>");
				}

				break;
			case 3:
				dealCard3 = ran.nextInt(51);
				while (dealCard3 == dealCard2) { // Make Sure Card isnt duplicate
					dealCard3 = ran.nextInt(51);
				}
				dC3 = deck.cards[dealCard3];
				dealerSum = dealerSum + dC3.getValue();
				if (!dC3.getName().equals(Integer.toString(dC3.getValue()))) {
					sysCard3.setText(
							"<html><h1>" + dC3.getName() + "</h1><br/>" + Integer.toString(dC3.getValue()) + "</html>");

				} else {
					sysCard3.setText("<html><h1>" + dC3.getName() + "</h1></html>");
				}

				break;
			case 4:
				dealCard4 = ran.nextInt(51);
				while (dealCard4 == dealCard3) { // Make Sure Card isnt duplicate
					dealCard4 = ran.nextInt(51);
				}
				dC4 = deck.cards[dealCard4];
				dealerSum = dealerSum + dC4.getValue();
				if (!dC4.getName().equals(Integer.toString(dC4.getValue()))) {
					sysCard4.setText(
							"<html><h1>" + dC4.getName() + "</h1><br/>" + Integer.toString(dC4.getValue()) + "</html>");

				} else {
					sysCard4.setText("<html><h1>" + dC4.getName() + "</h1></html>");
				}

				break;
			case 5:
				dealCard5 = ran.nextInt(51);
				while (dealCard5 == dealCard4) { // Make Sure Card isnt duplicate
					dealCard5 = ran.nextInt(51);
				}
				dC5 = deck.cards[dealCard5];
				dealerSum = dealerSum + dC5.getValue();
				if (!dC5.getName().equals(Integer.toString(dC5.getValue()))) {
					sysCard5.setText(
							"<html><h1>" + dC5.getName() + "</h1><br/>" + Integer.toString(dC5.getValue()) + "</html>");

				} else {
					sysCard5.setText("<html><h1>" + dC5.getName() + "</h1></html>");
				}

				break;
			}
		}
		checkIfWon();

	}

	public Boolean sumBelowTwentyOne() {
		if (Integer.parseInt(sumLab.getText()) <= 21) {
			return true;
		} else {
			return false;
		}

	}

	public void checkIfWon() {
		// If your sum > dealer sum and less than or equal to 21
		// If your sum == 21

		if (Integer.parseInt(sumLab.getText()) == 21
				|| (dealerSum < Integer.parseInt(sumLab.getText()) && Integer.parseInt(sumLab.getText()) < 21)
				|| dealerSum > 21) {
			// STAND WON
			winOrLose.setText("YOU WON!");
			if (nnBool == true) {
				gameOver = true;

				AICount++;
				// WON BY STANDING SO YOU SHOULDVE STOOD!
				nn.oA[0] = 1;
				nn.oA[1] = 0;
				nn.outputAxonT[0].setText("1");
				nn.outputAxonT[1].setText("0");
				nn.backProp();
			}
			gameOver();

		} else {
			gameOver = true;

			// STAND LOST
			if (nnBool == true) {
				// LOST BY STANDING SO YOU SHOULDnt have STOOD!
				nn.oA[0] = 0;
				nn.oA[1] = 1;
				nn.outputAxonT[0].setText("0");
				nn.outputAxonT[1].setText("1");
				nn.backProp();
			}
			winOrLose.setText("YOU LOST!");
			gameOver();
		}

		playAI100B.setEnabled(true);
		playAIB.setEnabled(true);
		playB.setEnabled(true);

	}

	public void gameOver() {

		playAI100B.setEnabled(true);
		playAIB.setEnabled(true);
		playB.setEnabled(true);
		hitB.setEnabled(false);
		standB.setEnabled(false);
	}

	public void nnFrame() {
		if (nn == null) {
			nn = new NNRD();
			HiddenNeuron[] hn;
			hn = new HiddenNeuron[4];
			OutputNeuron[] on;
			on = new OutputNeuron[2];

			for (int i = 0; i < hn.length; i++)
				switch (i) {
				case 0:
					hn[i] = new HiddenNeuron();
				case 1:
					hn[i] = new HiddenNeuron();
				case 2:
					hn[i] = new HiddenNeuron();
				case 3:
					hn[i] = new HiddenNeuron();
				}

			for (int i = 0; i < hn.length; i++)
				switch (i) {
				case 0:
					on[i] = new OutputNeuron();
				case 1:
					on[i] = new OutputNeuron();

				}

			nn.hn = hn;
			nn.on = on;
		}

		nnBool = true;
		showNN.setEnabled(false);

		int but = 18;
		for (int i = 0; i < but; i++) {
			nn.inputNeuron[i].setSelected(false);
			nn.inputAxon[i].setText("0");
			nn.iA[i] = 0;
		}

		nn.setVisible(true);

		nn.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(nn, "Are you sure you want to close this window?", "Close Window?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					showNN.setEnabled(true);
					nnBool = false;
				}
			}
		});
	}

	public void saveFile() 
	{
		String feed = nn.inputAxon[0].getText();;
		
		for (int i=1; i<18; i++)
		{
			feed = feed + "\n" + nn.inputAxon[i].getText();}
		for (int i=0; i<18; i++)
			{if (nn.inputNeuron[i].isSelected()) {
				feed = feed + "\n" + nn.inputNeuron[i].getText();
			} 
		}
			
		for (int j=0; j<4; j++)
		{
			feed = feed + "\n" + nn.hiddenAxon[j].getText();}
		for (int j=0; j<4; j++)
		{	if (nn.hiddenNeuron[j].isSelected()) {
			feed = feed + "\n" + nn.hiddenNeuron[j].getText();
			}
		else feed = feed + "\n" + "null";
		}
		
		for (int k=0; k<2; k++) {
			feed = feed + "\n" + nn.outputAxon[k].getText();}
		for (int k=0; k<2; k++) {
			if (nn.outputNeuron[k].isSelected()) {
				feed = feed + "\n" + nn.outputNeuron[k].getText();
			}

		} 			for (int p=0; p<2; p++) {
			feed = feed + "\n" + nn.outputAxonT[p].getText();}
		System.out.println(feed);
		
		
		final JFileChooser SaveAs = new JFileChooser();
		SaveAs.setApproveButtonText("Save");
		int actionDialog = SaveAs.showSaveDialog(this);
		if (actionDialog != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File fileName = new File(SaveAs.getSelectedFile() + ".txt");
		FileWriter fr = null;
		BufferedWriter outFile = null;
		String dataWithNewLine=feed+System.getProperty("line.separator");
    try{
        fr = new FileWriter(fileName);
        outFile = new BufferedWriter(fr);
        for(int i = 1; i>0; i--){
            outFile.write(dataWithNewLine);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }finally{
        try {
            outFile.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}
	
	/*public void saveFile() {
		
		final JFileChooser SaveAs = new JFileChooser();
		SaveAs.setApproveButtonText("Save");
		int actionDialog = SaveAs.showSaveDialog(this);
		if (actionDialog != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File fileName = new File(SaveAs.getSelectedFile() + ".txt");
		BufferedWriter outFile = null;
		try {
			outFile = new BufferedWriter(new FileWriter(fileName, false));
			Iterator<String> iterator = stm.iterator();

			while (iterator.hasNext()) {
				outFile.write(iterator.next() + "\n");
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (outFile != null) {
				try {
					outFile.close();
				} catch (IOException e) {

				}
			}
		}

	}
*/

	public void readFile() throws FileNotFoundException {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files only", "txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(getParent());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			filename = chooser.getSelectedFile().getAbsolutePath();
			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			try {
				reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));
				// new NNRD();
				String line = reader.readLine();
				while (line != null) {
					for (int i=0; i<18; i++)
					{System.out.println(line);
					nn.inputAxon[i].setText(line);
					line = reader.readLine();
					}
					//System.out.println(line);
				//	stm.add(line);
					
					System.out.println(Integer.parseInt(line));
					for (int i=0; i<18; i++) {
						System.out.println(Integer.parseInt(line));
						System.out.println(Integer.parseInt(line)-4);
					if (i == ((Integer.parseInt(line))-4)) {nn.inputNeuron[i].setSelected(true);}
					else {nn.inputNeuron[i].setSelected(false);}
					}
					line = reader.readLine();
					for (int i=0; i<4; i++)
					{
						System.out.println(line);
						nn.hiddenAxon[i].setText(line);
						line = reader.readLine();
					}
					for (int i=0; i<4; i++)
					{
						System.out.println(line);
					if (line == "null") {nn.hiddenNeuron[i].setSelected(false);}
					else nn.hiddenNeuron[i].setSelected(true);
					System.out.println(line);
					line = reader.readLine();
					}
					for (int i=0; i<2; i++)
					{
						nn.outputAxon[i].setText(line);
						line = reader.readLine();
					}
					for (int i=0; i<2; i++)
					{	
						if (line.equals("S")) {nn.outputNeuron[0].setSelected(true);}
						if (line.equals("H")) {nn.outputNeuron[1].setSelected(true);}
						line = reader.readLine();
					}
					for (int i=0; i<2; i++)
					{
						nn.outputAxonT[i].setText(line);
						line = reader.readLine();
					}
				}
			
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
