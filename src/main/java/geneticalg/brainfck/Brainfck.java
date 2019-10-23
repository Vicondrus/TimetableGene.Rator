package geneticalg.brainfck;

import java.util.Scanner;

public class Brainfck {
	private long maxMillis = 25;
	private Scanner sc = new Scanner(System.in);
	private final int LENGTH = 65535;
	private byte[] mem = new byte[LENGTH];
	private int dataPointer;
	private String console = "";

	public long interpret(String code) {
		long start = System.currentTimeMillis();
		int l = 0;
		for (int i = 0; i < code.length(); i++) {
			// System.out.print(i);
			if (System.currentTimeMillis() - start > maxMillis)
				return -1;
			else if (code.charAt(i) == '>') {
				dataPointer = (dataPointer == LENGTH - 1) ? 0 : dataPointer + 1;
			} else if (code.charAt(i) == '<') {
				dataPointer = (dataPointer == 0) ? LENGTH - 1 : dataPointer - 1;
			} else if (code.charAt(i) == '+') {
				mem[dataPointer]++;
			} else if (code.charAt(i) == '-') {
				mem[dataPointer]--;
			} else if (code.charAt(i) == '.') {
				// System.out.print((char) mem[dataPointer]);
				console += (char) mem[dataPointer];
			} else if (code.charAt(i) == ',') {
				// mem[dataPointer] = (byte) sc.next().charAt(0);
			} else if (code.charAt(i) == '[') {
				if (mem[dataPointer] == 0) {
					if (i < code.length() - 1)
						i++;
					while ((l > 0 || code.charAt(i) != ']')) {
						if (System.currentTimeMillis() - start > maxMillis)
							return -1;
						if (code.charAt(i) == '[')
							l++;
						if (code.charAt(i) == ']')
							l--;
						if (i < code.length() - 1)
							i++;
					}
				}
			} else if (code.charAt(i) == ']') {
				if (mem[dataPointer] != 0) {
					if (i > 0)
						i--;
					while ((l > 0 || code.charAt(i) != '[')) {
						if (System.currentTimeMillis() - start > maxMillis)
							return -1;
						if (code.charAt(i) == ']')
							l++;
						if (code.charAt(i) == '[')
							l--;
						if (i > 0)
							i--;
					}
					if (i > 0)
						i--;
				}
			}
		}
		return System.currentTimeMillis() - start;
	}

	public String getConsole() {
		return console;
	}

	public static void main(String[] args) {
		Brainfck bf = new Brainfck();
		System.out.println(bf.interpret("+[-[<<[+[--->]-[<<<]]]>>>-]>-.---.>..>.<<<<-.<+.>>>>>.>.<<.<-."));
		System.out.println(bf.getConsole());
	}
}
