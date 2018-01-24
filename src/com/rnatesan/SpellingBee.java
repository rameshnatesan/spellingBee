package com.rnatesan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.speech.Engine;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class SpellingBee
{
	private JButton next;
	private JPanel jPanel;
	private JTextField textField1;
	private JButton checkWord;
	private List<String> words = Arrays.asList("Clambering","craggy",
		"icelandic","shilling","halter",
		"enormous","zone",
		"cypress","chimpanzee",
		"world");

	private int i = -1;
	private int a = 0;

	private SpellingBee()
	{
		next.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				++i;
				if(i==words.size()){
					return;
				}
				try
				{
					// set property as Kevin Dictionary
					System.setProperty(
						"freetts.voices",
						"com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

					// Register Engine
					Central.registerEngineCentral
						("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

					// Create a Synthesizer
					Synthesizer synthesizer =
						Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));

					// Allocate synthesizer
					synthesizer.allocate();

					// Resume Synthesizer
					synthesizer.resume();

					// speaks the given text until queue is empty.
					synthesizer.speakPlainText(words.get(i), null);
					synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);

					// Deallocate the Synthesizer.
//					synthesizer.deallocate();
				}

				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		checkWord.addActionListener(new ActionListener()
		{
			@Override public void actionPerformed(ActionEvent e)
			{
				String msg = "";

					if (textField1.getText().equalsIgnoreCase(words.get(i)))
					{
						msg = "Right answer!!";
					}
					else
					{
						msg = "Wrong answer, please try again";
						++a;
					}
					JOptionPane.showMessageDialog(null, msg);
				}

		});
	}

	public static void main(String[] args)
	{

		JFrame jFrame = new JFrame("SpellingBee");
		jFrame.setContentPane(new SpellingBee().jPanel);
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jFrame.pack();
		jFrame.setVisible(true);
	}
}
