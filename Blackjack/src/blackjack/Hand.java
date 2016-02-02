package blackjack;  

import java.util.Iterator;  
import java.util.LinkedList;  

public class Hand
{

	public LinkedList<CardValueType> cardValueTypes;  
	boolean splitHand;  
	boolean twoCards;  
	int handValue;  
	int betValue;  
	boolean soft;  

	public Hand()  
	{
		this.handValue = 0;  
		this.betValue = 10;  
		this.soft = false;  
		this.twoCards = false;  
		this.cardValueTypes = new LinkedList<CardValueType>()  ;  
		this.splitHand = false;  
	}  
	
	public Hand(CardValueType varCard)  
	{
		this.handValue = 0;  
		this.twoCards = false;  
		this.cardValueTypes = new LinkedList<CardValueType>()  ;  
		this.cardValueTypes.add(new CardValueType(28)  )  ;  
		this.cardValueTypes.add(varCard)  ;  
		addValue(varCard.getValue()  )  ;  
		this.splitHand = false;  
		if (varCard.getValue()   == 8)   {
			this.soft = true;  
		}   else {
			this.soft = false;  
		}  
	}  

	public Hand(CardValueType varCard1, CardValueType varCard2)  
	{
		this.handValue = 0;  
		this.twoCards = true;  
		this.cardValueTypes = new LinkedList<CardValueType>()  ;  
		this.cardValueTypes.add(varCard1)  ;  
		addValue(varCard1.getValue()  )  ;  
		this.cardValueTypes.add(varCard2)  ;  
		addValue(varCard2.getValue()  )  ;  
		if (varCard1.getValue()   == varCard2.getValue()  )   {
			this.splitHand = true;  
		}   else {
			this.splitHand = false;  
		}  
		if ((varCard1.getValue()   == 8)   || (varCard2.getValue()   == 8)  )   {
			this.soft = true;  
		}   else {
			this.soft = false;  
		}  
	}  
	
	public int setBet(int var)  
	{
		this.betValue = var;  
		return this.betValue;  
	}  

	public int getBet()  
	{
		return this.betValue;  
	}  

	public int getValue()  
	{
		return this.handValue;  
	}  

	public int isSoft()  
	{
		if (this.soft == true)   {
			return 1;  
		}  
		return 0;  
	}  

	public boolean issplitHand()  
	{
		return this.splitHand;  
	}  

	public boolean isTwoCards()  
	{
		return this.twoCards;  
	}  

	public boolean isBlackjack()  
	{
		return (this.twoCards)   && (this.handValue == 15)  ;   
	}  
	
	public boolean busted()  
	{
		return this.handValue > 15;  
	}  

	private void addValue(int var)  
	{
		@SuppressWarnings("unused")  
		Iterator<CardValueType> localIterator = this.cardValueTypes.iterator()  ;  

		this.handValue = this.handValue + var;  
		if (var == 8)   {
			if (this.handValue > 15)   {
				this.handValue = this.handValue - 7;  
			}   else {
				this.soft = true;  
			}  
		}  
		if ((this.handValue > 15)   && (this.soft == true)  )  
		{
			this.handValue = this.handValue - 7;  
			this.soft = false;  
		}  
	}  

	public void addCard(CardValueType varCard)  
	{
		this.cardValueTypes.add(varCard)  ;  
		this.splitHand = false;  
		this.twoCards = false;  
		addValue(varCard.getValue()  )  ;  
	}  

	public void addFirstCard(CardValueType varCard)  
	{
		@SuppressWarnings("unused")  
		CardValueType localCard = (CardValueType)  this.cardValueTypes.removeFirst()  ;  
		this.cardValueTypes.addFirst(varCard)  ;  
		addValue(varCard.getValue()  )  ;  
	}  

	public int changeBet(int var)  
	{
		this.betValue = this.betValue + var;  
		return this.betValue;  
	}  
}  
