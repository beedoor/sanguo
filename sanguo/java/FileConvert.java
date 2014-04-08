import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileConvert {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter(new File("aa")));
			File f = new File("logs\\bd\\bd.log");
			BufferedReader fr = new BufferedReader(new FileReader(f));
			String line = null;
			while ((line = fr.readLine()) != null) {
//				System.out.println(parseUnicode(line));
				fw.write(parseUnicode(line));
				fw.write("\r\n");
			}
			// System.out.println("s11.badge=0;s11.cityName=\"\u9EC4\u660E\";s11.citySrc=5;s11.color=0;s11.heroCount=5;s11.id=20988;s11.leagueId=0;s11.lv=1;s11.occupierHeroCount=0;s11.occupierId=0;s11.occupierName=null;s11.occupyTime=null;s11.playerId=15529;s11.protectMsLeft=0;s11.typeAsInt=1;s11.unionName=\"\";");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String parseUnicode(String line) {
		int len = line.length();
		char[] out = new char[len];// 保存解析以后的结果
		int outLen = 0;
		for (int i = 0; i < len; i++) {
			char aChar = line.charAt(i);
			if (aChar == '\\') {
				aChar = line.charAt(++i);
				if (aChar == 'u') {
					int value = 0;
					for (int j = 0; j < 4; j++) {
						aChar = line.charAt(++i);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed \\uxxxx encoding.");
						}
					}
					out[outLen++] = (char) value;
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					out[outLen++] = aChar;
				}
			} else {
				out[outLen++] = aChar;
			}
		}
		return new String(out, 0, outLen);
	}

}
