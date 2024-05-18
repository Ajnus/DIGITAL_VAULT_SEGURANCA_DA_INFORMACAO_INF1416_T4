package view;

import java.lang.StringBuffer;

// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

public class TesteUnitarioProgram {
    public static void main(String[] args){
        TelaRegistro testeRegistro = new TelaRegistro();
    }
    public static String HexCodeString(byte[] hexCode)
	{
		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < hexCode.length; i++)
		{
			String hex = Integer.toHexString(0x0100 + (hexCode[i] & 0x00FF)).substring(1);
			buf.append((hex.length() < 2 ? "0" : "") + hex);
		}

		return buf.toString();
	}
}
