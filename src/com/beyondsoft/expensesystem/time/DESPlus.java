package com.beyondsoft.expensesystem.time;

import java.security.Key;

import javax.crypto.Cipher;

public class DESPlus {
	private static String strDefaultKey = "www.talk";

	private Cipher encryptCipher = null;

	private Cipher decryptCipher = null;

	/**
	 * ��byte����ת��Ϊ��ʾ16����ֵ���ַ� �磺byte[]{8,18}ת��Ϊ��0813�� ��public static byte[]
	 * hexStr2ByteArr(String strIn) ��Ϊ�����ת�����
	 * 
	 * @param arrB
	 *            ��Ҫת����byte����
	 * @return ת������ַ�
	 * @throws Exception
	 *             �������������κ��쳣�������쳣ȫ���׳�
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// ÿ��byte�������ַ���ܱ�ʾ�������ַ�ĳ��������鳤�ȵ�����
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// �Ѹ���ת��Ϊ����
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// С��0F������Ҫ��ǰ�油0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * ����ʾ16����ֵ���ַ�ת��Ϊbyte���飬 ��public static String byteArr2HexStr(byte[] arrB)
	 * ��Ϊ�����ת�����
	 * 
	 * @param strIn
	 *            ��Ҫת�����ַ�
	 * @return ת�����byte����
	 * @throws Exception
	 *             �������������κ��쳣�������쳣ȫ���׳�
	 * @author Administrator
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * Ĭ�Ϲ��췽����ʹ��Ĭ����Կ
	 * 
	 * @throws Exception
	 */
	public DESPlus() throws Exception {
		this(strDefaultKey);
	}

	/**
	 * ָ����Կ���췽��
	 * 
	 * @param strKey
	 *            ָ������Կ
	 * 
	 * @throws Exception
	 */
	public DESPlus(String strKey) throws Exception {
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	public byte[] encrypt(byte[] arrB) throws Exception 
	{
		return encryptCipher.doFinal(arrB);
	}
	public String encrypt(String strIn) throws Exception 
	{
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	public byte[] decrypt(byte[] arrB) throws Exception 
	{
		return decryptCipher.doFinal(arrB);
	}
	public String decrypt(String strIn) throws Exception 
	{
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	private Key getKey(byte[] arrBTmp) throws Exception {
		byte[] arrB = new byte[8]; 
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) 
		{
			arrB[i] = arrBTmp[i];
		}

		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}
	
	public static void main(String[]args) throws Exception
	{
		DESPlus de=null;
		try 
		{
			de = new DESPlus();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		System.out.println(de.encrypt("oip20140328"));
		System.out.println(de.encrypt("yd123457"));
		System.out.println(de.decrypt("2a0843ea9819a349dbc3f9ea261e9062"));
		System.out.println(de.decrypt("af828b33f03945c96e1b48b196e829a8"));
	}

}
