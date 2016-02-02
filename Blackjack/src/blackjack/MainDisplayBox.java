package blackjack;  

import java.awt.Dimension;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.WindowAdapter;  
import java.awt.event.WindowEvent;  
import java.util.Random;  

import javax.swing.Box;  
import javax.swing.BoxLayout;  
import javax.swing.JApplet;  
import javax.swing.JButton;  
import javax.swing.JCheckBoxMenuItem;  
import javax.swing.JFrame;  
import javax.swing.JLabel;  
import javax.swing.JMenu;  
import javax.swing.JMenuBar;  
import javax.swing.JMenuItem;  
import javax.swing.JPanel;  

public class MainDisplayBox
extends JApplet
{
	
	private static final long serialVersionUID = 1L;  

	JFrame f;  
	Player player;  
	Dealer dealer;  
	Shuffler cardShoe;  
	WindowPanel imagePanel;  
	Random randomGenerator;  
	JButton raiseBtn;  
	JButton lowerBtn;  
	JButton dealBtn;  
	JButton hitBtn;  
	JButton standBtn;  
	JButton doubleDownBtn;  
	JButton splitBtn;  
	JButton exitBtn;  
	JLabel adviceLabel;  
	JLabel moneyLabel;  
	JPanel buttons;  
	JMenuBar menuBar;  
	JMenu menu;  
	JMenu submenu;  
	JMenuItem menuItem;  
	JCheckBoxMenuItem standardMenuItem;  
	JCheckBoxMenuItem adviceMenuItem;  
	JMenuItem autoMenuItem;  
	int gameCount;  
	boolean adviceMode;  
	boolean autoMode;  
	boolean gameOverFlag;  

	public void start()  
	{
		init()  ;   // Initializes the ImageDisplayer object
		buildWindow()  ;   // creates a new frame with all elements
	}  

	public void init()  
	{
		this.cardShoe = new Shuffler(4)  ;  
		this.player = new Player(4)  ;  
		this.dealer = new Dealer()  ;  
		this.adviceMode = false;  
		this.autoMode = false;  
		this.gameOverFlag = true;  
	}  

	public void buildWindow()  
	{
		this.imagePanel = new WindowPanel(this.player.getHand()  , this.dealer.getHand()  )  ;  
		this.f = new JFrame("A.I. Blackjack with MDP")  ;  
		this.buttons = new JPanel()  ;  
		this.buttons.setLayout(new BoxLayout(this.buttons, 0)  )  ;  

		this.f.addWindowListener(new WindowAdapter()  
		{
			public void windowClosing(WindowEvent paramAnonymousWindowEvent)  
			{
				System.exit(0)  ;  
			}  
		}  )  ;  
		this.menuBar = new JMenuBar()  ;  
		this.f.setJMenuBar(this.menuBar)  ;  

		this.menu = new JMenu("File")  ;  
		this.menu.setMnemonic(70)  ;  
		this.menuBar.add(this.menu)  ;  

		this.menuItem = new JMenuItem("Quit", 81)  ;  
		this.menu.add(this.menuItem)  ;  

		this.menu = new JMenu("Mode")  ;  
		this.menu.setMnemonic(77)  ;  
		this.menuBar.add(this.menu)  ;  

		this.standardMenuItem = new JCheckBoxMenuItem("Standard")  ;  
		this.standardMenuItem.setMnemonic(83)  ;  
		this.menu.add(this.standardMenuItem)  ;  
		this.standardMenuItem.setState(true)  ;  
		this.adviceMenuItem = new JCheckBoxMenuItem("Advice")  ;  
		this.adviceMenuItem.setMnemonic(86)  ;  
		this.menu.add(this.adviceMenuItem)  ;  
		
		this.standardMenuItem.addActionListener(new MainDisplayBox.StandardMenuListener(this)  )  ;  
		this.adviceMenuItem.addActionListener(new MainDisplayBox.AdviceMenuListener(this)  )  ;  
		
		this.raiseBtn = new JButton("Raise Bet")  ;  
		this.lowerBtn = new JButton("Lower Bet")  ;  
		this.dealBtn = new JButton("Deal")  ;  
		this.hitBtn = new JButton("Hit")  ;  
		this.standBtn = new JButton("Stand")  ;  
		this.doubleDownBtn = new JButton("Double Down")  ;  
		this.splitBtn = new JButton("Split")  ;  
		this.exitBtn = new JButton("Exit")  ;  
		this.adviceLabel = new JLabel(" ")  ;  
		this.moneyLabel = new JLabel("Money: $400")  ;  

		this.hitBtn.setEnabled(false)  ;  
		this.standBtn.setEnabled(false)  ;  
		this.splitBtn.setEnabled(false)  ;  
		this.doubleDownBtn.setEnabled(false)  ;  

		this.raiseBtn.addActionListener(new MainDisplayBox.raiseBtnListener(this)  )  ;  
		this.lowerBtn.addActionListener(new MainDisplayBox.lowerBtnListener(this)  )  ;  
		this.dealBtn.addActionListener(new MainDisplayBox.dealBtnListener(this)  )  ;  
		this.hitBtn.addActionListener(new MainDisplayBox.hitBtnListener(this)  )  ;  
		this.standBtn.addActionListener(new MainDisplayBox.standBtnListener(this)  )  ;  
		this.doubleDownBtn.addActionListener(new MainDisplayBox.doubleDownBtnListener(this)  )  ;  
		this.splitBtn.addActionListener(new MainDisplayBox.splitBtnListener(this)  )  ;  
		this.exitBtn.addActionListener(new MainDisplayBox.exitBtnListener()  )  ;  

		this.buttons.add(this.raiseBtn)  ;  
		this.buttons.add(this.lowerBtn)  ;  
		this.buttons.add(this.dealBtn)  ;  
		this.buttons.add(this.hitBtn)  ;  
		this.buttons.add(this.standBtn)  ;  
		this.buttons.add(this.doubleDownBtn)  ;  
		this.buttons.add(this.splitBtn)  ;  
		this.buttons.add(Box.createRigidArea(new Dimension(20, 0)  )  )  ;  
		this.buttons.add(this.exitBtn)  ;  
		this.buttons.add(Box.createRigidArea(new Dimension(20, 0)  )  )  ;  
		this.buttons.add(this.moneyLabel)  ;  
		this.buttons.add(Box.createRigidArea(new Dimension(20, 0)  )  )  ;  
		this.buttons.add(this.adviceLabel)  ;  

		this.f.getContentPane()  .add(this.imagePanel, "Center")  ;  
		this.f.getContentPane()  .add(this.buttons, "South")  ;  
		this.f.setSize(new Dimension(800, 600)  )  ;  
		this.f.setVisible(true)  ;  
	}  

	public class StandardMenuListener
	implements ActionListener
	{
		MainDisplayBox display;  

		StandardMenuListener(MainDisplayBox paramImageDisplayer)  
		{
			this.display = paramImageDisplayer;  
		}  

		public void actionPerformed(ActionEvent paramActionEvent)  
		{
			this.display.setMode(1)  ;  
		}  
	}  

	public class AdviceMenuListener
	implements ActionListener
	{
		MainDisplayBox display;  

		AdviceMenuListener(MainDisplayBox paramImageDisplayer)  
		{
			this.display = paramImageDisplayer;  
		}  

		public void actionPerformed(ActionEvent paramActionEvent)  
		{
			this.display.setMode(2)  ;  
		}  
	}  

	public class raiseBtnListener
	implements ActionListener
	{
		MainDisplayBox display;  

		raiseBtnListener(MainDisplayBox paramImageDisplayer)  
		{
			this.display = paramImageDisplayer;  
		}  

		public void actionPerformed(ActionEvent paramActionEvent)  
		{
			this.display.changeBet(10)  ;  
		}  
	}  

	public class lowerBtnListener
	implements ActionListener
	{
		MainDisplayBox display;  

		lowerBtnListener(MainDisplayBox paramImageDisplayer)  
		{
			this.display = paramImageDisplayer;  
		}  

		public void actionPerformed(ActionEvent paramActionEvent)  
		{
			this.display.changeBet(-10)  ;  
		}  
	}  

	public class dealBtnListener
	implements ActionListener
	{
		MainDisplayBox display;  

		dealBtnListener(MainDisplayBox paramImageDisplayer)  
		{
			this.display = paramImageDisplayer;  
		}  

		public void actionPerformed(ActionEvent paramActionEvent)  
		{
			this.display.dealHand()  ;  
		}  
	}  

	public class hitBtnListener
	implements ActionListener
	{
		MainDisplayBox display;  

		hitBtnListener(MainDisplayBox paramImageDisplayer)  
		{

			this.display = paramImageDisplayer;  
		}  

		public void actionPerformed(ActionEvent paramActionEvent)  
		{
			this.display.hitit(false)  ;  
		}  
	}  

	public class standBtnListener
	implements ActionListener
	{
		MainDisplayBox display;  

		standBtnListener(MainDisplayBox paramImageDisplayer)  
		{
			this.display = paramImageDisplayer;  
		}  

		public void actionPerformed(ActionEvent paramActionEvent)  
		{
			this.display.playerStands()  ;  
		}  
	}  

	public class doubleDownBtnListener
	implements ActionListener
	{
		MainDisplayBox display;  

		doubleDownBtnListener(MainDisplayBox paramImageDisplayer)  
		{
			this.display = paramImageDisplayer;  
		}  

		public void actionPerformed(ActionEvent paramActionEvent)  
		{
			this.display.doubleBet()  ;  
			this.display.hitit(true)  ;  
		}  
	}  

	public class splitBtnListener
	implements ActionListener
	{
		MainDisplayBox display;  

		splitBtnListener(MainDisplayBox paramImageDisplayer)  
		{
			this.display = paramImageDisplayer;  
		}  

		public void actionPerformed(ActionEvent paramActionEvent)  
		{
			this.display.splitHand()  ;  
		}  
	}  

	public class exitBtnListener
	implements ActionListener
	{
		public exitBtnListener()   {}  

		public void actionPerformed(ActionEvent paramActionEvent)  
		{
			System.exit(0)  ;  
		}  
	}  

	public void setMode(int var)  
	{
		this.standardMenuItem.setState(var == 1)  ;  
		this.adviceMenuItem.setState(var == 2)  ;  
		this.adviceMode = this.adviceMenuItem.getState()  ;  
		if (this.adviceMode)   {
			try
			{
				if (this.gameOverFlag)   {
					this.adviceLabel.setText(this.player.bettingAdvice()  )  ;  

				}   else {
					this.adviceLabel.setText(this.player.advice(this.dealer.getValue()  )  )  ;  
				}  
			}  
			catch (Exception localException)  
			{
				localException.printStackTrace()  ;  
				System.out.println("Error in advice in setMode function")  ;  
			}  
		}   else {
			this.adviceLabel.setText("")  ;  
		}  
	}  

	public void dealHand()  
	{
		this.gameOverFlag = false;  
		if (this.cardShoe.lowOnCards()  )  
		{
			this.cardShoe.shuffleNew()  ;  
			this.player.resetCount()  ;  
		}  
		this.player.newHand(this.cardShoe.dealCard()  , this.cardShoe.dealCard()  )  ;  
		this.player.observeValue(this.dealer.newHand(this.cardShoe.dealCard()  , this.cardShoe.dealCard()  )  )  ;  
		if (this.dealer.hasBlackjack()  )  
		{
			this.player.observeValue(this.dealer.showFirstCard()  )  ;  
			gameOver()  ;  
			return;  
		}  
		this.imagePanel.newGame()  ;  
		this.moneyLabel.setText("money: $" + this.player.getMoney()  )  ;  
		this.imagePanel.addPicture(this.player.getHand()  , this.dealer.getHand()  )  ;  
		this.dealBtn.setEnabled(false)  ;  
		this.raiseBtn.setEnabled(false)  ;  
		this.lowerBtn.setEnabled(false)  ;  
		this.hitBtn.setEnabled(true)  ;  
		this.standBtn.setEnabled(true)  ;  
		this.doubleDownBtn.setEnabled(true)  ;  
		this.splitBtn.setEnabled(this.player.canSplit()  )  ;  

		playersTurn()  ;  
	}  

	public void splitHand()  
	{
		Hand localHand1 = this.player.splitHand(this.cardShoe.dealCard()  , this.cardShoe.dealCard()  )  ;  
		Hand localHand2 = this.player.getHand()  ;  

		this.imagePanel.splitHand(localHand2, localHand1)  ;  
		this.splitBtn.setEnabled(this.player.canSplit()  )  ;  

		playersTurn()  ;  
	}  

	public void changeBet(int var)  
	{
		int i = this.player.getBet()  ;  
		if (i + var > 50)   {
			return;  
		}  
		if (i + var < 10)   {
			return;  
		}  
		this.imagePanel.setBet(this.player.changeBet(var)   / 10)  ;  
	}  

	public void doubleBet()  
	{
		this.player.doubleBet()  ;  
		this.imagePanel.doubleBet()  ;  
	}  

	public void dealersTurn()  
	{
		this.adviceLabel.setText("")  ;  
		this.standBtn.setEnabled(false)  ;  
		this.hitBtn.setEnabled(false)  ;  
		this.doubleDownBtn.setEnabled(false)  ;  
		this.splitBtn.setEnabled(false)  ;  

		this.player.observeValue(this.dealer.showFirstCard()  )  ;  
		if (this.player.hasHand()  )   {
			while (this.dealer.getValue()   < 9)  
			{
				this.player.observeValue(this.dealer.addCard(this.cardShoe.dealCard()  )  )  ;  
				this.imagePanel.addPicture(this.player.getHand()  , this.dealer.getHand()  )  ;  
			}  
		}  
		gameOver()  ;  
	}  

	public void playersTurn()  
	{
		if ((this.player.getValue()   > 15)   || (this.player.hasBlackjack()  )  )  
		{
			if (this.player.nextHand()  )  
			{
				this.imagePanel.nextHand()  ;  
				this.doubleDownBtn.setEnabled(true)  ;  
				this.splitBtn.setEnabled(this.player.canSplit()  )  ;  
				try
				{
					if (this.adviceMode)   {
						this.adviceLabel.setText(this.player.advice(this.dealer.getValue()  )  )  ;  
					}  
				}  
				catch (Exception localException1)  
				{
					localException1.printStackTrace()  ;  
					System.out.println("Error in advice in if of playersTurn function")  ;  
				}  
			}  
			else
			{
				dealersTurn()  ;  
			}  
		}  
		else {
			try
			{
				if (this.adviceMode)   {
					this.adviceLabel.setText(this.player.advice(this.dealer.getValue()  )  )  ;  
				}  
			}  
			catch (Exception localException2)  
			{
				localException2.printStackTrace()  ;  
				System.out.println("Error in advice in else of playerTurn function")  ;  
			}  
		}  
	}  

	public void hitit(boolean paramBoolean)  
	{
		if (this.adviceMode)   {
			this.adviceLabel.setText("")  ;  
		}  
		this.player.addCard(this.cardShoe.dealCard()  )  ;  

		this.imagePanel.addPicture(this.player.getHand()  , this.dealer.getHand()  )  ;  
		this.doubleDownBtn.setEnabled(false)  ;  
		this.splitBtn.setEnabled(false)  ;  
		if (paramBoolean)   {
			playerStands()  ;  
		}   else {
			playersTurn()  ;  
		}  
	}  

	public void playerStands()  
	{
		if (this.player.nextHand()  )  
		{
			this.imagePanel.nextHand()  ;  
			this.imagePanel.addPicture(this.player.getHand()  , this.dealer.getHand()  )  ;  
			this.doubleDownBtn.setEnabled(true)  ;  
			this.splitBtn.setEnabled(this.player.canSplit()  )  ;  
			playersTurn()  ;  
		}  
		else
		{
			dealersTurn()  ;  
		}  
	}  

	public void gameOver()  
	{
		this.gameOverFlag = true;  
		this.player.settleBets(this.dealer.getValue()  )  ;  

		this.imagePanel.addPicture(this.player.getHand()  , this.dealer.getHand()  )  ;  
		this.imagePanel.gameOver()  ;  
		this.moneyLabel.setText("money: $" + this.player.getMoney()  )  ;  
		try
		{
			if (this.adviceMode)   {
				this.adviceLabel.setText(this.player.bettingAdvice()  )  ;  
			}  
		}  
		catch (Exception localException)  
		{
			System.out.println("Error in advice localException")  ;  
		}  
		this.dealBtn.setEnabled(true)  ;  
		this.raiseBtn.setEnabled(true)  ;  
		this.lowerBtn.setEnabled(true)  ;  
		this.hitBtn.setEnabled(false)  ;  
		this.standBtn.setEnabled(false)  ;  
		
	}  
}  
