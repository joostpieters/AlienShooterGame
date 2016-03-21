// Sahil Gandhi
// 4/24/12
// Game.java
// This program will allow the user to play the game Escape From Planet X. Survive all
// the single level and win. Don't get hit by the aliens, or you lose a life. Questions will be
// asked when energy is collected, so make sure your brain is ready if you want to live.
// to open, use appletviewer Game.html

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Game extends JApplet implements MouseListener
{
	private Menu menu; // these are classes, not JPanels
	//private JPanel PreGame, GameP, Htp, BackStory;
	private PreGame pg; // pregame panel
	private GamePanel gp; // game panel
	private Htp htp; // how to play panel
	private BackStory bs; // backstory panel
	private Questions qp; // question panel
	private CardLayout panels; // the layout (card)
	private Container game; // the container
	
	private int energyb, killcount; // energy counter and killcount
	private int lives, soldierx,score; // lives, score, and the xpos of soldier
	
	private JTextArea dialogue; // dialogue of soldier/alien
	private JTextArea questionarea; // question area 
	private JComboBox menubar; // menu checkbox
	private JButton textcon; // continue to the next panel/text
	
	private Image aface, sface, backg, soldier, missile, alien, bazooka, energy2, acity1, tornado, instructions; // lots and lots of images
	
	private boolean mlaunch; // launching the bullet
	private Timer energy; // timer for the energy
	private boolean energytest; // boolean for making the energy appear
	
	public Game() // setting the intial values of some variables
	{
		energyb = 10;
		lives = 5;
		mlaunch = false;
		dialogue = new JTextArea("There's no going back now. Please Click Continue to Begin the Transmission", 20, 19); // starting of the textarea
		soldierx = 0;
		energytest = false;
		killcount = 0;
		score = (killcount*10) + ((10-energyb)*100);
	}	
	public void init() // getting the images and setting the panels
	{
		aface = getImage(getDocumentBase(),"AlienFace.png"); // get pic 1
		WaitForImage(this,aface);
		
		missile = getImage(getDocumentBase(),"RealBullet.gif"); // get pic 2
		WaitForImage(this,missile);
		
		sface = getImage(getDocumentBase(),"Soldier face.png"); // get pic 3
		WaitForImage(this,sface);
		
		backg = getImage(getDocumentBase(),"DayBG.jpg"); // get pic 4
		WaitForImage(this,backg);
		
		soldier = getImage(getDocumentBase(),"RealSoldier.png"); // get pic 5
		WaitForImage(this,soldier);
		
		alien = getImage(getDocumentBase(),"Alien1Trial.gif"); // get pic 6
		WaitForImage(this,alien);

		bazooka = getImage(getDocumentBase(),"RealBazooka.png"); // get pic 7
		WaitForImage(this,bazooka);
		
		energy2 = getImage(getDocumentBase(),"Energy.jpg"); // get pic 8
		WaitForImage(this,energy2);
		
		acity1 = getImage(getDocumentBase(),"AlienCity1.png"); // get pic 8
		WaitForImage(this,energy2);

		tornado = getImage(getDocumentBase(),"Tornado.png"); // get pic 8
		WaitForImage(this,energy2); 	
		
		instructions = getImage(getDocumentBase(),"InstructionsPanel.png"); // get pic 8
		WaitForImage(this,instructions); 	
		
		game = this.getContentPane(); // setting the layout and container
		panels = new CardLayout();
		game.setLayout(panels);
		
		menu = new Menu();
		menu.setBackground(Color.blue);
		game.add(menu, "Menu Panel");
		menu.addMouseListener(this);
		
		pg = new PreGame(aface, sface);
		pg.setBackground(Color.green);
		game.add(pg, "PreGame Panel");
		pg.addMouseListener(this);
		
		htp = new Htp(instructions);
		htp.setBackground(Color.gray);
		game.add(htp, "How to Play Panel");
		htp.addMouseListener(this);
		
		bs = new BackStory();
		bs.setBackground(Color.orange);
		game.add(bs, "Back Story Panel");
		bs.addMouseListener(this);
		
		gp = new GamePanel(backg, soldier, missile, alien, bazooka, energy2, acity1, tornado);
		gp.setBackground(Color.black);
		game.add(gp, "Game Panel");
		gp.addMouseListener(this);
		
		qp = new Questions();
		qp.setBackground(Color.green);
		game.add(qp, "Question Panel");
		qp.addMouseListener(this);
		
	}
	public void WaitForImage (JApplet component, Image image)  // getting the pictures
	{
		MediaTracker tracker = new MediaTracker ( component ); // try catch for pictures
		try 
		{
			tracker.addImage(image,0);
			tracker.waitForID(0);
		}
		catch(InterruptedException e)   
		{
			e.printStackTrace();   
		}
	}
	public class Questions extends JPanel implements ActionListener
	{
		private String[] questions; // string for questions		
		private JRadioButton a, b, c, d; // the buttons (radio) -a, b, c ,d
		
		public Questions()
		{
		    this.setLayout(new GridLayout(5,1));
			ButtonGroup choices = new ButtonGroup();
			questions = new String [27]; // the string
			
			questions[0] = "What is Sine 30 degrees? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[1] = "Who is the smartest guy in the world (*Note may be a trick question)(Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[2] = " A substance with a pH of 13 is ________. (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[3] = "If log10x = -2, what is the value of x ? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[4] = "Which word is derived from the name of the nymph in Greek mythology who pined away for Narcissus until all that was left was her voice? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[5] = "The word concocting means _________. (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[6] = "The word cosmopolitan comes from the ___________. (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[7] = "Which statement about the Hindu caste system in India is accurate? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[8] = "When did King Ramses II live and rule? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[9] = "What is the chemical formula for sugar? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[10] = "What is the chemical formula for Barium Uranium Oxide? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[11] = "Ordinary table salt is sodium chloride. What is baking soda? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[12] = "The potato is a modified form (outgrowth) of _________________. (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[13] = "Prothrombin which helps in clotting of blood is released by ______________. (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[14] = "Most common disease of poultry in India is ______________. (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[15] = "Nuclear sizes are expressed in a unit named ___________. (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[16] = "What is the correct spelling of my T.A. in JAVA? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[17] = "What is the 2(Sine 90)? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[18] = "How tall is a basketball hoop? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[19] = "Which came first, the chicken or the egg? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[20] = "1+1=? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[21] = "2+2=? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[22] = "0-0=? (Answer this question to collect the energybar. Answer wrong, and you lose a life.)";
			questions[23] = "What organ produces the material stored by the gallbladder?";
			questions[24] = "What is the main function of the cerebrum?";
			questions[25] = "What does the umbilical vein do?";
			questions[26] = "What does the umbilical artery do?"; // all the questions

			questionarea = new JTextArea("You attempted to collect an energy bar. You must answer a question to continue and collect it. Good Luck", 20, 19);
			questionarea.setBounds(100, 50, 600, 300);
			questionarea.setFont(new Font ("Helvetica", Font.PLAIN, 20));
			questionarea.setEditable(false);
			questionarea.setLineWrap(true); // allowing the text to wrap
   			questionarea.setWrapStyleWord(true); // allowing the words to wrap
			this.add(questionarea);	// the text area for the question
			
			a = new JRadioButton("Choice A"); // choice a radio button
			choices.add(a);
			a.addActionListener(this);
			this.add(a);
			
			b = new JRadioButton("Choice B"); // choice b radio button
			choices.add(b);
			b.addActionListener(this);
			this.add(b);
			
			c = new JRadioButton("Choice C"); // choice c radio button
			choices.add(c);
			c.addActionListener(this);
			this.add(c);
			
			d = new JRadioButton("Choice D"); // choice d radio button
			choices.add(d);
			d.addActionListener(this);
			this.add(d);
			
		}
		public void random()
		{
			int rand = (int)(Math.random()*27); // rand int for the questions
			questionarea.setText(questions[rand]); // setting the textarea for a question
			a.setSelected(false); // making the choice false at first
			b.setSelected(false);// making the choice false at first
			c.setSelected(false);// making the choice false at first
			d.setSelected(false);// making the choice false at first
			
			switch(rand) // switching the rand variable to decide which choices should be given
			{
				case 0:
				a.setText("1/2"); // right answer
				b.setText("2");
				c.setText("root 2");
				d.setText("(root 3)/2");
				break;
				
				case 1:
				a.setText("Sahil");
				b.setText("Albert Einstein");
				c.setText("Stephen Hawking");
				d.setText("You"); // right answer
				break;
				
				case 2:
				a.setText("neutral");
				b.setText("strongly acidic");
				c.setText("strongly basic"); // right answer
				d.setText("mildly acidic");
				break;
				
				case 3:
				a.setText("100");
				b.setText("1/100"); // right answer
				c.setText("Root(1/100)"); 
				d.setText("10");
				break;
				
				case 4:
				a.setText("Vocal");
				b.setText("Echo"); // right answer
				c.setText("Larynx"); 
				d.setText("Articulate");
				break;
				
				case 5:
				a.setText("Examining");
				b.setText("Creating"); // right answer
				c.setText("Imagining"); 
				d.setText("Tasting");
				break;	
				
				case 6:
				a.setText("Lation word for Lion");
				b.setText("Greek name for the universe"); // right answer
				c.setText("Roman goddess of the earth"); 
				d.setText("Greek god Oceanus, god of water");
				break;
				
				case 7:
				a.setText("Different castes had quite similar rules for governing their behaviors.");
				b.setText("Castes were encouraged to interact with each other."); 
				c.setText("Castes were created to divide up the jobs in society."); // right answer 
				d.setText("People could move up in caste if they performed great deeds.");
				break;
				
				case 8:
				a.setText("During the 19th dynasty of the 12th century BC from 1292 B.C. to 1225 B.C."); // right answer
				b.setText("During the 14th dynasty of the 17th century B.C. from 1767 B.C. to 1700 B.C."); 
				c.setText("During the 14th dynasty of the 17th century B.C. from 1784 B.C. to 1717 B.C."); 
				d.setText("During the 14th dynasty of the 17th century B.C. from 1798 B.C. to 1731 B.C.");
				break;
				
				case 9:
				a.setText("C12 H6 O12"); 
				b.setText("C7 H12 O7"); 
				c.setText("C4 H8 O4"); 
				d.setText("C6 H12 O6"); // right answer
				break;
				
				case 10:
				a.setText("Ba U2 O7"); // right answer
				b.setText("Ba2 U4 O9"); 
				c.setText("Ba U4 O7"); 
				d.setText("Ba2 U4 O5");
				break;
				
				case 11:
				a.setText("Potassium Chloride"); 
				b.setText("Potassium Carbonate"); 
				c.setText("Potassium Hydroxide"); 
				d.setText("Sodium Bicarbonate"); // right answer
				break;
				
				case 12:
				a.setText("root"); 
				b.setText("stem"); // right answer
				c.setText("fruit"); 
				d.setText("leaf"); 
				break;
				
				case 13:
				a.setText("Lymphocytes"); 
				b.setText("Erthrocytes"); 
				c.setText("Monocytes"); 
				d.setText("Blood platelets"); // right answer
				break;
				
				case 14:
				a.setText("Fowl Pox"); 
				b.setText("Tick Fever"); 
				c.setText("Ranikhet"); // right answer
				d.setText("Coryza"); 
				break;
				
				case 15:
				a.setText("Fermi"); // right answer
				b.setText("angstorm"); 
				c.setText("newtons");
				d.setText("teslas"); 
				break;	
	
				case 16:
				a.setText("BACK TO WORK"); 
				b.setText("Seunghun Oh"); // right answer
				c.setText("Seunghyun Soh");
				d.setText("SungHyun Oh"); 
				break;
				
				case 17:
				a.setText("2"); // right answer
				b.setText("1"); 
				c.setText("root 3");
				d.setText("6 root 3"); 
				break;
				
				case 18:
				a.setText("9 feet"); 
				b.setText("12'"); 
				c.setText("10''");
				d.setText("10'"); // right answer
				break;

				case 19:
				a.setText("The Chicken"); 
				b.setText("The Egg"); 
				c.setText("A circle has no beginning"); // right answer
				d.setText("No Clue????"); 
				break;
				
				case 20:
				a.setText("No Clue"); 
				b.setText("1"); 
				c.setText("Window"); // right answer
				d.setText("None of the above"); 
				break;

				case 21:
				a.setText("Fish"); 
				b.setText("4"); 
				c.setText("None of the above"); 
				d.setText("5"); // right answer
				break;
				
				case 22:
				a.setText("0, DUH???"); // right answer
				b.setText("A face"); 
				c.setText("No Clue"); 
				d.setText("None of the above"); 
				break;			
				
				case 23:
				a.setText("The Liver"); // right answer
				b.setText("Stomach"); 
				c.setText("Pancreas"); 
				d.setText("The Gallblader"); 
				break;

				case 24:
				a.setText("Eating"); 
				b.setText("Sensory Neurons"); 
				c.setText("Playing this game."); // right answer
				d.setText("Nothing."); 
				break;

				case 25:
				a.setText("Absolutely Nothing"); 
				b.setText("Carry the deoxygenated blood away from the fetus."); 
				c.setText("No Clue"); 
				d.setText("Carry the oxygenated blood to the fetus."); // right answer
				break;

				case 26:
				a.setText("Absolutely Nothing"); 
				b.setText("Carry the deoxygenated blood away from the fetus to the placenta."); // right answer
				c.setText("No Clue"); 
				d.setText("Carry the oxygenated blood to the fetus from the placenta."); 
				break;
			} // all 26/27 question choices
		}
		public void actionPerformed (ActionEvent evt)
		{
			String command = evt.getActionCommand();
			// a very long if || if ... statement for all the choices that are true
			if(command.equals("1/2") || command.equals("You") || command.equals("strongly basic") || command.equals("Fermi")|| command.equals("Ranikhet")|| command.equals("stem")|| command.equals("Sodium Bicarbonate")|| command.equals("Ba U2 O7")|| command.equals("C6 H12 O6") || command.equals("1/100")
			|| command.equals("Blood platelets")|| command.equals("During the 19th dynasty of the 12th century BC from 1292 B.C. to 1225 B.C.")|| command.equals("Castes were created to divide up the jobs in society.")|| command.equals("Greek name for the universe")|| command.equals("Creating")|| command.equals("Echo")
			|| command.equals("Seunghun Oh")|| command.equals("2")|| command.equals("10'")|| command.equals("A circle has no beginning")|| command.equals("Window")|| command.equals("5")|| command.equals("0, DUH???") || command.equals("The Liver") || command.equals("Playing this game.") || command.equals("Carry the oxygenated blood to the fetus.") || command.equals("Carry the deoxygenated blood away from the fetus to the placenta."))
			{
				JOptionPane.showMessageDialog(null, "That's right. You collected the energy bar."); // dialogue box
				energyb--;
				panels.show(game, "Game Panel"); // back to the game panel
			}
			// a not so long else statement for the wrong choices
			else
			{
				lives--; // oh no, lose a life
				JOptionPane.showMessageDialog(null, "That's incorrect. You lost the life and didn't collect the energy bar. You now have " + lives + " lives/life."); // dialogue box
				panels.show(game, "Game Panel"); // back to game panel
			}
		}
	}
	public class GamePanel extends JPanel implements ActionListener, KeyListener
	{
		private Image back, soldiera, bomb, bad, soldierb, energy1, city1, storm; // all the images for this panel 
		
		private JLabel ebar, livebar, killcountbar, scorebar; // jlabels for various things like score, lives, energy, ec
		private Timer bulletmove; // a timer for making the bullet move
		private Timer alienmove; // a timer for maing the alien move
		private int soldiercounter, bstart, energyx, energycounter; // intergers for many different functions
		private int[] bmove; // array for bullet move
		private int[] aliencount; // array for the alien count
		private boolean astart; // starting the alien horde (MUHAHAHAHAHA!!!)
		public GamePanel(Image backg, Image soldier, Image missile, Image alien, Image bazooka, Image energy2, Image acity1, Image tornado)
		{
			bmove = new int [1]; // setting the bmove array (only 1 space, because only 1 bullet fired (may change later)
			bmove[0] = bstart + 30; // setting the starting position of the bullet
			aliencount = new int [300]; // setting the alien amount to 300 (because im cruel like that)
			for (int i = 0; i < aliencount.length; i++) // fixing the position of the alien horde
			{
				aliencount[i] = (i * 400)+900;
			}
			if(bulletmove == null) // setting bullet move timer
			{
				bulletmove = new Timer(1, new move());
			}	
			if (alienmove == null) // setting the alienmove timer
			{
				alienmove = new Timer(20, new amove());
			}
			if (energy == null) // setting the energy start timer
			{
				energy = new Timer(1000, new questions());
			}
			this.setLayout(null);
			astart = true; // lots of contructor setting of varialbles down below here in the constructor
			mlaunch = false;
			energytest = false;
			soldiera = soldier;
			back = backg;
			bomb = missile;
			bad = alien;
			soldierb = bazooka;
			energy1 = energy2;
			city1 = acity1;
			storm = tornado;
			soldiercounter = 1; 
			bstart = 0;
			energyx = 520;
			energycounter = 1;
			killcount = 0;
			ebar = new JLabel("Only " + energyb + " energy bars remaining"); // energy bar setter
			ebar.setForeground(Color.yellow);
			ebar.setBackground(Color.gray);
			ebar.setOpaque(true);
			ebar.setBounds(0, 775, 298, 25);
			this.add(ebar);
			livebar = new JLabel(" Only " + lives + " lives remaining"); // lives bar setter
			livebar.setForeground(Color.yellow);
			livebar.setBackground(Color.gray);
			livebar.setOpaque(true);
			livebar.setBounds(300, 775, 298, 25);
			this.add(livebar);
			killcountbar = new JLabel(" Killcount: " + killcount); // kill count bar setter
			killcountbar.setForeground(Color.yellow);
			killcountbar.setBackground(Color.gray);
			killcountbar.setOpaque(true);
			killcountbar.setBounds(600, 775, 200, 25);
			this.add(killcountbar);
			scorebar = new JLabel(" Score: " + score); // score bar setter (haven't they been convienently named??)
			scorebar.setForeground(Color.yellow);
			scorebar.setBackground(Color.gray);
			scorebar.setOpaque(true);
			scorebar.setBounds(0, 0, 800, 25);
			this.add(scorebar);
			this.addKeyListener(this);
		}
		public class questions implements ActionListener // the question starter (meaning the energy shower) method
		{	
			public void actionPerformed(ActionEvent evt)
			{
				energycounter++; // basically there is a counter for when the energy should pop up, if it pops up, then it is given the option for a question to appear, if and only if the energy and soldier contact
				if (energycounter % 2 == 0)
				{
					//System.out.print("hi");
					energyx = 500;
					energytest = true; // once again that variable to show the energy
				}
				else if (energycounter % 2 != 0)
				{
					//System.out.print("bye");
					energyx = -300;
				}
				repaint(); // always the repaint caller must be present
			}
		}
		public class move implements ActionListener // this is the bullet move timer method
		{
			public void actionPerformed(ActionEvent evt) 
			{
				for (int i = 0; i < bmove.length; i++) // shifts the bullet's position 2 to the right every millisecond ------>
				{
					bmove[i] += 2;
				}
				repaint(); // always the repaint caller must be present
			}
		}
		
		public class amove implements ActionListener // this is the alien move timer method
		{
			public void actionPerformed(ActionEvent evt) 
			{
				for (int i = 0; i < aliencount.length; i++) // shifts the alien horde 2 left every few milliseconds
				{
					aliencount[i] -= 2;
					astart = true;
				}
				repaint(); // always the repaint caller must be present
			}
		}	
		public void paintComponent(Graphics g) // the paint component, where the real action starts
		{
			super.paintComponent(g);
			alienmove.start(); // starting the timer
			energy.start(); // starting the timer
			ebar.setText(" Only " + energyb + " energy bars remaining"); // setting ebar text
			killcountbar.setText(" Killcount: " + killcount); // setting the killcount bar text
			livebar.setText(" Only " + lives + " lives remaining"); // setting the live bar text
			scorebar.setText(" Score: " + score); // setting the score bar text, this way all 4 are refreshed every time repaint is called
			g.drawImage(back, 0, 0, 800, 775, this); // the background image, yes it kind of wastes space, but whatever you know
			g.drawImage(soldierb, 5, 690, 75, 100, this);
			g.drawImage(city1, 5, 400, 75, 100, this);
			g.drawImage(storm, 600, 500, 75, 100, this);
			g.drawImage(storm, 300, 500, 75, 100, this);
			soldierx = 100 + 30*soldiercounter;
			g.drawImage(soldiera, soldierx, 660, 100, 125, this);
			if (energytest == true)
			{
				g.drawImage(energy1,energyx, 725, 50, 50, this); 
			}
			int i = 0;
			for(i = 0; i < 300; i++)
			if (astart == true)
			{
				g.drawImage(bad, aliencount[i], 675, 100, 100, this);		
			}
			
			if (mlaunch == true)
			{
				g.drawImage(bomb, bmove[0], 685, 100, 50, this); 
			}
			int bulletend = bmove[0] + 110;
			int soldierend = soldierx + 100;
			score = (killcount*10) + ((10-energyb)*100);		
			System.out.println(bmove[0]);
			for (int k = 0; k < aliencount.length; k++)
			{	
				if (bulletend == aliencount[k]) //put this in a for loop
				{
					mlaunch = false;
					astart = false;
					bulletmove.stop();
					bmove[0] = bstart + 30;
					aliencount[k] = -100;
					killcount++;
					killcountbar.setText("Killcount: " + killcount);
				}
				if (soldierend == aliencount[k])
				{
					astart = false;
					aliencount[k] = -100;
					lives --;
					livebar.setText("Only " + lives + " lives remaining");
					killcount++;
					killcountbar.setText("Killcount: " + killcount);
				} 
				if (lives == 0)
				{
					mlaunch = false;
					g.setColor(Color.black);
					g.setFont(new Font ("Helvetica", Font.PLAIN, 30));			
					g.drawString("GAME OVER, YOU DIED LIKE A NOOB", 100, 100);
					g.drawString("You scored  " + score + " points.", 240, 150);
					g.drawString("YOU LOSE!", 300, 200);
					bulletmove.stop();	
					alienmove.stop();	
					energy.stop();
				}
			}
			if (killcount == 300)
			{
				bulletmove.stop();
				alienmove.stop();
				energy.stop();
				g.setColor(Color.black);
				g.setFont(new Font ("Helvetica", Font.PLAIN, 30));			
				g.drawString("CONGRATULATIONS, YOU KILLED THE ALIEN HORDE", 20, 100);
				g.drawString("You scored  " + score + " points.", 240, 150);
				g.drawString("YOU WIN!", 300, 200);	
			}
			if (energyb == 0)
			{
				bulletmove.stop();
				alienmove.stop();
				energy.stop();
				g.setColor(Color.black);
				g.setFont(new Font ("Helvetica", Font.PLAIN, 30));			
				g.drawString("CONGRATULATIONS YOU REPAIRED YOUR TELEPORTER", 0, 100);
				g.drawString("YOU WIN!", 300, 200);
				g.drawString("You scored  " + score + " points.", 240, 150);	
			}
			
			int soldenergycontact = soldierx + 100;
			int energyend = energyx + 50;
			//System.out.println(soldenergycontact + "  " + energyx);
			if (soldenergycontact >= energyx && soldierx <= energyend)
			{
				panels.show(game, "Question Panel");
				alienmove.stop();
				bmove[0] = bstart + 30;
				mlaunch = false;
				bulletmove.stop();
				energy.stop();
				qp.random();
				energyx = -200;
			}
			else if (soldenergycontact != energyx)
			{
				alienmove.start();
				energy.start();
			}
			if (energyb > 5 && energyb < 8)
			{
				alienmove.setDelay(15);
			}
			else if (energyb > 1 && energyb < 5)
			{
				alienmove.setDelay(12);
			}
			else if (energyb == 1)
			{
				alienmove.setDelay(10);
			}
			this.requestFocus();
			repaint();
		}
		public void keyPressed (KeyEvent evt)
		{
		   	int value = evt.getKeyCode();
		   	if(value == KeyEvent.VK_RIGHT && soldiercounter < 20) // right
		   	{
		   		soldiercounter++;
		   		//System.out.print(x);
			}
			else if(value == KeyEvent.VK_LEFT && soldiercounter > -1)
			{
				soldiercounter--;
				//System.out.print(x);
			}
			
			if (value == KeyEvent.VK_SPACE)
			{
				mlaunch = true;	
				bulletmove.start();
			}
			repaint();
		}
		public void keyTyped (KeyEvent evt)
		{
			
		}
		public void keyReleased (KeyEvent evt)
		{
			
		}
		public void actionPerformed (ActionEvent evt)
		{
			
		}
	}
	public class Menu extends JPanel implements ActionListener
	{
		private JButton menucon;
		private JComboBox menuc;
		public Menu()
		{
			this.setLayout(null);
			menucon = new JButton("Click to continue to the panel selected");
			menucon.setBounds(220, 700, 300, 25);		
			menucon.addActionListener(this);
			this.add(menucon);
			
			menuc = new JComboBox();
			menuc.setBounds(300, 625, 150, 50);
			menuc.addItem("WELCOME");
			menuc.addItem("Play Game");
			menuc.addItem("Instructions");
			menuc.addItem("About Game");	
			this.add(menuc);
			
			JLabel message1 = new JLabel("",JLabel.CENTER);
			message1.setBounds(50, 50, 700, 100);
			message1.setText("ESCAPE FROM ");
			message1.setForeground(Color.red);
			message1.setBackground(Color.blue);
			message1.setFont(new Font("Serif",Font.BOLD,80));
			message1.setOpaque(false);
			this.add(message1);
			
			JLabel message3 = new JLabel("",JLabel.CENTER);
			message3.setBounds(140, 80, 500, 300);
			message3.setText("PLANET");
			message3.setForeground(Color.red);
			message3.setBackground(Color.blue);
			message3.setFont(new Font("Serif",Font.BOLD,110));
			message3.setOpaque(false);
			this.add(message3);
			
			JLabel message2 = new JLabel("",JLabel.CENTER);
			message2.setBounds(75, 150, 600, 600);
			message2.setText("X");
			message2.setForeground(Color.red);
			message2.setBackground(Color.blue);
			message2.setFont(new Font("Serif",Font.BOLD,300));
			message2.setOpaque(false);
			this.add(message2);
			
			JLabel message4 = new JLabel("",JLabel.CENTER);
			message4.setBounds(75, 740, 600, 50);
			message4.setText("How long can YOU survive");
			message4.setForeground(Color.yellow);
			message4.setBackground(Color.blue);
			message4.setFont(new Font("Serif",Font.BOLD,30));
			message4.setOpaque(false);
			this.add(message4);
		}
		public void actionPerformed (ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equals("Click to continue to the panel selected"));
			{
				panels.next(game);
			}
			int cc = menuc.getSelectedIndex();
			switch(cc)
			{
				case 1: panels.show(game, "Info Panel");
						break;
				case 2: panels.show(game, "How to Play Panel");
						break;
				case 3: panels.show(game, "Back Story Panel");
						break;
				default: panels.show(game, "Menu Panel");
						 break;
			}
		}
	}
	public class PreGame extends JPanel implements ActionListener
	{	
		private JButton cont;
		private int textcounter;
		private Image image1, image2;
		
		public PreGame(Image aface, Image sface)
		{
			this.setLayout(null);
			cont = new JButton("Continue");
			cont.setBounds(250, 600, 200, 50);
			cont.addActionListener(this);
			this.add(cont);
			
			dialogue.setBounds(100, 300, 500, 400);
			dialogue.setEditable(false);
			dialogue.setLineWrap(true);
			dialogue.setWrapStyleWord(true);
			dialogue.setFont(new Font ("Helvetica", Font.BOLD, 28));
			dialogue.setForeground(Color.blue);
			this.add(dialogue);
			
			image1 = aface;
			image2 = sface;
			textcounter = 0;
		}
		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			switch (textcounter)
			{
				case 1: g.drawImage(image2, 30, 30, 250, 250, this);
						break;
				case 2: g.drawImage (image1, 500, 30, 250, 250, this);
						break;
				case 3: g.drawImage(image2, 30, 30, 250, 250, this);
						break;
				default:break;
			}
		}
		public void actionPerformed (ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equals("Continue"));
			{
				textcounter++;
				panels.show(game, "PreGame Panel");
			}	
			if (textcounter == 1)
			{
				dialogue.setText("Dang! We have been left here alone. I don't think the teleporter works as well. Looks like we gotta collect the energy bars here to fix up our teleporter.");
			}
			else if (textcounter == 2)
			{
				dialogue.setText("HAHAHAHA!!! You are stranded. Prepare to be eaten by our horde of aliens. Aliens, begin the strike as soon as possible. Now back to those pesky humans. You both WILL DIE!!!!!!");
			}
			else if (textcounter == 3) 	
			{
				dialogue.setText("Dang! Now there's aliens. I think we gotta defend ourself Buffy. Forget the minerals, it's gonna take all of our effort to keep these aliens at bay till our teleporter is fixed. \n\nEnd Transmission ...");
			}
			else if (textcounter >= 4)
			{
				panels.show(game, "Game Panel");
			}
			repaint();
		}
	}
	public class BackStory extends JPanel implements ActionListener
	{
		private JTextArea bsbox;
		private JButton btm;
		public BackStory()
		{
			this.setLayout(null);
			bsbox = new JTextArea("Welcome Soldier. You have been assigned a mission to look \n\nfor minerals like gold and silver, because the Earth is running \n\nout of them. To assist you, we have provided you with \n\nanother soldier who has a bazooka, as well as a teleporter. \n\nGood Luck, and get us some minerals.\n\n\t ~OVER AND OUT~", 19, 20);
			bsbox.setBounds(100, 100, 600, 400);
			bsbox.setFont(new Font ("Helvetica", Font.PLAIN, 20));	
			bsbox.setForeground(Color.blue);
			bsbox.setLineWrap(true); // allowing the text to wrap
   			bsbox.setWrapStyleWord(true); // allowing the words to wrap
   			bsbox.setEditable(false); // editable = false
   			this.add(bsbox);
   			
			btm = new JButton("Click to go back to the main menu");
			btm.setBounds(220, 550, 300, 25);		
			btm.addActionListener(this);
			this.add(btm);			
			
			JLabel message4 = new JLabel("",JLabel.CENTER);
			message4.setBounds(85, 650, 600, 50);
			message4.setText("GOOD LUCK! You're gonna need it ... ");
			message4.setForeground(Color.red);
			message4.setBackground(Color.blue);
			message4.setFont(new Font("Serif",Font.BOLD,30));
			message4.setOpaque(false);
			this.add(message4);
   		}
		public void actionPerformed (ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equals("Click to go back to the main menu"));
			{
				panels.show(game, "Menu Panel");
			}
		}
	}
	public class Htp extends JPanel implements ActionListener
	{
		private JButton btm;
		private JTextArea bsbox;
		private Image ipanel;
		public Htp(Image instructions)
		{
			this.setLayout(null);
			btm = new JButton("Click to go back to the main menu");
			btm.setBounds(220, 725, 300, 25);		
			btm.addActionListener(this);
			this.add(btm);			
			ipanel = instructions;
			/*bsbox = new JTextArea("This game is extremely simple to play. The main controls are: \t\t Key \t  What it Does \n ------------------------------------------------- \t\t -> \t Move Soldier left \t\t <- \t Move Soldier right \t\t SpaceBar \t Shoot Bazooka* \t\t Mouse \t Click when it prompts you. \n\nThere are questions asked when you attempt to get an energy bar. 10 points awarded for every alien killed, and 100 points for every energybar collected. Now, survive this game, and prove that you earned your medals. \n\n*Note: The bazooka soldier is still a rookie, so he may miss sometimes. Don't worry, and try to fire again.", 19, 20);
			bsbox.setBounds(100, 100, 600, 400);
			bsbox.setFont(new Font ("Helvetica", Font.PLAIN, 20));	
			bsbox.setForeground(Color.blue);
			bsbox.setLineWrap(true); // allowing the text to wrap
   			bsbox.setWrapStyleWord(true); // allowing the words to wrap
   			bsbox.setEditable(false); // editable = false
   			this.add(bsbox);*/
		}
		public void paintComponent(Graphics h) // the paint component, where the real action starts
		{
			super.paintComponent(h);
			h.drawImage(ipanel, 0, 0, 800, 800, this);
			this.requestFocus();
			repaint();
		}
		public void actionPerformed (ActionEvent evt)
		{
			String command = evt.getActionCommand();
			if (command.equals("Click to go back to the main menu"));
			{
				panels.show(game, "Menu Panel");
			}
		}
	}
	
	public void mousePressed(MouseEvent e)
	{}
	public void mouseClicked(MouseEvent e)
	{}
	public void mouseReleased(MouseEvent e)
	{}
	public void mouseEntered(MouseEvent e)
	{}
	public void mouseExited(MouseEvent e)
	{}	
}	