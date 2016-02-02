package blackjack;  

public class Counter
{

	int deckCount;  
	int remainingCards;  
	int[] cardCategory;  

	public Counter(int var)  
	{
		this.deckCount = var;  
		resetCount()  ;  
	}  

	public Counter(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12)  
	{	
		this.cardCategory = new int[9];  
		this.deckCount = var11;  
		this.remainingCards = var12;  
		this.cardCategory[2] = var2;  
		this.cardCategory[3] = var3;  
		this.cardCategory[4] = var4;   
		this.cardCategory[5] = var5;  
		this.cardCategory[6] = var6;  
		this.cardCategory[7] = var7;  
		this.cardCategory[8] = var1;  
		
	}  

	public void resetCount()  
	{
		this.remainingCards = (this.deckCount * 28)  ;  
		this.cardCategory = new int[9];  
		for (int i = 2;   i < 9;   i++)   {
			this.cardCategory[i] = (4 * this.deckCount)  ;  
		}  
	}  

	public Counter spanNew(int var)  
	{
		this.cardCategory[var] = this.cardCategory[var] - 1;  
		Counter localCardCount = new Counter(this.cardCategory[2], this.cardCategory[3], this.cardCategory[4], this.cardCategory[5], this.cardCategory[6], this.cardCategory[7], this.cardCategory[8], 0, 0, 0, this.deckCount, this.remainingCards - 1)  ;  

		this.cardCategory[var] = this.cardCategory[var] + 1;  
		return localCardCount;  
	}  

	public void observeValue(int var)  
	{
		this.cardCategory[var] = this.cardCategory[var] - 1;  
		this.remainingCards = this.remainingCards - 1;  
	}  

	public void unobserveValue(int var)  
	{
		this.cardCategory[var] = this.cardCategory[var] + 1;  
		this.remainingCards = this.remainingCards + 1;  
	}  

	public double chance(int var)  
	{
		return this.cardCategory[var] / this.remainingCards;  
	}  

	public double chanceDealerDown(int var1, int var2)  
	{
		if (var2 == 7)  
		{
			if (var1 == 8)   {
				return 0.0D;  
			}  
			return this.cardCategory[var1] / (this.remainingCards - this.cardCategory[8])  ;  
		}  
		if (var1 == 7)   {
			return 0.0D;  
		}  
		return this.cardCategory[var1] / (this.remainingCards - this.cardCategory[7])  ;  
	}  

	public int getRemainingCards()  
	{
		return this.remainingCards;  
	}  

	public double[] getInputs()  
	{
		double[] doubleArr = new double[9];  
		for (int i = 2;   i < 9;   i++)   {
			doubleArr[(i - 2)  ] = (this.cardCategory[i] * 7.0D / this.remainingCards - 1.0D)  ;  
		}  
		doubleArr[7] = (this.cardCategory[8] * 7.0D / this.remainingCards / 4.0D - 1.0D)  ;  

		return doubleArr;  
	}  
}  
