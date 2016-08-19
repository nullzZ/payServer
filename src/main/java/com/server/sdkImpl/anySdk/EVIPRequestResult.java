package com.server.sdkImpl.anySdk;
/**
 * 
 * @author nullzZ
 *
 */
public enum EVIPRequestResult
{
	OK((byte)0),
	PayFailed((byte)1),
	InvalidOrderID((byte)2),
	InvalidOrderTime((byte)3),
	InvalidProduct((byte)4),
	InvalidRoleID((byte)5),
	CardAlreadyAquired((byte)6),
	MonthCardExpired((byte)7),
	DontHaveMonthCard((byte)8),
	LifetimeCardExpired((byte)9),
	DontHaveLifetimeCard((byte)10),
	NotEnoughVIPLevel((byte)11),
	VIPLevelAwardAlreadyAquired((byte)12),
	InvalidPayID((byte)13),
	NotFoundOrder((byte)14),
	InvalidPrice((byte)15),
	ExpiredOrderID((byte)16),
	
	Processing((byte)98),
	Unknown((byte)99),
	;
	
	private byte value;
	
	private EVIPRequestResult(byte value)
	{
		this.value = value;
	}
	
	public byte value()
	{
		return this.value;
	}
	
	public static EVIPRequestResult fromByte(byte type)
	{
		EVIPRequestResult result = null;
		EVIPRequestResult[] values = EVIPRequestResult.values();
		for(EVIPRequestResult value : values)
		{
			if(value.value() == type)
			{
				result = value;
				break;
			}
		}
		result = result == null ? Unknown : result;
		return result;
	}
	

}
