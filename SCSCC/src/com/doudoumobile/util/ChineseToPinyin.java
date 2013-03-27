package com.doudoumobile.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class ChineseToPinyin {

	private ChineseToPinyin() {
		pinyinTable = new HashMap<String, LinkedList<String>>();
		conflictPinyinMap = new HashMap<String, Set<String>>();
		tones = false;
		
		loadData();
	}

	private void loadData() {
		loadPinyinMap();
		loadConflictPinyinSet();
	}

	private void loadPinyinMap() {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(pinyinTableURL);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line;
			StringTokenizer st;
			String zhongwen;
			LinkedList<String> pinyin;
			while ((line = br.readLine()) != null) {
				st = new StringTokenizer(line);
				zhongwen = st.nextToken();
				pinyin = new LinkedList<String>();
				while (st.hasMoreTokens()) {
					pinyin.add(st.nextToken());
				}
				pinyinTable.put(zhongwen, pinyin);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadConflictPinyinSet() {
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(conflictPinyinSetURL);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line;
			StringTokenizer st;
			String lastWord;
			String keyS;
			while ((line = br.readLine()) != null) {
				st = new StringTokenizer(line, "' ");
				st.nextToken();
				st.nextToken();
				lastWord = st.nextToken();
				keyS = st.nextToken();
				addIntoConflictSet(keyS, lastWord);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addIntoConflictSet(String key, String value) {
		if (conflictPinyinMap.containsKey(key)) {
			Set<String> s = conflictPinyinMap.get(key);
			s.add(value);
		} else {
			Set<String> s = new HashSet<String>();
			s.add(value);
			conflictPinyinMap.put(key, s);
		}
	}

	// 检查是否是中文
	public static boolean checkChar(char oneChar) {
		if ((oneChar >= '\u4e00' && oneChar <= '\u9fa5')
				|| (oneChar >= '\uf900' && oneChar <= '\ufa2d'))
			return true;
		return false;
	}

	private void addNonePinyin(ArrayList<StringBuilder> al, String feiZhongwen) {
		if (0 == al.size()) {
			al.add(new StringBuilder(feiZhongwen));
			return;
		}
		for (StringBuilder sb : al) {
			sb.append(feiZhongwen);
		}
	}

	private void insertConflictPinyin(ArrayList<StringBuilder> result, String yunmu, boolean tones) {
		String tmpResult = yunmu;
		if (!tones) {
			tmpResult = yunmu.substring(0, yunmu.length() - 1);
		} else {
			tmpResult = convertPinyinToneNumberToMark(yunmu);
		}
		for (StringBuilder pin : result) {
			boolean flag = false;
			Set<String> set = conflictPinyinMap.get(yunmu.substring(0,
					yunmu.length() - 1));
			for (String s : set) {
				if (lastWord.endsWith(s)) {
					pin.append("'" + tmpResult);
					flag = true;
					break;
				}
			}
			if (!flag) {
				pin.append(tmpResult);
			}
		}
	}

	private void addPinyin(ArrayList<StringBuilder> result, String s, boolean tones) {
		StringBuilder sb = new StringBuilder(s);
		
		if (0 == result.size()) {
			lastWord = s.substring(0, s.length() - 1);
			if (tones) {
				result.add(new StringBuilder(convertPinyinToneNumberToMark(s)));
			} else {
				result.add(new StringBuilder(sb.deleteCharAt(s.length() - 1)));
			}
			return;
		}

		sb.deleteCharAt(sb.length() - 1);
		if (checkSuspected(sb.toString())) {
			insertConflictPinyin(result, s, tones);
			lastWord = s.substring(0, s.length() - 1);
			return;
		} else {
			if (tones) {
				String marked = convertPinyinToneNumberToMark(s.toString());
				sb = new StringBuilder(marked);
			}
			for (int i = 0; i < result.size(); i++) {
				result.get(i).append(sb);
			}
		}
		
		lastWord = s.substring(0, s.length() - 1);
	}

	private ArrayList<StringBuilder> addPinyinList(ArrayList<StringBuilder> result, List<String> pinyinList, boolean tones) {
		ArrayList<StringBuilder> tmpResult = new ArrayList<StringBuilder>();
		if (!tones) {
			pinyinList = filter(pinyinList);
		}
		for (int i = 0; i < pinyinList.size(); i++) {
			ArrayList<StringBuilder> sbList = new ArrayList<StringBuilder>();
			for (StringBuilder sb : result) {
				StringBuilder tmp = new StringBuilder(sb.toString());
				sbList.add(tmp);
			}
			addPinyin(sbList, pinyinList.get(i), tones);
			tmpResult.addAll(sbList);
		}

		return tmpResult;
	}

	public List<String> ZhongWenToPinyin(String input) {
		ArrayList<StringBuilder> result = new ArrayList<StringBuilder>();
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (checkChar(c)) {
				String zhongwen = String.valueOf(c);
				LinkedList<String> pinyinList = pinyinTable.get(zhongwen);
				if (null == pinyinList || pinyinList.size() == 0) {
					System.out.println("找到异常中文字" + pinyinList + zhongwen);
					return null;
				}
				// 多音字
				if (pinyinList.size() > 1) {
					result = addPinyinList(result, pinyinList, tones);
				} else {
					addPinyin(result, pinyinList.getFirst(), tones);
				}
			} else {
				String feiZhongwen = String.valueOf(c);
				lastWord += feiZhongwen;
				addNonePinyin(result, feiZhongwen);
			}
		}

		ArrayList<String> resultString = new ArrayList<String>(result.size());
		for (StringBuilder sb : result) {
			resultString.add(sb.toString());
		}

		return resultString;
	}
	
	//public List<String> ZhongWenToPinyinWithSeg
	
	private List<String> filter(List<String> list) {
		List<String> result = new LinkedList<String>();
		Set<String> tmpSet = new HashSet<String>();
		for (String string : list) {
			String former = string.substring(0, string.length() - 1);
			tmpSet.add(former + ".");
		}
		for (String string : tmpSet) {
			result.add(string);
		}
		return result;
	}
	
	private boolean checkSuspected(String suspect) {
		return conflictPinyinMap.containsKey(suspect);
	}

	private String convertPinyinToneNumberToMark(String pinyinStr) {
		int toneNO = Character.getNumericValue(pinyinStr.charAt(pinyinStr
				.length() - 1));
		int indexToConvert = -1;
		char charToConvert = '#';

		char charA = 'a';
		char charE = 'e';
		String ouStr = "ou";
		int indexOfA = pinyinStr.indexOf(charA);
		int indexOfE = pinyinStr.indexOf(charE);
		int ouIndex = pinyinStr.indexOf(ouStr);
		String allUnmarkedVowelStr = "aoeiuv";
		String allMarkedVowelStr = "aāáăàoōóŏòeēéĕèiīíĭìuūúŭùüǖǘǚǜ";

		// find the tone to convert
		if (-1 != indexOfA) {
			indexToConvert = indexOfA;
			charToConvert = charA;
		} else if (-1 != indexOfE) {
			indexToConvert = indexOfE;
			charToConvert = charE;
		} else if (-1 != ouIndex) {
			indexToConvert = ouIndex;
			charToConvert = ouStr.charAt(0);
		} else {
			for (int i = pinyinStr.length() - 1; i >= 0; i--) {
				if (String.valueOf(pinyinStr.charAt(i)).matches(
						"[" + allUnmarkedVowelStr + "]")) {
					indexToConvert = i;
					charToConvert = pinyinStr.charAt(i);
					break;
				}
			}
		}
		// convert
		int rowIndex = allUnmarkedVowelStr.indexOf(charToConvert);
		int columnIndex = toneNO;

		int vowelLocation = rowIndex * 5 + columnIndex;

		char markedVowel = allMarkedVowelStr.charAt(vowelLocation);

		StringBuffer resultBuffer = new StringBuffer();

		resultBuffer.append(pinyinStr.substring(0, indexToConvert).replaceAll("v", "ü"));
		resultBuffer.append(markedVowel);
		resultBuffer.append(pinyinStr.substring(indexToConvert + 1,	pinyinStr.length() - 1).replaceAll("v", "ü"));

		return resultBuffer.toString();

	}

	public static ChineseToPinyin getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		List<String> result = ChineseToPinyin.getInstance().ZhongWenToPinyin("测");
		for (String s : result) {
			System.out.println(s);
		}
	}

	private boolean tones;
	
	private String lastWord = "";

	private HashMap<String, LinkedList<String>> pinyinTable;
	private HashMap<String, Set<String>> conflictPinyinMap;

	private String pinyinTableURL = "gbk_pinyin_table";
	private String conflictPinyinSetURL = "conflictPinyinList";

	private static ChineseToPinyin instance = new ChineseToPinyin();

}
