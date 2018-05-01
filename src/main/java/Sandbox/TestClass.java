package Sandbox;

import java.util.Random;

public class TestClass {

	public String printString() {
		return "Hello World";
	}

	public String getRandomMac() {

		Random rand = new Random();

		byte[] macAddr = new byte[6];

		rand.nextBytes(macAddr);

		// zeroing last 2 bytes to make it unicast and locally adminstrated
		macAddr[0] = (byte) (macAddr[0] & (byte) 254); 

		StringBuilder sb = new StringBuilder(18);

		for (byte b : macAddr) {

			if (sb.length() > 0)
				sb.append(":");

			sb.append(String.format("%02x", b));
		}

		sb = sb.replace(0, 8, "00:A0:BC");

		return sb.toString().toUpperCase();
	}

	public static void main(String[] args) {
		TestClass tc = new TestClass();

		for (int i = 0; i < 100; i++) {
			String randomMac = tc.getRandomMac();

			System.out.println(randomMac);
		}
	}
}
