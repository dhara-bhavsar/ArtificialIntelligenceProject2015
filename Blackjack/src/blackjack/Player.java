package blackjack;  

import java.util.Iterator;  
import java.util.Random;  

public class Player
{  
	
	Hand[] handCards;  
	Counter cardCount;  
	int money;  
	int currentHand;  
	int ante;  
	int numberOfHands;  
	boolean hasAHand;  
	int[] handCardsInfo;  
	double[][] weightNN;  
	double[] weightMin;  
	double[] weightRange;  

	public Player(int var)  
	{  
		this.handCards = new Hand[4];  
		this.handCards[0] = new Hand()  ;  
		this.cardCount = new Counter(var)  ;  
		this.money = 400;  
		this.currentHand = 0;  
		this.ante = 10;  
		this.numberOfHands = 1;  
		this.hasAHand = false;  
		this.handCardsInfo = new int[4];  
		this.handCardsInfo[0] = 0;  
		this.handCardsInfo[1] = 0;  
		this.handCardsInfo[2] = 0;  
		this.handCardsInfo[3] = 0;  
	}  

	public Hand getHand()  
	{  
		return this.handCards[this.currentHand];  
	}  

	public int getValue()  
	{  
		return this.handCards[this.currentHand].getValue()  ;  
	}  

	public int getMoney()  
	{  
		return this.money;  
	}  

	public int changeBet(int var)  
	{  
		this.ante = this.ante + var;  
		return this.ante;  
	}  

	public int doubleBet()  
	{  
		return this.handCards[this.currentHand].changeBet(this.ante)  ;  
	}  

	public int getBet()  
	{  
		return this.ante;  
	}  

	public int[] getHandInfo()  
	{  
		return this.handCardsInfo;  
	}  

	private double squish(double paramDouble)  
	{  
		return 1.0D / (1.0D + Math.exp(-paramDouble)  )  ;  
	}  

	public boolean hasBlackjack()  
	{  
		return this.handCards[this.currentHand].isBlackjack()  ;  
	}  

	public int isSoft()  
	{  
		return this.handCards[this.currentHand].isSoft()  ;  
	}  

	public boolean canSplit()  
	{  
		return (this.handCards[this.currentHand].issplitHand()  )   && (this.numberOfHands < 4)  ;  
	}  

	public void addCard(CardValueType varCard)  
	{  
		observeValue(varCard.getValue()  )  ;  
		this.handCards[this.currentHand].addCard(varCard)  ;  
	}  

	public void observeValue(int var)  
	{  
		this.cardCount.observeValue(var)  ;  
	}  

	public void resetCount()  
	{  
		this.cardCount.resetCount()  ;  
	}  

	public String advice(int var)  
	{  
		//System.out.println(this.cardCount.getRemainingCards()  )  ;  
		return playerDecision(this.handCards[this.currentHand].getValue()  , this.handCards[this.currentHand].isSoft()  , var, this.handCards[this.currentHand].isTwoCards()  , (this.handCards[this.currentHand].issplitHand()  )   && (this.numberOfHands != 4)  , this.cardCount)  ;  
	}  
	
	public boolean hasHand()  
	{  
		return this.hasAHand;  
	}  

	public void newHand(CardValueType varCard1, CardValueType varCard2)  
	{  
		observeValue(varCard1.getValue()  )  ;  
		observeValue(varCard2.getValue()  )  ;  
		this.handCards[0] = new Hand(varCard1, varCard2)  ;  
		this.handCards[0].setBet(this.ante)  ;  
		this.currentHand = 0;  
		this.numberOfHands = 1;  
		this.hasAHand = false;  
	}  

	public boolean nextHand()  
	{  
		if ((this.handCards[this.currentHand].getValue()   <= 15)   && (!this.handCards[this.currentHand].isBlackjack()  )  )   {  
			this.hasAHand = true;  
		}  
		if (this.currentHand + 1 == this.numberOfHands)   {  
			return false;  
		}  
		this.currentHand = this.currentHand + 1;  

		return true;  
	}  

	public double dealerRecursive(int var1, int var2, int var3, int var4, boolean paramBoolean, Counter varCardCount)  
	{  
		if (var1 > 15)  
		{  
			if (var2 == 0)   {  
				return -1.0D;  
			}  
			var1 = var1 - 10;  
			var2--;  
		}  
		if (var3 > 15)  
		{  
			if (var4 == 0)   {  
				return 1.0D;  
			}  
			var3 = var3 - 10;  
			var4--;  
		}  
		if (var3 >= 9)  
		{  
			if (var3 > var1)   {  
				return -1.0D;  
			}  
			if (var3 < var1)   {  
				return 1.0D;  
			}  
			if (var3 == var1)   {  
				return 0.0D;  
			}  
		}  
		double d = 0.0D;  
		int i;  
		if ((paramBoolean)   && (var3 == 10)   && (var3 == 8)  )  
		{  
			for (i = 2;   i < 8;   i++)   {  
				if (varCardCount.chance(i)   > 0.0D)  
				{  
					varCardCount.observeValue(i)  ;  
					d = d + varCardCount.chanceDealerDown(i, var3)   * dealerRecursive(var1, var2, var3 + i, var4, false, varCardCount)  ;  
					varCardCount.unobserveValue(i)  ;  
				}  
			}  
			if (varCardCount.chance(8)   > 0.0D)  
			{  
				varCardCount.observeValue(8)  ;  
				d = d + varCardCount.chanceDealerDown(8, var3)   * dealerRecursive(var1, var2, var3 + 8, var4 + 1, false, varCardCount)  ;  
				varCardCount.unobserveValue(8)  ;  
			}  
		}  
		else
		{  
			for (i = 2;   i < 8;   i++)   {  
				if (varCardCount.chance(i)   > 0.0D)  
				{  
					varCardCount.observeValue(i)  ;  
					d = d + varCardCount.chance(i)   * dealerRecursive(var1, var2, var3 + i, var4, false, varCardCount)  ;  

					varCardCount.unobserveValue(i)  ;  
				}  
			}  
			if (varCardCount.chance(8)   > 0.0D)  
			{  
				varCardCount.observeValue(8)  ;  
				d = d + varCardCount.chance(8)   * dealerRecursive(var1, var2, var3 + 8, var4 + 1, false, varCardCount)  ;  

				varCardCount.unobserveValue(8)  ;  
			}  
		}  
		return d;  
	}  

	public void settleBets(int var)  
	{  
		for (int i = 0;   i < this.numberOfHands;   i++)   {  
			if (this.handCards[i].isBlackjack()  )  
			{  
				this.money = ((int)  (this.money + this.handCards[i].getBet()   * 1.5D)  )  ;  
				this.handCardsInfo[0] = this.handCardsInfo[0] + 1;  
			}  
			else if (this.handCards[i].busted()  )  
			{  
				this.money -= this.handCards[i].getBet()  ;  
				this.handCardsInfo[1] = this.handCardsInfo[1] + 1;  
			}  
			else if (var > 15)  
			{  
				this.money += this.handCards[i].getBet()  ;  
				this.handCardsInfo[0] = this.handCardsInfo[0] + 1;  
			}  
			else if (var > this.handCards[i].getValue()  )  
			{  
				this.money -= this.handCards[i].getBet()  ;  
				this.handCardsInfo[1] = this.handCardsInfo[1] + 1;  
			}  
			else if (var < this.handCards[i].getValue()  )  
			{  
				this.money += this.handCards[i].getBet()  ;  
				this.handCardsInfo[0] = this.handCardsInfo[0] + 1;  
			}  
			else
			{  
				this.handCardsInfo[2] = this.handCardsInfo[2] + 1;  
			}  
		}  
		this.handCardsInfo[3] = this.handCardsInfo[3] + this.numberOfHands;  
		this.ante = 10;  
	}  
	
	public double playerRecursive(int var1, int var2, int var3, int var4, Counter varCardCount)  
	{  
		if (var1 > 15)  
		{  
			if (var2 == 0)   {  
				return -1.0D;  
			}  
			var1 = var1 - 10;  
			var2--;  
		}  
		double d1 = -1.0D;  
		if (var1 > 8)   {  
			d1 = dealerRecursive(var1, var2, var3, var4, true, varCardCount)  ;  
		}  
		double d2 = 0.0D;  
		for (int i = 2;   i < 8;   i++)   {  
			if (varCardCount.chance(i)   > 0.0D)  
			{  
				varCardCount.observeValue(i)  ;  
				d2 = d2 + varCardCount.chance(i)   * playerRecursive(var1 + i, var2, var3, var4, varCardCount)  ;  
				varCardCount.unobserveValue(i)  ;  
			}  
		}  
		if (varCardCount.chance(8)   > 0.0D)  
		{  
			varCardCount.observeValue(8)  ;  
			d2 = d2 + varCardCount.chance(8)   * playerRecursive(var1 + 8, var2 + 1, var3, var4, varCardCount)  ;  
			varCardCount.unobserveValue(8)  ;  
		}  
		if (d1 > d2)   {  
			return d1;  
		}  
		return d2;  
	}  
	
	public String playerDecision(int var1, int var2, int var3, boolean paramBoolean1, boolean paramBoolean2, Counter varCardCount)  
	{  
		int i = 0;  
		if (var3 == 8)   {  
			i++;  
		}  
		double d1 = -1.0D;  
		if (var1 > 8)   {  
			d1 = dealerRecursive(var1, var2, var3, i, true, varCardCount)  ;  
		}  
		double d2 = 0.0D;  
		for (int j = 2;   j < 8;   j++)  
		{  
			varCardCount.observeValue(j)  ;  
			d2 = d2 + varCardCount.chance(j)   * playerRecursive(var1 + j, var2, var3, i, varCardCount)  ;  
			varCardCount.unobserveValue(j)  ;  
		}  
		varCardCount.observeValue(8)  ;  
		d2 = d2 + varCardCount.chance(8)   * playerRecursive(var1 + 8, var2 + 1, var3, i, varCardCount)  ;  
		varCardCount.unobserveValue(8)  ;  

		double d3 = -1.0D;  
		if (paramBoolean1)  
		{  
			d3 = 0.0D;  
			for (int k = 2;   k < 8;   k++)  
			{  
				varCardCount.observeValue(k)  ;  
				d3 = d3 + varCardCount.chance(k)   * dealerRecursive(var1 + k, var2, var3, i, true, varCardCount)  ;  
				varCardCount.unobserveValue(k)  ;  
			}  
			varCardCount.observeValue(8)  ;  
			d2 = d2 + varCardCount.chance(8)   * dealerRecursive(var1 + 8, var2 + 1, var3, i, true, varCardCount)  ;  
			varCardCount.unobserveValue(8)  ;  

			d3 *= 2.0D;  
		}  
		double d4 = -1.0D;  
		if (paramBoolean2)  
		{  
			int m = var1 / 2;  
			if (var2 == 1)   {  
				m = 11;  
			}  
			d4 = projectedValSplit(m, var3)  ;  
			d4 *= 2.0D;  
		}  
		if ((d1 > d2)   && (d1 > d3)   && (d1 > d4)  )   {  
			return new String("Stand")  ;  
		}  
		if ((d2 > d1)   && (d2 > d3)   && (d2 > d4)  )   {  
			return new String("Hit")  ;  
		}  
		if ((d4 > d2)   && (d4 > d3)   && (d4 > d1)  )   {  
			return new String("Split")  ;  
		}  
		
		Random r = new Random()  ;  
		int n = r.nextInt(2)  ;  
		String moves[] = {  "Stand","Hit","Double Down"}  ;  
		
		return moves[n];  
		
	}  

	public double projectedValSplit(int var1, int var2)  
	{  
		this.weightNN = new double[3][9];  
		this.weightMin = new double[9];  
		this.weightRange = new double[9];  

		this.weightNN[0][0] = -1.21651929D;  
		this.weightNN[0][1] = -1.75106837D;  
		this.weightNN[0][2] = -1.5230685D;  
		this.weightNN[0][3] = -2.1164724D;  
		this.weightNN[0][4] = -2.26898277D;  
		this.weightNN[0][5] = 1.66608082D;  
		this.weightNN[0][6] = 0.02400712D;  
		this.weightNN[0][7] = -0.24058565D;  
		this.weightNN[0][8] = 8.81926644D;  
		this.weightNN[1][0] = 1.22057476D;  
		this.weightNN[1][1] = 1.09101458D;  
		this.weightNN[1][2] = 1.10072435D;  
		this.weightNN[1][3] = 1.22400125D;  
		this.weightNN[1][4] = 1.65969363D;  
		this.weightNN[1][5] = 0.57825291D;  
		this.weightNN[1][6] = 1.72673181D;  
		this.weightNN[1][7] = -9.38866057D;  
		this.weightNN[1][8] = -0.27153814D;  
		this.weightNN[2][0] = 0.90780594D;  
		this.weightNN[2][1] = 1.51781949D;  
		this.weightNN[2][2] = 1.11716148D;  
		this.weightNN[2][3] = 2.02920127D;  
		this.weightNN[2][4] = 2.83394159D;  
		this.weightNN[2][5] = -0.29303796D;  
		this.weightNN[2][6] = 0.23615821D;  
		this.weightNN[2][7] = 0.23876952D;  
		this.weightNN[2][8] = -10.39379175D;  
		
		this.weightMin[0] = -1.0D;  
		this.weightMin[1] = -0.796875D;  
		this.weightMin[2] = -1.0D;  
		this.weightMin[3] = -0.75D;  
		this.weightMin[4] = -1.371428571D;  
		this.weightMin[5] = -0.796875D;  
		this.weightMin[6] = -1.351351351D;  
		this.weightMin[7] = 1.5D;  
		this.weightMin[8] = 1.5D;  
		
		this.weightRange[0] = 2.052631579D;  
		this.weightRange[1] = 1.699314024D;  
		this.weightRange[2] = 1.733333333D;  
		this.weightRange[3] = 1.71969697D;  
		this.weightRange[4] = 2.273867595D;  
		this.weightRange[5] = 0.820320856D;  
		this.weightRange[6] = 1.936170213D;  
		this.weightRange[7] = 9.0D;  
		this.weightRange[8] = 9.0D;  
		
		double[] doubleArr1 = new double[3];  

		doubleArr1[0] = 0.15847939D;  
		doubleArr1[1] = 3.21499955D;  
		doubleArr1[2] = 0.58619056D;  

		double[] doubleArr2 = this.cardCount.getInputs()  ;  

		doubleArr2[7] = var1;  
		doubleArr2[8] = var2;  
		for (int i = 0;   i < 3;   i++)   {  
			for (int j = 0;   j < 9;   j++)   {  
				doubleArr1[i] = doubleArr1[i] + this.weightNN[i][j] * (doubleArr2[j] - this.weightMin[j])   / this.weightRange[j];  
			}  
		}  
		for (int k = 0;   k < 3;   k++)   {  
			doubleArr1[k] = squish(doubleArr1[k])  ;  
		}  
		double d = squish(2.0134737D * doubleArr1[0] + -3.46032026D * doubleArr1[1] + 2.44507157D * doubleArr1[2] + 0.87420299D)  ;  
		d = d * 2.0D - 1.0D;  

		return d;  
	}  
	
	public Hand splitHand(CardValueType varCard1, CardValueType varCard2)  
	{  
		observeValue(varCard1.getValue()  )  ;  
		observeValue(varCard2.getValue()  )  ;  

		Iterator<?> localIterator = this.handCards[this.currentHand].cardValueTypes.iterator()  ;  
		CardValueType localCard1 = (CardValueType)  localIterator.next()  ;  
		CardValueType localCard2 = (CardValueType)  localIterator.next()  ;  

		this.handCards[this.currentHand] = new Hand(localCard1, varCard1)  ;  
		this.handCards[this.numberOfHands] = new Hand(localCard2, varCard2)  ;  
		this.handCards[this.currentHand].changeBet(this.ante)  ;  
		this.handCards[this.numberOfHands].changeBet(this.ante)  ;  
		this.numberOfHands = this.numberOfHands + 1;  

		return this.handCards[(this.numberOfHands - 1)  ];  
	}  
	

	public String bettingAdvice()  
	{  
		this.weightNN = new double[3][7];  
		this.weightMin = new double[7];  
		this.weightRange = new double[7];  

		this.weightNN[0][0] = -0.12920576D;  
		this.weightNN[0][1] = -0.06412242D;  
		this.weightNN[0][2] = -0.17444408D;  
		this.weightNN[0][3] = -0.01934681D;  
		this.weightNN[0][4] = 0.12794927D;  
		this.weightNN[0][5] = -0.03082812D;  
		this.weightNN[0][6] = -0.00958003D;  
		this.weightNN[1][0] = 0.17491729D;  
		this.weightNN[1][1] = -0.0467863D;  
		this.weightNN[1][2] = 0.17462811D;  
		this.weightNN[1][3] = 0.03037175D;  
		this.weightNN[1][4] = -0.27435917D;  
		this.weightNN[1][5] = -0.34074678D;  
		this.weightNN[1][6] = 0.04785409D;  
		this.weightNN[2][0] = 0.37539159D;  
		this.weightNN[2][1] = 0.25006792D;  
		this.weightNN[2][2] = 0.51257027D;  
		this.weightNN[2][3] = 0.68247989D;  
		this.weightNN[2][4] = -0.06858553D;  
		this.weightNN[2][5] = -0.8879999D;  
		this.weightNN[2][6] = -0.40723251D;  
		
		this.weightMin[0] = -0.711111111D;  
		this.weightMin[1] = -0.682926829D;  
		this.weightMin[2] = -0.704545455D;  
		this.weightMin[3] = -0.74D;  
		this.weightMin[4] = -0.779661017D;  
		this.weightMin[5] = -0.368055556D;  
		this.weightMin[6] = -0.711111111D;  
		
		this.weightRange[0] = 2.238888889D;  
		this.weightRange[1] = 1.360346184D;  
		this.weightRange[2] = 1.510101011D;  
		this.weightRange[3] = 1.597142857D;  
		this.weightRange[4] = 1.351089588D;  
		this.weightRange[5] = 0.715616532D;  
		this.weightRange[6] = 1.421637427D;  
		
		double[] doubleArr1 = new double[3];  

		doubleArr1[0] = 0.10667967D;  
		doubleArr1[1] = 0.15053564D;  
		doubleArr1[2] = 0.98879035D;  

		double[] doubleArr2 = this.cardCount.getInputs()  ;   
		
		
		for (int i = 0;   i < 3;   i++)   {  
			for (int j = 0;   j < 7;   j++)   {  
				doubleArr1[i] = doubleArr1[i] + this.weightNN[i][j] * (doubleArr2[j] - this.weightMin[j])   / this.weightRange[j];  
			}  
		}  
		for (int k = 0;   k < 3;   k++)   {  
			doubleArr1[k] = squish(doubleArr1[k])  ;  
		}  
		double d = squish(0.43549004D * doubleArr1[0] + 0.24037475D * doubleArr1[1] + -0.79204378D * doubleArr1[2] + 0.27736448D)  ;  
		//System.out.println("d = " + d)  ;  
		if (d > 0.5D)   {  
			return "Bet High";  
		}  
		return "Bet Low";  
	}  
}  
