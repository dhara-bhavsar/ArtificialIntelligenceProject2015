package blackjack;  

public class Dealer
{

	Hand handCards;  
	CardValueType hiddenCard;  
	boolean blackjack;  

	public Dealer()  
	{
		this.handCards = new Hand()  ;  
	}  

	public int newHand(CardValueType varCard1, CardValueType varCard2)  
	{
		this.hiddenCard = varCard1;  
		this.handCards = new Hand(varCard2)  ;  
		
		if (varCard1.getValue()   + varCard2.getValue()   == 15)   {
			this.blackjack = true;  
		}   else {
			this.blackjack = false;  
		}  
		return varCard2.getValue()  ;  
	}  

	public Hand getHand()  
	{
		return this.handCards;  
	}  

	public int getValue()  
	{
		return this.handCards.getValue()  ;  
	}  

	public boolean hasBlackjack()  
	{
		return this.blackjack;  
	}  

	public int isSoft()  
	{
		return this.handCards.isSoft()  ;  
	}  

	public int showFirstCard()  
	{
		this.handCards.addFirstCard(this.hiddenCard)  ;  
		return this.hiddenCard.getValue()  ;  
	}  
	
	public int addCard(CardValueType varCard)  
	{
		this.handCards.addCard(varCard)  ;  
		return varCard.getValue()  ;  
	}  
}  
