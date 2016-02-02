package blackjack;  

import java.util.LinkedList;  
import java.util.Random;  

public class Shuffler
{  
	
	int deckCount;  
	int remainingCards;  
	LinkedList<CardValueType> cardValueTypes;  

	public Shuffler(int var)  
	{  
		this.deckCount = var;  
		shuffleNew()  ;  
	}  

	public void shuffleNew()  
	{  
		this.remainingCards = (this.deckCount * 28)  ;  
		int[] arrayOfInt = new int[this.remainingCards];  
		Random localRandom = new Random()  ;  
		for (int i = 0;   i < this.remainingCards;   i++)   {  
			arrayOfInt[i] = (i % 28)  ;  
		}  
		for (int j = 0;   j < this.remainingCards;   j++)  
		{  
			int k = localRandom.nextInt(this.remainingCards)  ;  
			int m = arrayOfInt[k];  
			arrayOfInt[k] = arrayOfInt[j];  
			arrayOfInt[j] = m;  
		}  
		this.cardValueTypes = new LinkedList<CardValueType>()  ;  
		for (int k = 0;   k < this.remainingCards;   k++)   {  
			this.cardValueTypes.add(new CardValueType(arrayOfInt[k])  )  ;  
		}  
	}  

	public CardValueType dealCard()  
	{  
		this.remainingCards = this.remainingCards - 1;  
		return (CardValueType)  this.cardValueTypes.removeFirst()  ;  
	}  

	public boolean lowOnCards()  
	{  
		if (this.remainingCards < 19)   {  
			return true;  
		}  
		return false;  
	}  
}  
