package blackjack;  

public class CardValueType
{

	int value;  
	int place;  

	public CardValueType(int var)  
	{
		this.place = var;  
		
		this.value = (var % 7 + 1)  ;    
		if (this.value == 1)   {
			this.value = 8;  
		}  
	}  

	public int getPlace()  
	{
		return this.place;  
	}  
	
	public int getValue()  
	{
		return this.value;  
	}  

}  
